package cn.com.chaochuang.webservice.bean;

import java.util.Date;

public class PendingCommandInfo {
    /** 远程系统待办编号 */
    private String rmPendingId;
    /** URL */
    private String url;
    /** 标题 */
    private String title;
    /** 发送人编号 */
    private Long   senderId;
    /** 发送时间 */
    private Date   sendTime;
    /** 发送人姓名 */
    private String senderName;
    /** 待办数据读取时间 */
    private Date   readTime;
    /** 待办明细接收人编号 */
    private Long   recipientId;
    /** 紧急程度 */
    private String emergencyLevel;
    /** 发送人所在部门 */
    private String senderDeptName;
    /** 最后的发送时间 */
    private Date   lastSendTime;

    public String getRmPendingId() {
        return rmPendingId;
    }

    public void setRmPendingId(String rmPendingId) {
        this.rmPendingId = rmPendingId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public String getEmergencyLevel() {
        return emergencyLevel;
    }

    public void setEmergencyLevel(String emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }

    public String getSenderDeptName() {
        return senderDeptName;
    }

    public void setSenderDeptName(String senderDeptName) {
        this.senderDeptName = senderDeptName;
    }

    public Date getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(Date lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    @Override
    public String toString() {
        return "PendingCommandInfo [rmPendingId=" + rmPendingId + ", url=" + url + ", title=" + title + ", senderId="
                        + senderId + ", sendTime=" + sendTime + ", senderName=" + senderName + ", readTime=" + readTime
                        + ", recipientId=" + recipientId + ", emergencyLevel=" + emergencyLevel + ", senderDeptName="
                        + senderDeptName + ", lastSendTime=" + lastSendTime + ", getRmPendingId()=" + getRmPendingId()
                        + ", getUrl()=" + getUrl() + ", getTitle()=" + getTitle() + ", getSenderId()=" + getSenderId()
                        + ", getSendTime()=" + getSendTime() + ", getSenderName()=" + getSenderName()
                        + ", getReadTime()=" + getReadTime() + ", getRecipientId()=" + getRecipientId()
                        + ", getEmergencyLevel()=" + getEmergencyLevel() + ", getSenderDeptName()="
                        + getSenderDeptName() + ", getLastSendTime()=" + getLastSendTime() + ", getClass()="
                        + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

}
