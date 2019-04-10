package cn.com.chaochuang.workflow.def.bean;


public class NodeDeptEditBean {

    private Long      id;
    /** 流定义 */
    private String    flowId;

    /** 环节定义 */
    private String    nodeId;

    /** 接收部门 */
    private Long      receiveDepId;

    /** 接收祖先部门 */
    private Long      receiveAncestorDep;

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

    public Long getReceiveDepId() {
        return receiveDepId;
    }

    public void setReceiveDepId(Long receiveDepId) {
        this.receiveDepId = receiveDepId;
    }

    public Long getReceiveAncestorDep() {
        return receiveAncestorDep;
    }

    public void setReceiveAncestorDep(Long receiveAncestorDep) {
        this.receiveAncestorDep = receiveAncestorDep;
    }

}
