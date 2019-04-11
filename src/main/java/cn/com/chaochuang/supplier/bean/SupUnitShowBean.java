package cn.com.chaochuang.supplier.bean;

import java.util.Date;

import org.dozer.Mapping;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.supplier.reference.RecommendLevel;
import cn.com.chaochuang.supplier.reference.SupType;
import cn.com.chaochuang.supplier.reference.UnitNature;

public class SupUnitShowBean {


    private Long            id;
    
    @Mapping("group.groupName")
    private String          groupName;

    /** 单位名称 */
    private String   		unitName;
    
    /** 法人代表 */
    private String   		legalRepresentative;
    
    /** 联系电话 */
    private String  		contactPhone;
    
    /** 所属行业 */
    private String   		industry;
    
    /** 主要业务 */
    private String   		mainBusiness;
    
    /** 供应商类型 */
    private SupType   		supType;
    
    /** 品牌名称 */
    private String   		brand;
    
    /** 推荐级别 */
    private RecommendLevel  recommendLevel;
    
    /** 单位地址 */
    private String   		unitAddress;
    
    /** 网址 */
    private String   		url;
    
    /** 公司邮箱 */
    private String   		unitEmail;
    
    /** 登记日期 */
    private Date   	 		createTime;
    private String          createTimeShow;
    
    /** 企业性质 */
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

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
        if (this.createTime != null) {
            this.createTimeShow = Tools.DATE_FORMAT.format(this.createTime);
        }
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

	public String getCreateTimeShow() {
		return createTimeShow;
	}

	public SupType getSupType() {
		return supType;
	}

	public void setSupType(SupType supType) {
		this.supType = supType;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
    

}
