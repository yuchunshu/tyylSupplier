/*
 * FileName:    DocSwapEnvelopeShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.bean;

import java.util.Date;

import javax.persistence.Convert;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapEnvelope;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapTypeConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvRecFlag;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvReceiveStatus;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvReceiveStatusConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvStatus;

/**
 * @author yuandl
 *
 */
public class DocSwapEnvelopeShowBean {

    private static final RemoteEnvReceiveStatusConverter converter = new RemoteEnvReceiveStatusConverter();

    private Long                                         id;

    /** UUID */
    private String                                       envelopeUuid;

    /** 收发标志 */
    private RemoteEnvRecFlag                             receiveFlag;

    /** 接收单位组织机构代码 */
    private String                                       secondaryUnitIdentifier;

    /** 接收单位名称 */
    private String                                       secondaryUnitIdentifierName;

    /** 发送单位名称 */
    private String                                       senderAncestorName;

    /** 发送单位组织机构代码 */
    private String                                       senderAncestorOrg;

    /** 发送人编号 */
    private Long                                         senderId;

    /** 发送人姓名 */
    private String                                       senderName;

    /** 发送人部门 */
    private Long                                         senderDept;

    /** 发送时间 */
    private Date                                         sendTime;

    private String                                       sendTimeShow;

    /** 标题 */
    private String                                       title;

    /** 状态 */
    private RemoteEnvStatus                              envStatus;

    private RemoteEnvReceiveStatus                       envReceiveStatus;

    /** 收文时限 */
    private Date              							 limitTime;
    
    private String                                       limitTimeShow;
    
    /** 办结时间 */
    private Date 					 					 finishTime;
    
    private String                                       finishTimeShow;
    
    /** 公文类型（办件：0 阅件：1） */
    @Convert(converter = RemoteDocSwapTypeConverter.class)
    private RemoteDocSwapType documentType;
    
    /**签收人名称*/
    private String 			  							 signerName;
    
    /**签收时间*/
    private Date 			  							 signDate;
    
    private String                                       signDateShow;
    
    /**封首内容*/
    private RemoteDocSwapEnvelope    				     envelope;
    
    //非持久化字段，根据办理时限计算出天数
    private Integer 		  						     calDeadlineDay;
    
    /** 公文实例编号*/
    private String 			  	 						 instId;
    
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
            this.sendTimeShow = Tools.DATE_TIME_FORMAT.format(this.sendTime);
        }
    }

    /**
     * @return the sendTimeShow
     */
    public String getSendTimeShow() {
        return sendTimeShow;
    }

    /**
     * @param sendTimeShow
     *            the sendTimeShow to set
     */
    public void setSendTimeShow(String sendTimeShow) {
        this.sendTimeShow = sendTimeShow;
    }

    public String getLimitTimeShow() {
		return limitTimeShow;
	}

	public void setLimitTimeShow(String limitTimeShow) {
		this.limitTimeShow = limitTimeShow;
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
        if (this.envStatus != null) {

            this.envReceiveStatus = converter.convertToEntityAttribute(this.envStatus.getKey());
        }
    }

    /**
     * @return the envReceiveStatus
     */
    public RemoteEnvReceiveStatus getEnvReceiveStatus() {
        return envReceiveStatus;
    }

    /**
     * @param envReceiveStatus
     *            the envReceiveStatus to set
     */
    public void setEnvReceiveStatus(RemoteEnvReceiveStatus envReceiveStatus) {
        this.envReceiveStatus = envReceiveStatus;
    }

	public Date getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Date limitTime) {
		this.limitTime = limitTime;
        if (this.limitTime != null) {
            this.limitTimeShow = Tools.DATE_TIME_FORMAT.format(this.limitTime);
        }
		
	}

	public RemoteDocSwapType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(RemoteDocSwapType documentType) {
		this.documentType = documentType;
	}

	public String getSignerName() {
		return signerName;
	}

	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
        if (this.signDate != null) {
            this.signDateShow = Tools.DATE_TIME_FORMAT.format(this.signDate);
        }
	}

	public String getSignDateShow() {
		return signDateShow;
	}

	public void setSignDateShow(String signDateShow) {
		this.signDateShow = signDateShow;
	}

	public RemoteDocSwapEnvelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(RemoteDocSwapEnvelope envelope) {
		this.envelope = envelope;
	}

	public Integer getCalDeadlineDay() {
		return calDeadlineDay;
	}

	public void setCalDeadlineDay(Integer calDeadlineDay) {
		this.calDeadlineDay = calDeadlineDay;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
        if (this.finishTime != null) {
            this.finishTimeShow = Tools.DATE_TIME_FORMAT.format(this.finishTime);
        }
	}

	public String getFinishTimeShow() {
		return finishTimeShow;
	}

	public void setFinishTimeShow(String finishTimeShow) {
		this.finishTimeShow = finishTimeShow;
	}

}
