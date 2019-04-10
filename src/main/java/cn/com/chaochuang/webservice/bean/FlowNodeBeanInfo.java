package cn.com.chaochuang.webservice.bean;

import java.util.Date;

public class FlowNodeBeanInfo {
    /** 公文id */
    private Long   docId;
    /** 原实例编号 */
    private String rmInstanceId;
    /** 原系统流程环节实例编号 */
    private Long   rmInstnoId;
    /** 原系统前驱流程环节实例编号 */
    private Long   rmLastInstnoId;
    /** 前驱环节编号 */
    private String preNodeId;
    /** 环节编号 node_id */
    private String nodeId;
    /** 环节名称 node_name */
    private String nodeName;
    /** 当前办理人姓名 */
    private String transactName;
    /** 当前办理人id */
    private Long   transactId;
    /** 到达时间 */
    private Date   arriveTime;
    /** 完成时间 */
    private Date   finishTime;
    /** 是否提交数据 */
    // private SubmitAction submitData;
    /** 办理部门id */
    private Long   transactDeptId;

    /**
     *
     */
    public FlowNodeBeanInfo() {
        super();
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
     * @return the rmInstanceId
     */
    public String getRmInstanceId() {
        return rmInstanceId;
    }

    /**
     * @param rmInstanceId
     *            the rmInstanceId to set
     */
    public void setRmInstanceId(String rmInstanceId) {
        this.rmInstanceId = rmInstanceId;
    }

    /**
     * @return the rmInstnoId
     */
    public Long getRmInstnoId() {
        return rmInstnoId;
    }

    /**
     * @param rmInstnoId
     *            the rmInstnoId to set
     */
    public void setRmInstnoId(Long rmInstnoId) {
        this.rmInstnoId = rmInstnoId;
    }

    /**
     * @return the preNodeId
     */
    public String getPreNodeId() {
        return preNodeId;
    }

    /**
     * @param preNodeId
     *            the preNodeId to set
     */
    public void setPreNodeId(String preNodeId) {
        this.preNodeId = preNodeId;
    }

    /**
     * @return the nodeId
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * @param nodeId
     *            the nodeId to set
     */
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * @return the nodeName
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * @param nodeName
     *            the nodeName to set
     */
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * @return the transactName
     */
    public String getTransactName() {
        return transactName;
    }

    /**
     * @param transactName
     *            the transactName to set
     */
    public void setTransactName(String transactName) {
        this.transactName = transactName;
    }

    /**
     * @return the transactId
     */
    public Long getTransactId() {
        return transactId;
    }

    /**
     * @param transactId
     *            the transactId to set
     */
    public void setTransactId(Long transactId) {
        this.transactId = transactId;
    }

    /**
     * @return the arriveTime
     */
    public Date getArriveTime() {
        return arriveTime;
    }

    /**
     * @param arriveTime
     *            the arriveTime to set
     */
    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    /**
     * @return the finishTime
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * @param finishTime
     *            the finishTime to set
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * @return the submitData
     */
    // public SubmitAction getSubmitData() {
    // return submitData;
    // }

    /**
     * @param submitData
     *            the submitData to set
     */
    // public void setSubmitData(SubmitAction submitData) {
    // this.submitData = submitData;
    // }

    /**
     * @return the transactDeptId
     */
    public Long getTransactDeptId() {
        return transactDeptId;
    }

    /**
     * @param transactDeptId
     *            the transactDeptId to set
     */
    public void setTransactDeptId(Long transactDeptId) {
        this.transactDeptId = transactDeptId;
    }

    /**
     * @return the rmLastInstnoId
     */
    public Long getRmLastInstnoId() {
        return rmLastInstnoId;
    }

    /**
     * @param rmLastInstnoId
     *            the rmLastInstnoId to set
     */
    public void setRmLastInstnoId(Long rmLastInstnoId) {
        this.rmLastInstnoId = rmLastInstnoId;
    }

}
