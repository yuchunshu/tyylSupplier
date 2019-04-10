package cn.com.chaochuang.mobile.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.data.persistence.SearchFilter;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.reference.FlowSort;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.mobile.bean.AppDocNodeInfo;
import cn.com.chaochuang.mobile.bean.AppLeaveInfo;
import cn.com.chaochuang.mobile.bean.AppResponseInfo;
import cn.com.chaochuang.mobile.util.AesTool;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatusConverter;
import cn.com.chaochuang.pubaffairs.leave.bean.OaLeaveEditBean;
import cn.com.chaochuang.pubaffairs.leave.bean.OaLeaveQueryBean;
import cn.com.chaochuang.pubaffairs.leave.bean.OaLeaveShowBean;
import cn.com.chaochuang.pubaffairs.leave.domain.OaLeave;
import cn.com.chaochuang.pubaffairs.leave.reference.LeaveTypeConverter;
import cn.com.chaochuang.pubaffairs.leave.repository.OaLeaveRepository;
import cn.com.chaochuang.pubaffairs.leave.service.OaLeaveService;
import cn.com.chaochuang.pubaffairs.leave.service.PubLeaveService;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.def.domain.WfNodeLine;
import cn.com.chaochuang.workflow.def.service.WfNodeLineService;
import cn.com.chaochuang.workflow.def.service.WfNodeService;
import cn.com.chaochuang.workflow.event.service.WorkflowService;
import cn.com.chaochuang.workflow.inst.domain.WfAuditOpinion;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfAuditOpinionService;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfInstStatus;
import cn.com.chaochuang.workflow.util.WorkflowUtils;

/**
 * @author dengl 2018.8.28
 * 移动端休假管理接口
 */

@Controller
@RequestMapping(value = "/mobile/leave")
public class MobileLeaveController extends MobileController {

    @Autowired
    private ConversionService    	conversionService;
    
    @Autowired
    private OaLeaveService      	oaLeaveService;
    
    @Autowired
    private WfNodeInstService      	nodeInstService;
    
    @Autowired
	private WfAuditOpinionService 	auditOpinionService;
    
    @Autowired
    private WorkflowService        	workFlowService;
    
    @Autowired
    private WfNodeService          	nodeService;
    
    @Autowired
    private WfNodeLineService      	nodeLineService;
    
    @Autowired
    private OaLeaveRepository		leaveRepository;
    
    @Autowired
    private	PubLeaveService			pubLeaveService;
    
    @Autowired
    private SysAttachService 		attachService;
    

    /**我的休假申请列表页面*/
    @RequestMapping(value = {"/myLeaveList.mo"})
    @ResponseBody
    public AppResponseInfo myLeaveList(HttpServletRequest request, Boolean aesFlag_) {
    	SysUser user = this.loadCurrentUser(request);
    	if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
    	String page = request.getParameter("page");
        String rows = request.getParameter("rows");
    	
        //查询条件（可选）
    	String status = request.getParameter("status");
    	String reason = request.getParameter("reason");
    	String leaveType = request.getParameter("leaveType");
    	String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        
        Date dt1 = null;
        Date dt2 = null;
		try {
			if (StringUtils.isNotBlank(fromDate)) {
				dt1 = Tools.DATE_TIME_FORMAT.parse(fromDate);
	        }
			if (StringUtils.isNotBlank(toDate)) {
				dt2 = Tools.DATE_TIME_FORMAT.parse(toDate);
	        }
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
		SearchBuilder<OaLeave, String> searchBuilder = new SearchBuilder<OaLeave, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("creatorId", user.getId());
        
        if (StringUtils.isNotBlank(reason)) {
            searchBuilder.getFilterBuilder().like("reason", reason);
        }
        if (StringUtils.isNotBlank(leaveType)) {
        	searchBuilder.getFilterBuilder().equal("leaveType", new LeaveTypeConverter().convertToEntityAttribute(leaveType));
        }
        if (StringUtils.isNotBlank(status)) {
            searchBuilder.getFilterBuilder().equal("status", new CarStatusConverter().convertToEntityAttribute(status));
        }
        if (null != dt1) {
        	searchBuilder.getFilterBuilder().greaterThanOrEqualTo("createDate", dt1);
        }
        if (null != dt2) {
        	searchBuilder.getFilterBuilder().lessThanOrEqualTo("createDate", dt2);
        }
        
        searchBuilder.appendSort(Direction.DESC, "createDate");
        
        SearchListHelper<OaLeave> listhelper = new SearchListHelper<OaLeave>();
        listhelper.execute(searchBuilder, oaLeaveService.getRepository(), Integer.parseInt(page) , Integer.parseInt(rows));
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), OaLeaveShowBean.class));
        p.setTotal(listhelper.getCount());
        
        
        return AesTool.responseData(p,aesFlag_);
    }
    
    /** 休假审核列表页面*/
    @SuppressWarnings("unchecked")
	@RequestMapping(value = {"/myLeaveTaskList.mo"})
    @ResponseBody
    public AppResponseInfo myLeaveTaskList(HttpServletRequest request, Boolean aesFlag_) {
    	SysUser user = this.loadCurrentUser(request);
    	if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
    	String page = request.getParameter("page");
        String rows = request.getParameter("rows");
    	
        //查询条件（可选）
        String reason = request.getParameter("reason");
        String creatorName = request.getParameter("creatorName");
    	String deptName = request.getParameter("deptName");
    	String leaveType = request.getParameter("leaveType");
    	String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        
        Map<String, SearchFilter> thisFilters = new HashMap<String, SearchFilter>();
        Map<String, Object> searchParams = new TreeMap<String, Object>();
        
        if (StringUtils.isNotBlank(reason)) {
        	searchParams.put("LIKE__al.reason", reason);
        }
        if (StringUtils.isNotBlank(creatorName)) {
        	searchParams.put("LIKE__u.userName", creatorName);
        }
        if (StringUtils.isNotBlank(deptName)) {
        	searchParams.put("LIKE__d.deptName", deptName);
        }
        if (StringUtils.isNotBlank(fromDate)) {
        	searchParams.put("GTE__al.beginTime", fromDate);
        }
        if (StringUtils.isNotBlank(toDate)) {
        	searchParams.put("LTE__al.endTime", toDate);
        }
        if (StringUtils.isNotBlank(leaveType)) {
        	searchParams.put("EQ__al.leaveType", leaveType);
        }
        
        
        if (MapUtils.isNotEmpty(searchParams)) {
            thisFilters = SearchFilter.parse(searchParams);
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Page p = new Page();
        dataMap = this.oaLeaveService.leaveSelect(thisFilters.values(),user,Integer.parseInt(page),Integer.parseInt(rows));
        p.setRows((ArrayList<OaLeaveQueryBean>) dataMap.get("rows"));
        p.setTotal(dataMap.get("total") == null ? (long) 0 : (long) dataMap.get("total"));
        
        return AesTool.responseData(p,aesFlag_);
    }

    /** 打开办理页面 */
    @RequestMapping(value = { "/dealPage.mo" })
    @ResponseBody
    public AppResponseInfo taskDealPageJson(HttpServletRequest request, Boolean aesFlag_) {
        String id = request.getParameter("id");
        String entityId = request.getParameter("entityId");
        if (StringUtils.isBlank(entityId)) {
            return AesTool.responseData("0", "申请ID为空，参数错误！", null, aesFlag_);
        }
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }

        WfNodeInst nodeInst = nodeInstService.findOne(id);
        if (nodeInst == null) {
            return AesTool.responseData("0", "该任务出现异常，编号为空！", null, aesFlag_);
        }

        boolean isReadNode = WorkflowUtils.NODE_READ.equals(nodeInst.getCurNodeId());

        if (!user.getId().equals(nodeInst.getDealerId())) {
            return AesTool.responseData("0", "该任务不在您的办理范围内！", null, aesFlag_);
        }
        if (!isReadNode && WfInstStatus.办结.equals(nodeInst.getInstStatus())) {
            return AesTool.responseData("0", "该任务已经被其他人办理，请刷新或重新进入待办页面！", null, aesFlag_);
        }

        OaLeave obj = this.oaLeaveService.findOne(entityId);
        if (obj == null) {
            return AesTool.responseData("0", "找不到相应的申请！", null, aesFlag_);
        }
        AppLeaveInfo bean = BeanCopyUtil.copyBean(obj, AppLeaveInfo.class);

        // 获取办理节点信息
        String curNodeId = workFlowService.getNodeIdByFlowInstIdAndDealer(obj.getFlowInstId(), user.getId());
        // 更新节点已读状态
        workFlowService.updateNodeReadStatus(obj.getFlowInstId(), nodeInst.getCurNodeId(), user.getId());
        // 获取当前节点意见
        WfAuditOpinion auditOpinion = workFlowService.getAuditOpinionByNodeInstId(nodeInst.getId());
        bean.setOpinion((auditOpinion == null) ? "" : auditOpinion.getApprOpinion());
        bean.setCurNodeId(curNodeId);
        if (!isReadNode) {
            // 获取当前节点功能定义
            WfNode curNode = nodeService.findOne(nodeInst.getCurNodeId());
            List<String> funcList = new ArrayList<String>();
            if (curNode.getFuncBtns() != null) {
                funcList = Arrays.asList(curNode.getFuncBtns().split(","));
            }
            bean.setCurNodeId(curNode.getId());
            bean.setFlowId(curNode.getFlowId());
            bean.setFuncList(funcList);

            // 定义有回退环节
            List<WfNodeLine> blist = this.nodeLineService.getBackNodes(curNode.getFlowId(), curNode.getId());
            if (blist != null && !blist.isEmpty()) {
                List<WfNode> list = new ArrayList<WfNode>();
                for (WfNodeLine line : blist) {
                    list.add(line.getToNode());
                }
                bean.setBackNodeList(list);
            }


        }

        bean.setCurNodeInstId(id);
        
        // 加载附件
        bean.setAttachList(attachService.findByOwnerIdAndOwnerType(obj.getId(),OaLeave.class.getSimpleName()));

        return AesTool.responseData(bean, aesFlag_);
    }
    
    /** 取得某个公文的审批环节过程及意见列表 */
    @RequestMapping(value = { "/historyList.mo" })
    @ResponseBody
    public AppResponseInfo getTaskHistoryListJson(HttpServletRequest request, Boolean aesFlag_) {
        String entityId = request.getParameter("entityId");
        OaLeave obj = this.oaLeaveService.findOne(entityId);
        if (obj == null) {
            return AesTool.responseData("0", "找不到相应的申请！", null, aesFlag_);
        }
        // 读取审批意见
        List<WfAuditOpinion> opinionList = auditOpinionService.findAuditOpinionsByFlowInstId(obj.getFlowInstId());
        List<WfNodeInst> nodeList = nodeInstService.findByEntityId(entityId);
        List<AppDocNodeInfo> list = new ArrayList<AppDocNodeInfo>(); // BeanCopyBuilder.buildList(nodeList, AppDocNodeInfo.class);

        for (WfNodeInst node : nodeList) {
            AppDocNodeInfo bean = new AppDocNodeInfo();
            bean.setArrivalTime(node.getArrivalTime());
            bean.setCandidates(node.getCandidates());
            bean.setDealerId(node.getDealerId());
            bean.setDealerName(node.getDealer().getUserName());
            bean.setNodeId(node.getCurNodeId());
            bean.setNodeName(node.getCurNode().getNodeName());
            bean.setStatus(node.getInstStatus().getKey());
            bean.setReadFlag(node.getReadFlag().getKey());
            bean.setDealTime(node.getDealTime());
            bean.setArrivalTime(node.getArrivalTime());
            bean.setOpinion("");

            for (WfAuditOpinion op : opinionList) {
                if (op.getNodeInstId().equals(node.getId())) {
                    bean.setOpinion(StringUtils.isBlank(op.getApprOpinion()) ? "" : op.getApprOpinion());
                }
            }
            list.add(bean);
        }

        return AesTool.responseData(list, aesFlag_);
    }
    
    /** 申请详情页面 */
    @RequestMapping(value = { "/detail.mo" })
    @ResponseBody
    public AppResponseInfo detail(HttpServletRequest request, Boolean aesFlag_) {
        String entityId = request.getParameter("id");
        if (StringUtils.isBlank(entityId)) {
            return AesTool.responseData("0", "申请ID为空，参数错误！", null, aesFlag_);
        }
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }

        OaLeave obj = this.oaLeaveService.findOne(entityId);
        if (obj == null) {
            return AesTool.responseData("0", "找不到相应的申请！", null, aesFlag_);
        }
        AppLeaveInfo bean = BeanCopyUtil.copyBean(obj, AppLeaveInfo.class);

        // 加载附件
        bean.setAttachList(attachService.findByOwnerIdAndOwnerType(obj.getId(),OaLeave.class.getSimpleName()));
        
        return AesTool.responseData(bean, aesFlag_);
    }
    
    /** 提交审核办理 */
    @RequestMapping(value = { "/task/submit.mo" })
    @ResponseBody
    public AppResponseInfo taskSubmitJson(HttpServletRequest request, Boolean aesFlag_) {
        String taskId = request.getParameter("tid");
        String entityId = request.getParameter("entityId");
        String nextNodeId = request.getParameter("nextNodeId");
        String nextDealers = request.getParameter("nextDealers");
        String opinion = request.getParameter("opinion");
        String ver = request.getParameter("ver"); // 文件版本号，以避免提交冲突

        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }

        WfNodeInst ni = nodeInstService.findOne(taskId);

        if (ni == null) {
            return AesTool.responseData("0", "流程错误，没有找到任务实例，请联系管理员！", null, aesFlag_);
        } else if (ni != null && WfInstStatus.办结.equals(ni.getInstStatus())) {
            return AesTool.responseData("0", "任务已失效，可能已被其他人抢先执行！", null, aesFlag_);
        } else {
        	OaLeaveEditBean bean = new OaLeaveEditBean();
            bean.setNextNodeId(nextNodeId);
            bean.setNextDealers(nextDealers);
            bean.setId(entityId);
            bean.setOpinion(opinion);
            bean.setStartFlag(false);
            bean.setCurNodeId(ni.getCurNodeId());
            bean.setCurNodeInstId(ni.getId());
            bean.setFlowId(ni.getFlowInst().getFlowId());
            bean.setFlowInstId(ni.getFlowInstId());
            bean.setCurrentUser(user);
            bean.setVersion_(StringUtils.isBlank(ver) ? 1 : new Integer(ver));

            pubLeaveService.completeNodeDone(bean, request);

            if (nextNodeId.startsWith("N999")) {
                this.logService.saveMobileLog(user, "休假办结：" + ni.getFlowInst().getTitle(), request);
            } else {
                this.logService.saveMobileLog(user, "休假审核：" + ni.getFlowInst().getTitle(), request);
            }

            return AesTool.responseData("", aesFlag_);
        }

    }
    
    /**
     * 启动休假申请流程
     */
    @RequestMapping(value = "dealStartflow.mo", method = RequestMethod.POST)
    @ResponseBody
    public AppResponseInfo startWorkflow(OaLeaveEditBean bean, HttpServletResponse response, HttpServletRequest request, Boolean aesFlag_) {
    	SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
        bean.setCurrentUser(user);
        bean.setCreateDate(new Date());
        bean.setDeptId(user.getDeptId());
        bean.setDeptName(user.getDepartment().getDeptName());
    	// 先判断该休假状态，只有暂存状态下才能启动流程，不允许重复启动
        if (StringUtils.isNotBlank(bean.getId())) {
        	OaLeave obj = this.oaLeaveService.findOne(bean.getId());
            if (obj == null) {
                return AesTool.responseData("0", "假期申请不存在，无法执行操作！", null, aesFlag_);
            }
            if (!CarStatus.暂存.equals(obj.getStatus())) {
                return AesTool.responseData("0", "不允许重复启动流程！", null, aesFlag_);
            }
        }

        try {
	        // 启动流程
	        pubLeaveService.createFlowData(bean, request);
	        return AesTool.responseData("", aesFlag_);
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "移动端：启动流程：《" + bean.getReason() + "》失败！",LogStatus.失败,request);
            return AesTool.responseData("0", "流程启动失败！", null, aesFlag_);
        }

    }
    
    
    /** 休假申请审核数量 */
    @RequestMapping(value = {"/myLeaveTaskCount.mo"}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String myLeaveTaskCount(HttpServletRequest request) {
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return JSONObject.toJSONString(new AppResponseInfo("0", "用户不存在！", null));
        }
        return String.valueOf(this.nodeInstService.getTaskingCount(user.getId(),WfBusinessType.休假申请,FlowSort.其他));
    }
    
    /** 我的休假申请数量 */
    @RequestMapping(value = {"/myLeaveCount.mo"}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String myLeaveCount(HttpServletRequest request) {
    	SysUser user = this.loadCurrentUser(request);
    	if (user == null) {
    		return JSONObject.toJSONString(new AppResponseInfo("0", "用户不存在！", null));
    	}
    	
    	return String.valueOf(this.leaveRepository.countOaLeave(user.getId()));
    }
    
    /** 删除休假*/
    @RequestMapping("/delete.mo")
    @ResponseBody
    public AppResponseInfo delete(String ids, HttpServletRequest request, Boolean aesFlag_) {
        try {
            SysUser user = this.loadCurrentUser(request);
            if (user == null) {
                return AesTool.responseData("0", "用户不存在！", null,aesFlag_);
            }
            if (StringUtils.isNotBlank(ids)) {
                String[] idArr = ids.split(",");
                for (String id : idArr) {
                	OaLeave leave = this.oaLeaveService.findOne(id);
                	String reason = leave.getReason();
                	oaLeaveService.delLeave(leave.getId(), true);
                	logService.saveMobileLog(user, "移动端：删除休假，名称：" + reason, request);
                }
                return AesTool.responseData("1","删除成功",null,aesFlag_);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AesTool.responseData("0","操作失败",null,aesFlag_);
    }
    
    /** 保存休假*/
    @RequestMapping("/save.mo")
    @ResponseBody
    public AppResponseInfo save(OaLeaveEditBean bean, HttpServletRequest request, Boolean aesFlag_) {
    	try {
    		SysUser user = this.loadCurrentUser(request);
            if (user == null) {
                return AesTool.responseData("0", "用户不存在！", null,aesFlag_);
            }
            bean.setCreateDate(new Date());
            bean.setDeptId(user.getDeptId());
            bean.setDeptName(user.getDepartment().getDeptName());
    		ReturnBean returnBean = this.oaLeaveService.saveOaLeaveFlow(bean,request);
    		OaLeave leave=(OaLeave) returnBean.getObject();
    		if(StringUtils.isNoneBlank(leave.getId())){
    			return AesTool.responseData("1","保存成功",null,aesFlag_);
    		}
    		return AesTool.responseData("0","保存失败",null,aesFlag_);
    	}catch (Exception e) {
            e.printStackTrace();
            return AesTool.responseData("0","连接不上服务器",null,aesFlag_);
        }
    }
    
    /** 休假申请退回操作 */
    @RequestMapping(value = { "/task/return.mo" })
    @ResponseBody
    public AppResponseInfo doTaskReturn(HttpServletRequest request, Boolean aesFlag_) {
        String taskId = request.getParameter("tid");
        String entityId = request.getParameter("entityId");
        String backNodeId = request.getParameter("backNodeId");
        String opinion = request.getParameter("opinion");
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }

        try {
        	OaLeave leave = oaLeaveService.findOne(entityId);
            WfNodeInst nodeInst = nodeInstService.findOne(taskId);
            if (leave != null && nodeInst != null) {
            	pubLeaveService.returnNodeInst(leave, nodeInst, backNodeId, opinion, user.getId(), request);
                logService.saveMobileLog(user, "退回休假申请《" + leave.getReason() + "》到" + backNodeId, request);
                return AesTool.responseData("", aesFlag_);
            } else {
                return AesTool.responseData("0", "休假申请不存在，无法执行操作。", null, aesFlag_);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AesTool.responseData("0", "操作失败！" + e.getMessage(), null, aesFlag_);
        }
    }
    
    /**
     * 主动取回到当前环节
     */
    @RequestMapping("dealBringBack.mo")
    @ResponseBody
    public AppResponseInfo taskBringBack(String id, String curNodeId, HttpServletRequest request, Boolean aesFlag_) {
    	SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
    	OaLeave obj = this.oaLeaveService.findOne(id);
        try {
            if (obj != null) {
                String res = pubLeaveService.bringBackNodeInst(obj, curNodeId, user, request);
                if (res == null) {
                	return AesTool.responseData("1","取回成功！",null, aesFlag_);
                } else {
                	return AesTool.responseData("0", res, null, aesFlag_);
                }
            } else {
                return AesTool.responseData("0", "休假申请不存在，无法取回", null, aesFlag_);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "休假申请编号ID:" + obj.getId() + "取回失败！",LogStatus.失败, request);
            return AesTool.responseData("0", "操作失败："+e.getMessage(), null, aesFlag_);
        }
    }
    
    /**
     * 删除、撤文
     */
    @RequestMapping(value = { "deleteflow.mo" })
    @ResponseBody
    public AppResponseInfo deleteWorkflow(String id, HttpServletRequest request, Boolean aesFlag_) {
    	SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
    	OaLeave obj = this.oaLeaveService.findOne(id);
        try {
            if (obj == null) {
            	return AesTool.responseData("0", "申请不存在，无法执行操作！", null, aesFlag_);
            }
            pubLeaveService.deleteFlowData(user, id, request);
            return AesTool.responseData("1","删除成功",null,aesFlag_);
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "休假申请：《" + obj.getReason() + "》删除失败！",LogStatus.失败,request);
            return AesTool.responseData("0", "结束流程失败："+e.getMessage(), null, aesFlag_);
        }
    }
}
