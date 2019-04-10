package cn.com.chaochuang.common.syspower.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.syspower.bean.DetailPowerBean;
import cn.com.chaochuang.common.syspower.bean.PowerInfo;
import cn.com.chaochuang.common.syspower.bean.PowerTreeBean;
import cn.com.chaochuang.common.syspower.bean.PowerTreeGridBean;
import cn.com.chaochuang.common.syspower.domain.SysPower;
import cn.com.chaochuang.common.syspower.domain.SysRole;
import cn.com.chaochuang.common.syspower.reference.PowerType;

public interface SysPowerService extends CrudRestService<SysPower, Long> {

    public Page<SysPower> loadAllPower();

    public Page<SysPower> loadPowerNeed();

    public Collection<SysPower> findPowers(Long[] ids);

    public List<SysPower> findByPowerType(PowerType powerType);

    public List<PowerTreeBean> currentPowers();

    /**
     * 查询所有根节点 用于TreeGrid
     *
     * @return
     */
    public List<PowerTreeGridBean> getAllRoot();

    /**
     * 按父节点ID查询子节点 用于TreeGrid
     *
     * @param parentPowerId
     * @return
     */
    public List<PowerTreeGridBean> getChildrenByParentId(Long parentPowerId);

    public List<PowerTreeBean> getMenuTree();

    public List<PowerTreeBean> getMenuChildren(Long parentId);

    public List<PowerTreeGridBean> loadAllPowerNeed(Long roleId);

    public List<PowerTreeGridBean> getAllNeedChildren(Long parentId, Set<SysPower> havePower);

    public String savePower(PowerInfo info);

    /**
     * 删除权限
     *
     * @param id
     * @return 错误信息（正常则返回null）
     */
    public String deletePower(Long id);

    public List<SysPower> selectPower(Long parentPowerId, Long userId);

    /**
     * 根据父Id获取二三级菜单
     *
     * @param parentId
     * @return
     */
    public List<PowerTreeGridBean> getMenus(Long parentId);

    /**
     * 获取当前用户所有有url的菜单
     *
     * @return
     */
    public List<SysPower> selectCurAllMenus(Long userId, String powerName);


    /**
     * 获取用户一二三级菜单
     *
     * @return
     */
    public List<PowerTreeGridBean> getCurAllMenus();

    /** app菜单*/
    List<SysPower> selectAllAppMenus(Long userId);
    
    /** 详细权限 */
    public List<SysPower> selectPowerByRoleId(Long roleId, DetailPowerBean bean, Integer rows, Integer page);
    
    /** 详细权限总数量 */
    public Integer countPowerByRoleId(Long roleId);
    
    /** 新增权限关联角色（权限已有则直接关联，没有新增后关联） */
    public SysPower checkAndSaveSysPower(DetailPowerBean detailPower);
    
    /** 解除权限角色关联 */
    public SysRole delDetailPower(SysPower power,Long roleId);
    
    /**
     * 推荐权限编号
     * @param parentPowerId 用于新增权限，根据父权限推荐编号(编辑已有权限时可为null)
     * @param id 用于编辑已有权限，但编号为空时推荐编号(新增权限时可为null)
     * @return
     */
    public Long recommendPowerCode(Long parentPowerId, Long id);
    
    /**
     * 根据权限名称判断当前用户是否拥有该权限
     *
     * @return
     */
    public boolean selectPowerByPowerNameAndCurrentUser(String powerName);

}
