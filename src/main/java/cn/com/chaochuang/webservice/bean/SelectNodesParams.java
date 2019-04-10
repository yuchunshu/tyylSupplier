/*
 * FileName:    NextNodesInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年4月12日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

/**
 * @author huangwq
 *
 *         获取下一节点时需要的参数
 */
public class SelectNodesParams {

    /** 环节实例id */
    private String instId;
    /** 节点实例id */
    private Long   instNoId;
    /** 当前用户id */
    private Long   userId;
    /** 流程号 */
    private String flowId;
    /** 当前节点号 */
    private String curNo;
    /** 公文类型 */
    private String docType;

    /**
     *
     */
    public SelectNodesParams() {
        super();
    }

    /**
     * @param userId
     * @param flowId
     * @param curNo
     */
    public SelectNodesParams(Long userId, String flowId, String curNo, String instId) {
        super();
        this.userId = userId;
        this.flowId = flowId;
        this.curNo = curNo;
        this.instId = instId;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

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
     * @return the curNo
     */
    public String getCurNo() {
        return curNo;
    }

    /**
     * @param curNo
     *            the curNo to set
     */
    public void setCurNo(String curNo) {
        this.curNo = curNo;
    }

    /**
     * @return the instId
     */
    public String getInstId() {
        return instId;
    }

    /**
     * @param instId
     *            the instId to set
     */
    public void setInstId(String instId) {
        this.instId = instId;
    }

    /**
     * @return the docType
     */
    public String getDocType() {
        return docType;
    }

    /**
     * @param docType
     *            the docType to set
     */
    public void setDocType(String docType) {
        this.docType = docType;
    }

    /**
     * @return the instNoId
     */
    public Long getInstNoId() {
        return instNoId;
    }

    /**
     * @param instNoId
     *            the instNoId to set
     */
    public void setInstNoId(Long instNoId) {
        this.instNoId = instNoId;
    }

}
