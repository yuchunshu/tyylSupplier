package cn.com.chaochuang.workflow.inst.domain;

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
@Table(name = "wf_flow_inst")
public class WfFlowInst extends StringUuidEntity {

    /**  */
    private static final long serialVersionUID = -3543550105960266797L;

    /** 流程定义 */
    private String            flowId;
    @ManyToOne
    @JoinColumn(name = "flowId", insertable = false, updatable = false)
    private WfFlow            flow;

    /** 父流程实例ID，用于子流程*/
    private String            parentFlowInstId;

    /** 业务实体ID（比如公文id） */
    private String            entityId;

    /** 标题 */
    private String            title;

    /** 自然编号  */
    private String            sncode;

    /** 业务类型(收文或发文等) */
    private String            businessType;

    /** 紧急程度*/
    private String            urgencyLevel;

    /** 当前节点 */
    private String            curNodeId;
    @ManyToOne
    @JoinColumn(name = "curNodeId", insertable = false, updatable = false)
    private WfNode            curNode;

    /** 开始时间 */
    private Date              startTime;

    /** 结束时间 */
    private Date              endTime;

    /** 创建人*/
    private Long              creatorId;

    /** 办结人*/
    private Long              enderId;

    /** 办结时限（截止日期）*/
    private Date              expiryDate;

    /** 实例状态 */
    @Convert(converter = WfInstStatusConverter.class)
    private WfInstStatus      instStatus;

    /** 转办公文id */
    private String            parentEntityId;
    
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

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getEnderId() {
		return enderId;
	}

	public void setEnderId(Long enderId) {
		this.enderId = enderId;
	}

	public String getSncode() {
		return sncode;
	}

	public void setSncode(String sncode) {
		this.sncode = sncode;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getUrgencyLevel() {
		return urgencyLevel;
	}

	public void setUrgencyLevel(String urgencyLevel) {
		this.urgencyLevel = urgencyLevel;
	}

	public String getParentEntityId() {
		return parentEntityId;
	}

	public void setParentEntityId(String parentEntityId) {
		this.parentEntityId = parentEntityId;
	}


}
