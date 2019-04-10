
package cn.com.chaochuang.doc.expiredate.domain;


import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.doc.expiredate.reference.DeadlineType;
import cn.com.chaochuang.doc.expiredate.reference.DeadlineTypeConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapTypeConverter;


/** 
 * @ClassName: SysDeadLineType
 * @Description: 限时办结要求初始化设置
 * @author: yuchunshu
 * @date: 2017年10月17日 下午3:04:58  
 */
@Entity
@Table(name = "sys_deadline_type")
public class SysDeadLineType extends StringUuidEntity {

    private static final long  serialVersionUID      = 1153312316351132725L;

    /** 截止日期类型 */
    @Convert(converter = DeadlineTypeConverter.class)
    private DeadlineType       deadlineType;
    
    /** 公文类型（办件：0 阅件：1） */
    @Convert(converter = RemoteDocSwapTypeConverter.class)
    private RemoteDocSwapType  documentType;
    
    /** 限办天数 */
    private Integer            expireDay;

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

	public Integer getExpireDay() {
		return expireDay;
	}

	public void setExpireDay(Integer expireDay) {
		this.expireDay = expireDay;
	}
    
}
