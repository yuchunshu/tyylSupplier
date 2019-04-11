/*
 * FileName:    OaAddressPersonal.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月26日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.address.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

import cn.com.chaochuang.common.data.domain.LongIdEntity;

/**
 * @author HM
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "addr_id")) })
public class OaAddressPersonal extends LongIdEntity {

    private static final long serialVersionUID = -8749450542121773678L;

    /** 名称 */
    private String            pname;

    /** 部门名称 */
    private String            department;

    /** 职务 */
    private String            duty;

    /** 电话 */
    private String            phone;

    /** 手机 */
    private String            mobile;

    /** 家庭电话 */
    private String            homePhone;

    /** 电子邮箱 */
    private String            email;

    /** 备注 */
    private String            remark;

    /** 创建人ID */
    private Long              creatorId;

    /** 创建人部门ID */
    private Long              creatorDepId;

    /** 创建日期 */
    private Date              createDate;

    /** 传真 */
    private String            fax;

    /** 别称 */
    private String            alias;

    /** 部门id */
    private Long              depId;

    /** 地址 */
    private String            address;

    /** 邮编 */
    private String            postcode;

    /** 单位名称 */
    private String            unitName;

    /** 排序号 */
    private Long              orderNum;

    /**
     * @return the pname
     */
    public String getPname() {
        return pname;
    }

    /**
     * @param pname
     *            the pname to set
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department
     *            the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the duty
     */
    public String getDuty() {
        return duty;
    }

    /**
     * @param duty
     *            the duty to set
     */
    public void setDuty(String duty) {
        this.duty = duty;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     *            the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the homePhone
     */
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * @param homePhone
     *            the homePhone to set
     */
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     *            the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the creatorId
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * @param creatorId
     *            the creatorId to set
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * @return the creatorDepId
     */
    public Long getCreatorDepId() {
        return creatorDepId;
    }

    /**
     * @param creatorDepId
     *            the creatorDepId to set
     */
    public void setCreatorDepId(Long creatorDepId) {
        this.creatorDepId = creatorDepId;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     *            the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax
     *            the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias
     *            the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return the depId
     */
    public Long getDepId() {
        return depId;
    }

    /**
     * @param depId
     *            the depId to set
     */
    public void setDepId(Long depId) {
        this.depId = depId;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @param postcode
     *            the postcode to set
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * @return the unitName
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * @param unitName
     *            the unitName to set
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * @return the orderNum
     */
    public Long getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum
     *            the orderNum to set
     */
    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

}
