/*
 * FileName:    InboxShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月4日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dozer.Mapping;

import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.oa.mail.reference.IncepterStatus;

/**
 * @author HM
 *
 */
public class InboxShowBean {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /** 邮件接收 ID */
    private String           id;

    /** 邮件ID */
    private String           emailId;

    /** 主题 */
    @Mapping("mail.title")
    private String           title;

    /** 发件人名称 */
    @Mapping("mail.sender.userName")
    private String           senderName;

    /** 发送时间 */
    @Mapping("mail.sendDate")
    private Date             sendDate;

    private String           sendDateShow;

    @Mapping("status")
    private IncepterStatus   incepterStatus;

    @Mapping("mail.attachFlag")
    private AttachFlag       attachFlag;

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
     * @return the sendDate
     */
    public Date getSendDate() {
        return this.sendDate;
    }

    /**
     * @param sendDate
     *            the sendDate to set
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
        if (this.sendDate != null) {
            this.sendDateShow = this.sdf.format(this.sendDate);
        }
    }

    /**
     * @return the sendDateShow
     */
    public String getSendDateShow() {
        return this.sendDateShow;
    }

    /**
     * @return the incepterStatus
     */
    public IncepterStatus getIncepterStatus() {
        return incepterStatus;
    }

    /**
     * @param incepterStatus
     *            the incepterStatus to set
     */
    public void setIncepterStatus(IncepterStatus incepterStatus) {
        this.incepterStatus = incepterStatus;
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
