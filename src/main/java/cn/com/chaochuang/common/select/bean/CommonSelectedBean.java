/*
 * FileName:    CommonSelectedBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年8月4日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.select.bean;

/**
 * 选择dialog 已选数据
 *
 * @author HM
 *
 */
public class CommonSelectedBean {

    /** id */
    private String id;

    /** 显示名称 */
    private String dataText;

    public CommonSelectedBean() {
    };

    public CommonSelectedBean(String id, String dataText) {
        this.id = id;
        this.dataText = dataText;
    };

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
     * @return the dataText
     */
    public String getDataText() {
        return dataText;
    }

    /**
     * @param dataText
     *            the dataText to set
     */
    public void setDataText(String dataText) {
        this.dataText = dataText;
    }

}
