/*
 * FileName:    EmAttachServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月26日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.upload.support.UploadFileInfoPersistence;
import cn.com.chaochuang.common.upload.support.UploadFileItem;
import cn.com.chaochuang.common.util.AttachUtils;
import cn.com.chaochuang.oa.mail.domain.EmAttach;
import cn.com.chaochuang.oa.mail.repository.EmAttachRepository;

/**
 * @author HM
 *
 */
@Service
@Transactional
public class EmAttachServiceImpl extends SimpleUuidCrudRestService<EmAttach> implements EmAttachService,
                UploadFileInfoPersistence {

    @Value(value = "${upload.appid.emattach}")
    private String             appid;

    @Value(value = "${upload.rootpath}")
    private String             rootPath;

    @Autowired
    private EmAttachRepository repository;

    @Override
    public SimpleDomainRepository<EmAttach, String> getRepository() {
        return this.repository;
    }

    @Override
    public Map<String, List<EmAttach>> getAttachMap(String emailId) {
        List<EmAttach> attachList = null;
        if (emailId != null) {
            attachList = this.repository.findByEmailId(emailId);
        }
        if (attachList != null && attachList.size() > 0) {
            Map<String, List<EmAttach>> map = new HashMap<String, List<EmAttach>>();
            map.put(emailId.toString(), attachList);
            return map;
        }
        return null;
    }

    @Override
    public boolean deleteAttach(String attachId) {
        if (attachId != null) {
            EmAttach attach = this.repository.findOne(attachId);
            AttachUtils.removeFile(this.rootPath + File.separator + attach.getSavePath() + attach.getSaveName());
            this.repository.delete(attach);
            return true;
        }
        return false;
    }

    @Override
    public String getAppId() {
        return appid;
    }

    @Override
    public UploadFileItem getUploadFileInfo(String id) {
        if (null != id) {
            EmAttach attach = getRepository().findOne(id);
            if (null != attach) {
                UploadFileItem fileItem = new UploadFileItem();

                fileItem.setTrueName(attach.getTrueName());
                fileItem.setSaveName(attach.getSaveName());
                fileItem.setSavePath(attach.getSavePath());
                fileItem.setFileSize(attach.getFileSize());

                return fileItem;
            }
        }
        return null;
    }

    @Override
    public String saveUploadFileInfo(UploadFileItem fileItem) {
        EmAttach attach = new EmAttach();

        attach.setTrueName(fileItem.getTrueName());
        attach.setSaveName(fileItem.getSaveName());
        attach.setSavePath(fileItem.getSavePath());
        attach.setFileSize(fileItem.getFileSize());
        this.persist(attach);
        return attach.getId().toString();
    }

    @Override
    public void linkAttachAndEmail(String attachId, String emailId) {
        if (attachId != null) {
            EmAttach attach = this.findOne(attachId);
            if (attach != null) {
                attach.setEmailId(emailId);
                this.repository.save(attach);
            }
        }

    }

    @Override
    public List<EmAttach> findByEmailId(String emailId) {
        List<EmAttach> attachs = this.repository.findByEmailId(emailId);
        return attachs;
    }

    @Override
    public Map<String, List<EmAttach>> copyAttachMap(String emailId) {
        List<EmAttach> attachList = null;
        if (emailId != null) {
            attachList = this.repository.findByEmailId(emailId);
        }
        List<EmAttach> copyAttachList = new ArrayList<EmAttach>();
        if (attachList != null && attachList.size() > 0) {
            for (EmAttach att : attachList) {
                EmAttach attach = new EmAttach();
                attach.setTrueName(att.getTrueName());
                attach.setSaveName(att.getSaveName());
                attach.setSavePath(att.getSavePath());
                attach.setFileSize(att.getFileSize());
                this.persist(attach);
                copyAttachList.add(attach);
            }
            if (copyAttachList.size() > 0) {
                Map<String, List<EmAttach>> map = new HashMap<String, List<EmAttach>>();
                map.put(emailId.toString(), copyAttachList);
                return map;
            }
        }
        return null;
    }
}
