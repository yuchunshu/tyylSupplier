/*
 * FileName:    PowerTreeGridBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月24日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.syspower.bean;

import java.util.List;

import cn.com.chaochuang.common.syspower.reference.IsMenu;
import cn.com.chaochuang.common.syspower.reference.PowerType;

/**
 * @author HM
 *
 */
public class PowerTreeGridBean {

    /** id */
    private Long                    id;

    /** 权限显示顺序号 */
    private String                  powerCode;

    /** 系统菜单名称 */
    private String                  powerName;

    /** 模块名称 */
    private String                  module;

    /** 系统菜单url */
    private String                  url;

    /** App菜单url */
    private String                  appurl;
    /** app图标 */
    private String                  appicon;

    /** 菜单类型 */
    private PowerType               powerTypeFlag;

    /** 是否是菜单 */
    private IsMenu                  isMenu;

    private boolean                 checked;

    /** 排序 */
    private Integer                 sort;

    /** 图标相对路径 */
    private String                  icon;

    private List<PowerTreeGridBean> children;

    public PowerTreeGridBean() {

    }

    /**
     * @param id
     * @param powerCode
     * @param powerName
     * @param url
     * @param powerTypeFlag
     * @param isMenu
     * @param sort
     * @param children
     */
    public PowerTreeGridBean(Long id, String powerCode, String powerName, String url, PowerType powerTypeFlag,
                    IsMenu isMenu, Integer sort, List<PowerTreeGridBean> children) {
        super();
        this.id = id;
        this.powerCode = powerCode;
        this.powerName = powerName;
        this.url = url;
        this.powerTypeFlag = powerTypeFlag;
        this.isMenu = isMenu;
        this.sort = sort;
        this.children = children;
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
     * @return the children
     */
    public List<PowerTreeGridBean> getChildren() {
        return children;
    }

    /**
     * @param children
     *            the children to set
     */
    public void setChildren(List<PowerTreeGridBean> children) {
        this.children = children;
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
