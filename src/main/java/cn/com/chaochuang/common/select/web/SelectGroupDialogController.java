/*
 * FileName:    SelectGroupDialogController.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年3月7日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.select.web;

import javax.servlet.http.HttpServletRequest;

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
import cn.com.chaochuang.common.select.bean.PersonalGroupSelectShowBean;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchBuilderHelper;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.oa.personal.domain.OaPersonalGroup;
import cn.com.chaochuang.oa.personal.service.OaPersonalGroupService;

/**
 * @author HM
 *
 */
@Controller
@RequestMapping("/select/group")
public class SelectGroupDialogController {

    @Autowired
    private SysUserService         userService;

    @Autowired
    private OaPersonalGroupService groupService;

    @Autowired
    protected ConversionService    conversionService;

    @RequestMapping("/selectgroup")
    public ModelAndView selectManMulti(Long deptId) {
        ModelAndView mav = new ModelAndView("/selectdialog/selectdialogtemplate");
        // 取数据的url
        mav.addObject("url", "/select/group/selectgroup.json");
        // 是否单选
        mav.addObject("single", false);
        // id名称
        mav.addObject("idName", "id");
        // 显示名称
        mav.addObject("showName", "groupName");
        // 标题
        mav.addObject("selectDialogTitle", "选择群组");
        // 表头页面
        mav.addObject("tablehead", "/selectdialog/tablehead_group.vm");
        // 查询条件页面
        mav.addObject("query", "/selectdialog/query_group.vm");
        return mav;
    }

    @RequestMapping("/selectgroupSingle")
    public ModelAndView selectSingle() {
        ModelAndView mav = new ModelAndView("/selectdialog/selectdialogtemplate");
        // 取数据的url
        mav.addObject("url", "/select/group/selectgroup.json");
        // 是否单选
        mav.addObject("single", true);
        // id名称
        mav.addObject("idName", "id");
        // 显示名称
        mav.addObject("showName", "groupName");
        // 标题
        mav.addObject("selectDialogTitle", "选择群组");
        // 表头页面
        mav.addObject("tablehead", "/selectdialog/tablehead_group.vm");
        // 查询条件页面
        mav.addObject("query", "/selectdialog/query_group.vm");
        return mav;
    }

    @RequestMapping("/selectgroup.json")
    @ResponseBody
    public Page selectGroupJson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<OaPersonalGroup, Long> searchBuilder = SearchBuilderHelper.bindSearchBuilder(conversionService,
                        request);
        searchBuilder.appendSort(Direction.DESC, "id");
        searchBuilder.getFilterBuilder().equal("user", UserTool.getInstance().getCurrentUser());
        SearchListHelper<OaPersonalGroup> listhelper = new SearchListHelper<OaPersonalGroup>();
        listhelper.execute(searchBuilder, groupService.getRepository(), page, rows);

        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), PersonalGroupSelectShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }
}
