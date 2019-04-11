/*
 * FileName:    OaNoticeServiceImpl.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.notice.service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import cn.com.chaochuang.oa.datachange.reference.DataChangeTable;
import cn.com.chaochuang.oa.datachange.reference.OperationType;
import cn.com.chaochuang.oa.datachange.service.DataChangeService;
import cn.com.chaochuang.oa.notice.bean.NoticeEditBean;
import cn.com.chaochuang.oa.notice.bean.NoticeQueryShowBean;
import cn.com.chaochuang.oa.notice.domain.OaNotice;
import cn.com.chaochuang.oa.notice.repository.OaNoticeRepository;

/**
 * @author HM
 *
 */
@Service
@Transactional
public class OaNoticeServiceImpl extends SimpleCrudRestService<OaNotice, String> implements OaNoticeService {

    @Autowired
    private OaNoticeRepository      repository;

    @Autowired
    private SysAttachService        attachService;

    @Autowired
    private SysDepartmentService    deptService;

    @Autowired
    private DataChangeService       dataChangeService;

    @Autowired
    private SysDepartmentRepository depRepository;

    @PersistenceContext
    private EntityManager           em;

    @Override
    public SimpleDomainRepository<OaNotice, String> getRepository() {
        return this.repository;
    }

    @Override
    public String saveNotice(NoticeEditBean bean) {
        OaNotice notice = null;
        if (bean != null && bean.getId() != null) {
            notice = this.repository.findOne(bean.getId());
            // 保存数据变动
            dataChangeService.saveDataChange("notice_id=" + bean.getId(), DataChangeTable.通知公告, OperationType.修改);
        } else {
            notice = new OaNotice();
        }
        notice = BeanCopyUtil.copyBean(bean, OaNotice.class);
        StatusFlag noticeStatus = bean.getStatus();

        Date date = new Date();
        String dateString = Tools.DATE_TIME_FORMAT.format(date);
        try {
            date = Tools.DATE_TIME_FORMAT.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (StatusFlag.暂存.equals(noticeStatus)) {
            if (notice.getCreateDate() == null) {
                notice.setCreateDate(date);
            }
            notice.setPublishDate(null);
        }
        if (StatusFlag.已发布.equals(noticeStatus)) {
            if (notice.getCreateDate() == null) {
                notice.setCreateDate(date);
            }
            notice.setPublishDate(date);
        }

        if (PublishType.单位.equals(bean.getPublishType())) {
            List<SysDepartment> depList = depRepository.findByRootDepartment(ValidAble.VALID);
            if (Tools.isNotEmptyList(depList)) {
                notice.setPublishDepId(depList.get(0).getId());
            }
        }

        String attachIds = bean.getAttach();
        if (StringUtils.isNotBlank(attachIds)) {
            notice.setAttachFlag(AttachFlag.有附件);
        } else {
            notice.setAttachFlag(AttachFlag.无附件);
        }
        // 保证取到自动生成的ID
        notice = this.repository.save(notice);

        // 连接附件
        List<String> oldIdsForDel = new ArrayList<String>();
        if (StringUtils.isNotBlank(bean.getId())) {
            // 旧的附件id
            List<SysAttach> oldAttachs = this.attachService.findByOwnerIdAndOwnerType(bean.getId(),
                            OaNotice.class.getSimpleName());
            if (oldAttachs != null) {
                for (SysAttach a : oldAttachs) {
                    oldIdsForDel.add(a.getId().toString());
                }
            }
        }
        if (StringUtils.isNotBlank(attachIds)) {
            String[] idArray = StringUtils.split(attachIds, ",");
            String ownerId = notice.getId();
            for (String aIdStr : idArray) {
                // Long aId = Long.valueOf(aIdStr);
                this.attachService.linkAttachAndOwner(aIdStr, ownerId, OaNotice.class.getSimpleName());
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

        return notice.getId();
    }

    @Override
    public boolean delNotice(String id) {

        if (StringUtils.isNotBlank(id)) {
            attachService.deleteOwnerAttach(id, OaNotice.class.getSimpleName());
            repository.delete(id);
            // 保存数据变动
            dataChangeService.saveDataChange("notice_id=" + id, DataChangeTable.通知公告, OperationType.删除);
            return true;
        }
        return false;
    }

    @Override
    public Page selectAllForDeptShow(String title, String departmentId, Date fromDate, Date toDate,
                    String currentDeptId, Integer page, Integer rows) {
        StringBuilder sqlBuilder = new StringBuilder(" select ");
        sqlBuilder.append(" t.id, t.title, t.displayType, t.publishDepId, t.createDate, t.creatorId, t.publishDate, t.status,"
                        + " t.attachFlag, t.publishType from OaNotice t ");
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

        List totalList = query.getResultList();
        int total = 0;
        if (totalList != null) {
            total = totalList.size();
        }
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        List<NoticeQueryShowBean> noticeList = new ArrayList<NoticeQueryShowBean>();
        List list = query.getResultList();
        String[] names = { "id", "title", "displayType", "publishDepId", "createDate", "creatorId", "publishDate",
                        "status", "attachFlag", "publishType" };
        for (Object o : list) {
            Map<String, Object> map = Tools.changeListToMap(names, (Object[]) o);
            NoticeQueryShowBean b = new NoticeQueryShowBean();
            try {
                PropertyUtils.copyProperties(b, map);
                Long deptId = b.getPublishDepId();
                SysDepartment dep = deptService.findOne(deptId);
                if (dep != null) {
                    b.setPublishDeptName(dep.getDeptName());
                }
                noticeList.add(b);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        Page p = new Page();
        p.setRows(noticeList);
        p.setTotal(Long.valueOf(total));
        return p;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.oa.notice.service.OaNoticeService#getOaNotice(java.lang.Long)
     */
    @Override
    public List<OaNotice> getOaNotice(String id) {
        return this.repository.findByStatusAndIdGreaterThanOrderByIdDesc(StatusFlag.已发布, id);
    }

    /**
     * 通过公告创建时间获取最新的通知公告消息
     *
     * @param lastOutputTime
     * @return
     */
    @Override
    public List<OaNotice> selectRecentNotice(String lastOutputTime) {
        Date lastSendTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<OaNotice> list = null;
        try {
            if (Tools.isEmptyString(lastOutputTime)) {
                // 时间为空，根据id来查
                list = this.repository.findByStatus(StatusFlag.已发布);
            } else {
                lastSendTime = sdf.parse(lastOutputTime);
                list = this.repository.selectOaNoticeBySaveDate(lastSendTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }


}
