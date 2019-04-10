package cn.com.chaochuang.common.syspower.repository;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.syspower.domain.SysPowerModule;
/**
 * @author rdx
 */
public interface SysPowerModuleRepository extends SimpleDomainRepository<SysPowerModule, Long> {

	/**
	 * 根据moduleKey查找
	 * @param moduleKey
	 * @return
	 */
	SysPowerModule findByModuleKey(String moduleKey);
}
