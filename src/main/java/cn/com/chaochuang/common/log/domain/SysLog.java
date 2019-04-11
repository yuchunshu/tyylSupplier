/*
 * FileName:    Log.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年12月8日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.log.domain;

import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.LogStatusConverter;
import cn.com.chaochuang.common.log.reference.LogType;
import cn.com.chaochuang.common.log.reference.LogTypeConverter;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.reference.SjTypeConverter;
import cn.com.chaochuang.common.user.domain.SysUser;

/**
 * @author LaoZhiYong
 *
 */
@Entity
public class SysLog extends LongIdEntity {
    private static final long serialVersionUID = 4654453959299617496L;

    /** 操作人，如果能修改用户信息就要加冗余字段记下原始的用户名 */
    @ManyToOne
    @JoinColumn(name = "operatorId")
    private SysUser           operator;
    /** 操作IP */
    private String            ip;
    /** 操作对象 */
    private String            url;
    /** 操作类型 */
    private String            content;
    /** 操作时间 */
    private Date              operateDate;
    /** 日志类型 */
    @Convert(converter = LogTypeConverter.class)
    private LogType           logType          = LogType.普通用户;
    /** 审计类型 */
    @Convert(converter = SjTypeConverter.class)
    private SjType            sjType           = SjType.其他;
    /** 状态 */
    @Convert(converter = LogStatusConverter.class)
    private LogStatus         status           = LogStatus.成功;

    /**
     * @return the operator
     */
    public SysUser getOperator() {
        return operator;
    }

    /**
     * @param operator
     *            the operator to set
     */
    public void setOperator(SysUser operator) {
        this.operator = operator;
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
        return operateDate;
    }

    /**
     * @param operateDate
     *            the operateDate to set
     */
    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
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
