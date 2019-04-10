/*
 * FileName:    PublicController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.address.web;

import java.util.Date;

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
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.user.bean.UserEditBean;
import cn.com.chaochuang.common.user.bean.UserShowBean;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;

/**
 * @author HM 公共通讯录（数据来源于用户表SysUser）
 */
@Controller
@RequestMapping("oa/address/public")
public class PublicAddressController {

    @Autowired
    private SysUserService       userService;

    @Autowired
    private SysDepartmentService depService;

    @Autowired
    protected ConversionService  conversionService;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/oa/address/public/list");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<SysUser, Long> searchBuilder = new SearchBuilder<SysUser, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);

        searchBuilder.appendSort(Direction.ASC, "id");
        SearchListHelper<SysUser> listhelper = new SearchListHelper<SysUser>();
        listhelper.execute(searchBuilder, userService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), UserShowBean.class));
        p.setTotal(listhelper.getCount());

        return p;
    }

    // 导出EXCEL
    @RequestMapping("/export.json")
    public ModelAndView exportLaw(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("oa/address/public/excel/publicExport");
        mav.addObject("page", this.listjson(1, 1000000000, request));
        mav.addObject("fileName", "公共通讯录" + Tools.DATE_FORMAT4.format(new Date()));
        return mav;
    }

    @RequestMapping("/edit")
    public ModelAndView editPage(Long id) {
        ModelAndView mav = new ModelAndView("/oa/address/public/edit");
        if (id != null) {
            mav.addObject("obj", this.userService.findOne(id));
        }
        return mav;
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(UserEditBean info, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (info != null) {
                this.userService.updateUserInfo(info);
                ;
            }
            return new ReturnInfo(info.getId().toString(), null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detail(Long id) {
        ModelAndView mav = new ModelAndView("/oa/address/public/detail");
        if (id != null) {
            mav.addObject("obj", this.userService.findOne(id));
        }
        return mav;
    }
}
