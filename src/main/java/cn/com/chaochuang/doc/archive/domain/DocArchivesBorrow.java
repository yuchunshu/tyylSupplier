package cn.com.chaochuang.doc.archive.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.doc.archive.reference.BorrowStatus;
import cn.com.chaochuang.doc.archive.reference.BorrowStatusConverter;

/**
 * @author dengl 2017.11.30
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "borrow_id")) })
@Table(name = "oa_doc_archives_borrow")
public class DocArchivesBorrow extends StringUuidEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6331407925294897027L;

	/** 公文id*/
	private String 				fileId;
	
	/** 档案id*/
	private Long 				archId;
	@OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "arch_id", insertable = false, updatable = false)
	private Set<DocArchives>	docArchives;
	
	/** 借阅人名称*/
	private String 				borrowUserName;
	
	/** 经办人id*/
	private	Long				handleUserId;
	
	/** 经办人名称*/
	private String				handleUserName;
	
	/** 借阅时间*/
	private Date				borrowDate;
	
	/** 归还时间*/
	private Date				returnDate;
	
	/** 借阅原因*/
	private String 				borrowReason;
	
	/** 借阅状态*/
	@Convert(converter = BorrowStatusConverter.class)
	private BorrowStatus		archStatus;

	/** 创建时间*/
	private Date				createTime;
	
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

	public Set<DocArchives> getDocArchives() {
		return docArchives;
	}

	public void setDocArchives(Set<DocArchives> docArchives) {
		this.docArchives = docArchives;
	}

	public String getBorrowUserName() {
		return borrowUserName;
	}

	public void setBorrowUserName(String borrowUserName) {
		this.borrowUserName = borrowUserName;
	}

	public Long getHandleUserId() {
		return handleUserId;
	}

	public void setHandleUserId(Long handleUserId) {
		this.handleUserId = handleUserId;
	}

	public String getHandleUserName() {
		return handleUserName;
	}

	public void setHandleUserName(String handleUserName) {
		this.handleUserName = handleUserName;
	}

	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getBorrowReason() {
		return borrowReason;
	}

	public void setBorrowReason(String borrowReason) {
		this.borrowReason = borrowReason;
	}

	public BorrowStatus getArchStatus() {
		return archStatus;
	}

	public void setArchStatus(BorrowStatus archStatus) {
		this.archStatus = archStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
