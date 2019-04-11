/*
 * FileName:    CommonTreeBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年8月3日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.bean;

import java.util.List;

/**
 * 公共TreeBean
 *
 * @author HM
 */
public class CommonTreeBean {

    // easyuitree 基本属性 start
    private String               id;

    private String               text;

    private String               state;

    private String               iconCls;

    private List<CommonTreeBean> children;
    // easyuitree 基本属性 end

    /** 类型 */
    private String               type;


    /**
     *
     */
    public CommonTreeBean() {
        super();
    }

    /**
     * @param id
     * @param text
     * @param state
     * @param iconCls
     * @param children
     */
    public CommonTreeBean(String id, String text, String state, String iconCls, List<CommonTreeBean> children) {
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
    public List<CommonTreeBean> getChildren() {
        return children;
    }

    /**
     * @param children
     *            the children to set
     */
    public void setChildren(List<CommonTreeBean> children) {
        this.children = children;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

}
