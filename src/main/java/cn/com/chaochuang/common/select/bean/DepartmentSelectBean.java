/*
 * FileName:    DepartmentTreeBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月1日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.select.bean;

import java.util.List;

/**
 * @author HM
 *
 */
public class DepartmentSelectBean {
    private Long                     id;

    private String                   text;

    private String                   state;

    private String                   iconCls;

    private List<DepartmentSelectBean> children;

    /**
     *
     */
    public DepartmentSelectBean() {
        super();
    }

    public DepartmentSelectBean(Long id, String text) {
        super();
        this.id = id;
        this.text = text;
    }
    /**
     * @param id
     * @param text
     * @param state
     * @param iconCls
     * @param children
     */
    public DepartmentSelectBean(Long id, String text, String state, String iconCls, List<DepartmentSelectBean> children) {
        super();
        this.id = id;
        this.text = text;
        this.state = state;
        this.iconCls = iconCls;
        this.children = children;
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
    public List<DepartmentSelectBean> getChildren() {
        return children;
    }

    /**
     * @param children
     *            the children to set
     */
    public void setChildren(List<DepartmentSelectBean> children) {
        this.children = children;
    }

}
