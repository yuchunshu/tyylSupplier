package cn.com.chaochuang.common.user.bean;

import java.util.Date;

import cn.com.chaochuang.common.miji.reference.MijiType;
import cn.com.chaochuang.common.user.reference.Sex;

public class UserEditBean {

    /** id */
    private Long     id;

    /** 所属部门ID */
    private Long     deptId;

    /** 姓名 */
    private String   userName;

    /** 登录账号 */
    private String   account;

    /** 手机 */
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

    /** 注册日期 */
    private Date     regDate;

    /** 排序号 */
    private Long     sort;

    /** 性别 */
    private Sex      sex;

    /** 密级 */
    private MijiType miji;

    /** 职务 */
    private Long     dutyId;

    /** 身份证 */
    private String   idCard;

    /** 关联角色ID */
    private String   roles;

    /**
     * @return the deptId
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * @param deptId
     *            the deptId to set
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
     * @return the regDate
     */
    public Date getRegDate() {
        return regDate;
    }

    /**
     * @param regDate
     *            the regDate to set
     */
    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
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
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel
     *            the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the duty
     */
    public Long getDutyId() {
        return dutyId;
    }

    /**
     * @param duty
     *            the duty to set
     */
    public void setDutyId(Long dutyId) {
        this.dutyId = dutyId;
    }

    /**
     * @return the idCard
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * @param idCard
     *            the idCard to set
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * @return the roles
     */
    public String getRoles() {
        return roles;
    }

    /**
     * @param roles
     *            the roles to set
     */
    public void setRoles(String roles) {
        this.roles = roles;
    }

}
