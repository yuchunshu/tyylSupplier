package cn.com.chaochuang.crm.customer.bean;

import java.util.Date;

import javax.persistence.Convert;

import org.dozer.Mapping;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.crm.customer.reference.CusCategory;
import cn.com.chaochuang.crm.customer.reference.CusCategoryConverter;

public class CustomerShowBean {


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
    private String   		createTimeShow;
    
    /** 操作人员 */
    private String   	 	creatorName;
    
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
        if (this.createTime != null) {
            this.createTimeShow = Tools.DATE_FORMAT.format(this.createTime);
        }
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCreateTimeShow() {
		return createTimeShow;
	}

	public void setCreateTimeShow(String createTimeShow) {
		this.createTimeShow = createTimeShow;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

}
