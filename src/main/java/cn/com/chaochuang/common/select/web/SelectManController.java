/*
 * FileName:    SelectManController.java
 * Description:
 * Company:     
 * Copyright:    (c) 2015
 * History:     2015年1月14日 (LaoZhiYong) 1.0 Create
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
import cn.com.chaochuang.common.select.bean.UserSelectShowBean;
import cn.com.chaochuang.common.select.bean.UserSelectedListBean;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchBuilderHelper;
import cn.com.chaochuang.common.util.SearchListHelper;

/**
 * @author LaoZhiYong
 *
 */
@Controller
@RequestMapping("/select")
public class SelectManController {

    @Autowired
    private SysUserService       userService;

    @Autowired
    private SysDepartmentService depService;

    @Autowired
    protected ConversionService  conversionService;

    @RequestMapping("/selectmansClassify")
    public ModelAndView selectManMulti(String userIds) {
        ModelAndView mav = new ModelAndView("/selecttemplate/selectdialogtemplate");
        // 取数据的url
        mav.addObject("url", "/select/selectMansByDep.json");
        if (StringUtils.isNotBlank(userIds) && !"undefined".equals(userIds)) {
            mav.addObject("selectedDataUrl", "/select/getUsers.json?userIds=" + userIds);
        }
        // 是否单选
        mav.addObject("single", false);
        // id名称
        // mav.addObject("idName", "id");
        // 显示名称
        mav.addObject("dataText", "userName");
        // 标题
        mav.addObject("selectDialogTitle", "选择人员");
        // 表头页面
        mav.addObject("tablehead", "/selectdata/user/tablehead.vm");
        // 查询条件页面
        mav.addObject("query", "/selectdata/user/query.vm");
        // 窗口宽度
        mav.addObject("dialogWidth", "800");
        // 窗口高度
        mav.addObject("dialogHeight", "500");
        // layout左边
        mav.addObject("westLayout", "/selectdata/user/westLayout.vm");
        // layout左边宽度
        mav.addObject("westWidth", "235");

        return mav;
    }

    @RequestMapping("/selectSingleManClassify")
    public ModelAndView selectSingle() {
        ModelAndView mav = new ModelAndView("/selecttemplate/selectdialogtemplate");
        // 取数据的url
        mav.addObject("url", "/select/selectMansByDep.json");
        // 是否单选
        mav.addObject("single", true);
        // id名称
        // mav.addObject("idName", "id");
        // 显示名称
        mav.addObject("dataText", "userName");
        // 标题
        mav.addObject("selectDialogTitle", "选择人员");
        // 表头页面
        mav.addObject("tablehead", "/selectdata/user/tablehead.vm");
        // 查询条件页面
        mav.addObject("query", "/selectdata/user/query.vm");
        // 窗口宽度
        mav.addObject("dialogWidth", "650");
        // 窗口高度
        mav.addObject("dialogHeight", "500");
        // layout左边
        mav.addObject("westLayout", "/selectdata/user/westLayout.vm");
        // layout左边宽度
        mav.addObject("westWidth", "235");
        return mav;
    }

    /**
     * 环节人选择树形
     *
     * @param userIds
     *            用于回显
     * @return
     */
    @RequestMapping("/selectNodemans")
    public ModelAndView selectNodemans(String userIds) {
        ModelAndView mav = new ModelAndView("/selectdialog/selectNodemans");
        if (StringUtils.isNotBlank(userIds) && !"undefined".equals(userIds)) {
            mav.addObject("peopleSelectUrl", "/select/getUsers.json?userIds=" + userIds);
        }
        mav.addObject("selectDialogTitle", "选择收件人");
        return mav;
    }

    /**
     * 按部门取人员数据的JSON
     *
     * @param page
     * @param rows
     * @param request
     * @return
     */
    @RequestMapping("/selectMansByDep.json")
    @ResponseBody
    public Page selectManByDepJson(Integer page, Integer rows, HttpServletRequest request,Long[] dutyIdsConds) {
        SearchBuilder<SysUser, Long> searchBuilder = SearchBuilderHelper.bindSearchBuilder(conversionService, request);
        searchBuilder.clearSearchBuilder().findSearchParam(request);

        searchBuilder.appendSort(Direction.ASC, "sort");
        if(dutyIdsConds!= null && dutyIdsConds.length>0){
        	searchBuilder.getFilterBuilder().in("dutyId", dutyIdsConds);
        }

        SearchListHelper<SysUser> listhelper = new SearchListHelper<SysUser>();
        listhelper.execute(searchBuilder, userService.getRepository(), page, rows);

        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), UserSelectShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/getUserByDep.json")
    @ResponseBody
    public Page selectUsersByDep(Long depId) {

        List<SysUser> uList = null;
        if (depId != null) {
            uList = this.userService.findBydetpId(depId);
        }
        if (uList == null) {
            uList = new ArrayList<SysUser>();
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
                List<SysUser> users = this.userService.findBydetpId(Long.valueOf(idArr[i]));
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

        List<SysUser> uList = this.userService.getUsersByIdsGroupByDep(userIds);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(uList, UserSelectedListBean.class));
        p.setTotal((long) uList.size());
        return p;
    }

    /**  */
    @RequestMapping("/userAutocomplete.json")
    @ResponseBody
    public List getUsersJson(String search, String exceptUserIds, HttpServletRequest request) {
        if (StringUtils.isBlank(search)) {
            return null;
        }
        // SearchBuilder<SysUser, Long> searchBuilder = SearchBuilderHelper.bindSearchBuilder(conversionService,
        // request);
        // searchBuilder.clearSearchBuilder().findSearchParam(request);
        // searchBuilder.getFilterBuilder().like("userName", search.trim());
        // SearchListHelper<SysUser> listhelper = new SearchListHelper<SysUser>();
        // listhelper.execute(searchBuilder, userService.getRepository(), 1, 15);
        List<SysUser> list = userService.findUsersForAutocomplete(search, exceptUserIds, 1, 10);

        return BeanCopyBuilder.buildList(list, UserSelectShowBean.class);
    }
}
