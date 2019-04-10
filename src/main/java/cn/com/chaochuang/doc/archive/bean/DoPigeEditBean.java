/*
 * FileName:    NoticeShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.archive.bean;

import cn.com.chaochuang.doc.archive.reference.FileType;

/**
 * @author HeYunTao
 *
 */
public class DoPigeEditBean {

    /** 档案id */
    private Long     archId;
    /** 文件id */
    private String   fileIds;
    /** 档案类型 */
    private FileType archType;

    /**
     * @return the archId
     */
    public Long getArchId() {
        return archId;
    }

    /**
     * @param archId
     *            the archId to set
     */
    public void setArchId(Long archId) {
        this.archId = archId;
    }

    /**
     * @return the fileIds
     */
    public String getFileIds() {
        return fileIds;
    }

    /**
     * @param fileIds
     *            the fileIds to set
     */
    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    /**
     * @return the archType
     */
    public FileType getArchType() {
        return archType;
    }

    /**
     * @param archType
     *            the archType to set
     */
    public void setArchType(FileType archType) {
        this.archType = archType;
    }

}
