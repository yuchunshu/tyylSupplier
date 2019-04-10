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
public class MailOutboxInfo {
    /** 原邮箱的标记-逻辑删除 */
    public static final String   FLAG_LOGICDEL = "6";
    /** 发送失败 */
    public static final String   SEND_FAIL     = "2";
    /** 发送成功 */
    public static final String   SEND_SUCCESS  = "1";

    /** 接收人名称 */
    private String               receiveName;
    /** 发件人绑定用户 */
    private Long                 sendUserId;
    /** 收件人地址 */
    private String               receiveAddr;
    /** 发件人名称 */
    private String               sendName;
    /** 邮件主题 */
    private String               subject;
    /** 邮件内容 */
    private String               content;
    /** 内容摘要 */
    private String               digest;
    /** 同步 时间 */
    private Date                 inputTime;
    /** 发送错误描述 */
    private String               errorScript;
    /** 发送状态 */
    private String               status;
    /** 发送时间 */
    private Date                 sendTime;
    /** 附件 */
    private List<MailAttachInfo> remoteAttachs = new ArrayList<MailAttachInfo>();
    /** 原邮件的id */
    private String               rmMailId;


    public String getRmMailId() {
		return rmMailId;
	}

	public void setRmMailId(String rmMailId) {
		this.rmMailId = rmMailId;
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
     * @return the remoteAttachs
     */
    public List<MailAttachInfo> getRemoteAttachs() {
        return remoteAttachs;
    }

    /**
     * @param remoteAttachs
     *            the remoteAttachs to set
     */
    public void setRemoteAttachs(List<MailAttachInfo> remoteAttachs) {
        this.remoteAttachs = remoteAttachs;
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
     * @return the receiveAddr
     */
    public String getReceiveAddr() {
        return receiveAddr;
    }

    /**
     * @param receiveAddr
     *            the receiveAddr to set
     */
    public void setReceiveAddr(String receiveAddr) {
        this.receiveAddr = receiveAddr;
    }

    /**
     * @return the errorScript
     */
    public String getErrorScript() {
        return errorScript;
    }

    /**
     * @param errorScript
     *            the errorScript to set
     */
    public void setErrorScript(String errorScript) {
        this.errorScript = errorScript;
    }

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

}