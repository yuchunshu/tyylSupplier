/*
 * FileName:    EmDustbin.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
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
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.oa.mail.reference.DelFlag;
import cn.com.chaochuang.oa.mail.reference.DelFlagConverter;
import cn.com.chaochuang.oa.mail.reference.IncType;
import cn.com.chaochuang.oa.mail.reference.IncTypeConverter;
import cn.com.chaochuang.oa.mail.reference.IncepterStatus;
import cn.com.chaochuang.oa.mail.reference.IncepterStatusConverter;

/**
 * @author HM
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
public class EmIncepter extends StringUuidEntity {

    private static final long serialVersionUID = -8480977510071328453L;

    /** 邮件ID */
    private String            emailId;

    @ManyToOne
    @JoinColumn(name = "emailId", insertable = false, updatable = false)
    private EmMain            mail;

    /** 阅读日期 */
    private Date              readDate;

    /** 回复时间 */
    private Date              writebackDate;

    /** 接收人 */
    @ManyToOne
    @JoinColumn(name = "incepterId")
    private SysUser           incepter;

    /** 0普通收件人 1抄送收件人 2密送收件人 */
    @Convert(converter = IncTypeConverter.class)
    private IncType           incType;

    /** 回复ID */
    private Long              writebackId;

    /** 删除标识 */
    @Convert(converter = DelFlagConverter.class)
    private DelFlag           delFlag;

    /** 0 未处理 1 存档 2 垃圾 3 已收件 */
    @Convert(converter = IncepterStatusConverter.class)
    private IncepterStatus    status;

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
     * @return the incType
     */
    public IncType getIncType() {
        return incType;
    }

    /**
     * @param incType
     *            the incType to set
     */
    public void setIncType(IncType incType) {
        this.incType = incType;
    }

    /**
     * @return the writebackId
     */
    public Long getWritebackId() {
        return writebackId;
    }

    /**
     * @param writebackId
     *            the writebackId to set
     */
    public void setWritebackId(Long writebackId) {
        this.writebackId = writebackId;
    }

    /**
     * @return the delFlag
     */
    public DelFlag getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     *            the delFlag to set
     */
    public void setDelFlag(DelFlag delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * @return the status
     */
    public IncepterStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(IncepterStatus status) {
        this.status = status;
    }

    /**
     * @return the mail
     */
    public EmMain getMail() {
        return mail;
    }

    /**
     * @param mail
     *            the mail to set
     */
    public void setMail(EmMain mail) {
        this.mail = mail;
    }

}
