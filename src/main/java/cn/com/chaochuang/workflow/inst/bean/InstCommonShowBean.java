package cn.com.chaochuang.workflow.inst.bean;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfInstStatus;

public class InstCommonShowBean {
	
	private String 		 flowInstId;

    /** 业务实体ID（比如公文id） */
    private String       entityId;

    private String       title;

    private String       sendUnit;

    private String       docCode;

    private String       businessType;

    /** 紧急程度*/
    private String       urgencyLevel;

    private String       creatorName;

    private Date         createDate;
    private String       createDateShow;

    private Date         expiryDate;
    private String       expiryDateShow;

    private String       curNodeId;

    private String       nodeName;
    
    private String       dealerName;

    /** 实例状态 */
    private WfInstStatus instStatus;
    
    private String       deptName;
    
    private String       restTimeShow;
    
    private String       sncode;
    
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSendUnit() {
		return sendUnit;
	}

	public void setSendUnit(String sendUnit) {
		this.sendUnit = sendUnit;
	}

	public String getCurNodeId() {
		return curNodeId;
	}

	public void setCurNodeId(String curNodeId) {
		this.curNodeId = curNodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public WfInstStatus getInstStatus() {
		return instStatus;
	}

	public void setInstStatus(WfInstStatus instStatus) {
		this.instStatus = instStatus;
	}

	public String getCreateDateShow() {
        if (StringUtils.isBlank(this.createDateShow)) {
            if (this.createDate != null) {
                this.createDateShow = Tools.DATE_MINUTE_FORMAT.format(this.createDate);
            }
        }
		return createDateShow;
	}

	public void setCreateDateShow(String createDateShow) {
		this.createDateShow = createDateShow;
	}


	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getExpiryDateShow() {
		if (this.expiryDate != null) {
			this.expiryDateShow = Tools.DATE_MINUTE_FORMAT.format(this.expiryDate);
		}
		return expiryDateShow;
	}

	public void setExpiryDateShow(String expiryDateShow) {
		this.expiryDateShow = expiryDateShow;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getUrgencyLevel() {
		return urgencyLevel;
	}

	public void setUrgencyLevel(String urgencyLevel) {
		this.urgencyLevel = urgencyLevel;
	}

	/** 计算剩余时间*/
    public Long getRestTime() {
        if (this.expiryDate != null) {
            Date now = new Date();
            return ((this.expiryDate.getTime() - now.getTime()) / 1000);
        }
        return null;
    }

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getRestTimeShow() {
		return restTimeShow;
	}

	public void setRestTimeShow(String restTimeShow) {
		this.restTimeShow = restTimeShow;
	}

	public String getSncode() {
		return sncode;
	}

	public void setSncode(String sncode) {
		this.sncode = sncode;
	}

	public String getFlowInstId() {
		return flowInstId;
	}

	public void setFlowInstId(String flowInstId) {
		this.flowInstId = flowInstId;
	}
}
