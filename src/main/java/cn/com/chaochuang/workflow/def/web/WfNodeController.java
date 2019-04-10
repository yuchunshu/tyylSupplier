package cn.com.chaochuang.workflow.def.web;

import java.util.ArrayList;
import java.util.Arrays;
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
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.serial.service.SerialNumberService;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.workflow.def.bean.NodeEditBean;
import cn.com.chaochuang.workflow.def.domain.WfFlow;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.def.domain.WfNodeDept;
import cn.com.chaochuang.workflow.def.domain.WfNodeLine;
import cn.com.chaochuang.workflow.def.service.WfFlowService;
import cn.com.chaochuang.workflow.def.service.WfNodeDeptService;
import cn.com.chaochuang.workflow.def.service.WfNodeLineService;
import cn.com.chaochuang.workflow.def.service.WfNodeService;

/**
 * @author hzr 2016-11-24
 *
 */
@Controller
@RequestMapping("workflow/node")
public class WfNodeController {

    @Autowired
    private WfFlowService       	flowService;

    @Autowired
    private WfNodeService   	    nodeService;

    @Autowired
    private WfNodeDeptService   	nodeDeptService;

    @Autowired
    private WfNodeLineService   	nodeLineService;

    @Autowired
    private LogService          	logService;

    @Autowired
    private ConversionService   	conversionService;

    @Autowired
    private SysDepartmentService   	deptService;
    
    @Autowired
    private SysUserService         userService;
    
    @Autowired
    private SerialNumberService    serialNumberService;

    /**
     * 列表页面
     */
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/workflow/node/list");
        return mav;
    }

    /**
     * 列表数据
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<WfNode, String> searchBuilder = new SearchBuilder<WfNode, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);

        SearchListHelper<WfNode> listhelper = new SearchListHelper<WfNode>();
        listhelper.execute(searchBuilder, nodeService.getRepository(), page, rows);

        Page p = new Page();
        p.setRows(listhelper.getList());
        p.setTotal(listhelper.getCount());
        return p;
    }

    /**
     * 新增页面
     */
    @RequestMapping("/new")
    public ModelAndView newPage(String flowId) {
        ModelAndView mav = new ModelAndView("/workflow/node/edit");
        mav.addObject("flow", this.flowService.findOne(flowId));
        //推荐环节编号
        mav.addObject("newId", this.serialNumberService.getNewSN(WfNode.class.getSimpleName(), "N", "0000"));
        return mav;
    }

    /**
     * 编辑页面
     */
    @RequestMapping("/edit")
    public ModelAndView editPage(String id) {
        ModelAndView mav = new ModelAndView("/workflow/node/edit");
        if (id == null) return null;

        WfNode node = nodeService.findOne(id);
        if (node == null) return null;

        mav.addObject("obj", node);

        // 下一环节s
        List<WfNodeLine> nextlist = this.nodeLineService.getNextNodes(node.getFlowId(), node.getId());
        if(nextlist != null && !nextlist.isEmpty()){
        	String nextNodeIds = "";
        	String nextNodeNames = "";
            for (WfNodeLine line: nextlist) {
            	if ("".equals(nextNodeIds)) {
            		nextNodeIds = line.getToNode().getId();
            		nextNodeNames = line.getToNode().getNodeName();
            	} else {
            		nextNodeIds += "," + line.getToNode().getId();
            		nextNodeNames += "," + line.getToNode().getNodeName();
            	}
            }
            mav.addObject("nextNodeIds", nextNodeIds);
            mav.addObject("nextNodeNames", nextNodeNames);
        }

        // 退回环节s
        List<WfNodeLine> blist = this.nodeLineService.getBackNodes(node.getFlowId(), node.getId());
        if(blist != null && !blist.isEmpty()){
        	String backNodeIds = "";
        	String backNodeNames = "";
            for (WfNodeLine line: blist) {
            	if ("".equals(backNodeIds)) {
            		backNodeIds = line.getToNode().getId();
            		backNodeNames = line.getToNode().getNodeName();
            	} else {
            		backNodeIds += "," + line.getToNode().getId();
            		backNodeNames += "," + line.getToNode().getNodeName();
            	}
            }
            mav.addObject("backNodeIds", backNodeIds);
            mav.addObject("backNodeNames", backNodeNames);
        }

        // 自动提交环节
        String autoSubmitNodeId = this.nodeLineService.getAutoSubmitNodeIdByFromNodeId(node.getFlowId(), node.getId());
        if(StringUtils.isNotEmpty(autoSubmitNodeId)){
        	mav.addObject("submitNodeId", autoSubmitNodeId);
        	mav.addObject("submitNodeName", this.nodeService.findOne(autoSubmitNodeId).getNodeName());
        }

        List<String> funcList = new ArrayList<String>();
        if (node.getFuncBtns() != null) {
            funcList = Arrays.asList(node.getFuncBtns().split(","));
            mav.addObject("funcList", funcList);
        }

        //加载可办理部门
        List<WfNodeDept> list = this.nodeDeptService.findByFlowIdAndNodeId(node.getFlowId(), node.getId());
        if (list != null && !list.isEmpty()) {
        	String nodeDeptIds   = "";
        	String nodeDeptNames = "";
        	for(WfNodeDept nd: list) {
        		if (nd.getReceiveDepId() == null) continue;
        		SysDepartment dept = this.deptService.findOne(nd.getReceiveDepId());
        		if (dept == null) continue;
        		if ("".equals(nodeDeptIds)) {
        			nodeDeptIds = dept.getId().toString();
        			nodeDeptNames = dept.getDeptName();
        		} else {
        			nodeDeptIds = nodeDeptIds + "," + dept.getId().toString();
        			nodeDeptNames = nodeDeptNames + "," + dept.getDeptName();
        		}
        	}
        	mav.addObject("nodeDeptIds", nodeDeptIds);
        	mav.addObject("nodeDeptNames", nodeDeptNames);
        }
        
        //加载预选人员名字
        List<SysUser> userList = this.userService.getUsersByIdsGroupByDep(node.getReceiverIds());
        if(userList != null && !userList.isEmpty()){
        	String receiverNames = "";
        	for(SysUser u:userList){
        		if(StringUtils.isNotBlank(u.getUserName())){
        			receiverNames += u.getUserName() + ",";
        		}
        	}
        	if(StringUtils.isNotBlank(receiverNames)){
        		receiverNames = receiverNames.substring(0, receiverNames.length()-1);
        	}
        	mav.addObject("receiverNames", receiverNames);
        }

        return mav;
    }

    /**
     * 详情页面
     */
    @RequestMapping("/detail")
    public ModelAndView detailPage(String id) {
        ModelAndView mav = new ModelAndView("/workflow/node/detail");
        if (id != null) {
            WfNode nodes = nodeService.findOne(id);
            if (nodes != null) {
                mav.addObject("obj", nodes);
            }
        }
        return mav;
    }

    /**
     * 保存数据
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(NodeEditBean bean, HttpServletRequest request) {
        try {
        	if(YesNo.是.equals(bean.getIsNewNode()) && this.nodeService.findOne(bean.getId()) != null){
        		return new ReturnInfo(null, "环节编号已存在", null);
        	}
            String id = nodeService.saveNode(bean);
            if (id != null) {
                if (YesNo.是.equals(bean.getIsNewNode())) {
                    logService.saveDefaultLog("新增工作流环节定义，环节为：" + bean.getId(), request);
                    //编号累加操作在保存后再执行
                	this.serialNumberService.saveNewSN(WfNode.class.getSimpleName(),"N","0000");
                } else {
                    logService.saveDefaultLog("修改工作流环节定义，环节为：" + bean.getId(), request);
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
                    nodeService.delNode(id);
                }
                return new ReturnInfo("1", null, "删除成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReturnInfo(null, "服务器连接不上！", null);
    }
}
