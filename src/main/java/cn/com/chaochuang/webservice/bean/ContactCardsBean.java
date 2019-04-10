/*
 * FileName:    ContactCarsShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年5月27日 (Administrator@USER-20150531ET) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

import java.util.Date;

/**
 * @author Administrator@USER-20150531ET
 *
 */
public class ContactCardsBean {
    /** 姓名 */
    private String staffName;
    /** 部门 名称 */
    private String department;
    /** 职务 */
    private String duty;
    /** 固定电话 */
    private String phone;
    /** 移动电话 */
    private String mobile;
    /** 电子邮箱 */
    private String email;
    /** QQ */
    private String QQ;
    /** 单位ID */
    private int    depId;
    /** 单位名称 */
    private String unitName;
    /** 导入时间 */
    private Date   inputDate;
    /** 邮编 */
    private String zip;
    /** 原系统通讯录编号 */
    private long   rmLinkmanId;

    /** 创建人ID */
    private Long   creatorId;

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
     * @return the rmLinkmanId
     */
    public long getRmLinkmanId() {
        return rmLinkmanId;
    }

    /**
     * @param rmLinkmanId
     *            the rmLinkmanId to set
     */
    public void setRmLinkmanId(long rmLinkmanId) {
        this.rmLinkmanId = rmLinkmanId;
    }

    /** 家庭电话 */
    private String homePhone;
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
    public int getDepId() {
        return depId;
    }

    /**
     * @param depId
     *            the depId to set
     */
    public void setDepId(int depId) {
        this.depId = depId;
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
