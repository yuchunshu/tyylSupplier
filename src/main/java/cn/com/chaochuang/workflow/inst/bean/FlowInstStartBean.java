package cn.com.chaochuang.workflow.inst.bean;

import java.util.Date;

import cn.com.chaochuang.common.user.domain.SysUser;

public class FlowInstStartBean {

    /** 流程定义 */
    private String  flowId;

    /** 业务实体ID（比如公文id） */
    private String  entityId;

    /** 标题 */
    private String  title;

    /** 自然编号  */
    private String  sncode;

    private String  businessType;

    /** 紧急程度*/
    private String  urgencyLevel;

    /** 办理人 */
    private SysUser dealer;

    /** 结束时间 */
    private Date    endTime;

    /** 办结时限*/
    private Date    expiryDate;

    /** 限时办结-发送时间 */
    private Date    sendTime;
    
    /** 转办公文id */
    private String  parentEntityId;
    
    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }


    public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getSncode() {
		return sncode;
	}

	public void setSncode(String sncode) {
		this.sncode = sncode;
	}

	public SysUser getDealer() {
        return dealer;
    }

    public void setDealer(SysUser dealer) {
        this.dealer = dealer;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getUrgencyLevel() {
		return urgencyLevel;
	}

	public void setUrgencyLevel(String urgencyLevel) {
		this.urgencyLevel = urgencyLevel;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getParentEntityId() {
		return parentEntityId;
	}

	public void setParentEntityId(String parentEntityId) {
		this.parentEntityId = parentEntityId;
	}

}
