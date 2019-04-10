package cn.com.chaochuang.common.syspower.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.syspower.bean.DetailPowerBean;
import cn.com.chaochuang.common.syspower.bean.PowerInfo;
import cn.com.chaochuang.common.syspower.bean.PowerTreeBean;
import cn.com.chaochuang.common.syspower.bean.PowerTreeGridBean;
import cn.com.chaochuang.common.syspower.domain.SysPower;
import cn.com.chaochuang.common.syspower.domain.SysPowerModule;
import cn.com.chaochuang.common.syspower.domain.SysRole;
import cn.com.chaochuang.common.syspower.reference.IsMenu;
import cn.com.chaochuang.common.syspower.reference.PowerType;
import cn.com.chaochuang.common.syspower.repository.SysPowerRepository;
import cn.com.chaochuang.common.util.ModuleUtils;
import cn.com.chaochuang.common.util.Tools;

@Service
@Transactional
public class SysPowerServiceImpl extends SimpleLongIdCrudRestService<SysPower> implements SysPowerService {
	
	private final static String                 AUTO_ADD_POWER_NAME_PREFIX = "自动增加_";
	
	private final static String                 AUTO_ADD_POWER_CODE_PREFIX = "9999";

    @Autowired
    private SysPowerRepository    repository;

    @Autowired
    private SysRoleService        roleService;
    
    @Autowired
    private SysPowerModuleService powerModuleService;

    @PersistenceContext
    private EntityManager         em;

    @CacheEvict(value = "UserPowerCache", allEntries = true)
    public void persist(SysPower e) {
        super.persist(e);
    }

    @CacheEvict(value = "UserPowerCache", allEntries = true)
    public void merge(SysPower e) {
        super.merge(e);
    }

    @CacheEvict(value = "UserPowerCache", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @CacheEvict(value = "UserPowerCache", allEntries = true)
    public void delete(SysPower entity) {
        super.delete(entity);
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.data.service.CrudRestService#getRepository()
     */
    @Override
    public SimpleDomainRepository<SysPower, Long> getRepository() {
        return repository;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.syspower.service.SysPowerService#loadAllPower()
     */
    @Override
    public Page<SysPower> loadAllPower() {
        return new PageImpl<SysPower>(repository.findAll(new Sort("powerCode")));
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.syspower.service.SysPowerService#findPowers(java.lang.Long[])
     */
    @Override
    public Collection<SysPower> findPowers(Long[] ids) {
        return repository.findAll(Arrays.asList(ids));
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.syspower.service.SysPowerService#findByPowerType(cn.com.chaochuang.common.syspower.reference.PowerType)
     */
    @Override
    public List<SysPower> findByPowerType(PowerType powerType) {
        return repository.findByPowerTypeFlag(powerType);
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.syspower.service.SysPowerService#loadPowerNeed()
     */
    @Override
    public Page<SysPower> loadPowerNeed() {
        PowerType[] powerTypes = { PowerType.自动增加, PowerType.需要授权 };
        return new PageImpl<SysPower>(repository.findByIsMenuOrPowerTypeFlagInOrderByPowerCodeAsc(IsMenu.是, powerTypes));
    }

    @Override
    // 主菜单只允许最多三级
    public List<PowerTreeBean> currentPowers() {
        List<SysPower> allPowers = this.selectPower();

        List<PowerTreeBean> treeList = null;
        if (allPowers != null && allPowers.size() > 0) {
            treeList = new ArrayList<PowerTreeBean>();

            List<SysPower> rootlist = this.getSubPowers(allPowers, null);
            for (SysPower p : rootlist) {
                PowerTreeBean b = new PowerTreeBean(p.getId(), p.getPowerName(), p.getUrl(), p.getIcon());

                // 子菜单1
                List<SysPower> list1 = this.getSubPowers(allPowers, p.getId());
                // 子菜单树结构1
                List<PowerTreeBean> treeList1 = null;

                if (list1 != null && list1.size() > 0) {
                    treeList1 = new ArrayList<PowerTreeBean>();
                    for (SysPower p1 : list1) {
                        PowerTreeBean b1 = new PowerTreeBean(p1.getId(), p1.getPowerName(), p1.getUrl(), p1.getIcon());

                        // 子菜单2
                        List<SysPower> list2 = this.getSubPowers(allPowers, p1.getId());
                        // 子菜单树结构2
                        List<PowerTreeBean> treeList2 = null;

                        if (list2 != null && list2.size() > 0) {
                            treeList2 = new ArrayList<PowerTreeBean>();
                            for (SysPower p2 : list2) {
                                PowerTreeBean b2 = new PowerTreeBean(p2.getId(), p2.getPowerName(), p2.getUrl(),
                                                p2.getIcon());
                                treeList2.add(b2);
                            }
                        }

                        b1.setChildren(treeList2);
                        treeList1.add(b1);
                    }
                }

                b.setChildren(treeList1);

                treeList.add(b);
            }
        }
        return treeList;
    }

    // 一次性从数据库中加载当前用户的所有可用的菜单，然后再按父id来取子菜单
    private List<SysPower> getSubPowers(List<SysPower> allPowers, Long parentPowerId) {
        List<SysPower> list = new ArrayList<SysPower>();
        for (SysPower p : allPowers) {
            if (parentPowerId == null) {
                // 根菜单
                if (p.getParentPowerId() == null)
                    list.add(p);
            } else {
                if (p.getParentPowerId() != null && p.getParentPowerId().longValue() == parentPowerId.longValue())
                    list.add(p);
            }
        }
        return list;
    }

    private List<SysPower> selectPower() {

        StringBuilder sql = new StringBuilder();
        sql.append("select p.* from sys_power p where p.is_menu= ?1");
        sql.append(" and p.power_id in (select rp.power_id from sys_role_power rp, sys_user_role_rel ur where rp.role_id = ur.role_id and ur.user_id = ?2 ) ");
        sql.append(" order by p.sort ");
        Query query = em.createNativeQuery(sql.toString(), SysPower.class);
        query.setParameter(1,IsMenu.是.getKey());
        query.setParameter(2,UserTool.getInstance().getCurrentUserId());
        return query.getResultList();
    }

    @Override
    public List<PowerTreeGridBean> getAllRoot() {
        List<SysPower> rootList = this.repository.findAllRoot();
        List<PowerTreeGridBean> returnList = new ArrayList<PowerTreeGridBean>();
        if (rootList != null && rootList.size() > 0) {
            for (SysPower power : rootList) {
                PowerTreeGridBean bean = new PowerTreeGridBean();
                BeanUtils.copyProperties(power, bean);
                bean.setId(power.getId());
                bean.setChildren(this.getChildrenByParentId(power.getId()));
                returnList.add(bean);
            }
        }
        return returnList;
    }

    @Override
    public List<PowerTreeGridBean> getChildrenByParentId(Long parentPowerId) {
        List<PowerTreeGridBean> returnList = null;
        if (parentPowerId != null) {
            List<SysPower> powerList = this.repository.findByParentPowerIdOrderBySortAsc(parentPowerId);
            if (powerList != null && powerList.size() > 0) {
                returnList = new ArrayList<PowerTreeGridBean>();
                for (SysPower p : powerList) {
                    PowerTreeGridBean bean = new PowerTreeGridBean();
                    BeanUtils.copyProperties(p, bean);
                    bean.setId(p.getId());
                    bean.setChildren(this.getChildrenByParentId(p.getId()));
                    returnList.add(bean);
                }
            }
        }
        return returnList;
    }

    @Override
    public List<PowerTreeBean> getMenuTree() {
        List<SysPower> rootList = this.repository.findAllRoot();
        List<PowerTreeBean> returnList = new ArrayList<PowerTreeBean>();
        if (rootList != null && rootList.size() > 0) {
            for (SysPower power : rootList) {
                PowerTreeBean bean = new PowerTreeBean();
                bean.setId(power.getId());
                bean.setText(power.getPowerName());
                bean.setChildren(this.getMenuChildren(power.getId()));
                returnList.add(bean);
            }
        }
        return returnList;
    }

    @Override
    public List<PowerTreeBean> getMenuChildren(Long parentId) {
        List<PowerTreeBean> returnList = null;
        if (parentId != null) {
            List<SysPower> powerList = this.repository.findByParentPowerIdAndIsMenuOrderBySortAsc(parentId, IsMenu.是);
            if (powerList != null && powerList.size() > 0) {
                returnList = new ArrayList<PowerTreeBean>();
                for (SysPower p : powerList) {
                    PowerTreeBean bean = new PowerTreeBean();
                    bean.setId(p.getId());
                    bean.setText(p.getPowerName());
                    bean.setChildren(this.getMenuChildren(p.getId()));
                    returnList.add(bean);
                }
            }
        }
        return returnList;
    }

    @Override
    public List<PowerTreeGridBean> loadAllPowerNeed(Long roleId) {
        List<PowerTreeGridBean> returnList = null;
        List<SysPower> powers = this.repository.getAllNeedRoot();
        if (powers != null && powers.size() > 0) {
            Set<SysPower> havePower = null;
            if (roleId != null) {
                SysRole role = this.roleService.findOne(roleId);
                if (role != null) {
                    havePower = role.getPowers();
                }
            }
            if (havePower == null) {
                havePower = new HashSet<SysPower>();
            }
            returnList = new ArrayList<PowerTreeGridBean>();
            for (SysPower p : powers) {
                PowerTreeGridBean bean = new PowerTreeGridBean(p.getId(), p.getPowerCode(), p.getPowerName(),
                                p.getUrl(), p.getPowerTypeFlag(), p.getIsMenu(), p.getSort(), null);
                bean.setChecked(havePower.contains(p));
                bean.setChildren(this.getAllNeedChildren(p.getId(), havePower));
                returnList.add(bean);
            }
        }
        return returnList;
    }

    @Override
    public List<PowerTreeGridBean> getAllNeedChildren(Long parentId, Set<SysPower> havePower) {
        List<PowerTreeGridBean> returnList = null;
        if (parentId != null) {
            List<SysPower> children = this.repository.getAllNeedChildren(parentId);
            if (children != null && children.size() > 0) {
                returnList = new ArrayList<PowerTreeGridBean>();
                for (SysPower child : children) {
                    PowerTreeGridBean bean = new PowerTreeGridBean(child.getId(), child.getPowerCode(),
                                    child.getPowerName(), child.getUrl(), child.getPowerTypeFlag(), child.getIsMenu(),
                                    child.getSort(), null);
                    bean.setChecked(havePower.contains(child));
                    bean.setChildren(this.getAllNeedChildren(child.getId(), havePower));
                    returnList.add(bean);
                }
            }
        }
        return returnList;
    }

    @Override
    public String savePower(PowerInfo info) {
        SysPower p = new SysPower();
        if (info.getId() != null) {
            p = this.findOne(info.getId());
        }
        BeanUtils.copyProperties(info, p);
        this.persist(p);

        return p.getId().toString();
    }

    @Override
    public String deletePower(Long id) {
        if (id != null) {
            SysPower power = this.findOne(id);
            if (power != null) {
                List<SysRole> roles = (List<SysRole>) this.roleService.findAll();
                if (roles != null && roles.size() > 0) {
                    for (SysRole r : roles) {
                        Set<SysPower> pSet = r.getPowers();
                        if (pSet != null && pSet.size() > 0) {
                            if (pSet.contains(power)) {
                            	//将角色关联的power删除
                            	pSet.remove(power);
                            	r.setPowers(pSet);
                            	this.roleService.persist(r);
                                //return "该权限已关联有角色，不能删除!";
                            }
                        }
                    }
                }
                this.repository.delete(power);
            }
        }
        return null;
    }

    @Override
    public List<SysPower> selectPower(Long parentPowerId, Long userId) {
        // String sql =
        // "select distinct p.power_Id, p.* from sys_power p left join sys_role_power rp on p.power_id = rp.power_id left join sys_user_role_rel ur on rp.role_id = ur.role_id";
        // sql += " where ";
        // sql += " p.parent_power_id " + (parentPowerId != null ? "=" + parentPowerId : " is null ");
        // sql += " and ur.user_id = " + UserTool.getInstance().getCurrentUserId();
        // sql += " and p.is_menu= " + IsMenu.是.getKey();
        // sql += " order by p.sort ";

        StringBuffer sqlStr = new StringBuffer();
        sqlStr.append(" select p.* from  sys_power p");
        sqlStr.append(" where ");
        sqlStr.append("   exists(");
        sqlStr.append("          select 1 from sys_role_power rp,sys_user_role_rel ur ");
        sqlStr.append("             where rp.role_id = ur.role_id ");
        sqlStr.append("             and ur.user_id =" + UserTool.getInstance().getCurrentUserId());
        sqlStr.append("             and p.power_id=rp.power_id) ");
        sqlStr.append("   and p.is_menu=" + IsMenu.是.getKey());
        if (parentPowerId != null) {
            sqlStr.append("   and p.parent_power_id=" + parentPowerId);
        } else {
            sqlStr.append("   and p.parent_power_id is null");
        }
        sqlStr.append(" order by p.sort  ");
        Query query = em.createNativeQuery(sqlStr.toString(), SysPower.class);
        ArrayList list = (ArrayList) query.getResultList();
        return list;
    }

    @Override
    public List<PowerTreeGridBean> getMenus(Long parentId) {
        List<SysPower> l1 = this.selectPower(parentId, Long.valueOf(UserTool.getInstance().getCurrentUserId()));
        List<PowerTreeGridBean> value = null;
        if (l1 != null && l1.size() > 0) {
            value = new ArrayList<PowerTreeGridBean>();
            for (SysPower p : l1) {
                PowerTreeGridBean ptgb = new PowerTreeGridBean();
                BeanUtils.copyProperties(p, ptgb);

                List<SysPower> l2 = this
                                .selectPower(p.getId(), Long.valueOf(UserTool.getInstance().getCurrentUserId()));
                List<PowerTreeGridBean> value2 = null;
                if (l2 != null && l2.size() > 0) {
                    value2 = new ArrayList<PowerTreeGridBean>();
                    for (SysPower p2 : l2) {
                        PowerTreeGridBean ptgb2 = new PowerTreeGridBean();
                        BeanUtils.copyProperties(p2, ptgb2);
                        value2.add(ptgb2);
                    }
                }

                ptgb.setChildren(value2);

                value.add(ptgb);
            }
        }
        return value;
    }

    @Override
    public List<SysPower> selectCurAllMenus(Long userId, String powerName) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select p.* from sys_power p ");
        sql.append(" where ");
        sql.append("   exists(select 1 from sys_role_power rp,sys_user_role_rel ur where rp.role_id = ur.role_id and ur.user_id =? and  p.power_id=rp.power_id) ");
        sql.append("   and p.url is not null and  length(trim(p.url)) <> 0");
        sql.append("   and p.is_menu = ?");
        if (StringUtils.isNotBlank(powerName)) {
            sql.append("   and p.power_name like '%?%' ");
        }

        sql.append(" order by p.power_code ");
        Query query = em.createNativeQuery(sql.toString(), SysPower.class);
        query.setParameter(1, userId);
        query.setParameter(2, IsMenu.是.getKey());
        if (StringUtils.isNotBlank(powerName)) {
            query.setParameter(3, powerName);
        }
        ArrayList list = (ArrayList) query.getResultList();
        return list;
    }


    @Override
    public List<SysPower> selectAllAppMenus(Long userId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select p.* from sys_power p ");
        sql.append(" where ");
        sql.append(" exists(select 1 from sys_role_power rp,sys_user_role_rel ur where rp.role_id = ur.role_id and ur.user_id =? and  p.power_id=rp.power_id) ");
        sql.append(" and p.appurl is not null and  length(trim(p.appurl)) <> 0");
        sql.append(" and p.is_menu = ?");
        sql.append(" order by p.power_code ");
        Query query = em.createNativeQuery(sql.toString(), SysPower.class);
        query.setParameter(1, userId);
        query.setParameter(2, IsMenu.是.getKey());
        return query.getResultList();
    }


    @Override
    public List<PowerTreeGridBean> getCurAllMenus() {
        List<SysPower> powers = this.selectPower(null, null);
        List<PowerTreeGridBean> menus = new ArrayList<PowerTreeGridBean>();
        if (Tools.isNotEmptyList(powers)) {
            for (SysPower power : powers) {
                PowerTreeGridBean b = new PowerTreeGridBean();
                BeanUtils.copyProperties(power, b);
                b.setChildren(this.getMenus(power.getId()));
                b.setId(power.getId());
                menus.add(b);
            }
        }
        return menus;
    }
    
    @Override
    public boolean selectPowerByPowerNameAndCurrentUser(String powerName) {
    	boolean result = false;
        StringBuilder sql = new StringBuilder();
        sql.append("select p.* from sys_power p where p.is_menu= ?1");
        sql.append(" and p.power_id in (select rp.power_id from sys_role_power rp, sys_user_role_rel ur where rp.role_id = ur.role_id and ur.user_id = ?2 ) ");
        sql.append(" and p.power_name =?3 ");
        sql.append(" order by p.sort ");
        Query query = em.createNativeQuery(sql.toString(), SysPower.class);
        query.setParameter(1,IsMenu.否.getKey());
        query.setParameter(2,UserTool.getInstance().getCurrentUserId());
        query.setParameter(3,powerName);
        
        List<SysPower> list= query.getResultList();
        if(Tools.isNotEmptyList(list)){
        	 result = true;
        }
        return result;
    }
    
    @Override
    public List<SysPower> selectPowerByRoleId(Long roleId, DetailPowerBean bean, Integer rows, Integer page){
    	if(bean == null){
    		bean = new DetailPowerBean();
    	}
    	StringBuffer sql = new StringBuffer();
        sql.append(" select p.* from sys_power p ");
        sql.append(" where ");
        sql.append(" exists(select 1 from sys_role_power rp,sys_role r where rp.role_id = r.role_id and p.power_id = rp.power_id ");
        sql.append(" and r.role_id = ?1) ");
        sql.append(" and p.power_type_flag = ?2 ");
        sql.append(" and (p.is_menu <> ?3 or p.is_menu is null) ");
        sql.append(" and p.url is not null and  length(trim(p.url)) <> 0 ");
        if(StringUtils.isNotBlank(bean.getModule())){
        	sql.append(" and p.module = ?4 ");
        }
        sql.append(" and p.url like ?5 ");
        sql.append(" group by p.url ");
        sql.append(" order by p.url asc limit ?6,?7");//order by p.sort ASC,p.power_code DESC 
        
        Query query = em.createNativeQuery(sql.toString(), SysPower.class);
        query.setParameter(1, roleId);
        query.setParameter(2, PowerType.自动增加.getKey());
        query.setParameter(3, IsMenu.是.getKey());
        if(StringUtils.isNotBlank(bean.getModule())){
        	query.setParameter(4, bean.getModule());
        }
        if(StringUtils.isNotBlank(bean.getUrl())){
        	query.setParameter(5, "%" + bean.getUrl() + "%");
        }else{
        	query.setParameter(5, "%");
        }
        
        query.setParameter(6, rows*(page-1));
        query.setParameter(7, rows);
        return query.getResultList();
    }
    
    @Override
    public Integer countPowerByRoleId(Long roleId){
    	return this.selectPowerByRoleId(roleId, null, 10000000, 1).size();
    }
    
    @Override
	public SysPower checkAndSaveSysPower(DetailPowerBean detailPower){
		SysPower power = this.repository.findByUrl(detailPower.getUrl());
		SysRole role = this.roleService.findOne(detailPower.getRoleId());
		List<SysPowerModule> pmList = this.powerModuleService.getRepository().findAll();
		Set<SysPower> powerSet = role.getPowers();
		if(power == null){
			power = new SysPower();
			BeanUtils.copyProperties(detailPower, power);
			String idStr = Integer.toString(power.getUrl().hashCode());
			power.setIsMenu(IsMenu.否);
			power.setPowerTypeFlag(PowerType.自动增加);
			power.setPowerName(AUTO_ADD_POWER_NAME_PREFIX + idStr);
			power.setPowerCode(AUTO_ADD_POWER_CODE_PREFIX + idStr);
			power.setModule(ModuleUtils.matchModuleName(pmList, power.getUrl()));
			power.setOperate(ModuleUtils.matchOperate(power.getUrl()));
			power = this.repository.save(power);
		}
		
		powerSet.add(power);
		role.setPowers(powerSet);
		this.roleService.getRepository().save(role);
		return power;
	}
    
    @Override
    public SysRole delDetailPower(SysPower power,Long roleId){
		SysRole role = this.roleService.findOne(roleId);//查出关联角色，解除关联
		if(role == null){
			return null;
		}
		Set<SysPower> powerSet = role.getPowers();
		for(SysPower p:powerSet){
			if(p.getId().equals(power.getId())){
				powerSet.remove(p);
				break;
			}
		}
		role.setPowers(powerSet);
		return this.roleService.getRepository().save(role);
	}
    
    @Override
    public Long recommendPowerCode(Long parentPowerId, Long id){
    	//用于编辑编号为空的权限
    	if(id != null){
    		SysPower power = this.findOne(id);
    		if(power != null && power.getParentPowerId() != null){
    			parentPowerId = power.getParentPowerId();
    		}
    	}
    	//用于新增权限推荐编号
    	if(parentPowerId != null){
    		List<PowerTreeGridBean> childrenList = this.getChildrenByParentId(parentPowerId);
        	SysPower parentPower = this.findOne(parentPowerId);
        	Long maxCode = null;//最大编号
    		try {
    			maxCode = Long.valueOf(parentPower.getPowerCode() + "0000");
    		} catch (NumberFormatException e1) {
    			return null;
    		}
        	if(childrenList == null){
        		return ++maxCode;
        	}
        	for(PowerTreeGridBean bean:childrenList){
        		try {
    				Long powerCode = Long.valueOf(bean.getPowerCode());
    				maxCode = maxCode > powerCode ? maxCode : powerCode;
    			} catch (NumberFormatException e) {
    				continue;
    			}
        	}
        	return ++maxCode;
    	}
    	return null;
    }
}
