package cn.com.chaochuang.common.syspower.web;

import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.data.bean.SearchResult;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.data.web.SimpleCrudController;
import cn.com.chaochuang.common.syspower.domain.SysPower;
import cn.com.chaochuang.common.syspower.domain.SysRole;
import cn.com.chaochuang.common.syspower.service.SysPowerService;
import cn.com.chaochuang.common.syspower.service.SysRoleService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;

@Controller
@RequestMapping("sysrole")
public class SysRoleController extends SimpleCrudController<SysRole, SysRoleService> {

    @Autowired
    private SysPowerService sysPowerService;

    @Autowired
    private SysUserService  sysUserService;

    @RequestMapping(value = "/detail", params = { "act" })
    public ModelAndView showSavePower(Long id, String act, HttpServletRequest request) {
        ModelAndView mav = detail(id);

        mav.addObject("act", act);
        if ("power".equals(act)) {
            mav.addObject("page", sysPowerService.loadPowerNeed());
        } else if ("user".equals(act)) {
            mav.addObject("page", sysUserService.loadAllActiveUsers());
        }

        return mav;
    }

    @RequestMapping(value = "/save", params = { "act=power" })
    public ModelAndView savePower(Long roleid, Long[] powerids, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("redirect:list");

        if (null != roleid) {
            SysRole role = getCrudRestService().findOne(roleid);

            Collection<SysPower> ps = sysPowerService.findPowers(powerids);

            Set<SysPower> rps = role.getPowers();

            rps.clear();

            if (null != ps) {
                rps.addAll(ps);
            }

            getCrudRestService().persist(role);

        }

        return mav;
    }

    @RequestMapping(value = "/save", params = { "act=user" })
    public ModelAndView saveUser(Long roleid, Long[] userids, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("redirect:list");

        if (null != roleid) {
            SysRole role = getCrudRestService().findOne(roleid);

            Collection<SysUser> ps = sysUserService.findUsers(userids);

            Set<SysUser> rps = role.getUsers();

            rps.clear();

            if (null != ps) {
                rps.addAll(ps);
            }

            getCrudRestService().persist(role);

        }

        return mav;
    }

    @Override
    protected SearchResult beforeList(ModelAndView mav, Integer pageNum, Integer numPerPage, String orderField,
                    String orderDirection, SearchBuilder<SysRole, Long> searchBuilder, HttpServletRequest request,
                    Boolean isPageBreak) {
        searchBuilder.appendSort("orderNum");
        return super.beforeList(mav, pageNum, numPerPage, orderField, orderDirection, searchBuilder, request,
                        isPageBreak);
    }

    @Override
    protected boolean isPageBreakForList() {
        return false;
    }

}
