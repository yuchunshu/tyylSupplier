/*
 * FileName:    FileShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月24日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.event.bean;

import java.util.Date;

import org.dozer.Mapping;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.event.reference.DenseType;
import cn.com.chaochuang.doc.event.reference.FileStatusFlag;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelType;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * @author huangwq
 *
 */
public class FileShowBean {
    private String           id;
    /** 标题 */
    private String           title;
    /** 文号 */
    private String           docCode;
    /** 编号 */
    private String           fileCode;
    /** 创建日期 */
    private Date             createDate;
    private String           createDateShow;
    /** 拟稿信息 */
    private String           createInfo;
    /** 处理时限 */
    private Date             expiryDate;
    private String           expiryDateShow;

    /** 流程实例ID */
    private String           flowInstId;
    private String           flowId;
    private String           curTaskId;
    // private String curTaskName;
    // private String curTaskKey;
    // private String assignee;
    /** 文件类型 */
    private WfBusinessType   fileType;
    @Mapping("fileType.key")
    private String           fileTypeKey;

    /** 紧急程度 */
    private UrgencyLevelType urgencyLevel;

    private String           copies;

    /** 密级 */
    private DenseType        dense;

    /** 创建人 */
    private String           creatorName;

    private FileStatusFlag   status;

    /** 编 号，自然编号，如zh2017-0006 */
    private String           sncode;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
        if (this.createDate != null) {
            this.createDateShow = Tools.DATE_FORMAT.format(this.createDate);
        }
    }

    public String getCreateInfo() {
        return createInfo;
    }

    public void setCreateInfo(String createInfo) {
        this.createInfo = createInfo;
    }

    public String getFlowInstId() {
        return flowInstId;
    }

    public void setFlowInstId(String flowInstId) {
        this.flowInstId = flowInstId;
    }

    public String getCreateDateShow() {
        return createDateShow;
    }

    public void setCreateDateShow(String createDateShow) {
        this.createDateShow = createDateShow;
    }

    // public String getCurTaskName() {
    // return curTaskName;
    // }
    //
    // public void setCurTaskName(String curTaskName) {
    // this.curTaskName = curTaskName;
    // }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getCurTaskId() {
        return curTaskId;
    }

    public void setCurTaskId(String curTaskId) {
        this.curTaskId = curTaskId;
    }
    //
    // public String getAssignee() {
    // return assignee;
    // }
    //
    // public void setAssignee(String assignee) {
    // this.assignee = assignee;
    // }
    //
    // public String getCurTaskKey() {
    // return curTaskKey;
    // }
    //
    // public void setCurTaskKey(String curTaskKey) {
    // this.curTaskKey = curTaskKey;
    // }

    public WfBusinessType getFileType() {
        return fileType;
    }

    public void setFileType(WfBusinessType fileType) {
        this.fileType = fileType;
    }

    /**
     * @return the expiryDate
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * @param expiryDate
     *            the expiryDate to set
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
        if (this.expiryDate != null) {
            this.expiryDateShow = Tools.DATE_FORMAT.format(this.expiryDate);
        }
    }

    /**
     * @return the expiryDateShow
     */
    public String getExpiryDateShow() {
        return expiryDateShow;
    }

    /**
     * @param expiryDateShow
     *            the expiryDateShow to set
     */
    public void setExpiryDateShow(String expiryDateShow) {
        this.expiryDateShow = expiryDateShow;
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
     * @return the urgencyLevel
     */
    public UrgencyLevelType getUrgencyLevel() {
        return urgencyLevel;
    }

    /**
     * @param urgencyLevel
     *            the urgencyLevel to set
     */
    public void setUrgencyLevel(UrgencyLevelType urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    /**
     * @return the dense
     */
    public DenseType getDense() {
        return dense;
    }

    /**
     * @param dense
     *            the dense to set
     */
    public void setDense(DenseType dense) {
        this.dense = dense;
    }

    /**
     * @return the copies
     */
    public String getCopies() {
        return copies;
    }

    /**
     * @param copies
     *            the copies to set
     */
    public void setCopies(String copies) {
        this.copies = copies;
    }

    public FileStatusFlag getStatus() {
        return status;
    }

    public void setStatus(FileStatusFlag status) {
        this.status = status;
    }

    public String getFileTypeKey() {
        return fileTypeKey;
    }

    public void setFileTypeKey(String fileTypeKey) {
        this.fileTypeKey = fileTypeKey;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

	public String getSncode() {
		return sncode;
	}

	public void setSncode(String sncode) {
		this.sncode = sncode;
	}

}
