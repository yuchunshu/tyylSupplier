package cn.com.chaochuang.pubaffairs.meal.bean;

import java.util.Date;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;

/**
 * @author dengl 2018.08.13
 *
 */

public class OaMealStatbean {
	
	/** 用餐时间*/
    private Date 			mealDate;
    private String			mealDateShow;
    
    /** 青湖员工餐*/
    private Long 			staffMealTotalQH;
    
    /** 青湖接待餐*/
    private Long			recepMealTotalQH;
    
    /** 青湖加班餐*/
    private Long			overtimeMealTotalQH;
    
    /** 青湖自费餐*/
    private Long			practiceMealTotalQH;
    
    /** 明秀员工餐*/
    private Long 			staffMealTotalMX;
    
    /** 明秀接待餐*/
    private Long			recepMealTotalMX;
    
    /** 明秀加班餐*/
    private Long			overtimeMealTotalMX;
    
    /** 明秀自费餐*/
    private Long			practiceMealTotalMX;
    
    /** 青湖总数*/
    private Long 			totalQH;
    
    /** 明秀总数*/
    private Long			totalMX;
    
    /** 总数*/
    private Long			total;
    
    /** 状态 */
    private CarStatus       status; 
    
    private Long adjustNum(Long num) {
    	if (num == null) return Long.valueOf(0); else return num;
    }
    
    public Long getTotalQH() {
		return adjustNum(this.staffMealTotalQH)
				+ adjustNum(this.recepMealTotalQH)
				+ adjustNum(this.overtimeMealTotalQH)
				+ adjustNum(this.practiceMealTotalQH);
	}
    
    public Long getTotalMX() {
		return adjustNum(this.staffMealTotalMX)
				+ adjustNum(this.recepMealTotalMX)
				+ adjustNum(this.overtimeMealTotalMX)
				+ adjustNum(this.practiceMealTotalMX);
	}

	public Long getTotal() {
		return adjustNum(this.staffMealTotalQH)
				+ adjustNum(this.recepMealTotalQH)
				+ adjustNum(this.overtimeMealTotalQH)
				+ adjustNum(this.practiceMealTotalQH)
				+ adjustNum(this.staffMealTotalMX)
				+ adjustNum(this.recepMealTotalMX)
				+ adjustNum(this.overtimeMealTotalMX)
				+ adjustNum(this.practiceMealTotalMX);
	}

	public Date getMealDate() {
		return mealDate;
	}

	public void setMealDate(Date mealDate) {
		this.mealDate = mealDate;
	}

	public Long getStaffMealTotalQH() {
		return staffMealTotalQH;
	}

	public void setStaffMealTotalQH(Long staffMealTotalQH) {
		this.staffMealTotalQH = staffMealTotalQH;
	}

	public Long getRecepMealTotalQH() {
		return recepMealTotalQH;
	}

	public void setRecepMealTotalQH(Long recepMealTotalQH) {
		this.recepMealTotalQH = recepMealTotalQH;
	}

	public Long getOvertimeMealTotalQH() {
		return overtimeMealTotalQH;
	}

	public void setOvertimeMealTotalQH(Long overtimeMealTotalQH) {
		this.overtimeMealTotalQH = overtimeMealTotalQH;
	}

	public Long getPracticeMealTotalQH() {
		return practiceMealTotalQH;
	}

	public void setPracticeMealTotalQH(Long practiceMealTotalQH) {
		this.practiceMealTotalQH = practiceMealTotalQH;
	}

	public Long getStaffMealTotalMX() {
		return staffMealTotalMX;
	}

	public void setStaffMealTotalMX(Long staffMealTotalMX) {
		this.staffMealTotalMX = staffMealTotalMX;
	}

	public Long getRecepMealTotalMX() {
		return recepMealTotalMX;
	}

	public void setRecepMealTotalMX(Long recepMealTotalMX) {
		this.recepMealTotalMX = recepMealTotalMX;
	}

	public Long getOvertimeMealTotalMX() {
		return overtimeMealTotalMX;
	}

	public void setOvertimeMealTotalMX(Long overtimeMealTotalMX) {
		this.overtimeMealTotalMX = overtimeMealTotalMX;
	}

	public Long getPracticeMealTotalMX() {
		return practiceMealTotalMX;
	}

	public void setPracticeMealTotalMX(Long practiceMealTotalMX) {
		this.practiceMealTotalMX = practiceMealTotalMX;
	}

	public CarStatus getStatus() {
		return status;
	}

	public void setStatus(CarStatus status) {
		this.status = status;
	}
	
	public String getMealDateShow() {
		if (this.mealDate != null) {
			this.mealDateShow = Tools.DATE_FORMAT.format(mealDate);
		}
		return mealDateShow;
	}

	public void setMealDateShow(String mealDateShow) {
		this.mealDateShow = mealDateShow;
	}

}
