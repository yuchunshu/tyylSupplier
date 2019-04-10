package cn.com.chaochuang.pubaffairs.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.pubaffairs.car.domain.OfficialCar;
import cn.com.chaochuang.pubaffairs.car.service.OfficialCarService;
import cn.com.chaochuang.pubaffairs.leave.domain.OaLeave;
import cn.com.chaochuang.pubaffairs.leave.service.OaLeaveService;
import cn.com.chaochuang.pubaffairs.repair.domain.OaEquipmentRepair;
import cn.com.chaochuang.pubaffairs.repair.service.OaRepairService;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.def.domain.WfNodeLine;
import cn.com.chaochuang.workflow.def.repository.WfFlowRepository;
import cn.com.chaochuang.workflow.def.service.WfNodeLineService;
import cn.com.chaochuang.workflow.def.service.WfNodeService;
import cn.com.chaochuang.workflow.event.service.WorkflowService;
import cn.com.chaochuang.workflow.inst.domain.WfAuditOpinion;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfAuditOpinionService;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfApprResult;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfInstStatus;
import cn.com.chaochuang.workflow.util.WorkflowUtils;

/**
 * @author dengl 2018.01.05
 *
 */
public class PubCommonController {
	
	@Autowired
    private OfficialCarService 			carService;
    
    @Autowired
    protected ConversionService    		conversionService;
    
    @Autowired
    private WfNodeInstService      		nodeInstService;
    
    @Autowired
    private WorkflowService        		workFlowService;
    
    @Autowired
    private WfNodeService          		nodeService;
    
    @Autowired
    private WfNodeLineService      		nodeLineService;
    
    @Autowired
    private WfAuditOpinionService  		auditOpinionService;
    
    @Autowired
    private WfFlowRepository 	   		flowRepository;
    
    @Autowired
    private OaLeaveService      		oaLeaveService;
    
    @Autowired
	private OaRepairService  			repairService;
    
    @Autowired
    private SysAttachService    		attachService;
    
    public ModelAndView openNewPage(WfBusinessType busType) {
    	ModelAndView mav = null;
    	SysDepartment dept = (SysDepartment)UserTool.getInstance().getCurrentUserDepartment();
		SysUser currUser = (SysUser)UserTool.getInstance().getCurrentUser();
		
    	if(WfBusinessType.车辆申请.equals(busType)){
    		mav = new ModelAndView("/pubaffairs/car/new");
    	}else if(WfBusinessType.休假申请.equals(busType)){
    		mav = new ModelAndView("/pubaffairs/leave/edit");
    	}else if(WfBusinessType.故障报告申请.equals(busType)){
    		mav = new ModelAndView("/pubaffairs/repair/new");
    	}else if(WfBusinessType.外出申请.equals(busType)){
    		mav = new ModelAndView("/pubaffairs/car/attendance/new");
    	}else{
    		mav = new ModelAndView("/pubaffairs/meal/new");
    	}
		mav.addObject("dept",dept);
		mav.addObject("currUser",currUser);
		return mav;
    }
    
    public ModelAndView openDealPage(String opflag, String id, String fileId, WfBusinessType busType) {
    	ModelAndView mav = null;
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();

        WfNodeInst nodeInst = nodeInstService.findOne(id);
        if (nodeInst == null) {
        	mav = new ModelAndView("/doc/docevent/message");
            mav.addObject("hints", "该任务出现异常，编号为空！");
            return mav;
        }
        
        boolean isReadNode = WorkflowUtils.NODE_READ.equals(nodeInst.getCurNodeId());
        
        if (!user.getId().equals(nodeInst.getDealerId())) {
            mav = new ModelAndView("/doc/docevent/message");
            mav.addObject("hints", "该任务不在您的办理范围内！");
            return mav;
        }
        if (!isReadNode && WfInstStatus.办结.equals(nodeInst.getInstStatus())) {
            mav = new ModelAndView("/doc/docevent/message");
            mav.addObject("hints", "该任务已经被其它人办理，请刷新或重新进入车辆审核页面！");
            return mav;
        }
        
        String vm = "";
        if (isReadNode) {
        	vm = "form_noderead";
        } else {
        	vm = "form_nodedeal";
        }
        
        if(WfBusinessType.车辆申请.equals(busType)){
        	mav = new ModelAndView("/pubaffairs/car/" + vm);
            OfficialCar obj = this.carService.findOne(fileId);
            if (obj == null) {
                mav = new ModelAndView("/doc/docevent/message");
                mav.addObject("hints", "用车申请信息读取失败，该用车申请可能已被删除，请刷新或重新进入列表页面。");
                return mav;
            }
            // 更新节点已读状态
            workFlowService.updateNodeReadStatus(obj.getFlowInstId(), nodeInst.getCurNodeId(), user.getId());
            mav.addObject("opinionList", auditOpinionService.findAuditOpinionsByFlowInstIdAndResult(obj.getFlowInstId(), WfApprResult.通过));
            mav.addObject("obj", obj);
            mav.addObject("type", WfBusinessType.车辆申请.getKey());
            mav.addObject("attachMap", this.attachService.getAttachMap(obj.getId(), OfficialCar.class.getSimpleName()));

        }else if(WfBusinessType.休假申请.equals(busType)){
        	mav = new ModelAndView("/pubaffairs/leave/" + vm);
            OaLeave obj = this.oaLeaveService.findOne(fileId);
            if (obj == null) {
                mav = new ModelAndView("/doc/docevent/message");
                mav.addObject("hints", "休假申请信息读取失败，该休假申请可能已被删除，请刷新或重新进入列表页面。");
                return mav;
            }
            // 更新节点已读状态
            workFlowService.updateNodeReadStatus(obj.getFlowInstId(), nodeInst.getCurNodeId(), user.getId());
            mav.addObject("opinionList", auditOpinionService.findAuditOpinionsByFlowInstIdAndResult(obj.getFlowInstId(), WfApprResult.通过));
            mav.addObject("obj", obj);
            mav.addObject("type", WfBusinessType.休假申请.getKey());
            mav.addObject("attachMap", this.attachService.getAttachMap(obj.getId(), OaLeave.class.getSimpleName()));
            
        }else if(WfBusinessType.故障报告申请.equals(busType)){
        	mav = new ModelAndView("/pubaffairs/repair/" + vm);
        	OaEquipmentRepair obj = this.repairService.findOne(fileId);
            if (obj == null) {
                mav = new ModelAndView("/doc/docevent/message");
                mav.addObject("hints", "故障报告申请信息读取失败，该故障报告申请可能已被删除，请刷新或重新进入列表页面。");
                return mav;
            }
            // 更新节点已读状态
            workFlowService.updateNodeReadStatus(obj.getFlowInstId(), nodeInst.getCurNodeId(), user.getId());
            mav.addObject("opinionList", auditOpinionService.findAuditOpinionsByFlowInstIdAndResult(obj.getFlowInstId(), WfApprResult.通过));
            mav.addObject("obj", obj);
            mav.addObject("type", WfBusinessType.故障报告申请.getKey());
            mav.addObject("attachMap", this.attachService.getAttachMap(obj.getId(), OaEquipmentRepair.class.getSimpleName()));

        }else if(WfBusinessType.外出申请.equals(busType)){
        	mav = new ModelAndView("/pubaffairs/car/attendance/" + vm);
        	OfficialCar obj = this.carService.findOne(fileId);
            if (obj == null) {
                mav = new ModelAndView("/doc/docevent/message");
                mav.addObject("hints", "外出申请信息读取失败，该外出申请可能已被删除，请刷新或重新进入列表页面。");
                return mav;
            }
            // 更新节点已读状态
            workFlowService.updateNodeReadStatus(obj.getFlowInstId(), nodeInst.getCurNodeId(), user.getId());
            mav.addObject("opinionList", auditOpinionService.findAuditOpinionsByFlowInstIdAndResult(obj.getFlowInstId(), WfApprResult.通过));
            mav.addObject("obj", obj);
            mav.addObject("type", WfBusinessType.外出申请.getKey());
            mav.addObject("attachMap", this.attachService.getAttachMap(obj.getId(), OfficialCar.class.getSimpleName()));

        }
        
        // 获取当前节点意见
        WfAuditOpinion auditOpinion = workFlowService.getAuditOpinionByNodeInstId(nodeInst.getId());
        if (!isReadNode) {
            // 获取当前节点功能定义
            WfNode curNode = nodeService.findOne(nodeInst.getCurNodeId());
            List<String> funcList = new ArrayList<String>();
            if (curNode.getFuncBtns() != null) {
                funcList = Arrays.asList(curNode.getFuncBtns().split(","));
            }
            mav.addObject("curNode", curNode);
            mav.addObject("funcList", funcList);

        	//定义有回退环节
            int backBtns = 0;
            List<WfNodeLine> blist = this.nodeLineService.getBackNodes(curNode.getFlowId(), curNode.getId());
            if (blist != null && !blist.isEmpty()) {
            	List list = new ArrayList();
                for (WfNodeLine line: blist) {
              		list.add(line.getToNode());
                   	backBtns++;
                }
            	mav.addObject("backNodeList", list);
            }
            mav.addObject("backBtns", backBtns);
        }
        // 读取审批意见
        mav.addObject("opflag", opflag);
        mav.addObject("curNodeInstId", id);
        mav.addObject("oppr", (auditOpinion == null) ? "" : auditOpinion.getApprOpinion());
        mav.addObject("flowList", flowRepository.findByFlowTypeInOrderByFlowTypeAsc(new WfBusinessType[]{WfBusinessType.车辆申请}));
		return mav;
	}
}
