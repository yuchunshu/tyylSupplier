package cn.com.chaochuang.workflow.inst.bean;

import java.util.Date;

import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;

/**
 * @author yuandl 2016-11-26
 *
 */
public class FlowInstCompleteBean {

    /** 业务实体ID（比如公文id） */
    private String     entityId;

    /** 流程实例 */
    private WfFlowInst flowInst;

    /** 当前环节Id */
    private String     curNodeId;

    /** 处理人 */
    private Long       dealerId;

    /** 意见 */
    private String     opinion;

    /** 意见日期 */
    private Date       opinionDate;


    public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public WfFlowInst getFlowInst() {
        return flowInst;
    }

    public void setFlowInst(WfFlowInst flowInst) {
        this.flowInst = flowInst;
    }

    public String getCurNodeId() {
        return curNodeId;
    }

    public void setCurNodeId(String curNodeId) {
        this.curNodeId = curNodeId;
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

    public Date getOpinionDate() {
        return opinionDate;
    }

    public void setOpinionDate(Date opinionDate) {
        this.opinionDate = opinionDate;
    }


}
