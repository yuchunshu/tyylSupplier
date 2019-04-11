/*
 * FileName:    UserSelectedListBean.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年3月14日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.select.bean;

import org.dozer.Mapping;

/**
 * @author HM
 *
 */
public class UserSelectedListBean {

    /** Id */
    private Long   id;

    /** 姓名 */
    @Mapping("userName")
    private String dataText;

    /** 职务 */
    private Long   dutyId;

    /** 所属部门ID */
    @Mapping("department.id")
    private Long   depId;

    /** 所属部门名称 */
    @Mapping("department.deptName")
    private String depName;

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

    /**
     * @return the dutyId
     */
    public Long getDutyId() {
        return dutyId;
    }

    /**
     * @param dutyId
     *            the dutyId to set
     */
    public void setDutyId(Long dutyId) {
        this.dutyId = dutyId;
    }

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
