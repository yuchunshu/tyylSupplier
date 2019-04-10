/*
 * FileName:    OaNoticeServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.docsigned.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.doc.docsigned.domain.DocSigned;
import cn.com.chaochuang.doc.docsigned.repository.DocSignedRepository;
import cn.com.chaochuang.doc.event.domain.OaDocFile;

/**
 * @author huangwq
 *
 */
@Service
@Transactional
public class DocSignedServiceImpl extends SimpleLongIdCrudRestService<DocSigned> implements DocSignedService {

    @Autowired
    private DocSignedRepository repository;

    @PersistenceContext
    private EntityManager       em;

    @Override
    public SimpleDomainRepository<DocSigned, Long> getRepository() {
        return this.repository;
    }

    @Override
    public DocSigned saveDocSigned(OaDocFile obj) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        if (obj != null) {
            DocSigned ds = new DocSigned();
            if (obj.getArchives() != null)
                ds.setArchId(obj.getArchives().getId());
            ds.setAttachFlag(obj.getAttachFlag());
            ds.setContent(null);
            ds.setMainSend(user.getDepartment().getDeptName());
            ds.setReceiveDate(obj.getReceiveDate());
            ds.setReportCode(obj.getDocCode());
            ds.setSendDep(obj.getSendUnit());
            ds.setSignDate(new Date());
            ds.setSignerDeptId(user.getDepartment().getId());
            ds.setSignerDeptName(user.getDepartment().getDeptName());
            ds.setSignerUnitId(user.getDepartment().getUnitId());
            ds.setSignerId(user.getId());
            ds.setSignerName(user.getUserName());
            ds.setSignFileType(null);
            ds.setStatus(obj.getStatus());
            ds.setTitle(obj.getTitle());
            ds.setUrgencyLevel(obj.getUrgencyLevel());
            DocSigned d = this.repository.save(ds);
            return d;
        }
        return null;

    }

}
