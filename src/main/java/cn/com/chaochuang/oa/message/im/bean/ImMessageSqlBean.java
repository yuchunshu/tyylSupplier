/*
 * FileName:    ImMessageSqlBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年5月28日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.message.im.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.chaochuang.oa.message.im.reference.ImDelFlag;
import cn.com.chaochuang.oa.message.im.reference.ImMesStatus;

/**
 * @author HM
 *
 */
public class ImMessageSqlBean {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /** 流水号 */
    private Long             id;

    /** 发送人id */
    private Long             senderId;

    /** 发送人 */
    private String           senderName;

    /** 接收人id */
    private Long             incepterId;

    /** 接收人 */
    private String           incepterName;

    /** 内容 */
    private String           content;

    /** 创建时间 */
    private Date             createTime;
    private String           createTimeShow;

    /** 送达时间 */
    private Date             arrivalTime;
    private String           arrivalTimeShow;

    /** 接收状态 */
    private ImMesStatus      status;

    /** 发送删除标志 */
    private ImDelFlag        outdelFlag;

    /** 接收删除标志 */
    private ImDelFlag        indelFlag;


    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return the senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * @param senderName
     *            the senderName to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * @return the incepterId
     */
    public Long getIncepterId() {
        return incepterId;
    }

    /**
     * @param incepterId
     *            the incepterId to set
     */
    public void setIncepterId(Long incepterId) {
        this.incepterId = incepterId;
    }

    /**
     * @return the incepterName
     */
    public String getIncepterName() {
        return incepterName;
    }

    /**
     * @param incepterName
     *            the incepterName to set
     */
    public void setIncepterName(String incepterName) {
        this.incepterName = incepterName;
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
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        if (this.createTime != null) {
            this.createTimeShow = this.sdf.format(this.createTime);
        }
    }

    /**
     * @return the createTimeShow
     */
    public String getCreateTimeShow() {
        return createTimeShow;
    }

    /**
     * @return the arrivalTime
     */
    public Date getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @param arrivalTime
     *            the arrivalTime to set
     */
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
        if (this.arrivalTime != null) {
            this.arrivalTimeShow = this.sdf.format(arrivalTime);
        }
    }

    /**
     * @return the arrivalTimeShow
     */
    public String getArrivalTimeShow() {
        return arrivalTimeShow;
    }

    /**
     * @return the status
     */
    public ImMesStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(ImMesStatus status) {
        this.status = status;
    }

    /**
     * @return the outdelFlag
     */
    public ImDelFlag getOutdelFlag() {
        return outdelFlag;
    }

    /**
     * @param outdelFlag
     *            the outdelFlag to set
     */
    public void setOutdelFlag(ImDelFlag outdelFlag) {
        this.outdelFlag = outdelFlag;
    }

    /**
     * @return the indelFlag
     */
    public ImDelFlag getIndelFlag() {
        return indelFlag;
    }

    /**
     * @param indelFlag
     *            the indelFlag to set
     */
    public void setIndelFlag(ImDelFlag indelFlag) {
        this.indelFlag = indelFlag;
    }
}
