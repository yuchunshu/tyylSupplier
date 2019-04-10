package cn.com.chaochuang.webservice.bean;

public class PolicyAttachInfo {
    /** 附件保存名 */
    private String saveName;
    /** 附件大小 */
    private Long   fileSize;
    /** 附件原名 */
    private String trueName;
    /** 附件保存路径 */
    private String savePath;
    /** 是否为图片公告 */
    private String isImage;
    /** 原远程法律法规id */
    private String rmPolicyId;

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
     * @return the rmAttachId
     */
    public String getRmPolicyId() {
        return rmPolicyId;
    }

    /**
     * @param rmAttachId
     *            the rmAttachId to set
     */
    public void setRmPolicyId(String rmPolicyId) {
        this.rmPolicyId = rmPolicyId;
    }

}
