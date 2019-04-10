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
public class DeptNodesInfo {

    /** 部门名 */
    private String                deptName;
    /** 部门下的人员集合 */
    private List<NameAndIdEntity> usersList;

    /**
     * @return the deptName
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @param deptName
     *            the deptName to set
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * @return the usersList
     */
    public List<NameAndIdEntity> getUsersList() {
        return usersList;
    }

    /**
     * @param usersList
     *            the usersList to set
     */
    public void setUsersList(List<NameAndIdEntity> usersList) {
        this.usersList = usersList;
    }

}
