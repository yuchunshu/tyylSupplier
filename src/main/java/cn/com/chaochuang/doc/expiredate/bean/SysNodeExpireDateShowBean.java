
package cn.com.chaochuang.doc.expiredate.bean;



import org.dozer.Mapping;

import cn.com.chaochuang.doc.expiredate.reference.DeadlineType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;


/** 
 * @ClassName: SysNodeExpireDateShowBean 
 * @Description: 环节限办时间初始化设置
 * @author: yuchunshu
 * @date: 2017年10月12日 下午5:04:58  
 */
public class SysNodeExpireDateShowBean{


	/** id */
    private String             id;
    
    /** 截止日期类型 */
    private DeadlineType       deadlineType;
    
    /** 公文类型（办件：0 阅件：1） */
    private RemoteDocSwapType  documentType;
    
    /** 环节定义 */
    @Mapping("node.nodeName")
    private String             nodeName;
    
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

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
