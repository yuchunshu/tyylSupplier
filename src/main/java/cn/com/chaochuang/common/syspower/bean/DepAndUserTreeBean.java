/*
 * FileName:    DepAndUserTreeBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月7日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.syspower.bean;

import java.util.List;

/**
 * @author LJX
 *
 */
public class DepAndUserTreeBean {

    private String                   id;

    private String                   text;

    private boolean                  checked;

    private String                   state;

    private String                   iconCls;

    private boolean                  userFlag = false;

    private List<DepAndUserTreeBean> children;

    public DepAndUserTreeBean() {
        userFlag = false;
    }

    /**
     * @param id
     * @param text
     * @param checked
     * @param state
     */
    public DepAndUserTreeBean(String id, String text, boolean checked, String state, String iconCls) {
        super();
        this.id = id;
        this.text = text;
        this.checked = checked;
        this.state = state;
        this.iconCls = iconCls;
        userFlag = false;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
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
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the children
     */
    public List<DepAndUserTreeBean> getChildren() {
        return children;
    }

    /**
     * @param children
     *            the children to set
     */
    public void setChildren(List<DepAndUserTreeBean> children) {
        this.children = children;
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
     * @return the userFlag
     */
    public boolean isUserFlag() {
        return userFlag;
    }

    /**
     * @param userFlag
     *            the userFlag to set
     */
    public void setUserFlag(boolean userFlag) {
        this.userFlag = userFlag;
    }

}
