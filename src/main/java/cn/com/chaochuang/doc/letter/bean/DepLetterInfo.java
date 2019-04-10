package cn.com.chaochuang.doc.letter.bean;

import java.util.Date;

import cn.com.chaochuang.doc.event.reference.UrgencyLevelType;
import cn.com.chaochuang.doc.letter.reference.LetterReceiverStatus;

/**
 * @author LJX
 *
 */
public class DepLetterInfo {
    private String               id;
    /** 公文id */
    private String               fileId;
    /** 标题 */
    private String               title;
    /** 函件编号 */
    private String               letterCode;
    /** 接收部门 */
    private String               incepterDept;
    /** 紧急程度 */
    private UrgencyLevelType     urgencyLevel;
    /** 内容 */
    private String               content;
    /** 附件标识 */
    private String[]             attachs;
    /** 办理工作日时限 */
    private Integer              limitDay;
    /** 处理时限日期 */
    private Date                 expiryDate;
    /** 接收部门ID */
    private String               incepterDepts;

    private String               letterId;

    private String               receiveId;

    private LetterReceiverStatus receiverStatus;
    /** 补充说明 */
    private String               replenish;

    /**版本*/
    private Integer   apprVer;

    /**
     * @return the receiveId
     */
    public String getReceiveId() {
        return receiveId;
    }

    public Integer getApprVer() {
		return apprVer;
	}

	public void setApprVer(Integer apprVer) {
		this.apprVer = apprVer;
	}

	/**
     * @param receiveId
     *            the receiveId to set
     */
    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    /**
     * @return the replenish
     */
    public String getReplenish() {
        return replenish;
    }

    /**
     * @param replenish
     *            the replenish to set
     */
    public void setReplenish(String replenish) {
        this.replenish = replenish;
    }

    /**
     * @return the receiverStatus
     */
    public LetterReceiverStatus getReceiverStatus() {
        return receiverStatus;
    }

    /**
     * @param receiverStatus
     *            the receiverStatus to set
     */
    public void setReceiverStatus(LetterReceiverStatus receiverStatus) {
        this.receiverStatus = receiverStatus;
    }

    /**
     * @return the letterId
     */
    public String getLetterId() {
        return letterId;
    }

    /**
     * @param letterId
     *            the letterId to set
     */
    public void setLetterId(String letterId) {
        this.letterId = letterId;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the letterCode
     */
    public String getLetterCode() {
        return letterCode;
    }

    /**
     * @param letterCode
     *            the letterCode to set
     */
    public void setLetterCode(String letterCode) {
        this.letterCode = letterCode;
    }

    /**
     * @return the incepterDept
     */
    public String getIncepterDept() {
        return incepterDept;
    }

    /**
     * @param incepterDept
     *            the incepterDept to set
     */
    public void setIncepterDept(String incepterDept) {
        this.incepterDept = incepterDept;
    }

    /**
     * @return the urgencyLevel
     */
    public UrgencyLevelType getUrgencyLevel() {
        return urgencyLevel;
    }

    /**
     * @param urgencyLevel
     *            the urgencyLevel to set
     */
    public void setUrgencyLevel(UrgencyLevelType urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }


    public String[] getAttachs() {
		return attachs;
	}

	public void setAttachs(String[] attachs) {
		this.attachs = attachs;
	}

	/**
     * @return the limitDay
     */
    public Integer getLimitDay() {
        return limitDay;
    }

    /**
     * @param limitDay
     *            the limitDay to set
     */
    public void setLimitDay(Integer limitDay) {
        this.limitDay = limitDay;
    }

    public String getIncepterDepts() {
        return incepterDepts;
    }

    public void setIncepterDepts(String incepterDepts) {
        this.incepterDepts = incepterDepts;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

}
