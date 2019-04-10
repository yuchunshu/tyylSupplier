
package cn.com.chaochuang.doc.expiredate.domain;


import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.doc.expiredate.reference.DeadlineType;
import cn.com.chaochuang.doc.expiredate.reference.DeadlineTypeConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapTypeConverter;
import cn.com.chaochuang.workflow.def.domain.WfNode;


/** 
 * @ClassName: SysNodeExpireDate 
 * @Description: 环节限办时间初始化设置
 * @author: yuchunshu
 * @date: 2017年10月12日 下午5:04:58  
 */
@Entity
@Table(name = "sys_node_expiredate")
public class SysNodeExpireDate extends StringUuidEntity {

    private static final long  serialVersionUID      = 1153312316351132725L;

    /** 公文类型（办件：0 阅件：1） */
    @Convert(converter = RemoteDocSwapTypeConverter.class)
    private RemoteDocSwapType  documentType;
    
    /** 截止日期类型 */
    @Convert(converter = DeadlineTypeConverter.class)
    private DeadlineType       deadlineType;
    
    /** 环节定义 */
    private String             nodeId;
    @ManyToOne
    @JoinColumn(name = "nodeId", insertable = false, updatable = false)
    private WfNode             node;
    
    /** 环节限办天数 */
    private String             expireDay;
    
    /** 环节限办小时 */
    private String             expireHour;
    
    /** 环节限办分钟 */
    private String             expireMinute;
    
    /** 环节预警天数*/
    private String			   earlyWarnDay;
    
    /** 环节预警小时*/
    private String			   earlyWarnHour;
    
    /** 环节预警分钟*/
    private String			   earlyWarnMinute;

	public DeadlineType getDeadlineType() {
		return deadlineType;
	}

	public void setDeadlineType(DeadlineType deadlineType) {
		this.deadlineType = deadlineType;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public WfNode getNode() {
		return node;
	}

	public void setNode(WfNode node) {
		this.node = node;
	}

	public String getExpireDay() {
		return expireDay;
	}

	public void setExpireDay(String expireDay) {
		this.expireDay = expireDay;
	}

	public String getExpireHour() {
		return expireHour;
	}

	public void setExpireHour(String expireHour) {
		this.expireHour = expireHour;
	}

	public String getExpireMinute() {
		return expireMinute;
	}

	public void setExpireMinute(String expireMinute) {
		this.expireMinute = expireMinute;
	}

	public RemoteDocSwapType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(RemoteDocSwapType documentType) {
		this.documentType = documentType;
	}

	public String getEarlyWarnDay() {
		return earlyWarnDay;
	}

	public void setEarlyWarnDay(String earlyWarnDay) {
		this.earlyWarnDay = earlyWarnDay;
	}

	public String getEarlyWarnHour() {
		return earlyWarnHour;
	}

	public void setEarlyWarnHour(String earlyWarnHour) {
		this.earlyWarnHour = earlyWarnHour;
	}

	public String getEarlyWarnMinute() {
		return earlyWarnMinute;
	}

	public void setEarlyWarnMinute(String earlyWarnMinute) {
		this.earlyWarnMinute = earlyWarnMinute;
	}
}
