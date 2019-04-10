package cn.com.chaochuang.workflow.inst.bean;

import cn.com.chaochuang.workflow.reference.WfReadFlag;
import cn.com.chaochuang.workflow.reference.WfDealType;
import cn.com.chaochuang.workflow.reference.WfEndCond;
import cn.com.chaochuang.workflow.reference.WfFlowDir;
import cn.com.chaochuang.workflow.reference.WfInstStatus;

/**
 * @author yuandl 2016-11-25
 *
 */
public class NodeInstQueryBean {

    /** 流实例ID */
    private String       flowInstId;

    /** 呈送人：上一步办理人 */
    private Long         preDealerId;

    /** 当前环节 */
    private String       curNodeId;

    /** 环节in */
    private String       inCurNodeIds;

    /** 下一环节 */
    private String       nextNodeId;

    /** 办结条件 */
    private WfEndCond    endCond;

    /** 处理类型 */
    private WfDealType   dealType;

    /** 办理人 */
    private Long         dealerId;

    /** 办理部门 */
    private Long         dealDeptId;

    /** 流程方向 */
    private WfFlowDir    flowDir;

    /** 实例状态 */
    private WfInstStatus nodeInstStatus;

    /** 已读标识 */
    private WfReadFlag   readFlag;



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

    public WfFlowDir getFlowDir() {
        return flowDir;
    }

    public void setFlowDir(WfFlowDir flowDir) {
        this.flowDir = flowDir;
    }

    public WfInstStatus getNodeInstStatus() {
        return nodeInstStatus;
    }

    public void setNodeInstStatus(WfInstStatus nodeInstStatus) {
        this.nodeInstStatus = nodeInstStatus;
    }

    public WfReadFlag getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(WfReadFlag readFlag) {
        this.readFlag = readFlag;
    }

    public String getInCurNodeIds() {
        return inCurNodeIds;
    }

    public void setInCurNodeIds(String inCurNodeIds) {
        this.inCurNodeIds = inCurNodeIds;
    }

}
