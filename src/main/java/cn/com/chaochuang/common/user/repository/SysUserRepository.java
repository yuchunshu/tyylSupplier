package cn.com.chaochuang.common.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.user.domain.SysUser;

public interface SysUserRepository extends SimpleDomainRepository<SysUser, Long> {

    public SysUser findByAccountAndValid(final String account, final Integer valid);

    public List<SysUser> findByValid(final Integer valid);

    public List<SysUser> findByDepartmentIdAndValidOrderBySortAsc(final Long deptId, final Integer valid);

    public List<SysUser> findByAccount(String account);

    public List<SysUser> findByAccountLocked(Boolean isLocked);

    @Query("select u from SysUser u where u.userName like '%:userName%' order by u.userName asc")
    public List<SysUser> findByUserNameLikeOrderByUserNameAsc(@Param("userName") String userName);

    @Query("select u from SysUser u where u.valid = ?1 order by u.sort asc")
    public List<SysUser> findByValidOrderBySortAsc(Integer valid);
    
    @Modifying()
    @Query("update SysUser u set u.dutyId = null where u.dutyId =:dutyId")
    public void deleteDutyId(@Param("dutyId")Long dutyId);
    
    @Query("select count(u.userName) from SysUser u where u.deptId != :deptId and u.valid = :valid")
    public Integer findByDeptIdAndValid(@Param("deptId")Long deptId, @Param("valid")Integer valid);
}
