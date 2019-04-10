package cn.com.chaochuang.common.syspower.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.syspower.bean.ModuleShowBean;
import cn.com.chaochuang.common.syspower.domain.SysRole;
import cn.com.chaochuang.common.syspower.domain.SysRoleModule;
/**
 * @author RDX
 */
public interface SysRoleModuleService extends CrudRestService<SysRoleModule, String> {

	/**
	 * 更新角色与模块的关系
	 * @param beanList
	 */
	public void updateRoleModule(List<ModuleShowBean> beanList);

	/**
	 * 根据SysRoleModule,扫描url添加至指定角色
	 */
	SysRole updatePower(Long roleId);

	void refreshCaches(HttpServletRequest request, HttpServletResponse response);
	
	SysRoleModule findByModuleIdAndRoleId(Long moduleId, Long roleId);
}
