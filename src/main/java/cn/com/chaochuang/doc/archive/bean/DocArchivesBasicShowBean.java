package cn.com.chaochuang.doc.archive.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.chaochuang.doc.archive.reference.BasicType;

/**
 * @author shiql 2017.11.22
 *
 */
public class DocArchivesBasicShowBean {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String           id;
    /** 编码*/
    private String           basicCode;
    /** 名称 */
    private String           basicName;
    /** 创建日期 */
    private Date             createDate;
    private String           createDateShow;
    /** 档案类型 */
    private BasicType        basicType;

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
	 * @return the sdf
	 */
	public SimpleDateFormat getSdf() {
		return sdf;
	}
	/**
	 * @param sdf the sdf to set
	 */
	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
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
        if (this.createDate != null) {
            this.createDateShow = sdf.format(this.createDate);
        }
	}
	/**
	 * @return the createDateShow
	 */
	public String getCreateDateShow() {
		return createDateShow;
	}
	/**
	 * @param createDateShow the createDateShow to set
	 */
	public void setCreateDateShow(String createDateShow) {
		this.createDateShow = createDateShow;
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
}
