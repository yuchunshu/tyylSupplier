/*
 * FileName:    RemoteDocSwapAttach.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月21日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.domain;

import java.io.File;
import java.io.IOException;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.upload.support.UploadManager;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.remotedocswap.DocSwapTools;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteAttachType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteAttachTypeConverter;

/**
 * @author yuandl
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "attach_id")) })
@Table(name = "remote_doc_swap_attach")
public class RemoteDocSwapAttach extends LongIdEntity {
    /**  */
    private static final long  serialVersionUID  = 1514947913908488632L;
    /** 附件类型：正文 */
    public static final String ATTACH_TYPE_DOC   = "doc";
    /** 附件类型：其他 */
    public static final String ATTACH_TYPE_OTHER = "other";

    /** 封体记录编号 */
    private Long               remoteDocSwapContentId;
    // /** 文件Base64编码 */
    // private String fileBase64;
    /** 附件类型 */
    @Convert(converter = RemoteAttachTypeConverter.class)
    private RemoteAttachType   remoteAttachType;

    /** 附件保存名 */
    private String             saveName;
    /** 附件大小 */
    private Long               fileSize;
    /** 附件原名 */
    private String             trueName;
    /** 附件保存路径 */
    private String             savePath;

	//文件md5值
    @Transient
	private String fileMdf;
	
    // /** 是否为图文公告 */
    // private String isImage;
    /**
     * @return the remoteDocSwapContentId
     */
    public Long getRemoteDocSwapContentId() {
        return remoteDocSwapContentId;
    }

    /**
     * @param remoteDocSwapContentId
     *            the remoteDocSwapContentId to set
     */
    public void setRemoteDocSwapContentId(Long remoteDocSwapContentId) {
        this.remoteDocSwapContentId = remoteDocSwapContentId;
    }

    /**
     * @return the remoteAttachType
     */
    public RemoteAttachType getRemoteAttachType() {
        return remoteAttachType;
    }

    /**
     * @param remoteAttachType
     *            the remoteAttachType to set
     */
    public void setRemoteAttachType(RemoteAttachType remoteAttachType) {
        this.remoteAttachType = remoteAttachType;
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
     * @return 完整的文件路径及文件名
     */
    public String getPhysicalFile() {
        return UploadManager.getInstance().getUploadRootPath() + "/" + this.savePath + this.saveName;
    }
    
    /**
     * 获取文件后缀名
     * @return
     */
    public String getAffixSuffix(){
    	return DocSwapTools.getFileSuffix(saveName);
    }
    
    public String getFileMdf() {
		String md5="";
		if(!Tools.isEmptyString(getPhysicalFile())){
			File file = new File(getPhysicalFile());
			try {
				md5 = DocSwapTools.getFileMd5Code(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return md5;
	}
}
