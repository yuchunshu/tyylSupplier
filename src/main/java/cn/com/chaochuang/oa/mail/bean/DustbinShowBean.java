/*
 * FileName:    DustbinShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月7日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.user.domain.SysUser;

/**
 * @author HM
 *
 */
public class DustbinShowBean {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /** id */
    private String           id;

    /** 标题 */
    private String           title;

    /** 接收人 */
    private SysUser          incepter;

    /** 发送人 */
    private SysUser          sender;

    /** 邮件ID */
    private String           emailId;

    /** 删除时间 */
    private Date             delDate;

    private String           delDateShow;

    /** 有无附件 */
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
     * @return the delDateShow
     */
    public String getDelDateShow() {
        return delDateShow;
    }

}
