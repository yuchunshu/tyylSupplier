/*
 * FileName:    DepartmentInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年4月14日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

/**
 * @author huangwq
 *
 */
public class SelectDeptInfo {

    /** 部门名称 */
    private String depName;
    /** 部门id */
    private Long   depId;

    /**
     * @return the depId
     */
    public Long getDepId() {
        return depId;
    }

    /**
     * @param depId
     *            the depId to set
     */
    public void setDepId(Long depId) {
        this.depId = depId;
    }

    /**
     * @return the depName
     */
    public String getDepName() {
        return depName;
    }

    /**
     * @param depName
     *            the depName to set
     */
    public void setDepName(String depName) {
        this.depName = depName;
    }

}
