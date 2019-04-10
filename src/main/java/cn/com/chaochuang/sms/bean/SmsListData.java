/*
 * FileName:    SmsListData.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年12月07日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.sms.bean;

import cn.com.chaochuang.common.util.Tools;

import java.util.Date;

/**
 * @author Shicx
 */
public class SmsListData {

    private Long sendId;

    /** 发送时间 */
    private Date sendTime;
    private String sendTimeFmt;


    private Date timingSendTime;
    private String timingSendTimeFmt;

    /** 发送部门Id */
    private Long sendDepId;

    /** 发送人Id */
    private Long sendManId;

    /** 应用名称 */
    private String sysType;

    /** 接收号码 */
    private String receivePhone;

    /** 内容 */
    private String content;

    /** 错误原因 */
    private String err;

    /** EC/SI应用的ID */
    private String serviceId;

    /** 接收人 */
    private String receiveMan;

    public Long getSendId() {
        return sendId;
    }

    public void setSendId(Long sendId) {
        this.sendId = sendId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendTimeFmt() {
        if(this.sendTime!=null) {
            return Tools.DATE_MINUTE_FORMAT.format(this.sendTime);
        }
        return null;
    }

    public Long getSendDepId() {
        return sendDepId;
    }

    public void setSendDepId(Long sendDepId) {
        this.sendDepId = sendDepId;
    }

    public Long getSendManId() {
        return sendManId;
    }

    public void setSendManId(Long sendManId) {
        this.sendManId = sendManId;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getReceiveMan() {
        return receiveMan;
    }

    public void setReceiveMan(String receiveMan) {
        this.receiveMan = receiveMan;
    }

    public Date getTimingSendTime() {
        return timingSendTime;
    }

    public void setTimingSendTime(Date timingSendTime) {
        this.timingSendTime = timingSendTime;
    }

    public String getTimingSendTimeFmt() {
        if(this.timingSendTime!=null) {
            return Tools.DATE_MINUTE_FORMAT.format(this.timingSendTime);
        }
        return null;
    }
}

