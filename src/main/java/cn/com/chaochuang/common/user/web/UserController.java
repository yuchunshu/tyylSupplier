package cn.com.chaochuang.common.user.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.EasyUIPage;
import cn.com.chaochuang.common.bean.Result;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.syspower.service.SysRoleService;
import cn.com.chaochuang.common.user.bean.UserEditBean;
import cn.com.chaochuang.common.user.bean.UserShowBean;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysUserRepository;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.user.service.SysDutyService;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchListHelper;

@Controller
@RequestMapping("system/user")
public class UserController {

    @Autowired
    private SysUserService       uservice;

    @Autowired
    private SysDepartmentService deptService;

    @Autowired
    private SysRoleService       roleService;

    @Autowired
    private SysUserRepository    uRepository;

    @Autowired
    private LogService           logService;

    @Autowired
    protected ConversionService  conversionService;

    @Autowired
    protected SysDutyService     dutyService;

    @Value(value = "${super.admin.role}")
    private String               superRoleId;

    @Value(value = "${super.admin.user}")
    private String               superUserId;

    @RequestMapping("/list")
    public ModelAndView list(Long depId) {
        ModelAndView mav = new ModelAndView("/system/user/list");
        if (depId != null) {
            mav.addObject("depId", depId);
        }
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public EasyUIPage listjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<SysUser, Long> searchBuilder = new SearchBuilder<SysUser, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        if (!UserTool.getInstance().getCurrentUserId().equals(this.superUserId)) {
            searchBuilder.getFilterBuilder().notEqual("id", this.superUserId);
        }
        searchBuilder.appendSort(Direction.ASC, "department.orderNum");
        searchBuilder.appendSort(Direction.ASC, "sort");
        searchBuilder.appendSort(Direction.DESC, "id");

        SearchListHelper<SysUser> listhelper = new SearchListHelper<SysUser>();
        listhelper.execute(searchBuilder, uservice.getRepository(), page, rows);

        EasyUIPage p = new EasyUIPage();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), UserShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public Result del(Long id, HttpServletRequest request) {
        this.uservice.delUser(id);

        logService.saveLog(SjType.部门用户, "删除用户：流水号/" + id,LogStatus.成功, request);
        return new Result("success", null);
    }

    @RequestMapping("/new")
    public ModelAndView newEntity(Long depId) {
        ModelAndView mav = new ModelAndView("/system/user/edit");
        mav.addObject("detpList", this.deptService.findAllExceptTopUnit());
        mav.addObject("depId", depId);
        mav.addObject("roles", this.roleService.findAll());
        mav.addObject("dutys", this.dutyService.findAll());
        mav.addObject("type", "new");
        return mav;
    }

    @RequestMapping("/edit")
    public ModelAndView edit(Long id, String model) {
        ModelAndView mav = new ModelAndView("/system/user/edit");
        SysUser user = uservice.findOne(id);
        mav.addObject("obj", user);
        mav.addObject("dutys", this.dutyService.findAll());
        if (user != null) {
            mav.addObject("roles", this.roleService.getRolesAndChecked(user.getRoles()));
        }
        mav.addObject("model", model);
        return mav;
    }

    @RequestMapping("/editInfo")
    public ModelAndView editInfo() {
        ModelAndView mav = new ModelAndView("/system/user/editInfo");
        String id = UserTool.getInstance().getCurrentUserId();
        if (StringUtils.isNotBlank(id)) {
            mav.addObject("obj", uservice.findOne(Long.valueOf(id)));
        }
        return mav;
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo update(UserEditBean u, HttpServletRequest request, HttpServletResponse response) {
        Long id = uservice.saveUser(u, request);
        if (id != null) {
            if (u.getId() == null) {
                logService.saveLog(SjType.部门用户, "新增用户：" + u.getUserName() + ", 登录名：" + u.getAccount(),LogStatus.成功, request);
            } else {
                logService.saveLog(SjType.部门用户, "修改用户：" + u.getUserName(),LogStatus.成功, request);
            }
            return new ReturnInfo(id.toString(), null, u.getDeptId().toString());
        } else {
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/saveInfo.json")
    @ResponseBody
    public Result updateInfo(UserEditBean u, HttpServletRequest request, HttpServletResponse response) {
        String strCurUserId = UserTool.getInstance().getCurrentUserId();
        if (strCurUserId != null && Long.valueOf(strCurUserId).equals(u.getId())) {
            if (uservice.saveUser(u, request) != null) {
                return new Result("success", null);
            } else {
                return new Result("error", "服务器连接不上！");
            }
        } else {
            return new Result("error", "服务器连接不上！");
        }
    }

    @RequestMapping("/checkAccount.json")
    @ResponseBody
    public Boolean accountCheck(Long id, String account) {
        List<SysUser> ul = uRepository.findByAccount(account);
        SysUser u = null;
        if (ul != null && ul.size() > 0) {
            u = ul.get(0);
        }
        if (id != null) {
            if (u != null && !(u.getId().equals(id))) {
                return false;
            }
        } else {
            if (u != null) {
                return false;
            }
        }

        return true;

    }

    // 被锁定用户列表
    @RequestMapping("/locklist")
    public ModelAndView locklist() {
        ModelAndView mav = new ModelAndView("/system/user/locklist");
        return mav;
    }

    @RequestMapping("/listLock.json")
    @ResponseBody
    public EasyUIPage locklistjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<SysUser, Long> searchBuilder = new SearchBuilder<SysUser, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("accountLocked", Boolean.TRUE); // 被锁定的
        searchBuilder.appendSort(Direction.DESC, "id");

        SearchListHelper<SysUser> listhelper = new SearchListHelper<SysUser>();
        listhelper.execute(searchBuilder, uservice.getRepository(), page, rows);

        EasyUIPage p = new EasyUIPage();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), UserShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    // 解锁
    @RequestMapping("/dealUnLock.json")
    @ResponseBody
    public ReturnInfo unlock(String ids, HttpServletRequest request) {
        try {
            this.uservice.unLockedUser(ids, request);
            return new ReturnInfo("1", null, "解锁成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
}
