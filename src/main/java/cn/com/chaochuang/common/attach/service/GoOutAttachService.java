package cn.com.chaochuang.common.attach.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.repository.SysAttachRepository;
import cn.com.chaochuang.common.attach.service.sta.AttachStaticClass;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.upload.support.UploadFileInfoPersistence;
import cn.com.chaochuang.common.upload.support.UploadFileItem;

/** 
 * 因公外出申请
 * @author: yuchunshu
 * @date: 2018年10月29日 下午3:55:38  
 */
@Service
@Transactional
public class GoOutAttachService extends SimpleUuidCrudRestService<SysAttach> implements UploadFileInfoPersistence {

	@Value(value = "${upload.appid.goOut}")
	private String 					appid;

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
