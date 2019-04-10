/*
 * FileName:    UserBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月29日 (Administrator@USER-20150531ET) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

import javax.persistence.Convert;

import cn.com.chaochuang.common.user.reference.Sex;
import cn.com.chaochuang.common.user.reference.SexConverter;

/**
 * @author qinyh@USER-20150531ET
 *
 */
public class UserBean {

    /** 原系统用户编号 */
    private Long    rmUserId;

    /** 原系统部门编号 */
    private Long    rmDepId;

    /** 姓名 */
    private String  userName;

    /** 性别 */
    @Convert(converter = SexConverter.class)
    private Sex     sex;

    /** 职务名称 */
    private String  dutyName;

    /** 登录账号 */
    private String  account;

    /** 登录密码 */
    private String  password;

    /** 手机号 */
    private String  mobile;

    /** 排序号 */
    private Long    orderNum;

    /** 是否有效 */
    private Integer valid;

    /**
     * @return the rmDepId
     */
    public Long getRmDepId() {
        return rmDepId;
    }

    /**
     * @param rmDepId
     *            the rmDepId to set
     */
    public void setRmDepId(Long rmDepId) {
        this.rmDepId = rmDepId;
    }

    /**
     * @return the valid
     */
    public Integer getValid() {
        return valid;
    }

    /**
     * @param valid
     *            the valid to set
     */
    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getUserName() {
        return userName;
    }

    /**
     * @return the rmUserId
     */
    public Long getRmUserId() {
        return rmUserId;
    }

    /**
     * @param rmUserId
     *            the rmUserId to set
     */
    public void setRmUserId(Long rmUserId) {
        this.rmUserId = rmUserId;
    }

    /**
     * @return the dutyName
     */
    public String getDutyName() {
        return dutyName;
    }

    /**
     * @param dutyName
     *            the dutyName to set
     */
    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the sex
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * @param sex
     *            the sex to set
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
