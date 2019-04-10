package cn.com.chaochuang.doc.archive.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;

/**
 * @author dengl 2017.11.30
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "destroy_id")) })
@Table(name = "oa_doc_archives_destroy")
public class DocArchivesDestroy extends StringUuidEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8716943087434373965L;

	/** 公文id*/
	private String 				fileId;
	
	/** 档案id*/
	private Long 				archId;
	@OneToOne
	@JoinColumn(name = "archId", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private DocArchives			docArchives;
	
	/** 销毁人姓名*/
	private String				destroyUserName;
	
	/** 销毁时间*/
	private Date				destroyDate;
	
	/** 销毁原因*/
	private	String				destroyReason;

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
