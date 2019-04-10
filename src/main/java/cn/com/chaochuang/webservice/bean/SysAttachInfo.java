/*
 * FileName:    PubBoardAttach.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2005
 * History:     2005-12-20 (Dzy) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

import java.io.Serializable;

/**
 * 采用共用附件模式，对各种附件进行统一管理
 *
 * @author Jyb
 * @version 1.0, Mar 12, 2009
 */
public class SysAttachInfo implements Serializable {

    /**
     * 对象Id
     */

    /** 附件类型 */
    private String  attachType;
    /** 属主类型 */
    private String  ownerType;
    /** 属主Id */
    private String  ownerId;
    /** 附件流水号 */
    private Long    attachId;
    /** 附件保存名 */
    private String  saveName;
    /** 附件大小 */
    private Integer fileSize;
    /** 附件原名 */
    private String  trueName;
    /** 附件保存路径 */
    private String  savePath;
    /** 是否为图文公告 */
    private String  isImage;
    private String  physicalFile;

    /**
     * @return Returns the attachId.
     */
    public Long getAttachId() {
        return attachId;
    }

    /**
     * @param attachId
     *            The attachId to set.
     */
    public void setAttachId(Long attachId) {
        this.attachId = attachId;
    }

    /**
     * @return Returns the fileSize.
     */
    public Integer getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize
     *            The fileSize to set.
     */
    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return Returns the isImage.
     */
    public String getIsImage() {
        return isImage;
    }

    /**
     * @param isImage
     *            The isImage to set.
     */
    public void setIsImage(String isImage) {
        this.isImage = isImage;
    }

    /**
     * @return Returns the pubBoardId.
     */
    public String getOwnerType() {
        return ownerType;
    }

    /**
     * @param pubBoardId
     *            The pubBoardId to set.
     */
    public void setOwnerType(String pubBoardId) {
        this.ownerType = pubBoardId;
    }

    /**
     * @return Returns the saveName.
     */
    public String getSaveName() {
        return saveName;
    }

    /**
     * @param saveName
     *            The saveName to set.
     */
    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    /**
     * @return Returns the savePath.
     */
    public String getSavePath() {
        return savePath;
    }

    /**
     * @param savePath
     *            The savePath to set.
     */
    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    /**
     * @return Returns the trueName.
     */
    public String getTrueName() {
        return trueName;
    }

    /**
     * @param trueName
     *            The trueName to set.
     */
    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    /**
     * @return Returns the ownerId.
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId
     *            The ownerId to set.
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getAttachType() {
        return attachType;
    }

    public void setAttachType(String attachType) {
        this.attachType = attachType;
    }

    /**
     * @return the physicalFile
     */
    public String getPhysicalFile() {
        return physicalFile;
    }

    /**
     * @param physicalFile
     *            the physicalFile to set
     */
    public void setPhysicalFile(String physicalFile) {
        this.physicalFile = physicalFile;
    }

}
