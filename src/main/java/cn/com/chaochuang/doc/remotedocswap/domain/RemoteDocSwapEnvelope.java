/*
 * FileName:    RemoteDocSwapEnvelope.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.remotedocswap.DocSwapTools;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapTypeConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvRecFlag;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvRecFlagConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvStatus;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvStatusConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvTypeConverter;

/**
 * @author yuandl
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "envelope_id")) })
@Table(name = "remote_doc_swap_envelope")
public class RemoteDocSwapEnvelope extends LongIdEntity {

    /**  */
    private static final long 	 serialVersionUID = 7172548335535454298L;
    /** 封首类型 */
    @Convert(converter = RemoteEnvTypeConverter.class)
    private RemoteEnvType     	 envelopeType;

    /** 接收单位组织机构代码 */
    private String            	 secondaryUnitIdentifier;

    /** 接收单位名称 */
    private String            	 secondaryUnitIdentifierName;

    /** 资源标识 */
    private String            	 identifier;

    /** 收文时限类型 */
    private String 			  	 deadlineType;

    /** 收文时限值 */
    private Integer           	 deadlineDay;

    /** 收文时限 */
    private Date              	 limitTime;
    @Transient
    private String            	 limitTimeFmt;

    /** UUID */
    private String            	 envelopeUuid;

    /** 发送人编号 */
    private Long              	 senderId;

    /** 发送人姓名 */
    private String            	 senderName;

    /** 发送人部门 */
    private Long              	 senderDept;

    /** 发送时间 */
    private Date              	 sendTime;
    @Transient
    private String            	 sendTimeFmt;

    /** 状态 */
    @Convert(converter = RemoteEnvStatusConverter.class)
    private RemoteEnvStatus   	 envStatus;

    /** 相关封首编号 */
    private Long              	 linkEnvelopeId;

    /** 备注 */
    private String            	 remark;

    /** 收发标志 */
    @Convert(converter = RemoteEnvRecFlagConverter.class)
    private RemoteEnvRecFlag  	 receiveFlag;

    /** 发送单位名称 */
    private String            	 senderAncestorName;

    /** 发送单位组织机构代码 */
    private String            	 senderAncestorOrg;

    /** 标题 */
    private String            	 title;

    /** 封内文件的文件名 */
    @Transient
    private String            	 envelopeContentPath;

    /** 封内文件的MD5码 */
    @Transient
    private String            	 envelopeContentMD5;

    /** 收文登记的流水号 */
    private String            	 receiveFileId;

    //非持久化字段，公文文件夹包含的文件信息
    @Transient
    private List 			  	 fileMapList;
    
    /** 发送单位编号 */
    private Long 			  	 senderAncestorDept;
    
    /** 公文实例编号*/
    private String 			  	 instId;
    /**签收人id (user_id)*/
    private Long 			  	 signerId;
    /**签收人名称*/
    private String 			  	 signerName;
    /**签收人部门id*/
    private Long 			  	 signerDeptId;
    /**签收人部门*/
    private String 			  	 signerDeptName;
    /**签收时间*/
    private Date 			  	 signDate;
    @Transient
    private String            	 signDateFmt;
    /** 公文类型（办件：0 阅件：1） */
    @Convert(converter = RemoteDocSwapTypeConverter.class)
    private RemoteDocSwapType 	 documentType;
    
    /**撤销时间*/
    private Date 			  	 undoTime;
    @Transient
    private String            	 undoTimeFmt;
    /**作废时间*/
    private Date 			  	 invalidTime;
    @Transient
    private String            	 invalidTimeFmt;
    
    /**修改限时时间*/
    private Date 			  	 modifyDeadlineTime;
    @Transient
    private String            	 modifyDeadlineTimeFmt;
    
    
    /** 内部定义的办文时限 */
    private Date 			  	 selfLimitTime;
    
    //非持久化字段，根据办理时限计算出天数
    @Transient
    private Integer 		  	 calDeadlineDay;
    
    @Transient
    private RemoteDocSwapContent content;
    /**
     * @return the envelopeType
     */
    public RemoteEnvType getEnvelopeType() {
        return envelopeType;
    }

    /**
     * @param envelopeType
     *            the envelopeType to set
     */
    public void setEnvelopeType(RemoteEnvType envelopeType) {
        this.envelopeType = envelopeType;
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
     * @return the deadlineType
     */
    public String getDeadlineType() {
        return deadlineType;
    }

    /**
     * @param deadlineType
     *            the deadlineType to set
     */
    public void setDeadlineType(String deadlineType) {
        this.deadlineType = deadlineType;
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
        if (this.limitTime != null) {
            this.limitTimeFmt = Tools.DATE_TIME_FORMAT.format(this.limitTime);
        }
    }

    
    public String getLimitTimeFmt() {
		return limitTimeFmt;
	}

	public void setLimitTimeFmt(String limitTimeFmt) {
		this.limitTimeFmt = limitTimeFmt;
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
     * @return the senderId
     */
    public Long getSenderId() {
        return senderId;
    }

    /**
     * @param senderId
     *            the senderId to set
     */
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    /**
     * @return the senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * @param senderName
     *            the senderName to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * @return the senderDept
     */
    public Long getSenderDept() {
        return senderDept;
    }

    /**
     * @param senderDept
     *            the senderDept to set
     */
    public void setSenderDept(Long senderDept) {
        this.senderDept = senderDept;
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
        if (this.sendTime != null) {
            this.sendTimeFmt = Tools.DATE_TIME_FORMAT.format(this.sendTime);
        }
    }

    
    public String getSendTimeFmt() {
		return sendTimeFmt;
	}

	public void setSendTimeFmt(String sendTimeFmt) {
		this.sendTimeFmt = sendTimeFmt;
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
     * @return the receiveFlag
     */
    public RemoteEnvRecFlag getReceiveFlag() {
        return receiveFlag;
    }

    /**
     * @param receiveFlag
     *            the receiveFlag to set
     */
    public void setReceiveFlag(RemoteEnvRecFlag receiveFlag) {
        this.receiveFlag = receiveFlag;
    }

    /**
     * @return the senderAncestorName
     */
    public String getSenderAncestorName() {
        return senderAncestorName;
    }

    /**
     * @param senderAncestorName
     *            the senderAncestorName to set
     */
    public void setSenderAncestorName(String senderAncestorName) {
        this.senderAncestorName = senderAncestorName;
    }

    /**
     * @return the senderAncestorOrg
     */
    public String getSenderAncestorOrg() {
        return senderAncestorOrg;
    }

    /**
     * @param senderAncestorOrg
     *            the senderAncestorOrg to set
     */
    public void setSenderAncestorOrg(String senderAncestorOrg) {
        this.senderAncestorOrg = senderAncestorOrg;
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
     * @return the envelopeContentPath
     */
    public String getEnvelopeContentPath() {
        return envelopeContentPath;
    }

    /**
     * @param envelopeContentPath
     *            the envelopeContentPath to set
     */
    public void setEnvelopeContentPath(String envelopeContentPath) {
        this.envelopeContentPath = envelopeContentPath;
    }

    /**
     * @return the envelopeContentMD5
     */
    public String getEnvelopeContentMD5() {
        return envelopeContentMD5;
    }

    /**
     * @param envelopeContentMD5
     *            the envelopeContentMD5 to set
     */
    public void setEnvelopeContentMD5(String envelopeContentMD5) {
        this.envelopeContentMD5 = envelopeContentMD5;
    }

    /**
     * @return the receiveFileId
     */
    public String getReceiveFileId() {
        return receiveFileId;
    }

    /**
     * @param receiveFileId
     *            the receiveFileId to set
     */
    public void setReceiveFileId(String receiveFileId) {
        this.receiveFileId = receiveFileId;
    }

	public List getFileMapList() {
		return fileMapList;
	}

	public void setFileMapList(List fileMapList) {
		this.fileMapList = fileMapList;
	}

	public RemoteDocSwapType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(RemoteDocSwapType documentType) {
		this.documentType = documentType;
	}

	public Long getSenderAncestorDept() {
		return senderAncestorDept;
	}

	public void setSenderAncestorDept(Long senderAncestorDept) {
		this.senderAncestorDept = senderAncestorDept;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public Long getSignerId() {
		return signerId;
	}

	public void setSignerId(Long signerId) {
		this.signerId = signerId;
	}

	public String getSignerName() {
		return signerName;
	}

	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}

	public Long getSignerDeptId() {
		return signerDeptId;
	}

	public void setSignerDeptId(Long signerDeptId) {
		this.signerDeptId = signerDeptId;
	}

	public String getSignerDeptName() {
		return signerDeptName;
	}

	public void setSignerDeptName(String signerDeptName) {
		this.signerDeptName = signerDeptName;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
		this.signDate = signDate;
        if (this.signDate != null) {
            this.signDateFmt = Tools.DATE_TIME_FORMAT.format(this.signDate);
        }
	}

	public String getSignDateFmt() {
		return signDateFmt;
	}

	public void setSignDateFmt(String signDateFmt) {
		this.signDateFmt = signDateFmt;
	}

	public Date getUndoTime() {
		return undoTime;
	}

	public void setUndoTime(Date undoTime) {
		this.undoTime = undoTime;
        if (this.undoTime != null) {
            this.undoTimeFmt = Tools.DATE_TIME_FORMAT.format(this.undoTime);
        }
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
        if (this.invalidTime != null) {
            this.invalidTimeFmt = Tools.DATE_TIME_FORMAT.format(this.invalidTime);
        }
	}

	public Date getModifyDeadlineTime() {
		return modifyDeadlineTime;
	}

	public void setModifyDeadlineTime(Date modifyDeadlineTime) {
		this.modifyDeadlineTime = modifyDeadlineTime;
        if (this.modifyDeadlineTime != null) {
            this.modifyDeadlineTimeFmt = Tools.DATE_TIME_FORMAT.format(this.modifyDeadlineTime);
        }
	}

	public Date getSelfLimitTime() {
		return selfLimitTime;
	}

	public void setSelfLimitTime(Date selfLimitTime) {
		this.selfLimitTime = selfLimitTime;
	}
 
	public Integer getCalDeadlineDay() {
        if(this.limitTime!=null){
            return DocSwapTools.calculateDayInterval(new Date(),this.limitTime);
        }
        return null;
    }

	public String getUndoTimeFmt() {
		return undoTimeFmt;
	}

	public void setUndoTimeFmt(String undoTimeFmt) {
		this.undoTimeFmt = undoTimeFmt;
	}

	public String getInvalidTimeFmt() {
		return invalidTimeFmt;
	}

	public void setInvalidTimeFmt(String invalidTimeFmt) {
		this.invalidTimeFmt = invalidTimeFmt;
	}

	public RemoteDocSwapContent getContent() {
		return content;
	}

	public void setContent(RemoteDocSwapContent content) {
		this.content = content;
	}

	public String getModifyDeadlineTimeFmt() {
		return modifyDeadlineTimeFmt;
	}
	
	
}
