package cn.com.chaochuang.crm.customer.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.user.reference.Sex;
import cn.com.chaochuang.common.user.reference.SexConverter;

@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "cus_id")) })
public class Customer extends LongIdEntity {

	private static final long serialVersionUID = 2137845822962483790L;
	
	/** 客户名称 */
    private String   	cusName;
    
    /**联系人*/
    private String 		contactName;
    
    /**联系电话*/
    private String 		contactPhone;
    
    /** 客户名称 */
    private String   	cusCategory;
    
    /** 部门/职务 */
    private String   	duty;
    
    /** 客户地址 */
    private String   	address;
    
    /** 录入日期 */
    private Date   	 	createTime;

}
