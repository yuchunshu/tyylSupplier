package cn.com.chaochuang.common.user.bean;


import cn.com.chaochuang.common.reference.AdministrativeLevel;

public class DepartmentEditBean {

    /** id */
    private Long   				id;

    /** 部门名称 */
    private String 				deptName;

    /** 部门编码 */
    private String 				deptCode;

    /** 父部门编号 */
    private Long   				deptParent;

    /** 祖先部门编号 */
    private Long   				unitId;

    /** 排序号 */
    private Long   			  	orderNum;

    /** 部门级别(1/2/3级，一般代表省/市/县) */
    private AdministrativeLevel deptLevel;
    
    /**
     * @return the deptCode
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * @param deptCode
     *            the deptCode to set
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    /**
     * @return the deptParent
     */
    public Long getDeptParent() {
        return deptParent;
    }

    /**
     * @param deptParent
     *            the deptParent to set
     */
    public void setDeptParent(Long deptParent) {
        this.deptParent = deptParent;
    }

    /**
     * @return the unitId
     */
    public Long getUnitId() {
        return unitId;
    }

    /**
     * @param unitId
     *            the unitId to set
     */
    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    /**
     * @return the orderNum
     */
    public Long getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum
     *            the orderNum to set
     */
    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the deptName
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @param deptName
     *            the deptName to set
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

	public AdministrativeLevel getDeptLevel() {
		return deptLevel;
	}

	public void setDeptLevel(AdministrativeLevel deptLevel) {
		this.deptLevel = deptLevel;
	}

}
