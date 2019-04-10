package cn.com.chaochuang.workflow.def.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.reference.AdministrativeLevel;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.serial.service.SerialNumberService;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.workflow.def.bean.FlowEditBean;
import cn.com.chaochuang.workflow.def.domain.WfFlow;
import cn.com.chaochuang.workflow.def.domain.WfFlowDept;
import cn.com.chaochuang.workflow.def.domain.WfNode;

import cn.com.chaochuang.workflow.def.service.WfFlowDeptService;
import cn.com.chaochuang.workflow.def.service.WfFlowService;
import cn.com.chaochuang.workflow.def.service.WfNodeService;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;

/**
 * @author yuandl 2016-11-24
 *
 */
@Controller
@RequestMapping("workflow/flow")
public class WfFlowController {

    @Autowired
    private WfFlowService          flowService;

    @Autowired
    private LogService             logService;

    @Autowired
    private ConversionService      conversionService;

    @Autowired
    private WfNodeService          nodeService;

    @Autowired
    private WfNodeInstService      NodeInstService;

    @Autowired
    private SerialNumberService    serialNumberService;

    @Autowired
    private WfFlowDeptService      flowDeptService;

    @Autowired
    private SysDepartmentService   deptService;

    /**
     * 列表页面
     */
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/workflow/flow/list");
        return mav;
    }

    /**
     * 列表数据
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<WfFlow, String> searchBuilder = new SearchBuilder<WfFlow, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.appendSort(Direction.ASC, "flowType");

        SearchListHelper<WfFlow> listhelper = new SearchListHelper<WfFlow>();
        listhelper.execute(searchBuilder, flowService.getRepository(), page, rows);

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
        ModelAndView mav = new ModelAndView("/workflow/flow/edit");
        mav.addObject("serialNumber", this.serialNumberService.getNewSN(WfFlow.class.getSimpleName(),"F","000"));
        mav.addObject("flowList", this.flowService.findByFlowLevelAndParentFlowIdNotNull(AdministrativeLevel.区级,""));
        return mav;
    }

    /**
     * 编辑页面
     */
    @RequestMapping("/edit")
    public ModelAndView editPage(String flowId) {
        ModelAndView mav = new ModelAndView("/workflow/flow/edit");
        if (StringUtils.isNotBlank(flowId)) {
            WfFlow flow = flowService.findOne(flowId);
            if (flow != null) {
                mav.addObject("obj", flow);
                mav.addObject("flowList", this.flowService.findByFlowLevelAndParentFlowIdNotNull(flow.getFlowLevel(),flowId));
            }
        }
        
        //加载选择单位或部门
        List<WfFlowDept> list = this.flowDeptService.findByFlowId(flowId);
        if (list != null && !list.isEmpty()) {
        	String flowDeptIds   = "";
        	String flowDeptNames = "";
        	for(WfFlowDept fd: list) {
        		if (fd.getDepId() == null) continue;
        		SysDepartment dept = this.deptService.findOne(fd.getDepId());
        		if (dept == null) continue;
        		if ("".equals(flowDeptIds)) {
        			flowDeptIds = dept.getId().toString();
        			flowDeptNames = dept.getDeptName();
        		} else {
        			flowDeptIds = flowDeptIds + "," + dept.getId().toString();
        			flowDeptNames = flowDeptNames + "," + dept.getDeptName();
        		}
        	}
        	mav.addObject("flowDeptIds", flowDeptIds);
        	mav.addObject("flowDeptNames", flowDeptNames);
        }

        return mav;
    }

    /**
     * 详情页面
     */
    @RequestMapping("/detail")
    public ModelAndView detailPage(String flowId) {
        ModelAndView mav = new ModelAndView("/workflow/flow/detail");
        if (StringUtils.isNotBlank(flowId)) {
            WfFlow flow = flowService.findOne(flowId);
            if (flow != null) {
                mav.addObject("obj", flow);
            }
        }
        return mav;
    }

    /**
     * 配置页面
     */
    @RequestMapping("/listNode")
    public ModelAndView configPage(String flowId) {
        ModelAndView mav = new ModelAndView("/workflow/flow/nodelist");
        if (StringUtils.isNotBlank(flowId)) {
            WfFlow flow = flowService.findOne(flowId);
            if (flow != null) {
                mav.addObject("obj", flow);

                mav.addObject("nodeList", nodeService.findByFlowId(flowId));
            }
        }
        return mav;
    }

    /**
     * 配置页面数据
     */
    @RequestMapping("/listNode.json")
    @ResponseBody
    public Page listNodeJson(String flowId) {
    	List<WfNode> nodeList = nodeService.findByFlowId(flowId);
    	Page p = new Page();
        p.setRows(nodeList);
        p.setTotal(Long.valueOf(nodeList.size()));
        return p;
    }

    /**
     * 保存数据
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(FlowEditBean bean, HttpServletRequest request) {
        try {
        	if(null != bean.getOtherFlowType()){
        		bean.setFlowType(bean.getOtherFlowType());
        	}
        	if(YesNo.是.equals(bean.getIsNewFlow()) && this.flowService.findOne(bean.getId()) != null){
        		return new ReturnInfo(null, "流程编号已存在", null);
        	}
            String flowId = flowService.saveFlow(bean);
            if (StringUtils.isNotBlank(flowId)) {
                logService.saveDefaultLog("保存工作流，流名称为：" + bean.getFlowName(), request);
            }
            
            //编号累加操作在保存后再执行
            if(YesNo.是.equals(bean.getIsNewFlow())){
            	this.serialNumberService.saveNewSN(WfFlow.class.getSimpleName(),"F","000");
            }
            return new ReturnInfo(flowId, null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    /**
     * 保存流程图jason数据
     */
    @RequestMapping("/updateFlowJason.json")
    @ResponseBody
    public boolean updateFlowJason(FlowEditBean bean, HttpServletRequest request) {
        try {
            flowService.updateFlowJason(bean);
            logService.saveDefaultLog("保存工作流图形：" + bean.getFlowName(), request);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
                    flowService.delFlow(id);
                }
                return new ReturnInfo("1", null, "删除成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReturnInfo(null, "服务器连接不上！", null);
    }



    /**
     * 查找流程图json数据
     */
    @RequestMapping("/getFlowJson.json")
    @ResponseBody
    public Object getFlowJson(String flowId, String entityId, HttpServletRequest request){
    	WfFlow flow = null;
		if (StringUtils.isNotBlank(entityId) && StringUtils.isBlank(flowId)){
			flowId = NodeInstService.getFlowIdByEntityId(entityId);
		}
		if (StringUtils.isNotBlank(flowId)){
			flow = flowService.findOne(flowId);
		}

		return flow;
    }

    /** 自动生成流程图 */
    @RequestMapping("/addFlowAutomated.json")
    @ResponseBody
    public Object addFlowAutomated(String flowId,String nodeIds, HttpServletRequest request){
    	return this.nodeService.findByFlowIdAndNodeIdIsNotExist(flowId, nodeIds);
    }
    
    /** 根据流程级别查询父流程 */
    @RequestMapping({"/getParentFlowByFlowLevel.json"})
    @ResponseBody
    public List<WfFlow> getParentFlowByFlowLevel(AdministrativeLevel flowLevel,String flowId) {
    	List<WfFlow> list = flowService.findByFlowLevelAndParentFlowIdNotNull(flowLevel,flowId);
    	//设置首个选择为 无
    	WfFlow flow = new WfFlow();
    	flow.setId("");
    	flow.setFlowName("无");
    	list.add(0, flow);  
    	return list;
	}
}
