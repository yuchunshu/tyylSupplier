package cn.com.chaochuang.sms.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
// 区局oracle数据库
//@Table(name = "GX_FDA_SYS.SMS_PHRASE")
@Table(name = "SMS_RECEIVING")
public class SmsReceiving implements Serializable {

	/** 接收id */
	@Id
	// 区局oracle数据库 使用序列
	//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	//	@SequenceGenerator(name = "SMS_RECEIVING_ID")
	// 本地mysql
	@GeneratedValue
    private Long receiveId;

    /** 发送ID */
    private Integer sendId;

    /** 接收时间 */
	@Temporal(TemporalType.TIMESTAMP)
    private Date receiveTime;

    /** 号码 */
    private String phone;

    /** 服务id */
    private String serviceId;

    /** 内容 */
    private String content;

    /** 标志 */
    private String flag;

    /** default constructor */
    public SmsReceiving() {
    }

	public Long getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(Long receiveId) {
		this.receiveId = receiveId;
	}

	public Integer getSendId() {
		return sendId;
	}

	public void setSendId(Integer sendId) {
		this.sendId = sendId;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
