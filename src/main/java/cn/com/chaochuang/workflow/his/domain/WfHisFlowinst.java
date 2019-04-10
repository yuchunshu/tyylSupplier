package cn.com.chaochuang.workflow.his.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.workflow.def.domain.WfFlow;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.reference.WfInstStatus;
import cn.com.chaochuang.workflow.reference.WfInstStatusConverter;

/**
 * 流程实例
 *
 * @author hzr 2017-3-24
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "flow_inst_id")) })
@Table(name = "wf_his_flowinst")
public class WfHisFlowinst extends StringUuidEntity {

	private static final long serialVersionUID = -8826610224295519983L;

	/** 流程定义 */
    private String            flowId;
    @ManyToOne
    @JoinColumn(name = "flowId", insertable = false, updatable = false)
    private WfFlow            flow;

    /** 父流程实例ID，用于子流程*/
    private String            parentFlowInstId;

    /** 业务实体ID（比如公文id） */
    private String            entityId;

    /** 公文标题 */
    private String            title;

    /** 当前节点 */
    private String            curNodeId;
    @ManyToOne
    @JoinColumn(name = "curNodeId", insertable = false, updatable = false)
    private WfNode            curNode;

    /** 开始时间 */
    private Date              startTime;

    /** 结束时间 */
    private Date              endTime;

    /** 实例状态 */
    @Convert(converter = WfInstStatusConverter.class)
    private WfInstStatus      instStatus;

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


	public String getParentFlowInstId() {
		return parentFlowInstId;
	}

	public void setParentFlowInstId(String parentFlowInstId) {
		this.parentFlowInstId = parentFlowInstId;
	}


    public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getCurNodeId() {
        return curNodeId;
    }

    public void setCurNodeId(String curNodeId) {
        this.curNodeId = curNodeId;
    }

    public WfNode getCurNode() {
        return curNode;
    }

    public void setCurNode(WfNode curNode) {
        this.curNode = curNode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public WfInstStatus getInstStatus() {
        return instStatus;
    }

    public void setInstStatus(WfInstStatus instStatus) {
        this.instStatus = instStatus;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
