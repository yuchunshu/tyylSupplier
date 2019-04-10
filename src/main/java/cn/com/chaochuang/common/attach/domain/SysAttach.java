/*
 * FileName:    SysAttach.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月26日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.attach.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.reference.IsImage;
import cn.com.chaochuang.common.reference.IsImageConverter;

/**
 * @author HM
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "attach_id")) })
public class SysAttach extends StringUuidEntity {

    private static final long serialVersionUID = 1247558557976325910L;

    /** 附件保存名 */
    private String            saveName;

    /** 附件大小 */
    private Long              fileSize;

    /** 附件原名 */
    private String            trueName;

    /** 附件保存路径 */
    private String            savePath;

    /** 是否为图文信息 */
    @Convert(converter = IsImageConverter.class)
    private IsImage           isImage;

    /** 属主对象类型，class&object.simpleName */
    private String            ownerType;

    /** 属主id */
    private String            ownerId;

    /** 附件类型 */
    private String            attachType;

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
    public IsImage getIsImage() {
        return isImage;
    }

    /**
     * @param isImage
     *            the isImage to set
     */
    public void setIsImage(IsImage isImage) {
        this.isImage = isImage;
    }

    /**
     * @return the ownerType
     */
    public String getOwnerType() {
        return ownerType;
    }

    /**
     * @param ownerType
     *            the ownerType to set
     */
    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    /**
     * @return the ownerId
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId
     *            the ownerId to set
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @return the attachType
     */
    public String getAttachType() {
        return attachType;
    }

    /**
     * @param attachType
     *            the attachType to set
     */
    public void setAttachType(String attachType) {
        this.attachType = attachType;
    }

}
