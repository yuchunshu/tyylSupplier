/*
 * FileName:    MailInboxBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年8月21日 (HeYunTao) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author HeYunTao
 *
 */
public class MailInboxInfo {
    /** 已读 */
    public static final String   READ          = "1";
    /** 未读 */
    public static final String   NOTREAD       = "0";

    /** 接收人名称 */
    private String               receiveName;
    /** 接收人绑定用户 */
    private Long                 receiveUserId;
    /** 发件人绑定用户id */
    private Long                 sendUserId;
    // /** 发件人地址 */
    // private String sendAddr;
    /** 发件人名称 */
    private String               sendName;
    /** 邮件主题 */
    private String               subject;
    /** 邮件内容 */
    private String               content;
    /** 内容摘要 */
    private String               digest;
    /** 接受 时间 */
    private Date                 receiveTime;
    /** 同步 时间 */
    private Date                 inputTime;
    /** 是否已读 */
    private String               isRead;
    /** 回执标记 */
    private String               isReply;
    /** 邮件大小 */
    private Long                 mailSize;
    /** 邮件标识 */
    private String               MessageId;
    /** 附件 */
    private List<MailAttachInfo> remoteAttachs = new ArrayList<MailAttachInfo>();
    /** 原邮件的id */
    private String               rmMailId;

    /** 状态 0 未处理 1 存档 2 垃圾 */
    private String               status;

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the rmMailId
     */
    public String getRmMailId() {
        return rmMailId;
    }

    /**
     * @param rmMailId
     *            the rmMailId to set
     */
    public void setRmMailId(String rmMailId) {
        this.rmMailId = rmMailId;
    }

    /**
     * @return the attachs
     */
    public List<MailAttachInfo> getRemoteAttachs() {
        return remoteAttachs;
    }

    /**
     * @param attachs
     *            the attachs to set
     */
    public void setRemoteAttachs(List<MailAttachInfo> remoteAttachs) {
        this.remoteAttachs = remoteAttachs;
    }

    /**
     * @return the sendUserId
     */
    public Long getSendUserId() {
        return sendUserId;
    }

    /**
     * @param sendUserId
     *            the sendUserId to set
     */
    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    /**
     * @return the receiveName
     */
    public String getReceiveName() {
        return receiveName;
    }

    /**
     * @param receiveName
     *            the receiveName to set
     */
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    /**
     * @return the receiveUserId
     */
    public Long getReceiveUserId() {
        return receiveUserId;
    }

    /**
     * @param receiveUserId
     *            the receiveUserId to set
     */
    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    /**
     * @return the sendName
     */
    public String getSendName() {
        return sendName;
    }

    /**
     * @param sendName
     *            the sendName to set
     */
    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject
     *            the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
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

    /**
     * @return the digest
     */
    public String getDigest() {
        return digest;
    }

    /**
     * @param digest
     *            the digest to set
     */
    public void setDigest(String digest) {
        this.digest = digest;
    }

    /**
     * @return the receiveDate
     */
    public Date getReceiveTime() {
        return receiveTime;
    }

    /**
     * @param receiveDate
     *            the receiveDate to set
     */
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * @return the inputTime
     */
    public Date getInputTime() {
        return inputTime;
    }

    /**
     * @param inputTime
     *            the inputTime to set
     */
    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    /**
     * @return the isRead
     */
    public String getIsRead() {
        return isRead;
    }

    /**
     * @param isRead
     *            the isRead to set
     */
    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    /**
     * @return the isReply
     */
    public String getIsReply() {
        return isReply;
    }

    /**
     * @param isReply
     *            the isReply to set
     */
    public void setIsReply(String isReply) {
        this.isReply = isReply;
    }

    /**
     * @return the mailSize
     */
    public Long getMailSize() {
        return mailSize;
    }

    /**
     * @param mailSize
     *            the mailSize to set
     */
    public void setMailSize(Long mailSize) {
        this.mailSize = mailSize;
    }

    /**
     * @return the messageId
     */
    public String getMessageId() {
        return MessageId;
    }

    /**
     * @param messageId
     *            the messageId to set
     */
    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

}