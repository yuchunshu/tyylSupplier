package cn.com.chaochuang.common.syspower.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.syspower.bean.ModuleShowBean;
import cn.com.chaochuang.common.syspower.domain.SysPowerModule;
/**
 * @author RDX
 */
public interface SysPowerModuleService extends CrudRestService<SysPowerModule, Long> {

	/**
	 * 根据角色id查询模块
	 */
	List<ModuleShowBean> getAllModuleByRoleId(Long roleId);
	
	/**
	 * 更新
	 * @param powerModule
	 * @return
	 */
	SysPowerModule updatePowerModule(SysPowerModule powerModule);

	/**
	 * 删除
	 * @param powerModule
	 * @return
	 */
	void deletePowerModule(SysPowerModule powerModule);
	
	/**
	 * 获取模块名称下拉数据
	 * @return
	 */
	List<String> getModuleSelectBox();
	
}
