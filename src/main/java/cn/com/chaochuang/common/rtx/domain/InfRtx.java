/**
 *
 */
package cn.com.chaochuang.common.rtx.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;

/**
 * @author hzr 2016年8月15日
 * 用于绑定的RTX弹出提醒功能，本系统只需将提醒内容写入该表，扫描并负责发送的程序不在本项目内
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
@Table(name = "wf_inf_rtx")
public class InfRtx extends LongIdEntity {

    private static final long serialVersionUID = -6064561069441913743L;

    /**发送人*/
    private Long    sendMan;

    /**接收人*/
    private Long    receiveMan;

    /**所属应用系统，appId='00'，表示系统发出的提醒消息，目前只用该固定值*/
    private String  appId = "00";

    /**发送时间*/
    private Date    sendTime;

    /**内容（长度2000）*/
    private String  content;

    public InfRtx() {};

    public InfRtx(Long sendMan, Long receiveMan, String content) {
        this.sendMan = sendMan;
        this.receiveMan = receiveMan;
        this.content = content;
        this.sendTime = new Date();
    }

    public Long getSendMan() {
        return sendMan;
    }

    public void setSendMan(Long sendMan) {
        this.sendMan = sendMan;
    }

    public Long getReceiveMan() {
        return receiveMan;
    }

    public void setReceiveMan(Long receiveMan) {
        this.receiveMan = receiveMan;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
