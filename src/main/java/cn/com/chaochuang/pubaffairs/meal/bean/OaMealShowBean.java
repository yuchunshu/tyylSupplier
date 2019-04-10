package cn.com.chaochuang.pubaffairs.meal.bean;

import java.util.Date;

import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.meal.reference.MealPlace;
import cn.com.chaochuang.pubaffairs.meal.reference.MealType;

/**
 * @author dengl 2018.08.13
 *
 */
public class OaMealShowBean {
	
	private String			id;
	
	/** 申请时间*/
	private Date 			createTime;
	private String			createTimeShow;
	
	/** 申请人Id*/
	private Long			creatorId;
	
	/** 申请人部门ID */
    private Long          	deptId;
    
    /** 用餐时间*/
    private Date 			mealDate;
    private String			mealDateShow;
    
    /** 状态 */
    private CarStatus       status;
    
    /** 用餐人员Id*/
    private Long			mealUserId;
    
    /** 用餐人员姓名（通讯录）*/
    private String			mealUserName;
    
    /** 用餐人员部门Id*/
    private Long			mealDeptId;
    
    /** 用餐人员部门名称（通讯录）*/
    private String			mealDeptName;
    
    /** 用餐地点*/
    private MealPlace		mealPlace;
    
    /** 用餐类型*/
    private MealType		mealType;
    
    /** 审核人ID*/
    private Long			checkId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeShow() {
		if (this.createTime != null) {
			this.createTimeShow = Tools.DATE_MINUTE_FORMAT.format(createTime);
		}
		return createTimeShow;
	}

	public void setCreateTimeShow(String createTimeShow) {
		this.createTimeShow = createTimeShow;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Date getMealDate() {
		return mealDate;
	}

	public void setMealDate(Date mealDate) {
		this.mealDate = mealDate;
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

	public CarStatus getStatus() {
		return status;
	}

	public void setStatus(CarStatus status) {
		this.status = status;
	}

	public Long getMealUserId() {
		return mealUserId;
	}

	public void setMealUserId(Long mealUserId) {
		this.mealUserId = mealUserId;
	}

	public Long getMealDeptId() {
		return mealDeptId;
	}

	public void setMealDeptId(Long mealDeptId) {
		this.mealDeptId = mealDeptId;
	}

	public MealPlace getMealPlace() {
		return mealPlace;
	}

	public void setMealPlace(MealPlace mealPlace) {
		this.mealPlace = mealPlace;
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	public Long getCheckId() {
		return checkId;
	}

	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}

	public String getMealUserName() {
		return mealUserName;
	}

	public void setMealUserName(String mealUserName) {
		this.mealUserName = mealUserName;
	}

	public String getMealDeptName() {
		return mealDeptName;
	}

	public void setMealDeptName(String mealDeptName) {
		this.mealDeptName = mealDeptName;
	}
    
}
