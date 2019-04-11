/*
 * FileName:    SelectController.java
 * Description:
 * Company:     
 * Copyright:    (c) 2015
 * History:     2015年1月9日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.select.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.select.bean.DepartmentTreeShowBean;
import cn.com.chaochuang.common.select.bean.SysUserShowBeanShowBean;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.user.service.SysUserService;

import com.google.common.collect.Lists;

/**
 * @author LaoZhiYong
 *
 */
@Controller
@RequestMapping("/select")
public class SelectController {

    @Autowired
    private SysDepartmentService depService;
    @Autowired
    private SysUserService       userService;

    @Autowired
    protected ConversionService  conversionService;

    @RequestMapping("/selectMan")
    public ModelAndView fnSelectMan() {
        ModelAndView mav = new ModelAndView("/selectdialog/selectmandialog");
        return mav;
    }

    @RequestMapping("/treeDepartment.json")
    @ResponseBody
    public List<DepartmentTreeShowBean> getDepartmentTree(Long id) {
        SearchBuilder<SysDepartment, Long> searchBuilder = new SearchBuilder<SysDepartment, Long>(conversionService);
        searchBuilder.appendSort(Direction.ASC, "orderNum");
        if (id == null) {
            searchBuilder.getFilterBuilder().equal("parentDep", 1).equal("unitFlag", 2);
        } else {
            searchBuilder.getFilterBuilder().equal("parentDep", id);
        }
        return BeanCopyBuilder.buildList(searchBuilder.findAll(depService.getRepository()),
                        DepartmentTreeShowBean.class);
    }

    @RequestMapping("/selectManByDepartment.json")
    @ResponseBody
    public List<SysUserShowBeanShowBean> getSysUserTree(Long depId) {
        SearchBuilder<SysUser, Long> searchBuilder = new SearchBuilder<SysUser, Long>(conversionService);
        searchBuilder.appendSort(Direction.ASC, "userName");
        if (depId != null) {
            searchBuilder.getFilterBuilder().equal("department.id", depId);
        } else {
            return Lists.newArrayList();
        }
        return BeanCopyBuilder.buildList(searchBuilder.findAll(userService.getRepository()),
                        SysUserShowBeanShowBean.class);
    }
}
