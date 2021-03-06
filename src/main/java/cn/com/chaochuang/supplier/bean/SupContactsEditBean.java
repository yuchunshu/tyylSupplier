package cn.com.chaochuang.supplier.bean;

import java.util.Date;


import cn.com.chaochuang.common.user.reference.Sex;

public class SupContactsEditBean {

    // Fields
    private Long     id;
    
    /** 姓名 */
    private String   name;
    
    /** 性别 */
    private Sex      sex;
    
    /** QQ */
    private String   qq;
    
    /** 所在部门 */
    private String   dept;
    
    /** 职务 */
    private String   duty;
    
    /** 常用手机 */
    private String   commonPhone;
    
    /** 备用手机 */
    private String   sparePhone;
    
    /** 电子邮箱 */
    private String   email;
    
    /** 登记日期 */
    private Date   	 createTime;
    
    /** 操作人员 */
    private String   idCard;

    /** 所属单位ID */
    private Long     unitId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getCommonPhone() {
		return commonPhone;
	}

	public void setCommonPhone(String commonPhone) {
		this.commonPhone = commonPhone;
	}

	public String getSparePhone() {
		return sparePhone;
	}

	public void setSparePhone(String sparePhone) {
		this.sparePhone = sparePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
    
}
