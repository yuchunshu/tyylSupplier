package cn.com.chaochuang.pubaffairs.leave.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Convert;

import org.dozer.Mapping;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatusConverter;
import cn.com.chaochuang.pubaffairs.leave.reference.LeaveType;
import cn.com.chaochuang.pubaffairs.leave.reference.LeaveTypeConverter;
import cn.com.chaochuang.workflow.reference.WfInstStatus;

/**
 * @author dengl 2018.08.08
 *
 */

public class OaLeaveQueryBean {
	
	
	private String			id;
	
	/** 申请日期 */
    private Date        	createDate;
    private String       	createDateShow;

    /** 申请人ID */
    private Long          	creatorId;
    
    /** 申请人名称 */
    private String          creatorName;
    
    /** 申请人部门ID */
    private Long          	deptId;
    
    /** 申请人部门名称 */
    private String          deptName;
    
    /** 假期类型 */
    @Convert(converter = LeaveTypeConverter.class)
    private LeaveType       leaveType;

    /** 请假事由 */
    private String   		reason;

    /** 请假天数 */
    private BigDecimal   	leaveDay;

    /** 休假开始时间 */
    private Date	        beginTime;
    private String       	beginTimeShow;

    /** 休假结束时间 */
    private Date	        endTime;
    private String       	endTimeShow;
    
    /** 状态 */
    @Convert(converter = CarStatusConverter.class)
    private CarStatus       status;

    /** 版本号 */
    private Integer         version_;
    
    /** 流程定义ID */
    private String          flowId;
    
    /** 流程实例ID */
    private String          flowInstId;
    
    /** 标识当前登录人是否处理过这条记录 */
    private String          doneFlag;
    
    @Mapping("flowInst.entityId")
    private String       	entityId;
	
	@Mapping("flowInst.businessType")
    private String  	 	businessType;
	
	/** 实例状态 */
    private WfInstStatus 	instStatus;
    
    @Mapping("flowInst.startTime")
    private Date         	startTime;
    private String       	startTimeShow;
    
    /** 送达时间 */
    private Date         	arrivalTime;
    
    private String 			wfNodeInstId;

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

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public BigDecimal getLeaveDay() {
		return leaveDay;
	}

	public void setLeaveDay(BigDecimal leaveDay) {
		this.leaveDay = leaveDay;
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

	public String getDoneFlag() {
		return doneFlag;
	}

	public void setDoneFlag(String doneFlag) {
		this.doneFlag = doneFlag;
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

	public String getWfNodeInstId() {
		return wfNodeInstId;
	}

	public void setWfNodeInstId(String wfNodeInstId) {
		this.wfNodeInstId = wfNodeInstId;
	}
	
	public String getBeginTimeShow() {
		if (this.beginTime != null) {
			this.beginTimeShow = Tools.DATE_MINUTE_FORMAT.format(beginTime);
		}
		return beginTimeShow;
	}

	public String getEndTimeShow() {
		if (this.endTime != null) {
			this.endTimeShow = Tools.DATE_MINUTE_FORMAT.format(endTime);
		}
		return endTimeShow;
	}
	
	public String getCreateDateShow() {
		if (this.createDate != null) {
			this.createDateShow = Tools.DATE_MINUTE_FORMAT.format(createDate);
		}
		return createDateShow;
	}
}
