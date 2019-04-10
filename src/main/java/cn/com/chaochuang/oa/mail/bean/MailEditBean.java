/*
 * FileName:    MailEditBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月3日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.bean;

import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.oa.mail.reference.EmailType;
import cn.com.chaochuang.oa.mail.reference.MailStatus;
import cn.com.chaochuang.oa.mail.reference.ReceiptFlag;

/**
 * @author HM
 *
 */
public class MailEditBean {

    /** id */
    private String      id;

    /** 标题 */
    private String      title;

    /** 内容 */
    private String      content;

    /** 状态 */
    private MailStatus  status;

    /** 回执:0-否，1-是 */
    private ReceiptFlag receiptFlag;

    /** 邮件类型 */
    private EmailType   emailType;

    /** 地址 : 收件人/或单位IDs */
    private String      unitAddress;
    private String      manAddress;
    private String      groupAddress;

    /** 地址名称 : 收件人/或单位名称s */
    private String      unitAddressNames;
    private String      manAddressNames;
    private String      groupAddressNames;

    /** 有无附件 */
    private AttachFlag  attachFlag;

    /** 附件IDs */
    private String      attachIds;

    private Long        sender;

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
     * @return the status
     */
    public MailStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(MailStatus status) {
        this.status = status;
    }

    /**
     * @return the receiptFlag
     */
    public ReceiptFlag getReceiptFlag() {
        return receiptFlag;
    }

    /**
     * @param receiptFlag
     *            the receiptFlag to set
     */
    public void setReceiptFlag(ReceiptFlag receiptFlag) {
        this.receiptFlag = receiptFlag;
    }

    /**
     * @return the emailType
     */
    public EmailType getEmailType() {
        return emailType;
    }

    /**
     * @param emailType
     *            the emailType to set
     */
    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }

    /**
     * @return the unitAddress
     */
    public String getUnitAddress() {
        return unitAddress;
    }

    /**
     * @param unitAddress
     *            the unitAddress to set
     */
    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    /**
     * @return the manAddress
     */
    public String getManAddress() {
        return manAddress;
    }

    /**
     * @param manAddress
     *            the manAddress to set
     */
    public void setManAddress(String manAddress) {
        this.manAddress = manAddress;
    }

    /**
     * @return the unitAddressNames
     */
    public String getUnitAddressNames() {
        return unitAddressNames;
    }

    /**
     * @param unitAddressNames
     *            the unitAddressNames to set
     */
    public void setUnitAddressNames(String unitAddressNames) {
        this.unitAddressNames = unitAddressNames;
    }

    /**
     * @return the manAddressNames
     */
    public String getManAddressNames() {
        return manAddressNames;
    }

    /**
     * @param manAddressNames
     *            the manAddressNames to set
     */
    public void setManAddressNames(String manAddressNames) {
        this.manAddressNames = manAddressNames;
    }

    /**
     * @return the attachFlag
     */
    public AttachFlag getAttachFlag() {
        return attachFlag;
    }

    /**
     * @param attachFlag
     *            the attachFlag to set
     */
    public void setAttachFlag(AttachFlag attachFlag) {
        this.attachFlag = attachFlag;
    }

    /**
     * @return the attachIds
     */
    public String getAttachIds() {
        return attachIds;
    }

    /**
     * @param attachIds
     *            the attachIds to set
     */
    public void setAttachIds(String attachIds) {
        this.attachIds = attachIds;
    }

    /**
     * @return the groupAddress
     */
    public String getGroupAddress() {
        return groupAddress;
    }

    /**
     * @param groupAddress
     *            the groupAddress to set
     */
    public void setGroupAddress(String groupAddress) {
        this.groupAddress = groupAddress;
    }

    /**
     * @return the groupAddressNames
     */
    public String getGroupAddressNames() {
        return groupAddressNames;
    }

    /**
     * @param groupAddressNames
     *            the groupAddressNames to set
     */
    public void setGroupAddressNames(String groupAddressNames) {
        this.groupAddressNames = groupAddressNames;
    }

    /**
     * @return the sender
     */
    public Long getSender() {
        return sender;
    }

    /**
     * @param sender
     *            the sender to set
     */
    public void setSender(Long sender) {
        this.sender = sender;
    }

}
