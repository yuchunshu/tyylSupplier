/*
 * FileName:    DepartmentBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年8月5日 (覃咏晖@USER-20150531ET) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

/**
 * @author 覃咏晖@USER-20150531ET
 *
 */
public class DepartmentBean {
    /** 部门名称 */
    private String  depName;

    /** 父部门编号 */
    private Long    parentDep;

    /** 部门别名 */
    private String  depAlias;

    /** 祖先部门编号 */
    private Long    ancestorDep;

    /** 排序号 */
    private Long    orderNum;

    /** 原系统的有效性标志 */
    private String  delFlag;

    private Integer valid;

    /** 原系统部门编号 */
    private Long    rmDepId;

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

    /**
     * @return the parentDep
     */
    public Long getParentDep() {
        return parentDep;
    }

    /**
     * @param parentDep
     *            the parentDep to set
     */
    public void setParentDep(Long parentDep) {
        this.parentDep = parentDep;
    }

    /**
     * @return the depAlias
     */
    public String getDepAlias() {
        return depAlias;
    }

    /**
     * @param depAlias
     *            the depAlias to set
     */
    public void setDepAlias(String depAlias) {
        this.depAlias = depAlias;
    }

    /**
     * @return the ancestorDep
     */
    public Long getAncestorDep() {
        return ancestorDep;
    }

    /**
     * @param ancestorDep
     *            the ancestorDep to set
     */
    public void setAncestorDep(Long ancestorDep) {
        this.ancestorDep = ancestorDep;
    }

    /**
     * @return the orderNum
     */
    public Long getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum
     *            the orderNum to set
     */
    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * @return the delFlag
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     *            the delFlag to set
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * @return the valid
     */
    public Integer getValid() {
        return valid;
    }

    /**
     * @param valid
     *            the valid to set
     */
    public void setValid(Integer valid) {
        this.valid = valid;
    }

    /**
     * @return the rmDepId
     */
    public Long getRmDepId() {
        return rmDepId;
    }

    /**
     * @param rmDepId
     *            the rmDepId to set
     */
    public void setRmDepId(Long rmDepId) {
        this.rmDepId = rmDepId;
    }

}
