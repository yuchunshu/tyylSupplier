/*
 * FileName:    MailOutboxAttach.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年6月12日 (LLM) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

/**
 * @author LLM
 *
 */
public class MailAttachInfo {
    /** 保存到收件箱附件 */
    public static final String SAVE_INBOX  = "1";
    /** 保存到发件箱附件 */
    public static final String SAVE_OUTBOX = "0";

    /** 邮件Id */
    private Long               emailId;
    /** 发件箱附件临时编号 */
    private String             tmpAttachId;
    /** 附件保存名 */
    private String             saveName;
    /** 附件大小 */
    private Long               fileSize;
    /** 附件原名 */
    private String             trueName;
    /** 附件保存路径 */
    private String             savePath;
    /** 是否为图片公告 */
    private String             isImage;
    /** 是否本地数据 */
    private String             localData;
    /** 附件保存标识 "1"为收件附件 “0"为发件附件 */
    private String             saveFlag;
    /** 原附件id */
    private String             rmAttachId;

    /**
     * @return the emailId
     */
    public Long getEmailId() {
        return emailId;
    }

    /**
     * @param emailId
     *            the emailId to set
     */
    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    /**
     * @return the tmpAttachId
     */
    public String getTmpAttachId() {
        return tmpAttachId;
    }

    /**
     * @param tmpAttachId
     *            the tmpAttachId to set
     */
    public void setTmpAttachId(String tmpAttachId) {
        this.tmpAttachId = tmpAttachId;
    }

    /**
     * @return the saveName
     */
    public String getSaveName() {
        return saveName;
    }

    /**
     * @param saveName
     *            the saveName to set
     */
    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    /**
     * @return the fileSize
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize
     *            the fileSize to set
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return the trueName
     */
    public String getTrueName() {
        return trueName;
    }

    /**
     * @param trueName
     *            the trueName to set
     */
    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    /**
     * @return the savePath
     */
    public String getSavePath() {
        return savePath;
    }

    /**
     * @param savePath
     *            the savePath to set
     */
    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    /**
     * @return the isImage
     */
    public String getIsImage() {
        return isImage;
    }

    /**
     * @param isImage
     *            the isImage to set
     */
    public void setIsImage(String isImage) {
        this.isImage = isImage;
    }

    /**
     * @return the localData
     */
    public String getLocalData() {
        return localData;
    }

    /**
     * @param localData
     *            the localData to set
     */
    public void setLocalData(String localData) {
        this.localData = localData;
    }

    /**
     * @return the saveFlag
     */
    public String getSaveFlag() {
        return saveFlag;
    }

    /**
     * @param saveFlag
     *            the saveFlag to set
     */
    public void setSaveFlag(String saveFlag) {
        this.saveFlag = saveFlag;
    }

    /**
     * @return the rmAttachId
     */
    public String getRmAttachId() {
        return rmAttachId;
    }

    /**
     * @param rmAttachId
     *            the rmAttachId to set
     */
    public void setRmAttachId(String rmAttachId) {
        this.rmAttachId = rmAttachId;
    }

}
