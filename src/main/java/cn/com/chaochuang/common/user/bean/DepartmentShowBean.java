package cn.com.chaochuang.common.user.bean;

import org.dozer.Mapping;

public class DepartmentShowBean {

    /** id */
    private Long   	id;

    /** 部门名称 */
    private String 	deptName;

    /** 父部门 */
    @Mapping("parentDepartment.deptName")
    private String 	parentDeptName;

    /** 祖先部门 */
    @Mapping("unitDept.deptName")
    private String 	unitDeptName;

    /** 部门代码 */
    private String 	deptCode;

    /** 排序号 */
    private Long   	orderNum;

    /** 是否有子部门标志 */
    private boolean hasChild;

    /**
     * @return the parentDeptName
     */
    public String getParentDeptName() {
        return parentDeptName;
    }

    /**
     * @param parentDeptName
     *            the parentDeptName to set
     */
    public void setParentDeptName(String parentDeptName) {
        this.parentDeptName = parentDeptName;
    }

    /**
     * @return the unitDeptName
     */
    public String getUnitDeptName() {
        return unitDeptName;
    }

    /**
     * @param unitDeptName
     *            the unitDeptName to set
     */
    public void setUnitDeptName(String unitDeptName) {
        this.unitDeptName = unitDeptName;
    }

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

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}


    // /**
    // * @return the parentDeptName
    // */
    // public String getParentDeptName() {
    // return parentDeptName;
    // }
    //
    // /**
    // * @param parentDeptName
    // * the parentDeptName to set
    // */
    // public void setParentDeptName(String parentDeptName) {
    // this.parentDeptName = parentDeptName;
    // }

}