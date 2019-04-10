/*
 * FileName:    SysShowBeanShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年1月9日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.select.bean;

/**
 * @author LaoZhiYong
 *
 */
public class SysUserShowBeanShowBean {
    /** 姓名 */
    private String userName;
    /** 职务 */
    private Long   dutyId;
    /** Id */
    private Long   id;

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
