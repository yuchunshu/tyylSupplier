/*
 * FileName:    SysUserLockedController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年9月18日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.user.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.data.bean.SearchResult;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.data.web.SimpleCrudController;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;

/**
 * @author LJX
 *
 */
@Controller
@RequestMapping("lockeduser")
public class SysUserLockedController extends SimpleCrudController<SysUser, SysUserService> {

    @Autowired
    private SysUserService service;

    @Override
    protected SearchResult beforeList(ModelAndView mav, Integer pageNum, Integer numPerPage, String orderField,
                    String orderDirection, SearchBuilder<SysUser, Long> searchBuilder, HttpServletRequest request,
                    Boolean isPageBreak) {
        searchBuilder.getFilterBuilder().equal("accountLocked", true);
        return super.beforeList(mav, pageNum, numPerPage, orderField, orderDirection, searchBuilder, request,
                        isPageBreak);
    }

    @RequestMapping("/unlocked")
    public ModelAndView unLocked(String ids, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("redirect:/lockeduser/list");
        this.service.unLockedUser(ids, request);
        return mav;

    }

}
