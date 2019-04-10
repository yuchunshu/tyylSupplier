package cn.com.chaochuang.doc.log.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.service.OaDocFileService;
import cn.com.chaochuang.doc.log.bean.DocModifyLogEditBean;
import cn.com.chaochuang.doc.log.domain.DocModifyLog;
import cn.com.chaochuang.doc.log.repository.DocModifyLogRepository;
import cn.com.chaochuang.workflow.inst.domain.WfAuditOpinion;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.inst.repository.WfAuditOpinionRepository;
import cn.com.chaochuang.workflow.inst.service.WfAuditOpinionService;
import cn.com.chaochuang.workflow.inst.service.WfFlowInstService;

@Service
@Transactional
public class DocModifyLogServiceImpl extends SimpleUuidCrudRestService<DocModifyLog> implements DocModifyLogService {

    @Autowired
    private DocModifyLogRepository 	 repository;

    @Autowired
    private OaDocFileService         docFileService;
    
    @Autowired
    private WfFlowInstService        flowInstService;
    
    @Autowired
    private WfAuditOpinionService    auditOpinionService;
    
    @Autowired
    private WfAuditOpinionRepository auditOpinionRepository;
    
    @Override
    public SimpleDomainRepository<DocModifyLog, String> getRepository() {
        return repository;
    }

	@Override
	public String saveDocModifyLog(DocModifyLogEditBean bean) {
		
		SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
		// 基本信息
        OaDocFile obj = docFileService.findOne(bean.getId());
        Date curTime = new Date();
        
        List<DocModifyLog> logList = new ArrayList<DocModifyLog>();
        DocModifyLog log = new DocModifyLog();
        
		
        //当字段值被修改时才保存修改记录
        if("1".equals(bean.getType())){
        	if(!obj.getTitle().equals(bean.getTitle())){
            	log.setOldContent(obj.getTitle());
            	log.setNewContent(bean.getTitle());
            	log.setItemId("receive_title");
            	log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
            	logList.add(log);
            	obj.setTitle(bean.getTitle());
            }
            
            if(!obj.getDocCode().equals(bean.getDocCode())){
            	log = new DocModifyLog();
            	log.setOldContent(obj.getDocCode());
            	log.setNewContent(bean.getDocCode());
            	log.setItemId("receive_docCode");
            	log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
            	logList.add(log);
            	obj.setDocCode(bean.getDocCode());
            }
            
        	if(obj.getReceiveDate().getTime()!= bean.getReceiveDate().getTime()){
        		log = new DocModifyLog();
            	log.setOldContent(Tools.DATE_FORMAT.format(obj.getReceiveDate()));
            	log.setNewContent(Tools.DATE_FORMAT.format(bean.getReceiveDate()));
            	log.setItemId("receive_receiveDate");
            	log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
            	logList.add(log);
            	obj.setReceiveDate(bean.getReceiveDate());
            }
            
            if(!obj.getSendUnit().equals(bean.getSendUnit())){
            	log = new DocModifyLog();
            	log.setOldContent(obj.getSendUnit());
            	log.setNewContent(bean.getSendUnit());
            	log.setItemId("receive_sendUnit");
            	log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
            	logList.add(log);
            	obj.setSendUnit(bean.getSendUnit());
            }
            
            if(isNotEmptyString(obj.getSuggestion(),bean.getSuggestion())){
            	log = new DocModifyLog();
            	log.setOldContent(obj.getSuggestion());
            	log.setNewContent(bean.getSuggestion());
            	log.setItemId("receive_suggestion");
            	log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
            	logList.add(log);
            	obj.setSuggestion(bean.getSuggestion());
            }
            
            if(isNotEmptyString(obj.getRemark(),bean.getRemark())){
            	log = new DocModifyLog();
            	log.setOldContent(obj.getRemark());
            	log.setNewContent(bean.getRemark());
            	log.setItemId("receive_remark");
            	log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
            	logList.add(log);
            	obj.setRemark(bean.getRemark());
            }
            
            
            String apprOpinion = bean.getApprOpinion();
            String apprOpinionId = bean.getApprOpinionId();
            String apprOpinionNodeId = bean.getApprOpinionNodeId();
            
            String[] apprOpinionArr = {};
            String[] apprOpinionIdArr = {};
            String[] apprOpinionNodeIdArr = {};
            
            if(!Tools.isEmptyString(apprOpinionId)){
            	apprOpinionArr = apprOpinion.split("\\|");
                apprOpinionIdArr = apprOpinionId.split(",");
                apprOpinionNodeIdArr = apprOpinionNodeId.split(",");
            }
            
            for(int i=0;i<apprOpinionIdArr.length;i++){
            	WfAuditOpinion auditOpinion  = auditOpinionService.findOne(apprOpinionIdArr[i]);
            	
            	if(!auditOpinion.getApprOpinion().trim().equals(apprOpinionArr[i].trim())){
            		log = new DocModifyLog();
                	log.setOldContent(auditOpinion.getApprOpinion());
                	log.setNewContent(apprOpinionArr[i].trim());
                	log.setItemId("receive_apprOpinion_" + apprOpinionNodeIdArr[i]);
                	log.setModCreater(user.getUserName());
                	log.setModTime(curTime);
                	log.setFileId(bean.getId());
                	log.setModType("2");
                	logList.add(log);
                	auditOpinion.setApprOpinion(apprOpinionArr[i].trim());
                	
                	auditOpinionRepository.save(auditOpinion);
                }
            	
            }
        }
        else if("2".equals(bean.getType())){
        	if(!obj.getUrgencyLevel().equals(bean.getUrgencyLevel())){
        		log = new DocModifyLog();
            	log.setOldContent(obj.getUrgencyLevel().getValue());
            	log.setNewContent(bean.getUrgencyLevel().getValue());
            	log.setItemId("send_urgencyLevel");
            	log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
            	logList.add(log);
            	obj.setUrgencyLevel(bean.getUrgencyLevel());
            }
        	
        	if(!obj.getOpenFlag().equals(bean.getOpenFlag())){
        		log = new DocModifyLog();
        		log.setOldContent(obj.getOpenFlag().getValue());
        		log.setNewContent(bean.getOpenFlag().getValue());
        		log.setItemId("send_openFlag");
        		log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
        		logList.add(log);
        		obj.setOpenFlag(bean.getOpenFlag());
        	}
        	
        	if(!obj.getTitle().equals(bean.getTitle())){
        		log = new DocModifyLog();
        		log.setOldContent(obj.getTitle());
        		log.setNewContent(bean.getTitle());
        		log.setItemId("send_title");
        		log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
        		logList.add(log);
        		obj.setTitle(bean.getTitle());
        	}
            
            if(!bean.getDocCode().equals(obj.getDocCode())){
            	log = new DocModifyLog();
            	log.setOldContent(obj.getDocCode());
            	log.setNewContent(bean.getDocCode());
            	log.setItemId("send_docCode");
            	log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
            	logList.add(log);
            	obj.setDocCode(bean.getDocCode());
            }
            
            if(isNotEmptyString(obj.getMainSend(),bean.getMainSend())){
            	log = new DocModifyLog();
            	log.setOldContent(obj.getMainSend());
            	log.setNewContent(bean.getMainSend());
            	log.setItemId("send_mainSend");
            	log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
            	logList.add(log);
            	obj.setMainSend(bean.getMainSend());
            }
            
            if(isNotEmptyString(obj.getReportSend(),bean.getReportSend())){
            	log = new DocModifyLog();
            	log.setOldContent(obj.getReportSend());
            	log.setNewContent(bean.getReportSend());
            	log.setItemId("send_reportSend");
            	log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
            	logList.add(log);
            	obj.setReportSend(bean.getReportSend());
            }
            
            if(isNotEmptyString(obj.getCopySend(),bean.getCopySend())){
            	log = new DocModifyLog();
            	log.setOldContent(obj.getCopySend());
            	log.setNewContent(bean.getCopySend());
            	log.setItemId("send_copySend");
            	log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
            	logList.add(log);
            	obj.setCopySend(bean.getCopySend());
            }
            
            String apprOpinion = bean.getApprOpinion();
            String apprOpinionId = bean.getApprOpinionId();
            String apprOpinionNodeId = bean.getApprOpinionNodeId();
            
            String[] apprOpinionArr = {};
            String[] apprOpinionIdArr = {};
            String[] apprOpinionNodeIdArr = {};
            
            if(!Tools.isEmptyString(apprOpinionId)){
            	apprOpinionArr = apprOpinion.split("\\|");
                apprOpinionIdArr = apprOpinionId.split(",");
                apprOpinionNodeIdArr = apprOpinionNodeId.split(",");
            }
            
            for(int i=0;i<apprOpinionIdArr.length;i++){
            	WfAuditOpinion auditOpinion  = auditOpinionService.findOne(apprOpinionIdArr[i]);
            	
            	if(!auditOpinion.getApprOpinion().trim().equals(apprOpinionArr[i].trim())){
            		log = new DocModifyLog();
                	log.setOldContent(auditOpinion.getApprOpinion());
                	log.setNewContent(apprOpinionArr[i].trim());
                	log.setItemId("send_apprOpinion_" + apprOpinionNodeIdArr[i]);
                	log.setModCreater(user.getUserName());
                	log.setModTime(curTime);
                	log.setFileId(bean.getId());
                	log.setModType("2");
                	logList.add(log);
                	auditOpinion.setApprOpinion(apprOpinionArr[i].trim());
                	
                	auditOpinionRepository.save(auditOpinion);
                }
            	
            }
        }
        else if("3".equals(bean.getType())){
        	if(!obj.getTitle().equals(bean.getTitle())){
            	log.setOldContent(obj.getTitle());
            	log.setNewContent(bean.getTitle());
            	log.setItemId("interequest_title");
            	log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
            	logList.add(log);
            	obj.setTitle(bean.getTitle());
            }
            
            if(isNotEmptyString(obj.getSendUnit(),bean.getSendUnit())){
            	log = new DocModifyLog();
            	log.setOldContent(obj.getSendUnit());
            	log.setNewContent(bean.getSendUnit());
            	log.setItemId("interequest_sendUnit");
            	log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
            	logList.add(log);
            	obj.setSendUnit(bean.getSendUnit());
            }
            
            if(isNotEmptyString(obj.getSuggestion(),bean.getSuggestion())){
            	log = new DocModifyLog();
            	log.setOldContent(obj.getSuggestion());
            	log.setNewContent(bean.getSuggestion());
            	log.setItemId("interequest_suggestion");
            	log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
            	logList.add(log);
            	obj.setSuggestion(bean.getSuggestion());
            }
            
            if(isNotEmptyString(obj.getRemark(),bean.getRemark())){
            	log = new DocModifyLog();
            	log.setOldContent(obj.getRemark());
            	log.setNewContent(bean.getRemark());
            	log.setItemId("interequest_remark");
            	log.setModCreater(user.getUserName());
            	log.setModTime(curTime);
            	log.setFileId(bean.getId());
            	log.setModType("2");
            	logList.add(log);
            	obj.setRemark(bean.getRemark());
            }
            
            
            String apprOpinion = bean.getApprOpinion();
            String apprOpinionId = bean.getApprOpinionId();
            String apprOpinionNodeId = bean.getApprOpinionNodeId();
            
            String[] apprOpinionArr = {};
            String[] apprOpinionIdArr = {};
            String[] apprOpinionNodeIdArr = {};
            
            if(!Tools.isEmptyString(apprOpinionId)){
            	apprOpinionArr = apprOpinion.split("\\|");
                apprOpinionIdArr = apprOpinionId.split(",");
                apprOpinionNodeIdArr = apprOpinionNodeId.split(",");
            }
            
            for(int i=0;i<apprOpinionIdArr.length;i++){
            	WfAuditOpinion auditOpinion  = auditOpinionService.findOne(apprOpinionIdArr[i]);
            	
            	if(!auditOpinion.getApprOpinion().trim().equals(apprOpinionArr[i].trim())){
            		log = new DocModifyLog();
                	log.setOldContent(auditOpinion.getApprOpinion());
                	log.setNewContent(apprOpinionArr[i].trim());
                	log.setItemId("interequest_apprOpinion_" + apprOpinionNodeIdArr[i]);
                	log.setModCreater(user.getUserName());
                	log.setModTime(curTime);
                	log.setFileId(bean.getId());
                	log.setModType("2");
                	logList.add(log);
                	auditOpinion.setApprOpinion(apprOpinionArr[i].trim());
                	
                	auditOpinionRepository.save(auditOpinion);
                }
            	
            }
        }
        
        List<WfFlowInst> flowInstList = flowInstService.findByEntityId(obj.getId());
        for(WfFlowInst flowInst : flowInstList){
        	if(!flowInst.getTitle().equals(bean.getTitle())){
        		flowInst.setTitle(obj.getTitle());
            	flowInstService.getRepository().save(flowInst);
            }
        	
        }
        
        repository.save(logList);
		docFileService.save(obj);
		return obj.getId();
	}

    
	/**
     * 判断两个字符串是否为空（null和零长度的字符串都是空）并且要不等于equ指定的字符串
     *
     * @param str
     *            源字符串
     * @param equ
     *            要不相等的字符串
     * @return true：不等于equ字符串  ;false：空或等于equ
     */
    public static boolean isNotEmptyString(String str, String equ) {
    	if(null == str){
    		str = "";
    	}
    	if(null == equ){
    		str = "";
    	}
        if (Tools.isEmptyString(str) && Tools.isEmptyString(equ)) {
            return false;
        }
        return !str.equals(equ);
    }
}
