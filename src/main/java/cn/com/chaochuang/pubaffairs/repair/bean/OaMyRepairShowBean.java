package cn.com.chaochuang.pubaffairs.repair.bean;

import java.util.Date;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.repair.reference.RepairType;

/**
 * @author dengl 2018.08.10
 *
 */

public class OaMyRepairShowBean {

	private String				id;
	
	/** 申请日期 */
    private Date        		createDate;
    private String				createDateShow;

    /** 申请人ID */
    private Long          		creatorId;
    
    /** 申请人名称 */
    private String          	creatorName;
    
    /** 申请人部门ID */
    private Long          		deptId;
    
    /** 申请人部门名称 */
    private String          	deptName;
    
    /** 仪器名称 */
    private String          	deviceName;
    
    /** 型号 */
    private String          	deviceModel;
    
    /** 编号 */
    private String          	deviceNumber;
    
    /** 故障原因 */
    private String          	failureReason;
    
    /** 维修类型 */
    private RepairType      	repairType;
    
    /** 预计费用 */
    private String          	expectCost;
    
    /** 状态 */
    private CarStatus       	status;

    /** 版本号 */
    private Integer         	version_;
    
    /** 流程定义ID */
    private String          	flowId;
    
    /** 流程实例ID */
    private String          	flowInstId;

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

	public String getCreateDateShow() {
		if (this.createDate != null) {
			this.createDateShow = Tools.DATE_MINUTE_FORMAT.format(createDate);
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

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public RepairType getRepairType() {
		return repairType;
	}

	public void setRepairType(RepairType repairType) {
		this.repairType = repairType;
	}

	public String getExpectCost() {
		return expectCost;
	}

	public void setExpectCost(String expectCost) {
		this.expectCost = expectCost;
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
}
