package cn.com.chaochuang.mobile.bean;

import java.util.Date;
import org.dozer.Mapping;


/**
 * @author hzr 2017.8.13
 *
 */
public class AppDocNodeInfo {

	/** 环节*/
	@Mapping("curNode.id")
	private String            nodeId;
	@Mapping("curNode.nodeName")
	private String            nodeName;

    /** 办理候选人 （多个用逗号分开） */
    private String            candidates;

    /** 办理人 */
    private Long              dealerId;
    @Mapping("dealer.userName")
    private String            dealerName;

    /** 办理部门 */
    private Long              dealDeptId;
    @Mapping("dealDept.deptName")
    private String            dealDeptName;

    /** 送达时间 */
    private Date              arrivalTime;

    /** 处理时间 */
    private Date              dealTime;

    /** 办理意见*/
    private String            opinion;

	/** 签批意见*/
	private String            signContent;

    /** 实例状态 */
    private String            status;

    /** 已读标识 */
    private String            readFlag;

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

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public Long getDealDeptId() {
		return dealDeptId;
	}

	public void setDealDeptId(Long dealDeptId) {
		this.dealDeptId = dealDeptId;
	}

	public String getDealDeptName() {
		return dealDeptName;
	}

	public void setDealDeptName(String dealDeptName) {
		this.dealDeptName = dealDeptName;
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

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getSignContent() {
		return signContent;
	}

	public void setSignContent(String signContent) {
		this.signContent = signContent;
	}
}
