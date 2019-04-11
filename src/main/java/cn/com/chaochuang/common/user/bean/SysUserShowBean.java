package cn.com.chaochuang.common.user.bean;

import org.dozer.Mapping;

/**
 * @author LJX
 *
 */
public class SysUserShowBean {

    /** 所属部门名称 */
    @Mapping("department.deptName")
    private String deptName;

    /** 所属部门Id */
    @Mapping("department.id")
    private String deptId;

    /** 流水号 */
    private String id;

    /** 姓名 */
    private String userName;

    /** 登录账号 */
    private String account;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}


}
