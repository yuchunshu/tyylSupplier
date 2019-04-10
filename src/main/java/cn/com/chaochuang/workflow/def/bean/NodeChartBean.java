package cn.com.chaochuang.workflow.def.bean;

/**
 * 用于自动生成流程图
 *
 * @author rdx 2017-09-28
 *
 */
public class NodeChartBean{


    /** 流程定义 */
    private String            flowId;

    /** 环节定义 */
    private String            nodeId;
    
    /** 环节名字 */
    private String            nodeName;

    /** 下一环节，多个用逗号分开 */
    private String            nextNodeIds;
    
    /** 上一环节，多个用逗号分开 */
    private String            prevNodeIds;  
    
    /** 可退回环节，多个用逗号分开 */
    private String            backNodeIds;

    /** 子流程id */
    private String            subflowId;

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

	public String getPrevNodeIds() {
		return prevNodeIds;
	}

	public void setPrevNodeIds(String prevNodeIds) {
		this.prevNodeIds = prevNodeIds;
	}

	public String getBackNodeIds() {
		return backNodeIds;
	}

	public void setBackNodeIds(String backNodeIds) {
		this.backNodeIds = backNodeIds;
	}

	public String getSubflowId() {
		return subflowId;
	}

	public void setSubflowId(String subflowId) {
		this.subflowId = subflowId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}
