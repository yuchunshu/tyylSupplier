package cn.com.chaochuang.workflow.inst.bean;

import java.util.Date;

import cn.com.chaochuang.workflow.reference.WfApprResult;

public class AuditOpinionQueryBean {

    /** 流程实例 */
    private String       flowInstId;

    /** 环节实例 */
    private String       nodeInstId;

    /** 所属环节 */
    private String       nodeId;

    /** 审批意见 */
    private String       apprOpinion;

    /** 审批人 */
    private Long         approverId;

    /** 审批时间 */
    private Date         apprTime;

    /** 审批结果 */
    private WfApprResult apprResult;




    public String getFlowInstId() {
		return flowInstId;
	}

	public void setFlowInstId(String flowInstId) {
		this.flowInstId = flowInstId;
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


}
