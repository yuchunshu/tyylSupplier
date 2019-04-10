package cn.com.chaochuang.common.syspower.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.syspower.bean.ModuleShowBean;
import cn.com.chaochuang.common.syspower.domain.SysPowerModule;
import cn.com.chaochuang.common.syspower.domain.SysRoleModule;
import cn.com.chaochuang.common.syspower.repository.SysPowerModuleRepository;
import cn.com.chaochuang.common.syspower.repository.SysRoleModuleRepository;
/**
 * @author RDX
 */
@Service
@Transactional
public class SysPowerModuleServiceImpl extends SimpleLongIdCrudRestService<SysPowerModule> implements SysPowerModuleService {

    @Autowired
    private SysPowerModuleRepository repository;
    
    @Autowired
    private SysRoleModuleRepository  roleModuleRepository;
    
    @PersistenceContext
    private EntityManager            em;

	@Override
	public SimpleDomainRepository<SysPowerModule, Long> getRepository() {
		return this.repository;
	}
	
	@Override
	public List<ModuleShowBean> getAllModuleByRoleId(Long roleId){
		if(roleId == null){
			return null;
		}
		List<ModuleShowBean> moduleList = new ArrayList<ModuleShowBean>();
		List<SysRoleModule> roleModuleList = this.roleModuleRepository.findByRoleId(roleId);
		List<SysPowerModule> powerModuleList = this.repository.findAll();
		for(SysPowerModule p:powerModuleList){//联合查询，组成showBean
			ModuleShowBean m = new ModuleShowBean();
			m.setModuleId(p.getId());
			m.setModuleName(p.getModuleName());
			m.setModuleKey(p.getModuleKey());
			
			for(SysRoleModule r:roleModuleList){
				if(p.getId().equals(r.getModuleId())){
					m.setOperates(r.getOperates());
					m.setRoleId(m.getRoleId());
					break;
				}
			}
			moduleList.add(m);
		}
		return moduleList;
	}
	
	@Override
	public SysPowerModule updatePowerModule(SysPowerModule powerModule){
		if(powerModule == null){
			return null;
		}
		SysPowerModule pm = null;
		if(powerModule.getId() != null){//修改
			pm = this.findOne(powerModule.getId());
			BeanUtils.copyProperties(powerModule, pm);
			pm.setId(powerModule.getId());
		}else{//新增
			pm = powerModule;
		}
		return this.repository.save(pm);
	}
	
	@Override
	public void deletePowerModule(SysPowerModule powerModule){
		if(powerModule != null && powerModule.getId() != null){
			this.repository.delete(powerModule.getId());
		}
	}
	
	@Override
	public List<String> getModuleSelectBox(){
		List<SysPowerModule> pms = this.repository.findAll();
		if(pms == null){
			return null;
		}
		List<String> selectList = new ArrayList<String>();
		for(SysPowerModule pm:pms){
			if(StringUtils.isNotBlank(pm.getModuleName())){
				selectList.add(pm.getModuleName());
			}
		}
		return selectList;
	}
}
