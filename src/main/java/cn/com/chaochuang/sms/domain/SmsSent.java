package cn.com.chaochuang.sms.domain;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
// 区局oracle数据库
//@Table(name = "GX_FDA_SYS.SMS_PHRASE")
@Table(name = "SMS_SENDED")
public class SmsSent implements Serializable {

    /** 发送id */
	@Id
	@GeneratedValue
    private Long sendId;

    /** 发送时间 */
	@Temporal(TemporalType.TIMESTAMP)
    private Date sendTime;

    /** 接收人Id */
    private Long receiveManId;

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

	/**
	 * 回复内容
	 */
	@OneToMany(fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "sendId", insertable = false, updatable = false)
	private List<SmsReceiving> receivingList;


    /** default constructor */
    public SmsSent() {
    }

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

	public Long getReceiveManId() {
		return receiveManId;
	}

	public void setReceiveManId(Long receiveManId) {
		this.receiveManId = receiveManId;
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

	public List<SmsReceiving> getReceivingList() {
		return receivingList;
	}

	public void setReceivingList(List<SmsReceiving> receivingList) {
		this.receivingList = receivingList;
	}
}
