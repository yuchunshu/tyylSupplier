/*
 * FileName:    RoleCheckedBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年4月9日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.syspower.bean;

/**
 * @author HM
 *
 */
public class RoleCheckedBean {

    /** 流水号 */
    private Long    id;

    /** 角色名称 */
    private String  roleName;

    /** 是否选中 */
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
