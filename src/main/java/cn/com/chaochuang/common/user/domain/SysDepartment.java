package cn.com.chaochuang.common.user.domain;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import cn.com.chaochuang.common.data.domain.PersistEntity;
import cn.com.chaochuang.common.lookup.annotation.LookUp;
import cn.com.chaochuang.common.reference.AdministrativeLevel;
import cn.com.chaochuang.common.reference.AdministrativeLevelConverter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@LookUp
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "dept_id")),
                @AttributeOverride(name = "valid", column = @Column(name = "valid")) })
public class SysDepartment extends PersistEntity {

    /** 暂定根部门ID = 1*/
    public static final long    ROOT_DEPT     = 1;

    /** 部门名称 */
    private String              deptName;

    /** 部门编码 */
    private String              deptCode;

    /** 父部门编号 */
    private Long                deptParent;

    /** 祖先部门编号 */
    private Long                unitId;

    /** 排序号 */
    private Long                orderNum;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "deptParent")
    private Set<SysDepartment>  subDepartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "deptParent", insertable = false, updatable = false)
    private SysDepartment       parentDepartment;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "unitId", insertable = false, updatable = false)
    private SysDepartment       unitDept;

    /** 部门级别(改成与区食药新权限管理系统的部门表一致) */
    @Convert(converter = AdministrativeLevelConverter.class)
    private AdministrativeLevel deptLevel;

    /**
     * @return the leaf
     */
    public boolean isLeaf() {
        if (null != subDepartment && subDepartment.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Long getDeptParent() {
        return deptParent;
    }

    public void setDeptParent(Long deptParent) {
        this.deptParent = deptParent;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public Set<SysDepartment> getSubDepartment() {
        return subDepartment;
    }

    public void setSubDepartment(Set<SysDepartment> subDepartment) {
        this.subDepartment = subDepartment;
    }

    public SysDepartment getParentDepartment() {
        return parentDepartment;
    }

    public void setParentDepartment(SysDepartment parentDepartment) {
        this.parentDepartment = parentDepartment;
    }

    public SysDepartment getUnitDept() {
        return unitDept;
    }

    public void setUnitDept(SysDepartment unitDept) {
        this.unitDept = unitDept;
    }

	public AdministrativeLevel getDeptLevel() {
		return deptLevel;
	}

	public void setDeptLevel(AdministrativeLevel deptLevel) {
		this.deptLevel = deptLevel;
	}

}
