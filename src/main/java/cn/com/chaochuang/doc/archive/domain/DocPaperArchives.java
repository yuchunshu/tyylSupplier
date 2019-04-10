package cn.com.chaochuang.doc.archive.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.doc.event.reference.OpenFlag;
import cn.com.chaochuang.doc.event.reference.OpenFlagConverter;

/**
 * @author dengl 2017.11.24
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "file_id")) })
@Table(name = "oa_doc_paper_archives")
public class DocPaperArchives extends StringUuidEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6357459840966354507L;

	/** 标题*/
	private String 				title;
	
	/** 文号*/
	private String 				docCode;
	
	/** 编号:1-9+流水号*/
	private String 				sncode;
	
    /** 密级*/
    private String       		dense;
    
    /** 公开 */
    @Convert(converter = OpenFlagConverter.class)
    private OpenFlag        	openFlag;
    
    /** 紧急程度 */
    private String   			urgencyLevel;
    
    /** 拟稿日期*/
    private Date 				createDate;
    
    /** 拟稿人姓名*/
    private String				creatorName;

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

	public OpenFlag getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(OpenFlag openFlag) {
		this.openFlag = openFlag;
	}
	
	public String getDense() {
		return dense;
	}

	public void setDense(String dense) {
		this.dense = dense;
	}

	public String getUrgencyLevel() {
		return urgencyLevel;
	}

	public void setUrgencyLevel(String urgencyLevel) {
		this.urgencyLevel = urgencyLevel;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
}
