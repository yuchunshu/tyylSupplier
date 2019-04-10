package cn.com.chaochuang.workflow.inst.bean;

import java.util.Date;

import cn.com.chaochuang.workflow.reference.WfInstStatus;

public class FlowInstEditBean {

    private String id;

    /** 父流程实例ID，用于子流程*/
    private String parentFlowInstId;

    /** 流定义 */
    private String flowId;

    /** 业务实体ID（比如公文id） */
    private String entityId;

    /** 自然编号  */
    private String sncode;

    private String businessType;

    /** 标题 */
    private String title;

    /** 当前节点 */
    private String curNodeId;

    /** 开始时间 */
    private Date   startTime;

    /** 结束时间 */
    private Date   endTime;

    /** 创建人*/
    private Long   creatorId;

    /** 办结人*/
    private Long   enderId;

    private WfInstStatus instStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


	public String getParentFlowInstId() {
		return parentFlowInstId;
	}

	public void setParentFlowInstId(String parentFlowInstId) {
		this.parentFlowInstId = parentFlowInstId;
	}

	public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getSncode() {
		return sncode;
	}

	public void setSncode(String sncode) {
		this.sncode = sncode;
	}

    public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getCurNodeId() {
        return curNodeId;
    }

    public void setCurNodeId(String curNodeId) {
        this.curNodeId = curNodeId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public WfInstStatus getInstStatus() {
        return instStatus;
    }

    public void setInstStatus(WfInstStatus instStatus) {
        this.instStatus = instStatus;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getEnderId() {
		return enderId;
	}

	public void setEnderId(Long enderId) {
		this.enderId = enderId;
	}


}
