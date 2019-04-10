package cn.com.chaochuang.mobile.bean;


public class AppUserInfo {

    private Long     userId;

    private String   userName;

    private Long     deptId;

    private String   deptName;

    private String   key;

    private String   ukey;


	public AppUserInfo(Long userId, String userName, Long deptId, String deptName) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.deptId = deptId;
		this.deptName = deptName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUkey() {
		return ukey;
	}

	public void setUkey(String ukey) {
		this.ukey = ukey;
	}
}