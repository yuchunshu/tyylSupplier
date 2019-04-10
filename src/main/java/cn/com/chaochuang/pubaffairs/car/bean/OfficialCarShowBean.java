package cn.com.chaochuang.pubaffairs.car.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dozer.Mapping;

import cn.com.chaochuang.pubaffairs.car.reference.BusinessType;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.workflow.reference.WfInstStatus;

/**
 * @author dengl 2018.08.08
 *
 */
public class OfficialCarShowBean {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private String 				id;
	
	@Mapping("flowInst.entityId")
    private String       		entityId;
	
	@Mapping("flowInst.businessType")
    private String  	 		businessType;
	
	/** 创建时间*/
	private Date 				createDate;
	private String				createDateShow;
	
	/** 创建人ID */
    private Long            	creatorId;
    
    /** 创建人姓名*/
    @Mapping("preDealer.userName")
    private String				creatorName;
    
    /** 所属部门ID */
    private Long            	deptId;
    
    /** 所属部门名称*/
    @Mapping("preDealer.department.deptName")
    private String				deptName;
    
    /** 事由*/
    @Mapping("flowInst.title")
    private String				reason;
    
    /** 用车开始时间*/
    private Date				beginTime;
    private String       		beginTimeShow;
    
    /** 用车结束时间*/
    private Date				endTime;
    private String       		endTimeShow;
    
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
    private BusinessType		type;
    
    /** 实例状态 */
    private WfInstStatus 		instStatus;
    
    @Mapping("flowInst.startTime")
    private Date         		startTime;
    private String       		startTimeShow;
    
    /** 送达时间 */
    private Date         		arrivalTime;
    
    /** 星期*/
    private String				week;

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

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public WfInstStatus getInstStatus() {
		return instStatus;
	}

	public void setInstStatus(WfInstStatus instStatus) {
		this.instStatus = instStatus;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStartTimeShow() {
		if (this.startTime != null) {
			this.startTimeShow = this.sdf.format(startTime);
		}
		return startTimeShow;
	}

	public void setStartTimeShow(String startTimeShow) {
		this.startTimeShow = startTimeShow;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getCreateDateShow() {
		if (this.createDate != null) {
			this.createDateShow = this.sdf.format(createDate);
		}
		return createDateShow;
	}

	public void setCreateDateShow(String createDateShow) {
		this.createDateShow = createDateShow;
	}

	public String getBeginTimeShow() {
		if (this.beginTime != null) {
			this.beginTimeShow = this.sdf.format(beginTime);
		}
		return beginTimeShow;
	}

	public void setBeginTimeShow(String beginTimeShow) {
		this.beginTimeShow = beginTimeShow;
	}

	public String getEndTimeShow() {
		if (this.endTime != null) {
			this.endTimeShow = this.sdf.format(endTime);
		}
		return endTimeShow;
	}

	public void setEndTimeShow(String endTimeShow) {
		this.endTimeShow = endTimeShow;
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

	public BusinessType getType() {
		return type;
	}

	public void setType(BusinessType type) {
		this.type = type;
	}
}
