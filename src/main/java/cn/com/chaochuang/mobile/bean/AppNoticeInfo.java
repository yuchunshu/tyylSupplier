/*
 * FileName:    InfopubShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年2月24日 (zhongli) 1.0 Create
 */

package cn.com.chaochuang.mobile.bean;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.reference.StatusFlag;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 公共发布类
 *
 * @author zhongli
 *
 */
public class AppNoticeInfo {
	@JsonIgnore
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String           id;
    /** 标题 */
    private String           title;

    /** 内容 */
    private String           content;

    /** 发布日期 */
    private Date             publishDate;
    private String           publishDateShow;

    /** 创建人 */
    private Long             creatorId;

    /** 创建人 */
    private String           creatorName;

    /** 状态 */
    private StatusFlag       status;

    /** 附件列表 */
    private List<SysAttach>  attachList;

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
     * @return the publishDate
     */
    public Date getPublishDate() {
        return publishDate;
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
     * @return the creatorName
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * @param creatorName
     *            the creatorName to set
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
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
     * @return the publishDateShow
     */
    public String getPublishDateShow() {
        return publishDateShow;
    }

	public List<SysAttach> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<SysAttach> attachList) {
		this.attachList = attachList;
	}


}
