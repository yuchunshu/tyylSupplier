/*
 * FileName:    MaintenanceController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年11月17日 (huangm) 1.0 Create
 */

package cn.com.chaochuang.common.maintance.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.maintance.bean.MaintenanceEditBean;
import cn.com.chaochuang.common.maintance.bean.MaintenanceShowBean;
import cn.com.chaochuang.common.maintance.domain.SysMaintenance;
import cn.com.chaochuang.common.maintance.service.SysMaintenanceService;
import cn.com.chaochuang.common.util.SearchListHelper;

/**
 * @author huangm
 *
 */
@Controller
@RequestMapping("system/maintenance")
public class MaintenanceController {

    @Autowired
    private SysMaintenanceService maintService;

    @Autowired
    private SysAttachService      attachService;

    @Autowired
    private LogService            logService;

//    @Autowired
//    private SysAppService         appService;

    @Autowired
    protected ConversionService   conversionService;

    /**
     * 提问列表
     *
     * @return
     */
    @RequestMapping("/quelist")
    public ModelAndView queList() {
        ModelAndView mav = new ModelAndView("/system/maint/list");
        mav.addObject("type", "question");
        return mav;
    }

    /**
     * 回答列表
     *
     * @return
     */
    @RequestMapping("/answerlist")
    public ModelAndView answerList() {
        ModelAndView mav = new ModelAndView("/system/maint/list");
        mav.addObject("type", "answer");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(String type, Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<SysMaintenance, String> searchBuilder = new SearchBuilder<SysMaintenance, String>(
                        conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        if ("answer".equals(type)) {
            searchBuilder.appendSort(Direction.ASC, "status");
        }
        searchBuilder.appendSort(Direction.DESC, "queTime");
        SearchListHelper<SysMaintenance> listhelper = new SearchListHelper<SysMaintenance>();
        listhelper.execute(searchBuilder, maintService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), MaintenanceShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    /**
     * 提问编辑页面
     *
     * @return
     */
    @RequestMapping("/new")
    public ModelAndView newQuestion() {
        ModelAndView mav = new ModelAndView("/system/maint/edit");
//        mav.addObject("applist", this.appService.findAll());
        return mav;
    }

    /**
     * 详情
     *
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detailQuestion(String id) {
        ModelAndView mav = new ModelAndView("/system/maint/detail");
        if (StringUtils.isNotBlank(id)) {
            mav.addObject("obj", this.maintService.findOne(id));
            mav.addObject("attachMap", this.attachService.getAttachMap(id, SysMaintenance._ATT_OWNER_TYPE));
        }
        return mav;
    }

    /**
     * 问题处理/解答页面
     *
     * @return
     */
    @RequestMapping("/deal")
    public ModelAndView dealQuestion(String id) {
        ModelAndView mav = new ModelAndView("/system/maint/deal");
        if (StringUtils.isNotBlank(id)) {
            mav.addObject("obj", this.maintService.findOne(id));
            mav.addObject("attachMap", this.attachService.getAttachMap(id, SysMaintenance._ATT_OWNER_TYPE));
        }
        return mav;
    }

    /**
     * 保存用户提问
     *
     * @param bean
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/saveque.json")
    @ResponseBody
    public ReturnInfo saveQuestion(MaintenanceEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(bean.getId())) {
                logService.saveLog(SjType.普通操作, "新增用户提问。", LogStatus.成功, request);
            } else {
                logService.saveLog(SjType.普通操作, "修改用户提问。", LogStatus.成功, request);
            }
            return new ReturnInfo(this.maintService.saveQuestion(bean), null, "保存成功。");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    /**
     * 保存管理员回答
     *
     * @param bean
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/saveans.json")
    @ResponseBody
    public ReturnInfo saveAnswer(MaintenanceEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(bean.getId())) {
                logService.saveLog(SjType.普通操作, "回答用户提问。", LogStatus.成功, request);
                return new ReturnInfo(this.maintService.saveAnswer(bean), null, "保存成功。");
            } else {
                return new ReturnInfo(null, "找不到提问记录。", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo delete(String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(id)) {
                logService.saveLog(SjType.普通操作, "删除用户提问。", LogStatus.成功, request);
                this.maintService.delete(id);
                return new ReturnInfo(id, null, "删除成功。");
            } else {
                return new ReturnInfo(null, "找不到提问记录。", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
}
