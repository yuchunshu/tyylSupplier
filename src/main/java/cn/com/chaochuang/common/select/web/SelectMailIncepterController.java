/*
 * FileName:    SelectMailIncepterController.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年3月11日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.select.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.select.bean.UserSelectShowBean;
import cn.com.chaochuang.common.user.bean.DepartmentSelectShowBean;
import cn.com.chaochuang.common.user.bean.DepartmentTreeShowBean;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.oa.personal.domain.OaPersonalGroup;
import cn.com.chaochuang.oa.personal.service.OaPersonalGroupService;

/**
 * @author HM
 *
 */
@Controller
@RequestMapping("/select/incepters")
public class SelectMailIncepterController {

    @Autowired
    private SysUserService         uService;

    @Autowired
    private SysDepartmentService   depService;

    @Autowired
    private OaPersonalGroupService personalGroupService;

    @Autowired
    protected ConversionService    conversionService;

    // @RequestMapping("/selectMan")
    // public ModelAndView selectMan(String userIds) {
    // ModelAndView mav = new ModelAndView("/selectdialog/selectmansformail");
    // if (StringUtils.isNotBlank(userIds)) {
    // mav.addObject("peopleSelectUrl", "/select/incepters/getUsers.json?userIds=" + userIds);
    // }
    // mav.addObject("selectDialogTitle", "选择收件人");
    // return mav;
    // }
    //
    // @RequestMapping("/selectMansByDep.json")
    // @ResponseBody
    // public Page selectManByDepJson(Integer page, Integer rows, HttpServletRequest request) {
    // SearchBuilder<SysUser, Long> searchBuilder = SearchBuilderHelper.bindSearchBuilder(conversionService, request);
    // searchBuilder.clearSearchBuilder().findSearchParam(request);
    //
    // SearchListHelper<SysUser> listhelper = new SearchListHelper<SysUser>();
    // listhelper.execute(searchBuilder, uService.getRepository(), page, rows);
    //
    // Page p = new Page();
    // p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), UserSelectShowBean.class));
    // p.setTotal(listhelper.getCount());
    // return p;
    // }

    @RequestMapping("/selectGroup")
    public ModelAndView selectGroup(String userIds) {
        ModelAndView mav = new ModelAndView("/selectdialog/selectgroupsformail");
        if (StringUtils.isNotBlank(userIds)) {
            mav.addObject("peopleSelectUrl", "/select/incepters/getUsers.json?userIds=" + userIds);
        }
        mav.addObject("selectDialogTitle", "选择收件人");
        return mav;
    }

    @RequestMapping("/selectGroup.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<OaPersonalGroup, Long> searchBuilder = new SearchBuilder<OaPersonalGroup, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        searchBuilder.getFilterBuilder().equal("user", user);

        searchBuilder.appendSort(Direction.ASC, "orderNum");
        SearchListHelper<OaPersonalGroup> listhelper = new SearchListHelper<OaPersonalGroup>();
        listhelper.execute(searchBuilder, personalGroupService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(this.personalGroupService.getShowList(listhelper.getList()));
        p.setTotal(listhelper.getCount());

        return p;
    }

    @RequestMapping("/selectDepMan")
    public ModelAndView selectDepMan(String userIds) {
        ModelAndView mav = new ModelAndView("/selectdialog/selectdepmansformail");
        if (StringUtils.isNotBlank(userIds)) {
            mav.addObject("peopleSelectUrl", "/select/incepters/getUsers.json?userIds=" + userIds);
        }
        mav.addObject("selectDialogTitle", "选择收件人");
        return mav;
    }

    @RequestMapping("/selectDepTree.json")
    @ResponseBody
    public List<DepartmentTreeShowBean> selectDepTreeJson(HttpServletRequest request) {
        return this.depService.getDepartmentTreeOnlyThreeLayers(null);
    }

    @RequestMapping("/getSubDeps.json")
    @ResponseBody
    public Page getSubDepsJson(Long parentId, String containDeps, Integer page, Integer rows, HttpServletRequest request) {
        Page p = new Page();
        List<DepartmentSelectShowBean> returnList = null;
        returnList = this.depService.getSubDepSelectBeans(parentId, containDeps);
        if (returnList != null) {
            p.setRows(returnList);
            p.setTotal((long) returnList.size());
        }
        return p;
    }

    @RequestMapping("/getUserByDep.json")
    @ResponseBody
    public Page selectUsersByDep(Long depId) {

        List<SysUser> uList = null;
        if (depId != null) {
            uList = this.uService.findBydetpId(depId);
        }
        if (uList == null) {
            uList = new ArrayList<SysUser>();
        }
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(uList, UserSelectShowBean.class));
        p.setTotal((long) uList.size());
        return p;
    }

    @RequestMapping("/getUserByGroup.json")
    @ResponseBody
    public Page selectUsersByGroup(Long groupId) {

        List<SysUser> uList = null;
        if (groupId != null) {
            uList = this.personalGroupService.getUsersByGroupId(groupId);
        }
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(uList, UserSelectShowBean.class));
        p.setTotal((long) uList.size());
        return p;
    }

    // 查找多个部门的人员
    @RequestMapping("/getUserByAllDep.json")
    @ResponseBody
    public Page selectDepsUsers(String depIds) {

        List<SysUser> uList = new ArrayList<SysUser>();
        if (StringUtils.isNotBlank(depIds)) {
            String[] idArr = depIds.split(",");
            for (int i = 0; i < idArr.length; i++) {
                List<SysUser> users = this.uService.findBydetpId(Long.valueOf(idArr[i]));
                if (users != null && users.size() > 0) {
                    uList.addAll(users);
                }
            }
        }
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(uList, UserSelectShowBean.class));
        p.setTotal((long) uList.size());
        return p;
    }

    @RequestMapping("/getUsers.json")
    @ResponseBody
    public Page getUsersJson(String userIds, Integer page, Integer rows, HttpServletRequest request) {

        List<SysUser> uList = this.uService.getUsersByIdsGroupByDep(userIds);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(uList, UserSelectShowBean.class));
        p.setTotal((long) uList.size());
        return p;
    }

}
