/*
 * FileName:    OaImMessage.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年5月24日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.message.im.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.oa.message.im.reference.ImDelFlag;
import cn.com.chaochuang.oa.message.im.reference.ImDelFlagConverter;
import cn.com.chaochuang.oa.message.im.reference.ImMesStatus;
import cn.com.chaochuang.oa.message.im.reference.ImMesStatusConverter;

/**
 * 即时消息
 *
 * @author HM
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
public class OaImMessage extends LongIdEntity {

    private static final long serialVersionUID = 1611901905956430034L;

    /** 发送人id */
    private Long              senderId;

    /** 发送人 */
    @ManyToOne
    @JoinColumn(name = "senderId", insertable = false, updatable = false)
    private SysUser           sender;

    /** 接收人id */
    private Long              incepterId;

    /** 接收人 */
    @ManyToOne
    @JoinColumn(name = "incepterId", insertable = false, updatable = false)
    private SysUser           incepter;

    /** 内容 */
    private String            content;

    /** 创建时间 */
    private Date              createTime;

    /** 送达时间 */
    private Date              arrivalTime;

    /** 接收状态 */
    @Convert(converter = ImMesStatusConverter.class)
    private ImMesStatus       status           = ImMesStatus.发送中;

    /** 发送删除标志 */
    @Convert(converter = ImDelFlagConverter.class)
    private ImDelFlag         outdelFlag       = ImDelFlag.未删除;

    /** 接收删除标志 */
    @Convert(converter = ImDelFlagConverter.class)
    private ImDelFlag         indelFlag        = ImDelFlag.未删除;

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
     * @return the sender
     */
    public SysUser getSender() {
        return sender;
    }

    /**
     * @param sender
     *            the sender to set
     */
    public void setSender(SysUser sender) {
        this.sender = sender;
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
     * @return the incepter
     */
    public SysUser getIncepter() {
        return incepter;
    }

    /**
     * @param incepter
     *            the incepter to set
     */
    public void setIncepter(SysUser incepter) {
        this.incepter = incepter;
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
