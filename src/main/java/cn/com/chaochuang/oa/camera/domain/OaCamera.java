/*
 * FileName:    OaCamera.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.camera.domain;

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
import cn.com.chaochuang.common.reference.PublishType;
import cn.com.chaochuang.common.reference.PublishTypeConverter;
import cn.com.chaochuang.common.reference.StatusFlag;
import cn.com.chaochuang.common.reference.StatusFlagConverter;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.oa.camera.reference.CameraType;
import cn.com.chaochuang.oa.camera.reference.CameraTypeConverter;
import cn.com.chaochuang.oa.notice.reference.DisplayType;
import cn.com.chaochuang.oa.notice.reference.DisplayTypeConverter;
import cn.com.chaochuang.oa.notice.reference.MesFlag;
import cn.com.chaochuang.oa.notice.reference.MesFlagConverter;
import cn.com.chaochuang.oa.notice.reference.SmsFlag;
import cn.com.chaochuang.oa.notice.reference.SmsFlagConverter;


/** 
 * @ClassName: OaCamera 
 * @Description: 视频管理
 * @author: chunshu
 * @date: 2017年8月11日 上午9:59:25  
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "camera_id")) })
public class OaCamera extends StringUuidEntity {

    private static final long serialVersionUID = -95726168101117077L;

    /** 标题 */
    private String        title;

    /** 显示类型 */
    @Convert(converter = DisplayTypeConverter.class)
    private DisplayType   displayType;

    /** 发布范围 */
    @Convert(converter = PublishTypeConverter.class)
    private PublishType   publishType;

    /** 内容 */
    private String        content;

    /** 发布部门 */
    private Long          publishDepId;

    @ManyToOne
    @JoinColumn(name = "publishDepId", insertable = false, updatable = false)
    private SysDepartment publishDept;

    /** 创建日期 */
    private Date          createDate;

    /** 创建人ID */
    private Long          creatorId;

    /** 发布日期 */
    private Date          publishDate;

    /** 状态 */
    @Convert(converter = StatusFlagConverter.class)
    private StatusFlag    status;

    /** 短信提示 */
    @Convert(converter = SmsFlagConverter.class)
    private SmsFlag       smsFlag;

    /** 消息提示 */
    @Convert(converter = MesFlagConverter.class)
    private MesFlag       mesFlag;

    /** 有无附件标识 */
    @Convert(converter = AttachFlagConverter.class)
    private AttachFlag    attachFlag;
    
    /** 视频分类 */
    @Convert(converter = CameraTypeConverter.class)
    private CameraType    cameraType;

    /** 视频作者 */
    private String        author;
    
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
     * @return the publishDept
     */
    public SysDepartment getPublishDept() {
        return publishDept;
    }

    /**
     * @param publishDept
     *            the publishDept to set
     */
    public void setPublishDept(SysDepartment publishDept) {
        this.publishDept = publishDept;
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
     * @return the smsFlag
     */
    public SmsFlag getSmsFlag() {
        return smsFlag;
    }

    /**
     * @param smsFlag
     *            the smsFlag to set
     */
    public void setSmsFlag(SmsFlag smsFlag) {
        this.smsFlag = smsFlag;
    }

    /**
     * @return the mesFlag
     */
    public MesFlag getMesFlag() {
        return mesFlag;
    }

    /**
     * @param mesFlag
     *            the mesFlag to set
     */
    public void setMesFlag(MesFlag mesFlag) {
        this.mesFlag = mesFlag;
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

	public CameraType getCameraType() {
		return cameraType;
	}

	public void setCameraType(CameraType cameraType) {
		this.cameraType = cameraType;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


}
