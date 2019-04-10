package cn.com.chaochuang.oa.docs.bean;

import cn.com.chaochuang.oa.docs.reference.DocsType;

public class DocsEditBean {

    // Fields
    private Long     id;
    private String   fileName;
    private String   keyword;
    private DocsType fileType;
    private Long     folderId;

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
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword
     *            the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

}
