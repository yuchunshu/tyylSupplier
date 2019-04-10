package cn.com.chaochuang.workflow.def.bean;


import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.workflow.reference.WfEditFlag;
import cn.com.chaochuang.workflow.reference.WfEndCond;

/**
 * @author hzr 2016-12-29
 *
 */
public class FlowNodeEditBean {

    private Long   			  id;
    /** 流定义 */
    private String 			  flowId;

    /** 环节定义 */
    private String 			  nodeId;

    /** 下一环节s，多个用逗号分开 */
    private String            nextNodeIds;
    /** 退回环节 */
    private String            backNodeIds;
    /** 默认提交环节 */
    private String            submitNodeId;

    /** 备选接收人s，多个用逗号分开 */
    private String            receiverIds;

    /** 办结条件 */
    private WfEndCond         endCond;
    /** 是否分流 */
    private YesNo             divFlag;
    /** 是否汇聚 */
    private YesNo             comFlag;

    /** 子流程id */
    private String            subflowId;

    /** 功能按钮s，多个用逗号分开 */
    private String            funcBtns;

    /** 编辑标识 */
    private WfEditFlag        editFlag;

    /** 公文信息是否修改 */
    private YesNo             docEditable;

    /** 设置可办理部门*/
    private String            nodeDeptIds;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

	public String getNextNodeIds() {
		return nextNodeIds;
	}

	public void setNextNodeIds(String nextNodeIds) {
		this.nextNodeIds = nextNodeIds;
	}

	public String getBackNodeIds() {
		return backNodeIds;
	}

	public void setBackNodeIds(String backNodeIds) {
		this.backNodeIds = backNodeIds;
	}

	public String getSubmitNodeId() {
		return submitNodeId;
	}

	public void setSubmitNodeId(String submitNodeId) {
		this.submitNodeId = submitNodeId;
	}

	public String getReceiverIds() {
		return receiverIds;
	}

	public void setReceiverIds(String receiverIds) {
		this.receiverIds = receiverIds;
	}


	public WfEndCond getEndCond() {
		return endCond;
	}

	public void setEndCond(WfEndCond endCond) {
		this.endCond = endCond;
	}

	public YesNo getDivFlag() {
		return divFlag;
	}

	public void setDivFlag(YesNo divFlag) {
		this.divFlag = divFlag;
	}

	public YesNo getComFlag() {
		return comFlag;
	}

	public void setComFlag(YesNo comFlag) {
		this.comFlag = comFlag;
	}


	public String getFuncBtns() {
		return funcBtns;
	}

	public void setFuncBtns(String funcBtns) {
		this.funcBtns = funcBtns;
	}

	public WfEditFlag getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(WfEditFlag editFlag) {
		this.editFlag = editFlag;
	}

	public YesNo getDocEditable() {
		return docEditable;
	}

	public void setDocEditable(YesNo docEditable) {
		this.docEditable = docEditable;
	}

	public String getSubflowId() {
		return subflowId;
	}

	public void setSubflowId(String subflowId) {
		this.subflowId = subflowId;
	}

	public String getNodeDeptIds() {
		return nodeDeptIds;
	}

	public void setNodeDeptIds(String nodeDeptIds) {
		this.nodeDeptIds = nodeDeptIds;
	}


}
