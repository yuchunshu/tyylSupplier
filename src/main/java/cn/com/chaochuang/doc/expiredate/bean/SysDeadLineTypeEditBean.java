
package cn.com.chaochuang.doc.expiredate.bean;




import cn.com.chaochuang.doc.expiredate.reference.DeadlineType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;


/** 
 * @ClassName: SysDeadLineTypeEditBean 
 * @Description: 限时办结要求初始化设置
 * @author: yuchunshu
 * @date: 2017年10月12日 下午5:04:58  
 */
public class SysDeadLineTypeEditBean{


	/** id */
    private String            id;
    
    /** 截止日期类型 */
    private DeadlineType      deadlineType;
    
    /** 公文类型（办件：0 阅件：1） */
    private RemoteDocSwapType documentType;
    
    /** 限办天数 */
    private String            expireDay;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DeadlineType getDeadlineType() {
		return deadlineType;
	}

	public void setDeadlineType(DeadlineType deadlineType) {
		this.deadlineType = deadlineType;
	}

	public RemoteDocSwapType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(RemoteDocSwapType documentType) {
		this.documentType = documentType;
	}

	public String getExpireDay() {
		return expireDay;
	}

	public void setExpireDay(String expireDay) {
		this.expireDay = expireDay;
	}

}
