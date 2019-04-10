package cn.com.chaochuang.common.attach.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.attach.bean.AttachBean;
import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.repository.SysAttachRepository;
import cn.com.chaochuang.common.attach.service.sta.AttachStaticClass;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.reference.IsImage;
import cn.com.chaochuang.common.upload.support.UploadFileInfoPersistence;
import cn.com.chaochuang.common.upload.support.UploadFileItem;
import cn.com.chaochuang.common.util.AttachHelper;
import cn.com.chaochuang.common.util.AttachUtils;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.event.domain.OaDocFile;

/**
 * @author rongln
 * @date 2017年12月22日 上午11:26:40
 * 
 */
@Service
@Transactional
public class BriefingAttachService extends SimpleUuidCrudRestService<SysAttach> implements UploadFileInfoPersistence {
	
	@Value(value = "${upload.appid.briefing}")
    private String              	appid;

	@Autowired
	private SysAttachRepository 	repository;

	@Override
	public String getAppId() {
		return appid;
	}

	@Override
	public String saveUploadFileInfo(UploadFileItem fileItem) {
		SysAttach attach =AttachStaticClass.saveUploadFileInfo(fileItem);
        this.persist(attach);
        return attach.getId().toString();
	}

	@Override
	public UploadFileItem getUploadFileInfo(String id) {
		return AttachStaticClass.getUploadFileInfo(id, getRepository());
	}

	@Override
	public SimpleDomainRepository<SysAttach, String> getRepository() {
		return this.repository;
	}
}
