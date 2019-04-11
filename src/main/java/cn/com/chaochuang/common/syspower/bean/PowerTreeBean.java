/*
 * FileName:    PowerTreeBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月24日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.syspower.bean;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author LJX
 *
 */
public class PowerTreeBean {

    private Long                id;

    private String              text;

    private String              iconCls;

    private String              url;

    private String              appurl;

    private boolean             checked;

    private String              icon;

    private List<PowerTreeBean> children;

    public PowerTreeBean() {
    }

    public PowerTreeBean(Long id, String text, String url, String icon) {
        super();
        this.id = id;
        this.text = text;
        this.url = url;
        this.icon = icon;
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
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the state
     */
    public String getState() {
        if (this.children != null && this.children.size() > 0) {
            return "closed";
        }
        return null;
    }

    /**
     * @return the iconCls
     */
    public String getIconCls() {
        return iconCls;
    }

    /**
     * @param iconCls
     *            the iconCls to set
     */
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    /**
     * @return the children
     */
    public List<PowerTreeBean> getChildren() {
        return children;
    }

    /**
     * @param children
     *            the children to set
     */
    public void setChildren(List<PowerTreeBean> children) {
        this.children = children;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        if (StringUtils.isNotBlank(url) && url.startsWith("/")) {
            return url.substring(1);
        }
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

}
