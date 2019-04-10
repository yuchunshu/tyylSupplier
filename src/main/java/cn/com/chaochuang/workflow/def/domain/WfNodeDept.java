package cn.com.chaochuang.workflow.def.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;

/**
 * 环节接收部门定义
 * 
 * @author hzr 2016-12-24
 *
 */
@Entity
@Table(name = "wf_node_dept")
public class WfNodeDept extends LongIdEntity {

    /**  */
    private static final long serialVersionUID = -1526568614534221918L;

    /** 流程定义 */
    private String            flowId;
    @ManyToOne
    @JoinColumn(name = "flowId", insertable = false, updatable = false)
    private WfFlow            flow;

    /** 环节定义 */
    private String            nodeId;
    @ManyToOne
    @JoinColumn(name = "nodeId", insertable = false, updatable = false)
    private WfNode            node;

    /** 接收部门 */
    private Long              receiveDepId;

    /** 接收祖先部门 */
    private Long              receiveAncestorDep;
    
    /** 职务ID*/
    private String 			  dutyIds;
     

    public String getDutyIds() {
		return dutyIds;
	}

	public void setDutyIds(String dutyIds) {
		this.dutyIds = dutyIds;
	}

	public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public WfFlow getFlow() {
        return flow;
    }

    public void setFlow(WfFlow flow) {
        this.flow = flow;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public WfNode getNode() {
        return node;
    }

    public void setNode(WfNode node) {
        this.node = node;
    }


    public Long getReceiveDepId() {
        return receiveDepId;
    }

    public void setReceiveDepId(Long receiveDepId) {
        this.receiveDepId = receiveDepId;
    }

    public Long getReceiveAncestorDep() {
        return receiveAncestorDep;
    }

    public void setReceiveAncestorDep(Long receiveAncestorDep) {
        this.receiveAncestorDep = receiveAncestorDep;
    }

}
