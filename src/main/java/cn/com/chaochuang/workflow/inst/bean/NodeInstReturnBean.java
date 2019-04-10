package cn.com.chaochuang.workflow.inst.bean;

import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;

/**
 * 环节退回
 *
 * @author yuandl 2016-11-26
 *
 */
public class NodeInstReturnBean {

    /** 业务实体ID（比如公文id） */
    private String     entityId;

    /** 流程实例ID */
    private String     flowInstId;

    /** 当前环节实例Id */
    private WfNodeInst nodeInst;

    /** 处理人 */
    private Long       dealerId;

    /** 退回意见 */
    private String     opinion;


    public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

    public String getFlowInstId() {
		return flowInstId;
	}

	public void setFlowInstId(String flowInstId) {
		this.flowInstId = flowInstId;
	}

	public WfNodeInst getNodeInst() {
		return nodeInst;
	}

	public void setNodeInst(WfNodeInst nodeInst) {
		this.nodeInst = nodeInst;
	}

	public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }


}
