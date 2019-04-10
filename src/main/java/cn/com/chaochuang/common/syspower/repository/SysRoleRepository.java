package cn.com.chaochuang.common.syspower.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.syspower.domain.SysRole;

public interface SysRoleRepository extends SimpleDomainRepository<SysRole, Long> {

    List<SysRole> findByRoleName(String roleName);

    List<SysRole> findByUsersId(Long userId);

}
