package cn.com.chaochuang.common.syspower.bean;
/**
 * @author RDX
 */
public class ModuleShowBean {
	
	/** 模块id */
	private Long    moduleId;
	
	/** 角色id */
	private Long    roleId;
	
	/** 模块名称 */
	private String  moduleName;
	
	/** 模块已选操作 */
	private String  operates;
	
	/** 模块key */
	private String  moduleKey;
	
	/** 添加 */
	private boolean cre;
	
	/** 修改 */
	private boolean upd;
	
	/** 删除 */
	private boolean del;
	
	/** 查询 */
	private boolean ret;
	
	/** 统计 */
	private boolean sta;
	
	/** 审核 */
	private boolean aud;
	
	/** 打印 */
	private boolean prn;
	
	/** 导出 */
	private boolean exp;
	
	/** 导入 */
	private boolean imp;
	

	
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getOperates() {
		return operates;
	}

	public void setOperates(String operates) {
		this.operates = operates;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public boolean isCre() {
		return cre;
	}

	public void setCre(boolean cre) {
		this.cre = cre;
	}

	public boolean isUpd() {
		return upd;
	}

	public void setUpd(boolean upd) {
		this.upd = upd;
	}

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

	public boolean isRet() {
		return ret;
	}

	public void setRet(boolean ret) {
		this.ret = ret;
	}

	public boolean isSta() {
		return sta;
	}

	public void setSta(boolean sta) {
		this.sta = sta;
	}

	public boolean isAud() {
		return aud;
	}

	public void setAud(boolean aud) {
		this.aud = aud;
	}

	public boolean isPrn() {
		return prn;
	}

	public void setPrn(boolean prn) {
		this.prn = prn;
	}

	public boolean isExp() {
		return exp;
	}

	public void setExp(boolean exp) {
		this.exp = exp;
	}

	public boolean isImp() {
		return imp;
	}

	public void setImp(boolean imp) {
		this.imp = imp;
	}

	public String getModuleKey() {
		return moduleKey;
	}

	public void setModuleKey(String moduleKey) {
		this.moduleKey = moduleKey;
	}

	
	
	
	

}
