/*
 * FileName:    DeptInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年4月13日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

import java.util.List;

/**
 * @author huangwq
 *
 */
public class SelectNodesAndBtnInfo {

    // 按钮列表
    private String[]              buttons;

    private List<SelectNodesInfo> SelectNodesList;

    /**
     * @return the selectNodesList
     */
    public List<SelectNodesInfo> getSelectNodesList() {
        return SelectNodesList;
    }

    /**
     * @param selectNodesList
     *            the selectNodesList to set
     */
    public void setSelectNodesList(List<SelectNodesInfo> selectNodesList) {
        SelectNodesList = selectNodesList;
    }

    /**
     * @return the buttons
     */
    public String[] getButtons() {
        return buttons;
    }

    /**
     * @param buttons
     *            the buttons to set
     */
    public void setButtons(String[] buttons) {
        this.buttons = buttons;
    }

}
