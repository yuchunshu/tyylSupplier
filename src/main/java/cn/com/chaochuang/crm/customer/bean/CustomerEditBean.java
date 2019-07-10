package cn.com.chaochuang.crm.customer.bean;

import java.util.Date;

import javax.persistence.Convert;

import cn.com.chaochuang.crm.customer.reference.CusCategory;
import cn.com.chaochuang.crm.customer.reference.CusCategoryConverter;

public class CustomerEditBean {

    // Fields
    private Long     		id;
    
    /** 客户名称 */
    private String   		cusName;
    
    /**联系人*/
    private String 			contactName;
    
    /**联系电话*/
    private String 			contactPhone;
    
    /** 客户类别 */
    @Convert(converter = CusCategoryConverter.class)
    private CusCategory   	cusCategory;
    
    /** 部门/职务 */
    private String   		duty;
    
    /** 客户地址 */
    private String   		address;
    
    /** 录入日期 */
    private Date   	 		createTime;
    
    /** 操作人员 */
    private String   	 	creatorName;
    
    /** 附件IDs 通过','分割 */
    private String      	attach;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public CusCategory getCusCategory() {
		return cusCategory;
	}

	public void setCusCategory(CusCategory cusCategory) {
		this.cusCategory = cusCategory;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

}
