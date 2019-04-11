package cn.com.chaochuang.common.syspower.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.syspower.domain.SysPower;
import cn.com.chaochuang.common.syspower.reference.IsMenu;
import cn.com.chaochuang.common.syspower.reference.PowerType;

public interface SysPowerRepository extends SimpleDomainRepository<SysPower, Long> {

    public SysPower findByUrl(String url);

    public SysPower findByPowerName(String powerName);

    public List<SysPower> findByPowerTypeFlag(PowerType powerType);

    public List<SysPower> findByIsMenuOrPowerTypeFlagInOrderByPowerCodeAsc(IsMenu isMenu, PowerType[] powerType);

    /**
     * 查询所有根节点 权限
     *
     * @return
     */
    @Query("select p from SysPower p where p.parentPowerId is null and p.powerTypeFlag <> 0 order by p.sort asc")
    public List<SysPower> findAllRoot();

    /**
     * 查询所有根节点 菜单
     *
     * @return
     */
    @Query("select p from SysPower p where p.parentPowerId is null and p.isMenu = 1 order by p.sort asc")
    public List<SysPower> findAllMenuRoot();

    /**
     * 按父节点ID查询 菜单
     *
     * @param parentPowerId
     * @param isMenu
     * @return
     */
    public List<SysPower> findByParentPowerIdAndIsMenuOrderBySortAsc(Long parentPowerId, IsMenu isMenu);

    /**
     * 按父节点ID查询 权限
     *
     * @param parentPowerId
     * @return
     */
    public List<SysPower> findByParentPowerIdOrderBySortAsc(Long parentPowerId);

    /**
     * 查询所有需要授权的权限根权限
     *
     * @return
     */
    @Query("select p from SysPower p where p.parentPowerId is null and (p.powerTypeFlag = 3 or p.isMenu = 1) order by p.sort asc")
    public List<SysPower> getAllNeedRoot();

    @Query("select p from SysPower p where p.parentPowerId=:parentId and (p.powerTypeFlag = 3 or p.isMenu = 1) order by p.sort asc")
    public List<SysPower> getAllNeedChildren(@Param("parentId") Long parentId);

}
