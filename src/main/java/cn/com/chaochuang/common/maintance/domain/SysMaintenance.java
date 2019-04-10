/*
 * FileName:    SysMaintenance.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年11月17日 (huangm) 1.0 Create
 */

package cn.com.chaochuang.common.maintance.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.maintance.reference.QueStatus;
import cn.com.chaochuang.common.maintance.reference.QueStatusConverter;
import cn.com.chaochuang.common.maintance.reference.QueType;
import cn.com.chaochuang.common.maintance.reference.QueTypeConverter;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.reference.AttachFlagConverter;

/**
 * 问题反馈（系统维护需求）
 *
 * @author huangm
 *
 */
@Entity
@Table(name = "sys_maintenance")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "maint_id")) })
public class SysMaintenance extends StringUuidEntity {

    private static final long  serialVersionUID = 5791252505744217337L;

    public static final String _ATT_OWNER_TYPE  = "maint";

    /** 问题描述 */
    private String             queDesc;

    /** 需求分类 */
    @Convert(converter = QueTypeConverter.class)
    private QueType            queType;

    /** 提出时间 */
    private Date               queTime;

    /** 提出人（姓名）/ 用户 */
    private String             raiser;

    /** 联系电话 */
    private String             telphone;

    /** 状态 */
    @Convert(converter = QueStatusConverter.class)
    private QueStatus          status;

    /** 处理人 */
    private String             dealer;

    /** 处理时间 */
    private Date               dealTime;

    /** 处理结果 */
    private String             dealResult;

    /** 所属子系统 */
    private String             subSys;

    /** 有无附件 */
    @Convert(converter = AttachFlagConverter.class)
    private AttachFlag         attachFlag;

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
    public Date getQueTime() {
        return queTime;
    }

    /**
     * @param queTime
     *            the queTime to set
     */
    public void setQueTime(Date queTime) {
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
    public Date getDealTime() {
        return dealTime;
    }

    /**
     * @param dealTime
     *            the dealTime to set
     */
    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    /**
     * @return the dealResult
     */
    public String getDealResult() {
        return dealResult;
    }

    /**
     * @param dealResult
     *            the dealResult to set
     */
    public void setDealResult(String dealResult) {
        this.dealResult = dealResult;
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
