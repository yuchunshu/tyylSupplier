/*
 * FileName:    DepartmentSelectShowBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年3月14日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.user.bean;

import org.dozer.Mapping;

/**
 * @author HM
 *
 */
public class DepartmentSelectShowBean {

    /** 流水号 */
    private Long    id;

    /** 部门名称 */
    private String  deptName;

    /** 父部门ID */
    @Mapping("parentDepartment.id")
    private Long    parentDepId;

    /** 父部门名称 */
    @Mapping("parentDepartment.deptName")
    private String  parentDepName;

    private boolean checked;

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

    /**
     * @return the parentDepId
     */
    public Long getParentDepId() {
        return parentDepId;
    }

    /**
     * @param parentDepId
     *            the parentDepId to set
     */
    public void setParentDepId(Long parentDepId) {
        this.parentDepId = parentDepId;
    }

    /**
     * @return the parentDepName
     */
    public String getParentDepName() {
        return parentDepName;
    }

    /**
     * @param parentDepName
     *            the parentDepName to set
     */
    public void setParentDepName(String parentDepName) {
        this.parentDepName = parentDepName;
    }

    /**
     * @return the checked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * @param checked
     *            the checked to set
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
