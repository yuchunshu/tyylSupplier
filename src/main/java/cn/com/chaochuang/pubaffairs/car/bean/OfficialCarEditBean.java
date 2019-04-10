package cn.com.chaochuang.pubaffairs.car.bean;

import java.util.Date;

import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.pubaffairs.car.reference.BusinessType;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;

/**
 * @author dengl 2018.08.08
 *
 */
public class OfficialCarEditBean extends OfficialCarCommonBean {
	
	private String 				id;
	
	/** 创建时间*/
	private Date 				createDate;
	
	/** 创建人ID */
    private Long            	creatorId;
    
    /** 创建人姓名*/
    private String				creatorName;
    
    /** 所属部门ID */
    private Long            	deptId;
    
    /** 所属部门名称*/
    private String				deptName;
    
    /** 事由*/
    private String				reason;
    
    /** 用车开始时间*/
    private Date				beginTime;
    
    /** 用车结束时间*/
    private Date				endTime;
    
    /** 车牌号码*/
    private String				carNumber;
    
    /** 驾驶员*/
    private	String				driver;
    
    /** 状态*/
    private CarStatus			status;
    
    /** 版本号*/
    private Integer				version_;
    
    /** 流程定义ID*/
    private String				flowId;
    
    /** 流程实例ID*/
    private String				flowInstId;
    
    /** 使用地点（目的地）*/
    private String				rentalPlace;
    
    /** 出发地*/
    private String				departurePlace;
    
    /** 业务类型*/
    private BusinessType		businessType;
    
    /** 下一环节定义 */
    private String          	nextNodeId;
    /** 下个环节的办理人(可以有多个) */
    private String           	nextDealers;
    
    /** 是否修改公文信息 */
    private YesNo              	docEditable;
    
    /** 处室共享 */
    private String              deptShare;
    
    /** 星期*/
    private String				week;
    
    /** 公文类型*/
    private String				type;
    
    /** 附件IDs 通过','分割 */
    private String      		attach;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public CarStatus getStatus() {
		return status;
	}

	public void setStatus(CarStatus status) {
		this.status = status;
	}

	public Integer getVersion_() {
		return version_;
	}

	public void setVersion_(Integer version_) {
		this.version_ = version_;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFlowInstId() {
		return flowInstId;
	}

	public void setFlowInstId(String flowInstId) {
		this.flowInstId = flowInstId;
	}

	public String getRentalPlace() {
		return rentalPlace;
	}

	public void setRentalPlace(String rentalPlace) {
		this.rentalPlace = rentalPlace;
	}

	public String getNextNodeId() {
		return nextNodeId;
	}

	public void setNextNodeId(String nextNodeId) {
		this.nextNodeId = nextNodeId;
	}

	public String getNextDealers() {
		return nextDealers;
	}

	public void setNextDealers(String nextDealers) {
		this.nextDealers = nextDealers;
	}

	public YesNo getDocEditable() {
		return docEditable;
	}

	public void setDocEditable(YesNo docEditable) {
		this.docEditable = docEditable;
	}

	public String getDeptShare() {
		return deptShare;
	}

	public void setDeptShare(String deptShare) {
		this.deptShare = deptShare;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getDeparturePlace() {
		return departurePlace;
	}

	public void setDeparturePlace(String departurePlace) {
		this.departurePlace = departurePlace;
	}

	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}
	
}
