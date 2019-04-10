/*
 * FileName:    OaDocReceiveFile.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.docsigned.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.doc.event.reference.FileStatusFlag;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelType;

/**
 * @author huangwq
 *
 */

public class DocSignedShowBean {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private Long             id;
    // 文件名称
    private String           title;
    // 来文文号
    private String           reportCode;
    // 主办部门
    private String           mainSend;
    // 来文单位
    private String           sendDep;
    // 来文日期
    private Date             receiveDate;

    private String           receiveDateShow;

    // 文件类别
    private Integer          signFileType;
    // 签收日期
    private Date             signDate;

    private String           signDateShow;

    // 签收人
    private Long             signerId;
    // 签收人姓名
    private String           signerName;
    // 签收部门
    private Long             signerDeptId;
    // 签收部门名称
    private String           signerDeptName;
    // 紧急程度
    private UrgencyLevelType urgencyLevel;                            // '紧急程度:1-特急 2-急件 3-无',
    // 状态
    private FileStatusFlag   status;                                  // '状态:1-在办，2-已办结，3-已归档',
    // 摘要
    private String           content;
    // 档案卷ID
    private Long             archId;
    // 是否有附件
    private AttachFlag       attachFlag;                              // '0 无 1 有附件',

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
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
     * @return the reportCode
     */
    public String getReportCode() {
        return reportCode;
    }

    /**
     * @param reportCode
     *            the reportCode to set
     */
    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    /**
     * @return the mainSend
     */
    public String getMainSend() {
        return mainSend;
    }

    /**
     * @param mainSend
     *            the mainSend to set
     */
    public void setMainSend(String mainSend) {
        this.mainSend = mainSend;
    }

    /**
     * @return the sendDep
     */
    public String getSendDep() {
        return sendDep;
    }

    /**
     * @param sendDep
     *            the sendDep to set
     */
    public void setSendDep(String sendDep) {
        this.sendDep = sendDep;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * @param receiveDate
     *            the receiveDate to set
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
        if (this.receiveDate != null) {
            this.receiveDateShow = sdf.format(this.receiveDate);
        }
    }

    /**
     * @return the signFileType
     */
    public Integer getSignFileType() {
        return signFileType;
    }

    /**
     * @param signFileType
     *            the signFileType to set
     */
    public void setSignFileType(Integer signFileType) {
        this.signFileType = signFileType;
    }

    /**
     * @return the signDate
     */
    public Date getSignDate() {
        return signDate;
    }

    /**
     * @param signDate
     *            the signDate to set
     */
    public void setSignDate(Date signDate) {
        this.signDate = signDate;
        if (this.signDate != null) {
            this.signDateShow = sdf.format(this.signDate);
        }
    }

    /**
     * @return the signerId
     */
    public Long getSignerId() {
        return signerId;
    }

    /**
     * @param signerId
     *            the signerId to set
     */
    public void setSignerId(Long signerId) {
        this.signerId = signerId;
    }

    /**
     * @return the signerName
     */
    public String getSignerName() {
        return signerName;
    }

    /**
     * @param signerName
     *            the signerName to set
     */
    public void setSignerName(String signerName) {
        this.signerName = signerName;
    }

    /**
     * @return the signerDeptId
     */
    public Long getSignerDeptId() {
        return signerDeptId;
    }

    /**
     * @param signerDeptId
     *            the signerDeptId to set
     */
    public void setSignerDeptId(Long signerDeptId) {
        this.signerDeptId = signerDeptId;
    }

    /**
     * @return the signerDeptName
     */
    public String getSignerDeptName() {
        return signerDeptName;
    }

    /**
     * @param signerDeptName
     *            the signerDeptName to set
     */
    public void setSignerDeptName(String signerDeptName) {
        this.signerDeptName = signerDeptName;
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
     * @return the archId
     */
    public Long getArchId() {
        return archId;
    }

    /**
     * @param archId
     *            the archId to set
     */
    public void setArchId(Long archId) {
        this.archId = archId;
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


    public String getReceiveDateShow() {
        return receiveDateShow;
    }

    public void setReceiveDateShow(String receiveDateShow) {
        this.receiveDateShow = receiveDateShow;
    }

    /**
     * @return the signDateShow
     */
    public String getSignDateShow() {
        return signDateShow;
    }

    /**
     * @param signDateShow
     *            the signDateShow to set
     */
    public void setSignDateShow(String signDateShow) {
        this.signDateShow = signDateShow;
    }

}
