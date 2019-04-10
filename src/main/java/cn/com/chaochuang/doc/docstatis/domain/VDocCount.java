/*
 * FileName:    VDocCount.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年12月4日 (ndy) 1.0 Create
 */

package cn.com.chaochuang.doc.docstatis.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.NotGenerateIdEntity;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;

/**
 *
 * 公文办理统计
 *
 * @author ndy
 *
 */
@Entity
@Table(name = "v_doc_count")
public class VDocCount extends NotGenerateIdEntity<Long> {

    private static final long serialVersionUID = -5010168872324074864L;

    /** 人员 */
    private Long              dealerId;
    @ManyToOne
    @JoinColumn(name = "dealerId", updatable = false, insertable = false)
    private SysUser           dealer;

    /** 待阅 */
    private Integer           reading;

    /** 已阅 */
    private Integer           readed;

    /** 待办 */
    private Integer           doing;

    /** 经办 */
    private Integer           doBy;

    /** 办结 */
    private Integer           done;

    /** 创建 */
    private Integer           createNum;

    /** 所属部门 */
    private Long              deptId;
    @ManyToOne
    @JoinColumn(name = "deptId", updatable = false, insertable = false)
    private SysDepartment     dept;

    public VDocCount() {

    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public SysUser getDealer() {
        return dealer;
    }

    public void setDealer(SysUser dealer) {
        this.dealer = dealer;
    }

    public Integer getReading() {
        return reading;
    }

    public void setReading(Integer reading) {
        this.reading = reading;
    }

    public Integer getReaded() {
        return readed;
    }

    public void setReaded(Integer readed) {
        this.readed = readed;
    }

    public Integer getDoing() {
        return doing;
    }

    public void setDoing(Integer doing) {
        this.doing = doing;
    }

    public Integer getDoBy() {
        return doBy;
    }

    public void setDoBy(Integer doBy) {
        this.doBy = doBy;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public Integer getCreateNum() {
        return createNum;
    }

    public void setCreateNum(Integer createNum) {
        this.createNum = createNum;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public SysDepartment getDept() {
        return dept;
    }

    public void setDept(SysDepartment dept) {
        this.dept = dept;
    }
}
