/*
 * FileName:    RoleInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年4月7日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.syspower.bean;

/**
 * @author LJX
 *
 */
public class RoleInfo {

    private Long    id;

    /** 角色名称 */
    private String  roleName;

    /** 简要说明 */
    private String  remark;

    /** 显示顺序号 */
    private Integer orderNum;

    private Long[]  powerIds;

    private Long[]  userIds;

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
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum
     *            the orderNum to set
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * @return the powerIds
     */
    public Long[] getPowerIds() {
        return powerIds;
    }

    /**
     * @param powerIds
     *            the powerIds to set
     */
    public void setPowerIds(Long[] powerIds) {
        this.powerIds = powerIds;
    }

    /**
     * @return the userIds
     */
    public Long[] getUserIds() {
        return userIds;
    }

    /**
     * @param userIds
     *            the userIds to set
     */
    public void setUserIds(Long[] userIds) {
        this.userIds = userIds;
    }

}
