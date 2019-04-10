package cn.com.chaochuang.workflow.his.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.reference.WfReadFlag;
import cn.com.chaochuang.workflow.reference.WfReadFlagConverter;
import cn.com.chaochuang.workflow.reference.WfDealType;
import cn.com.chaochuang.workflow.reference.WfDealTypeConverter;
import cn.com.chaochuang.workflow.reference.WfEndCond;
import cn.com.chaochuang.workflow.reference.WfEndCondConverter;
import cn.com.chaochuang.workflow.reference.WfFlowDir;
import cn.com.chaochuang.workflow.reference.WfFlowDirConverter;
import cn.com.chaochuang.workflow.reference.WfInstStatus;
import cn.com.chaochuang.workflow.reference.WfInstStatusConverter;

/**
 * 环节实例
 *
 * @author hzr 2016-12-24
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "node_inst_id")) })
@Table(name = "wf_his_nodeinst")
public class WfHisNodeinst extends StringUuidEntity {

    /**  */
    private static final long serialVersionUID = 8764299177506566094L;

    /** 流实例ID */
    private String            flowInstId;
    @ManyToOne
    @JoinColumn(name = "flowInstId", insertable = false, updatable = false)
    private WfFlowInst        flowInst;

    /** 父环节实例ID */
    private String            parentNodeInstId;

    /** 呈送人：上一步办理人 */
    private Long              preDealerId;
    @ManyToOne
    @JoinColumn(name = "preDealerId", insertable = false, updatable = false)
    private SysUser           preDealer;

    /** 当前环节 */
    private String            curNodeId;
    @ManyToOne
    @JoinColumn(name = "curNodeId", insertable = false, updatable = false)
    private WfNode            curNode;

    /** 下一环节 */
    private String            nextNodeId;

    /** 办结条件 */
    @Convert(converter = WfEndCondConverter.class)
    private WfEndCond         endCond;

    /** （用于抢占处理事项，一个人办完 ，其它人失效） */
    private String            exclusiveKey;

    /** 处理类型 */
    @Convert(converter = WfDealTypeConverter.class)
    private WfDealType        dealType;

    /** 办理候选人 （多个用逗号分开） */
    private String            candidates;

    /** 办理人 */
    private Long              dealerId;
    @ManyToOne
    @JoinColumn(name = "dealerId", insertable = false, updatable = false)
    private SysUser           dealer;

    /** 办理部门 */
    private Long              dealDeptId;
    @ManyToOne
    @JoinColumn(name = "dealDeptId", insertable = false, updatable = false)
    private SysDepartment     dealDept;

    /** 送达时间 */
    private Date              arrivalTime;

    /** 处理时间 */
    private Date              dealTime;

    /** 流程方向 */
    @Convert(converter = WfFlowDirConverter.class)
    private WfFlowDir         flowDir;

    /** 实例状态 */
    @Convert(converter = WfInstStatusConverter.class)
    private WfInstStatus      instStatus;

    /** 已读标识 */
    @Convert(converter = WfReadFlagConverter.class)
    private WfReadFlag          readFlag;


    public String getFlowInstId() {
		return flowInstId;
	}

	public void setFlowInstId(String flowInstId) {
		this.flowInstId = flowInstId;
	}

	public WfFlowInst getFlowInst() {
        return flowInst;
    }

    public void setFlowInst(WfFlowInst flowInst) {
        this.flowInst = flowInst;
    }

    public Long getPreDealerId() {
        return preDealerId;
    }

    public void setPreDealerId(Long preDealerId) {
        this.preDealerId = preDealerId;
    }

    public SysUser getPreDealer() {
        return preDealer;
    }

    public void setPreDealer(SysUser preDealer) {
        this.preDealer = preDealer;
    }

    public String getCurNodeId() {
        return curNodeId;
    }

    public void setCurNodeId(String curNodeId) {
        this.curNodeId = curNodeId;
    }

    public String getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(String nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    public WfEndCond getEndCond() {
        return endCond;
    }

    public void setEndCond(WfEndCond endCond) {
        this.endCond = endCond;
    }

    public WfDealType getDealType() {
        return dealType;
    }

    public void setDealType(WfDealType dealType) {
        this.dealType = dealType;
    }

    public String getCandidates() {
		return candidates;
	}

	public void setCandidates(String candidates) {
		this.candidates = candidates;
	}

	public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public Long getDealDeptId() {
        return dealDeptId;
    }

    public void setDealDeptId(Long dealDeptId) {
        this.dealDeptId = dealDeptId;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public WfFlowDir getFlowDir() {
        return flowDir;
    }

    public void setFlowDir(WfFlowDir flowDir) {
        this.flowDir = flowDir;
    }

    public WfInstStatus getInstStatus() {
        return instStatus;
    }

    public void setInstStatus(WfInstStatus instStatus) {
        this.instStatus = instStatus;
    }

    public WfReadFlag getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(WfReadFlag readFlag) {
        this.readFlag = readFlag;
    }

    public SysUser getDealer() {
        return dealer;
    }

    public void setDealer(SysUser dealer) {
        this.dealer = dealer;
    }

    public SysDepartment getDealDept() {
        return dealDept;
    }

    public void setDealDept(SysDepartment dealDept) {
        this.dealDept = dealDept;
    }

    public WfNode getCurNode() {
        return curNode;
    }

    public void setCurNode(WfNode curNode) {
        this.curNode = curNode;
    }


	public String getParentNodeInstId() {
		return parentNodeInstId;
	}

	public void setParentNodeInstId(String parentNodeInstId) {
		this.parentNodeInstId = parentNodeInstId;
	}

	public String getExclusiveKey() {
		return exclusiveKey;
	}

	public void setExclusiveKey(String exclusiveKey) {
		this.exclusiveKey = exclusiveKey;
	}

	public Long getDuration() {
        if (this.arrivalTime != null && this.dealTime != null) {
            return (this.dealTime.getTime() - this.arrivalTime.getTime()) / (1000);
        } else if (this.arrivalTime != null) {
            return (new Date().getTime() - this.arrivalTime.getTime()) / (1000);
        }
        return null;
    }
}
