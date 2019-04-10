package cn.com.chaochuang.pubaffairs.car.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dozer.Mapping;

import cn.com.chaochuang.pubaffairs.car.reference.BusinessType;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;

/**
 * @author dengl 2018.08.08
 *
 */
public class OfficialMyCarShowBean {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private String 				id;
	
	/** 创建时间*/
	private Date 				createDate;
	private String				createDateShow;
	
	/** 创建人ID */
    private Long            	creatorId;
    
    /** 创建人姓名*/
    @Mapping("creator.userName")
    private String				creatorName;
    
    /** 所属部门ID */
    private Long            	deptId;
    
    /** 所属部门名称*/
    @Mapping("dept.deptName")
    private String				deptName;
    
    /** 事由*/
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
    private BusinessType		businessType;
    
    /** 星期*/
    private String				week;
    
	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

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

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
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

	public String getBeginTimeShow() {
		if (this.beginTime != null) {
			this.beginTimeShow = this.sdf.format(beginTime);
		}
		return beginTimeShow;
	}

	public void setBeginTimeShow(String beginTimeShow) {
		this.beginTimeShow = beginTimeShow;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public String getCreateDateShow() {
		if (this.createDate != null) {
			this.createDateShow = this.sdf.format(createDate);
		}
		return createDateShow;
	}

	public void setCreateDateShow(String createDateShow) {
		this.createDateShow = createDateShow;
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

	public String getCreatorName() {
		return creatorName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}
