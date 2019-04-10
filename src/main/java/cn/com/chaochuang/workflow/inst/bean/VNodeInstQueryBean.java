package cn.com.chaochuang.workflow.inst.bean;

import java.util.Date;

import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelType;
import cn.com.chaochuang.workflow.reference.WfReadFlag;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfDealType;
import cn.com.chaochuang.workflow.reference.WfFlowDir;
import cn.com.chaochuang.workflow.reference.WfInstStatus;

public class VNodeInstQueryBean {
    private String           entityId;
    private String           flowInstId;
    private Date             arrivalTime;
    private Long             preDealerId;
    private SysUser          preDealer;
    private String           curNodeId;
    private String           curNodeName;
    private WfInstStatus     nodeInstStatus;
    private Long             dealerId;
    private String           dealerName;
    private WfDealType       dealType;
    private WfInstStatus     instStatus;
    private Long             deptId;
    private WfFlowDir        flowDir;
    private WfReadFlag       readFlag;
    private Date             endTime;
    private String           title;
    private WfBusinessType      fileType;
    private Date             expiryDate;
    private Long             creatorId;
    private UrgencyLevelType urgencyLevel;
    private Date             createDateBegin;
    private Date             createDateEnd;



    public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getFlowInstId() {
		return flowInstId;
	}

	public void setFlowInstId(String flowInstId) {
		this.flowInstId = flowInstId;
	}

	public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
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

    public String getCurNodeName() {
        return curNodeName;
    }

    public void setCurNodeName(String curNodeName) {
        this.curNodeName = curNodeName;
    }

    public WfInstStatus getNodeInstStatus() {
        return nodeInstStatus;
    }

    public void setNodeInstStatus(WfInstStatus nodeInstStatus) {
        this.nodeInstStatus = nodeInstStatus;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public WfDealType getDealType() {
        return dealType;
    }

    public void setDealType(WfDealType dealType) {
        this.dealType = dealType;
    }

    public WfInstStatus getInstStatus() {
        return instStatus;
    }

    public void setInstStatus(WfInstStatus instStatus) {
        this.instStatus = instStatus;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public WfFlowDir getFlowDir() {
        return flowDir;
    }

    public void setFlowDir(WfFlowDir flowDir) {
        this.flowDir = flowDir;
    }

    public WfReadFlag getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(WfReadFlag readFlag) {
        this.readFlag = readFlag;
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

    public WfBusinessType getFileType() {
        return fileType;
    }

    public void setFileType(WfBusinessType fileType) {
        this.fileType = fileType;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public UrgencyLevelType getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(UrgencyLevelType urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public Date getCreateDateBegin() {
        return createDateBegin;
    }

    public void setCreateDateBegin(Date createDateBegin) {
        this.createDateBegin = createDateBegin;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

}
