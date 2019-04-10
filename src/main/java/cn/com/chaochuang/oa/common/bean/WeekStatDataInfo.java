/**
 * 
 */
package cn.com.chaochuang.oa.common.bean;

import java.util.List;

/**
 * @author hzr 2016年5月28日
 *
 */
public class WeekStatDataInfo {

    /**一行数据中，第一列表示数据编码或属性*/
    private String  code;
    
    /**在周日格子上显示的数据项*/
    private List    items0;
    
    /**在周一格子上显示的数据项*/
    private List    items1;
    
    /**在周二格子上显示的数据项*/
    private List    items2;
    
    /**在周三格子上显示的数据项*/
    private List    items3;
    
    /**在周四格子上显示的数据项*/
    private List    items4;
    
    /**在周五格子上显示的数据项*/
    private List    items5;
    
    /**在周六格子上显示的数据项*/
    private List    items6;

    
    public WeekStatDataInfo(String code) {
        super();
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List getItems0() {
        return items0;
    }

    public void setItems0(List items0) {
        this.items0 = items0;
    }

    public List getItems1() {
        return items1;
    }

    public void setItems1(List items1) {
        this.items1 = items1;
    }

    public List getItems2() {
        return items2;
    }

    public void setItems2(List items2) {
        this.items2 = items2;
    }

    public List getItems3() {
        return items3;
    }

    public void setItems3(List items3) {
        this.items3 = items3;
    }

    public List getItems4() {
        return items4;
    }

    public void setItems4(List items4) {
        this.items4 = items4;
    }

    public List getItems5() {
        return items5;
    }

    public void setItems5(List items5) {
        this.items5 = items5;
    }

    public List getItems6() {
        return items6;
    }

    public void setItems6(List items6) {
        this.items6 = items6;
    }
    
    
    
}
