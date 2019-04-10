/*
 * FileName:    VDocCountShowBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年12月5日 (ndy) 1.0 Create
 */

package cn.com.chaochuang.doc.docstatis.bean;

import org.dozer.Mapping;

/**
 * @author ndy
 *
 */
public class VDocCountShowBean {

    private Long    id;

    /** 处理人 */
    @Mapping("dealer.userName")
    private String  dealerName;

    /** 待阅 */
    private Integer reading;

    /** 已阅 */
    private Integer readed;

    /** 待办 */
    private Integer doing;

    /** 经办 */
    private Integer doBy;

    /** 办结 */
    private Integer done;

    /** 创建 */
    private Integer createNum;

    /** 所属部门 */
    @Mapping("dept.deptName")
    private String  deptName;

    public VDocCountShowBean() {

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

    /**
     * @return the dealerName
     */
    public String getDealerName() {
        return dealerName;
    }

    /**
     * @param dealerName
     *            the dealerName to set
     */
    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    /**
     * @return the reading
     */
    public Integer getReading() {
        return reading;
    }

    /**
     * @param reading
     *            the reading to set
     */
    public void setReading(Integer reading) {
        this.reading = reading;
    }

    /**
     * @return the readed
     */
    public Integer getReaded() {
        return readed;
    }

    /**
     * @param readed
     *            the readed to set
     */
    public void setReaded(Integer readed) {
        this.readed = readed;
    }

    /**
     * @return the doing
     */
    public Integer getDoing() {
        return doing;
    }

    /**
     * @param doing
     *            the doing to set
     */
    public void setDoing(Integer doing) {
        this.doing = doing;
    }

    /**
     * @return the doBy
     */
    public Integer getDoBy() {
        return doBy;
    }

    /**
     * @param doBy
     *            the doBy to set
     */
    public void setDoBy(Integer doBy) {
        this.doBy = doBy;
    }

    /**
     * @return the done
     */
    public Integer getDone() {
        return done;
    }

    /**
     * @param done
     *            the done to set
     */
    public void setDone(Integer done) {
        this.done = done;
    }

    /**
     * @return the createNum
     */
    public Integer getCreateNum() {
        return createNum;
    }

    /**
     * @param createNum
     *            the createNum to set
     */
    public void setCreateNum(Integer createNum) {
        this.createNum = createNum;
    }

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

}
