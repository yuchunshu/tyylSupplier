/*
 * FileName:    OaDocReceiveFile.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.process.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.doc.event.domain.OaDocFile;

/**
 * @author huangwq
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
@Table(name = "oa_doc_process")
public class DocProcess extends LongIdEntity {

    /**  */
    private static final long serialVersionUID = -1039810004950666578L;
    private String            fileId;
    @ManyToOne
    @JoinColumn(name = "fileId", insertable = false, updatable = false)
    private OaDocFile         docFile;
    private Long              depId;
    private Long              processUserId;
    private String            processUserName;
    private Date              processDate;
    private String            processNumber;
    private String            remark;
    private String            processDepName;
    /** 是否当前办理用户：仅查看 */
    @Transient
    private String            hasPower;

    /**
     * @return the docFile
     */
    public OaDocFile getDocFile() {
        return docFile;
    }

    /**
     * @param docFile
     *            the docFile to set
     */
    public void setDocFile(OaDocFile docFile) {
        this.docFile = docFile;
    }

    /**
     * @return the depId
     */
    public Long getDepId() {
        return depId;
    }

    /**
     * @param depId
     *            the depId to set
     */
    public void setDepId(Long depId) {
        this.depId = depId;
    }

    /**
     * @return the processUserId
     */
    public Long getProcessUserId() {
        return processUserId;
    }

    /**
     * @param processUserId
     *            the processUserId to set
     */
    public void setProcessUserId(Long processUserId) {
        this.processUserId = processUserId;
    }

    /**
     * @return the processUserName
     */
    public String getProcessUserName() {
        return processUserName;
    }

    /**
     * @param processUserName
     *            the processUserName to set
     */
    public void setProcessUserName(String processUserName) {
        this.processUserName = processUserName;
    }

    /**
     * @return the processDate
     */
    public Date getProcessDate() {
        return processDate;
    }

    /**
     * @param processDate
     *            the processDate to set
     */
    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    /**
     * @return the processNumber
     */
    public String getProcessNumber() {
        return processNumber;
    }

    /**
     * @param processNumber
     *            the processNumber to set
     */
    public void setProcessNumber(String processNumber) {
        this.processNumber = processNumber;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     *            the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the processDepName
     */
    public String getProcessDepName() {
        return processDepName;
    }

    /**
     * @param processDepName
     *            the processDepName to set
     */
    public void setProcessDepName(String processDepName) {
        this.processDepName = processDepName;
    }

    public String getHasPower() {
        return hasPower;
    }

    public void setHasPower(String hasPower) {
        this.hasPower = hasPower;
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

}
