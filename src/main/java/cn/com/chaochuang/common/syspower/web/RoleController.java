/*
 * FileName:    RoleController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月6日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.syspower.web;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.select.bean.UserSelectShowBean;
import cn.com.chaochuang.common.syspower.bean.DepAndUserTreeBean;
import cn.com.chaochuang.common.syspower.bean.RoleInfo;
import cn.com.chaochuang.common.syspower.bean.RoleShowBean;
import cn.com.chaochuang.common.syspower.domain.SysPower;
import cn.com.chaochuang.common.syspower.domain.SysRole;
import cn.com.chaochuang.common.syspower.repository.SysRoleRepository;
import cn.com.chaochuang.common.syspower.service.SysPowerService;
import cn.com.chaochuang.common.syspower.service.SysRoleService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchListHelper;

/**
 * @author LJX
 *
 */
@Controller
@RequestMapping("system/role")
public class RoleController {

    @Autowired
    private SysRoleService      sysRoleService;

    @Autowired
    protected ConversionService conversionService;

    @Autowired
    private SysRoleRepository   sysRoleRepository;

    @Autowired
    private SysPowerService     sysPowerService;

    @Autowired
    private SysUserService      sysUserService;

    @Autowired
    private LogService          logService;

    @Value(value = "${super.admin.role}")
    private String              superRoleId;

    @Value(value = "${super.admin.user}")
    private String              superUserId;

    @RequestMapping("/roleList")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("system/role/roleList");
        return mav;
    }

    @RequestMapping("/listRole.json")
    @ResponseBody
    public Page roleList(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<SysRole, Long> searchBuilder = new SearchBuilder<SysRole, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.appendSort(Direction.ASC, "orderNum");
        if (!UserTool.getInstance().getCurrentUserId().equals(this.superUserId)) {
            searchBuilder.getFilterBuilder().notEqual("id", this.superRoleId);
        }
        SearchListHelper<SysRole> listhelper = new SearchListHelper<SysRole>();
        listhelper.execute(searchBuilder, sysRoleService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), RoleShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/editRole")
    public ModelAndView edit(Long roleId) {
        ModelAndView mav = new ModelAndView("system/role/roleEdit");
        if (roleId != null) {
            mav.addObject("role", this.sysRoleService.findOne(roleId));
        }
        return mav;
    }

    @RequestMapping({"/editUserRole"})
    public ModelAndView userRoleEdit(Long roleId) {
      ModelAndView mav = new ModelAndView("system/role/userRoleEdit");
      if (roleId != null) {
        mav.addObject("role", this.sysRoleService.findOne(roleId));
        mav.addObject("peopleSelectList",this.sysRoleService.getUsersByRoleId(roleId));
        
      }
      return mav;
    }
    
    @RequestMapping("/linkPower")
    public ModelAndView linkPower(Long roleId, Long appId) {
        ModelAndView mav = new ModelAndView("system/role/linkPower");
        if (roleId != null) {
            // mav.addObject("role", this.sysRoleService.findOne(roleId));
            mav.addObject("powers", this.sysRoleService.powerIds(roleId));
        }
        mav.addObject("roleId", roleId);
        mav.addObject("appId", appId);
        return mav;
    }

    @RequestMapping("/checkRoleName.json")
    public @ResponseBody boolean checkRoleName(String roleName, Long id) {
        Boolean flag = false;
        if (StringUtils.isNotBlank(roleName)) {
            if (this.sysRoleRepository.findByRoleName(roleName) == null
                            || this.sysRoleRepository.findByRoleName(roleName).size() <= 0) {
                flag = true;
            }
        }
        if (id != null) {
            SysRole r = sysRoleService.findOne(id);
            if (r != null && r.getRoleName().equals(roleName)) {
                flag = true;
            }
        }
        return flag;
    }

    @RequestMapping("/saveRole.json")
    public @ResponseBody ReturnInfo saveRole(RoleInfo roleBean, HttpServletRequest request) {
        try {
            Long roleId = this.sysRoleService.saveRole(roleBean);
            logService.saveLog(SjType.角色权限, "保存角色：流水号/" + roleId + "，角色名/" + roleBean.getRoleName(),LogStatus.成功, request);
            return new ReturnInfo(roleId.toString(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }

    }

    @RequestMapping({"/saveUserRole.json"})
    @ResponseBody
    public ReturnInfo saveUserRole(RoleInfo roleBean, HttpServletRequest request)
    {
      try {
        if (roleBean.getId() != null) {
          SysRole r = (SysRole)this.sysRoleService.findOne(roleBean.getId());
          
          Collection<SysUser> us = this.sysUserService.findUsers(roleBean.getUserIds());
          
          if (us != null) {
            Set<SysUser> u = new HashSet();
            u.addAll(us);
            r.setUsers(u);
          }
          
          this.sysRoleService.persist(r);
          this.logService.saveLog(SjType.角色权限, "保存角色——权限关联：流水号/" + roleBean.getId() + "，角色名/" + r.getRoleName(),LogStatus.成功, request);
        }
        
        return new ReturnInfo(roleBean.getId().toString(), null, null);
      } catch (Exception e) {
        e.printStackTrace(); }
      return new ReturnInfo(null, "服务器连接不上！", null);
    }
    
    @RequestMapping({"/selectUsersByRoleId.json"})
    @ResponseBody
    public List<UserSelectShowBean> selectUsersByRoleId(Long roleId) { 
    	return this.sysRoleService.getUsersByRoleId(roleId); 
    	}
    
    @RequestMapping("/saveLinkPower.json")
    public @ResponseBody ReturnInfo saveLinkPower(Long roleId, Long[] powerIds, HttpServletRequest request) {
        try {
            if (null != roleId) {
                SysRole role = sysRoleService.findOne(roleId);

                Collection<SysPower> ps = sysPowerService.findPowers(powerIds);

                Set<SysPower> rps = role.getPowers();

                rps.clear();

                if (null != ps) {
                    rps.addAll(ps);
                }
                sysRoleService.persist(role);
                logService.saveLog(SjType.角色权限, "保存角色——权限关联：流水号/" + roleId + "，角色名/" + role.getRoleName(),LogStatus.成功, request);

            }
            return new ReturnInfo(roleId.toString(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }

    }

    @RequestMapping("/deleteRole.json")
    public @ResponseBody ReturnInfo deleteRole(Long roleId, HttpServletRequest request) {
        try {
            String errMsg = this.sysRoleService.deleteRole(roleId);
            if (StringUtils.isBlank(errMsg)) {
                logService.saveLog(SjType.角色权限, "删除角色：流水号/" + roleId,LogStatus.成功, request);
            }
            return new ReturnInfo(null, errMsg, null);
        } catch (Exception e) {
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/linkUser.json")
    public @ResponseBody List<DepAndUserTreeBean> roleUserList(Long roleId, Long parentId) {
        return this.sysUserService.roleUsers(roleId);
    }

    @RequestMapping("/saveLinkUser.json")
    public @ResponseBody ReturnInfo saveLinkUser(Long roleId, Long[] userIds, HttpServletRequest request) {
        try {
            if (null != roleId) {
                SysRole role = this.sysRoleService.findOne(roleId);

                Collection<SysUser> ps = sysUserService.findUsers(userIds);

                Set<SysUser> rps = role.getUsers();
                rps.clear();
                if (null != ps) {
                    rps.addAll(ps);
                }
                this.sysRoleService.persist(role);
                logService.saveLog(SjType.角色权限, "保存角色——用户关联：流水号/" + roleId + "，角色名/" + role.getRoleName(),LogStatus.成功, request);
            }
            return new ReturnInfo(roleId.toString(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

}
