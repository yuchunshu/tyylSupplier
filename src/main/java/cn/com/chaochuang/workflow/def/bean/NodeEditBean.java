package cn.com.chaochuang.workflow.def.bean;


import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.workflow.reference.WfEditFlag;
import cn.com.chaochuang.workflow.reference.WfEndCond;
import cn.com.chaochuang.workflow.reference.WfNodeType;

/**
 * @author hzr 2016-12-29
 *
 */
public class NodeEditBean {

    private String			  id;
    /** 流定义 */
    private String 			  flowId;

    /** 环节名称 */
    private String            nodeName;
    /** 环节KEY */
    private String            nodeKey;
    /** 环节类型 */
    private WfNodeType        nodeType;

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

    /** 排序：     =0 表示首 环节 */
    private Integer           sort;

    /**以下属性不在Node属性里*/
    //下一环节s，多个用逗号分开
    private String            nextNodeIds;
    //退回环节
    private String            backNodeIds;
    //默认提交环节
    private String            submitNodeId;
    
    /** 是否是新建环节，用于id重复判断 */
    private YesNo             isNewNode;


    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }


	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeKey() {
		return nodeKey;
	}

	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}

	public WfNodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(WfNodeType nodeType) {
		this.nodeType = nodeType;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public YesNo getIsNewNode() {
		return isNewNode;
	}

	public void setIsNewNode(YesNo isNewNode) {
		this.isNewNode = isNewNode;
	}


}
