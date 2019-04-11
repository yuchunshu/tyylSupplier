/*
 * FileName:    WorkLogController.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.personal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.oa.personal.bean.GroupEditBean;
import cn.com.chaochuang.oa.personal.bean.GroupShowBean;
import cn.com.chaochuang.oa.personal.domain.OaPersonalGroup;
import cn.com.chaochuang.oa.personal.service.OaPersonalGroupService;

/**
 * @author HM
 *
 */
@Controller
@RequestMapping("oa/personal/group")
public class PersonalGroupController {

    @Autowired
    private OaPersonalGroupService personalGroupService;

    @Autowired
    private SysUserService         userService;

    @Autowired
    protected ConversionService    conversionService;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/oa/group/list");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<OaPersonalGroup, Long> searchBuilder = new SearchBuilder<OaPersonalGroup, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        SysUser user = this.userService.findOne(Long.valueOf(UserTool.getInstance().getCurrentUserId()));
        searchBuilder.getFilterBuilder().equal("user", user);

        searchBuilder.appendSort(Direction.ASC, "orderNum");
        SearchListHelper<OaPersonalGroup> listhelper = new SearchListHelper<OaPersonalGroup>();
        listhelper.execute(searchBuilder, personalGroupService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(this.personalGroupService.getShowList(listhelper.getList()));
        p.setTotal(listhelper.getCount());

        return p;
    }

    @RequestMapping("/new")
    public ModelAndView newPage() {
        ModelAndView mav = new ModelAndView("/oa/group/edit");
        return mav;
    }

    @RequestMapping("/edit")
    public ModelAndView editPage(Long id) {
        ModelAndView mav = new ModelAndView("/oa/group/edit");
        GroupShowBean bean = this.personalGroupService.getGroupForShow(id);
        mav.addObject("obj", bean);
        return mav;
    }

    // 保存
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(GroupEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {
            Long groupId = this.personalGroupService.saveGroup(bean);
            return new ReturnInfo(groupId.toString(), null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    // 删除
    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo del(String ids) {
        try {
            if (this.personalGroupService.deleteGroups(ids)) {
                return new ReturnInfo("1", null, "删除成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }

        return new ReturnInfo(null, "服务器连接不上！", null);
    }

    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detail(Long id) {
        ModelAndView mav = new ModelAndView("/oa/group/detail");
        GroupShowBean bean = this.personalGroupService.getGroupForShow(id);
        mav.addObject("obj", bean);
        return mav;
    }
}
