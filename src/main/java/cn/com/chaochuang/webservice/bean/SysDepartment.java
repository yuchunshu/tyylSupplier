package cn.com.chaochuang.webservice.bean;

public class SysDepartment {
    private static final long serialVersionUID = -8098711992771334960L;

    /** 原系统部门编号 */
    private Long              rmDepId;
    /** 部门名称 */
    private String            depName;
    /** 父部门编号 */
    private Long              parentDep;
    /** 祖先部门编号 */
    private Long              ancestorDep;
    /** 排序号 */
    private Long              orderNum;
    /** 部门别名 */
    private String            depAlias;
    /** 部门领导 */
    private String            depLead;
    /** 县局标识 */
    private String            depRank;
    /** 原系统的有效性标志 */
    private String            delFlag;
    /** MD5校验码 */
    private String            mdfCode;

    private Integer           valid;

    public Long getRmDepId() {
        return rmDepId;
    }

    public void setRmDepId(Long rmDepId) {
        this.rmDepId = rmDepId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public Long getParentDep() {
        return parentDep;
    }

    public void setParentDep(Long parentDep) {
        this.parentDep = parentDep;
    }

    public Long getAncestorDep() {
        return ancestorDep;
    }

    public void setAncestorDep(Long ancestorDep) {
        this.ancestorDep = ancestorDep;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public String getDepAlias() {
        return depAlias;
    }

    public void setDepAlias(String depAlias) {
        this.depAlias = depAlias;
    }

    public String getDepLead() {
        return depLead;
    }

    public void setDepLead(String depLead) {
        this.depLead = depLead;
    }

    public String getDepRank() {
        return depRank;
    }

    public void setDepRank(String depRank) {
        this.depRank = depRank;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getMdfCode() {
        return mdfCode;
    }

    public void setMdfCode(String mdfCode) {
        this.mdfCode = mdfCode;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

}
