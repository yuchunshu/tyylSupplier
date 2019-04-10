package cn.com.chaochuang.common.syspower.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.com.chaochuang.common.power.PowerUtil;
import cn.com.chaochuang.common.syspower.domain.SysPower;
import cn.com.chaochuang.common.syspower.domain.SysPowerModule;
import cn.com.chaochuang.common.syspower.domain.SysRole;
import cn.com.chaochuang.common.syspower.domain.SysRoleModule;
import cn.com.chaochuang.common.syspower.reference.PowerType;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.ModuleUtils;

@Service
@CacheConfig(cacheNames = "UserPowerCache")
public class UserPowerServiceImpl implements UserPowerService {

    @Autowired
    private SysUserService            sysUserService;

    @Autowired
    private SysPowerService           sysPowerService;
    
    @Autowired
    private SysPowerModuleService     powerModuleService;
    
    @Autowired
    private SysRoleModuleService      roleModuleService;

    private Map<String, List<String>> defaultRoleNamePowerUrlMap = Maps.newConcurrentMap();

    @Resource(name = "defaultRoleNamePowerUrlMap")
    @CacheEvict(allEntries = true)
    public void setDefaultRoleNamePowerUrlMap(Map<String, List<String>> defaultRoleNamePowerUrlMap) {
        Map<String, List<String>> result = Maps.newConcurrentMap();
        if (MapUtils.isNotEmpty(defaultRoleNamePowerUrlMap)) {
            for (String key : defaultRoleNamePowerUrlMap.keySet()) {
                List<String> goodList = Lists.newArrayList();
                for (String url : defaultRoleNamePowerUrlMap.get(key)) {
                    goodList.add(PowerUtil.standPowerUrl(url));
                }
                result.put(key, goodList);
            }
        }
        this.defaultRoleNamePowerUrlMap = result;
    }

    private void powerUrlToSet(final Set<String> powerUrlSet, final Collection<SysPower> ps) {
        for (SysPower power : ps) {
            if (StringUtils.isNotBlank(power.getUrl()))
                powerUrlSet.add(power.getUrl());
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.syspower.service.UserPowerService#userPowerUrlSet(java.lang.String)
     */
    @Override
    @Cacheable
    public Set<String> userPowerUrlSet(String userId) {
        SysUser user = sysUserService.findOne(Long.parseLong(userId));
        if (null != user) {
            Set<String> powerUrlSet = new HashSet<String>();

            for (SysRole role : user.getRoles()) {
                powerUrlToSet(powerUrlSet, role.getPowers());
                if (defaultRoleNamePowerUrlMap.containsKey(role.getRoleName())) {
                    powerUrlSet.addAll(defaultRoleNamePowerUrlMap.get(role.getRoleName()));
                }
            }

            powerUrlToSet(powerUrlSet, sysPowerService.findByPowerType(PowerType.公用授权));
            powerUrlToSet(powerUrlSet, sysPowerService.findByPowerType(PowerType.不用授权));

            return powerUrlSet;

        }
        return null;
    }

    public Map<String, List<String>> getDefaultRoleNamePowerUrlMap() {
        return Collections.unmodifiableMap(defaultRoleNamePowerUrlMap);
    }
    
    @Override
    public boolean validateUrlPower(String url, String userIdStr){
    	Long userId = null;
    	try{
    		userId = Long.valueOf(userIdStr);
    	}catch(NumberFormatException e){
    		return false;
    	}
    	
    	//获取用户角色
    	SysUser user = this.sysUserService.findOne(userId);
    	Set<SysRole> roles = user.getRoles();
    	if(roles == null){
    		return false;
    	}
    	
    	//匹配URL模块key
    	List<SysPowerModule> list = this.powerModuleList();
    	Long moduleId = ModuleUtils.moduleKey(url,list);
    	if(moduleId == null){
    		return false;
    	}
    	
    	//匹配URL具体规则
    	boolean havePower = false;
    	for(SysRole role:roles){
    		Long roleId = role.getId();
    		SysRoleModule rm = this.roleModuleService.findByModuleIdAndRoleId(moduleId, roleId);
    		
    		if(rm == null || rm.getOperates() == null){
    			continue;
    		}
    		if(ModuleUtils.validateUrloperatesPower(url, rm.getOperates())){
    			havePower = true;
    		}
    	}
    	if(havePower){
    		return true;
    	}
    	return false;
    }
    
    @Override
    @Cacheable
    public List<SysPowerModule> powerModuleList() {
    	return this.powerModuleService.getRepository().findAll();
    }

}
