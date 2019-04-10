/*
 * FileName:    DocSwapEnvelopeEditBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.bean;

import java.util.Date;

import javax.persistence.Convert;

import org.springframework.web.multipart.MultipartFile;

import cn.com.chaochuang.doc.event.reference.DenseType;
import cn.com.chaochuang.doc.event.reference.EmergencyLevelType;
import cn.com.chaochuang.doc.event.reference.EmergencyLevelTypeConverter;
import cn.com.chaochuang.doc.event.reference.OpenFlag;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapTypeConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvStatus;

/**
 * @author yuandl
 *
 */
public class DocSwapEnvelopeEditBean {

    /** 编辑类型：暂存 */
    public static final String EDIT_TYPE_TMPSAVE = "0";
    /** 编辑类型： 提交 */
    public static final String EDIT_TYPE_APPLY   = "1";
    /** 编辑类型：退回 */
    public static final String EDIT_TYPE_BACK    = "2";

    /** 流水号 */
    private Long               id;
    private String 			   envelopeIds;
    /** 组织机构代码 */
    private String             secondaryUnitIdentifier;
    /** 接收单位名称 */
    private String             secondaryUnitIdentifierName;
    /** 资源标识 */
    private String             identifier;
    /** 状态 */
    private RemoteEnvStatus    envStatus;
    /** 相关封首编号 */
    private Long               linkEnvelopeId;
    /** 备注 */
    private String             remark;
    /** 标题 */
    private String             title;
    /** 编辑标志:0:暂存；1：提交 */
    private String             editType;
    /** 收文时限值 */
    private Integer            deadlineDay;
    /** 收文单位组织机构 */
    private String             feedbackOrg;
    /** 主送 */
    private String             mainSend;
    /** 抄送 */
    private String             copySend;
    /** 印发机关 */
    private String             printOrg;
    /** 印发日期 */
    private Date               printDate;
    //成文日期 归档日期
    private Date 			   docFileTime;
    /** 限办日期 */
    private Date               limitTime;
    /** 联系描述 */
    private String             linkScript;
    /** 密级 */
    private DenseType          secretLevel;
    /** 紧急程度 */
    @Convert(converter = EmergencyLevelTypeConverter.class)
    private EmergencyLevelType emergencyLevel;
    /** 公开方式 */
    private OpenFlag           publishType;
    /** 文号 */
    private String             docNumber;
    /** 签收人 */
    private String             signReceiveMan;
    /** 资源标识 */
    private String             resourceIden;
    /** 以公文传阅方式传送的部门及人员 **/
    private String             readDept2;
    /** 以公文传阅方式传送的部门及人员 **/
    private String             readMan2;
    /** 传阅公文的文件类别 */
    private String             docProperty;
    /** 公文交换附件编号 */
    private Long[]             swapFileAttachId;
    /** 附件正文 */
    private MultipartFile      mainFile;
    /** 公文交换正文编号 */
    private Long               remoteDocAttachId;

    /** 公文类型（办件：0 阅件：1） */
    @Convert(converter = RemoteDocSwapTypeConverter.class)
    private RemoteDocSwapType documentType;
    
    private String 			  deadlineType;
    
    //办结信息
    /**联系人*/
    private String 			  contactName;
    /**联系电话*/
    private String 			  contactPhone;
    
    /** 办结备注说明 */
    private String 			  finishMemo;
    
    /** 正文附件 */
    private String 			  gdFileId;
    private String 		 	  gwFileId;
    private String 			  tifFileId;
    
    private String 			  feedBackType;
    
    private String 			  backReason;
    
    //处理结果信息
    private String 			  dealResultType;
    private String 			  undoReason;
    private String 			  convertReadDoc;
    
    //阅件
    private String 			  docReadBody;
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the secondaryUnitIdentifier
     */
    public String getSecondaryUnitIdentifier() {
        return secondaryUnitIdentifier;
    }

    /**
     * @param secondaryUnitIdentifier
     *            the secondaryUnitIdentifier to set
     */
    public void setSecondaryUnitIdentifier(String secondaryUnitIdentifier) {
        this.secondaryUnitIdentifier = secondaryUnitIdentifier;
    }

    /**
     * @return the secondaryUnitIdentifierName
     */
    public String getSecondaryUnitIdentifierName() {
        return secondaryUnitIdentifierName;
    }

    /**
     * @param secondaryUnitIdentifierName
     *            the secondaryUnitIdentifierName to set
     */
    public void setSecondaryUnitIdentifierName(String secondaryUnitIdentifierName) {
        this.secondaryUnitIdentifierName = secondaryUnitIdentifierName;
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier
     *            the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the envStatus
     */
    public RemoteEnvStatus getEnvStatus() {
        return envStatus;
    }

    /**
     * @param envStatus
     *            the envStatus to set
     */
    public void setEnvStatus(RemoteEnvStatus envStatus) {
        this.envStatus = envStatus;
    }

    /**
     * @return the linkEnvelopeId
     */
    public Long getLinkEnvelopeId() {
        return linkEnvelopeId;
    }

    /**
     * @param linkEnvelopeId
     *            the linkEnvelopeId to set
     */
    public void setLinkEnvelopeId(Long linkEnvelopeId) {
        this.linkEnvelopeId = linkEnvelopeId;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     *            the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the editType
     */
    public String getEditType() {
        return editType;
    }

    /**
     * @param editType
     *            the editType to set
     */
    public void setEditType(String editType) {
        this.editType = editType;
    }

    /**
     * @return the deadlineDay
     */
    public Integer getDeadlineDay() {
        return deadlineDay;
    }

    /**
     * @param deadlineDay
     *            the deadlineDay to set
     */
    public void setDeadlineDay(Integer deadlineDay) {
        this.deadlineDay = deadlineDay;
    }

    /**
     * @return the feedbackOrg
     */
    public String getFeedbackOrg() {
        return feedbackOrg;
    }

    /**
     * @param feedbackOrg
     *            the feedbackOrg to set
     */
    public void setFeedbackOrg(String feedbackOrg) {
        this.feedbackOrg = feedbackOrg;
    }

    /**
     * @return the mainSend
     */
    public String getMainSend() {
        return mainSend;
    }

    /**
     * @param mainSend
     *            the mainSend to set
     */
    public void setMainSend(String mainSend) {
        this.mainSend = mainSend;
    }

    /**
     * @return the copySend
     */
    public String getCopySend() {
        return copySend;
    }

    /**
     * @param copySend
     *            the copySend to set
     */
    public void setCopySend(String copySend) {
        this.copySend = copySend;
    }

    /**
     * @return the printOrg
     */
    public String getPrintOrg() {
        return printOrg;
    }

    /**
     * @param printOrg
     *            the printOrg to set
     */
    public void setPrintOrg(String printOrg) {
        this.printOrg = printOrg;
    }

    /**
     * @return the printDate
     */
    public Date getPrintDate() {
        return printDate;
    }

    /**
     * @param printDate
     *            the printDate to set
     */
    public void setPrintDate(Date printDate) {
        this.printDate = printDate;
    }

    /**
     * @return the limitTime
     */
    public Date getLimitTime() {
        return limitTime;
    }

    /**
     * @param limitTime
     *            the limitTime to set
     */
    public void setLimitTime(Date limitTime) {
        this.limitTime = limitTime;
    }

    /**
     * @return the linkScript
     */
    public String getLinkScript() {
        return linkScript;
    }

    /**
     * @param linkScript
     *            the linkScript to set
     */
    public void setLinkScript(String linkScript) {
        this.linkScript = linkScript;
    }

    /**
     * @return the secretLevel
     */
    public DenseType getSecretLevel() {
        return secretLevel;
    }

    /**
     * @param secretLevel
     *            the secretLevel to set
     */
    public void setSecretLevel(DenseType secretLevel) {
        this.secretLevel = secretLevel;
    }

    /**
     * @return the emergencyLevel
     */
    public EmergencyLevelType getEmergencyLevel() {
        return emergencyLevel;
    }

    /**
     * @param emergencyLevel
     *            the emergencyLevel to set
     */
    public void setEmergencyLevel(EmergencyLevelType emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }

    /**
     * @return the publishType
     */
    public OpenFlag getPublishType() {
        return publishType;
    }

    /**
     * @param publishType
     *            the publishType to set
     */
    public void setPublishType(OpenFlag publishType) {
        this.publishType = publishType;
    }

    /**
     * @return the docNumber
     */
    public String getDocNumber() {
        return docNumber;
    }

    /**
     * @param docNumber
     *            the docNumber to set
     */
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    /**
     * @return the signReceiveMan
     */
    public String getSignReceiveMan() {
        return signReceiveMan;
    }

    /**
     * @param signReceiveMan
     *            the signReceiveMan to set
     */
    public void setSignReceiveMan(String signReceiveMan) {
        this.signReceiveMan = signReceiveMan;
    }

    /**
     * @return the resourceIden
     */
    public String getResourceIden() {
        return resourceIden;
    }

    /**
     * @param resourceIden
     *            the resourceIden to set
     */
    public void setResourceIden(String resourceIden) {
        this.resourceIden = resourceIden;
    }

    /**
     * @return the readDept2
     */
    public String getReadDept2() {
        return readDept2;
    }

    /**
     * @param readDept2
     *            the readDept2 to set
     */
    public void setReadDept2(String readDept2) {
        this.readDept2 = readDept2;
    }

    /**
     * @return the readMan2
     */
    public String getReadMan2() {
        return readMan2;
    }

    /**
     * @param readMan2
     *            the readMan2 to set
     */
    public void setReadMan2(String readMan2) {
        this.readMan2 = readMan2;
    }

    /**
     * @return the docProperty
     */
    public String getDocProperty() {
        return docProperty;
    }

    /**
     * @param docProperty
     *            the docProperty to set
     */
    public void setDocProperty(String docProperty) {
        this.docProperty = docProperty;
    }

    /**
     * @return the swapFileAttachId
     */
    public Long[] getSwapFileAttachId() {
        return swapFileAttachId;
    }

    /**
     * @param swapFileAttachId
     *            the swapFileAttachId to set
     */
    public void setSwapFileAttachId(Long[] swapFileAttachId) {
        this.swapFileAttachId = swapFileAttachId;
    }

    /**
     * @return the mainFile
     */
    public MultipartFile getMainFile() {
        return mainFile;
    }

    /**
     * @param mainFile
     *            the mainFile to set
     */
    public void setMainFile(MultipartFile mainFile) {
        this.mainFile = mainFile;
    }

    /**
     * @return the remoteDocAttachId
     */
    public Long getRemoteDocAttachId() {
        return remoteDocAttachId;
    }

    /**
     * @param remoteDocAttachId
     *            the remoteDocAttachId to set
     */
    public void setRemoteDocAttachId(Long remoteDocAttachId) {
        this.remoteDocAttachId = remoteDocAttachId;
    }

	public RemoteDocSwapType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(RemoteDocSwapType documentType) {
		this.documentType = documentType;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getDeadlineType() {
		return deadlineType;
	}

	public void setDeadlineType(String deadlineType) {
		this.deadlineType = deadlineType;
	}

	public String getGdFileId() {
		return gdFileId;
	}

	public void setGdFileId(String gdFileId) {
		this.gdFileId = gdFileId;
	}

	public String getGwFileId() {
		return gwFileId;
	}

	public void setGwFileId(String gwFileId) {
		this.gwFileId = gwFileId;
	}

	public String getTifFileId() {
		return tifFileId;
	}

	public void setTifFileId(String tifFileId) {
		this.tifFileId = tifFileId;
	}

	public String getFeedBackType() {
		return feedBackType;
	}

	public void setFeedBackType(String feedBackType) {
		this.feedBackType = feedBackType;
	}

	public String getDealResultType() {
		return dealResultType;
	}

	public void setDealResultType(String dealResultType) {
		this.dealResultType = dealResultType;
	}

	public String getUndoReason() {
		return undoReason;
	}

	public void setUndoReason(String undoReason) {
		this.undoReason = undoReason;
	}

	public String getConvertReadDoc() {
		return convertReadDoc;
	}

	public void setConvertReadDoc(String convertReadDoc) {
		this.convertReadDoc = convertReadDoc;
	}

	public String getFinishMemo() {
		return finishMemo;
	}

	public void setFinishMemo(String finishMemo) {
		this.finishMemo = finishMemo;
	}

	public Date getDocFileTime() {
		return docFileTime;
	}

	public void setDocFileTime(Date docFileTime) {
		this.docFileTime = docFileTime;
	}

	public String getDocReadBody() {
		return docReadBody;
	}

	public void setDocReadBody(String docReadBody) {
		this.docReadBody = docReadBody;
	}

	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}

	public String getEnvelopeIds() {
		return envelopeIds;
	}

	public void setEnvelopeIds(String envelopeIds) {
		this.envelopeIds = envelopeIds;
	}

}
