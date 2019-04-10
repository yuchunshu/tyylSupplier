package cn.com.chaochuang.pubaffairs.leave.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Convert;

import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.pubaffairs.car.bean.OfficialCarCommonBean;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatusConverter;
import cn.com.chaochuang.pubaffairs.leave.reference.LeaveType;
import cn.com.chaochuang.pubaffairs.leave.reference.LeaveTypeConverter;

/**
 * @author dengl 2018.08.08
 *
 */

public class OaLeaveEditBean extends OfficialCarCommonBean{
	
	private String			id;
	
	/** 申请日期 */
    private Date        	createDate;

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

    /** 休假结束时间 */
    private Date	        endTime;
    
    /** 状态 */
    @Convert(converter = CarStatusConverter.class)
    private CarStatus       status;

    /** 版本号 */
    private Integer         version_;
    
    /** 流程定义ID */
    private String          flowId;
    
    /** 流程实例ID */
    private String          flowInstId;
    
    /** 下一环节定义 */
    private String          nextNodeId;
    /** 下个环节的办理人(可以有多个) */
    private String          nextDealers;
    
    /** 是否修改信息 */
    private YesNo           docEditable;
    
    /** 附件IDs 通过','分割 */
    private String      	attach;
    
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

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}
	
}
