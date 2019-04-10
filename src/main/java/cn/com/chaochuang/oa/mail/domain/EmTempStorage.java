/*
 * FileName:    EmDustbin.java
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
import cn.com.chaochuang.oa.mail.reference.IsNeedback;
import cn.com.chaochuang.oa.mail.reference.IsNeedbackConverter;

/**
 * @author HM
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "tmp_id")) })
public class EmTempStorage extends StringUuidEntity {

    private static final long serialVersionUID = -4752543068535665386L;

    /** 标题 */
    private String            title;

    /** 接收人 */
    @ManyToOne
    @JoinColumn(name = "incepterId")
    private SysUser           incepter;

    /** 发送人 */
    @ManyToOne
    @JoinColumn(name = "senderId")
    private SysUser           sender;

    /** 内容 */
    private String            content;

    /** 发送日期 */
    private Date              sendDate;

    /** 阅读日期 */
    private Date              readDate;

    /** 暂存时间 */
    private Date              pigeDate;

    /** 邮件ID */
    private String            emailId;

    /** 是否需要回执 */
    @Convert(converter = IsNeedbackConverter.class)
    private IsNeedback        isNeedback;

    /** 回复时间 */
    private Date              writebackDate;

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
     * @return the incepter
     */
    public SysUser getIncepter() {
        return incepter;
    }

    /**
     * @param incepter
     *            the incepter to set
     */
    public void setIncepter(SysUser incepter) {
        this.incepter = incepter;
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
     * @return the readDate
     */
    public Date getReadDate() {
        return readDate;
    }

    /**
     * @param readDate
     *            the readDate to set
     */
    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    /**
     * @return the pigeDate
     */
    public Date getPigeDate() {
        return pigeDate;
    }

    /**
     * @param pigeDate
     *            the pigeDate to set
     */
    public void setPigeDate(Date pigeDate) {
        this.pigeDate = pigeDate;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId
     *            the emailId to set
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * @return the isNeedback
     */
    public IsNeedback getIsNeedback() {
        return isNeedback;
    }

    /**
     * @param isNeedback
     *            the isNeedback to set
     */
    public void setIsNeedback(IsNeedback isNeedback) {
        this.isNeedback = isNeedback;
    }

    /**
     * @return the writebackDate
     */
    public Date getWritebackDate() {
        return writebackDate;
    }

    /**
     * @param writebackDate
     *            the writebackDate to set
     */
    public void setWritebackDate(Date writebackDate) {
        this.writebackDate = writebackDate;
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

}
