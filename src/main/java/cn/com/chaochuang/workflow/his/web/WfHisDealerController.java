package cn.com.chaochuang.workflow.his.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.workflow.his.bean.HisDealerEditBean;
import cn.com.chaochuang.workflow.his.domain.WfHisDealer;
import cn.com.chaochuang.workflow.his.service.WfHisDealerService;

/**
 * @author hzr 2016-12-24
 *
 */
@Controller
@RequestMapping("workflow/hisdealer")
public class WfHisDealerController {
    @Autowired
    private WfHisDealerService 	hisDealerService;

    @Autowired
    private LogService        	logService;

    @Autowired
    private ConversionService 	conversionService;

    /**
     * 列表页面
     */
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/workflow/hisdealer/list");
        return mav;
    }

    /**
     * 列表数据
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<WfHisDealer, Long> searchBuilder = new SearchBuilder<WfHisDealer, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);

        SearchListHelper<WfHisDealer> listhelper = new SearchListHelper<WfHisDealer>();
        listhelper.execute(searchBuilder, hisDealerService.getRepository(), page, rows);

        Page p = new Page();
        p.setRows(listhelper.getList());
        p.setTotal(listhelper.getCount());
        return p;
    }

    /**
     * 新增页面
     */
    @RequestMapping("/new")
    public ModelAndView newPage() {
        ModelAndView mav = new ModelAndView("/workflow/hisdealer/edit");
        return mav;
    }

    /**
     * 编辑页面
     */
    @RequestMapping("/edit")
    public ModelAndView editPage(String id) {
        ModelAndView mav = new ModelAndView("/workflow/hisdealer/edit");
        if (StringUtils.isNotBlank(id)) {
            WfHisDealer mans = hisDealerService.findOne(id);
            if (mans != null) {
                mav.addObject("obj", mans);
            }
        }
        return mav;
    }

    /**
     * 详情页面
     */
    @RequestMapping("/detail")
    public ModelAndView detailPage(String id) {
        ModelAndView mav = new ModelAndView("/workflow/hisdealer/detail");
        if (StringUtils.isNotBlank(id)) {
            WfHisDealer mans = hisDealerService.findOne(id);
            if (mans != null) {
                mav.addObject("obj", mans);
            }
        }
        return mav;
    }

    /**
     * 保存数据
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(HisDealerEditBean bean, HttpServletRequest request) {
        try {
            String id = hisDealerService.saveHisDealer(bean);
            if (StringUtils.isNotBlank(id)) {
                if (StringUtils.isNotBlank(bean.getId())) {
                    logService.saveDefaultLog("新增环节历史选择，环节为：" + bean.getNodeId(), request);
                } else {
                    logService.saveDefaultLog("保存环节历史选择，环节为：" + bean.getNodeId(), request);
                }
                return new ReturnInfo(id.toString(), null, "保存成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReturnInfo(null, "服务器连接不上！", null);
    }

    /**
     * 删除数据
     */
    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo delete(String ids, HttpServletRequest request) {
        try {
            if (StringUtils.isNotEmpty(ids)) {
                String[] idArr = ids.split(",");
                for (String id : idArr) {
                    hisDealerService.delHisDealer(id);
                }
                return new ReturnInfo("1", null, "删除成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReturnInfo(null, "服务器连接不上！", null);
    }
}
