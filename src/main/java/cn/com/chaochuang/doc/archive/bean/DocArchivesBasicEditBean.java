
package cn.com.chaochuang.doc.archive.bean;

import java.util.Date;

import cn.com.chaochuang.doc.archive.reference.BasicType;

/**
 * @author shiql 2017.11.22
 *
 */
public class DocArchivesBasicEditBean {

    private String     	id;
    
    /** 编码 */
    private String   	basicCode;
    
    /** 名称 */
    private String   	basicName;
    
    /** 创建日期 */
    private Date     	createDate;
    
    /** 修改时间*/
    private Date 		updateDate;

    /** 类型*/
    private BasicType 	basicType;
    
    /** 创建者Id*/
    private Long  		creatorId;
    
    /** 修改人Id*/
    private Long  		updatorId;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the basicCode
	 */
	public String getBasicCode() {
		return basicCode;
	}

	/**
	 * @param basicCode the basicCode to set
	 */
	public void setBasicCode(String basicCode) {
		this.basicCode = basicCode;
	}

	/**
	 * @return the basicName
	 */
	public String getBasicName() {
		return basicName;
	}

	/**
	 * @param basicName the basicName to set
	 */
	public void setBasicName(String basicName) {
		this.basicName = basicName;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the basicType
	 */
	public BasicType getBasicType() {
		return basicType;
	}

	/**
	 * @param basicType the basicType to set
	 */
	public void setBasicType(BasicType basicType) {
		this.basicType = basicType;
	}

	/**
	 * @return the creatorId
	 */
	public Long getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * @return the updatorId
	 */
	public Long getUpdatorId() {
		return updatorId;
	}

	/**
	 * @param updatorId the updatorId to set
	 */
	public void setUpdatorId(Long updatorId) {
		this.updatorId = updatorId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
