package cn.com.chaochuang.common.syspower.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.cache.RefreshCache;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.syspower.bean.ModuleShowBean;
import cn.com.chaochuang.common.syspower.domain.SysPower;
import cn.com.chaochuang.common.syspower.domain.SysRole;
import cn.com.chaochuang.common.syspower.domain.SysRoleModule;
import cn.com.chaochuang.common.syspower.repository.SysRoleModuleRepository;
import cn.com.chaochuang.common.util.ModuleUtils;

/**
 *
 * @author RDX
 *
 */
@Service
@Transactional
public class SysRoleModuleServiceImpl extends SimpleUuidCrudRestService<SysRoleModule> implements SysRoleModuleService {

	protected final Log                  logger = LogFactory.getLog(getClass());

    @Autowired
    private SysRoleModuleRepository      repository;

    @Autowired
    private SysPowerService              sysPowerService;

    @Autowired
    private SysRoleService               sysRoleService;

    @PersistenceContext
    private EntityManager                em;


    @Autowired(required = false)
    private RefreshCache[]               refreshCaches;

    @Value("${syspower.powermenu.viewname}")
    private String                       powerMenuViewName;



	@Override
	public SimpleDomainRepository<SysRoleModule, String> getRepository() {
		return this.repository;
	}

	@Override
	public void updateRoleModule(List<ModuleShowBean> beanList){
		Long roleId = beanList.get(0).getRoleId();
		List<SysRoleModule> roleModuleList = this.repository.findByRoleId(roleId);//找出当前角色所有关联模块
		for(ModuleShowBean bean:beanList){
			boolean flag = true;
			for(SysRoleModule roleModule:roleModuleList){
				if(roleModule.getModuleId().equals(bean.getModuleId())){
					BeanUtils.copyProperties(bean, roleModule);//主要复制已选操作operates
					flag = false;
				}
			}
			if(flag){//加入之前没有的关联
				SysRoleModule rm = new SysRoleModule();
				BeanUtils.copyProperties(bean, rm);
				roleModuleList.add(rm);
			}
		}
		this.repository.save(roleModuleList);
		//this.updatePower(roleId);

		//没有勾选操作的，删除与角色的关联，方便删除模块
		List<SysRoleModule> rmList = this.repository.findAll();
		List<SysRoleModule> rmDelList = new ArrayList<SysRoleModule>();
		for(SysRoleModule rm: rmList){
			if(StringUtils.isBlank(rm.getOperates())){
				rmDelList.add(rm);
			}
		}
		this.repository.delete(rmDelList);
	}

	@Override
	public SysRole updatePower(Long roleId){
		SysRole role = this.sysRoleService.findOne(roleId);
		Set<SysPower> powerSet = role.getPowers();
		Set<SysPower> powerIsMuneSet = new HashSet<SysPower>();//当前角色已拥有的，除了菜单以外所有的SysPower都不要
		for(SysPower p:powerSet){
			if(YesNo.是.equals(p.getIsMenu())){
				powerIsMuneSet.add(p);
			}
		}
		Set<SysPower> sysPowerSaveList = new HashSet<SysPower>();//要保存的权限
		List<SysRoleModule> roleModules = this.repository.findByRoleId(roleId);

		List<SysPower> powerList = this.sysPowerService.getRepository().findAll();
		for(SysPower p:powerList){
			if(StringUtils.isBlank(p.getUrl())){
				continue;
			}
			String url = p.getUrl().toLowerCase();
			Long moduleId = null;
			//根据moduleKey匹配 && 根据url规则匹配
			if(ModuleUtils.matchURL(url, roleModules, moduleId)){
				sysPowerSaveList.add(p);
			}
		}

		sysPowerSaveList.addAll(powerIsMuneSet);
		role.setPowers(sysPowerSaveList);
		return this.sysRoleService.getRepository().save(role);
	}

	/**
	 * 刷新缓存
	 */
	public void refreshCaches(HttpServletRequest request, HttpServletResponse response){

		if (null != refreshCaches) {
            for (RefreshCache rc : refreshCaches) {
                rc.refreshCache();
            }
        }
	}
	
	@Override
	public SysRoleModule findByModuleIdAndRoleId(Long moduleId, Long roleId){
		return this.repository.findByModuleIdAndRoleId(moduleId, roleId);
	}
}
