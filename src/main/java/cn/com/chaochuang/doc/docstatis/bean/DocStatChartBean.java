/*
 * FileName:    DocStatChartBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年10月13日 (HeYunTao) 1.0 Create
 */

package cn.com.chaochuang.doc.docstatis.bean;

import java.util.List;

/**
 * highcharts使用的bean
 *
 * @author HeYunTao
 *
 */
public class DocStatChartBean {

    private String       name;

    private List<Object> data;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the data
     */
    public List<Object> getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(List<Object> data) {
        this.data = data;
    }

}
