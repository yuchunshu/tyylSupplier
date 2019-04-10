package cn.com.chaochuang.workflow.inst.bean;

import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;

/**
 * @author yuandl 2016-11-25
 *
 */
public class NodeInstCheckBean {
	
	/** 流程实例 */
    private WfFlowInst flowInst;

    /** 环节实例 */
    private WfNodeInst nodeInst;

    /** 处理人 */
    private Long       dealer;

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

    public Long getDealer() {
        return dealer;
    }

    public void setDealer(Long dealer) {
        this.dealer = dealer;
    }

}
