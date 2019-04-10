/*
 * FileName:    OaNewsServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.news.service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.domain.ValidAble;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleCrudRestService;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.reference.PublishType;
import cn.com.chaochuang.common.reference.StatusFlag;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.repository.SysDepartmentRepository;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.oa.camera.reference.CameraType;
import cn.com.chaochuang.oa.datachange.reference.DataChangeTable;
import cn.com.chaochuang.oa.datachange.reference.OperationType;
import cn.com.chaochuang.oa.datachange.service.DataChangeService;
import cn.com.chaochuang.oa.news.bean.NewsEditBean;
import cn.com.chaochuang.oa.news.bean.NewsQueryShowBean;
import cn.com.chaochuang.oa.news.domain.OaNews;
import cn.com.chaochuang.oa.news.reference.NewsType;
import cn.com.chaochuang.oa.news.repository.OaNewsRepository;

/**
 * @author yuchunshu
 *
 */
@Service
@Transactional
public class OaNewsServiceImpl extends SimpleCrudRestService<OaNews, String> implements OaNewsService {

    @Autowired
    private OaNewsRepository        repository;

    @Autowired
    private SysAttachService        attachService;

    @Autowired
    private DataChangeService       dataChangeService;

    @Autowired
    private SysDepartmentRepository depRepository;

    @PersistenceContext
    private EntityManager           em;

    @Autowired
    private SysDepartmentService    deptService;
    
    @Override
    public SimpleDomainRepository<OaNews, String> getRepository() {
        return this.repository;
    }

    @Override
    public String saveNews(NewsEditBean bean) {
        OaNews news = null;
        if (bean != null && bean.getId() != null) {
            news = this.repository.findOne(bean.getId());
            // 保存数据变动
            dataChangeService.saveDataChange("News_id=" + bean.getId(), DataChangeTable.通知公告, OperationType.修改);
        } else {
            news = new OaNews();
        }
        news = BeanCopyUtil.copyBean(bean, OaNews.class);
        StatusFlag NewsStatus = bean.getStatus();

        Date date = new Date();
        String dateString = Tools.DATE_TIME_FORMAT.format(date);
        try {
            date = Tools.DATE_TIME_FORMAT.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (StatusFlag.暂存.equals(NewsStatus)) {
            if (news.getCreateDate() == null) {
                news.setCreateDate(date);
            }
            news.setPublishDate(null);
        }
        if (StatusFlag.已发布.equals(NewsStatus)) {
            if (news.getCreateDate() == null) {
                news.setCreateDate(date);
            }
            news.setPublishDate(date);
        }

        if (PublishType.单位.equals(bean.getPublishType())) {
            List<SysDepartment> depList = depRepository.findByRootDepartment(ValidAble.VALID);
            if (Tools.isNotEmptyList(depList)) {
                news.setPublishDepId(depList.get(0).getId());
            }
        }

        String attachIds = bean.getAttach();
        if (StringUtils.isNotBlank(attachIds)) {
            news.setAttachFlag(AttachFlag.有附件);
        } else {
            news.setAttachFlag(AttachFlag.无附件);
        }
        // 保证取到自动生成的ID
        news = this.repository.save(news);

        // 连接附件
        List<String> oldIdsForDel = new ArrayList<String>();
        if (StringUtils.isNotBlank(bean.getId())) {
            // 旧的附件id
            List<SysAttach> oldAttachs = this.attachService.findByOwnerIdAndOwnerType(bean.getId(),
                            OaNews.class.getSimpleName());
            if (oldAttachs != null) {
                for (SysAttach a : oldAttachs) {
                    oldIdsForDel.add(a.getId().toString());
                }
            }
        }
        if (StringUtils.isNotBlank(attachIds)) {
            String[] idArray = StringUtils.split(attachIds, ",");
            String ownerId = news.getId();
            for (String aIdStr : idArray) {
                // Long aId = Long.valueOf(aIdStr);
                this.attachService.linkAttachAndOwner(aIdStr, ownerId, OaNews.class.getSimpleName());
                if (oldIdsForDel.contains(aIdStr)) {
                    oldIdsForDel.remove(aIdStr);
                }
            }
        }
        // 删除剩余的
        if (oldIdsForDel.size() > 0) {
            for (String delAttachId : oldIdsForDel) {
                this.attachService.deleteAttach(delAttachId);
            }
        }

        return news.getId();
    }

    @Override
    public boolean delNews(String id) {

        if (StringUtils.isNotBlank(id)) {
            attachService.deleteOwnerAttach(id, OaNews.class.getSimpleName());
            repository.delete(id);
            // 保存数据变动
            dataChangeService.saveDataChange("News_id=" + id, DataChangeTable.通知公告, OperationType.删除);
            return true;
        }
        return false;
    }
    
    @Override
    public Page selectAllForDeptShow(String title, String departmentId, Date fromDate, Date toDate,String newsType,
                    String currentDeptId, Integer page, Integer rows) {
        StringBuilder sqlBuilder = new StringBuilder(" select ");
        sqlBuilder.append(" t.id, t.title, t.displayType, t.publishDepId, t.createDate, t.creatorId, t.publishDate, t.status,"
                        + " t.attachFlag, t.publishType, t.newsType, t.author from OaNews t ");
        sqlBuilder.append(" where ((t.publishType='0') ");
        sqlBuilder.append(" or (t.publishType=1 and t.publishDepId = ?1 ))");
        sqlBuilder.append(" and t.status='1'");
        // 以下是动态查询
        if (StringUtils.isNotBlank(title)) {
            sqlBuilder.append(" and t.title like ?2 ");
        }
        if (StringUtils.isNotBlank(departmentId)) {
            sqlBuilder.append(" and t.publishDepId = ?3 ");
        }
        if (fromDate != null) {
            sqlBuilder.append(" and t.publishDate >= ?4 ");
        }
        if (toDate != null) {
            sqlBuilder.append(" and t.publishDate <= ?5 ");
        }
        if (StringUtils.isNotBlank(newsType)) {
        	sqlBuilder.append(" and t.newsType = ?6 ");
        }
        sqlBuilder.append(" order by t.displayType desc,t.publishDate desc ");
        Query query = em.createQuery(sqlBuilder.toString());

        query.setParameter(1, new Long(currentDeptId));
        if (StringUtils.isNotBlank(title)) {
            query.setParameter(2, "%" + title + "%");
        }
        if (StringUtils.isNotBlank(departmentId)) {
            query.setParameter(3, new Long(departmentId));
        }
        if (fromDate != null) {
            query.setParameter(4, fromDate);
        }
        if (toDate != null) {
            query.setParameter(5, toDate);
        }
        if (StringUtils.isNotBlank(newsType)) {
        	if("0".equals(newsType)){
        		query.setParameter(6, NewsType.时政);
        	}else if("1".equals(newsType)){
        		query.setParameter(6, NewsType.社会);
        	}else if("2".equals(newsType)){
        		query.setParameter(6, NewsType.法制);
        	}
            
        }
        List totalList = query.getResultList();
        int total = 0;
        if (totalList != null) {
            total = totalList.size();
        }
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        List<NewsQueryShowBean> newsList = new ArrayList<NewsQueryShowBean>();
        List list = query.getResultList();
        String[] names = { "id", "title", "displayType", "publishDepId", "createDate", "creatorId", "publishDate",
                        "status", "attachFlag", "publishType", "newsType", "author" };
        for (Object o : list) {
            Map<String, Object> map = Tools.changeListToMap(names, (Object[]) o);
            NewsQueryShowBean b = new NewsQueryShowBean();
            try {
                PropertyUtils.copyProperties(b, map);
                Long deptId = b.getPublishDepId();
                SysDepartment dep = deptService.findOne(deptId);
                if (dep != null) {
                    b.setPublishDeptName(dep.getDeptName());
                }
                newsList.add(b);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        Page p = new Page();
        p.setRows(newsList);
        p.setTotal(Long.valueOf(total));
        return p;
    }
    
}
