/*
 * FileName:    EmMain.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月26日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.reference.AttachFlagConverter;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.oa.mail.reference.EmailType;
import cn.com.chaochuang.oa.mail.reference.EmailTypeConverter;
import cn.com.chaochuang.oa.mail.reference.MailMesFlag;
import cn.com.chaochuang.oa.mail.reference.MailMesFlagConverter;
import cn.com.chaochuang.oa.mail.reference.MailStatus;
import cn.com.chaochuang.oa.mail.reference.MailStatusConverter;
import cn.com.chaochuang.oa.mail.reference.ReceiptFlag;
import cn.com.chaochuang.oa.mail.reference.ReceiptFlagConverter;
import cn.com.chaochuang.oa.mail.reference.SaveFlag;
import cn.com.chaochuang.oa.mail.reference.SaveFlagConverter;

/**
 * @author HM
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "email_id")) })
public class EmMain extends StringUuidEntity {

    private static final long serialVersionUID = 3435299782745214012L;

    /** 标题 */
    private String            title;

    /** 内容 */
    private String            content;

    /** 发送人 */
    @ManyToOne
    @JoinColumn(name = "senderId")
    private SysUser           sender;

    /** 保存日期 */
    private Date              saveDate;

    /** 发送日期 */
    private Date              sendDate;

    /** 状态 */
    @Convert(converter = MailStatusConverter.class)
    private MailStatus        status;

    /** 回执:0-否，1-是 */
    @Convert(converter = ReceiptFlagConverter.class)
    private ReceiptFlag       receiptFlag;

    /** 保存：0-不保存，1-保存 */
    @Convert(converter = SaveFlagConverter.class)
    private SaveFlag          saveFlag;

    /** 留言:0-不留言，1-留言 */
    @Convert(converter = MailMesFlagConverter.class)
    private MailMesFlag       mesFlag;

    /** 邮件类型 */
    @Convert(converter = EmailTypeConverter.class)
    private EmailType         emailType;

    /** 地址 : 收件人/或单位IDs */
    private String            address;

    /** 地址名称 : 收件人/或单位名称s */
    private String            addressName;

    /** 有无附件 */
    @Convert(converter = AttachFlagConverter.class)
    private AttachFlag        attachFlag;

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
     * @return the sender
     */
    public SysUser getSender() {
        return sender;
    }

    /**
     * @param sender
     *            the sender to set
     */
    public void setSender(SysUser sender) {
        this.sender = sender;
    }

    /**
     * @return the saveDate
     */
    public Date getSaveDate() {
        return saveDate;
    }

    /**
     * @param saveDate
     *            the saveDate to set
     */
    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    /**
     * @return the sendDate
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * @param sendDate
     *            the sendDate to set
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
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
     * @return the saveFlag
     */
    public SaveFlag getSaveFlag() {
        return saveFlag;
    }

    /**
     * @param saveFlag
     *            the saveFlag to set
     */
    public void setSaveFlag(SaveFlag saveFlag) {
        this.saveFlag = saveFlag;
    }

    /**
     * @return the mesFlag
     */
    public MailMesFlag getMesFlag() {
        return mesFlag;
    }

    /**
     * @param mesFlag
     *            the mesFlag to set
     */
    public void setMesFlag(MailMesFlag mesFlag) {
        this.mesFlag = mesFlag;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the addressName
     */
    public String getAddressName() {
        return addressName;
    }

    /**
     * @param addressName
     *            the addressName to set
     */
    public void setAddressName(String addressName) {
        this.addressName = addressName;
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

    // 获取收件人地址
    public String getIncAdd() {
        String incAdd = null;
        String[] inc_adds = null;
        if (address != null) {
            inc_adds = address.split(",");
            if (inc_adds[0] != null && inc_adds[0].length() != 0) {
                incAdd = inc_adds[0];
            }
        }
        return incAdd;
    }

}
