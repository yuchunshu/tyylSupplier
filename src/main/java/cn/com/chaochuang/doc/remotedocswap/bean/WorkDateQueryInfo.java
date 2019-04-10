package cn.com.chaochuang.doc.remotedocswap.bean;

import javax.persistence.Convert;

import cn.com.chaochuang.doc.remotedocswap.reference.RemoteWorkDateType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteWorkDateTypeConverter;

/**
 * @author Shicx
 */
public class WorkDateQueryInfo {

    private String  		   fromDateGte;
    private String  		   fromDateLte;

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

	public String getFromDateGte() {
		return fromDateGte;
	}

	public void setFromDateGte(String fromDateGte) {
		this.fromDateGte = fromDateGte;
	}

	public String getFromDateLte() {
		return fromDateLte;
	}

	public void setFromDateLte(String fromDateLte) {
		this.fromDateLte = fromDateLte;
	}

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

