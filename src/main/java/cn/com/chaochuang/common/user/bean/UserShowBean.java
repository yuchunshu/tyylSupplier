package cn.com.chaochuang.common.user.bean;

import org.dozer.Mapping;

import cn.com.chaochuang.common.miji.reference.MijiType;
import cn.com.chaochuang.common.user.reference.Sex;

public class UserShowBean {

    /** id */
    private Long     id;

    /** 所属部门名称 */
    @Mapping("department.deptName")
    private String   deptName;

    /** 姓名 */
    private String   userName;

    /** 登录账号 */
    private String   account;

    /** 手机号 */
    private String   mobile;

    /** 固定电话 */
    private String   tel;

    /** 传真 */
    private String   fax;

    /** 电子信箱 */
    private String   email;

    /** 家庭地址 */
    private String   homeAddr;

    /** 邮编 */
    private String   postcode;

    /** 家庭电话 */
    private String   homeTel;

    /** 性别 */
    private Sex      sex;

    /** 职务 */
    @Mapping("duty.dutyName")
    private String   duty;

    /** 是否有效 */
    private Integer  valid;

    /** 是否锁定 */
    private Boolean  accountLocked;

    /** 密级 */
    private MijiType miji;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getHomeAddr() {
        return homeAddr;
    }

    public void setHomeAddr(String homeAddr) {
        this.homeAddr = homeAddr;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHomeTel() {
        return homeTel;
    }

    public void setHomeTel(String homeTel) {
        this.homeTel = homeTel;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    /**
     * @return the accountLocked
     */
    public Boolean getAccountLocked() {
        return accountLocked;
    }

    /**
     * @param accountLocked
     *            the accountLocked to set
     */
    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    /**
     * @return the miji
     */
    public MijiType getMiji() {
        return miji;
    }

    /**
     * @param miji
     *            the miji to set
     */
    public void setMiji(MijiType miji) {
        this.miji = miji;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the deptName
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @param deptName
     *            the deptName to set
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

}