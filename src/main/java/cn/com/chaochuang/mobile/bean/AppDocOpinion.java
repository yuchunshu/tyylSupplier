/*
 * FileName:    AppDocOpinion.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2018
 * History:     2018年06月29日 (shicx) 1.0 Create
 */

package cn.com.chaochuang.mobile.bean;

import org.dozer.Mapping;

import java.util.Date;

/**
 * @author shicx
 */
public class AppDocOpinion {

    private String id;
    private String            flowInstId;
    /** 环节实例ID */
    private String            nodeInstId;
    /** 所属环节 */
    private String            nodeId;
    /** 审批人 */
    private Long              approverId;
    /**
     * 环节名称
     */
    @Mapping("curNode.nodeName")
    private String nodeName;
    /**
     * 环节KEY
     */
    @Mapping("curNode.nodeKey")
    private String nodeKey;
    /**
     * 审批意见
     */
    private String apprOpinion;
    /**
     * 审批人
     */
    @Mapping("approver.userName")
    private String approverName;
    /**
     * 审批时间
     */
    private Date apprTime;
    /**
     * 文笺意见栏定位
     */
    private String opinionFix;
    /**
     * 签批意见
     */
    //@Mapping("signOpinion.signContent")
    private String signContent;
    /**
     * 排序号
     */
    private Long sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getApprOpinion() {
        return apprOpinion;
    }

    public void setApprOpinion(String apprOpinion) {
        this.apprOpinion = apprOpinion;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Date getApprTime() {
        return apprTime;
    }

    public void setApprTime(Date apprTime) {
        this.apprTime = apprTime;
    }

    public String getOpinionFix() {
        return opinionFix;
    }

    public void setOpinionFix(String opinionFix) {
        this.opinionFix = opinionFix;
    }

    public String getSignContent() {
        return signContent;
    }

    public void setSignContent(String signContent) {
        this.signContent = signContent;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getFlowInstId() {
        return flowInstId;
    }

    public void setFlowInstId(String flowInstId) {
        this.flowInstId = flowInstId;
    }

    public String getNodeInstId() {
        return nodeInstId;
    }

    public void setNodeInstId(String nodeInstId) {
        this.nodeInstId = nodeInstId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }
}

