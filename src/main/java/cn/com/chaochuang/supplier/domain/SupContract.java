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
import cn.com.chaochuang.supplier.reference.ContractStatus;
import cn.com.chaochuang.supplier.reference.ContractStatusConverter;
import cn.com.chaochuang.supplier.reference.Currency;
import cn.com.chaochuang.supplier.reference.CurrencyConverter;
import cn.com.chaochuang.supplier.reference.PaymentMethod;
import cn.com.chaochuang.supplier.reference.PaymentMethodConverter;
import cn.com.chaochuang.supplier.reference.PaymentType;
import cn.com.chaochuang.supplier.reference.PaymentTypeConverter;

@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "contract_id")) })
public class SupContract extends LongIdEntity {


	private static final long serialVersionUID = -1880521123519070156L;

	/** 合同编号 */
    private String   		contractCode;
    
    /** 合同名称 */
    private String   		contractName;
    
    /** 合同甲方 */
    private String   		contractPartytA;
    
    /** 合同乙方 */
    private String   		contractPartytB;
    
    /** 签订日期 */
    private Date   			signedDate;
    
    /** 合同总价 */
    private String   		contractTotalPrice;
    
    /** 甲方责任人 */
    private String   		responsiblePersonA;
    
    /** 乙方责任人 */
    private String   		responsiblePersonB;
    
    /** 合同状态 */
    @Convert(converter = ContractStatusConverter.class)
    private ContractStatus  contractStatus;
    
    /** 收付类型 */
    @Convert(converter = PaymentTypeConverter.class)
    private PaymentType     paymentType;
    
    /** 币种 */
    @Convert(converter = CurrencyConverter.class)
    private Currency      	currency;
    
    /** 支付方式 */
    @Convert(converter = PaymentMethodConverter.class)
    private PaymentMethod   paymentMethod;
    
    /** 开始日期 */
    private Date   			beginDate;
    
    /** 截止日期 */
    private Date   			endDate;
    
    /** 所属项目 */
    private String   		contractEvent;
    
    /** 责任部门 */
    private String   		responsibleDept;
    
    /** 业务员 */
    private String   		salesman;
    
    /** 合同内容 */
    private String   		contractContent;
    
    /** 合同备注 */
    private String   		contractRemark;
    
    /** 登记日期 */
    private Date   	 		createTime;
    
    /** 操作人员*/
    private String   		creatorName;
    
    /** 所属单位ID */
    private Long     		unitId;
    
    /** 所属单位 */
    @ManyToOne
    @JoinColumn(name = "unitId", insertable = false, updatable = false)
    private SupUnit  		unit;

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractPartytA() {
		return contractPartytA;
	}

	public void setContractPartytA(String contractPartytA) {
		this.contractPartytA = contractPartytA;
	}

	public String getContractPartytB() {
		return contractPartytB;
	}

	public void setContractPartytB(String contractPartytB) {
		this.contractPartytB = contractPartytB;
	}

	public Date getSignedDate() {
		return signedDate;
	}

	public void setSignedDate(Date signedDate) {
		this.signedDate = signedDate;
	}

	public String getContractTotalPrice() {
		return contractTotalPrice;
	}

	public void setContractTotalPrice(String contractTotalPrice) {
		this.contractTotalPrice = contractTotalPrice;
	}

	public String getResponsiblePersonA() {
		return responsiblePersonA;
	}

	public void setResponsiblePersonA(String responsiblePersonA) {
		this.responsiblePersonA = responsiblePersonA;
	}

	public String getResponsiblePersonB() {
		return responsiblePersonB;
	}

	public void setResponsiblePersonB(String responsiblePersonB) {
		this.responsiblePersonB = responsiblePersonB;
	}

	public ContractStatus getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(ContractStatus contractStatus) {
		this.contractStatus = contractStatus;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getContractEvent() {
		return contractEvent;
	}

	public void setContractEvent(String contractEvent) {
		this.contractEvent = contractEvent;
	}

	public String getResponsibleDept() {
		return responsibleDept;
	}

	public void setResponsibleDept(String responsibleDept) {
		this.responsibleDept = responsibleDept;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public String getContractContent() {
		return contractContent;
	}

	public void setContractContent(String contractContent) {
		this.contractContent = contractContent;
	}

	public String getContractRemark() {
		return contractRemark;
	}

	public void setContractRemark(String contractRemark) {
		this.contractRemark = contractRemark;
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

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public SupUnit getUnit() {
		return unit;
	}

	public void setUnit(SupUnit unit) {
		this.unit = unit;
	}
	
}
