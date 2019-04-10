/*
 * FileName:    SmsEditInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年12月06日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.sms.bean;

import cn.com.chaochuang.common.user.domain.SysUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * @author Shicx
 */
public class SmsEditInfo {

    /** 接收人号码:接收人姓名:接收人Id 组成的字符串*/
    private String receiveManData;
    /** 短信类型 */
    private String smsType;
    /** 内容 */
    private String content;
    /** 接收号码 */
    private String cellPhones;
    /** 定时发送标志 */
    private String timingSendFlag;
    /** 定时发送时间 */
    private Date timingSendTime;
    /** 部门简称*/
    private String depAlias;
    /** 是否需要回复 0:不需要  1:需要 */
    private Integer reqDeliveryReport;

    /**创建人,用于新建时传递当前用户*/
    @JsonIgnore
    private SysUser creator;

    public String getReceiveManData() {
        return receiveManData;
    }

    public void setReceiveManData(String receiveManData) {
        this.receiveManData = receiveManData;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCellPhones() {
        return cellPhones;
    }

    public void setCellPhones(String cellPhones) {
        this.cellPhones = cellPhones;
    }

    public String getTimingSendFlag() {
        return timingSendFlag;
    }

    public void setTimingSendFlag(String timingSendFlag) {
        this.timingSendFlag = timingSendFlag;
    }

    public Date getTimingSendTime() {
        return timingSendTime;
    }

    public void setTimingSendTime(Date timingSendTime) {
        this.timingSendTime = timingSendTime;
    }

    public String getDepAlias() {
        return depAlias;
    }

    public void setDepAlias(String depAlias) {
        this.depAlias = depAlias;
    }

    public Integer getReqDeliveryReport() {
        return reqDeliveryReport;
    }

    public void setReqDeliveryReport(Integer reqDeliveryReport) {
        this.reqDeliveryReport = reqDeliveryReport;
    }

    public SysUser getCreator() {
        return creator;
    }

    public void setCreator(SysUser creator) {
        this.creator = creator;
    }
}

