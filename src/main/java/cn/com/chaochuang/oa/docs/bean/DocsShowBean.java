package cn.com.chaochuang.oa.docs.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dozer.Mapping;

import cn.com.chaochuang.oa.docs.reference.DocsType;

public class DocsShowBean {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private Long             id;
    private String           fileName;
    private String           keyword;
    private DocsType         fileType;
    private Long             fileSize;
    private Date             createDate;
    private String           createDateShow;
    private String           attachId;
    @Mapping("folder.folderName")
    private String           folderName;

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
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     *            the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the fileType
     */
    public DocsType getFileType() {
        return fileType;
    }

    /**
     * @param fileType
     *            the fileType to set
     */
    public void setFileType(DocsType fileType) {
        this.fileType = fileType;
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
        if (this.createDate != null) {
            this.createDateShow = sdf.format(this.createDate);
        }
    }

    /**
     * @return the createDateShow
     */
    public String getCreateDateShow() {
        return createDateShow;
    }

    /**
     * @return the attachId
     */
    public String getAttachId() {
        return attachId;
    }

    /**
     * @param attachId
     *            the attachId to set
     */
    public void setAttachId(String attachId) {
        this.attachId = attachId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setCreateDateShow(String createDateShow) {
        this.createDateShow = createDateShow;
    }

    /**
     * @return the folderName
     */
    public String getFolderName() {
        return folderName;
    }

    /**
     * @param folderName the folderName to set
     */
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

}
