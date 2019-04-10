package cn.com.chaochuang.workflow.def.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.NotGenerateIdEntity;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.reference.YesNoConverter;
import cn.com.chaochuang.workflow.reference.WfEditFlag;
import cn.com.chaochuang.workflow.reference.WfEditFlagConverter;
import cn.com.chaochuang.workflow.reference.WfEndCond;
import cn.com.chaochuang.workflow.reference.WfEndCondConverter;
import cn.com.chaochuang.workflow.reference.WfNodeType;
import cn.com.chaochuang.workflow.reference.WfNodeTypeConverter;

/**
 * 流程环节定义
 *
 * @author hzr 2016-12-29
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "node_id")) })
@Table(name = "wf_node")
public class WfNode extends NotGenerateIdEntity<String> {

    private static final long serialVersionUID = -8305994979282627347L;

    /** 流程定义 */
    private String            flowId;
    @ManyToOne
    @JoinColumn(name = "flowId", insertable = false, updatable = false)
    private WfFlow            flow;

    /** 环节名称 */
    private String            nodeName;
    /** 环节KEY */
    private String            nodeKey;
    /** 环节类型 */
    @Convert(converter = WfNodeTypeConverter.class)
    private WfNodeType        nodeType;

    /** 办结条件 */
    @Convert(converter = WfEndCondConverter.class)
    private WfEndCond         endCond;
    /** 是否分流 */
    @Convert(converter = YesNoConverter.class)
    private YesNo             divFlag;
    /** 是否汇聚 */
    @Convert(converter = YesNoConverter.class)
    private YesNo             comFlag;

    /** 子流程id */
    private String            subflowId;

    /** 功能按钮s，多个用逗号分开 */
    private String            funcBtns;

    /** 编辑标识 */
    @Convert(converter = WfEditFlagConverter.class)
    private WfEditFlag        editFlag;

    /** 公文信息是否修改 */
    @Convert(converter = YesNoConverter.class)
    private YesNo             docEditable;

    /** 备选接收人s，多个用逗号分开 */
    private String            receiverIds;

    /**
     * 排序号
     * 说明：当该流程的第一个环节Nxxxx保存时，必须在流向表(WfNodeLine)中增加一条该流程的首环节流向记录 N0000-->Nxxxx，表示流程的起点
     * */
    private Integer           sort;


    /**判断是否包含有该功能按钮*/
    public boolean hasFuncBtns(String btn) {
    	return this.funcBtns.indexOf(btn) != -1;
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


	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}


}
