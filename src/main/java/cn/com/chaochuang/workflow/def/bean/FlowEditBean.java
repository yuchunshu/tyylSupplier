package cn.com.chaochuang.workflow.def.bean;

import cn.com.chaochuang.common.reference.AdministrativeLevel;
import cn.com.chaochuang.common.reference.EnableStatus;
import cn.com.chaochuang.common.reference.FlowSort;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * 工作流编辑bean
 *
 * @author yuandl 2016年11月24日
 *
 */
public class FlowEditBean {

    private String                         id;
    /** 工作流名称 */
    private String                         flowName;
    
    /** 父流程ID */
    private String                         parentFlowId;

    /** 流程图jason */
    private String                         flowJason;
    
    /** 流程分类 （1收文 2发文 9其它）*/
    private WfBusinessType                 flowType;
    
    /** 流程分类 （1收文 2发文 9其它）*/
    private WfBusinessType                 otherFlowType;
    
    /** 流程级别(1/2/3级，一般代表省/市/县) */
    private AdministrativeLevel            flowLevel;
    
    /** 所属单位或部门IDs */
    private String                         flowDeptIds;
    
    /** 是否同级共享 （=1为共享）*/
    private YesNo                          shareFlag;

    /** 工作流状态 */
    private EnableStatus                   flowStatus;
    
    /** 是否是新增流程，用于重复ID校验 */
    private YesNo                          isNewFlow;
    
    /** 流程类别*/
    private FlowSort					   flowSort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public EnableStatus getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(EnableStatus flowStatus) {
        this.flowStatus = flowStatus;
    }

	public String getFlowJason() {
		return flowJason;
	}

	public void setFlowJason(String flowJason) {
		this.flowJason = flowJason;
	}

	public String getParentFlowId() {
		return parentFlowId;
	}

	public void setParentFlowId(String parentFlowId) {
		this.parentFlowId = parentFlowId;
	}

	public WfBusinessType getFlowType() {
		return flowType;
	}

	public void setFlowType(WfBusinessType flowType) {
		this.flowType = flowType;
	}

	
	public AdministrativeLevel getFlowLevel() {
		return flowLevel;
	}

	public void setFlowLevel(AdministrativeLevel flowLevel) {
		this.flowLevel = flowLevel;
	}

	public String getFlowDeptIds() {
		return flowDeptIds;
	}

	public void setFlowDeptIds(String flowDeptIds) {
		this.flowDeptIds = flowDeptIds;
	}

	public YesNo getShareFlag() {
		return shareFlag;
	}

	public void setShareFlag(YesNo shareFlag) {
		this.shareFlag = shareFlag;
	}

	public YesNo getIsNewFlow() {
		return isNewFlow;
	}

	public void setIsNewFlow(YesNo isNewFlow) {
		this.isNewFlow = isNewFlow;
	}

	public FlowSort getFlowSort() {
		return flowSort;
	}

	public void setFlowSort(FlowSort flowSort) {
		this.flowSort = flowSort;
	}

	public WfBusinessType getOtherFlowType() {
		return otherFlowType;
	}

	public void setOtherFlowType(WfBusinessType otherFlowType) {
		this.otherFlowType = otherFlowType;
	}
}
