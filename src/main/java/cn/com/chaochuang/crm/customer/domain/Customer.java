package cn.com.chaochuang.crm.customer.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.crm.customer.reference.CusCategory;
import cn.com.chaochuang.crm.customer.reference.CusCategoryConverter;

@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "cus_id")) })
public class Customer extends LongIdEntity {

	private static final long serialVersionUID = 2137845822962483790L;
	
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
    
    /** 操作人员*/
    private String   	 	creatorName;

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
