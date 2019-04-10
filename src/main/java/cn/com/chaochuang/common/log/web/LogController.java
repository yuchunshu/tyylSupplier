/*
 * FileName:    LogController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2014
 * History:     2014年12月12日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.log.web;

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
import cn.com.chaochuang.common.log.bean.LogShowBean;
import cn.com.chaochuang.common.log.domain.SysLog;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.util.SearchListHelper;

/**
 * @author LaoZhiYong
 *
 */
@Controller
@RequestMapping("log")
public class LogController {

    @Autowired
    private LogService          service;

    @Autowired
    protected ConversionService conversionService;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/system/log/list");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<SysLog, Long> searchBuilder = new SearchBuilder<SysLog, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.appendSort(Direction.DESC, "operateDate");
        SearchListHelper<SysLog> listhelper = new SearchListHelper<SysLog>();
        listhelper.execute(searchBuilder, service.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), LogShowBean.class));
        p.setTotal(listhelper.getCount());

        return p;
    }

}
