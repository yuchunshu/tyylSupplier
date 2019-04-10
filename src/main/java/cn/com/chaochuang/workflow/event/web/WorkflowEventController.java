/*
B * FileName:    NoticeController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.workflow.event.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.select.bean.UserSelectShowBean;
import cn.com.chaochuang.workflow.def.bean.NodeDeptSelectTreeBean;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.def.service.WfNodeDeptService;
import cn.com.chaochuang.workflow.def.service.WfNodeService;
import cn.com.chaochuang.workflow.event.service.WorkflowService;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;

/**
 * @author hzr 2017.6.6
 *
 */
@Controller
@RequestMapping("workflow/event")
public class WorkflowEventController {

    @Autowired
    private WorkflowService        workflowService;

    @Autowired
    private WfNodeInstService      nodeInstService;

    @Autowired
    private WfNodeService          nodeService;
    
    @Autowired
    private WfNodeDeptService      nodeDeptService;


    /**
     * 历史轨迹
     */
    @RequestMapping("/listHisTask")
    public ModelAndView histasklist(String flowInstId) {
        ModelAndView mav = new ModelAndView("/workflow/inner/histask_list");

        List<WfNodeInst> nodeList = nodeInstService.findByFlowInstId(flowInstId);

        mav.addObject("nodeList", nodeList);
        return mav;
    }


    /**
     * 通过公文id获取历史轨迹
     */
    @RequestMapping("/getTasklist")
    public ModelAndView histasklistByFileId(String entityId) {
        ModelAndView mav = new ModelAndView("/workflow/inner/histask_list");

        List<WfNodeInst> nodeList = nodeInstService.findByEntityId(entityId);

        mav.addObject("nodeList", nodeList);
        return mav;
    }


    /**
     * 返回办理过的流程环节ID列表（列表中最后一个ID为当前办理环节），用于流程图显示。注意：因为是实例，所以有重复办理过的ID
     */
    @RequestMapping("/selectNodeInstIds.json")
    @ResponseBody
    public List<String> selectNodeInstIds(String entityId) {
        return this.nodeInstService.selectNodeInstIds(entityId);
    }


    /**
     * 选择下一办理环节和办理人
     */
    @RequestMapping("/selectNextTask")
    public ModelAndView selectNextTask(String flowId, String nodeId) {
        ModelAndView mav = new ModelAndView("/selectdialog/selectNextTask");
        try {
            // if (StringUtils.isBlank(flowId)) {
            // flowId = DocFileUtils.getFlowIdByDocSource(docSource);
            // }
            //List<WfNode> nodes = workflowService.selectBranchNodeByFlowIdAndNodeId(flowId, nodeId);
            //mav.addObject("nodes", nodes);
            mav.addObject("flowId", flowId);
            mav.addObject("nodeId", nodeId);
            //mav.addObject("nodeDatas", this.nodeDeptService.allNodeDept(flowId, nodes));
            mav.addObject("manOrDept", this.nodeDeptService.nextNodeManOrDept(flowId, nodeId));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return mav;
    }


    @RequestMapping("/getNextNodes")
    public ModelAndView getNextNodes(String flowId, String nodeId, String curNodeInstId, String startflow) {
        ModelAndView mav = new ModelAndView("/selectdialog/selectNextNodes");
        
        if (YesNo.是.getKey().equals(startflow)) {
        	nodeId = this.nodeService.getFirstTrueNode(flowId).getId();
        }
        
        List<WfNode> nodes = workflowService.selectBranchNodeByFlowIdAndNodeId(flowId, nodeId);
        mav.addObject("nodes", nodes);
        mav.addObject("nodesCount", nodes.size());
        mav.addObject("flagBean", workflowService.getNextNodeDataBeforeDealing(flowId, nodeId, curNodeInstId));
        mav.addObject("startflow", startflow);

        return mav;
    }


    /**
     * 选择下一办理环节
     */
    @RequestMapping("/getNextNodes.json")
    @ResponseBody
    public List<WfNode> selectNextNodes(String flowId, String nodeId) {
        List<WfNode> nodes = workflowService.selectBranchNodeByFlowIdAndNodeId(flowId, nodeId);

        return nodes;
    }

    @RequestMapping("/selectReceiver.json")
    @ResponseBody
    public List<UserSelectShowBean> selectReceiver(String flowId, String nodeId, String __LIKE__userName) {
        return this.nodeDeptService.selectReceiver(flowId, nodeId, __LIKE__userName);
    }

    @RequestMapping("/selectDeps.json")
    @ResponseBody
    public List<NodeDeptSelectTreeBean> selectDeps(String flowId, String nodeId) {
        return this.nodeDeptService.selectDeps(flowId, nodeId);
    }

    @RequestMapping("/selectHistory.json")
    @ResponseBody
    public List<UserSelectShowBean> selectHistory(String nodeId) {
        return this.nodeDeptService.selectHistory(nodeId);
    }


    @RequestMapping("/selectTask")
    public ModelAndView tastInfoList(@RequestParam("id") String id, @RequestParam("taskId") String taskId,
                    @RequestParam("ftype") String ftype) {
        ModelAndView mav = new ModelAndView("/selectdialog/selectTaskForm");
        return mav;
    }

}
