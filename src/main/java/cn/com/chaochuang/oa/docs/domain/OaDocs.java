package cn.com.chaochuang.oa.docs.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.oa.docs.reference.DocsStatus;
import cn.com.chaochuang.oa.docs.reference.DocsStatusConverter;
import cn.com.chaochuang.oa.docs.reference.DocsType;
import cn.com.chaochuang.oa.docs.reference.DocsTypeConverter;

/**
 * 电子文件管理
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "file_id")) })
public class OaDocs extends LongIdEntity {

    private static final long serialVersionUID = -7472924328308296127L;

    /** 文件名称 */
    private String            fileName;

    /** 文件类型 */
    @Convert(converter = DocsTypeConverter.class)
    private DocsType          fileType;

    /** 文件大小 */
    private Long              fileSize;

    /** 关键字 */
    private String            keyword;

    /** 上传人ID */
    private Long              userId;

    /** 上传日期 */
    private Date              createDate;

    /** 部门ID */
    private Long              depId;

    /** 保存名称 */
    private String            saveName;

    /** 保存路径 */
    private String            savePath;

    /** 文件状态 */
    @Convert(converter = DocsStatusConverter.class)
    private DocsStatus        status;

    /** 文件路径ID */
    private Long              folderId;

    /** 文件目录 */
    @ManyToOne
    @JoinColumn(name = "folderId", insertable = false, updatable = false)
    private DocsFolder        folder;

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

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public DocsType getFileType() {
        return this.fileType;
    }

    public void setFileType(DocsType fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getDepId() {
        return this.depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * @return the status
     */
    public DocsStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(DocsStatus status) {
        this.status = status;
    }

    /**
     * @return the folderId
     */
    public Long getFolderId() {
        return folderId;
    }

    /**
     * @param folderId the folderId to set
     */
    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    /**
     * @return the folder
     */
    public DocsFolder getFolder() {
        return folder;
    }

    /**
     * @param folder the folder to set
     */
    public void setFolder(DocsFolder folder) {
        this.folder = folder;
    }

}