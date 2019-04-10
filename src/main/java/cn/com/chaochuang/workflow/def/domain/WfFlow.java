package cn.com.chaochuang.workflow.def.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.NotGenerateIdEntity;
import cn.com.chaochuang.common.reference.AdministrativeLevel;
import cn.com.chaochuang.common.reference.AdministrativeLevelConverter;
import cn.com.chaochuang.common.reference.EnableStatus;
import cn.com.chaochuang.common.reference.EnableStatusConverter;
import cn.com.chaochuang.common.reference.FlowSort;
import cn.com.chaochuang.common.reference.FlowSortConverter;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.reference.YesNoConverter;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfBusinessTypeConverter;

/**
 * 流程定义
 *
 * @author yuandl 2016年11月24日
 *
 */
@Entity
@AttributeOverrides({@AttributeOverride(name="id",column=@Column(name="flow_id"))})
@Table(name = "wf_flow")
public class WfFlow extends NotGenerateIdEntity<String> {

    /**  */
    private static final long 	serialVersionUID = 4738271776727087573L;

    /** 流程名称 */
    private String            	flowName;

    /** 父流程ID */
    private String            	parentFlowId;

    /** 流程图jason */
    private String            	flowJason;

    /** 流程分类 （1收文 2发文 9其它）*/
    @Convert(converter = WfBusinessTypeConverter.class)
    private WfBusinessType    	flowType;

    /** 流程级别(1/2/3级，一般代表省/市/县) */
    @Convert(converter = AdministrativeLevelConverter.class)
    private AdministrativeLevel flowLevel;

    /** 所属单位或部门ID */
    private String              deptId;

    /** 是否同级共享 （=1为共享）*/
    @Convert(converter = YesNoConverter.class)
    private YesNo               shareFlag;
    
    /** 工作流状态 */
    @Convert(converter = EnableStatusConverter.class)
    private EnableStatus        flowStatus;
    
    /** 流程类别*/
    @Convert(converter =FlowSortConverter.class)
    private FlowSort			flowSort;

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getFlowJason() {
		return flowJason;
	}

	public void setFlowJason(String flowJason) {
		this.flowJason = flowJason;
	}

	public EnableStatus getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(EnableStatus flowStatus) {
        this.flowStatus = flowStatus;
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

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public YesNo getShareFlag() {
		return shareFlag;
	}

	public void setShareFlag(YesNo shareFlag) {
		this.shareFlag = shareFlag;
	}

	public FlowSort getFlowSort() {
		return flowSort;
	}

	public void setFlowSort(FlowSort flowSort) {
		this.flowSort = flowSort;
	}
}
