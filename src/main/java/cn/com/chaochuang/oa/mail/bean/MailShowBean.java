/*
 * FileName:    MailShowBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年3月3日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dozer.Mapping;

import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.oa.mail.reference.ReceiptFlag;

/**
 * @author HM
 *
 */
public class MailShowBean {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /** id */
    private String           id;

    /** 标题 */
    private String           title;

    /** 发送人 */
    @Mapping("sender.userName")
    private String           senderName;

    /** 保存日期 */
    private Date             saveDate;
    private String           saveDateShow;

    /** 发送日期 */
    private Date             sendDate;
    private String           sendDateShow;

    /** 删除时间 */
    private Date             delDate;
    private String           delDateShow;

    /** 回执:0-否，1-是 */
    private ReceiptFlag      receiptFlag;

    /** 地址名称 : 收件人/或单位名称s */
    private String           addressName;

    /** 有无附件 */
    private AttachFlag       attachFlag;

    /** 阅读日期 */
    private Date             readDate;

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
     * @return the senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * @param senderName
     *            the senderName to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * @return the saveDate
     */
    public Date getSaveDate() {
        return null;
    }

    /**
     * @param saveDate
     *            the saveDate to set
     */
    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
        if (this.saveDate != null) {
            this.saveDateShow = sdf.format(this.saveDate);
        }
    }

    /**
     * @return the sendDate
     */
    public Date getSendDate() {
        return null;
    }

    /**
     * @param sendDate
     *            the sendDate to set
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
        if (this.sendDate != null) {
            this.sendDateShow = sdf.format(this.sendDate);
        }
    }

    /**
     * @return the delDate
     */
    public Date getDelDate() {
        return null;
    }

    /**
     * @param delDate
     *            the delDate to set
     */
    public void setDelDate(Date delDate) {
        this.delDate = delDate;
        if (this.delDate != null) {
            this.delDateShow = sdf.format(this.delDate);
        }
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
     * @return the saveDateShow
     */
    public String getSaveDateShow() {
        return saveDateShow;
    }

    /**
     * @return the sendDateShow
     */
    public String getSendDateShow() {
        return sendDateShow;
    }

    /**
     * @return the delDateShow
     */
    public String getDelDateShow() {
        return delDateShow;
    }

}
