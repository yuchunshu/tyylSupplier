package cn.com.chaochuang.workflow.inst.domain;

import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.NotGenerateIdEntity;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelType;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelTypeConverter;
import cn.com.chaochuang.workflow.reference.WfReadFlag;
import cn.com.chaochuang.workflow.reference.WfReadFlagConverter;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfBusinessTypeConverter;
import cn.com.chaochuang.workflow.reference.WfDealType;
import cn.com.chaochuang.workflow.reference.WfDealTypeConverter;
import cn.com.chaochuang.workflow.reference.WfFlowDir;
import cn.com.chaochuang.workflow.reference.WfFlowDirConverter;
import cn.com.chaochuang.workflow.reference.WfInstStatus;
import cn.com.chaochuang.workflow.reference.WfInstStatusConverter;

@Entity
@Table(name = "v_node_inst")
public class VNodeInst extends NotGenerateIdEntity<Long> {

    /**  */
    private static final long serialVersionUID = 4207474603475119873L;

    private String            entityId;
    private String            flowInstId;
    private Date              arrivalTime;
    private Long              preDealerId;
    @ManyToOne
    @JoinColumn(name = "preDealerId", insertable = false, updatable = false)
    private SysUser           preDealer;
    private String            curNodeId;
    private String            curNodeName;
    @Convert(converter = WfInstStatusConverter.class)
    private WfInstStatus      nodeInstStatus;
    private Long              dealerId;
    private String            dealerName;
    @Convert(converter = WfDealTypeConverter.class)
    private WfDealType        dealType;
    @Convert(converter = WfInstStatusConverter.class)
    private WfInstStatus      instStatus;
    private Long              deptId;
    @Convert(converter = WfFlowDirConverter.class)
    private WfFlowDir         flowDir;
    @Convert(converter = WfReadFlagConverter.class)
    private WfReadFlag        readFlag;
    private Date              endTime;
    private String            title;
    @Convert(converter = WfBusinessTypeConverter.class)
    private WfBusinessType       fileType;
    private Date              expiryDate;
    private Long              creatorId;
    @Convert(converter = UrgencyLevelTypeConverter.class)
    private UrgencyLevelType  urgencyLevel;
    private Date              createDate;


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

    public String getCurNodeId() {
        return curNodeId;
    }

    public void setCurNodeId(String curNodeId) {
        this.curNodeId = curNodeId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCurNodeName() {
        return curNodeName;
    }

    public void setCurNodeName(String curNodeName) {
        this.curNodeName = curNodeName;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public SysUser getPreDealer() {
        return preDealer;
    }

    public void setPreDealer(SysUser preDealer) {
        this.preDealer = preDealer;
    }

}
