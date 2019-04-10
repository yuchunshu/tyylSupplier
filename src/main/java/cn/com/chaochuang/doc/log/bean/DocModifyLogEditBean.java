/*
 * FileName:    NoticeShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.log.bean;

import java.util.Date;

import cn.com.chaochuang.doc.event.reference.OpenFlag;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelType;

/** 
 * @ClassName: DocModifyLogEditBean 
 * @Description: 公文编辑记录编辑
 * @author: yuchunshu
 * @date: 2017年9月5日 下午10:34:40  
 */
public class DocModifyLogEditBean {

    private String      	 id;

    /** 标题 */
    private String 			 title;
    
    /** 来文日期 */
    private Date 			 receiveDate;
    
    /** 来文单位 */
    private String 			 sendUnit;
    
    /** 来文字号 */
    private String 			 docCode;
    
    /** 拟办意见 */
    private String 			 suggestion;
    
    /** 备注 */
    private String 			 remark;
    
    /** 环节审批意见id */
    private String 			 apprOpinionId;
    
    /** 环节审批意见 */
    private String 			 apprOpinion;
    
    /** 环节审批意见环节id */
    private String 			 apprOpinionNodeId;
    
    /** 紧急程度 */
    private UrgencyLevelType urgencyLevel;
    
    /** 公开方式 */
    private OpenFlag         openFlag;
    
    /** 主送 */
    private String           mainSend;
    
    /** 抄报 */
    private String           reportSend;
    
    /** 抄送 */
    private String           copySend;
    
    /** 1:收文，2：发文 ，3：内部请示*/
    private String 			 type;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getSendUnit() {
		return sendUnit;
	}
	public void setSendUnit(String sendUnit) {
		this.sendUnit = sendUnit;
	}
	public String getDocCode() {
		return docCode;
	}
	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getApprOpinionId() {
		return apprOpinionId;
	}
	public void setApprOpinionId(String apprOpinionId) {
		this.apprOpinionId = apprOpinionId;
	}
	public String getApprOpinion() {
		return apprOpinion;
	}
	public void setApprOpinion(String apprOpinion) {
		this.apprOpinion = apprOpinion;
	}
	public String getApprOpinionNodeId() {
		return apprOpinionNodeId;
	}
	public void setApprOpinionNodeId(String apprOpinionNodeId) {
		this.apprOpinionNodeId = apprOpinionNodeId;
	}
	public UrgencyLevelType getUrgencyLevel() {
		return urgencyLevel;
	}
	public void setUrgencyLevel(UrgencyLevelType urgencyLevel) {
		this.urgencyLevel = urgencyLevel;
	}
	public OpenFlag getOpenFlag() {
		return openFlag;
	}
	public void setOpenFlag(OpenFlag openFlag) {
		this.openFlag = openFlag;
	}
	public String getMainSend() {
		return mainSend;
	}
	public void setMainSend(String mainSend) {
		this.mainSend = mainSend;
	}
	public String getReportSend() {
		return reportSend;
	}
	public void setReportSend(String reportSend) {
		this.reportSend = reportSend;
	}
	public String getCopySend() {
		return copySend;
	}
	public void setCopySend(String copySend) {
		this.copySend = copySend;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
