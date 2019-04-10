/*
 * FileName:    SysDepartmentUIShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年1月25日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.user.bean;

import cn.com.chaochuang.common.user.domain.SysDepartment;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author LaoZhiYong
 *
 */
public class SysDepartmentUIShowBean {
    /** 部门名称 */
    private Long          id;

    private String        depName;

    private String        parentDepName;

    @JsonIgnore
    private SysDepartment parentDepartment;

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
     * @return the depName
     */
    public String getDepName() {
        return depName;
    }

    /**
     * @param depName
     *            the depName to set
     */
    public void setDepName(String depName) {
        this.depName = depName;
    }

    /**
     * @return the parentDepName
     */
    public String getParentDepName() {
        return parentDepName;
    }

    /**
     * @return the parentDepartment
     */
    public SysDepartment getParentDepartment() {
        return parentDepartment;
    }

    /**
     * @param parentDepartment
     *            the parentDepartment to set
     */
    public void setParentDepartment(SysDepartment parentDepartment) {
        this.parentDepartment = parentDepartment;
        this.parentDepName = parentDepartment.getDeptName();
    }

}
