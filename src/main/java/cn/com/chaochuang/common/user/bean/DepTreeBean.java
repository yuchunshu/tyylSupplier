/*
 * FileName:    depTreeBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2015
 * History:     2015年7月5日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.user.bean;

/**
 * @author LJX
 *
 */
public class DepTreeBean {
    private String  text;
    private Long    id;
    private boolean leaf;

    /**
     * 构造函数
     */
    public DepTreeBean() {
        super();
    }

    /**
     * 构造函数
     *
     * @param id
     * @param text
     */
    public DepTreeBean(Long id, String text, boolean leaf) {
        super();
        this.text = text;
        this.id = id;
        this.leaf = leaf;
    }

    /**
     * @return the leaf
     */
    public boolean isLeaf() {
        return leaf;
    }

    /**
     * @param leaf
     *            the leaf to set
     */
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    /**
     * @return the state
     */
    public String getState() {
        if (leaf) {
            return "open";
        }
        return "closed";
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

}
