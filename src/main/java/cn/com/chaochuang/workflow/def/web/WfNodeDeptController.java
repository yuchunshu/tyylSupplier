package cn.com.chaochuang.workflow.def.web;

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
import cn.com.chaochuang.workflow.def.bean.NodeDeptEditBean;
import cn.com.chaochuang.workflow.def.domain.WfNodeDept;
import cn.com.chaochuang.workflow.def.service.WfNodeDeptService;

/**
 * @author hzr 2016-12-24
 *
 */
@Controller
@RequestMapping("workflow/nodedept")
public class WfNodeDeptController {
    @Autowired
    private WfNodeDeptService nodeDeptService;

    @Autowired
    private LogService        logService;

    @Autowired
    private ConversionService conversionService;

    /**
     * 列表页面
     */
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/workflow/nodedept/list");
        return mav;
    }

    /**
     * 列表数据
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<WfNodeDept, Long> searchBuilder = new SearchBuilder<WfNodeDept, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);

        SearchListHelper<WfNodeDept> listhelper = new SearchListHelper<WfNodeDept>();
        listhelper.execute(searchBuilder, nodeDeptService.getRepository(), page, rows);

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
        ModelAndView mav = new ModelAndView("/workflow/nodedept/edit");
        return mav;
    }

    /**
     * 编辑页面
     */
    @RequestMapping("/edit")
    public ModelAndView editPage(Long id) {
        ModelAndView mav = new ModelAndView("/workflow/nodedept/edit");
        if (id != null) {
            WfNodeDept receive = nodeDeptService.findOne(id);
            if (receive != null) {
                mav.addObject("obj", receive);
            }
        }
        return mav;
    }

    /**
     * 详情页面
     */
    @RequestMapping("/detail")
    public ModelAndView detailPage(Long id) {
        ModelAndView mav = new ModelAndView("/workflow/nodedept/detail");
        if (id != null) {
            WfNodeDept receive = nodeDeptService.findOne(id);
            if (receive != null) {
                mav.addObject("obj", receive);
            }
        }
        return mav;
    }

    /**
     * 保存数据
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(NodeDeptEditBean bean, HttpServletRequest request) {
        try {
            Long id = nodeDeptService.saveNodeDept(bean);
            if (id != null) {
                if (bean.getId() == null) {
                    logService.saveDefaultLog("新增环节接收定义，环节为：" + bean.getNodeId(), request);
                } else {
                    logService.saveDefaultLog("保存环节接收定义，环节为：" + bean.getNodeId(), request);
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
                    nodeDeptService.delNodeDept(Long.valueOf(id));
                }
                return new ReturnInfo("1", null, "删除成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReturnInfo(null, "服务器连接不上！", null);
    }
}
