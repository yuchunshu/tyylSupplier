/*
 * FileName:    DocFileUpdate.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年4月3日 (LLM) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

/**
 * @author huangwq
 *
 */
public class DocFileUpdate1 {
    /** 待办事宜编号 */
    private Long    fordoId;
    /** 远程系统待办明细编号 */
    private Long    rmPendingItemId;
    /** 原实例编号 */
    private Long    rmInstanceId;
    /** 流程编号 */
    private String  flowId;
    /** 公文编号 */
    private Long    docId;
    /** 当前实例环节编号 */
    private Long    curInstnoId;
    /** 下一环节编号 */
    private Integer nextNodeId;
    /** 下一环节办理人 */
    private String  receiveMans;
    /** 原意见id */
    private Long    opinionId;
    /** 审批意见 */
    private String  opinions;
    /** oa意见id */
    private Long    rmNodeOpinionsId;
    /** 原系统数据类型表编号 */
    private Long    rmDataitemId;
    /** 环节标识（就是itemId） */
    private String  nodeFlag;
    /** 发送人id */
    private Long    senderId;
    /** 操纵标识 */
    private String  oprationType;
    /** 移动端新建的节点InstnoId */
    // private String newFlowNodeInfoId;
    /** 提交类型 send：呈送；finish：办结；back：退回 */
    private String  submitAction;

    /**
     * @return the flowId
     */
    public String getFlowId() {
        return flowId;
    }

    /**
     * @param flowId
     *            the flowId to set
     */
    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    /**
     * @return the fordoId
     */
    public Long getFordoId() {
        return fordoId;
    }

    /**
     * @param fordoId
     *            the fordoId to set
     */
    public void setFordoId(Long fordoId) {
        this.fordoId = fordoId;
    }

    /**
     * @return the docId
     */
    public Long getDocId() {
        return docId;
    }

    /**
     * @param docId
     *            the docId to set
     */
    public void setDocId(Long docId) {
        this.docId = docId;
    }

    /**
     * @return the curInstnoId
     */
    public Long getCurInstnoId() {
        return curInstnoId;
    }

    /**
     * @param curInstnoId
     *            the curInstnoId to set
     */
    public void setCurInstnoId(Long curInstnoId) {
        this.curInstnoId = curInstnoId;
    }

    /**
     * @return the nextNodeId
     */
    public Integer getNextNodeId() {
        return nextNodeId;
    }

    /**
     * @param nextNodeId
     *            the nextNodeId to set
     */
    public void setNextNodeId(Integer nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    /**
     * @return the opinions
     */
    public String getOpinions() {
        return opinions;
    }

    /**
     * @param opinions
     *            the opinions to set
     */
    public void setOpinions(String opinions) {
        this.opinions = opinions;
    }

    /**
     * @return the nodeFlag
     */
    public String getNodeFlag() {
        return nodeFlag;
    }

    /**
     * @param nodeFlag
     *            the nodeFlag to set
     */
    public void setNodeFlag(String nodeFlag) {
        this.nodeFlag = nodeFlag;
    }

    /**
     * @return the oprationType
     */
    public String getOprationType() {
        return oprationType;
    }

    /**
     * @param oprationType
     *            the oprationType to set
     */
    public void setOprationType(String oprationType) {
        this.oprationType = oprationType;
    }

    /**
     * @return the rmPendingItemId
     */
    public Long getRmPendingItemId() {
        return rmPendingItemId;
    }

    /**
     * @param rmPendingItemId
     *            the rmPendingItemId to set
     */
    public void setRmPendingItemId(Long rmPendingItemId) {
        this.rmPendingItemId = rmPendingItemId;
    }

    /**
     * @return the rmInstanceId
     */
    public Long getRmInstanceId() {
        return rmInstanceId;
    }

    /**
     * @param rmInstanceId
     *            the rmInstanceId to set
     */
    public void setRmInstanceId(Long rmInstanceId) {
        this.rmInstanceId = rmInstanceId;
    }

    /**
     * @return the submitAction
     */
    public String getSubmitAction() {
        return submitAction;
    }

    /**
     * @param submitAction
     *            the submitAction to set
     */
    public void setSubmitAction(String submitAction) {
        this.submitAction = submitAction;
    }

    /**
     * @return the receiveMans
     */
    public String getReceiveMans() {
        return receiveMans;
    }

    /**
     * @param receiveMans
     *            the receiveMans to set
     */
    public void setReceiveMans(String receiveMans) {
        this.receiveMans = receiveMans;
    }

    /**
     * @return the rmNodeOpinionsId
     */
    public Long getRmNodeOpinionsId() {
        return rmNodeOpinionsId;
    }

    /**
     * @param rmNodeOpinionsId
     *            the rmNodeOpinionsId to set
     */
    public void setRmNodeOpinionsId(Long rmNodeOpinionsId) {
        this.rmNodeOpinionsId = rmNodeOpinionsId;
    }

    /**
     * @return the senderId
     */
    public Long getSenderId() {
        return senderId;
    }

    /**
     * @param senderId
     *            the senderId to set
     */
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    /**
     * @return the rmDataitemId
     */
    public Long getRmDataitemId() {
        return rmDataitemId;
    }

    /**
     * @param rmDataitemId
     *            the rmDataitemId to set
     */
    public void setRmDataitemId(Long rmDataitemId) {
        this.rmDataitemId = rmDataitemId;
    }

    /**
     * @return the opinionId
     */
    public Long getOpinionId() {
        return opinionId;
    }

    /**
     * @param opinionId
     *            the opinionId to set
     */
    public void setOpinionId(Long opinionId) {
        this.opinionId = opinionId;
    }
}
