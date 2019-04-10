package cn.com.chaochuang.pubaffairs.repair.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.letter.domain.DocDepLetter;
import cn.com.chaochuang.doc.letter.service.DocDepLetterService;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.repair.bean.OaRepairEditBean;
import cn.com.chaochuang.pubaffairs.repair.domain.OaEquipmentRepair;
import cn.com.chaochuang.workflow.def.service.WfNodeService;
import cn.com.chaochuang.workflow.event.service.WorkflowService;
import cn.com.chaochuang.workflow.his.bean.HisDealerEditBean;
import cn.com.chaochuang.workflow.his.service.WfHisDealerService;
import cn.com.chaochuang.workflow.inst.bean.FlowInstCompleteBean;
import cn.com.chaochuang.workflow.inst.bean.FlowInstStartBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstBringBackBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstDealBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstReturnBean;
import cn.com.chaochuang.workflow.inst.domain.WfAuditOpinion;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfFlowInstService;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfDoneResult;

/**
 * @author dengl 2018.08.10
 *
 */
@Component
@Transactional
public class PubRepairService {

	@Autowired
    private WorkflowService              	workflowService;
    
    @Autowired
    private OaRepairService 				repairService;
    
    @Autowired
    private LogService                   	logService;
    
    @Autowired
    private WfNodeService            		nodeService;
    
    @Autowired
    private WfNodeInstService            	nodeInstService;
    
    @Autowired
    private WfFlowInstService            	flowInstService;
    
    @Autowired
    private WfHisDealerService           	hisDealerService;
    
    @Autowired
    private DocDepLetterService          	depLetterService;
    
	/** 提交故障报告申请,启动故障报告流程*/
	public ReturnBean createFlowData(OaRepairEditBean bean, HttpServletRequest request) throws ParseException {
		SysUser user = bean.getCurrentUser();
		bean.setStartFlag(true);
		if(bean != null){
			bean.setStatus(CarStatus.在办);
		}
		ReturnBean ret = this.repairService.saveRepair(bean,request);
		if (!ret.isSuccess()) return ret;
		OaEquipmentRepair obj = (OaEquipmentRepair)ret.getObject();
		
		// 启动流程
        FlowInstStartBean startBean = new FlowInstStartBean();
        startBean.setFlowId(bean.getFlowId());
        startBean.setEntityId(obj.getId());
        startBean.setTitle(obj.getFailureReason());//故障原因
        startBean.setBusinessType(WfBusinessType.故障报告申请.getKey());
        startBean.setDealer(user);
        Map startMap = this.workflowService.startWorkflow(startBean);
        WfFlowInst flowInst = (WfFlowInst)startMap.get("flowInst");
        WfNodeInst firstNodeInst = (WfNodeInst)startMap.get("firstNodeInst");
        if (flowInst == null) {
            throw new RuntimeException("流程启动失败，请检查流程配置。");
        }
        obj.setFlowId(flowInst.getFlowId());
        obj.setFlowInstId(flowInst.getId());
        repairService.persist(obj);
        bean.setFlowInstId(flowInst.getId());
        // 处理首环节
        bean.setCurNodeInstId(firstNodeInst.getId());
        bean.setCurNodeId(flowInst.getCurNodeId());
        bean.setDocEditable(YesNo.否);// 已经保存过了，不再重复
        this.completeNodeDone(bean, request);
        
        
        
        this.logService.saveUserLog(user, SjType.普通操作, "启动流程：" + bean.getFailureReason() + "，实例ID：" + flowInst.getId(),LogStatus.成功,request);
        
        return new ReturnBean("流程启动成功！", flowInst, true);
	}
	
    /**
     * 流程环节处理
     *
     * @param bean
     * @param request
     * @throws ParseException 
     */
    public void completeNodeDone(OaRepairEditBean bean, HttpServletRequest request){
        String nextNodeId = bean.getNextNodeId();
        String nextDealers = bean.getNextDealers();
        // 保存故障报告申请信息
        OaEquipmentRepair obj = null;
        if (!bean.isStartFlag()) {
            // 非启动环节，更新故障报告申请信息
        	ReturnBean ret = this.repairService.updateRepairInfo(bean,request);
            if (ret.isSuccess()) {
                obj = (OaEquipmentRepair)ret.getObject();
            } else {
            	throw new RuntimeException(ret.getMessage());
            }
        } else {
        	obj = this.repairService.findOne(bean.getId());
        }

        SysUser user = bean.getCurrentUser();

        // 设置审批意见
        WfAuditOpinion auditOpinion = new WfAuditOpinion();
        auditOpinion.setApprOpinion(bean.getOpinion());
        if (bean.getOpinionDate() != null) {
            auditOpinion.setApprTime(bean.getOpinionDate());
        }

        // 构成处理信息
        NodeInstDealBean dealBean = new NodeInstDealBean();
        dealBean.setEntityId(bean.getId());
        dealBean.setTitle(obj.getFailureReason());
        dealBean.setBusinessType(WfBusinessType.故障报告申请.getKey());
        dealBean.setNodeId(bean.getCurNodeId());
        dealBean.setDealer(user.getId());
        dealBean.setNextNodeId(nextNodeId);// 设置下一环节
        if (StringUtils.isNotBlank(nextDealers)) {// 设置下一步处理人
            dealBean.setNextDealer(nextDealers.split(","));
        }

        dealBean.setNode(nodeService.findOne(bean.getCurNodeId()));
        dealBean.setNodeInst(nodeInstService.findOne(bean.getCurNodeInstId()));
        dealBean.setFlowInst(flowInstService.findOne(bean.getFlowInstId()));
        dealBean.setAuditOpinion(auditOpinion);// 设置审批意见
        dealBean.setDeptShare(bean.getDeptShare());//设置本处室共享标识
        
        // 完成当前环节
        WfDoneResult result = workflowService.dealNodeInst(dealBean);

        if (WfDoneResult.环节办结.equals(result)) {
            // 保存环节历史选择
            HisDealerEditBean mansBean = new HisDealerEditBean();
            mansBean.setNodeId(nextNodeId);
            mansBean.setOwnerId(user.getId());
            mansBean.setUseTime(new Date());
            mansBean.setDealerIds(nextDealers);
            hisDealerService.updateHisDealer(mansBean);

        } else if (WfDoneResult.流程办结.equals(result)) {
            // 设置公文完结
            obj.setStatus(CarStatus.办结);
            repairService.save(obj);
        }
    }
    
    /**
     * 完结故障报告申请流程
     *
     * @param bean
     * @param request
     */
    public void completeFlowData(OaRepairEditBean bean, HttpServletRequest request) {
        // 保存申请信息
    	repairService.finishTheRepair(bean.getId());

        // 完成流程
        FlowInstCompleteBean completeBean = new FlowInstCompleteBean();
        completeBean.setEntityId(bean.getId());
        completeBean.setCurNodeId(bean.getCurNodeId());
        completeBean.setDealerId(bean.getCurrentUser().getId());
        completeBean.setOpinion(bean.getOpinion());
        if (bean.getOpinionDate() != null) {
            completeBean.setOpinionDate(bean.getOpinionDate());
        }
        completeBean.setFlowInst(flowInstService.getByFlowIdAndEntityId(bean.getFlowId(),  bean.getId()));
        workflowService.completeWorkflow(completeBean);
    }
    
    /**
     * 退回环节
     *
     * @param file
     * @param nodeId
     * @param opinion
     * @param request
     */
    public void returnNodeInst(OaEquipmentRepair repair, WfNodeInst nodeInst, String backNodeId, String opinion, Long userId,
                    HttpServletRequest request) {
        // 环节退回
        NodeInstReturnBean returnBean = new NodeInstReturnBean();
        returnBean.setFlowInstId(nodeInst.getFlowInstId());
        returnBean.setEntityId(repair.getId());
        returnBean.setNodeInst(nodeInst);
        returnBean.setDealerId(userId);
        returnBean.setOpinion(opinion);
        workflowService.returnNodeInst(returnBean, backNodeId);

    }
    
    public String bringBackNodeInst(OaEquipmentRepair obj, String nodeId, SysUser user, HttpServletRequest request) {
		// 取回当前环节
        NodeInstBringBackBean backBean = new NodeInstBringBackBean();
        backBean.setEntityId(obj.getId());
        backBean.setCurNodeId(nodeId);
        backBean.setDealer(user);
        String res = workflowService.bringBackNodeInst(backBean);
        if (res == null) {
        	logService.saveUserLog(user, SjType.普通操作, "取回当前环节：" + "故障报告申请编号ID：'" + obj.getId() + "，环节id：" + nodeId,LogStatus.成功, request);
        	return null;
        } else {
        	return res;
        }
	}
    
    /**
     * 删除故障报告申请流程
     *
     * @param fileId
     * @param request
     */
    public void deleteFlowData(String fileId, HttpServletRequest request) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();

        OaEquipmentRepair obj = this.repairService.findOne(fileId);
        // 删除流程实例
        workflowService.delWorkflow(fileId);

        // 删除相关的部门函件
        List<DocDepLetter> letters = depLetterService.selectLettersByFileId(fileId);
        String letterIds = Tools.changeArrayToString(letters, "id", ",", false);
        depLetterService.deleteLetter(letterIds.split(","));
        
        // 删除故障报告申请信息
        repairService.delRepair(fileId, true);

        logService.saveUserLog(user, SjType.普通操作, "删除故障报告申请信息：" + (obj != null ? obj.getFailureReason() : "") + "，申请ID：'" + fileId,LogStatus.成功, request);
    }
}
