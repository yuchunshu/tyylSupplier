package cn.com.chaochuang.common.syspower.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;

/**
 * @author hzr 2017年9月15日
 *
 */
@Entity
@Table(name = "sys_power_module")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "module_id")) })
public class SysPowerModule  extends LongIdEntity {

	private static final long serialVersionUID = 2824452217539257749L;

	/**子系统ID*/
    private String       appId;

    /**模块key*/
    private String       moduleKey;

    /**模块名称*/
    private String       moduleName;


	public String getModuleKey() {
		return moduleKey;
	}

	public void setModuleKey(String moduleKey) {
		this.moduleKey = moduleKey;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}


}
