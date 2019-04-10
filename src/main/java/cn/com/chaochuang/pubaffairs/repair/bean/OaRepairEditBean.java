package cn.com.chaochuang.pubaffairs.repair.bean;

import java.util.Date;

import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.pubaffairs.car.bean.OfficialCarCommonBean;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.repair.reference.RepairType;

/**
 * @author dengl 2018.08.10
 *
 */

public class OaRepairEditBean extends OfficialCarCommonBean {

	private String				id;
	
	/** 申请日期 */
    private Date        		createDate;

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
    
    /** 下一环节定义 */
    private String          	nextNodeId;
    /** 下个环节的办理人(可以有多个) */
    private String           	nextDealers;
    
    /** 是否修改公文信息 */
    private YesNo              	docEditable;
    
    /** 处室共享 */
    private String              deptShare;
    
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

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}
	
}
