package cn.com.chaochuang.doc.archive.bean;

import java.util.Date;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.archive.domain.DocArchives;
import cn.com.chaochuang.doc.archive.reference.BorrowStatus;

/**
 * @author dengl 2017.11.30
 *
 */
public class DocArchivesBorrowShowBean {
	
	private String 			id;
	
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
	
	private String			borrowDateShow;
	
	/** 归还时间*/
	private Date			returnDate;
	
	private String			returnDateShow;
	
	/** 借阅原因*/
	private String 			borrowReason;
	
	/** 借阅状态*/
	private BorrowStatus	archStatus;

	/** 创建时间*/
	private Date			createTime;
	
	private String			createTimeShow;
	
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
		if (this.borrowDate != null) {
			this.borrowDateShow = Tools.DATE_FORMAT.format(this.borrowDate);
		}
	}
	
	public String getBorrowDateShow() {
		if (this.borrowDate != null) {
			this.borrowDateShow = Tools.DATE_FORMAT.format(this.borrowDate);
		}
		return borrowDateShow;
	}

	public void setBorrowDateShow(String borrowDateShow) {
		this.borrowDateShow = borrowDateShow;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
		if (this.returnDate != null) {
			this.returnDateShow = Tools.DATE_FORMAT.format(this.returnDate);
		}
	}

	public String getReturnDateShow() {
		if (this.returnDate != null) {
			this.returnDateShow = Tools.DATE_FORMAT.format(this.returnDate);
		}
		return returnDateShow;
	}

	public void setReturnDateShow(String returnDateShow) {
		this.returnDateShow = returnDateShow;
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
		if (this.createTime != null) {
			this.createTimeShow = Tools.DATE_MINUTE_FORMAT.format(this.createTime);
		}
	}

	public String getCreateTimeShow() {
		if (this.createTime != null) {
			this.createTimeShow = Tools.DATE_MINUTE_FORMAT.format(this.createTime);
		}
		return createTimeShow;
	}

	public void setCreateTimeShow(String createTimeShow) {
		this.createTimeShow = createTimeShow;
	}
	
}
