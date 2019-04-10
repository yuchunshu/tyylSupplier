package cn.com.chaochuang.workflow.inst.bean;

import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;

/**
 * 阅知环节完成
 *
 * @author yuandl 2016-11-26
 *
 */
public class NodeInstReadDealBean {

	/** 业务实体ID（比如公文id） */
    private String     entityId;

    /** 流程实例 */
    private WfFlowInst flowInst;

    /** 处理人 */
    private Long       dealerId;

    /** 阅知意见 */
    private String     opinion;

    /** 意见版本 */
    private Integer    opinionVer;


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

    public Integer getOpinionVer() {
        return opinionVer;
    }

    public void setOpinionVer(Integer opinionVer) {
        this.opinionVer = opinionVer;
    }

}
