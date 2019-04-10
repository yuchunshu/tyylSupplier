/*
 * FileName:    OaLaw.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月26日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.briefing.domain;

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
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.oa.briefing.reference.BriefingType;
import cn.com.chaochuang.oa.briefing.reference.BriefingTypeConverter;
import cn.com.chaochuang.oa.notice.reference.DisplayType;
import cn.com.chaochuang.oa.notice.reference.DisplayTypeConverter;
import cn.com.chaochuang.oa.notice.reference.MesFlag;
import cn.com.chaochuang.oa.notice.reference.MesFlagConverter;
import cn.com.chaochuang.oa.notice.reference.SmsFlag;
import cn.com.chaochuang.oa.notice.reference.SmsFlagConverter;

/**
 * @author HM
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "brief_id")) })
public class OaBriefing extends StringUuidEntity {

    private static final long serialVersionUID = -7029856391339470761L;

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

    /** 类别 */
    @Convert(converter = BriefingTypeConverter.class)
    private BriefingType  briefType;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DisplayType getDisplayType() {
		return displayType;
	}

	public void setDisplayType(DisplayType displayType) {
		this.displayType = displayType;
	}

	public PublishType getPublishType() {
		return publishType;
	}

	public void setPublishType(PublishType publishType) {
		this.publishType = publishType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getPublishDepId() {
		return publishDepId;
	}

	public void setPublishDepId(Long publishDepId) {
		this.publishDepId = publishDepId;
	}

	public SysDepartment getPublishDept() {
		return publishDept;
	}

	public void setPublishDept(SysDepartment publishDept) {
		this.publishDept = publishDept;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public StatusFlag getStatus() {
		return status;
	}

	public void setStatus(StatusFlag status) {
		this.status = status;
	}

	public SmsFlag getSmsFlag() {
		return smsFlag;
	}

	public void setSmsFlag(SmsFlag smsFlag) {
		this.smsFlag = smsFlag;
	}

	public MesFlag getMesFlag() {
		return mesFlag;
	}

	public void setMesFlag(MesFlag mesFlag) {
		this.mesFlag = mesFlag;
	}

	public AttachFlag getAttachFlag() {
		return attachFlag;
	}

	public void setAttachFlag(AttachFlag attachFlag) {
		this.attachFlag = attachFlag;
	}

	public BriefingType getBriefType() {
		return briefType;
	}

	public void setBriefType(BriefingType briefType) {
		this.briefType = briefType;
	}

     
    
}
