/*
 * FileName:    SmsBaseInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2011
 * History:     2011-9-29 (pjs) 1.0 Create
 */

package cn.com.chaochuang.doc.sms.bean;

import java.util.Date;


/** 
 * @ClassName: SmsBaseInfo 
 * @Description: 短信发送实体类
 * @author: chunshu
 * @date: 2017年9月21日 上午10:06:45  
 */
public class SmsBaseInfo {
	
	/** 是否需要回复 0:不需要 */
	public static Integer REPORT_NEED = 1;
	/** 是否需要回复 1:需要 */
    public static Integer REPORT_NOT_NEED = 0;
    /** 即时发送 */
    public static String IMM_SEND = "0";
    /** 定时发送 */
    public static String TIMING_SEND = "1";
    
    /** 内容 */
	private String 		 message;
	/** 定时发送标志 默认为：0 即时发送 1：为定时发送*/
	private String 		 timingSendFlag;
	/** 定时发送时间 */
	private Date 		 timingSendTime;
	/** 发送人ID */
	private Long 		 sendManId;
	/** 是否需要回复 0:不需要  1:需要 */
    private Integer 	 reqDeliveryReport;

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setTimingSendTime(Date timingSendTime) {
		this.timingSendTime = timingSendTime;
	}
	
	public Date getTimingSendTime() {
		return timingSendTime;
	}
	
	public void setTimingSendFlag(String timingSendFlag) {
		this.timingSendFlag = timingSendFlag;
	}
	
	public void setSendManId(Long sendManId) {
		this.sendManId = sendManId;
	}
	
	public Long getSendManId() {
		return sendManId;
	}
	
	public Integer getReqDeliveryReport() {
		return reqDeliveryReport;
	}
	
	public void setReqDeliveryReport(Integer reqDeliveryReport) {
		this.reqDeliveryReport = reqDeliveryReport;
	}
	
	public String getTimingSendFlag() {
		return timingSendFlag;
	}
	
}
