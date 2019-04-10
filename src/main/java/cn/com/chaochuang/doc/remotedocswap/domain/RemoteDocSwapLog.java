package cn.com.chaochuang.doc.remotedocswap.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapLogStatus;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapLogStatusConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapLogType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapLogTypeConverter;

 
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "log_id")) })
@Table(name = "remote_doc_swap_log")
public class RemoteDocSwapLog extends LongIdEntity {

    /**  */
    private static final long  serialVersionUID  = 1514947913908488632L;
 
    /** 内容 */
    private String 			   	   log;
    
    /** 时间 */
    private Date 			   	   logTime;
    
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
	}

	public RemoteDocSwapLogStatus getStatus() {
		return status;
	}

	public void setStatus(RemoteDocSwapLogStatus status) {
		this.status = status;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public RemoteDocSwapLogType getLogType() {
		return logType;
	}

	public void setLogType(RemoteDocSwapLogType logType) {
		this.logType = logType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
