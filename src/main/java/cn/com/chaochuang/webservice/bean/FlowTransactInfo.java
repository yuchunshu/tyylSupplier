/*
 * FileName:    FlowTransactInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     HeYunTao 2016-07-04 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

import java.util.Date;

/**
 * @author HeYunTao 2016-07-04
 */
public class FlowTransactInfo {

    /**
     *
     */
    /** 标题 */
    private String title;
    /** 原系统公文办理人编号 */
    private Long   rmTransactId;
    /** 公文创建时间 */
    private Date   createTime;
    /** 公文创建人姓名 */
    private String creatorName;
    /** 原系统公文编号 */
    private String rmFileId;
    /** 公文类别 */
    private String fileType;
    /** 数据导入时间 */
    private Date   inputTime;
    /** 公文创建人部门 */
    private String creatorDeptName;

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
     * @return the rmTransactId
     */
    public Long getRmTransactId() {
        return rmTransactId;
    }

    /**
     * @param rmTransactId
     *            the rmTransactId to set
     */
    public void setRmTransactId(Long rmTransactId) {
        this.rmTransactId = rmTransactId;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
     * @return the rmFileId
     */
    public String getRmFileId() {
        return rmFileId;
    }

    /**
     * @param rmFileId
     *            the rmFileId to set
     */
    public void setRmFileId(String rmFileId) {
        this.rmFileId = rmFileId;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType
     *            the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return the inputTime
     */
    public Date getInputTime() {
        return inputTime;
    }

    /**
     * @param inputTime
     *            the inputTime to set
     */
    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    /**
     * @return the creatorDeptName
     */
    public String getCreatorDeptName() {
        return creatorDeptName;
    }

    /**
     * @param creatorDeptName
     *            the creatorDeptName to set
     */
    public void setCreatorDeptName(String creatorDeptName) {
        this.creatorDeptName = creatorDeptName;
    }

}
