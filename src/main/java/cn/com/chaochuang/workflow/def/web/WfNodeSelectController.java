/*
 * FileName:    SelectManController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年1月14日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.workflow.def.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.util.SearchBuilderHelper;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.workflow.def.bean.FlowEditBean;
import cn.com.chaochuang.workflow.def.bean.NodeEditBean;
import cn.com.chaochuang.workflow.def.domain.WfFlow;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.def.service.WfFlowService;
import cn.com.chaochuang.workflow.def.service.WfNodeService;

/**
 * @author hzr 2017.7.10
 *
 */
@Controller
@RequestMapping("workflow/select")
public class WfNodeSelectController {

    @Autowired
    private WfNodeService        nodeService;
    
    @Autowired
    private WfFlowService        flowService;

    @Autowired
    protected ConversionService  conversionService;

    /**
     * 获取节点数据的JSON
     *
     * @param page
     * @param rows
     * @param request
     * @return
     */
    @RequestMapping("/selectNodes.json")
    @ResponseBody
    public Page selectNodesJson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<WfNode, String> searchBuilder = SearchBuilderHelper.bindSearchBuilder(conversionService,
                        request);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        SearchListHelper<WfNode> listhelper = new SearchListHelper<WfNode>();
        listhelper.execute(searchBuilder, nodeService.getRepository(), page, rows);

    	List<NodeEditBean> beanList = new ArrayList<NodeEditBean>();
    	//page=2 rows=10 i=10 last=19
    	int lastTail = rows;
    	int leastCount = (listhelper.getList().size())%10;
    	if(leastCount != 0){
    		lastTail = leastCount;
    	}
    	for(int i=0;i<lastTail;i++){
    		NodeEditBean bean = new NodeEditBean();
    		bean.setId(listhelper.getList().get(i).getId());
    		bean.setNodeName(listhelper.getList().get(i).getNodeName());
    		beanList.add(bean);
    	}

        Page p = new Page();
        p.setRows(beanList);
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/selectSingleNodeClassify")
    public ModelAndView selectSingleNodeClassify() {
        ModelAndView mav = new ModelAndView("/workflow/select/selectnodedialogtemplate");
        // 取数据的url
        mav.addObject("url", "/workflow/select/selectNodes.json");
        // 是否单选
        mav.addObject("single", true);
        // id名称
        // mav.addObject("idName", "id");
        // 显示名称
        mav.addObject("dataText", "nodeName");
        // 标题
        mav.addObject("selectDialogTitle", "选择节点");
        // 表头页面
        mav.addObject("tablehead", "/selectdata/node/tablehead.vm");
        // 查询条件页面
        mav.addObject("query", "/selectdata/node/query.vm");
        // 窗口宽度
        mav.addObject("dialogWidth", "540");
        // 窗口高度
        mav.addObject("dialogHeight", "500");
        // layout左边
        // mav.addObject("westLayout", "/selectdata/user/westLayout.vm");
        // layout左边宽度
        // mav.addObject("westWidth", "235");
        return mav;
    }

    @RequestMapping("/selectNodesClassify")
    public ModelAndView selectNodesClassify() {
        ModelAndView mav = new ModelAndView("/workflow/select/selectnodedialogtemplate");
        // 取数据的url
        mav.addObject("url", "/workflow/select/selectNodes.json");
        // 是否单选
        mav.addObject("single", false);
        // id名称
        // mav.addObject("idName", "id");
        // 显示名称
        mav.addObject("dataText", "nodeName");
        // 标题
        mav.addObject("selectDialogTitle", "选择节点");
        // 表头页面
        mav.addObject("tablehead", "/selectdata/node/tablehead.vm");
        // 查询条件页面
        mav.addObject("query", "/selectdata/node/query.vm");
        // 窗口宽度
        mav.addObject("dialogWidth", "540");
        // 窗口高度
        mav.addObject("dialogHeight", "500");
        // layout左边
        // mav.addObject("westLayout", "/selectdata/user/westLayout.vm");
        // layout左边宽度
        // mav.addObject("westWidth", "235");
        return mav;
    }

    /**
     * 获取流程节点数据的JSON
     *
     * @param page
     * @param rows
     * @param request
     * @return
     */
    @RequestMapping("/selectSubFlows.json")
    @ResponseBody
    public Page selectSubFlowsJson(Integer page, Integer rows, HttpServletRequest request) {
    	String flowId = request.getParameter("flowId");
    	String flowName = request.getParameter("flowName");
        FlowEditBean bean = new FlowEditBean();
    	if (StringUtils.isNotBlank(flowId)) {
    		WfFlow flow = flowService.findOne(flowId);
    		bean.setFlowType(flow.getFlowType());
    		bean.setFlowLevel(flow.getFlowLevel());
        }
    	if (StringUtils.isNotBlank(flowName)) {
    		bean.setFlowName(flowName);
    	}
        return flowService.selectSubFlow(bean, page, rows);
    }
    
    @RequestMapping("/selectSubFlowClassify")
    public ModelAndView selectSingleFlowClassify(String flowId) {
        ModelAndView mav = new ModelAndView("/workflow/select/selectnodedialogtemplate");
        // 取数据的url
        mav.addObject("url", "/workflow/select/selectSubFlows.json?flowId=" + flowId);

        // 是否单选
        mav.addObject("single", true);
        // id名称
        // mav.addObject("idName", "id");
        // 显示名称
        mav.addObject("dataText", "nodeName");
        // 标题
        mav.addObject("selectDialogTitle", "选择流程");
        // 表头页面
        mav.addObject("tablehead", "/selectdata/flow/tablehead.vm");
        // 查询条件页面
        mav.addObject("query", "/selectdata/flow/query.vm");
        // 窗口宽度
        mav.addObject("dialogWidth", "540");
        // 窗口高度
        mav.addObject("dialogHeight", "500");
        return mav;
    }
}
