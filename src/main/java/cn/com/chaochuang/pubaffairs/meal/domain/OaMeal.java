package cn.com.chaochuang.pubaffairs.meal.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatusConverter;
import cn.com.chaochuang.pubaffairs.meal.reference.MealPlace;
import cn.com.chaochuang.pubaffairs.meal.reference.MealPlaceConverter;
import cn.com.chaochuang.pubaffairs.meal.reference.MealType;
import cn.com.chaochuang.pubaffairs.meal.reference.MealTypeConverter;

/**
 * 订餐申请
 * @author dengl 2018.08.13
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "meal_id")) })
public class OaMeal  extends StringUuidEntity {

	private static final long serialVersionUID = -6186347191047261218L;
	
	/** 申请时间*/
	private Date 			createTime;
	
	/** 申请人Id*/
	private Long			creatorId;
	
	@ManyToOne
    @JoinColumn(name = "creatorId" , insertable = false, updatable = false)
    private SysUser         creator;
	
	/** 申请人部门ID */
    private Long          	deptId;
    @ManyToOne
    @JoinColumn(name = "deptId", updatable = false, insertable = false)
    // 找不到引用的外键数据时忽略
    @NotFound(action = NotFoundAction.IGNORE)
    private SysDepartment 	dept;
    
    /** 用餐时间*/
    private Date 			mealDate;
    
    /** 状态 */
    @Convert(converter = CarStatusConverter.class)
    private CarStatus       status;
    
    /** 用餐人员Id*/
    private Long			mealUserId;
    
    /** 用餐人员姓名（通讯录）*/
    private String			mealUserName;
    
    @ManyToOne
    @JoinColumn(name = "mealUserId" , insertable = false, updatable = false)
    private SysUser         mealUser;
    
    /** 用餐人员部门Id*/
    private Long			mealDeptId;
    
    /** 用餐人员部门名称（通讯录）*/
    private String			mealDeptName;
    
    @ManyToOne
    @JoinColumn(name = "mealDeptId", updatable = false, insertable = false)
    // 找不到引用的外键数据时忽略
    @NotFound(action = NotFoundAction.IGNORE)
    private SysDepartment 	mealDept;
    
    /** 用餐地点*/
    @Convert(converter = MealPlaceConverter.class)
    private MealPlace		mealPlace;
    
    /** 用餐类型*/
    @Convert(converter = MealTypeConverter.class)
    private MealType		mealType;
    
    /** 审核人ID*/
    private Long			checkId;
    
    @ManyToOne
    @JoinColumn(name = "checkId" , insertable = false, updatable = false)
    private SysUser         checkor;

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
