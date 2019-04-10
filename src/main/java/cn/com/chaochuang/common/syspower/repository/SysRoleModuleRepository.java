package cn.com.chaochuang.common.syspower.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.syspower.domain.SysRoleModule;
/**
 * @author RDX
 */
public interface SysRoleModuleRepository extends SimpleDomainRepository<SysRoleModule, String> {

	public List<SysRoleModule> findByRoleId(Long roleId);
	
	@Query("select rm from SysRoleModule rm where rm.moduleId = :moduleId and rm.roleId = :roleId")
	public SysRoleModule findByModuleIdAndRoleId(@Param("moduleId") Long moduleId,@Param("roleId") Long roleId);

}
