/*
 * FileName:    OaReceiveFileService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.docread.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.reference.SignStatus;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.docread.bean.DocReadEditBean;
import cn.com.chaochuang.doc.docread.bean.ReceivesReadEditBean;
import cn.com.chaochuang.doc.docread.domain.DocRead;
import cn.com.chaochuang.doc.docread.domain.DocReadReceives;
import cn.com.chaochuang.doc.docread.reference.DocReadStatus;
import cn.com.chaochuang.doc.docread.repository.DocReadReceivesRepository;
import cn.com.chaochuang.doc.docread.repository.DocReadRepository;
import cn.com.chaochuang.doc.docsigned.domain.DocSigned;
import cn.com.chaochuang.doc.docsigned.service.DocSignedService;
import cn.com.chaochuang.doc.event.reference.FileStatusFlag;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelType;
import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;

/**
 * @author HeYunTao
 *
 */
@Service
@Transactional
public class DocReadServiceImpl extends SimpleLongIdCrudRestService<DocRead> implements DocReadService {

    @Autowired
    private DocReadRepository         repository;

    @Autowired
    private DocReadReceivesRepository receivesRepository;

    @Autowired
    private SysUserService            userService;

    @Autowired
    private SysAttachService          attachService;

    @Autowired
    private DocSignedService signedService;


    @Override
    public SimpleDomainRepository<DocRead, Long> getRepository() {
        return this.repository;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.doc.docread.service.DocReadService#saveDocread(cn.com.chaochuang.doc.docread.bean.DocReadEditBean)
     */
    @Override
    public Long saveDocread(DocReadEditBean bean) {

        DocRead docread = null;
        if(bean.getId() != null){
        	docread = this.findOne(bean.getId());
        }
        if(null == docread){
        	docread = new DocRead();
        }
        BeanUtils.copyProperties(bean, docread);

        SysUser currUser = (SysUser) UserTool.getInstance().getCurrentUser();
        docread.setSendTime(new Date());
        docread.setSendDeptId(Long.valueOf(currUser.getDepartment().getId()));
        docread.setSendMan(Long.valueOf(currUser.getId()));
        docread.setSendManName(currUser.getUserName());
        if(null == docread.getCreateTime()){
        	docread.setCreateTime(new Date());
        }

        this.persist(docread);

        String attachIds = bean.getAttach();
        //旧附件
        List<SysAttach> oldAttachList = this.attachService.findByOwnerIdAndOwnerType(String.valueOf(docread.getId()),DocRead.class.getSimpleName());
        if (StringUtils.isNotBlank(attachIds)) {
            docread.setAttachFlag(AttachFlag.有附件);
            for(String id:attachIds.split(",")){
            	SysAttach attach = this.attachService.findOne(id);
            	if(null != attach){
            		attach.setOwnerId(String.valueOf(docread.getId()));
            		attach.setOwnerType(DocRead.class.getSimpleName());
            		this.attachService.persist(attach);
            		if(oldAttachList.contains(attach)){//如果当前附加存在于旧附件中，将其从旧附件中移除，方便后面删除
            			oldAttachList.remove(attach);
            		}
            	}
            }
        } else {
            docread.setAttachFlag(AttachFlag.无附件);
        }
        //删除旧附件
        this.attachService.deleteAttach(oldAttachList);

        this.persist(docread);

        if (docread.getStatus() != null && DocReadStatus.发送.equals(docread.getStatus())) {
            String executePerIds = bean.getReadMan();
            if (!Tools.isEmptyString(executePerIds)) {
                String[] ids = executePerIds.split(",");
                for (String i : ids) {
                    Long id = Long.parseLong(i);
                    SysUser user = this.userService.findOne(id);
                    DocReadReceives d = new DocReadReceives();
                    d.setReceiverId(id);
                    d.setReceiverName(user.getUserName());
                    d.setReceiverDeptId(Long.valueOf(user.getDepartment().getId()));
                    d.setReceiverDeptName(user.getDepartment().getDeptName());
                    d.setDocReadId(docread.getId());
                    d.setStatus(SignStatus.未签收);
                    this.receivesRepository.save(d);
                }
            }
            if(YesNo.是.equals(docread.getToSigned())){//设置添加到来文签收
            	DocSigned sign = new DocSigned();
            	sign.setTitle(docread.getDocTitle());
            	sign.setSendDep(docread.getSendUnit());
            	sign.setReportCode(docread.getDocNumber());
            	sign.setMainSend(currUser.getDepartment().getDeptName());
            	sign.setReceiveDate(docread.getCreateTime());
            	sign.setSignDate(docread.getCreateTime());
            	sign.setSignerId(currUser.getId());
            	sign.setSignerName(currUser.getUserName());
            	sign.setSignerDeptId(currUser.getDepartment().getId());
            	sign.setSignerDeptName(currUser.getDepartment().getDeptName());
            	sign.setUrgencyLevel(UrgencyLevelType.无);
            	sign.setStatus(FileStatusFlag.在办);
            	sign.setContent(docread.getRemark());
            	sign.setAttachFlag(docread.getAttachFlag());
            	this.signedService.persist(sign);

            	List<SysAttach> docAttachs = this.attachService.findByOwnerIdAndOwnerType(String.valueOf(docread.getId()),DocRead.class.getSimpleName());
            	if(Tools.isNotEmptyList(docAttachs)){
	            	for(SysAttach a : docAttachs){
	            		SysAttach newAttach = this.attachService.copyAttach(a);
	            		if(null != newAttach){
	            			newAttach.setOwnerType(DocSigned.class.getSimpleName());
	            			newAttach.setOwnerId(String.valueOf(sign.getId()));
	            			this.attachService.persist(newAttach);

	            		}
	            	}
            	}
            }
        }

        return docread.getId();
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.doc.docread.service.DocReadService#saveDocreadReceives(cn.com.chaochuang.doc.docread.bean.ReceivesReadEditBean)
     */
    @Override
    public Long saveDocreadReceives(ReceivesReadEditBean bean) {
        DocReadReceives docReadReceives = new DocReadReceives();
        if (bean.getId() != null) {
            docReadReceives = this.receivesRepository.findOne(bean.getId());
            docReadReceives.setOpinion(bean.getOpinion());
            docReadReceives.setOpinionTime(new Date());
            docReadReceives.setStatus(bean.getStatus());
            this.receivesRepository.save(docReadReceives);
        }
        return docReadReceives.getId();
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.doc.docread.service.DocReadService#readDocRead(java.lang.Long)
     */
    @Override
    public void readDocRead(Long id) {
        if (id != null) {
            DocReadReceives docReadReceives = this.receivesRepository.findOne(id);
            if (SignStatus.未签收.equals(docReadReceives.getStatus())) {
                docReadReceives.setStatus(SignStatus.已签收);
                docReadReceives.setReadTime(new Date());
                this.receivesRepository.save(docReadReceives);
            }
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.doc.docread.service.DocReadService#getReadManNames(java.lang.Long)
     */
    @Override
    public String getReadManNames(Long docreadId) {
        DocRead docread = null;
        String readManNames = "";
        if (docreadId != null) {
            docread = this.repository.findOne(docreadId);
        }
        if (docread != null) {
            if (StringUtils.isNotBlank(docread.getReadMan())) {
                String[] receives = docread.getReadMan().split(",");
                for (String rec : receives) {
                    readManNames += (this.userService.findOne(Long.valueOf(rec)).getUserName() + ",");
                }
            }
        }
        if (StringUtils.isNotBlank(readManNames)) {
            readManNames = readManNames.substring(0, readManNames.length() - 1);
        }
        return readManNames;
    }
}
