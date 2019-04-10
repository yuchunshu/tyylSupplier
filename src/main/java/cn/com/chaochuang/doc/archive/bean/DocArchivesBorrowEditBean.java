package cn.com.chaochuang.doc.archive.bean;

import java.util.Date;

import cn.com.chaochuang.doc.archive.domain.DocArchives;
import cn.com.chaochuang.doc.archive.reference.BorrowStatus;

/**
 * @author dengl 2017.11.30
 *
 */
public class DocArchivesBorrowEditBean {
	
	private String 			id;
	
	private String			ids;
	
	/** 公文id*/
	private String 			fileId;
	
	/** 档案id*/
	private Long 			archId;
	private DocArchives		docArchives;
	
	/** 借阅人名称*/
	private String 			borrowUserName;
	
	/** 经办人id*/
	private	Long			handleUserId;
	
	/** 经办人名称*/
	private String			handleUserName;
	
	/** 借阅时间*/
	private Date			borrowDate;
	
	/** 归还时间*/
	private Date			returnDate;
	
	/** 借阅原因*/
	private String 			borrowReason;
	
	/** 借阅状态*/
	private BorrowStatus	archStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}
