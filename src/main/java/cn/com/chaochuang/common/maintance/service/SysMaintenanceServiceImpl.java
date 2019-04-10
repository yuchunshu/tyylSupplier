/*
 * FileName:    SysMaintenanceServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年11月17日 (huangm) 1.0 Create
 */

package cn.com.chaochuang.common.maintance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.maintance.bean.MaintenanceEditBean;
import cn.com.chaochuang.common.maintance.domain.SysMaintenance;
import cn.com.chaochuang.common.maintance.reference.QueStatus;
import cn.com.chaochuang.common.maintance.repository.SysMaintenanceRepository;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.util.UserHelper;

/**
 * @author huangm
 *
 */
@Service
@Transactional
public class SysMaintenanceServiceImpl extends SimpleUuidCrudRestService<SysMaintenance> implements
                SysMaintenanceService {

    @Autowired
    private SysMaintenanceRepository repository;

    @Autowired
    private SysAttachService         attachService;

    @Override
    public SimpleDomainRepository<SysMaintenance, String> getRepository() {
        return repository;
    }

    @Override
    public String saveQuestion(MaintenanceEditBean bean) {
        if (bean != null) {
            SysMaintenance maint = null;
            if (StringUtils.isNotBlank(bean.getId())) {
                maint = this.repository.findOne(bean.getId());
            }
            if (maint == null) {
                maint = new SysMaintenance();
            }

            maint.setQueDesc(bean.getQueDesc());
            if (maint.getQueTime() == null) {
                maint.setQueTime(new Date());
            }
            maint.setQueType(bean.getQueType());
            maint.setRaiser(bean.getRaiser());
            maint.setStatus(QueStatus.未处理);
            maint.setSubSys(bean.getSubSys());
            maint.setTelphone(bean.getTelphone());

            String attachIds = bean.getAttach();
            if (StringUtils.isNotBlank(attachIds)) {
                maint.setAttachFlag(AttachFlag.有附件);
            } else {
                maint.setAttachFlag(AttachFlag.无附件);
            }
            maint = this.repository.save(maint);

            // 连接附件
            List<String> oldIdsForDel = new ArrayList<String>();
            if (StringUtils.isNotBlank(maint.getId())) {
                // 旧的附件id
                List<SysAttach> oldAttachs = this.attachService.findByOwnerIdAndOwnerType(maint.getId(),
                                SysMaintenance._ATT_OWNER_TYPE);
                if (oldAttachs != null) {
                    for (SysAttach a : oldAttachs) {
                        oldIdsForDel.add(a.getId().toString());
                    }
                }
            }
            if (StringUtils.isNotBlank(attachIds)) {
                String[] idArray = StringUtils.split(attachIds, ",");
                String ownerId = maint.getId();
                for (String aIdStr : idArray) {
                    this.attachService.linkAttachAndOwner(aIdStr, ownerId, SysMaintenance._ATT_OWNER_TYPE);
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

            return maint.getId();
        }
        return null;
    }

    @Override
    public String saveAnswer(MaintenanceEditBean bean) {
        if (bean != null && StringUtils.isNotBlank(bean.getId())) {
            SysMaintenance maint = this.repository.findOne(bean.getId());
            if (maint != null) {
                maint.setStatus(QueStatus.已处理);
                maint.setDealer(UserHelper.getCurrentUser().getUserName());
                maint.setDealResult(bean.getDealResult());
                maint.setDealTime(new Date());

                maint = this.repository.save(maint);

                return maint.getId();
            }
        }
        return null;
    }

}
