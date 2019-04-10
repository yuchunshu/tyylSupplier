package cn.com.chaochuang.doc.letter.bean;

import java.util.Date;

import org.dozer.Mapping;

/**
 * @author LJX
 *
 */
public class DepLetterShowBean {
    @Mapping("id")
    private String letterId;
    /** 标题 */
    private String title;
    /** 函件编号 */
    private String letterCode;
    /** 接收部门 */
    private String incepterDept;
    /** 处理时限日期 */
    private String expiryDate;
    /** 紧急程度 */
    @Mapping("urgencyLevel.key")
    private String urgencyLevelKey;
    @Mapping("urgencyLevel.value")
    private String urgencyLevelValue;
    /** 状态 */
    @Mapping("status.key")
    private String statusKey;
    @Mapping("status.value")
    private String statusValue;
    /** 创建人姓名 */
    private String creatorName;
    /** 创建日期 */
    private Date   createDate;
    /** 附件标识 */
    @Mapping("attachFlag.key")
    private String attachFlagKey;

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
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
     * @return the expiryDate
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * @param expiryDate
     *            the expiryDate to set
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * @return the urgencyLevelKey
     */
    public String getUrgencyLevelKey() {
        return urgencyLevelKey;
    }

    /**
     * @param urgencyLevelKey
     *            the urgencyLevelKey to set
     */
    public void setUrgencyLevelKey(String urgencyLevelKey) {
        this.urgencyLevelKey = urgencyLevelKey;
    }

    /**
     * @return the urgencyLevelValue
     */
    public String getUrgencyLevelValue() {
        return urgencyLevelValue;
    }

    /**
     * @param urgencyLevelValue
     *            the urgencyLevelValue to set
     */
    public void setUrgencyLevelValue(String urgencyLevelValue) {
        this.urgencyLevelValue = urgencyLevelValue;
    }

    /**
     * @return the statusKey
     */
    public String getStatusKey() {
        return statusKey;
    }

    /**
     * @param statusKey
     *            the statusKey to set
     */
    public void setStatusKey(String statusKey) {
        this.statusKey = statusKey;
    }

    /**
     * @return the statusValue
     */
    public String getStatusValue() {
        return statusValue;
    }

    /**
     * @param statusValue
     *            the statusValue to set
     */
    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    /**
     * @return the creatorName
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * @param creatorName
     *            the creatorName to set
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     *            the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the attachFlagKey
     */
    public String getAttachFlagKey() {
        return attachFlagKey;
    }

    /**
     * @param attachFlagKey
     *            the attachFlagKey to set
     */
    public void setAttachFlagKey(String attachFlagKey) {
        this.attachFlagKey = attachFlagKey;
    }

}
