/*
 * FileName:    ReceiveFileController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.docstatis.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.upload.support.PluploadController;
import cn.com.chaochuang.doc.docstatis.bean.DocStatChartBean;
import cn.com.chaochuang.doc.docstatis.service.DocStatisService;
import net.sf.json.JSONArray;

/**
 * @author HeYunTao
 *
 */
@Controller
@RequestMapping("doc/docstatis")
public class DocStatisController extends PluploadController {

    @Autowired
    private DocStatisService    docStatisService;

    @Autowired
    protected ConversionService conversionService;
    
    @Autowired
    private LogService          logService;

    @RequestMapping("/docCountList")
    public ModelAndView docCountList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/doc/docstatis/docCountList");
        mav.addObject("userId", request.getParameter("userId"));
        mav.addObject("userName", request.getParameter("userName"));
        mav.addObject("deptId", request.getParameter("deptId"));
        mav.addObject("deptName", request.getParameter("deptName"));
        mav.addObject("fromDate", request.getParameter("fromDate"));
        mav.addObject("toDate", request.getParameter("toDate"));
        mav.addObject("showUsers", request.getParameter("showUsers"));
        return mav;
    }

    @RequestMapping("/listDocCount.json")
    @ResponseBody
    public Page docCountListJson(Integer page, Integer rows, HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String deptId = request.getParameter("deptId");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String showUs = request.getParameter("showUsers");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dt1=null, dt2=null;
        Page p = new Page();
        try {
            if(StringUtils.isNotBlank(fromDate)) {
                dt1 = df.parse(fromDate+" 00:00");
            }
            if(StringUtils.isNotBlank(toDate)) {
                dt2 = df.parse(toDate+" 23:59");
            }
            boolean showUsers = false;
            if (StringUtils.isNotBlank(userId) || StringUtils.isNotBlank(showUs)) {
            	showUsers = true;
            }
            List list = docStatisService.summary(deptId, userId, dt1, dt2, showUsers);
            
            p.setRows(list);
            p.setTotal(Long.valueOf(list.size()));
        } catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文办理数量统计-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;
    }


    // 统计直方图
    @RequestMapping("/statColumnStatistical")
    @ResponseBody
    public ModelAndView columnStatistical(String userName, String dataStr) {
    	ModelAndView mav = new ModelAndView("/doc/docstatis/columnDiagram");

        List<DocStatChartBean> dataList = new ArrayList<DocStatChartBean>();
        String[] datas = dataStr.split(",");
        for (String sub: datas) {
        	String[] item = sub.split(":");
        	DocStatChartBean bean = new DocStatChartBean();
            bean.setName(item[0]);
            List data = new ArrayList();
            data.add(new Long(item[1]));
            bean.setData(data);
            dataList.add(bean);
        }


        List cates = new ArrayList();
        cates.add(userName);

        mav.addObject("datas", JSONArray.fromObject(dataList).toString());
        mav.addObject("categories", JSONArray.fromObject(cates).toString());
        if (StringUtils.isNotBlank(userName)) {
            mav.addObject("showTitle", userName);
        } else {
            mav.addObject("showTitle", "广西东盟食品药品安全检验检测中心");
        }

        return mav;
    }


    // 统计饼图
    @RequestMapping("/statPieStatistical")
    @ResponseBody
    public ModelAndView pieStatistical(String userName, String dataStr) {
        ModelAndView mav = new ModelAndView("/doc/docstatis/pieDiagram");
        List dataList = new ArrayList();
        String[] datas = dataStr.split(",");
        for (String sub: datas) {
        	String[] item = sub.split(":");
            List data = new ArrayList();
            data.add(item[0]);
            data.add(new Long(item[1]));
            dataList.add(data);
        }

        mav.addObject("datas", JSONArray.fromObject(dataList).toString());
        if (StringUtils.isNotBlank(userName)) {
            mav.addObject("showTitle", userName);
        } else {
            mav.addObject("showTitle", "广西东盟食品药品安全检验检测中心");
        }

        return mav;
    }

    @RequestMapping("/columnStatisticalAll")
    @ResponseBody
    public ModelAndView columnStatisticalAll() {
        ModelAndView mav = new ModelAndView("/doc/docstatis/columnDiagram");
        /**
        Map map = this.docStatisService.columnStatistical(staUserIds, staFirstDay, staLastDay);
        if (map != null && map.containsKey("datas")) {
            mav.addObject("datas", map.get("datas"));
        }
        if (map != null && map.containsKey("categories")) {
            mav.addObject("categories", map.get("categories"));
        }
        if (StringUtils.isNotEmpty(staUserIds) && staUserIds.split(",").length == 1) {
            mav.addObject("showTitle", this.userService.findOne(Long.parseLong(staUserIds)).getUserName());
        } else {
            mav.addObject("showTitle", "广西东盟食品药品安全检验检测中心");
        }
        */
        return mav;
    }



    @RequestMapping("/pieStatisticalAll")
    @ResponseBody
    public ModelAndView pieStatisticalAll() {
        ModelAndView mav = new ModelAndView("/doc/docstatis/pieDiagram");
        /**
        Map map = this.docStatisService.pieStatistical(staUserIds, staFirstDay, staLastDay);
        if (map != null && map.containsKey("datas")) {
            mav.addObject("datas", map.get("datas"));
        }
        if (StringUtils.isNotEmpty(staUserIds) && staUserIds.split(",").length == 1) {
            mav.addObject("showTitle", this.userService.findOne(Long.parseLong(staUserIds)).getUserName());
        } else {
            mav.addObject("showTitle", "广西东盟食品药品安全检验检测中心");
        }
        */
        return mav;
    }


    @RequestMapping("/exportDocCount.json")
    public ModelAndView export(Long userId, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/doc/docstatis/excel/docCountExport");
        //mav.addObject("page", this.docCountListJson(1, 1000000000, null, null, null, null, request));
        mav.addObject("fileName", "公文办理数量统计表");
        return mav;
    }
}
