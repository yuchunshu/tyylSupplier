/*
 * FileName:    SelectMailIncepterController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年8月3日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.CommonTreeBean;
import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.select.bean.CommonSelectedBean;
import cn.com.chaochuang.common.select.bean.UserSelectShowBean;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchBuilderHelper;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.oa.personal.service.OaPersonalGroupMemberService;
import cn.com.chaochuang.oa.personal.service.OaPersonalGroupService;

/**
 * 邮件选人控制器
 *
 * @author HM
 *
 */
@Controller
@RequestMapping("oa/mail/select")
public class SelectIncepterController {

    @Autowired
    private SysUserService               userService;

    @Autowired
    private SysDepartmentService         depService;

    @Autowired
    private OaPersonalGroupService       personalGroupService;

    @Autowired
    private OaPersonalGroupMemberService personalGroupMemberService;

    @Autowired
    protected ConversionService          conversionService;

    @RequestMapping("/getIncepters")
    public ModelAndView selectManMulti(String userIds) {
        ModelAndView mav = new ModelAndView("/selecttemplate/selectdialogtemplate");
        // 取数据的url
        mav.addObject("url", "/oa/mail/select/getIncepters.json");
        if (StringUtils.isNotBlank(userIds) && !"undefined".equals(userIds)) {
            mav.addObject("selectedDataUrl", "/oa/mail/select/getUsers.json?userIds=" + userIds);
        }
        // 是否单选
        mav.addObject("single", false);
        // id名称
        // mav.addObject("idName", "id");
        // 显示名称
        mav.addObject("dataText", "userName");
        // 标题
        mav.addObject("selectDialogTitle", "选择收件人");
        // 表头页面
        mav.addObject("tablehead", "/oa/mail/select/tablehead.vm");
        // 查询条件页面
        mav.addObject("query", "/oa/mail/select/query.vm");
        // 窗口宽度
        mav.addObject("dialogWidth", "800");
        // 窗口高度
        mav.addObject("dialogHeight", "500");
        // layout左边
        mav.addObject("westLayout", "/oa/mail/select/westLayout.vm");
        // layout左边宽度
        mav.addObject("westWidth", "235");

        return mav;
    }

    /**
     * 取人员数据的JSON
     *
     * @param page
     * @param rows
     * @param request
     * @return
     */
    @RequestMapping("/getIncepters.json")
    @ResponseBody
    public Page selectManByDepJson(String type, Long groupId, Integer page, Integer rows, HttpServletRequest request) {
        if (StringUtils.isNotBlank(type)) {
            return selectUsersByGroup(groupId, page, rows);
        } else {
            return selectUsersByDep(page, rows, request);
        }
    }

    private Page selectUsersByDep(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<SysUser, Long> searchBuilder = SearchBuilderHelper.bindSearchBuilder(conversionService, request);
        searchBuilder.clearSearchBuilder().findSearchParam(request);

        SearchListHelper<SysUser> listhelper = new SearchListHelper<SysUser>();
        listhelper.execute(searchBuilder, userService.getRepository(), page, rows);

        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), UserSelectShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    private Page selectUsersByGroup(Long groupId, Integer page, Integer rows) {

        Page p = new Page();
        if (groupId != null) {
            Map<String, Object> map = personalGroupMemberService.selectGroupMembers(groupId, page, rows);
            p.setRows(BeanCopyBuilder.buildList((List) map.get("datas"), UserSelectShowBean.class));
            Integer total = (Integer) map.get("total");
            if (total != null) {
                p.setTotal(total.longValue());
            } else {
                p.setTotal(0l);
            }
        }
        return p;
    }

    /**
     * 延迟加载树(部门树与群组树组合)
     *
     * @param id
     * @return
     */
    @RequestMapping("/getLazyComboTree.json")
    @ResponseBody
    public List<CommonTreeBean> lazyDepTreeJson(Long id) {
        if (id == null) {
            List<CommonTreeBean> list = this.depService.getTreeRootBean();
            if (list != null) {
                list.addAll(personalGroupService.mailGroupTree());
                return list;
            }
            return new ArrayList<CommonTreeBean>();
        }
        return this.depService.getChildrenBean(id);
    }

    @RequestMapping("/getUsers.json")
    @ResponseBody
    public Page getUsersJson(String userIds, Integer page, Integer rows, HttpServletRequest request) {

        List<SysUser> uList = this.userService.getUsersByIdsGroupByDep(userIds);
        Page p = new Page();
        List<CommonSelectedBean> beanList = new ArrayList<CommonSelectedBean>();
        if (uList != null && uList.size() > 0) {
            for (SysUser user : uList) {
                beanList.add(new CommonSelectedBean(user.getId().toString(), user.getUserName()));
            }
        }
        p.setRows(beanList);
        // p.setRows(BeanCopyBuilder.buildList(uList, UserSelectShowBean.class));
        p.setTotal((long) uList.size());
        return p;
    }
}
