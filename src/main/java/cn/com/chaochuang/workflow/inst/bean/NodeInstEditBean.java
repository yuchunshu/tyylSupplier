package cn.com.chaochuang.workflow.inst.bean;

import java.util.Date;

import cn.com.chaochuang.workflow.reference.WfReadFlag;
import cn.com.chaochuang.workflow.reference.WfDealType;
import cn.com.chaochuang.workflow.reference.WfEndCond;
import cn.com.chaochuang.workflow.reference.WfFlowDir;
import cn.com.chaochuang.workflow.reference.WfInstStatus;

public class NodeInstEditBean {

    private String       id;

    /** 流实例ID */
    private String       flowInstId;

    /** 父环节实例ID */
    private String       parentNodeInstId;

    /** 呈送人：上一步办理人 */
    private Long         preDealerId;

    /** 当前环节 */
    private String       curNodeId;

    /** 下一环节 */
    private String       nextNodeId;

    /** 办结条件 */
    private WfEndCond    endCond;

    /** （用于抢占处理事项，一个人办完 ，其它人失效） */
    private String       exclusiveKey;

    /** 处理类型 */
    private WfDealType   dealType;

    /** 办理候选人 （多个用逗号分开） */
    private String       candidates;

    /** 办理人 */
    private Long         dealerId;

    /** 办理部门 */
    private Long         dealDeptId;

    /** 送达时间 */
    private Date         arrivalTime;

    /** 处理时间 */
    private Date         dealTime;

    /** 流程方向 */
    private WfFlowDir    flowDir;

    /** 实例状态 */
    private WfInstStatus instStatus;

    /** 已读标识 */
    private WfReadFlag   readFlag;

    /** 环节限办时间（分钟） */
    private Long 		 nodeExpiryMinute;
    
    /** 环节预警时间（分钟）*/
    private Long		 nodeWarnMinute;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFlowInstId() {
		return flowInstId;
	}

	public void setFlowInstId(String flowInstId) {
		this.flowInstId = flowInstId;
	}

	public Long getPreDealerId() {
        return preDealerId;
    }

    public void setPreDealerId(Long preDealerId) {
        this.preDealerId = preDealerId;
    }

    public String getCurNodeId() {
        return curNodeId;
    }

    public void setCurNodeId(String curNodeId) {
        this.curNodeId = curNodeId;
    }

    public String getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(String nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    public WfEndCond getEndCond() {
        return endCond;
    }

    public void setEndCond(WfEndCond endCond) {
        this.endCond = endCond;
    }

    public WfDealType getDealType() {
        return dealType;
    }

    public void setDealType(WfDealType dealType) {
        this.dealType = dealType;
    }

    public String getCandidates() {
		return candidates;
	}

	public void setCandidates(String candidates) {
		this.candidates = candidates;
	}

	public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public Long getDealDeptId() {
        return dealDeptId;
    }

    public void setDealDeptId(Long dealDeptId) {
        this.dealDeptId = dealDeptId;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public WfFlowDir getFlowDir() {
        return flowDir;
    }

    public void setFlowDir(WfFlowDir flowDir) {
        this.flowDir = flowDir;
    }

    public WfInstStatus getInstStatus() {
        return instStatus;
    }

    public void setInstStatus(WfInstStatus instStatus) {
        this.instStatus = instStatus;
    }

    public WfReadFlag getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(WfReadFlag readFlag) {
        this.readFlag = readFlag;
    }


	public String getParentNodeInstId() {
		return parentNodeInstId;
	}

	public void setParentNodeInstId(String parentNodeInstId) {
		this.parentNodeInstId = parentNodeInstId;
	}

	public String getExclusiveKey() {
		return exclusiveKey;
	}

	public void setExclusiveKey(String exclusiveKey) {
		this.exclusiveKey = exclusiveKey;
	}

	public Long getNodeExpiryMinute() {
		return nodeExpiryMinute;
	}

	public void setNodeExpiryMinute(Long nodeExpiryMinute) {
		this.nodeExpiryMinute = nodeExpiryMinute;
	}

	public Long getNodeWarnMinute() {
		return nodeWarnMinute;
	}

	public void setNodeWarnMinute(Long nodeWarnMinute) {
		this.nodeWarnMinute = nodeWarnMinute;
	}
}
