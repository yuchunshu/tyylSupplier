/*
 * FileName:    OaAppMessage.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年5月9日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.message.app.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.reference.YesNoConverter;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.oa.message.app.reference.MesStatus;
import cn.com.chaochuang.oa.message.app.reference.MesStatusConverter;
import cn.com.chaochuang.oa.message.app.reference.MesType;
import cn.com.chaochuang.oa.message.app.reference.MesTypeConverter;

/**
 * 应用消息提醒表
 *
 * @author HM
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
public class OaAppMessage extends LongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 4381117123375266204L;
    // // Fields
    // /** 流水号 */
    // private Long id;
    /** 接收人ID */
    private Long              incepterId;
//    /** 接收人 */
//    @ManyToOne
//    @JoinColumn(name = "incepterId", insertable = false, updatable = false)
//    private SysUser           receiveMan;
    /** 发送人 ID */
    private Long              senderId;
    /** 发送人 */
    @ManyToOne
    @JoinColumn(name = "senderId", insertable = false, updatable = false)
    private SysUser           sendMan;
    /** 发送消息 */
    private String            content;
    /** 消息类型 分别为 pendingFileType：待办、toReadMatterType：待阅、mailType：邮件、meetingType：会议、scheduleType：日程 */
    @Convert(converter = MesTypeConverter.class)
    private MesType           mesType;
    /** 创建时间 */
    private Date              createTime;
    /** 送达时间 */
    private Date              arrivalTime;
    /** 状态0未提示 1已提示 */
    @Convert(converter = MesStatusConverter.class)
    private MesStatus         status;
    /** 是否已阅:0否 1是 */
    @Convert(converter = YesNoConverter.class)
    private YesNo             isRead           = YesNo.否;
    /** 跳转url */
    private String            url;

    // /** 待办 */
    // public static final String PENDINGFILETYPE = "0";
    // /** 待阅 */
    // public static final String TOREADMATTERTYPE = "1";
    // /** 邮件 */
    // public static final String MAILTYPE = "2";
    // /** 会议 */
    // public static final String MEETINGTYPE = "3";
    // /** 日程 */
    // public static final String SCHEDULETYPE = "4";
    // /** 状态：未提示 */
    // public static final String STA_HIDDEN = "0";
    // /** 状态：已提示 */
    // public static final String STA_SHOW = "1";

    // Constructors

    /** default constructor */
    public OaAppMessage() {
    }

    /** minimal constructor */
    public OaAppMessage(Long id) {
        this.id = id;
    }


    /**
     * @param incepterId
     * @param senderId
     * @param content
     * @param mesType
     * @param createTime
     * @param status
     * @param isRead
     */
    public OaAppMessage(Long incepterId, Long senderId, String content, MesType mesType, Date createTime,
                    MesStatus status, YesNo isRead) {
        super();
        this.incepterId = incepterId;
        this.senderId = senderId;
        this.content = content;
        this.mesType = mesType;
        this.createTime = createTime;
        this.status = status;
        this.isRead = isRead;
    }

    /**
     * @param incepterId
     * @param senderId
     * @param content
     * @param mesType
     * @param createTime
     * @param status
     * @param isRead
     * @param url
     */
    public OaAppMessage(Long incepterId, Long senderId, String content, MesType mesType, Date createTime,
                    MesStatus status, YesNo isRead, String url) {
        super();
        this.incepterId = incepterId;
        this.senderId = senderId;
        this.content = content;
        this.mesType = mesType;
        this.createTime = createTime;
        this.status = status;
        this.isRead = isRead;
        this.url = url;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
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

//    /**
//     * @return the receiveMan
//     */
//    public SysUser getReceiveMan() {
//        return receiveMan;
//    }
//
//    /**
//     * @param receiveMan
//     *            the receiveMan to set
//     */
//    public void setReceiveMan(SysUser receiveMan) {
//        this.receiveMan = receiveMan;
//    }

    /**
     * @return the sendMan
     */
    public SysUser getSendMan() {
        return sendMan;
    }

    /**
     * @param sendMan
     *            the sendMan to set
     */
    public void setSendMan(SysUser sendMan) {
        this.sendMan = sendMan;
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

}
