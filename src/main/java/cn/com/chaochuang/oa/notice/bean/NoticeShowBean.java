/*
 * FileName:    NoticeShowBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.notice.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dozer.Mapping;

import cn.com.chaochuang.common.miji.reference.MijiType;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.reference.PublishType;
import cn.com.chaochuang.common.reference.StatusFlag;
import cn.com.chaochuang.oa.notice.reference.DisplayType;

/**
 * @author HM
 *
 */
public class NoticeShowBean {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private String           id;

    /** 标题 */
    private String           title;

    /** 显示类型 */
    private DisplayType      displayType;

    /** 发布部门 */
    @Mapping("publishDept.deptName")
    private String           publishDeptName;

    /** 创建日期 */
    private Date             createDate;

    private String           createDateShow;

    /** 创建人ID */
    private Long             creatorId;

    /** 发布日期 */
    private Date             publishDate;

    private String           publishDateShow;

    /** 状态 */
    private StatusFlag       status;

    /** 有无附件标识 */
    private AttachFlag       attachFlag;

    /** 密级 */
    private MijiType         miji;

    /** 发布范围 */
    private PublishType      publishType;

    /**
     * @return the createDateShow
     */
    public String getCreateDateShow() {
        return createDateShow;
    }

    /**
     * @return the publishDateShow
     */
    public String getPublishDateShow() {
        return publishDateShow;
    }


    public String getId() {
		return id;
	}

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
     * @return the displayType
     */
    public DisplayType getDisplayType() {
        return displayType;
    }

    /**
     * @param displayType
     *            the displayType to set
     */
    public void setDisplayType(DisplayType displayType) {
        this.displayType = displayType;
    }

    /**
     * @return the publishDeptName
     */
    public String getPublishDeptName() {
        return publishDeptName;
    }

    /**
     * @param publishDeptName
     *            the publishDeptName to set
     */
    public void setPublishDeptName(String publishDeptName) {
        this.publishDeptName = publishDeptName;
    }

    /**
     * @param createDate
     *            the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
        if (this.createDate != null) {
            this.createDateShow = sdf.format(this.createDate);
        }
    }

    /**
     * @return the creatorId
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * @param creatorId
     *            the creatorId to set
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * @param publishDate
     *            the publishDate to set
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
        if (this.publishDate != null) {
            this.publishDateShow = sdf.format(this.publishDate);
        }
    }

    /**
     * @return the status
     */
    public StatusFlag getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(StatusFlag status) {
        this.status = status;
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
     * @return the miji
     */
    public MijiType getMiji() {
        return miji;
    }

    /**
     * @param miji
     *            the miji to set
     */
    public void setMiji(MijiType miji) {
        this.miji = miji;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return null;
    }

    /**
     * @return the publishDate
     */
    public Date getPublishDate() {
        return null;
    }

    /**
     * @return the publishType
     */
    public PublishType getPublishType() {
        return publishType;
    }

    /**
     * @param publishType the publishType to set
     */
    public void setPublishType(PublishType publishType) {
        this.publishType = publishType;
    }



}
