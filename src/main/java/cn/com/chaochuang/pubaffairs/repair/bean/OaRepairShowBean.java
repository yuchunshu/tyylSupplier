package cn.com.chaochuang.pubaffairs.repair.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dozer.Mapping;

import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.workflow.reference.WfInstStatus;

/**
 * @author dengl 2018.08.10
 *
 */

public class OaRepairShowBean {

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
    
    /** 故障原因*/
    @Mapping("flowInst.title")
    private String				failureReason;
    
    /** 仪器名称*/
    private String				deviceName;
    
    /** 状态*/
    private CarStatus			status;
    
    /** 版本号*/
    private Integer				version_;
    
    /** 流程定义ID*/
    private String				flowId;
    
    /** 流程实例ID*/
    private String				flowInstId;
    
    /** 实例状态 */
    private WfInstStatus 		instStatus;
    
    @Mapping("flowInst.startTime")
    private Date         		startTime;
    private String       		startTimeShow;
    
    /** 送达时间 */
    private Date         		arrivalTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
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
}
