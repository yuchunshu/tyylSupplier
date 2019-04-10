/*
 * FileName:    MaintenanceShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年11月17日 (huangm) 1.0 Create
 */

package cn.com.chaochuang.common.maintance.bean;

import cn.com.chaochuang.common.maintance.reference.QueStatus;
import cn.com.chaochuang.common.maintance.reference.QueType;
import cn.com.chaochuang.common.reference.AttachFlag;

/**
 * @author huangm
 *
 */
public class MaintenanceShowBean {

    private String     id;

    /** 问题描述 */
    private String     queDesc;

    /** 需求分类 */
    private QueType    queType;

    /** 提出时间 */
    private String     queTime;

    /** 提出人（姓名）/ 用户 */
    private String     raiser;

    /** 联系电话 */
    private String     telphone;

    /** 状态 */
    private QueStatus  status;

    /** 处理人 */
    private String     dealer;

    /** 处理时间 */
    private String     dealTime;

    /** 所属子系统 */
    private String     subSys;

    /** 有无附件 */
    private AttachFlag attachFlag;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the queDesc
     */
    public String getQueDesc() {
        return queDesc;
    }

    /**
     * @param queDesc
     *            the queDesc to set
     */
    public void setQueDesc(String queDesc) {
        this.queDesc = queDesc;
    }

    /**
     * @return the queType
     */
    public QueType getQueType() {
        return queType;
    }

    /**
     * @param queType
     *            the queType to set
     */
    public void setQueType(QueType queType) {
        this.queType = queType;
    }

    /**
     * @return the queTime
     */
    public String getQueTime() {
        return queTime;
    }

    /**
     * @param queTime
     *            the queTime to set
     */
    public void setQueTime(String queTime) {
        this.queTime = queTime;
    }

    /**
     * @return the raiser
     */
    public String getRaiser() {
        return raiser;
    }

    /**
     * @param raiser
     *            the raiser to set
     */
    public void setRaiser(String raiser) {
        this.raiser = raiser;
    }

    /**
     * @return the telphone
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * @param telphone
     *            the telphone to set
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    /**
     * @return the status
     */
    public QueStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(QueStatus status) {
        this.status = status;
    }

    /**
     * @return the dealer
     */
    public String getDealer() {
        return dealer;
    }

    /**
     * @param dealer
     *            the dealer to set
     */
    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    /**
     * @return the dealTime
     */
    public String getDealTime() {
        return dealTime;
    }

    /**
     * @param dealTime
     *            the dealTime to set
     */
    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    /**
     * @return the subSys
     */
    public String getSubSys() {
        return subSys;
    }

    /**
     * @param subSys
     *            the subSys to set
     */
    public void setSubSys(String subSys) {
        this.subSys = subSys;
    }

    /**
     * @return the attachFlag
     */
    public AttachFlag getAttachFlag() {
        return attachFlag;
    }

    /**
     * @param attachFlag
     *            the attachFlag to set
     */
    public void setAttachFlag(AttachFlag attachFlag) {
        this.attachFlag = attachFlag;
    }

}
