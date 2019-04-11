/*
 * FileName:    NoticeShowBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.notice.bean;

import java.util.Date;

import cn.com.chaochuang.common.miji.reference.MijiType;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.reference.PublishType;
import cn.com.chaochuang.common.reference.StatusFlag;
import cn.com.chaochuang.oa.notice.reference.DisplayType;

/**
 * @author HM
 *
 */
public class NoticeEditBean {

    private String      id;

    /** 标题 */
    private String      title;

    /** 显示类型 */
    private DisplayType displayType;

    /** 发布部门Id */
    private Long        publishDepId;

    /** 发布范围 */
    private PublishType publishType;

    /** 创建日期 */
    private Date        createDate;

    /** 创建人ID */
    private Long        creatorId;

    /** 发布日期 */
    private Date        publishDate;

    /** 状态 */
    private StatusFlag  status;

    /** 有无附件标识 */
    private AttachFlag  attachFlag;

    /** 内容 */
    private String      content;

    /** 附件IDs 通过','分割 */
    private String      attach;

    /** 密级 */
    private MijiType    miji;



    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
     * @return the publishType
     */
    public PublishType getPublishType() {
        return publishType;
    }

    /**
     * @param publishType
     *            the publishType to set
     */
    public void setPublishType(PublishType publishType) {
        this.publishType = publishType;
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
     * @return the publishDepId
     */
    public Long getPublishDepId() {
        return publishDepId;
    }

    /**
     * @param publishDepId
     *            the publishDepId to set
     */
    public void setPublishDepId(Long publishDepId) {
        this.publishDepId = publishDepId;
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

}
