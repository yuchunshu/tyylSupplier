/*
 * FileName:    OaDocReceiveFile.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.readmatter.bean;

import java.util.Date;

import cn.com.chaochuang.common.reference.ReadStatus;

/**
 * @author huangwq
 *
 */

public class ReadMatterEditBean {

    /**
     *
     */
    private String       id;
    private String       sendMan;
    private Integer      sendTime;
    private Long         readMan;
    private Date         readTime;
    private String       fileId;
    private String       opinion;
    private ReadStatus   status;
    // 任务id
    private String       taskId;



    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
     * @return the sendMan
     */
    public String getSendMan() {
        return sendMan;
    }

    /**
     * @param sendMan
     *            the sendMan to set
     */
    public void setSendMan(String sendMan) {
        this.sendMan = sendMan;
    }

    /**
     * @return the sendTime
     */
    public Integer getSendTime() {
        return sendTime;
    }

    /**
     * @param sendTime
     *            the sendTime to set
     */
    public void setSendTime(Integer sendTime) {
        this.sendTime = sendTime;
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

}
