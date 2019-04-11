/*
 * FileName:    EmAttach.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月26日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;

/**
 * @author HM
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "attach_id")) })
public class EmAttach extends StringUuidEntity {

    private static final long serialVersionUID = 4437761536033395865L;

    /** 附件保存名 */
    private String            saveName;

    /** 附件大小 */
    private Long              fileSize;

    /** 附件原名 */
    private String            trueName;

    /** 附件保存路径 */
    private String            savePath;

    /** 邮件ID */
    private String            emailId;

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
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId
     *            the emailId to set
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

}
