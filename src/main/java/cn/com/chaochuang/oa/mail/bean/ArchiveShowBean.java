/*
 * FileName:    ArchiveShowBean.java
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

/**
 * @author HM
 *
 */
public class ArchiveShowBean {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /** id */
    private String           id;

    /** 标题 */
    private String           title;

    /** 发送人 */
    @Mapping("sender.userName")
    private String           senderName;

    /** 保存日期 */
    private Date             pigeDate;
    private String           pigeDateShow;

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
     * @return the pigeDate
     */
    public Date getPigeDate() {
        return null;
    }

    /**
     * @param pigeDate
     *            the pigeDate to set
     */
    public void setPigeDate(Date pigeDate) {
        this.pigeDate = pigeDate;
        if (this.pigeDate != null) {
            this.pigeDateShow = sdf.format(this.pigeDate);
        }
    }

    /**
     * @return the pigeDateShow
     */
    public String getPigeDateShow() {
        return pigeDateShow;
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
