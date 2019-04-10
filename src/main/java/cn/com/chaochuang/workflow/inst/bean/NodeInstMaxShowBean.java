package cn.com.chaochuang.workflow.inst.bean;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.dozer.Mapping;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.event.reference.UrgencyLevelType;
import cn.com.chaochuang.workflow.reference.WfReadFlag;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfDealType;
import cn.com.chaochuang.workflow.reference.WfFlowDir;
import cn.com.chaochuang.workflow.reference.WfInstStatus;

public class NodeInstMaxShowBean {
    @Mapping("inst.entityId")
    private String           entityId;
    @Mapping("inst.instId")
    private String           instId;
    @Mapping("inst.arrivalTime")
    private Date             arrivalTime;
    private String           arrivalTimeShow;
    @Mapping("inst.preDealerId")
    private Long             preDealerId;
    @Mapping("inst.preDealer.userName")
    private String           preDealerName;
    @Mapping("inst.curNodeId")
    private String           curNodeId;
    @Mapping("inst.curNodeName")
    private String           curNodeName;
    @Mapping("inst.nodeInstStatus")
    private WfInstStatus     nodeInstStatus;
    @Mapping("inst.dealerId")
    private Long             dealerId;
    @Mapping("inst.dealerName")
    private String           dealerName;
    @Mapping("inst.dealType")
    private WfDealType       dealType;
    @Mapping("inst.instStatus")
    private WfInstStatus     instStatus;
    @Mapping("inst.deptId")
    private Long             deptId;
    @Mapping("inst.flowDir")
    private WfFlowDir        flowDir;
    @Mapping("inst.readFlag")
    private WfReadFlag         readFlag;
    @Mapping("inst.endTime")
    private Date             endTime;
    private String           endTimeShow;
    @Mapping("inst.title")
    private String           title;
    @Mapping("inst.fileType")
    private WfBusinessType      fileType;
    @Mapping("inst.fileType.key")
    private String           fileTypeKey;
    @Mapping("inst.expiryDate")
    private Date             expiryDate;
    private String           expiryDateShow;
    @Mapping("inst.creatorId")
    private Long             creatorId;
    @Mapping("inst.urgencyLevel")
    private UrgencyLevelType urgencyLevel;
    @Mapping("inst.createDate")
    private Date             createDate;
    private String           createDateShow;


    public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
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

    public String getFileTypeKey() {
        return fileTypeKey;
    }

    public void setFileTypeKey(String fileTypeKey) {
        this.fileTypeKey = fileTypeKey;
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

    public String getArrivalTimeShow() {
        if (StringUtils.isBlank(this.arrivalTimeShow)) {
            if (this.arrivalTime != null) {
                this.arrivalTimeShow = Tools.DATE_MINUTE_FORMAT.format(this.arrivalTime);
            }
        }
        return arrivalTimeShow;
    }

    public void setArrivalTimeShow(String arrivalTimeShow) {
        this.arrivalTimeShow = arrivalTimeShow;
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

    public String getEndTimeShow() {
        if (StringUtils.isBlank(this.endTimeShow)) {
            if (this.endTime != null) {
                this.endTimeShow = Tools.DATE_FORMAT.format(this.endTime);
            }
        }
        return endTimeShow;
    }

    public void setEndTimeShow(String endTimeShow) {
        this.endTimeShow = endTimeShow;
    }

    public String getExpiryDateShow() {
        if (StringUtils.isBlank(this.expiryDateShow)) {
            if (this.expiryDate != null) {
                this.expiryDateShow = Tools.DATE_FORMAT.format(this.expiryDate);
            }
        }
        return expiryDateShow;
    }

    public void setExpiryDateShow(String expiryDateShow) {
        this.expiryDateShow = expiryDateShow;
    }

    public String getCreateDateShow() {
        if (StringUtils.isBlank(this.createDateShow)) {
            if (this.createDate != null) {
                this.createDateShow = Tools.DATE_FORMAT.format(this.createDate);
            }
        }
        return createDateShow;
    }

    public void setCreateDateShow(String createDateShow) {
        this.createDateShow = createDateShow;
    }

    public String getPreDealerName() {
        return preDealerName;
    }

    public void setPreDealerName(String preDealerName) {
        this.preDealerName = preDealerName;
    }

}
