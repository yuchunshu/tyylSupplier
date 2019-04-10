package cn.com.chaochuang.common.syspower.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.select.bean.UserSelectShowBean;
import cn.com.chaochuang.common.syspower.bean.RoleCheckedBean;
import cn.com.chaochuang.common.syspower.bean.RoleInfo;
import cn.com.chaochuang.common.syspower.domain.SysPower;
import cn.com.chaochuang.common.syspower.domain.SysRole;
import cn.com.chaochuang.common.syspower.reference.IsMenu;
import cn.com.chaochuang.common.syspower.repository.SysRoleRepository;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysDepartmentRepository;
import cn.com.chaochuang.common.user.repository.SysUserRepository;
import cn.com.chaochuang.common.user.service.SysUserService;

@Service
@Transactional
public class SysRoleServiceImpl extends SimpleLongIdCrudRestService<SysRole> implements SysRoleService {

    @Autowired
    private SysRoleRepository       repository;

    @Autowired
    private SysDepartmentRepository depRepository;

    @Autowired
    private SysUserService          sysUserService;

    @Autowired
    private SysUserRepository       userRepository;

    @Autowired
    private SysPowerService         sysPowerService;

    @Value(value = "${super.admin.role}")
    private Long                    superRoleId;

    @Value(value = "${super.admin.user}")
    private String                  superUserId;

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.data.service.CrudRestService#getRepository()
     */
    @Override
    public SimpleDomainRepository<SysRole, Long> getRepository() {
        return repository;
    }

    @CacheEvict(value = "UserPowerCache", allEntries = true)
    public void persist(SysRole e) {
        super.persist(e);
    }

    @CacheEvict(value = "UserPowerCache", allEntries = true)
    public void merge(SysRole e) {
        super.merge(e);
    }

    @CacheEvict(value = "UserPowerCache", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @CacheEvict(value = "UserPowerCache", allEntries = true)
    public void delete(SysRole entity) {
        super.delete(entity);
    }

    @Override
    @CacheEvict(value = "UserPowerCache", allEntries = true)
    public Long saveRole(RoleInfo roleBean) {

        SysRole r = new SysRole();
        if (roleBean.getId() != null) {
            r = this.findOne(roleBean.getId());

            // 只有超级管理员可以修改超级管理员的角色
            if (r.getId().equals(this.superRoleId)
                            && !UserTool.getInstance().getCurrentUserId().equals(this.superUserId)) {
                return r.getId();
            }

        }

        r.setRoleName(roleBean.getRoleName());
        r.setOrderNum(roleBean.getOrderNum());
        r.setRemark(roleBean.getRemark());
        
        //取出非菜单power
        Set<SysPower> isNotMenuePowers = new HashSet<SysPower>();
        if(r.getPowers() != null){
        	for(SysPower p:r.getPowers()){
            	if(IsMenu.否.equals(p.getIsMenu())){
            		isNotMenuePowers.add(p);
            	}
            }
        }
        
        //r.setUsers(null);
        r.setPowers(null);
        
        Collection<SysPower> ps = sysPowerService.findPowers(roleBean.getPowerIds());

        if (null != ps) {
            Set<SysPower> p = new HashSet<SysPower>();
            p.addAll(ps);
            p.addAll(isNotMenuePowers);
            r.setPowers(p);
        }
        

//        Collection<SysUser> us = sysUserService.findUsers(roleBean.getUserIds());
//
//        if (null != us) {
//            Set<SysUser> u = new HashSet<SysUser>();
//            u.addAll(us);
//            r.setUsers(u);
//        }

        this.persist(r);

        return r.getId();
    }

    @Override
    public String deleteRole(Long roleId) {
        if (roleId != null) {
            SysRole r = this.findOne(roleId);
            if (r.getUsers() != null && r.getUsers().size() > 0) {
                return "该角色关联有人员，不能删除！";
            }
            if (r.getPowers() != null && r.getPowers().size() > 0) {
                return "该角色关联有权限，不能删除！";
            }
            this.delete(r);
        }
        return null;
    }

    @Override
    public String powerIds(Long roleId) {
        if (roleId != null) {
            SysRole role = this.findOne(roleId);
            if (role != null && role.getPowers() != null && role.getPowers().size() > 0) {
                String value = "";
                for (SysPower p : role.getPowers()) {
                    value += p.getId() + ",";
                }
                if (StringUtils.isNotBlank(value)) {
                    return value.substring(0, value.length() - 1);
                }
            }
        }
        return null;
    }

    @Override
    public List<RoleCheckedBean> getRolesAndChecked(Set<SysRole> userRoles) {
        List<RoleCheckedBean> returnList = new ArrayList<RoleCheckedBean>();
        List<SysRole> allRoles = this.repository.findAll();
        if (allRoles != null && allRoles.size() > 0) {
            for (SysRole role : allRoles) {
                RoleCheckedBean bean = new RoleCheckedBean();
                bean.setId(role.getId());
                bean.setRoleName(role.getRoleName());
                if (userRoles != null && userRoles.contains(role)) {
                    bean.setChecked(true);
                } else {
                    bean.setChecked(false);
                }
                returnList.add(bean);
            }
        }
        return returnList;
    }

    @Override
    public List<Long> getRoleIdListByUserId(Long userId) {
        List<Long> idList = new ArrayList<Long>();
        if (userId != null) {
            List<SysRole> roles = repository.findByUsersId(userId);
            if (roles != null && roles.size() > 0) {
                for (SysRole r : roles) {
                    idList.add(r.getId());
                }
            }
        }
        return idList;
    }
    
    public List<UserSelectShowBean> getUsersByRoleId(Long roleId)
    {
      List<UserSelectShowBean> list = new ArrayList();
      SysRole role = null;
      if (roleId != null) {
        role = (SysRole)findOne(roleId);
      }
      Set<SysUser> users = new HashSet();
      if ((role != null) && (role.getUsers() != null)) {
        users = role.getUsers();
      }
      
      for (SysUser str : users) {
        UserSelectShowBean usb = new UserSelectShowBean();
        usb.setId((Long)str.getId());
        usb.setUserName(str.getUserName());
        list.add(usb);
      }
      
//      List<UserSelectShowBean> list1 = new ArrayList();
//      for(int i = 0;i<=list.size()/3;i++){
//    	  UserSelectShowBean usb = list.get(i*3);
//    	  usb.setUserName(usb.getUserName());
//    	  
//    	  if(i*3+1 <list.size()){
//    		  UserSelectShowBean usb1 = list.get(i*3+1);
//    		  usb.setUserName1(usb1.getUserName());
//    	  }else{
//    		  usb.setUserName1("");
//    	  }
//    	  
//    	  if(i*3+2 <list.size()){
//    		  UserSelectShowBean usb2 = list.get(i*3+2);
//    		  usb.setUserName2(usb2.getUserName());
//    	  }else{
//    		  usb.setUserName2("");
//    	  }
//     
//    	  list1.add(usb);
//      }
      
      return list;
    }
}
