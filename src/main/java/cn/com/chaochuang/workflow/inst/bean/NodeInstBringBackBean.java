package cn.com.chaochuang.workflow.inst.bean;

import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;

/**
 * 公文取回
 *
 * @author yuandl 2016-11-27
 *
 */
public class NodeInstBringBackBean {

    /** 业务实体ID（比如公文id） */
    private String     entityId;

    /** 流程实例 */
    private WfFlowInst flowInst;

    /** 当前环节 */
    private String     curNodeId;

    /** 处理人 */
    private SysUser    dealer;


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

    public SysUser getDealer() {
        return dealer;
    }

    public void setDealer(SysUser dealer) {
        this.dealer = dealer;
    }


}
