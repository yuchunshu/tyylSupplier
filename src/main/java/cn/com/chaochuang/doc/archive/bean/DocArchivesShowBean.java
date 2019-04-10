/*
 * FileName:    NoticeShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.archive.bean;

import java.util.Date;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.archive.reference.ArchStatus;
import cn.com.chaochuang.doc.archive.reference.FileType;

/**
 * @author dengl 2017.11.23
 *
 */
public class DocArchivesShowBean {

	private Long			id;
	
	/** 档案名称 */
    private String          archName;
    
    /** 档案编号 */
    private String          archNo;

    /** 立卷日期 */
    private Date            createDate;
    
    private String 			createDateShow;
    
    /** 保留期限*/
    private Date 			retentionDate;
    
    private String			retentionDateShow;
    
    /** 柜位号*/
    private String			counterNo;
    
    /** 柜位号名称*/
    private String 			counterNoName;
    
    /** 案卷类型*/
    private String			archType;
    
    /** 案卷类型名称*/
    private String 			archTypeName;
    
    /** 介质类型*/
    private String			mediumType;
    
    /** 介质类型名称*/
    private String			mediumTypeName;
    
    /** 公文id*/
    private String			fileId;
    
    /** 公文标题*/
    private String			title;
    
    /** 年份*/
    private String			archYear;
    
    /** 备注 */
    private String          remark;

    /** 排序号 */
    private Long            orderNum;
    
    /** 公文类型 */
    private FileType        fileType;
    
    /** 所属部门ID */
    private Long            depId;
    
    /** 档案状态*/
    private ArchStatus		archStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		if (this.createDate != null) {
			this.createDateShow = Tools.DATE_FORMAT.format(this.createDate);
		}
	}

	public Date getRetentionDate() {
		return retentionDate;
	}

	public void setRetentionDate(Date retentionDate) {
		this.retentionDate = retentionDate;
		if (this.retentionDate != null) {
			this.retentionDateShow = Tools.DATE_FORMAT.format(this.retentionDate);
		}
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getCreateDateShow() {
		if (this.createDate != null) {
			this.createDateShow = Tools.DATE_FORMAT.format(this.createDate);
		}
		return createDateShow;
	}

	public void setCreateDateShow(String createDateShow) {
		this.createDateShow = createDateShow;
	}

	public String getRetentionDateShow() {
		if (this.retentionDate != null) {
			this.retentionDateShow = Tools.DATE_FORMAT.format(this.retentionDate);
		}
		return retentionDateShow;
	}

	public void setRetentionDateShow(String retentionDateShow) {
		this.retentionDateShow = retentionDateShow;
	}

	public String getCounterNoName() {
		return counterNoName;
	}

	public void setCounterNoName(String counterNoName) {
		this.counterNoName = counterNoName;
	}

	public String getArchTypeName() {
		return archTypeName;
	}

	public void setArchTypeName(String archTypeName) {
		this.archTypeName = archTypeName;
	}

	public String getMediumTypeName() {
		return mediumTypeName;
	}

	public void setMediumTypeName(String mediumTypeName) {
		this.mediumTypeName = mediumTypeName;
	}

	public ArchStatus getArchStatus() {
		return archStatus;
	}

	public void setArchStatus(ArchStatus archStatus) {
		this.archStatus = archStatus;
	}
}
