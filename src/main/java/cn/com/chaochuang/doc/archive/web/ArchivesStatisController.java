package cn.com.chaochuang.doc.archive.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.data.persistence.SearchFilter;
import cn.com.chaochuang.doc.archive.bean.ArchivesStatisShowBean;
import cn.com.chaochuang.doc.archive.service.ArchivesStatisService;
import cn.com.chaochuang.doc.docstatis.bean.DocStatChartBean;
import net.sf.json.JSONArray;

/**
 * @author dengl 2017.12.01
 *
 */
@Controller
@RequestMapping("doc/archives/statis")
public class ArchivesStatisController {
	
	@Autowired
    private ArchivesStatisService 	statisService;
	
	@RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/doc/archives/docstatis/docCountList");
        return mav;
    }
	
	/**
     * 统计数据
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public Page listJson(HttpServletRequest request) {

        Map<String, SearchFilter> thisFilters = new HashMap<String, SearchFilter>();

        Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "__");
        if (MapUtils.isNotEmpty(searchParams)) {
            thisFilters = SearchFilter.parse(searchParams);
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Page p = new Page();
        dataMap = this.statisService.statArchives(thisFilters.values());
        p.setRows((ArrayList<ArchivesStatisShowBean>) dataMap.get("rows"));
        p.setTotal(dataMap.get("total") == null ? (long) 0 : (long) dataMap.get("total"));
        return p;
    }
    
    // 统计直方图
    @RequestMapping("/statColumnStatistical")
    @ResponseBody
    public ModelAndView columnStatistical(String userName, String dataStr) {
    	ModelAndView mav = new ModelAndView("/doc/archives/docstatis/columnDiagram");

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
        ModelAndView mav = new ModelAndView("/doc/archives/docstatis/pieDiagram");
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
	
	
}
