package cn.com.chaochuang.pubaffairs.meal.bean;

import java.math.BigInteger;

public class OaMealAllPersonBean {
	
	/** 姓名*/
	private String   		userName;
	
	/** 部门Id*/
	private BigInteger	 	deptId;
	
	/** 部门名称*/
	private String			deptName;
	
	/** 用餐地点*/
    private Character		mealPlace;
    
    /** 备注*/
    private String			remark;
    
    /** 订餐状态*/
    private String			place;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigInteger getDeptId() {
		return deptId;
	}

	public void setDeptId(BigInteger deptId) {
		this.deptId = deptId;
	}

	public Character getMealPlace() {
		return mealPlace;
	}

	public void setMealPlace(Character mealPlace) {
		this.mealPlace = mealPlace;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
}
