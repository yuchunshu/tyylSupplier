/*
 * FileName:    DepLinkman.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年3月19日 (LLM) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

import java.util.Date;

/**
 * @author huangwq 公共通讯录
 */
public class DepLinkmanInfo {
    /** 通讯录成员项目 */
    private String staffName;
    /** 成员所在部门 */
    private String department;
    /** 成员单位 */
    private String unitName;
    /** 成员职务 */
    private String duty;
    /** 固定电话 */
    private String phone;
    /** 家庭电话 */
    private String homePhone;
    /** 移动电话 */
    private String mobile;
    /** 电子邮件 */
    private String email;
    /** QQ */
    private String QQ;
    /** 邮编 */
    private String zip;
    /** 通讯录所属单位编号 */
    private Long   depId;
    /** 数据导入时间 */
    private Date   inputDate;
    /** 原系统通讯录编号 */
    private Long   rmLinkmanId;
    /** 地址 */
    private String address;

    /**
     * @return the staffName
     */
    public String getStaffName() {
        return staffName;
    }

    /**
     * @param staffName
     *            the staffName to set
     */
    public void setStaffName(String staffName) {
        this.staffName = staffName;
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
     * @return the qQ
     */
    public String getQQ() {
        return QQ;
    }

    /**
     * @param qQ
     *            the qQ to set
     */
    public void setQQ(String qQ) {
        QQ = qQ;
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
     * @return the inputDate
     */
    public Date getInputDate() {
        return inputDate;
    }

    /**
     * @param inputDate
     *            the inputDate to set
     */
    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip
     *            the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return the rmLinkmanId
     */
    public Long getRmLinkmanId() {
        return rmLinkmanId;
    }

    /**
     * @param rmLinkmanId
     *            the rmLinkmanId to set
     */
    public void setRmLinkmanId(Long rmLinkmanId) {
        this.rmLinkmanId = rmLinkmanId;
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

}
