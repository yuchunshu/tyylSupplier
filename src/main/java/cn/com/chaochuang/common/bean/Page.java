/*
 * FileName:    Page.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年1月7日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.bean;

import java.util.List;

/**
 * @author LaoZhiYong
 *
 */
public class Page {
    private Long total;
    private List rows;

    /**
     * @return the total
     */
    public Long getTotal() {
        return total;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal(Long total) {
        this.total = total;
    }

    /**
     * @return the rows
     */
    public List getRows() {
        return rows;
    }

    /**
     * @param rows
     *            the rows to set
     */
    public void setRows(List rows) {
        this.rows = rows;
    }

}
