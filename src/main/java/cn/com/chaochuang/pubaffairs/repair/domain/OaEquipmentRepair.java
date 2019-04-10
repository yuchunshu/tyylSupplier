package cn.com.chaochuang.pubaffairs.repair.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatusConverter;
import cn.com.chaochuang.pubaffairs.repair.reference.RepairType;
import cn.com.chaochuang.pubaffairs.repair.reference.RepairTypeConverter;

/**
 * 仪器设备故障报告、维修
 * @author yucs 2018.08.09
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "repair_id")) })
public class OaEquipmentRepair extends StringUuidEntity {

	
	private static final long serialVersionUID = 1845046860689097073L;

	/** 申请日期 */
    private Date        	createDate;

    /** 申请人ID */
    private Long          	creatorId;
    
    @ManyToOne
    @JoinColumn(name = "creatorId" , insertable = false, updatable = false)
    private SysUser         creator; 
    
    /** 申请人名称 */
    private String          creatorName;
    
    /** 申请人部门ID */
    private Long          	deptId;
    @ManyToOne
    @JoinColumn(name = "deptId", updatable = false, insertable = false)
    // 找不到引用的外键数据时忽略
    @NotFound(action = NotFoundAction.IGNORE)
    private SysDepartment 	dept;
    
    /** 申请人部门名称 */
    private String          deptName;
    
    /** 仪器名称 */
    private String          deviceName;
    
    /** 型号 */
    private String          deviceModel;
    
    /** 编号 */
    private String          deviceNumber;
    
    /** 故障原因 */
    private String          failureReason;
    
    /** 维修类型 */
    @Convert(converter = RepairTypeConverter.class)
    private RepairType      repairType;
    
    /** 预计费用 */
    private String          expectCost;
    
    /** 状态 */
    @Convert(converter = CarStatusConverter.class)
    private CarStatus       status;

    /** 版本号 */
    private Integer         version_;
    
    /** 流程定义ID */
    private String          flowId;
    
    /** 流程实例ID */
    private String          flowInstId;

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

	public String getDeviceName() {
		return deviceName;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public RepairType getRepairType() {
		return repairType;
	}

	public String getExpectCost() {
		return expectCost;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public void setRepairType(RepairType repairType) {
		this.repairType = repairType;
	}

	public void setExpectCost(String expectCost) {
		this.expectCost = expectCost;
	}
    
}

