/*
 * FileName:    NoticeController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.process.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.data.persistence.SearchFilter;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.doc.process.service.DocProcessService;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * @author huangwq
 *
 */
@Controller
@RequestMapping("doc/process")
public class DocProcessController {

    @Autowired
    protected DocProcessService docProcessService;

    @Autowired
    private WfNodeInstService   nodeInstService;

    @Autowired
    protected ConversionService conversionService;

    @RequestMapping("/receive/list")
    public ModelAndView receiveList(String type) {
        ModelAndView mav = new ModelAndView("/doc/process/list");
        mav.addObject("type", type);
        return mav;
    }

    @RequestMapping("/send/list")
    public ModelAndView sendList(String type) {
        ModelAndView mav = new ModelAndView("/doc/process/sendlist");
        mav.addObject("type", type);
        return mav;
    }

    @RequestMapping("/receive/list.json")
    @ResponseBody
    // 对页面Pag的操作
    public Page rlistjson(Integer page, Integer rows, HttpServletRequest request, String type) {

        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        Map<String, SearchFilter> thisFilters = new HashMap<String, SearchFilter>();
        Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "__");
        if (MapUtils.isNotEmpty(searchParams)) {
            // request里的查询参数
            thisFilters = SearchFilter.parse(searchParams);
            // 追加查询的参数
            thisFilters.put("process.depId_" + user.getDepartment().getId(), new SearchFilter("process.depId",
                            SearchFilter.Operator.EQ, user.getDepartment().getId()));
            thisFilters.put("process.docFile.fileType_" + WfBusinessType.收文.getKey(), new SearchFilter(
                            "process.docFile.fileType", SearchFilter.Operator.EQ, WfBusinessType.收文.getKey()));

            if (StringUtils.isNotEmpty(type) && type.equals("person")) {
                // 个人收文登记簿
                thisFilters.put("node.dealer_" + user.getId(), new SearchFilter("node.dealer",
                                SearchFilter.Operator.EQ, user.getId()));
            }

        }

        return this.docProcessService.selectProcessPage(thisFilters.values(), page, rows);
    }

    @RequestMapping("/send/list.json")
    @ResponseBody
    // 对页面Pag的操作
    public Page slistjson(Integer page, Integer rows, HttpServletRequest request, String type) {

        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        Map<String, SearchFilter> thisFilters = new HashMap<String, SearchFilter>();

        Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "__");
        if (MapUtils.isNotEmpty(searchParams)) {
            // request里的查询参数
            thisFilters = SearchFilter.parse(searchParams);
            // 追加查询的参数
            thisFilters.put("process.depId_" + user.getDepartment().getId(), new SearchFilter("process.depId",
                            SearchFilter.Operator.EQ, user.getDepartment().getId()));
            thisFilters.put("process.docFile.fileType_" + WfBusinessType.发文.getKey(), new SearchFilter(
                            "process.docFile.fileType", SearchFilter.Operator.EQ, WfBusinessType.发文.getKey()));

            if (StringUtils.isNotEmpty(type) && type.equals("person")) {
                // 个人发文登记簿
                thisFilters.put("node.dealer_" + user.getId(), new SearchFilter("node.dealer",
                                SearchFilter.Operator.EQ, user.getId()));
            }

        }

        return this.docProcessService.selectProcessPage(thisFilters.values(), page, rows);
    }

    // 发文登记簿导出EXCEL
    @RequestMapping("/exportSendProcess.json")
    public ModelAndView sendProcessExport(HttpServletRequest request, HttpServletResponse response, String type) {
        ModelAndView mav = new ModelAndView("/doc/process/excel/processExport");
        mav.addObject("page", this.slistjson(1, 1000000000, request, type));

        // 个人/部门区分标识
        mav.addObject("type", type);
        mav.addObject("processType", "send");

        if (StringUtils.isNotEmpty(type) && type.equals("person")) {
            mav.addObject("fileName", "个人发文登记簿列表");
            mav.addObject("title", "个人发文登记簿");
        } else {
            mav.addObject("fileName", "部门发文登记簿列表");
            mav.addObject("title", "部门发文登记簿");
        }
        return mav;
    }

    // 收文登记簿导出EXCEL
    @RequestMapping("/exportRecProcess.json")
    public ModelAndView recProcessExport(HttpServletRequest request, HttpServletResponse response, String type) {
        ModelAndView mav = new ModelAndView("/doc/process/excel/processExport");
        mav.addObject("page", this.rlistjson(1, 1000000000, request, type));

        // 个人/部门区分标识
        mav.addObject("type", type);
        mav.addObject("processType", "rec");

        if (StringUtils.isNotEmpty(type) && type.equals("person")) {
            mav.addObject("fileName", "个人收文登记簿列表");
            mav.addObject("title", "个人收文登记簿");
        } else {
            mav.addObject("fileName", "部门收文登记簿列表");
            mav.addObject("title", "部门收文登记簿");
        }
        return mav;
    }

}
