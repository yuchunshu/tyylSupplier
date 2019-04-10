package cn.com.chaochuang.pubaffairs.car.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.pubaffairs.car.reference.BusinessType;
import cn.com.chaochuang.pubaffairs.car.reference.BusinessTypeConverter;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatusConverter;

/**
 * @author dengl 2018.08.08
 *
 */

@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "car_id")) })
@Table(name = "oa_official_car")
public class OfficialCar extends StringUuidEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6041406025257664650L;
	
	/** 创建时间*/
	private Date 				createDate;
	
	/** 创建人ID */
    private Long            	creatorId;
    @ManyToOne
    @JoinColumn(name = "creatorId" , insertable = false, updatable = false)
    private SysUser            	creator; 
    
    /** 创建人姓名*/
    private String				creatorName;
    
    /** 所属部门ID */
    private Long            	deptId;
    @ManyToOne
    @JoinColumn(name = "deptId", updatable = false, insertable = false)
    // 忽略脏数据
    @NotFound(action = NotFoundAction.IGNORE)
    private SysDepartment 		dept;
    
    /** 所属部门名称*/
    private String				deptName;
    
    /** 事由*/
    private String				reason;
    
    /** 用车开始时间*/
    private Date				beginTime;
    
    /** 用车结束时间*/
    private Date				endTime;
    
    /** 车牌号码*/
    private String				carNumber;
    
    /** 驾驶员*/
    private	String				driver;
    
    /** 状态*/
    @Convert(converter = CarStatusConverter.class)
    private CarStatus			status;
    
    /** 版本号*/
    private Integer				version_;
    
    /** 流程定义ID*/
    private String				flowId;
    
    /** 流程实例ID*/
    private String				flowInstId;
    
    /** 使用地点(目的地)*/
    private String				rentalPlace;
    
    /** 出发地*/
    private String				departurePlace;
    
    /** 业务类型*/
    @Convert(converter = BusinessTypeConverter.class)
    private BusinessType		businessType;
    
    /** 星期*/
    private String				week;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public SysUser getCreator() {
		return creator;
	}

	public void setCreator(SysUser creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public SysDepartment getDept() {
		return dept;
	}

	public void setDept(SysDepartment dept) {
		this.dept = dept;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public CarStatus getStatus() {
		return status;
	}

	public void setStatus(CarStatus status) {
		this.status = status;
	}

	public Integer getVersion_() {
		return version_;
	}

	public void setVersion_(Integer version_) {
		this.version_ = version_;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFlowInstId() {
		return flowInstId;
	}

	public void setFlowInstId(String flowInstId) {
		this.flowInstId = flowInstId;
	}

	public String getRentalPlace() {
		return rentalPlace;
	}

	public void setRentalPlace(String rentalPlace) {
		this.rentalPlace = rentalPlace;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public String getDeparturePlace() {
		return departurePlace;
	}

	public void setDeparturePlace(String departurePlace) {
		this.departurePlace = departurePlace;
	}
}
