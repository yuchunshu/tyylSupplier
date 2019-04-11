/*
 * FileName:    LogShowBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年4月15日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.log.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Convert;

import org.dozer.Mapping;

import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.LogStatusConverter;
import cn.com.chaochuang.common.log.reference.LogType;
import cn.com.chaochuang.common.log.reference.SjType;

/**
 * @author LJX
 *
 */
public class LogShowBean {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Mapping("operator.userName")
    private String           operatorName;
    /** 操作IP */
    private String           ip;
    /** 操作对象 */
    private String           url;
    /** 操作类型 */
    private String           content;
    /** 操作时间 */
    private String           operateDateStr;
    /** 日志类型 */
    private LogType          logType;
    /** 审计类型 */
    private SjType           sjType;
    /** 状态 */
    private LogStatus        status;
    /**
     * @return the operateDateStr
     */
    public String getOperateDateStr() {
        return operateDateStr;
    }

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
     * @return the operatorName
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * @param operatorName
     *            the operatorName to set
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     *            the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
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
        this.url = url;
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
     * @return the operateDate
     */
    public Date getOperateDate() {
        return null;
    }

    /**
     * @param operateDate
     *            the operateDate to set
     */
    public void setOperateDate(Date operateDate) {
        if (operateDate != null) {
            this.operateDateStr = sdf.format(operateDate);
        }
    }

    /**
     * @return the logType
     */
    public LogType getLogType() {
        return logType;
    }

    /**
     * @param logType
     *            the logType to set
     */
    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    /**
     * @return the sjType
     */
    public SjType getSjType() {
        return sjType;
    }

    /**
     * @param sjType
     *            the sjType to set
     */
    public void setSjType(SjType sjType) {
        this.sjType = sjType;
    }

	public LogStatus getStatus() {
		return status;
	}

	public void setStatus(LogStatus status) {
		this.status = status;
	}

}
