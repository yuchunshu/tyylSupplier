/*
 * FileName:    OaDocReceiveFile.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.readmatter.bean;

import java.util.Date;

import org.dozer.Mapping;

import cn.com.chaochuang.common.reference.ReadStatus;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * @author huangwq
 *
 */

public class ReadMatterShowBean {
    private String      id;
    private Long        sendMan;

    @Mapping("sendUser.userName")
    private String      sendManName;

    private Date        sendTime;
    private String      sendTimeShow;
    private Long        readMan;
    private Date        readTime;
    private String      readTimeShow;
    private String      fileId;
    private String      opinion;
    private ReadStatus  status;
    private String      title;
    private String      taskId;
    private WfBusinessType fileType;



    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
     * @return the sendMan
     */
    public Long getSendMan() {
        return sendMan;
    }

    /**
     * @param sendMan
     *            the sendMan to set
     */
    public void setSendMan(Long sendMan) {
        this.sendMan = sendMan;
    }

    /**
     * @return the sendManName
     */
    public String getSendManName() {
        return sendManName;
    }

    /**
     * @param sendManName
     *            the sendManName to set
     */
    public void setSendManName(String sendManName) {
        this.sendManName = sendManName;
    }

    /**
     * @return the sendTime
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * @param sendTime
     *            the sendTime to set
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
        if (this.sendTime != null) {
            this.sendTimeShow = Tools.DATE_MINUTE_FORMAT.format(this.sendTime);
        }
    }

    /**
     * @return the readMan
     */
    public Long getReadMan() {
        return readMan;
    }

    /**
     * @param readMan
     *            the readMan to set
     */
    public void setReadMan(Long readMan) {
        this.readMan = readMan;
    }

    /**
     * @return the readTime
     */
    public Date getReadTime() {
        return readTime;
    }

    /**
     * @param readTime
     *            the readTime to set
     */
    public void setReadTime(Date readTime) {
        this.readTime = readTime;
        if (this.readTime != null) {
            this.readTimeShow = Tools.DATE_MINUTE_FORMAT.format(this.readTime);
        }
    }

    /**
     * @return the fileId
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * @param fileId
     *            the fileId to set
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * @return the opinion
     */
    public String getOpinion() {
        return opinion;
    }

    /**
     * @param opinion
     *            the opinion to set
     */
    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public ReadStatus getStatus() {
        return status;
    }

    public void setStatus(ReadStatus status) {
        this.status = status;
    }

    /**
     * @return the sendTimeShow
     */
    public String getSendTimeShow() {
        return sendTimeShow;
    }

    /**
     * @param sendTimeShow
     *            the sendTimeShow to set
     */
    public void setSendTimeShow(String sendTimeShow) {
        this.sendTimeShow = sendTimeShow;
    }

    /**
     * @return the readTimeShow
     */
    public String getReadTimeShow() {
        return readTimeShow;
    }

    /**
     * @param readTimeShow
     *            the readTimeShow to set
     */
    public void setReadTimeShow(String readTimeShow) {
        this.readTimeShow = readTimeShow;
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
     * @return the taskId
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId
     *            the taskId to set
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public WfBusinessType getFileType() {
        return fileType;
    }

    public void setFileType(WfBusinessType fileType) {
        this.fileType = fileType;
    }

}
