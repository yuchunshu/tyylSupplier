package cn.com.chaochuang.supplier.bean;

import java.util.Date;


import cn.com.chaochuang.supplier.reference.Evaluation;

public class SupDeviceEditBean {

    // Fields
    private Long     		id;
    
    /** 材料名称 */
    private String   		deviceName;
    
    /** 同档次产品 */
    private String   		sameProduct;
    
    /** 数量 */
    private String   		quantity;
    
    /** 单位 */
    private String   		deviceUnit;
    
    /** 不含税单价 */
    private String   		taxFreeUnitPrice;
    
    /** 规格 */
    private String   		specification;
    
    /** 含税单价 */
    private String   		taxInclusiveUnitPrice;
    
    /** 材质性能 */
    private Evaluation      materialProperty;
    
    /** 金额 */
    private String   		amount;
    
    /** 更新时间 */
    private Date   			updateTime;
    
    /** 报价时间 */
    private Date   			quotesTime;
    
    /** 登记日期 */
    private Date   	 		createTime;
    
    /** 操作人员 */
    private String   	 	creatorName;
    
    /** 商品类别 */
    private String   		goodsCategory;
    
    /** 品牌 */
    private String   		brand;
    
    /** 到货地址 */
    private String   		arrivalAddress;
    
    /** 价格类别 */
    private String   		priceCategory;
    
    /** 项目名称 */
    private String   		eventName;
    
    /** 厂家面价 */
    private String   		factoryPrice;
    
    /** 备注 */
    private String   		remark;

    /** 所属单位ID */
    private Long     		unitId;
    
    /** 附件IDs 通过','分割 */
    private String      	attach;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getSameProduct() {
		return sameProduct;
	}

	public void setSameProduct(String sameProduct) {
		this.sameProduct = sameProduct;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getDeviceUnit() {
		return deviceUnit;
	}

	public void setDeviceUnit(String deviceUnit) {
		this.deviceUnit = deviceUnit;
	}

	public String getTaxFreeUnitPrice() {
		return taxFreeUnitPrice;
	}

	public void setTaxFreeUnitPrice(String taxFreeUnitPrice) {
		this.taxFreeUnitPrice = taxFreeUnitPrice;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getTaxInclusiveUnitPrice() {
		return taxInclusiveUnitPrice;
	}

	public void setTaxInclusiveUnitPrice(String taxInclusiveUnitPrice) {
		this.taxInclusiveUnitPrice = taxInclusiveUnitPrice;
	}

	public Evaluation getMaterialProperty() {
		return materialProperty;
	}

	public void setMaterialProperty(Evaluation materialProperty) {
		this.materialProperty = materialProperty;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getQuotesTime() {
		return quotesTime;
	}

	public void setQuotesTime(Date quotesTime) {
		this.quotesTime = quotesTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getArrivalAddress() {
		return arrivalAddress;
	}

	public void setArrivalAddress(String arrivalAddress) {
		this.arrivalAddress = arrivalAddress;
	}

	public String getPriceCategory() {
		return priceCategory;
	}

	public void setPriceCategory(String priceCategory) {
		this.priceCategory = priceCategory;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getFactoryPrice() {
		return factoryPrice;
	}

	public void setFactoryPrice(String factoryPrice) {
		this.factoryPrice = factoryPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

}
