package cn.com.chaochuang.doc.archive.bean;

import java.util.Date;

import org.dozer.Mapping;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.archive.domain.DocArchives;

/**
 * @author dengl 2017.11.30
 *
 */
public class DocArchivesDestroyShowBean {
	
	private String 			id;
	
	private String			ids;
	
	/** 公文id*/
	private String 			fileId;
	
	/** 档案id*/
	private Long 			archId;
	private DocArchives		docArchives;
	
	/** 标题*/
	@Mapping("docArchives.title")
	private String			title;
	
	/** 销毁人姓名*/
	private String			destroyUserName;
	
	/** 销毁时间*/
	private Date			destroyDate;
	
	private String			destroyDateShow;
	
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
		if (this.destroyDate != null) {
			this.destroyDateShow = Tools.DATE_FORMAT.format(this.destroyDate);
		}
	}
	
	public String getDestroyDateShow() {
		if (this.destroyDate != null) {
			this.destroyDateShow = Tools.DATE_FORMAT.format(this.destroyDate);
		}
		return destroyDateShow;
	}

	public void setDestroyDateShow(String destroyDateShow) {
		this.destroyDateShow = destroyDateShow;
	}

	public String getDestroyReason() {
		return destroyReason;
	}

	public void setDestroyReason(String destroyReason) {
		this.destroyReason = destroyReason;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
