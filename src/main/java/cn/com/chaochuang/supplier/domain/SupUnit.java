package cn.com.chaochuang.supplier.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.supplier.reference.RecommendLevel;
import cn.com.chaochuang.supplier.reference.RecommendLevelConverter;
import cn.com.chaochuang.supplier.reference.SupType;
import cn.com.chaochuang.supplier.reference.SupTypeConverter;
import cn.com.chaochuang.supplier.reference.UnitNature;
import cn.com.chaochuang.supplier.reference.UnitNatureConverter;

@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "unit_id")) })
public class SupUnit extends LongIdEntity {

    private static final long serialVersionUID = -8749450542121773678L;

    /** 单位名称 */
    private String   		unitName;
    
    /** 法人代表 */
    private String   		legalRepresentative;
    
    /** 联系电话 */
    private String  		contactPhone;
    
    /** 所属行业 */
    private String   		industry;
    
    /** 供应商类型 */
    @Convert(converter = SupTypeConverter.class)
    private SupType   		supType;
    
	/** 主要业务 */
    private String   		mainBusiness;
    
    /** 品牌名称 */
    private String   		brand;
    
    /** 推荐级别 */
    @Convert(converter = RecommendLevelConverter.class)
    private RecommendLevel  recommendLevel;
    
    /** 单位地址 */
    private String   		unitAddress;
    
    /** 网址 */
    private String   		url;
    
    /** 公司邮箱 */
    private String   		unitEmail;
    
    /** 登记日期 */
    private Date   	 		createTime;
    
    /** 企业性质 */
    @Convert(converter = UnitNatureConverter.class)
    private UnitNature   	unitNature;
    
    /** 办公传真 */
    private String   		fax;
    
    /** 开户行 */
    private String   		accountBank;
    
    /** 开户行账号 */
    private String   		accountBankAccount;
    
    /** 单位税号 */
    private String   		unitTaxNumber;
    
    /** 注册资金 */
    private String   		registeredCapital;
    
    /** 单位资质 */
    private String   		qualification;
    
    /** 说明 */
    private String   		remark;
    
    /** 身份证 */
    private String   		idCard;
    
    /** 隶属分组ID */
    private Long            groupId;
    
    /** 隶属分组 */
    @ManyToOne
    @JoinColumn(name = "groupId", insertable = false, updatable = false)
    private SupGroup      	group;

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public RecommendLevel getRecommendLevel() {
		return recommendLevel;
	}

	public void setRecommendLevel(RecommendLevel recommendLevel) {
		this.recommendLevel = recommendLevel;
	}

	public String getUnitAddress() {
		return unitAddress;
	}

	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUnitEmail() {
		return unitEmail;
	}

	public void setUnitEmail(String unitEmail) {
		this.unitEmail = unitEmail;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public UnitNature getUnitNature() {
		return unitNature;
	}

	public void setUnitNature(UnitNature unitNature) {
		this.unitNature = unitNature;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getAccountBankAccount() {
		return accountBankAccount;
	}

	public void setAccountBankAccount(String accountBankAccount) {
		this.accountBankAccount = accountBankAccount;
	}

	public String getUnitTaxNumber() {
		return unitTaxNumber;
	}

	public void setUnitTaxNumber(String unitTaxNumber) {
		this.unitTaxNumber = unitTaxNumber;
	}

	public String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public SupGroup getGroup() {
		return group;
	}

	public void setGroup(SupGroup group) {
		this.group = group;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public SupType getSupType() {
		return supType;
	}

	public void setSupType(SupType supType) {
		this.supType = supType;
	}
}
