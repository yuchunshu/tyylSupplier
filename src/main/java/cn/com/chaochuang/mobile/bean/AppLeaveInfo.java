package cn.com.chaochuang.mobile.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.leave.reference.LeaveType;
import cn.com.chaochuang.workflow.def.domain.WfNode;

/**
 * @author dengl 2018.8.28
 *
 */
public class AppLeaveInfo {

	private String 				id;
	
	/** 创建时间*/
	private Date 				createDate;
	private String				createDateShow;
	
	/** 创建人ID */
    private Long            	creatorId;
    
    /** 创建人姓名*/
    private String				creatorName;
    
    /** 所属部门ID */
    private Long            	deptId;
    
    /** 所属部门名称*/
    private String				deptName;
    
    /** 假期类型 */
    private LeaveType       	leaveType;

    /** 请假天数 */
    private BigDecimal   		leaveDay;
    
    /** 休假开始时间*/
    private Date				beginTime;
    private String       		beginTimeShow;
    
    /** 休假结束时间*/
    private Date				endTime;
    private String       		endTimeShow;
    
    /** 状态*/
    private CarStatus			status;
    
    /** 版本号*/
    private Integer				version_;
    
    /** 流程定义ID*/
    private String				flowId;
    
    /** 流程实例ID*/
    private String				flowInstId;
   
    /** 请假事由 */
    private String   			reason;
    
    /** 功能按钮列表 */
    private List<String>    	funcList;
    
    /** 可回退的环节 */
    private List<WfNode>    	backNodeList;

    /** 审批意见 */
    private String          	opinion;
    
    /** 当前环节ID */
    private String          	curNodeId;
    
    /** 当前环节实例ID */
    private String          	curNodeInstId;
    
    /** 附件列表 */
    private List<SysAttach>  	attachList;


    public Integer getVersion_() {
        return version_;
    }

    public void setVersion_(Integer version_) {
        this.version_ = version_;
    }

    public List<String> getFuncList() {
        return funcList;
    }

    public void setFuncList(List<String> funcList) {
        this.funcList = funcList;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getCurNodeId() {
        return curNodeId;
    }

    public void setCurNodeId(String curNodeId) {
        this.curNodeId = curNodeId;
    }

    public List<WfNode> getBackNodeList() {
        return backNodeList;
    }

    public void setBackNodeList(List<WfNode> backNodeList) {
        this.backNodeList = backNodeList;
    }

    public String getCurNodeInstId() {
        return curNodeInstId;
    }

    public void setCurNodeInstId(String curNodeInstId) {
        this.curNodeInstId = curNodeInstId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
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

	public String getFlowInstId() {
		return flowInstId;
	}

	public void setFlowInstId(String flowInstId) {
		this.flowInstId = flowInstId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getBeginTimeShow() {
		if (this.beginTime != null) {
			this.beginTimeShow = Tools.DATE_MINUTE_FORMAT.format(beginTime);
		}
		return beginTimeShow;
	}

	public void setBeginTimeShow(String beginTimeShow) {
		this.beginTimeShow = beginTimeShow;
	}

	public String getEndTimeShow() {
		if (this.endTime != null) {
			this.endTimeShow = Tools.DATE_MINUTE_FORMAT.format(endTime);
		}
		return endTimeShow;
	}

	public void setEndTimeShow(String endTimeShow) {
		this.endTimeShow = endTimeShow;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public BigDecimal getLeaveDay() {
		return leaveDay;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public void setLeaveDay(BigDecimal leaveDay) {
		this.leaveDay = leaveDay;
	}

	public List<SysAttach> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<SysAttach> attachList) {
		this.attachList = attachList;
	}

}
