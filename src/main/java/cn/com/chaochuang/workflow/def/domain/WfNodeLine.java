/**
 *
 */
package cn.com.chaochuang.workflow.def.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;

/**
 * 流程环节之间的流向设置（线条）
 * @author hzr 2017年10月12日
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
@Table(name = "wf_node_line")
public class WfNodeLine extends StringUuidEntity {

	private static final long serialVersionUID = -4234981151276004829L;
	/** 流程定义 */
    private String            flowId;
    @ManyToOne
    @JoinColumn(name = "flowId", insertable = false, updatable = false)
    private WfFlow            flow;

    /** 起始环节 */
    private String            fromNodeId;

    /** 目标环节 */
    private String            toNodeId;
    @ManyToOne
    @JoinColumn(name = "toNodeId", insertable = false, updatable = false)
    private WfNode            toNode;

    /** 流向标志：默认0正向， 1反向（即退回） */
    private String            dirFlag;

    /** 是否自动提交 : 1自动，其它值为否*/
    private String            autoSubmit;

    /** 流向跳转条件设置 */
    private String            dirCond;

    /** 流向名称*/
    private String            lineTitle;

    /** 备注 */
    private String            remark;


	public WfNodeLine() {

	}

	public WfNodeLine(String flowId, String fromNodeId, String toNodeId, String dirFlag) {
		super();
		this.flowId = flowId;
		this.fromNodeId = fromNodeId;
		this.toNodeId = toNodeId;
		this.dirFlag = dirFlag;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public WfFlow getFlow() {
		return flow;
	}

	public void setFlow(WfFlow flow) {
		this.flow = flow;
	}

	public String getFromNodeId() {
		return fromNodeId;
	}

	public void setFromNodeId(String fromNodeId) {
		this.fromNodeId = fromNodeId;
	}

	public WfNode getToNode() {
		return toNode;
	}

	public void setToNode(WfNode toNode) {
		this.toNode = toNode;
	}

	public String getToNodeId() {
		return toNodeId;
	}

	public void setToNodeId(String toNodeId) {
		this.toNodeId = toNodeId;
	}

	public String getDirFlag() {
		return dirFlag;
	}

	public void setDirFlag(String dirFlag) {
		this.dirFlag = dirFlag;
	}

	public String getAutoSubmit() {
		return autoSubmit;
	}

	public void setAutoSubmit(String autoSubmit) {
		this.autoSubmit = autoSubmit;
	}

	public String getDirCond() {
		return dirCond;
	}

	public void setDirCond(String dirCond) {
		this.dirCond = dirCond;
	}

	public String getLineTitle() {
		return lineTitle;
	}

	public void setLineTitle(String lineTitle) {
		this.lineTitle = lineTitle;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}



}
