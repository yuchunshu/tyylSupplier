/**
 *
 */
package cn.com.chaochuang.workflow.event.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.dozer.Mapping;

import cn.com.chaochuang.workflow.reference.WfDealType;
import cn.com.chaochuang.workflow.reference.WfFlowDir;
import cn.com.chaochuang.workflow.reference.WfInstStatus;
import cn.com.chaochuang.workflow.reference.WfReadFlag;

/**
 * @author hzr 2017-5-20
 *
 */
public class TaskShowBean {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    private String       id;

    @Mapping("flowInst.title")
    private String       title;

    @Mapping("flowInst.entityId")
    private String       entityId;

    @Mapping("flowInst.sncode")
    private String       sncode;

    @Mapping("flowInst.businessType")
    private String  	 businessType;

    @Mapping("flowInst.urgencyLevel")
    private String       urgencyLevel;

    @Mapping("flowInst.expiryDate")
    private Date         expiryDate;
    private String       expiryDateShow;
    
    @Mapping("flowInst.startTime")
    private Date         startTime;
    private String       startTimeShow;

    /** 流实例ID */
    private String       flowInstId;

    /** 父环节实例ID */
    private String       parentNodeInstId;

    /** 呈送人：上一步办理人 */
    @Mapping("preDealer.userName")
    private String       preDealerName;
    
    private Long 		 deptId;
    /** 办理部门*/
    @Mapping("preDealer.department.deptName")
    private String 		 deptName;

    /** 当前环节 */
    private String       curNodeId;

    /** 下一环节 */
    private String       nextNodeId;

    /** 处理类型 */
    private WfDealType   dealType;

    /** 办理候选人 （多个用逗号分开） */
    private String       candidates;

    /** 办理人 */
    private Long         dealerId;

    /** 办理部门 */
    private Long         dealDeptId;

    /** 送达时间 */
    private Date         arrivalTime;
    private String       arrivalTimeShow;

    /** 流程方向 */
    private WfFlowDir    flowDir;

    /** 实例状态 */
    private WfInstStatus instStatus;

    /** 已读标识 */
    private WfReadFlag   readFlag;

    /** 在办环节 */
    @Mapping("curNode.nodeName")
    private String       nodeName;
    
    /** 来文单位*/
    private String 		sendUnit;
    
	public String getArrivalTimeShow() {
		return arrivalTimeShow;
	}

	public void setArrivalTimeShow(String arrivalTimeShow) {
		this.arrivalTimeShow = arrivalTimeShow;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFlowInstId() {
		return flowInstId;
	}

	public void setFlowInstId(String flowInstId) {
		this.flowInstId = flowInstId;
	}

	public String getParentNodeInstId() {
		return parentNodeInstId;
	}

	public void setParentNodeInstId(String parentNodeInstId) {
		this.parentNodeInstId = parentNodeInstId;
	}

	public String getSncode() {
		return sncode;
	}

	public void setSncode(String sncode) {
		this.sncode = sncode;
	}

	public String getPreDealerName() {
		return preDealerName;
	}

	public void setPreDealerName(String preDealerName) {
		this.preDealerName = preDealerName;
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
		if (this.arrivalTime != null) {
			this.arrivalTimeShow = this.sdf.format(arrivalTime);
		}
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
		if (this.expiryDate != null) {
			this.expiryDateShow = this.sdf2.format(expiryDate);
		}
	}

	public String getExpiryDateShow() {
		if (this.expiryDate != null) {
			this.expiryDateShow = this.sdf2.format(expiryDate);
		}
		return expiryDateShow;
	}

	public void setExpiryDateShow(String expiryDateShow) {
		this.expiryDateShow = expiryDateShow;
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


	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
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

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStartTimeShow() {
		if (this.startTime != null) {
			this.startTimeShow = this.sdf.format(startTime);
		}
		return startTimeShow;
	}

	public void setStartTimeShow(String startTimeShow) {
		this.startTimeShow = startTimeShow;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getSendUnit() {
		return sendUnit;
	}

	public void setSendUnit(String sendUnit) {
		this.sendUnit = sendUnit;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
}
