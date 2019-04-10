package cn.com.chaochuang.common.syspower.bean;


import cn.com.chaochuang.common.data.domain.LongIdEntity;


public class DetailPowerBean extends LongIdEntity {

	private static final long serialVersionUID = -4982833362859274340L;

	/** 角色id */
	private Long   roleId;
	
	/** URL请求 */
	private String url;
	
	/** 属性 */
	private String operate;
	
	/** 模块 */
	private String module;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	

}
