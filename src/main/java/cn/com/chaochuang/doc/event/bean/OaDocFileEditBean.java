/*
 * FileName:    ReceiveFileEditBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.event.bean;

import java.util.Date;

import javax.persistence.Convert;

import cn.com.chaochuang.common.reference.FeedbackFlag;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.doc.archive.domain.DocArchives;
import cn.com.chaochuang.doc.event.reference.DocCate;
import cn.com.chaochuang.doc.event.reference.DocSource;
import cn.com.chaochuang.doc.event.reference.EmergencyLevelType;
import cn.com.chaochuang.doc.event.reference.EmergencyLevelTypeConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;

/**
 * @author huangwq
 *
 */
public class OaDocFileEditBean extends DocCommonBean {

    /** 发文(来文)单位 */
    private String             sendUnit;
    /** 收文日期 */
    private Date               receiveDate;
    /** 收文类型 */
    private DocSource          sourceType;
    /** 相关公文添加附件字符串 */
    private String             linkAttachIds;
    /** 相关公文编号 */
    private Long               linkFileId;
    /** 所属案卷 */
    private DocArchives        archives;

    private String             nextDeputyDealer;

    private String             waitOther;
    private String             endCond;
    /** 文种 */
    private DocCate            docCate;

    /** 意见反馈函件编号 */
    private String             docDepLetterId;
    /** 意见反馈状态 */
    private FeedbackFlag       letterStatus;

    /** 处室共享 */
    private String             deptShare;
    /** 主送 */
    private String             mainSend;
    /** 主题词 */
    private String             reportSend;
    /** 抄送 */
    private String             copySend;
    /** 校对人 */
    private Long               reviewer;
    /** 模板ID */
    private Long               templateId;
    /** 联系电话 */
    private String             telephone;

    /** 主题词 */
    private String             keyword;

    /** 拟稿信息 */
    private String             createInfo;
    /** 一校 */
    private String             reviewer1;
    /** 二校 */
    private String             reviewer2;
    /** 打字员 */
    private String             typist;

    /** 是否修改公文信息 */
    private YesNo              docEditable;

    /** 判断是否来源于移动端提交*/
    private boolean            fromMobile;

    /** 版本号（用于多用户保存互斥判断） */
    private Integer            version_;

    /** 排序类型 */
    private String             sncodeType;
    /** 处理意见 */
    private String             suggestion;

    //---报政府时需要的信息
    /**纸质公文标识*/
    private String             feedBackFlag;

    /** 办文时限值 */
    private String 			   deadlineType;

    /**公文类型*/
    private RemoteDocSwapType  documentType;

    /** 紧急程度 */
    @Convert(converter = EmergencyLevelTypeConverter.class)
    private EmergencyLevelType emergencyLevel;
    /** 印发机关 */
    private String 			   printOrg;
    /** 来文单位简称 */
    private String 			   printOrgAbb;

    private String 			   secondaryUnitIdentifierName;

    private String 			   identifier;

    /** 封首内容id 限时办结转内部公文 */
    private Long 			   envelopeId;
    
    /** 是否督办 */
    private String 			   superviseFlag;
    
    /** 环节限办时间 */
    private Date 			   nodeExpiryDate;

    /** 环节限办时间对应ids */
    private String 			   nodeIds;
    
    /** 环节限办时间集合 */
    private String 			   nodeExpireMinutes;
    
    /** 父类ID（关联转办公文ID） */
    private String             parentId;
    
    /** 系统外发ID（关联三级贯通转内部公文ID） */
    private String             outerId;
    
    /** 部门ID集合 */
    private String             deptIds;
    
    /** 部门名称集合 */
    private String             deptNames;
    
    /** 自定义限办时间 */
    private Date 			   expiryDateOther;
    
    /**
     * @return the sendUnit
     */
    public String getSendUnit() {
        return sendUnit;
    }

    /**
     * @param sendUnit
     *            the sendUnit to set
     */
    public void setSendUnit(String sendUnit) {
        this.sendUnit = sendUnit;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * @param receiveDate
     *            the receiveDate to set
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public DocSource getSourceType() {
        return sourceType;
    }

    public void setSourceType(DocSource sourceType) {
        this.sourceType = sourceType;
    }

    public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

    public Long getReviewer() {
        return reviewer;
    }

    public void setReviewer(Long reviewer) {
        this.reviewer = reviewer;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCreateInfo() {
        return createInfo;
    }

    public void setCreateInfo(String createInfo) {
        this.createInfo = createInfo;
    }

    public String getReviewer1() {
        return reviewer1;
    }

    public void setReviewer1(String reviewer1) {
        this.reviewer1 = reviewer1;
    }

    public String getReviewer2() {
        return reviewer2;
    }

    public void setReviewer2(String reviewer2) {
        this.reviewer2 = reviewer2;
    }

    public String getTypist() {
        return typist;
    }

    public void setTypist(String typist) {
        this.typist = typist;
    }

    /**
     * @return the linkAttachIds
     */
    public String getLinkAttachIds() {
        return linkAttachIds;
    }

    /**
     * @param linkAttachIds
     *            the linkAttachIds to set
     */
    public void setLinkAttachIds(String linkAttachIds) {
        this.linkAttachIds = linkAttachIds;
    }

    /**
     * @return the linkFileId
     */
    public Long getLinkFileId() {
        return linkFileId;
    }

    /**
     * @param linkFileId
     *            the linkFileId to set
     */
    public void setLinkFileId(Long linkFileId) {
        this.linkFileId = linkFileId;
    }

    /**
     * @return the archives
     */
    public DocArchives getArchives() {
        return archives;
    }

    /**
     * @param archives
     *            the archives to set
     */
    public void setArchives(DocArchives archives) {
        this.archives = archives;
    }

    /**
     * @return the nextDeputyDealer
     */
    public String getNextDeputyDealer() {
        return nextDeputyDealer;
    }

    /**
     * @param nextDeputyDealer
     *            the nextDeputyDealer to set
     */
    public void setNextDeputyDealer(String nextDeputyDealer) {
        this.nextDeputyDealer = nextDeputyDealer;
    }

    /**
     * @return the waitOther
     */
    public String getWaitOther() {
        return waitOther;
    }

    /**
     * @param waitOther
     *            the waitOther to set
     */
    public void setWaitOther(String waitOther) {
        this.waitOther = waitOther;
    }


    public String getEndCond() {
		return endCond;
	}

	public void setEndCond(String endCond) {
		this.endCond = endCond;
	}

	public String getDocDepLetterId() {
        return docDepLetterId;
    }

    public void setDocDepLetterId(String docDepLetterId) {
        this.docDepLetterId = docDepLetterId;
    }

    // public String getProcessKey() {
    // if (super.getProcessKey() == null) {
    // return DEFAULT_PROCESS_KEY;
    // } else {
    // return super.getProcessKey();
    // }
    // }

    public FeedbackFlag getLetterStatus() {
        return letterStatus;
    }

    public void setLetterStatus(FeedbackFlag letterStatus) {
        this.letterStatus = letterStatus;
    }


    public DocCate getDocCate() {
        return docCate;
    }

    public void setDocCate(DocCate docCate) {
        this.docCate = docCate;
    }

    public YesNo getDocEditable() {
        return docEditable;
    }

    public void setDocEditable(YesNo docEditable) {
        this.docEditable = docEditable;
    }

    public Integer getVersion_() {
		return version_;
	}

	public void setVersion_(Integer version_) {
		this.version_ = version_;
	}

	/**
     * @return the sncodeType
     */
    public String getSncodeType() {
        return sncodeType;
    }

    /**
     * @param sncodeType
     *            the sncodeType to set
     */
    public void setSncodeType(String sncodeType) {
        this.sncodeType = sncodeType;
    }

    /**
     * @return the suggestion
     */
    public String getSuggestion() {
        return suggestion;
    }

    /**
     * @param suggestion
     *            the suggestion to set
     */
    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

	public String getFeedBackFlag() {
		return feedBackFlag;
	}

	public void setFeedBackFlag(String feedBackFlag) {
		this.feedBackFlag = feedBackFlag;
	}

	public RemoteDocSwapType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(RemoteDocSwapType documentType) {
		this.documentType = documentType;
	}


	public EmergencyLevelType getEmergencyLevel() {
		return emergencyLevel;
	}

	public void setEmergencyLevel(EmergencyLevelType emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}

	public String getPrintOrg() {
		return printOrg;
	}

	public void setPrintOrg(String printOrg) {
		this.printOrg = printOrg;
	}

	public String getPrintOrgAbb() {
		return printOrgAbb;
	}

	public void setPrintOrgAbb(String printOrgAbb) {
		this.printOrgAbb = printOrgAbb;
	}

	public String getDeptShare() {
		return deptShare;
	}

	public void setDeptShare(String deptShare) {
		this.deptShare = deptShare;
	}

	public String getDeadlineType() {
		return deadlineType;
	}

	public void setDeadlineType(String deadlineType) {
		this.deadlineType = deadlineType;
	}

	public String getSecondaryUnitIdentifierName() {
		return secondaryUnitIdentifierName;
	}

	public void setSecondaryUnitIdentifierName(String secondaryUnitIdentifierName) {
		this.secondaryUnitIdentifierName = secondaryUnitIdentifierName;
	}

	public Long getEnvelopeId() {
		return envelopeId;
	}

	public void setEnvelopeId(Long envelopeId) {
		this.envelopeId = envelopeId;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public boolean isFromMobile() {
		return fromMobile;
	}

	public void setFromMobile(boolean fromMobile) {
		this.fromMobile = fromMobile;
	}

	public String getSuperviseFlag() {
		return superviseFlag;
	}

	public void setSuperviseFlag(String superviseFlag) {
		this.superviseFlag = superviseFlag;
	}

	public Date getNodeExpiryDate() {
		return nodeExpiryDate;
	}

	public void setNodeExpiryDate(Date nodeExpiryDate) {
		this.nodeExpiryDate = nodeExpiryDate;
	}

	public String getNodeIds() {
		return nodeIds;
	}

	public void setNodeIds(String nodeIds) {
		this.nodeIds = nodeIds;
	}

	public String getNodeExpireMinutes() {
		return nodeExpireMinutes;
	}

	public void setNodeExpireMinutes(String nodeExpireMinutes) {
		this.nodeExpireMinutes = nodeExpireMinutes;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getDeptNames() {
		return deptNames;
	}

	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}

	public Date getExpiryDateOther() {
		return expiryDateOther;
	}

	public void setExpiryDateOther(Date expiryDateOther) {
		this.expiryDateOther = expiryDateOther;
	}

}
