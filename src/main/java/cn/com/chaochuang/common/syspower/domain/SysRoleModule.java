package cn.com.chaochuang.common.syspower.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;

/**
 * @author rdx 2017年9月19日
 *
 */
@Entity
@Table(name = "sys_role_module")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
public class SysRoleModule  extends StringUuidEntity {

	private static final long serialVersionUID = 6792791343481286023L;

	/** 系统角色ID */
    private Long         roleId;

    /** 模块ID */
    private Long         moduleId;

    /** 已选操作 */
    private String       operates;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getOperates() {
		return operates;
	}

	public void setOperates(String operates) {
		this.operates = operates;
	}




}
