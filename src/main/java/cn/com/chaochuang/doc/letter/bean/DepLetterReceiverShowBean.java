package cn.com.chaochuang.doc.letter.bean;

import org.dozer.Mapping;

/**
 * @author LJX
 *
 */
public class DepLetterReceiverShowBean {
    private String letterId;
    @Mapping("id")
    private String receiverId;
    /** 接收日期 */
    private String receiveDate;
    /** 标题 */
    @Mapping("letter.title")
    private String title;
    /** 处理时限日期 */
    @Mapping("letter.expiryDate")
    private String expiryDate;
    /** 紧急程度 */
    @Mapping("letter.urgencyLevel.key")
    private String urgencyLevelKey;
    @Mapping("letter.urgencyLevel.value")
    private String urgencyLevelValue;
    @Mapping("letter.creatorDep.deptName")
    private String sendDep;
    @Mapping("letter.unit.deptName")
    private String sendUnit;
    /** 附件标识 */
    @Mapping("attachFlag.key")
    private String attachFlagKey;
    @Mapping("status.key")
    private String statusKey;
    @Mapping("status.value")
    private String statusValue;

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
     * @return the receiverId
     */
    public String getReceiverId() {
        return receiverId;
    }

    /**
     * @param receiverId
     *            the receiverId to set
     */
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * @return the receiveDate
     */
    public String getReceiveDate() {
        return receiveDate;
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
     * @param receiveDate
     *            the receiveDate to set
     */
    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
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
     * @return the sendDep
     */
    public String getSendDep() {
        return sendDep;
    }

    /**
     * @param sendDep
     *            the sendDep to set
     */
    public void setSendDep(String sendDep) {
        this.sendDep = sendDep;
    }

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
