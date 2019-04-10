/*
 * FileName:    RemoteWorkDate.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2017年8月1日 (yuchunshu) 1.0 Create
 */
package cn.com.chaochuang.doc.remotedocswap.domain;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvTypeConverter;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteWorkDateType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteWorkDateTypeConverter;

@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "work_date_id")) })
@Table(name = "remote_work_date")
public class RemoteWorkDate extends LongIdEntity {

	private static final long  serialVersionUID = 3449347776817418072L;

    /**开始日期*/
    private String             fromDate;
    
    /**限办工日*/
    private Integer 		   workDays;
    
    /**结束日期*/
    private String 			   toDate;
    
    /** 类型 
     * 区政府的工作日数据
     * 自定义的节假日数据
     * 自定义的加班日数据
     * */
    @Convert(converter = RemoteWorkDateTypeConverter.class)
    private RemoteWorkDateType type;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public Integer getWorkDays() {
		return workDays;
	}

	public void setWorkDays(Integer workDays) {
		this.workDays = workDays;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public RemoteWorkDateType getType() {
		return type;
	}

	public void setType(RemoteWorkDateType type) {
		this.type = type;
	}

}
