package cn.com.chaochuang.common.user.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.lookup.annotation.LookUp;
import cn.com.chaochuang.common.miji.domain.MijiPersistEntity;
import cn.com.chaochuang.common.syspower.domain.SysRole;
import cn.com.chaochuang.common.user.reference.Sex;
import cn.com.chaochuang.common.user.reference.SexConverter;

@Entity
@LookUp
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "user_id")),
                @AttributeOverride(name = "valid", column = @Column(name = "valid")) })
public class SysUser extends MijiPersistEntity implements IDictionary {

    private static final long serialVersionUID = -4615274498193533591L;
    
    
    private Long 			  deptId;
    /** 所属部门 */
    @ManyToOne
    @JoinColumn(name = "deptId",updatable = false, insertable = false)
    private SysDepartment     department;

    /** 姓名 */
    private String            userName;

    /** 性别 */
    @Convert(converter = SexConverter.class)
    private Sex               sex;

    /** 职务 */
    private Long              dutyId;
    @OneToOne(cascade={})
    @JoinColumn(name = "dutyId", insertable = false, updatable = false)
    private SysDuty           duty;

    /** 身份证 */
    private String            idCard;

    /** 登录账号 */
    private String            account;

    /** 登录密码 */
    private String            password;

    /** 手机 */
    private String            mobile;

    /** 固定电话 */
    private String            tel;

    /** 传真 */
    private String            fax;

    /** 电子信箱 */
    private String            email;

    /** 家庭地址 */
    private String            homeAddr;

    /** 邮编 */
    private String            postcode;

    /** 家庭电话 */
    private String            homeTel;

    /** 注册日期 */
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date              regDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date              lastPasswdDate;

    /** 排序号 */
    private Long              sort;

    /** 登录失败次数 */
    private Integer           loginFailCount;

    /** 是否锁定 */
    private Boolean           accountLocked;

    // /** 是否有效 */
    // private String valid;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_role_rel", joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id") }, inverseJoinColumns = {
                                    @JoinColumn(name = "role_id", referencedColumnName = "role_id") })
    private Set<SysRole>      roles;

    /**
     * @return the dutyId
     */
    public Long getDutyId() {
        return dutyId;
    }

    /**
     * @param dutyId
     *            the dutyId to set
     */
    public void setDutyId(Long dutyId) {
        this.dutyId = dutyId;
    }

    /**
     * @return the duty
     */
    public SysDuty getDuty() {
        return duty;
    }

    /**
     * @param duty
     *            the duty to set
     */
    public void setDuty(SysDuty duty) {
        this.duty = duty;
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

    public SysDepartment getDepartment() {
        return department;
    }

    public void setDepartment(SysDepartment department) {
        this.department = department;
    }

    public String getUserName() {
        return userName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SysRole> roles) {
        this.roles = roles;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHomeTel() {
        return homeTel;
    }

    public void setHomeTel(String homeTel) {
        this.homeTel = homeTel;
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
     * @return the loginFailCount
     */
    public Integer getLoginFailCount() {
        return loginFailCount;
    }

    /**
     * @param loginFailCount
     *            the loginFailCount to set
     */
    public void setLoginFailCount(Integer loginFailCount) {
        this.loginFailCount = loginFailCount;
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
     * @return the lastPasswdDate
     */
    public Date getLastPasswdDate() {
        return lastPasswdDate;
    }

    /**
     * @param lastPasswdDate
     *            the lastPasswdDate to set
     */
    public void setLastPasswdDate(Date lastPasswdDate) {
        this.lastPasswdDate = lastPasswdDate;
    }

    @Override
    public String getKey() {
        return this.id.toString();
    }

    @Override
    public String getValue() {
        return this.userName;
    }

    @Override
    public Object getObject() {
        return this;
    }

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
}
