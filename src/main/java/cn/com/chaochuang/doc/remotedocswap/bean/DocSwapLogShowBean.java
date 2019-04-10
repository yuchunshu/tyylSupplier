/*
 * FileName:    DocSwapEnvelopeShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.bean;

import java.util.Date;

import javax.persistence.Convert;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapLogStatus;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapLogStatusConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapLogType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapLogTypeConverter;


/** 
 * @ClassName: DocSwapLogShowBean 
 * @Description: 限时日志查询列表展示
 * @author: chunshu
 * @date: 2017年7月24日 下午5:07:44  
 */
public class DocSwapLogShowBean {

    private Long 				   id;
    
    /** 内容 */
    private String 			   	   log;
    
    /** 时间 */
    private Date 			   	   logTime;
    private String 				   logTimeShow;
    
    /** 状态 */
    @Convert(converter = RemoteDocSwapLogStatusConverter.class)
    private RemoteDocSwapLogStatus status;
    
    /** 关联封内id */
    private String 			   	   dataId;
    
    /** 类型 */
    @Convert(converter = RemoteDocSwapLogTypeConverter.class)
    private RemoteDocSwapLogType   logType;
    
    /** 备忘 */
    private String 			   	   memo;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
        if (this.logTime != null) {
            this.logTimeShow = Tools.DATE_TIME_FORMAT.format(this.logTime);
        }
	}
	public String getLogTimeShow() {
		return logTimeShow;
	}
	public void setLogTimeShow(String logTimeShow) {
		this.logTimeShow = logTimeShow;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public RemoteDocSwapLogStatus getStatus() {
		return status;
	}
	public void setStatus(RemoteDocSwapLogStatus status) {
		this.status = status;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public RemoteDocSwapLogType getLogType() {
		return logType;
	}
	public void setLogType(RemoteDocSwapLogType logType) {
		this.logType = logType;
	}

}
