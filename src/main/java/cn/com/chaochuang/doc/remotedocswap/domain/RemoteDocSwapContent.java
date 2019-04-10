/*
 * FileName:    RemoteDocSwapContent.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.event.reference.DenseType;
import cn.com.chaochuang.doc.event.reference.DenseTypeConverter;
import cn.com.chaochuang.doc.event.reference.EmergencyLevelType;
import cn.com.chaochuang.doc.event.reference.EmergencyLevelTypeConverter;
import cn.com.chaochuang.doc.event.reference.OpenFlag;
import cn.com.chaochuang.doc.event.reference.OpenFlagConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteContentStatus;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteContentStatusConverter;

/**
 * @author yuandl
 *
 */
@Entity
@Table(name = "remote_doc_swap_content")
public class RemoteDocSwapContent extends LongIdEntity {

	/** 反馈标识:A表示请求方要求反馈办理情况 */
    public static final String 		 FB_TRANS = "A";
    /** 反馈标识:B表示请求方要求反馈业务数据 */
    public static final String 		 FB_DATAS = "B";
    /** 反馈标识:C表示请求方要求反馈征求意见数据 */
    public static final String 		 FB_OPINION = "C";
    /** 反馈标识:D表示请求方要求反馈调研业务数据 */
    public static final String 		 FB_SEARCH = "D";
    /** 反馈标识:Z表示无反馈要求 */
    public static final String 		 FB_NONE = "Z";
    
	/**业务类型：发送电子公文*/
    public static final String 		 BUS_TYPE_DZGW = "dzgw";
    /**业务类型：收文回执*/
    public static final String 		 BUS_TYPE_SWHZ = "swhz";
    /**业务类型：撤销公文*/
    public static final String 		 BUS_TYPE_CXGW = "cxgw";
    /**业务类型：作废公文*/
    public static final String 		 BUS_TYPE_ZFGW = "zfgw";
    /**业务类型：修改时限*/
    public static final String 		 BUS_TYPE_XGSX = "xgsx";
    /**业务类型：反馈处理信息*/
    public static final String 		 BUS_TYPE_CLQK = "clqk";
    /**业务类型：反馈正式公文*/
    public static final String 		 BUS_TYPE_FKGW = "fkgw";
    /**业务类型：反馈送阅信息*/
    public static final String 		 BUS_TYPE_SYXX = "syxx";
    /**业务类型：反馈归档信息*/
    public static final String 		 BUS_TYPE_GDXX = "gdxx";
    /**业务类型：请求重发*/
    public static final String 		 BUS_TYPE_QQCF = "qqcf";
    /**业务类型：退文*/
    public static final String 		 BUS_TYPE_TW = "tw";
    /**业务类型：纸质公文*/
    public static final String 		 BUS_TYPE_ZZGW = "zzgw";
    /**业务类型：反馈文回执*/
    public static final String 		 BUS_TYPE_FKHZ = "fkhz";
    //处理结果信息 1=已办结；2=不办理；3=转阅件
    public static final String 		 DEAL_RES_TYPE_FINISH = "1";
    public static final String 		 DEAL_RES_TYPE_UNDEAL = "2";
    public static final String 		 DEAL_RES_TYPE_TO_READ = "3";
    /**  */
    private static final long     	 serialVersionUID = 3449347776817418071L;
    /** 资源标识 */
    private String                	 resourceIden;

    /** 封首UUID */
    private String                	 envelopeUuid;

    /** 密级 */
    @Convert(converter = DenseTypeConverter.class)
    private DenseType             	 secretLevel;

    /** 紧急程度 */
    @Convert(converter = EmergencyLevelTypeConverter.class)
    private EmergencyLevelType       emergencyLevel;

    /** 发送方组织机构代码 */
    private String                	 sendOrg;

    /** 回馈方组织机构代码 */
    private String                	 feedbackOrg;

    /** 封首流水号 */
    private Long                  	 envelopeId;

    /** 状态 */
    @Convert(converter = RemoteContentStatusConverter.class)
    private RemoteContentStatus   	 conStatus;

    /** 反馈标识 */
    private String 				  	 feedbackFlag;

    /** 份号 */
    private String                	 numIden;

    /** 保密期限 */
    private String                	 secretLimit;

    /** 发文机关 */
    private String                	 sendOrgName;

    /** 签发人 */
    private String                	 signSendMan;

    /** 标题 */
    private String                	 title;

    /** 主送 */
    private String                	 mainSend;

    /** 抄送 */
    private String                	 copySend;

    /** 联系描述 */
    private String                	 linkScript;

    /** 公开方式 */
    @Convert(converter = OpenFlagConverter.class)
    private OpenFlag              	 publishType;

    /** 文号 */
    private String                	 docNumber;

    /** 印发机关 */
    private String                	 printOrg;

    /** 印发日期 */
    private Date                  	 printDate;
    @Transient
    private String                   printDateFmt;

    /** 签收人 */
    private String                	 signReceiveMan;

    /** 签收日期 */
    private Date                  	 signReceiveDate;

    /** 签收部门 */
    private String                	 signReceiveDept;

    /** 承办时间 */
    private Date                  	 transDate;

    /** 承办部门 */
    private String                	 transDept;

    /** 承办人 */
    private String                	 transMan;

    /** 办理意见 */
    private String                	 transOpinion;

    /** 审批人 */
    private String                	 approveMan;

    /** 审批意见 */
    private String                	 approveOpinion;

    /** 处理结果 */
    private String                	 transResult;

    /** 征求意见 */
    private String                	 askOpinion;

    /** 退文科室 */
    private String                	 returnDept;

    /** 退文人 */
    private String                	 returnMan;

    /** 退文日期 */
    private Date                  	 returnDate;

    /** 退文理由 */
    private String                	 returnReason;

    /** 撤销部门 */
    private String                	 revokeDept;

    /** 撤销人 */
    private String                	 revokeMan;

    /** 撤销日期 */
    private Date                  	 revokeDate;

    /** 撤销理由 */
    private String                	 revokeReason;
    
    /** 封首内容 */
    @ManyToOne
    @JoinColumn(name = "envelopeId", insertable = false, updatable = false)
    private RemoteDocSwapEnvelope    envelope;

    /** 正文 */
    @Transient
    private RemoteDocSwapAttach 	 gdFileDoc;
    
    @Transient
    private RemoteDocSwapAttach 	 gwFileDoc;
    
    @Transient
    private RemoteDocSwapAttach 	 tifFileDoc;
    
    /** 附件集合 */
    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "remoteDocSwapContentId")
    private Set<RemoteDocSwapAttach> remoteDocSwapAttachs;
    
    // /** 附件集合 */
    // @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // @JsonIgnore
    // private Set<RemoteDocSwapAttach> remoteDocSwapAttachs;

    /** 数字签名(解析使用) */
    @Transient
    private String                	 digitalSignature;

    /** 数字印章(解析使用) */
    @Transient
    private String                	 digitalSeal;

    /** 二维码(解析使用) */
    @Transient
    private String                	 twoDimensionCode;

    /** 发送时间(解析使用) */
    @Transient
    private Date                  	 sendTime;
    
    //办结信息
    /**联系人*/
    private String 					 contactName;
    /**联系电话*/
    private String 					 contactPhone;
    /** 办结时间 */
    private Date 					 finishTime;
    @Transient
    private String                   finishTimeFmt;
    /** 办结备注说明 */
    private String 					 finishMemo;
    /**文件标识*/
    private String 					 letterId;
    /**业务类型*/
    private String 					 bussinessType;

    //处理结果信息
    private String 					 dealResultType;
    private String 					 dealResult;
    
    //阅件信息
    private String 					 docReadBody;
    private Date 					 docReadTime;
    @Transient
    private String                   docReadTimeFmt;
    private Date 					 docFileTime;
    @Transient
    private String                   docFileTimeFmt;

    //退文
    private Date 					 backTime;
    @Transient
    private String                   backTimeFmt;
    
    private String 					 backReason;
    
    /** 印发机关简称 */
    private String 					 printOrgAbb;
    
    /**作废时间*/
    @Transient
    private Date 					 invalidTime;
    /**撤销时间*/
    @Transient
    private Date 					 undoTime;
    
    /**是否超时*/
    @Transient
    private String 					 overTimeFlag;
    //
    // /** 正文附件编号 */
    // private Long remoteDocAttachId;

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
     * @return the envelopeUuid
     */
    public String getEnvelopeUuid() {
        return envelopeUuid;
    }

    /**
     * @param envelopeUuid
     *            the envelopeUuid to set
     */
    public void setEnvelopeUuid(String envelopeUuid) {
        this.envelopeUuid = envelopeUuid;
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
     * @return the sendOrg
     */
    public String getSendOrg() {
        return sendOrg;
    }

    /**
     * @param sendOrg
     *            the sendOrg to set
     */
    public void setSendOrg(String sendOrg) {
        this.sendOrg = sendOrg;
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

    

    public String getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}

	public RemoteDocSwapEnvelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(RemoteDocSwapEnvelope envelope) {
		this.envelope = envelope;
	}

	/**
     * @return the conStatus
     */
    public RemoteContentStatus getConStatus() {
        return conStatus;
    }

    /**
     * @param conStatus
     *            the conStatus to set
     */
    public void setConStatus(RemoteContentStatus conStatus) {
        this.conStatus = conStatus;
    }

    /**
     * @return the feedbackFlag
     */
    public String getFeedbackFlag() {
        return feedbackFlag;
    }

    /**
     * @param feedbackFlag
     *            the feedbackFlag to set
     */
    public void setFeedbackFlag(String feedbackFlag) {
        this.feedbackFlag = feedbackFlag;
    }

    /**
     * @return the numIden
     */
    public String getNumIden() {
        return numIden;
    }

    /**
     * @param numIden
     *            the numIden to set
     */
    public void setNumIden(String numIden) {
        this.numIden = numIden;
    }

    /**
     * @return the secretLimit
     */
    public String getSecretLimit() {
        return secretLimit;
    }

    /**
     * @param secretLimit
     *            the secretLimit to set
     */
    public void setSecretLimit(String secretLimit) {
        this.secretLimit = secretLimit;
    }

    /**
     * @return the sendOrgName
     */
    public String getSendOrgName() {
        return sendOrgName;
    }

    /**
     * @param sendOrgName
     *            the sendOrgName to set
     */
    public void setSendOrgName(String sendOrgName) {
        this.sendOrgName = sendOrgName;
    }

    /**
     * @return the signSendMan
     */
    public String getSignSendMan() {
        return signSendMan;
    }

    /**
     * @param signSendMan
     *            the signSendMan to set
     */
    public void setSignSendMan(String signSendMan) {
        this.signSendMan = signSendMan;
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
        if (this.printDate != null) {
            this.printDateFmt = Tools.DATE_TIME_FORMAT.format(this.printDate);
        }
    }

    public String getPrintDateFmt() {
		return printDateFmt;
	}

	public void setPrintDateFmt(String printDateFmt) {
		this.printDateFmt = printDateFmt;
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
     * @return the signReceiveDate
     */
    public Date getSignReceiveDate() {
        return signReceiveDate;
    }

    /**
     * @param signReceiveDate
     *            the signReceiveDate to set
     */
    public void setSignReceiveDate(Date signReceiveDate) {
        this.signReceiveDate = signReceiveDate;
    }

    /**
     * @return the signReceiveDept
     */
    public String getSignReceiveDept() {
        return signReceiveDept;
    }

    /**
     * @param signReceiveDept
     *            the signReceiveDept to set
     */
    public void setSignReceiveDept(String signReceiveDept) {
        this.signReceiveDept = signReceiveDept;
    }

    /**
     * @return the transDate
     */
    public Date getTransDate() {
        return transDate;
    }

    /**
     * @param transDate
     *            the transDate to set
     */
    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    /**
     * @return the transDept
     */
    public String getTransDept() {
        return transDept;
    }

    /**
     * @param transDept
     *            the transDept to set
     */
    public void setTransDept(String transDept) {
        this.transDept = transDept;
    }

    /**
     * @return the transMan
     */
    public String getTransMan() {
        return transMan;
    }

    /**
     * @param transMan
     *            the transMan to set
     */
    public void setTransMan(String transMan) {
        this.transMan = transMan;
    }

    /**
     * @return the transOpinion
     */
    public String getTransOpinion() {
        return transOpinion;
    }

    /**
     * @param transOpinion
     *            the transOpinion to set
     */
    public void setTransOpinion(String transOpinion) {
        this.transOpinion = transOpinion;
    }

    /**
     * @return the approveMan
     */
    public String getApproveMan() {
        return approveMan;
    }

    /**
     * @param approveMan
     *            the approveMan to set
     */
    public void setApproveMan(String approveMan) {
        this.approveMan = approveMan;
    }

    /**
     * @return the approveOpinion
     */
    public String getApproveOpinion() {
        return approveOpinion;
    }

    /**
     * @param approveOpinion
     *            the approveOpinion to set
     */
    public void setApproveOpinion(String approveOpinion) {
        this.approveOpinion = approveOpinion;
    }

    /**
     * @return the transResult
     */
    public String getTransResult() {
        return transResult;
    }

    /**
     * @param transResult
     *            the transResult to set
     */
    public void setTransResult(String transResult) {
        this.transResult = transResult;
    }

    /**
     * @return the askOpinion
     */
    public String getAskOpinion() {
        return askOpinion;
    }

    /**
     * @param askOpinion
     *            the askOpinion to set
     */
    public void setAskOpinion(String askOpinion) {
        this.askOpinion = askOpinion;
    }

    /**
     * @return the returnDept
     */
    public String getReturnDept() {
        return returnDept;
    }

    /**
     * @param returnDept
     *            the returnDept to set
     */
    public void setReturnDept(String returnDept) {
        this.returnDept = returnDept;
    }

    /**
     * @return the returnMan
     */
    public String getReturnMan() {
        return returnMan;
    }

    /**
     * @param returnMan
     *            the returnMan to set
     */
    public void setReturnMan(String returnMan) {
        this.returnMan = returnMan;
    }

    /**
     * @return the returnDate
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * @param returnDate
     *            the returnDate to set
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * @return the returnReason
     */
    public String getReturnReason() {
        return returnReason;
    }

    /**
     * @param returnReason
     *            the returnReason to set
     */
    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    /**
     * @return the revokeDept
     */
    public String getRevokeDept() {
        return revokeDept;
    }

    /**
     * @param revokeDept
     *            the revokeDept to set
     */
    public void setRevokeDept(String revokeDept) {
        this.revokeDept = revokeDept;
    }

    /**
     * @return the revokeMan
     */
    public String getRevokeMan() {
        return revokeMan;
    }

    /**
     * @param revokeMan
     *            the revokeMan to set
     */
    public void setRevokeMan(String revokeMan) {
        this.revokeMan = revokeMan;
    }

    /**
     * @return the revokeDate
     */
    public Date getRevokeDate() {
        return revokeDate;
    }

    /**
     * @param revokeDate
     *            the revokeDate to set
     */
    public void setRevokeDate(Date revokeDate) {
        this.revokeDate = revokeDate;
    }

    /**
     * @return the revokeReason
     */
    public String getRevokeReason() {
        return revokeReason;
    }

    /**
     * @param revokeReason
     *            the revokeReason to set
     */
    public void setRevokeReason(String revokeReason) {
        this.revokeReason = revokeReason;
    }

    /**
     * @return the digitalSignature
     */
    public String getDigitalSignature() {
        return digitalSignature;
    }

    /**
     * @param digitalSignature
     *            the digitalSignature to set
     */
    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    /**
     * @return the digitalSeal
     */
    public String getDigitalSeal() {
        return digitalSeal;
    }

    /**
     * @param digitalSeal
     *            the digitalSeal to set
     */
    public void setDigitalSeal(String digitalSeal) {
        this.digitalSeal = digitalSeal;
    }

    /**
     * @return the twoDimensionCode
     */
    public String getTwoDimensionCode() {
        return twoDimensionCode;
    }

    /**
     * @param twoDimensionCode
     *            the twoDimensionCode to set
     */
    public void setTwoDimensionCode(String twoDimensionCode) {
        this.twoDimensionCode = twoDimensionCode;
    }

    /**
     * @return the sendTime
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * @param sendTime
     *            the sendTime to set
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

	public Set<RemoteDocSwapAttach> getRemoteDocSwapAttachs() {
		return remoteDocSwapAttachs;
	}

	public void setRemoteDocSwapAttachs(Set<RemoteDocSwapAttach> remoteDocSwapAttachs) {
		this.remoteDocSwapAttachs = remoteDocSwapAttachs;
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

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
        if (this.finishTime != null) {
            this.finishTimeFmt = Tools.DATE_TIME_FORMAT.format(this.finishTime);
        }
	}

	public String getFinishMemo() {
		return finishMemo;
	}

	public void setFinishMemo(String finishMemo) {
		this.finishMemo = finishMemo;
	}

	public String getLetterId() {
		return letterId;
	}

	public void setLetterId(String letterId) {
		this.letterId = letterId;
	}

	public String getDealResultType() {
		return dealResultType;
	}

	public void setDealResultType(String dealResultType) {
		this.dealResultType = dealResultType;
	}

	public String getDealResult() {
		return dealResult;
	}

	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}

	public String getDocReadBody() {
		return docReadBody;
	}

	public void setDocReadBody(String docReadBody) {
		this.docReadBody = docReadBody;
	}

	public Date getDocReadTime() {
		return docReadTime;
	}

	public void setDocReadTime(Date docReadTime) {
		this.docReadTime = docReadTime;
        if (this.docReadTime != null) {
            this.docReadTimeFmt = Tools.DATE_TIME_FORMAT.format(this.docReadTime);
        }
	}

	public Date getDocFileTime() {
		return docFileTime;
	}

	public void setDocFileTime(Date docFileTime) {
		this.docFileTime = docFileTime;
        if (this.docFileTime != null) {
            this.docFileTimeFmt = Tools.DATE_TIME_FORMAT.format(this.docFileTime);
        }
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
        if (this.backTime != null) {
            this.backTimeFmt = Tools.DATE_TIME_FORMAT.format(this.backTime);
        }
	}

	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}

	public String getPrintOrgAbb() {
		return printOrgAbb;
	}

	public void setPrintOrgAbb(String printOrgAbb) {
		this.printOrgAbb = printOrgAbb;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public Date getUndoTime() {
		return undoTime;
	}

	public void setUndoTime(Date undoTime) {
		this.undoTime = undoTime;
	}

	public Long getEnvelopeId() {
		return envelopeId;
	}

	public void setEnvelopeId(Long envelopeId) {
		this.envelopeId = envelopeId;
	}

	public RemoteDocSwapAttach getGdFileDoc() {
		return gdFileDoc;
	}

	public void setGdFileDoc(RemoteDocSwapAttach gdFileDoc) {
		this.gdFileDoc = gdFileDoc;
	}

	public RemoteDocSwapAttach getGwFileDoc() {
		return gwFileDoc;
	}

	public void setGwFileDoc(RemoteDocSwapAttach gwFileDoc) {
		this.gwFileDoc = gwFileDoc;
	}

	public RemoteDocSwapAttach getTifFileDoc() {
		return tifFileDoc;
	}

	public void setTifFileDoc(RemoteDocSwapAttach tifFileDoc) {
		this.tifFileDoc = tifFileDoc;
	}

	public String getBackTimeFmt() {
		return backTimeFmt;
	}

	public void setBackTimeFmt(String backTimeFmt) {
		this.backTimeFmt = backTimeFmt;
	}

	public String getFinishTimeFmt() {
		return finishTimeFmt;
	}

	public void setFinishTimeFmt(String finishTimeFmt) {
		this.finishTimeFmt = finishTimeFmt;
	}

	public String getDocReadTimeFmt() {
		return docReadTimeFmt;
	}

	public void setDocReadTimeFmt(String docReadTimeFmt) {
		this.docReadTimeFmt = docReadTimeFmt;
	}

	public String getDocFileTimeFmt() {
		return docFileTimeFmt;
	}

	public void setDocFileTimeFmt(String docFileTimeFmt) {
		this.docFileTimeFmt = docFileTimeFmt;
	}

	public String getOverTimeFlag() {
		return overTimeFlag;
	}

	public void setOverTimeFlag(String overTimeFlag) {
		this.overTimeFlag = overTimeFlag;
	}
     

}
