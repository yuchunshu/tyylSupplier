package cn.com.chaochuang.common.syspower.domain;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.lookup.annotation.LookUp;
import cn.com.chaochuang.common.syspower.reference.IsMenu;
import cn.com.chaochuang.common.syspower.reference.IsMenuConverter;
import cn.com.chaochuang.common.syspower.reference.PowerType;
import cn.com.chaochuang.common.syspower.reference.PowerTypeConverter;

@Entity
@LookUp
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "power_id")) })
public class SysPower extends LongIdEntity {

    private static final long serialVersionUID = -3059154610506597099L;

    /** 权限显示顺序号 */
    private String            powerCode;

    /** 系统菜单名称 */
    private String            powerName;

    /** 模块名称 */
    private String            module;
    
    /** 属性(新增、删除、修改...) */
    private String            operate;

    /** 系统菜单url */
    private String            url;
    /** 图标 */
    private String            icon;

    /** App菜单url */
    private String            appurl;
    /** app图标 */
    private String            appicon;

    /** 菜单类型 */
    @Convert(converter = PowerTypeConverter.class)
    private PowerType         powerTypeFlag    = PowerType.自动增加;

    /** 是否是菜单 */
    @Convert(converter = IsMenuConverter.class)
    private IsMenu            isMenu;
    /** 父级菜单 */
    private Long              parentPowerId;
    /** 排序 */
    private Integer           sort;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_power", joinColumns = { @JoinColumn(name = "power_id", referencedColumnName = "power_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "role_id") })
    private Set<SysRole>      roles;

    public String getPowerCode() {
        return powerCode;
    }

    public void setPowerCode(String powerCode) {
        this.powerCode = powerCode;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppurl() {
		return appurl;
	}

	public void setAppurl(String appurl) {
		this.appurl = appurl;
	}

	public PowerType getPowerTypeFlag() {
        return powerTypeFlag;
    }

    public void setPowerTypeFlag(PowerType powerTypeFlag) {
        this.powerTypeFlag = powerTypeFlag;
    }

    /**
     * @return the isMenu
     */
    public IsMenu getIsMenu() {
        return isMenu;
    }

    /**
     * @param isMenu
     *            the isMenu to set
     */
    public void setIsMenu(IsMenu isMenu) {
        this.isMenu = isMenu;
    }

    /**
     * @return the parentPowerId
     */
    public Long getParentPowerId() {
        return parentPowerId;
    }

    /**
     * @param parentPowerId
     *            the parentPowerId to set
     */
    public void setParentPowerId(Long parentPowerId) {
        this.parentPowerId = parentPowerId;
    }

    /**
     * @return the sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort
     *            the sort to set
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     *            the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getAppicon() {
		return appicon;
	}

	public void setAppicon(String appicon) {
		this.appicon = appicon;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}


    // public Set<SysRole> getRoles() {
    // return roles;
    // }
    //
    // public void setRoles(Set<SysRole> roles) {
    // this.roles = roles;
    // }

}
