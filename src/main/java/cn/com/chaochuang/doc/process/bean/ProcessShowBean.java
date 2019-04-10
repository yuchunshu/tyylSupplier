/*
 * FileName:    NoticeShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.process.bean;

import java.util.Date;

import javax.persistence.Convert;

import org.apache.commons.lang.StringUtils;
import org.dozer.Mapping;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.event.reference.FileStatusFlag;
import cn.com.chaochuang.doc.event.reference.FileStatusFlagConverter;

/**
 * @author huangwq
 *
 */
public class ProcessShowBean {
    private Long           id;
    @Mapping("docFile.id")
    private String         fileId;
    private Long           depId;
    private Long           processUserId;
    private String         processUserName;
    private Date           processDate;
    private String         processShowDate;
    private String         processNumber;
    private String         remark;
    private String         processDepName;
    @Mapping("docFile.title")
    private String         title;
    @Mapping("docFile.fileType")
    private String         fileType;
    @Mapping("docFile.docCode")
    private String         docCode;
    @Mapping("docFile.sncode")
    private String         sncode;
    @Mapping("docFile.sendUnit")
    private String         sendUnit;
    private String         receiveShowDate;
    @Mapping("docFile.status")
    @Convert(converter = FileStatusFlagConverter.class)
    private FileStatusFlag status;
    /** 创建日期 */
    @Mapping("docFile.createDate")
    private Date           createDate;
    @Mapping("docFile.createDateShow")
    private String         createDateShow;
    @Mapping("docFile.expiryDate")
    private Date           expiryDate;
    private String         expiryDateShow;
    /** 是否当前办理用户：仅查看 */
    private String         hasPower;
    /** 超期信息 */
    private String         message;

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
        if (this.processDate != null) {
            this.processShowDate = Tools.DATE_FORMAT.format(this.processDate);
        }
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
     * @return the docCode
     */
    public String getDocCode() {
        return docCode;
    }

    /**
     * @param docCode
     *            the docCode to set
     */
    public void setDocCode(String docCode) {
        this.docCode = docCode;
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
     * @return the processShowDate
     */
    public String getProcessShowDate() {
        return processShowDate;
    }

    /**
     * @param processShowDate
     *            the processShowDate to set
     */
    public void setProcessShowDate(String processShowDate) {
        this.processShowDate = processShowDate;
    }

    /**
     * @return the receiveShowDate
     */
    public String getReceiveShowDate() {
        return receiveShowDate;
    }

    /**
     * @param receiveShowDate
     *            the receiveShowDate to set
     */
    public void setReceiveShowDate(String receiveShowDate) {
        this.receiveShowDate = receiveShowDate;
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
     * @return the createDateShow
     */
    public String getCreateDateShow() {
        if (this.createDate != null) {
            this.createDateShow = Tools.DATE_FORMAT.format(this.createDate);
        }
        return createDateShow;
    }

    /**
     * @param createDateShow
     *            the createDateShow to set
     */
    public void setCreateDateShow(String createDateShow) {
        this.createDateShow = createDateShow;
    }

    public String getMessage() {
        if (StringUtils.isBlank(this.message)) {
            if (FileStatusFlag.在办.equals(getStatus())) {
                // 在办状态计算过期信息
                if (this.expiryDate != null) {
                    Date now = new Date();
                    // 计算超期信息
                    Integer days = Tools.countWorkingDay(this.getExpiryDate(), now);
                    if (days <= 0) {
                        this.message = "（已过期限）";
                    } else {
                        this.message = "（" + days + "工作日）";
                    }
                }
            }
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getExpiryDateShow() {
        if (StringUtils.isBlank(expiryDateShow)) {
            if (expiryDate != null) {
                this.expiryDateShow = Tools.DATE_FORMAT.format(this.expiryDate);
            }
        }
        return expiryDateShow;
    }

    public void setExpiryDateShow(String expiryDateShow) {
        this.expiryDateShow = expiryDateShow;
    }

    public String getHasPower() {
        return hasPower;
    }

    public void setHasPower(String hasPower) {
        this.hasPower = hasPower;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

}
