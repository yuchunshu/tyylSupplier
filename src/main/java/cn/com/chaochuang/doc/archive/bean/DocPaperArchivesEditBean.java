package cn.com.chaochuang.doc.archive.bean;

import java.util.Date;

import cn.com.chaochuang.doc.archive.reference.FileType;
import cn.com.chaochuang.doc.event.reference.OpenFlag;
/**
 * @author dengl 2017.11.24
 *
 */
public class DocPaperArchivesEditBean {
	
	//纸质归档字段
	
	private String 				id;
	
	/** 标题*/
	private String 				title;
	
	/** 文号*/
	private String 				docCode;
	
	/** 编号:1-9+流水号*/
	private String 				sncode;
	
	/** 密级*/
	private String       		dense;
	
	/** 公开 */
	private OpenFlag        	openFlag;
	
	/** 紧急程度 */
	private String   			urgencyLevel;
	
	/** 拟稿日期*/
    private Date 				paperCreateDate;
    
    /** 拟稿人姓名*/
    private String				creatorName;
    
	/** 附件IDs 通过','分割 */
	private String 				attach;
    
    //档案信息字段
    
    /** 档案名称 */
    private String          	archName;
    
    /** 档案编号 */
    private String          	archNo;

    /** 立卷日期 */
    private Date            	createDate;
    
    /** 保留期限*/
    private Date 				retentionDate;
    
    /** 柜位号*/
    private String				counterNo;
    
    /** 案卷类型*/
    private String				archType;
    
    /** 介质类型*/
    private String				mediumType;
    
    /** 公文id*/
    private String				fileId;
    
    /** 年份*/
    private String				archYear;
    
    /** 备注 */
    private String          	remark;

    /** 排序号 */
    private Long            	orderNum;
    
    /** 公文类型 */
    private FileType        	fileType;
    
    /** 所属部门ID */
    private Long            	depId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getSncode() {
		return sncode;
	}

	public void setSncode(String sncode) {
		this.sncode = sncode;
	}

	public String getDense() {
		return dense;
	}

	public void setDense(String dense) {
		this.dense = dense;
	}

	public void setUrgencyLevel(String urgencyLevel) {
		this.urgencyLevel = urgencyLevel;
	}

	public OpenFlag getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(OpenFlag openFlag) {
		this.openFlag = openFlag;
	}

	public Date getPaperCreateDate() {
		return paperCreateDate;
	}

	public void setPaperCreateDate(Date paperCreateDate) {
		this.paperCreateDate = paperCreateDate;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getArchName() {
		return archName;
	}

	public void setArchName(String archName) {
		this.archName = archName;
	}

	public String getArchNo() {
		return archNo;
	}

	public void setArchNo(String archNo) {
		this.archNo = archNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getRetentionDate() {
		return retentionDate;
	}

	public void setRetentionDate(Date retentionDate) {
		this.retentionDate = retentionDate;
	}

	public String getCounterNo() {
		return counterNo;
	}

	public void setCounterNo(String counterNo) {
		this.counterNo = counterNo;
	}

	public String getArchType() {
		return archType;
	}

	public void setArchType(String archType) {
		this.archType = archType;
	}

	public String getMediumType() {
		return mediumType;
	}

	public void setMediumType(String mediumType) {
		this.mediumType = mediumType;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getArchYear() {
		return archYear;
	}

	public void setArchYear(String archYear) {
		this.archYear = archYear;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public Long getDepId() {
		return depId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

	public String getUrgencyLevel() {
		return urgencyLevel;
	}
}
