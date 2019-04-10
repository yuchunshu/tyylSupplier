package cn.com.chaochuang.webservice.bean;

import java.util.Date;

public class FlowNodeOpinionsInfo {
    /** 公文id */
    private Long   docId;
    /** 环节标识 ? */
    private String nodeFlag;
    /** 办理人姓名 */
    private String transactName;
    /** 办理人id */
    private Long   transactId;
    /** 办理部门 */
    private String transactDeptName;
    /** 办理部门id */
    private Long   transactDeptId;
    /** 办理意见 */
    private String opinions;
    /** 办理时间 */
    private Date   transactTime;
    /** 向OA获取数据时传递的时间 */
    private String getDataTime;
    /** 向OA获取数据时传递的时间 */
    private String getDataNoId;
    /** 原实例编号 */
    private String rmInstanceId;
    /** 原系统流程环节实例编号 */
    private Long   rmInstnoId;
    /** 远程意见id */
    private Long   rmNodeOpinionsId;
    /** 原系统数据类型表编号 */
    private Long   rmDataitemId;

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
     * @return the transactDeptName
     */
    public String getTransactDeptName() {
        return transactDeptName;
    }

    /**
     * @param transactDeptName
     *            the transactDeptName to set
     */
    public void setTransactDeptName(String transactDeptName) {
        this.transactDeptName = transactDeptName;
    }

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
     * @return the transactTime
     */
    public Date getTransactTime() {
        return transactTime;
    }

    /**
     * @param transactTime
     *            the transactTime to set
     */
    public void setTransactTime(Date transactTime) {
        this.transactTime = transactTime;
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
     * @return the getDataTime
     */
    public String getGetDataTime() {
        return getDataTime;
    }

    /**
     * @param getDataTime
     *            the getDataTime to set
     */
    public void setGetDataTime(String getDataTime) {
        this.getDataTime = getDataTime;
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
     * @return the getDataNoId
     */
    public String getGetDataNoId() {
        return getDataNoId;
    }

    /**
     * @param getDataNoId
     *            the getDataNoId to set
     */
    public void setGetDataNoId(String getDataNoId) {
        this.getDataNoId = getDataNoId;
    }

}
