/*
 * FileName:    OaBriefingServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.briefing.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.domain.ValidAble;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.reference.PublishType;
import cn.com.chaochuang.common.reference.StatusFlag;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.repository.SysDepartmentRepository;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.oa.briefing.bean.BriefingEditBean;
import cn.com.chaochuang.oa.briefing.bean.BriefingQueryShowBean;
import cn.com.chaochuang.oa.briefing.domain.OaBriefing;
import cn.com.chaochuang.oa.briefing.reference.BriefingType;
import cn.com.chaochuang.oa.briefing.repository.OaBriefingRepository;

/**
 * @author HM
 *
 */
@Service
@Transactional
public class OaBriefingServiceImpl extends SimpleUuidCrudRestService<OaBriefing> implements OaBriefingService {

    @Autowired
    private OaBriefingRepository  	repository;

    @Autowired
    private SysAttachService 		attachService;
    
    @PersistenceContext
    private EntityManager           em;

    @Autowired
    private SysDepartmentService    deptService;
    
    @Autowired
    private SysDepartmentRepository depRepository;
    
    @Override
    public SimpleDomainRepository<OaBriefing, String> getRepository() {
        return this.repository;
    }

    @Override
    public String saveBriefing(BriefingEditBean bean) {
        OaBriefing briefing = null;
        if (bean != null && bean.getId() != null) {
            briefing = this.repository.findOne(bean.getId());
        } else {
            briefing = new OaBriefing();
        }
        briefing = BeanCopyUtil.copyBean(bean, OaBriefing.class);
        StatusFlag briefingStatus = bean.getStatus();
        if (StatusFlag.暂存.equals(briefingStatus)) {
            if (briefing.getCreateDate() == null) {
                briefing.setCreateDate(new Date());
            }
        }
        
        if (StatusFlag.已发布.equals(briefingStatus)) {
            if (briefing.getCreateDate() == null) {
                briefing.setCreateDate(new Date());
            }
            briefing.setPublishDate(new Date());
        }
        
        if (PublishType.单位.equals(bean.getPublishType())) {
            List<SysDepartment> depList = depRepository.findByRootDepartment(ValidAble.VALID);
            if (Tools.isNotEmptyList(depList)) {
            	briefing.setPublishDepId(depList.get(0).getId());
            }
        }
        
        String attachIds = bean.getAttach();
        if (StringUtils.isNotBlank(attachIds)) {
            briefing.setAttachFlag(AttachFlag.有附件);
        } else {
            briefing.setAttachFlag(AttachFlag.无附件);
        }

        briefing = this.repository.save(briefing);

        // 连接附件
        List<String> oldIdsForDel = new ArrayList<String>();
        if (bean.getId() != null) {
            // 旧的附件id
            List<SysAttach> oldAttachs = this.attachService.findByOwnerIdAndOwnerType(bean.getId(),
                            OaBriefing.class.getSimpleName());
            if (oldAttachs != null) {
                for (SysAttach a : oldAttachs) {
                    oldIdsForDel.add(a.getId().toString());
                }
            }
        }
        if (attachIds != null) {
            String[] idArray = StringUtils.split(attachIds, ",");
            String ownerId = briefing.getId();
            for (String aId : idArray) {
                this.attachService.linkAttachAndOwner(aId, ownerId, OaBriefing.class.getSimpleName());
                if (oldIdsForDel.contains(aId)) {
                    oldIdsForDel.remove(aId);
                }
            }
        }
        // 删除剩余的
        if (oldIdsForDel.size() > 0) {
            for (String delAttachId : oldIdsForDel) {
                this.attachService.deleteAttach(delAttachId);
            }
        }

        return briefing.getId();
    }

    @Override
    public boolean delBriefing(String id) {
        if (id != null) {
            OaBriefing briefing = this.repository.findOne(id);
            if (briefing != null) {
                briefing.setStatus(StatusFlag.已删除);
                this.repository.save(briefing);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Page selectAllForDeptShow(String title, String department, Date fromDate, Date toDate,String briefType,
                    String currentDeptId, Integer page, Integer rows) {
        StringBuilder sqlBuilder = new StringBuilder(" select ");
        sqlBuilder.append(" t.id, t.title, t.displayType, t.publishDepId, t.createDate, t.creatorId, t.publishDate, t.status,"
                        + " t.attachFlag, t.publishType, t.briefType from OaBriefing t ");
        sqlBuilder.append(" where ((t.publishType='0') ");
        sqlBuilder.append(" or (t.publishType=1 and t.publishDepId = ?1 ))");
        sqlBuilder.append(" and t.status='1'");
        // 以下是动态查询
        if (StringUtils.isNotBlank(title)) {
            sqlBuilder.append(" and t.title like ?2 ");
        }
        if (StringUtils.isNotBlank(department)) {
            sqlBuilder.append(" and t.publishDept.deptName like ?3 ");
        }
        if (fromDate != null) {
            sqlBuilder.append(" and t.publishDate >= ?4 ");
        }
        if (toDate != null) {
            sqlBuilder.append(" and t.publishDate <= ?5 ");
        }
        if (StringUtils.isNotBlank(briefType)) {
        	sqlBuilder.append(" and t.briefType = ?6 ");
        }
        
        sqlBuilder.append(" order by t.displayType desc,t.publishDate desc ");
        Query query = em.createQuery(sqlBuilder.toString());

        query.setParameter(1, new Long(currentDeptId));
        if (StringUtils.isNotBlank(title)) {
            query.setParameter(2, "%" + title + "%");
        }
        if (StringUtils.isNotBlank(department)) {
        	query.setParameter(3, "%" + department + "%");
        }
        if (fromDate != null) {
            query.setParameter(4, fromDate);
        }
        if (toDate != null) {
            query.setParameter(5, toDate);
        }
        if (StringUtils.isNotBlank(briefType)) {
        	if("1".equals(briefType)){
        		query.setParameter(6, BriefingType.国家法律法规);
        	}else if("2".equals(briefType)){
        		query.setParameter(6, BriefingType.地方法规);
        	}else if("3".equals(briefType)){
        		query.setParameter(6, BriefingType.中外条约);
        	}else if("4".equals(briefType)){
        		query.setParameter(6, BriefingType.政策参考);
        	}else if("5".equals(briefType)){
        		query.setParameter(6, BriefingType.立法追踪);
        	}else if("6".equals(briefType)){
        		query.setParameter(6, BriefingType.司法解释);
        	}
            
        }
        List totalList = query.getResultList();
        int total = 0;
        if (totalList != null) {
            total = totalList.size();
        }
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        List<BriefingQueryShowBean> briefingList = new ArrayList<BriefingQueryShowBean>();
        List list = query.getResultList();
        String[] names = { "id", "title", "displayType", "publishDepId", "createDate", "creatorId", "publishDate",
                        "status", "attachFlag", "publishType", "briefType" };
        for (Object o : list) {
            Map<String, Object> map = Tools.changeListToMap(names, (Object[]) o);
            BriefingQueryShowBean b = new BriefingQueryShowBean();
            try {
                PropertyUtils.copyProperties(b, map);
                Long deptId = b.getPublishDepId();
                SysDepartment dep = deptService.findOne(deptId);
                if (dep != null) {
                    b.setPublishDeptName(dep.getDeptName());
                }
                briefingList.add(b);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        Page p = new Page();
        p.setRows(briefingList);
        p.setTotal(Long.valueOf(total));
        return p;
    }
}
