/*
 * FileName:    DepartmentTreeShowBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2015
 * History:     2015年1月9日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.select.bean;

import org.dozer.Mapping;

/**
 * @author LaoZhiYong
 *
 */
public class DepartmentTreeShowBean {
    @Mapping("depName")
    private String  text;
    private Long    id;
    private boolean leaf;

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
