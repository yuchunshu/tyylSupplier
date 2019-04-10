package cn.com.chaochuang.sms.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
// 区局oracle数据库
//@Table(name = "GX_FDA_SYS.SMS_PHRASE")
@Table(name = "SMS_SENDING")
public class SmsSending implements Serializable {

    /** 即时发送 */
    public static final String IMM_SEND = "0";
    /** 定时发送 */
    public static final String TIMING_SEND = "1";

	@Id
	// 区局oracle数据库 使用序列
	//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	//	@SequenceGenerator(name = "SMS_SENDING_ID")
	// 本地mysql
	@GeneratedValue
    /** 发送id */
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

    /** EC/SI应用的ID */
    private String serviceId;

    /** 定时发送标志 默认为：0 即时发送 1：为定时发送*/
    private String timingSendFlag;

    /** 定时发送时间 */
	@Temporal(TemporalType.TIMESTAMP)
    private Date timingSendTime;

    /** default constructor */
    public SmsSending() {
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

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Date getTimingSendTime() {
		return timingSendTime;
	}

	public void setTimingSendTime(Date timingSendTime) {
		this.timingSendTime = timingSendTime;
	}

	public String getTimingSendFlag() {
		return timingSendFlag;
	}

	public void setTimingSendFlag(String timingSendFlag) {
		this.timingSendFlag = timingSendFlag;
	}
}
