package cn.com.chaochuang.doc.archive.bean;

import java.util.Date;

import cn.com.chaochuang.common.util.Tools;

/**
 * @author dengl 2017.12.07
 *
 */

public class LuceneShowBean {
	
	/** 档案ID*/
	private String 		id;
	
	/**公文ID*/
	private String 		fileId;
	
	/** 公文名称 */
    private String 		fileName;
    
    /**公文类型*/
    private String 		fileType;
    
    /** 命中的检索内容 */
    private String 		hitContent;
    
    /** 归档日期 */
    private Date        createDate;
    
    private String 		createDateShow;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getHitContent() {
		return hitContent;
	}

	public void setHitContent(String hitContent) {
		this.hitContent = hitContent;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		if (this.createDate != null) {
			this.createDateShow = Tools.DATE_FORMAT.format(this.createDate);
		}
	}

	public String getCreateDateShow() {
		if (this.createDate != null) {
			this.createDateShow = Tools.DATE_FORMAT.format(this.createDate);
		}
		return createDateShow;
	}

	public void setCreateDateShow(String createDateShow) {
		this.createDateShow = createDateShow;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
