/*
 * FileName:    PowerInfo.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年4月6日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.syspower.bean;

import cn.com.chaochuang.common.syspower.reference.IsMenu;
import cn.com.chaochuang.common.syspower.reference.PowerType;

/**
 * @author LJX
 *
 */
public class PowerInfo {

    private Long      id;

    /** 权限显示顺序号 */
    private String    powerCode;

    /** 系统菜单名称 */
    private String    powerName;

    /** 模块名称 */
    private String    module;

    /** 系统菜单url */
    private String    url;

    /** App菜单url */
    private String    appurl;

    private PowerType powerTypeFlag;

    /** 是否是菜单 */
    private IsMenu    isMenu;
    /** 父级菜单 */
    private Long      parentPowerId;
    /** 排序 */
    private Integer   sort;

    /** 应用ID */
    private Long      appId;

    /** 图标相对路径 */
    private String    icon;
    /** app图标 */
    private String    appicon;

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
     * @return the powerCode
     */
    public String getPowerCode() {
        return powerCode;
    }

    /**
     * @param powerCode
     *            the powerCode to set
     */
    public void setPowerCode(String powerCode) {
        this.powerCode = powerCode;
    }

    /**
     * @return the powerName
     */
    public String getPowerName() {
        return powerName;
    }

    /**
     * @param powerName
     *            the powerName to set
     */
    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the powerTypeFlag
     */
    public PowerType getPowerTypeFlag() {
        return powerTypeFlag;
    }

    /**
     * @param powerTypeFlag
     *            the powerTypeFlag to set
     */
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
     * @return the appId
     */
    public Long getAppId() {
        return appId;
    }

    /**
     * @param appId
     *            the appId to set
     */
    public void setAppId(Long appId) {
        this.appId = appId;
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

	public String getAppurl() {
		return appurl;
	}

	public void setAppurl(String appurl) {
		this.appurl = appurl;
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


}
