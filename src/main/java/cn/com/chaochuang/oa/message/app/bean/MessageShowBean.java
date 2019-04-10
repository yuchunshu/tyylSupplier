/*
 * FileName:    MessageShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年5月10日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.message.app.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapping;

import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.oa.message.app.reference.MesStatus;
import cn.com.chaochuang.oa.message.app.reference.MesType;

/**
 * @author HM
 *
 */
public class MessageShowBean {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /** 流水号 */
    private Long             id;

    /** 接收人ID */
    private Long             incepterId;

    // /** 接收人 */
    // @Mapping("receiveMan.userName")
    // private String receiveManName;

    /** 发送人ID */
    private Long             senderId;

    /** 发送人 */
    @Mapping("sendMan.userName")
    private String           sendManName;

    /** 发送消息 */
    private String           content;

    /** 消息类型 分别为 pendingFileType：待办、toReadMatterType：待阅、mailType：邮件、meetingType：会议、scheduleType：日程 */
    private MesType          mesType;
    private String           mesTypeKey;

    /** 创建时间 */
    private Date             createTime;
    private String           createTimeShow;

    /** 送达时间 */
    private Date             arrivalTime;
    private String           arrivalTimeShow;

    /** 状态0未提示 1已提示 */
    private MesStatus        status;

    /** 是否已阅:0否 1是 */
    private YesNo            isRead;

    /** 跳转url */
    private String           url;

    /**
     * @return the sdf
     */
    public SimpleDateFormat getSdf() {
        return sdf;
    }

    /**
     * @param sdf
     *            the sdf to set
     */
    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

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

    // /**
    // * @return the receiveManName
    // */
    // public String getReceiveManName() {
    // return receiveManName;
    // }
    //
    // /**
    // * @param receiveManName
    // * the receiveManName to set
    // */
    // public void setReceiveManName(String receiveManName) {
    // this.receiveManName = receiveManName;
    // }

    /**
     * @return the sendManName
     */
    public String getSendManName() {
        return sendManName;
    }

    /**
     * @param sendManName
     *            the sendManName to set
     */
    public void setSendManName(String sendManName) {
        this.sendManName = sendManName;
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
     * @return the mesType
     */
    public MesType getMesType() {
        return mesType;
    }

    /**
     * @param mesType
     *            the mesType to set
     */
    public void setMesType(MesType mesType) {
        this.mesType = mesType;
        if (this.mesType != null) {
            this.mesTypeKey = this.mesType.getKey();
        }
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return null;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        if (this.createTime != null) {
            this.createTimeShow = sdf.format(this.createTime);
        }
    }

    /**
     * @return the arrivalTime
     */
    public Date getArrivalTime() {
        return null;
    }

    /**
     * @param arrivalTime
     *            the arrivalTime to set
     */
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
        if (this.arrivalTime != null) {
            this.arrivalTimeShow = sdf.format(this.arrivalTime);
        }
    }

    /**
     * @return the status
     */
    public MesStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(MesStatus status) {
        this.status = status;
    }

    /**
     * @return the createTimeShow
     */
    public String getCreateTimeShow() {
        return createTimeShow;
    }

    /**
     * @return the arrivalTimeShow
     */
    public String getArrivalTimeShow() {
        return arrivalTimeShow;
    }

    /**
     * @return the mesTypeKey
     */
    public String getMesTypeKey() {
        return mesTypeKey;
    }

    /**
     * @return the isRead
     */
    public YesNo getIsRead() {
        return isRead;
    }

    /**
     * @param isRead
     *            the isRead to set
     */
    public void setIsRead(YesNo isRead) {
        this.isRead = isRead;
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
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = StringUtils.trimToNull(url);
    }

}
