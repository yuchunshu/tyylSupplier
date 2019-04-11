package cn.com.chaochuang.common.syspower.domain;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.user.domain.SysUser;

@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "role_id")) })
public class SysRole extends LongIdEntity {

    private static final long serialVersionUID = 41678910598605943L;

    /** 角色名称 */
    private String              roleName;

    /** 简要说明 */
    private String              remark;

    /** 显示顺序号 */
    private Integer             orderNum;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_power", joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "power_id", referencedColumnName = "power_id") })
    private Set<SysPower>       powers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_role_rel", joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "user_id") })
    private Set<SysUser>        users;
    
    public Set<SysUser> getUsers() {
        return users;
    }

    public void setUsers(Set<SysUser> users) {
        this.users = users;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public Set<SysPower> getPowers() {
        return powers;
    }

    public void setPowers(Set<SysPower> powers) {
        this.powers = powers;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
