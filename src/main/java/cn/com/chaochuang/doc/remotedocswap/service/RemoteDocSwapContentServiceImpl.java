/*
 * FileName:    RemoteDocSwapContentServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.repository.SysAttachRepository;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.reference.IsImage;
import cn.com.chaochuang.common.util.AttachUtils;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapAttach;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapContent;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteAttachType;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteDocSwapAttachRepository;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteDocSwapContentRepository;

/**
 * @author yuandl
 *
 */
@Service
@Transactional
public class RemoteDocSwapContentServiceImpl extends SimpleLongIdCrudRestService<RemoteDocSwapContent> implements
                RemoteDocSwapContentService {

    @Autowired
    private RemoteDocSwapContentRepository repository;
    
    @Autowired
    private RemoteDocSwapAttachRepository  attachRepository;
    
    @Autowired
    private SysAttachRepository  		   sysAttachRepository;

    @Override
    public SimpleDomainRepository<RemoteDocSwapContent, Long> getRepository() {
        return this.repository;
    }

    @Override
    public RemoteDocSwapContent getByEnvelopeId(Long envelopeId) {
        List<RemoteDocSwapContent> list = this.repository.findByEnvelopeId(envelopeId);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<RemoteDocSwapContent> findByEnvelopeUuid(String envelopeUuid) {
        return this.repository.findByEnvelopeUuid(envelopeUuid);
    }

	@Override
	public List<SysAttach> copyDocSwapAttachToSysAttach(Long envelopeId) {
		RemoteDocSwapContent envelopeContent = this.getByEnvelopeId(envelopeId);
        
		//查询正文附件pdf
        List<RemoteDocSwapAttach> cnvAttachs = this.attachRepository.findByRemoteDocSwapContentIdAndRemoteAttachType(
        		envelopeContent.getId(), RemoteAttachType.转换过的正文附件);
        //查询附件
        List<RemoteDocSwapAttach> otherAttachs = this.attachRepository.findByRemoteDocSwapContentIdAndRemoteAttachType(
        		envelopeContent.getId(), RemoteAttachType.其他);
        
        List<RemoteDocSwapAttach> attachs = new ArrayList<RemoteDocSwapAttach>();
        if(Tools.isNotEmptyList(cnvAttachs)){
        	for(RemoteDocSwapAttach attach:cnvAttachs){
        		attachs.add(attach);
        	}
        }
        
        if(Tools.isNotEmptyList(otherAttachs)){
        	for(RemoteDocSwapAttach attach:otherAttachs){
        		attachs.add(attach);
        	}
        }
        
        //复制附件记录到系统附件表
        List<SysAttach> sysAttachList = new ArrayList<SysAttach>();
        if(Tools.isNotEmptyList(attachs)){
        	for(RemoteDocSwapAttach attach:attachs){
        		SysAttach sysAttach = new SysAttach();
        		BeanUtils.copyProperties(attach, sysAttach, new String[] { "id" });
        		sysAttach.setFileSize(0L);
        		sysAttach.setIsImage(IsImage.非图文公告);
        		sysAttach.setAttachType(AttachUtils.getFileSuffix(attach.getTrueName()));
        		sysAttachList.add(sysAttach);
        		sysAttachRepository.save(sysAttach);
        	}
        }

		return sysAttachList;
	}

}
