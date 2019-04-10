/*
 * FileName:    ReceiveProcessServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年4月27日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.template.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.upload.support.UploadFileInfoPersistence;
import cn.com.chaochuang.common.upload.support.UploadFileItem;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.doc.template.bean.DocTemplateEditBean;
import cn.com.chaochuang.doc.template.domain.DocTemplate;
import cn.com.chaochuang.doc.template.repository.DocTemplateRepository;
import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;

/**
 * @author huangwq
 *
 */
@Service
@Transactional
public class DocTemplateServiceImpl extends SimpleLongIdCrudRestService<DocTemplate> implements DocTemplateService,
                UploadFileInfoPersistence {
    @Value(value = "${upload.appid.doc.template.attach}")
    private String                appid;

    @Value(value = "${upload.rootpath}")
    private String                rootPath;

    @Autowired
    private DocTemplateRepository repository;

    @Autowired
    private SysAttachService      attachService;
    /** 附件所属对象名 */
    private final String          _OWNER_TYPE = "DocTemplate";

    @Override
    public SimpleDomainRepository<DocTemplate, Long> getRepository() {
        return this.repository;
    }

    @Override
    public Long saveDocTemplate(DocTemplateEditBean bean) {

        DocTemplate temp = null;
//        if (bean.getId() == null && bean.getAttach() != null) {
//            if (bean.getAttach() != null && bean.getAttach() != "") {
//                String[] idArray = StringUtils.split(bean.getAttach(), ",");
//                if (idArray != null)
//                    bean.setId(idArray[0]);
//            }
//        }

//        if (bean != null && bean.getId() != null) {
//            temp = this.repository.findOne(bean.getId());
//            bean.setTemplateFilePath(temp.getTemplateFilePath());
//            // temp.setTemplateFilePath("");
//        } else {
//            temp = new DocTemplate();
//        }

       
        temp = BeanCopyUtil.copyBean(bean, DocTemplate.class);
        SysAttach attach = attachService.findOne(bean.getAttach());
        temp.setTemplateFilePath(rootPath + "/" + attach.getSavePath() + attach.getSaveName());
        if (temp.getDeptId() == null) {
            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            // temp.setDeptId(user.getDepartment().getId());
            // 改成用祖先部门id保存
            temp.setDeptId(user.getDepartment().getUnitDept().getId());
        }
        
        this.repository.save(temp);
        //先删除之前的附件，然后再次保存上传记录
        attachService.deleteOwnerAttach(temp.getId().toString(), _OWNER_TYPE);

        SysAttach attach_new = new SysAttach();
        attach_new = BeanCopyUtil.copyBean(attach, SysAttach.class);
        attach_new.setOwnerId(temp.getId().toString());
        attach_new.setOwnerType(_OWNER_TYPE);
        attachService.getRepository().save(attach_new);
        return temp.getId();
    }

    @Override
    public boolean delDocTemplate(Long id) {
        DocTemplate docTemplate = null;
        if (id != null) {
            docTemplate = this.repository.findOne(id);
            if (docTemplate != null) {
                this.repository.delete(docTemplate);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<DocTemplate> findByDeptId(Long depId) {
        return this.repository.findByDeptId(depId);
    }

    @Override
    public String getAppId() {
        return appid;
    }

    @Override
    public String saveUploadFileInfo(UploadFileItem fileItem) {
        DocTemplate temp = new DocTemplate();
        temp.setTemplateFilePath(rootPath + "/" + fileItem.getSavePath() + fileItem.getSaveName());
        this.persist(temp);
        return temp.getId().toString();
    }

    @Override
    public boolean deleteAttach(Long attachId) {
        if (attachId != null) {
            DocTemplate doc = this.repository.findOne(attachId);
            doc.setTemplateFilePath(null);
            this.repository.save(doc);
            return true;
        }
        return false;
    }

    @Override
    public UploadFileItem getUploadFileInfo(String id) {
        /*
         * if (null != id) { DocTemplate attach = getRepository().findOne(Long.parseLong(id)); if (null != attach) {
         * UploadFileItem fileItem = new UploadFileItem();
         * 
         * fileItem.setTrueName(attach.getTrueName()); fileItem.setSaveName(attach.getSaveName());
         * fileItem.setSavePath(attach.getSavePath()); fileItem.setFileSize(attach.getFileSize());
         * 
         * return fileItem; } }
         */
        return null;
    }

}
