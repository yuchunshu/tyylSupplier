package cn.com.chaochuang.workflow.inst.bean;

import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;

/**
 * 阅知环节
 *
 * @author yuandl 2016-11-26
 *
 */
public class NodeInstReadBean {

    /** 业务实体ID（比如公文id） */
    private String     entityId;

    /** 流程实例 */
    private WfFlowInst flowInst;

    /** 发送人 */
    private Long       dealerId;

    /** 接收人 */
    private String     coDealers;



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

    public String getCoDealers() {
        return coDealers;
    }

    public void setCoDealers(String coDealers) {
        this.coDealers = coDealers;
    }

}
