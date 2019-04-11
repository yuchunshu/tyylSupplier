/*
 * FileName:    RoleShowBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月6日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.syspower.bean;

import java.util.Set;

import cn.com.chaochuang.common.syspower.domain.SysPower;

/**
 * @author LJX
 *
 */
public class RoleShowBean {

    private Long    id;

    /** 角色名称 */
    private String  roleName;

    /** 简要说明 */
    private String  remark;

    /** 显示顺序号 */
    private String  orderNum;

    private Integer userCount;

    private Integer powerCount;

    public void setPowers(Set<SysPower> powers) {
        if (powers != null) {
            this.powerCount = powers.size();
        } else {
            this.powerCount = 0;
        }
    }

    /** 用户数多（超过500）的项目请不要加这段代码，会很慢*/
    /**
    public Set<SysUser> getUsers() {
        return null;
    }
    public void setUsers(Set<SysUser> users) {
        if (users != null) {
            this.userCount = users.size();
        } else {
            this.userCount = 0;
        }
    }
    */

    public Set<SysPower> getPowers() {
        return null;
    }

    /**
     * @return the userCount
     */
    public Integer getUserCount() {
        return userCount;
    }

    /**
     * @return the powerCount
     */
    public Integer getPowerCount() {
        return powerCount;
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
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName
     *            the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     *            the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the orderNum
     */
    public String getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum
     *            the orderNum to set
     */
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

}
