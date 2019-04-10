package cn.com.chaochuang.pubaffairs.meal.bean;

import java.util.Date;

import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.meal.reference.MealPlace;
import cn.com.chaochuang.pubaffairs.meal.reference.MealType;

/**
 * @author dengl 2018.08.13
 *
 */
public class OaMealEditBean {
	
	private String			id;
	
	/** 申请时间*/
	private Date 			createTime;
	
	/** 申请人Id*/
	private Long			creatorId;
	
	/** 申请人部门ID */
    private Long          	deptId;
    
    /** 用餐时间*/
    private Date 			mealDate;
    
    /** 状态 */
    private CarStatus       status;
    
    /** 用餐人员Id*/
    private Long[]			mealUserId;
    
    /** 用餐人员姓名*/
    private String[]		mealUserName;
    
    /** 用餐人员部门Id*/
    private Long[]			mealDeptId;
    
    /** 用餐人员部门名称*/
    private String[]		mealDeptName;
    
    /** 用餐地点*/
    private MealPlace		mealPlace;
    
    /** 用餐类型*/
    private MealType[]		mealType;
    
    /** 审核人ID*/
    private Long			checkId;
    
    /** 用餐人员姓名（通讯录）*/
    private String[]		mailUserName;
    
    /** 用餐人员部门名称（通讯录）*/
    private String[]		mailDeptName;
    
    /** 用餐类型（通讯录）*/
    private MealType[]		mealType1;

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

	public CarStatus getStatus() {
		return status;
	}

	public void setStatus(CarStatus status) {
		this.status = status;
	}

	public MealPlace getMealPlace() {
		return mealPlace;
	}

	public void setMealPlace(MealPlace mealPlace) {
		this.mealPlace = mealPlace;
	}


	public Long getCheckId() {
		return checkId;
	}

	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}

	public Long[] getMealUserId() {
		return mealUserId;
	}

	public void setMealUserId(Long[] mealUserId) {
		this.mealUserId = mealUserId;
	}

	public String[] getMealUserName() {
		return mealUserName;
	}

	public void setMealUserName(String[] mealUserName) {
		this.mealUserName = mealUserName;
	}

	public Long[] getMealDeptId() {
		return mealDeptId;
	}

	public void setMealDeptId(Long[] mealDeptId) {
		this.mealDeptId = mealDeptId;
	}

	public String[] getMealDeptName() {
		return mealDeptName;
	}

	public void setMealDeptName(String[] mealDeptName) {
		this.mealDeptName = mealDeptName;
	}

	public MealType[] getMealType() {
		return mealType;
	}

	public void setMealType(MealType[] mealType) {
		this.mealType = mealType;
	}

	public String[] getMailUserName() {
		return mailUserName;
	}

	public void setMailUserName(String[] mailUserName) {
		this.mailUserName = mailUserName;
	}

	public String[] getMailDeptName() {
		return mailDeptName;
	}

	public void setMailDeptName(String[] mailDeptName) {
		this.mailDeptName = mailDeptName;
	}

	public MealType[] getMealType1() {
		return mealType1;
	}

	public void setMealType1(MealType[] mealType1) {
		this.mealType1 = mealType1;
	}
	
}
