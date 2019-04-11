/*
 * FileName:    SysDepartmentSelectShowBeang.java
 * Description:
 * Company:     
 * Copyright:    (c) 2015
 * History:     2015年1月22日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.select.bean;

/**
 * @author LaoZhiYong
 *
 */
public class SysDepartmentSelectShowBean {
    private Long   id;
    /** 部门名称 */
    private String deptName;
    /** 部门编码 */
    private String deptCode;

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

}
