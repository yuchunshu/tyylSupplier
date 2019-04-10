package cn.com.chaochuang.doc.archive.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.doc.archive.reference.BasicType;
import cn.com.chaochuang.doc.archive.reference.BasicTypeConverter;
/** 
 * @ClassName: DocArchivesBasic 
 * @Description: 基础档案维护
 * @author: sqlang
 * @date: 2017年11月20日 下午11:07:40  
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "basic_id")) })
@Table(name = "oa_doc_archives_basic")
public class DocArchivesBasic extends StringUuidEntity {

	/**  */
	private static final long serialVersionUID = -1937994306552765323L;

    /** 编码 */
    private String          	basicCode;

    /** 名称 */
    private String          	basicName;
    
    /** 类型 */
    @Convert(converter = BasicTypeConverter.class)
    private BasicType        	basicType;
    
    /** 所属部门ID */
    private Long            	depId;
    
    /** 创建人ID */
    private Long            	creatorId;
    @ManyToOne
    @JoinColumn(name = "creatorId" , insertable = false, updatable = false)
    private SysUser            	creator;

    /** 创建日期 */
    private Date            	createDate;
    
    /** 修改人ID */
    private Long            	updatorId;
    @ManyToOne
    @JoinColumn(name = "updatorId" , insertable = false, updatable = false)
    private SysUser            	updator;

    /** 修改日期 */
    private Date            	updateDate;

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
	 * @return the depId
	 */
	public Long getDepId() {
		return depId;
	}

	/**
	 * @param depId the depId to set
	 */
	public void setDepId(Long depId) {
		this.depId = depId;
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
	 * @return the creator
	 */
	public SysUser getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(SysUser creator) {
		this.creator = creator;
	}

	/**
	 * @return the updator
	 */
	public SysUser getUpdator() {
		return updator;
	}

	/**
	 * @param updator the updator to set
	 */
	public void setUpdator(SysUser updator) {
		this.updator = updator;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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
}
