/*
 * FileName:    LawEditBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月28日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.briefing.bean;

import java.util.Date;

import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.reference.PublishType;
import cn.com.chaochuang.common.reference.StatusFlag;
import cn.com.chaochuang.oa.briefing.reference.BriefingType;
import cn.com.chaochuang.oa.notice.reference.DisplayType;

/**
 * @author HM
 *
 */
public class BriefingEditBean {

    /** ID */
    private String      id;

    /** 标题 */
    private String       title;

    /** 显示类型 */
    private DisplayType  displayType;

    /** 发布部门Id */
    private Long         publishDepId;

    /** 发布范围 */
    private PublishType  publishType;

    /** 创建日期 */
    private Date         createDate;

    /** 创建人ID */
    private Long         creatorId;

    /** 发布日期 */
    private Date         publishDate;

    /** 状态 */
    private StatusFlag   status;

    /** 有无附件标识 */
    private AttachFlag   attachFlag;

    /** 内容 */
    private String       content;

    /** 类别 */
    private BriefingType briefType;

    /** 附件IDs 通过','分割 */
    private String     attach;

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

    public DisplayType getDisplayType() {
		return displayType;
	}

	public void setDisplayType(DisplayType displayType) {
		this.displayType = displayType;
	}

	public Long getPublishDepId() {
		return publishDepId;
	}

	public void setPublishDepId(Long publishDepId) {
		this.publishDepId = publishDepId;
	}

	public PublishType getPublishType() {
		return publishType;
	}

	public void setPublishType(PublishType publishType) {
		this.publishType = publishType;
	}

	/**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     *            the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the publishDate
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * @param publishDate
     *            the publishDate to set
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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

    public BriefingType getBriefType() {
		return briefType;
	}

	public void setBriefType(BriefingType briefType) {
		this.briefType = briefType;
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
     * @return the attach
     */
    public String getAttach() {
        return attach;
    }

    /**
     * @param attach
     *            the attach to set
     */
    public void setAttach(String attach) {
        this.attach = attach;
    }

}
