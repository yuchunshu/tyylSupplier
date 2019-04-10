package cn.com.chaochuang.workflow.def.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;

/**
 * 流程归属部门定义
 * 
 * @author yuchunshu 2017-11-21
 *
 */
@Entity
@Table(name = "wf_flow_dept")
public class WfFlowDept extends LongIdEntity {

    /**  */
    private static final long serialVersionUID = -1526568614534221918L;

    /** 流程定义 */
    private String            flowId;
    @ManyToOne
    @JoinColumn(name = "flowId", insertable = false, updatable = false)
    private WfFlow            flow;

    /** 所属部门 */
    private Long              depId;

    /** 所属祖先部门 */
    private Long              ancestorDepId;


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

	public Long getDepId() {
		return depId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

	public Long getAncestorDepId() {
		return ancestorDepId;
	}

	public void setAncestorDepId(Long ancestorDepId) {
		this.ancestorDepId = ancestorDepId;
	}

}
