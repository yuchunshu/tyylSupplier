package cn.com.chaochuang.webservice.bean;

import java.util.Date;

public class SysUser {
    /** 原系统用户编号 */
    private Long    rmUserId;
    /** 原系统用户信息编号 */
    private Long    rmUserInfoId;
    /** 原系统部门编号 */
    private Long    rmDepId;
    /** 姓名 */
    private String  userName;
    /** 性别 */
    private String  sex;
    /** 登录账号 */
    private String  account;
    /** 登录密码 */
    private String  password;
    /** 职务名称 */
    private String  dutyName;
    /** 移动电话 */
    private String  mobile;
    /** 工作电话 */
    private String  workPhone;
    /** 消息推送注册号 */
    private String  registrationId;
    /** 原系统状态标识 */
    private String  delFlag;
    /** MD5校验码 */
    private String  mdfCode;
    /** 排序号 */
    private Long    orderNum;
    /** 注册时间 */
    private Date    registerTime;
    /** 手机IMEI码 */
    private String  imeiCode;
    private Integer valid;

    public Long getRmUserId() {
        return rmUserId;
    }

    public void setRmUserId(Long rmUserId) {
        this.rmUserId = rmUserId;
    }

    public Long getRmUserInfoId() {
        return rmUserInfoId;
    }

    public void setRmUserInfoId(Long rmUserInfoId) {
        this.rmUserInfoId = rmUserInfoId;
    }

    public Long getRmDepId() {
        return rmDepId;
    }

    public void setRmDepId(Long rmDepId) {
        this.rmDepId = rmDepId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getMdfCode() {
        return mdfCode;
    }

    public void setMdfCode(String mdfCode) {
        this.mdfCode = mdfCode;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getImeiCode() {
        return imeiCode;
    }

    public void setImeiCode(String imeiCode) {
        this.imeiCode = imeiCode;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

}
