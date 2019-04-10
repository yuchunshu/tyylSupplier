/*
 * FileName:    PubInfoAttachInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年6月27日 (Administrator@USER-20150531ET) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

/**
 * @author Administrator@USER-20150531ET
 *
 */
public class PubInfoAttachInfo {
    /** 附件保存名 */
    private String saveName;
    private Long   attachId;
    /** 附件原名 */
    private String trueName;
    /** 附件大小 */
    private Long   fileSize;
    /** 是否为图片公告 */
    private String isImage;
    /** 附件保存路径 */
    private String savePath;

    private Long   pubInfoId;

    private char   localData;

    private Long   rmAttachId;

    /**
     * @return the rmAttachId
     */
    public Long getRmAttachId() {
        return rmAttachId;
    }

    /**
     * @param rmAttachId
     *            the rmAttachId to set
     */
    public void setRmAttachId(Long rmAttachId) {
        this.rmAttachId = rmAttachId;
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
     * @return the attachId
     */
    public Long getAttachId() {
        return attachId;
    }

    /**
     * @param attachId
     *            the attachId to set
     */
    public void setAttachId(Long attachId) {
        this.attachId = attachId;
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
     * @return the pubInfoId
     */
    public Long getPubInfoId() {
        return pubInfoId;
    }

    /**
     * @param pubInfoId
     *            the pubInfoId to set
     */
    public void setPubInfoId(Long pubInfoId) {
        this.pubInfoId = pubInfoId;
    }

    /**
     * @return the localData
     */
    public char getLocalData() {
        return localData;
    }

    /**
     * @param localData
     *            the localData to set
     */
    public void setLocalData(char localData) {
        this.localData = localData;
    }

}
