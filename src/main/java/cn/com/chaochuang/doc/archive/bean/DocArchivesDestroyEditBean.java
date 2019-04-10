package cn.com.chaochuang.doc.archive.bean;

import java.util.Date;

import cn.com.chaochuang.doc.archive.domain.DocArchives;

/**
 * @author dengl 2017.11.30
 *
 */
public class DocArchivesDestroyEditBean {
	
	private String 			id;
	
	private String			ids;
	
	/** 公文id*/
	private String 			fileId;
	
	/** 档案id*/
	private Long 			archId;
	private DocArchives		docArchives;
	
	/** 销毁人姓名*/
	private String			destroyUserName;
	
	/** 销毁时间*/
	private Date			destroyDate;
	
	/** 销毁原因*/
	private	String			destroyReason;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Long getArchId() {
		return archId;
	}

	public void setArchId(Long archId) {
		this.archId = archId;
	}

	public DocArchives getDocArchives() {
		return docArchives;
	}

	public void setDocArchives(DocArchives docArchives) {
		this.docArchives = docArchives;
	}

	public String getDestroyUserName() {
		return destroyUserName;
	}

	public void setDestroyUserName(String destroyUserName) {
		this.destroyUserName = destroyUserName;
	}

	public Date getDestroyDate() {
		return destroyDate;
	}

	public void setDestroyDate(Date destroyDate) {
		this.destroyDate = destroyDate;
	}

	public String getDestroyReason() {
		return destroyReason;
	}

	public void setDestroyReason(String destroyReason) {
		this.destroyReason = destroyReason;
	}
}
