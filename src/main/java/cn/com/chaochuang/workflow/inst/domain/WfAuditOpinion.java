package cn.com.chaochuang.workflow.inst.domain;

import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.reference.WfApprResult;
import cn.com.chaochuang.workflow.reference.WfApprResultConverter;

/**
 * 环节审批意见
 *
 * @author yuandl 2016-11-25
 *
 */
@Entity
@Table(name = "wf_audit_opinion")
public class WfAuditOpinion extends StringUuidEntity {

    /**  */
    private static final long serialVersionUID = -5401475852302999337L;

    /** 流程实例 */
    private String            flowInstId;
    @ManyToOne
    @JoinColumn(name = "flowInstId", insertable = false, updatable = false)
    private WfFlowInst        flowInst;

    /** 环节实例ID */
    private String            nodeInstId;

    /** 所属环节 */
    private String            nodeId;
    @ManyToOne
    @JoinColumn(name = "nodeId", insertable = false, updatable = false)
    private WfNode            curNode;

    /** 审批意见 */
    private String            apprOpinion;

    /** 审批人 */
    private Long              approverId;
    @ManyToOne
    @JoinColumn(name = "approverId", insertable = false, updatable = false)
    private SysUser           approver;

    /** 审批时间 */
    private Date              apprTime;

    /** 审批结果 */
    @Convert(converter = WfApprResultConverter.class)
    private WfApprResult      apprResult;



    public String getFlowInstId() {
		return flowInstId;
	}

	public void setFlowInstId(String flowInstId) {
		this.flowInstId = flowInstId;
	}

	public WfFlowInst getFlowInst() {
        return flowInst;
    }

    public void setFlowInst(WfFlowInst flowInst) {
        this.flowInst = flowInst;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getApprOpinion() {
        return apprOpinion;
    }

    public void setApprOpinion(String apprOpinion) {
        this.apprOpinion = apprOpinion;
    }

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }

    public SysUser getApprover() {
        return approver;
    }

    public void setApprover(SysUser approver) {
        this.approver = approver;
    }

    public Date getApprTime() {
        return apprTime;
    }

    public void setApprTime(Date apprTime) {
        this.apprTime = apprTime;
    }

    public WfApprResult getApprResult() {
        return apprResult;
    }

    public void setApprResult(WfApprResult apprResult) {
        this.apprResult = apprResult;
    }


	public String getNodeInstId() {
		return nodeInstId;
	}

	public void setNodeInstId(String nodeInstId) {
		this.nodeInstId = nodeInstId;
	}

	public WfNode getCurNode() {
		return curNode;
	}

	public void setCurNode(WfNode curNode) {
		this.curNode = curNode;
	}


}
