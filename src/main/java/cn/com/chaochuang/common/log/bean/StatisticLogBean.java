/*
 * FileName:    StatisticLogBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年11月21日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.log.bean;

/**
 * @author LJX
 *
 */
public class StatisticLogBean {

    private Long oprUser;
    private Long oprPower;
    private Long oprIllegal;
    private Long oprOther;

    /**
     * @return the oprUser
     */
    public Long getOprUser() {
        return oprUser;
    }

    /**
     * @param oprUser
     *            the oprUser to set
     */
    public void setOprUser(Long oprUser) {
        this.oprUser = oprUser;
    }

    /**
     * @return the oprPower
     */
    public Long getOprPower() {
        return oprPower;
    }

    /**
     * @param oprPower
     *            the oprPower to set
     */
    public void setOprPower(Long oprPower) {
        this.oprPower = oprPower;
    }

    /**
     * @return the oprIllegal
     */
    public Long getOprIllegal() {
        return oprIllegal;
    }

    /**
     * @param oprIllegal
     *            the oprIllegal to set
     */
    public void setOprIllegal(Long oprIllegal) {
        this.oprIllegal = oprIllegal;
    }

    /**
     * @return the oprOther
     */
    public Long getOprOther() {
        return oprOther;
    }

    /**
     * @param oprOther
     *            the oprOther to set
     */
    public void setOprOther(Long oprOther) {
        this.oprOther = oprOther;
    }

}
