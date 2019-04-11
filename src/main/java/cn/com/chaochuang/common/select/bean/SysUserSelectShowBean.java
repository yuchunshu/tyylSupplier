/*
 * FileName:    SysUserSelectShowBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2015
 * History:     2015年1月14日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.select.bean;

import org.dozer.Mapping;

/**
 * @author LaoZhiYong
 *
 */
public class SysUserSelectShowBean {
    /** 所属部门名称 */
    @Mapping("department.deptName")
    private String departmentName;

    /** 姓名 */
    private String userName;

    /** id */
    private Long   id;

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
     * @return the departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @param departmentName
     *            the departmentName to set
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
