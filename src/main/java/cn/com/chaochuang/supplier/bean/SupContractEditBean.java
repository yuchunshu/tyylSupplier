package cn.com.chaochuang.supplier.bean;

import java.util.Date;


import cn.com.chaochuang.supplier.reference.ContractStatus;
import cn.com.chaochuang.supplier.reference.Currency;
import cn.com.chaochuang.supplier.reference.PaymentMethod;
import cn.com.chaochuang.supplier.reference.PaymentType;

public class SupContractEditBean {

    // Fields
    private Long     		id;
    
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
    private ContractStatus  contractStatus;
    
    /** 收付类型 */
    private PaymentType     paymentType;
    
    /** 币种 */
    private Currency      	currency;
    
    /** 支付方式 */
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
    
    /** 附件IDs 通过','分割 */
    private String      	attach;

	public Long getId() {
		return id;
	}

	public String getContractCode() {
		return contractCode;
	}

	public String getContractName() {
		return contractName;
	}

	public String getContractPartytA() {
		return contractPartytA;
	}

	public String getContractPartytB() {
		return contractPartytB;
	}

	public Date getSignedDate() {
		return signedDate;
	}

	public String getContractTotalPrice() {
		return contractTotalPrice;
	}

	public String getResponsiblePersonA() {
		return responsiblePersonA;
	}

	public String getResponsiblePersonB() {
		return responsiblePersonB;
	}

	public ContractStatus getContractStatus() {
		return contractStatus;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public Currency getCurrency() {
		return currency;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getContractEvent() {
		return contractEvent;
	}

	public String getResponsibleDept() {
		return responsibleDept;
	}

	public String getSalesman() {
		return salesman;
	}

	public String getContractContent() {
		return contractContent;
	}

	public String getContractRemark() {
		return contractRemark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public Long getUnitId() {
		return unitId;
	}

	public String getAttach() {
		return attach;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public void setContractPartytA(String contractPartytA) {
		this.contractPartytA = contractPartytA;
	}

	public void setContractPartytB(String contractPartytB) {
		this.contractPartytB = contractPartytB;
	}

	public void setSignedDate(Date signedDate) {
		this.signedDate = signedDate;
	}

	public void setContractTotalPrice(String contractTotalPrice) {
		this.contractTotalPrice = contractTotalPrice;
	}

	public void setResponsiblePersonA(String responsiblePersonA) {
		this.responsiblePersonA = responsiblePersonA;
	}

	public void setResponsiblePersonB(String responsiblePersonB) {
		this.responsiblePersonB = responsiblePersonB;
	}

	public void setContractStatus(ContractStatus contractStatus) {
		this.contractStatus = contractStatus;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setContractEvent(String contractEvent) {
		this.contractEvent = contractEvent;
	}

	public void setResponsibleDept(String responsibleDept) {
		this.responsibleDept = responsibleDept;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public void setContractContent(String contractContent) {
		this.contractContent = contractContent;
	}

	public void setContractRemark(String contractRemark) {
		this.contractRemark = contractRemark;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

}
