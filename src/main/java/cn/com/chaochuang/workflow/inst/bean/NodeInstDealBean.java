package cn.com.chaochuang.workflow.inst.bean;

import java.util.Date;

import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.inst.domain.WfAuditOpinion;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;

/**
 * @author yuandl 2016-11-25
 *
 */
public class NodeInstDealBean {

    /** 业务实体ID（比如公文id） */
    private String         entityId;

    /** 标题 */
    private String  	   title;

    /** 自然编号  */
    private String         sncode;

    private String         businessType;

    /** 流程实例 */
    private WfFlowInst     flowInst;

    /** 当前环节ID */
    private String         nodeId;

    /** 流程环节定义 */
    private WfNode     	   node;

    /** 环节实例 */
    private WfNodeInst     nodeInst;

    /** 审批意见 */
    private WfAuditOpinion auditOpinion;

    /** 办理候选人 （多个用逗号分开） */
    private String         candidates;

    /** 当前处理人 */
    private Long           dealer;

    /** 下一步处理人 */
    private String[]       nextDealer;

    /** 下一步处理部门 */
    private Long           nextDealDept;

    /** 下一步环节 */
    private String         nextNodeId;

    /** 待办元素 */
    //private FordoBean      fd;

    /** 处室共享 */
    private String         deptShare;
    
    /** 环节限办时间（分钟） */
    private Long 		   nodeExpiryMinute;
    
    /** 环节预警分钟*/
    private Long		   earlyWarnMinute;
    
    /** 流程限办时间 */
    private Date 		   expiryDate;
    
    /** 紧急程序 */
    private String         urgencyLevel;
    
    public WfFlowInst getFlowInst() {
        return flowInst;
    }

    public void setFlowInst(WfFlowInst flowInst) {
        this.flowInst = flowInst;
    }

    public WfNodeInst getNodeInst() {
        return nodeInst;
    }

    public void setNodeInst(WfNodeInst nodeInst) {
        this.nodeInst = nodeInst;
    }

    public WfAuditOpinion getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(WfAuditOpinion auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getCandidates() {
		return candidates;
	}

	public void setCandidates(String candidates) {
		this.candidates = candidates;
	}

	public Long getDealer() {
        return dealer;
    }

    public void setDealer(Long dealer) {
        this.dealer = dealer;
    }

    public String[] getNextDealer() {
        return nextDealer;
    }

    public void setNextDealer(String[] nextDealer) {
        this.nextDealer = nextDealer;
    }

    // public Long getNextDealDept() {
    // return nextDealDept;
    // }
    //
    // public void setNextDealDept(Long nextDealDept) {
    // this.nextDealDept = nextDealDept;
    // }

    public String getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(String nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    public WfNode getNode() {
		return node;
	}

	public void setNode(WfNode node) {
		this.node = node;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Long getNextDealDept() {
        return nextDealDept;
    }

    public void setNextDealDept(Long nextDealDept) {
        this.nextDealDept = nextDealDept;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSncode() {
		return sncode;
	}

	public void setSncode(String sncode) {
		this.sncode = sncode;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getDeptShare() {
		return deptShare;
	}

	public void setDeptShare(String deptShare) {
		this.deptShare = deptShare;
	}

	public Long getNodeExpiryMinute() {
		return nodeExpiryMinute;
	}

	public void setNodeExpiryMinute(Long nodeExpiryMinute) {
		this.nodeExpiryMinute = nodeExpiryMinute;
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

	public Long getEarlyWarnMinute() {
		return earlyWarnMinute;
	}

	public void setEarlyWarnMinute(Long earlyWarnMinute) {
		this.earlyWarnMinute = earlyWarnMinute;
	}
}
