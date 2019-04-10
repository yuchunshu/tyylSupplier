/**
 * 南宁超创信息工程有限公司
 */
package cn.com.chaochuang.doc.event.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dozer.Mapping;

import cn.com.chaochuang.doc.event.reference.DenseType;
import cn.com.chaochuang.doc.event.reference.FileStatusFlag;

/**
 * @author hzr 2016年2月17日
 */
public class OaDocFileShowBean {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private String           id;

    private String           title;
    private String           docCode;
    private String           sncode;
    private String           fileYear;
    private String           fileCode;
    private String           sendUnit;
    /** 创建日期 */
    private Date             createDate;
    private String           createDateShow;
    /** 创建部门 */
    @Mapping("createrDept.deptName")
    private String           depname;
    /** 创建人 */
    private String           creatorName;

    /** 流程实例ID */
    private String           flowInstId;
    private String           flowId;
    private String           curTaskId;
    private String           curTaskName;
    private String           curTaskKey;
    private String           assignee;
    private FileStatusFlag   status;
    /** 收文/发文 */
    private String           fileType;

    private String           copies;
    /** 密级 */
    private DenseType        dense;

    /** 拟稿信息 */
    private String           createInfo;

    /** 档案编号 */
    @Mapping("archives.archNo")
    private String           archNo;
    /** 档案名称 */
    @Mapping("archives.archName")
    private String           archName;

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
            this.createDateShow = sdf.format(this.createDate);
        }
    }

    /**
     * @return the sdf
     */
    public SimpleDateFormat getSdf() {
        return sdf;
    }

    /**
     * @param sdf
     *            the sdf to set
     */
    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    /**
     * @return the sncode
     */
    public String getSncode() {
        return sncode;
    }

    /**
     * @param sncode
     *            the sncode to set
     */
    public void setSncode(String sncode) {
        this.sncode = sncode;
    }

    /**
     * @return the sendUnit
     */
    public String getSendUnit() {
        return sendUnit;
    }

    /**
     * @param sendUnit
     *            the sendUnit to set
     */
    public void setSendUnit(String sendUnit) {
        this.sendUnit = sendUnit;
    }

    /**
     * @return the depname
     */
    public String getDepname() {
        return depname;
    }

    /**
     * @param depname
     *            the depname to set
     */
    public void setDepname(String depname) {
        this.depname = depname;
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

    public String getCurTaskName() {
        return curTaskName;
    }

    public void setCurTaskName(String curTaskName) {
        this.curTaskName = curTaskName;
    }

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

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getCurTaskKey() {
        return curTaskKey;
    }

    public void setCurTaskKey(String curTaskKey) {
        this.curTaskKey = curTaskKey;
    }

    /**
     * @return the status
     */
    public FileStatusFlag getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(FileStatusFlag status) {
        this.status = status;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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

    public String getCreateInfo() {
        return createInfo;
    }

    public void setCreateInfo(String createInfo) {
        this.createInfo = createInfo;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getArchNo() {
        return archNo;
    }

    public void setArchNo(String archNo) {
        this.archNo = archNo;
    }

    public String getArchName() {
        return archName;
    }

    public void setArchName(String archName) {
        this.archName = archName;
    }

    public String getFileYear() {
        return fileYear;
    }

    public void setFileYear(String fileYear) {
        this.fileYear = fileYear;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

}
