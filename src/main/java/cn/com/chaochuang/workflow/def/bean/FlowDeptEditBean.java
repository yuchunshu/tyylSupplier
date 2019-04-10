package cn.com.chaochuang.workflow.def.bean;

/**
 * @author yuchunshu 2017-11-22
 *
 */
public class FlowDeptEditBean {

    private Long      id;
    /** 流定义 */
    private String    flowId;

    /** 所属部门 */
    private Long      depId;

    /** 所属祖先部门 */
    private Long      ancestorDepId;

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

	public Long getDepId() {
		return depId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

	public Long getAncestorDepId() {
		return ancestorDepId;
	}

	public void setAncestorDepId(Long ancestorDepId) {
		this.ancestorDepId = ancestorDepId;
	}

}
