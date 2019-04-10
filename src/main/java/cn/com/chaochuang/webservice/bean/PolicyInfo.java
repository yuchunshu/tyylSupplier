/*
 * FileName:    Policy.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年3月25日 (LLM) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

import java.util.Date;

/**
 * @author huangwq
 *
 */
public class PolicyInfo {
    /** 原系统的法规编号 */
    private Long   rmPolicyId;
    /** 法规名称 */
    private String policyName;
    /** 颁发单位 */
    private String releaseUnit;
    /** 颁发日期 */
    private Date   releaseDate;
    /** 实施日期 */
    private Date   effectDate;
    /** 数据导入日期 */
    private Date   inputDate;
    /** 内容 */
    private String content;
    /** 状态 */
    private String status;

    /**
     * @return the policyName
     */
    public String getPolicyName() {
        return policyName;
    }

    /**
     * @param policyName
     *            the policyName to set
     */
    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    /**
     * @return the releaseUnit
     */
    public String getReleaseUnit() {
        return releaseUnit;
    }

    /**
     * @param releaseUnit
     *            the releaseUnit to set
     */
    public void setReleaseUnit(String releaseUnit) {
        this.releaseUnit = releaseUnit;
    }

    /**
     * @return the inputDate
     */
    public Date getInputDate() {
        return inputDate;
    }

    /**
     * @param inputDate
     *            the inputDate to set
     */
    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    /**
     * @return the rmPolicyId
     */
    public Long getRmPolicyId() {
        return rmPolicyId;
    }

    /**
     * @param rmPolicyId
     *            the rmPolicyId to set
     */
    public void setRmPolicyId(Long rmPolicyId) {
        this.rmPolicyId = rmPolicyId;
    }

    /**
     * @return the releaseDate
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate
     *            the releaseDate to set
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return the effectDate
     */
    public Date getEffectDate() {
        return effectDate;
    }

    /**
     * @param effectDate
     *            the effectDate to set
     */
    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
