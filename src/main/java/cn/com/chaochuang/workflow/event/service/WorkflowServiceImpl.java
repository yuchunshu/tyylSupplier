package cn.com.chaochuang.workflow.event.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.event.domain.OaDocDeptFile;
import cn.com.chaochuang.doc.event.service.OaDocDeptFileService;
import cn.com.chaochuang.oa.message.app.reference.MesType;
import cn.com.chaochuang.oa.message.app.service.AppMessageService;
import cn.com.chaochuang.workflow.def.bean.NodeCommonBean;
import cn.com.chaochuang.workflow.def.domain.WfFlow;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.def.domain.WfNodeDept;
import cn.com.chaochuang.workflow.def.domain.WfNodeLine;
import cn.com.chaochuang.workflow.def.service.WfFlowService;
import cn.com.chaochuang.workflow.def.service.WfNodeDeptService;
import cn.com.chaochuang.workflow.def.service.WfNodeLineService;
import cn.com.chaochuang.workflow.def.service.WfNodeService;
import cn.com.chaochuang.workflow.inst.bean.AuditOpinionEditBean;
import cn.com.chaochuang.workflow.inst.bean.AuditOpinionQueryBean;
import cn.com.chaochuang.workflow.inst.bean.FlowInstCompleteBean;
import cn.com.chaochuang.workflow.inst.bean.FlowInstStartBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstBringBackBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstCheckBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstDealBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstEditBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstQueryBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstReadBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstReadDealBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstReturnBean;
import cn.com.chaochuang.workflow.inst.domain.WfAuditOpinion;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfAuditOpinionService;
import cn.com.chaochuang.workflow.inst.service.WfFlowInstService;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfApprResult;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfDealType;
import cn.com.chaochuang.workflow.reference.WfDoneResult;
import cn.com.chaochuang.workflow.reference.WfEndCond;
import cn.com.chaochuang.workflow.reference.WfFlowDir;
import cn.com.chaochuang.workflow.reference.WfInstStatus;
import cn.com.chaochuang.workflow.reference.WfNodeType;
import cn.com.chaochuang.workflow.reference.WfReadFlag;
import cn.com.chaochuang.workflow.util.WorkflowUtils;

/**
 * @author hzr 2017-2-24
 *
 */
@Service
@Transactional
public class WorkflowServiceImpl implements WorkflowService {

    @Autowired
    private WfFlowService               flowService;

    @Autowired
    private WfFlowInstService           flowInstService;

    @Autowired
    private WfNodeService               nodeService;

    @Autowired
    private WfNodeLineService           nodeLineService;

    @Autowired
    private WfNodeInstService           nodeInstService;

    @Autowired
    private WfNodeDeptService           nodeDeptService;

    @Autowired
    private WfAuditOpinionService       auditOpinionService;

    @Autowired
    private SysUserService              userService;

    @Autowired
    private AppMessageService           appMessageService;

    @Autowired
    private OaDocDeptFileService        oaDocDeptFileService;

    @Override
    public Map startWorkflow(FlowInstStartBean bean) {
        String flowId = bean.getFlowId();
        String entityId = bean.getEntityId();
        WfFlow flow = flowService.findOne(flowId);// 查找流程定义
        if (null == flow) {
            throw new RuntimeException("处理失败，请检查流程是否已定义。");
        }

        // 第一个环节定义
        WfNode nextNode = nodeService.getFirstTrueNode(flowId);

        Date now = new Date();
        // 保存流程实例
        WfFlowInst flowInst = new WfFlowInst();
        flowInst.setFlowId(flowId);
        flowInst.setEntityId(entityId);
        flowInst.setTitle(bean.getTitle());
        flowInst.setBusinessType(bean.getBusinessType());
        flowInst.setSncode(bean.getSncode());
        flowInst.setCurNodeId(nextNode.getId());// 设置当前环节
        flowInst.setStartTime(now);// 设置开始时间
        flowInst.setEndTime(bean.getEndTime());// 设置结束时间
        flowInst.setCreatorId(bean.getDealer().getId());
        flowInst.setExpiryDate(bean.getExpiryDate());
        flowInst.setUrgencyLevel(bean.getUrgencyLevel());
        flowInst.setInstStatus(WfInstStatus.在办);// 设置流程状态
        flowInst.setParentEntityId(bean.getParentEntityId());
        flowInstService.persist(flowInst);

        Map res = new HashMap();
        res.put("flowInst", flowInst);
        res.put("node", nextNode);

        // 保存首环节，非N000
        if (flowInst != null) {
            NodeInstEditBean nodeInstBean = new NodeInstEditBean();
            nodeInstBean.setFlowInstId(flowInst.getId());
            nodeInstBean.setCurNodeId(nextNode.getId());
            nodeInstBean.setArrivalTime(null!=bean.getSendTime()?bean.getSendTime():now);
            nodeInstBean.setPreDealerId(bean.getDealer().getId());
            nodeInstBean.setDealerId(bean.getDealer().getId());
            nodeInstBean.setEndCond(WfEndCond.单人办结);
            nodeInstBean.setFlowDir(WfFlowDir.普通);
            nodeInstBean.setDealType(WfDealType.阅办);
            nodeInstBean.setInstStatus(WfInstStatus.在办);
            nodeInstBean.setReadFlag(WfReadFlag.未读);

            WfNodeInst ni = nodeInstService.saveNodeInst(nodeInstBean);
            res.put("firstNodeInst", ni);

            if (ni == null) {
                throw new RuntimeException("处理失败，请检查流程首环节配置是否正确。");
            }
        } else {
            throw new RuntimeException("处理失败，请检查流程配置是否正确。");
        }

        return res;
    }


    /**启动子流程*/
    private void startSubflow(String subFlowId, NodeInstDealBean bean, String parentNodeInstId) {

        // 第一个环节定义
        WfNode firstNode = nodeService.getFirstTrueNode(subFlowId);

        Date now = new Date();

        for (String dealerId : bean.getNextDealer()) {
            if (StringUtils.isBlank(dealerId)) {
                continue;
            }

	        // 保存流程实例，每个办理分支都是一个子实例
            WfFlowInst flowInst = new WfFlowInst();
            flowInst.setFlowId(subFlowId);
            flowInst.setEntityId(bean.getEntityId());
            flowInst.setParentFlowInstId(bean.getFlowInst().getId());
            flowInst.setCurNodeId(firstNode.getId());// 设置当前环节
            flowInst.setTitle(bean.getTitle());
            flowInst.setBusinessType(bean.getBusinessType());
            flowInst.setSncode(bean.getSncode());
            flowInst.setStartTime(now);// 设置开始时间
            flowInst.setInstStatus(WfInstStatus.在办);// 设置流程状态
            flowInst.setExpiryDate(bean.getExpiryDate());
            flowInst.setUrgencyLevel(bean.getUrgencyLevel());
	        flowInstService.persist(flowInst);;


	    	NodeInstEditBean firstNodeInstBean = new NodeInstEditBean();
	        firstNodeInstBean.setFlowInstId(flowInst.getId());
	        firstNodeInstBean.setParentNodeInstId(parentNodeInstId);
	        firstNodeInstBean.setArrivalTime(now);
	        firstNodeInstBean.setCurNodeId(firstNode.getId());
	        firstNodeInstBean.setPreDealerId(bean.getDealer());
	        firstNodeInstBean.setFlowDir(WfFlowDir.普通);
	        firstNodeInstBean.setInstStatus(WfInstStatus.在办);
	        firstNodeInstBean.setDealType(WfDealType.阅办);
	        firstNodeInstBean.setReadFlag(WfReadFlag.未读);
	        firstNodeInstBean.setDealerId(Long.valueOf(dealerId));
            firstNodeInstBean.setEndCond(WfEndCond.单人办结);
            firstNodeInstBean.setNodeExpiryMinute(bean.getNodeExpiryMinute());
            if(bean.getEarlyWarnMinute() != null){
            	firstNodeInstBean.setNodeWarnMinute(bean.getEarlyWarnMinute());
            }
            nodeInstService.saveNodeInst(firstNodeInstBean);
            //判断内部请示文的消息提示
            if(bean.getBusinessType().equals("5")){
            	appMessageService.insertOaAppMsg(Long.valueOf(dealerId), bean.getDealer(), "公文《" + bean.getTitle()
            	+ "》等待您的办理", MesType.内部请示);
        	}else{
        		appMessageService.insertOaAppMsg(Long.valueOf(dealerId), bean.getDealer(), "公文《" + bean.getTitle()
                	+ "》等待您的办理", MesType.待办);
        	}
        }
    }


    @Override
    public WfNodeInst getNodeInstByFlowInstIdAndNodeId(String instId, String nodeId) {
        return nodeInstService.getByFlowInstIdAndCurNodeId(instId, nodeId);
    }


    //判断是否是办理队列中最后的办理人
    private boolean isLastDealer(String candidates, String dealer) {
    	String[] cans = candidates.split(",");
    	return dealer.equals(cans[cans.length-1]);
    }

    /**判断流程实例是否完结*/
    private boolean isFlowInstCompleted(String flowInstId) {
    	List<WfNodeInst> list = this.nodeInstService.findByFlowInstId(flowInstId);
    	List<WfNodeInst> newlist = new ArrayList();
    	for (WfNodeInst node: list) {
    		if (WfDealType.阅办.equals(node.getDealType()) || WfDealType.子流程办理.equals(node.getDealType())) {
    			newlist.add(node);
    		}
    	}
    	int finish = 0;
    	for (WfNodeInst node: newlist) {
    		if (WfInstStatus.办结.equals(node.getInstStatus())) finish ++;
    	}
    	return finish == newlist.size();
    }


    /**判断流程包含的所有子流程实例是否完结*/
    private boolean isAllSubflowInstCompleted(String nodeInstId) {
    	List<WfNodeInst> list = this.nodeInstService.findByParentNodeInstId(nodeInstId);
    	List<WfNodeInst> newlist = new ArrayList();
    	for (WfNodeInst node: list) {
    		if (WfDealType.阅办.equals(node.getDealType()) || WfDealType.子流程办理.equals(node.getDealType())) {
    			newlist.add(node);
    		}
    	}
    	int finish = 0;
    	for (WfNodeInst node: newlist) {
    		if (WfInstStatus.办结.equals(node.getInstStatus())) finish ++;
    	}
    	return finish == newlist.size();
    }

    //通过子流程编号取得主流程下个环节，子流程办结后，自动跳转到主流程下一个环节
//    private WfFlowNode getNextMainFlowNode(String subflowInstId) {
//    	return null;
//    }


    @Override
	public NodeCommonBean getNextNodeDataBeforeDealing(String flowId, String curNodeId, String curNodeInstId) {
    	NodeCommonBean bean = new NodeCommonBean();
    	bean.setNextOpenSel(true);

    	//流程刚启动提交完成前，首环节实例ID是空的
    	WfNodeInst curNodeInst = null;
    	if (StringUtils.isNotBlank(curNodeInstId)) {
    		curNodeInst  = nodeInstService.findOne(curNodeInstId);
    	}
    	if (curNodeInst == null) {
    		return bean;
    	}


    	if (WfEndCond.会签.equals(curNodeInst.getEndCond())) {
    		int i = getUndealNodeInsts(curNodeInst.getFlowInstId(), curNodeInst.getCurNodeId());
    		//如果还有多个实例未办，就不用打开选人选环节了，相当于会签未结束
    		if (i>1) bean.setNextOpenSel(false);
    	}

    	// 查找当前环节的定义
		WfNode node = this.nodeService.findOne(curNodeInst.getCurNodeId());

		String autoSubmitNodeId = this.nodeLineService.getAutoSubmitNodeIdByFromNodeId(flowId, curNodeInst.getCurNodeId());

		if (WfEndCond.排队办结.equals(node.getEndCond())) {
        	//
			if(StringUtils.isNotBlank(curNodeInst.getCandidates())){
				String[] cans = curNodeInst.getCandidates().split(",");
	        	if (!curNodeInst.getDealerId().toString().equals(cans[cans.length-1])) {
	        		//不是最后一个
	        		bean.setNodeId(curNodeInst.getCurNodeId());
	        		//取下一个
	        		int p = 0;
	        		for(int i=0; i<cans.length-1; i++) {
	        			if (curNodeInst.getDealerId().toString().equals(cans[i])) {
	        				p = i;
	        				break;
	        			}
	        		}
	        		if (p >= 0 && p<cans.length - 1) {
	        			bean.setDealerId(new Long(cans[p+1]));
	        			bean.setNextOpenSel(false);
	        		} else {
	        			//出错了
	        			//bean.setDealer(curNodeInst.getDealerId());
	        		}
	        	} else {
	        		//最后一个排队办结
	        		if (StringUtils.isNotBlank(autoSubmitNodeId)) {
	                	// 有配置默认提交环节
	                	bean.setNodeId(autoSubmitNodeId);
	                	bean.setNextOpenSel(false);
	                }
	        	}
			}
		} else {
			if (StringUtils.isNotBlank(autoSubmitNodeId)) {
            	// 有配置默认提交环节
            	bean.setNodeId(autoSubmitNodeId);
            	bean.setNextOpenSel(false);
            }
		}


		//如果下一环节已经明确，不需要弹出选择了，则要锁定办理人
        if (!bean.getNextOpenSel() && StringUtils.isNotBlank(bean.getNodeId()) && bean.getDealerId() == null) {
        	// 下一环节处理对象没有选择：先在历史环节中找到配置nextNodeId的历史办理人，如果没有历史办理人，则发回上一环节的处理人
            // TODO 还应该优化：可以去找nextNodeId的配置接收人/部门 by yuandl 20161215
            NodeInstQueryBean hisNodeInstQuery = new NodeInstQueryBean();
            hisNodeInstQuery.setFlowInstId(curNodeInst.getFlowInstId());
            hisNodeInstQuery.setNodeInstStatus(WfInstStatus.办结);
            hisNodeInstQuery.setCurNodeId(bean.getNodeId());

            List<WfNodeInst> hisNodeInsts = nodeInstService.selectNodeInsts(hisNodeInstQuery);
            if (hisNodeInsts != null && hisNodeInsts.size() > 0) {
                WfNodeInst hisNodeInst = hisNodeInsts.get(hisNodeInsts.size() - 1);// 倒序取最新一个
                bean.setDealerId(hisNodeInst.getDealerId());// 下一环处理人为上一环处理人
            } else {

            	//找环节定义中配置的人选
            	WfNode selNode = this.nodeService.findOne(bean.getNodeId());
            	String rs = selNode.getReceiverIds();
            	if (StringUtils.isNotBlank(rs)) {
            		String[] uids = rs.split(",");
            		bean.setDealerId(new Long(uids[0]));
            	} else {
                    // 没有配置和历史办理人，需要找到上一环节办理人，但是可能会出现上一环节办理人
                    // 并不是nextNodeId应存在的办理人的错误，需要配置流程时注意避免这样的配置 by yuandl 20161215
                    bean.setDealerId(curNodeInst.getPreDealerId());// 下一环处理人为上一环处理人
                }
            }

        }

        if (!bean.getNextOpenSel()) {
        	bean.setNodeName(nodeService.findOne(bean.getNodeId()).getNodeName());
        	bean.setDealerName(userService.findOne(bean.getDealerId()).getUserName());
        }

		return bean;
	}


    @Override
    public void saveNodeOpinion(NodeInstDealBean bean) {
    	WfFlowInst flowInst = bean.getFlowInst();

    	 if (bean.getAuditOpinion() != null) {
             AuditOpinionEditBean opinionEditBean = new AuditOpinionEditBean();
             if (bean.getDealer() != null) {
                 // 从历史审批意见读取，若有暂存的
                 WfAuditOpinion auditOpinion = auditOpinionService.getByNodeInstId(bean.getNodeInst().getId());
                 if (auditOpinion != null) {
                     BeanUtils.copyProperties(auditOpinion, opinionEditBean);
                     opinionEditBean.setId(auditOpinion.getId());
                 } else {
                     // BeanUtils.copyProperties(bean.getAuditOpinion(), opinionEditBean);
                     opinionEditBean.setFlowInstId(flowInst.getId());
                     opinionEditBean.setNodeId(bean.getNodeId());
                     opinionEditBean.setApproverId(bean.getDealer());
                 }
             }
             opinionEditBean.setApprResult(WfApprResult.通过);
             opinionEditBean.setApprOpinion(bean.getAuditOpinion().getApprOpinion());
             opinionEditBean.setNodeInstId(bean.getNodeInst().getId());
             if (bean.getAuditOpinion().getApprTime() != null) {
                 opinionEditBean.setApprTime(bean.getAuditOpinion().getApprTime());
             } else if (opinionEditBean.getApprTime() == null) {
                 opinionEditBean.setApprTime(new Date());
             }

             if (opinionEditBean.getApprOpinion() != null) {
                 auditOpinionService.saveAuditOpinion(opinionEditBean);
             }
         }
    }


	@Override
    public WfDoneResult dealNodeInst(NodeInstDealBean bean) {
        WfFlowInst flowInst = bean.getFlowInst() == null ? flowInstService.getMainFlowInstByEntityId(bean.getEntityId()) : bean.getFlowInst();
        if (flowInst == null) {
            throw new RuntimeException("操作失败！无法找到流程实例。");
        } else if (WfInstStatus.办结.equals(flowInst.getInstStatus())) {
        	throw new RuntimeException("操作失败！该流程已办结。");
        }
        String nodeId = bean.getNodeId();
        // 查找当前环节的定义
        WfNode curNode = bean.getNode() == null ? nodeService.findOne(nodeId) : bean.getNode();

   		String autoSubmitNodeId = this.nodeLineService.getAutoSubmitNodeIdByFromNodeId(curNode.getFlowId(), nodeId);

        WfNodeInst curNodeInst = bean.getNodeInst();
        Date now = new Date();

        // 查找当前环节实例
        if (curNodeInst == null) {
            NodeInstQueryBean nodeInstQueryBean = new NodeInstQueryBean();
            nodeInstQueryBean.setFlowInstId(flowInst.getId());
            nodeInstQueryBean.setCurNodeId(bean.getNodeId());
            nodeInstQueryBean.setDealerId(bean.getDealer());
            nodeInstQueryBean.setNodeInstStatus(WfInstStatus.在办);
            List<WfNodeInst> nodeInsts = nodeInstService.selectNodeInsts(nodeInstQueryBean);
            if (nodeInsts != null && nodeInsts.size() > 0) {
            	curNodeInst = nodeInsts.get(0);
            }
            bean.setNodeInst(curNodeInst);
        }
        if (curNodeInst == null) {
            throw new RuntimeException("操作失败！无法找到流程当前环节实例。");
        } else if (WfInstStatus.办结.equals(curNodeInst.getInstStatus())) {
        	throw new RuntimeException("操作失败！该环节已办结。");
        }
        curNodeInst.setDealTime(now);// 设置处理时间

        this.saveNodeOpinion(bean);

        //如果会签环节勾选本处室共享，则记录当前用户处室关联环节信息，以供本部门公文查询使用
        if("1".equals(bean.getDeptShare())){
        	SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        	OaDocDeptFile deptFile = new OaDocDeptFile();
        	deptFile.setDeptId(user.getDepartment().getId());
        	deptFile.setNodeInstId(curNodeInst.getId());
        	oaDocDeptFileService.getRepository().save(deptFile);
        }

        // 检查当前环节是否已完成
        NodeInstCheckBean checkBean = new NodeInstCheckBean();
        checkBean.setFlowInst(flowInst);
        checkBean.setNodeInst(curNodeInst);
        checkBean.setDealer(bean.getDealer());
    	curNodeInst.setInstStatus(WfInstStatus.办结);
        nodeInstService.getRepository().save(curNodeInst);
        if (!this.isTheNodeInstFinished(checkBean)) {
            // 当前环节未完成，只需完成当前处理人
            return WfDoneResult.环节未办结;
        }

        // 更新当前环节状态
        this.updateNodeInstStatus(bean);

        //-----------------------以下代码处理"上一环节已全部完成"的情况下-------------------------------
        // 读取下一环节定义
        // 获取下一环节NodeId
        if (StringUtils.isNotBlank(bean.getNextNodeId())) {
            //
        } else if (StringUtils.isNotBlank(autoSubmitNodeId)) {// 默认提交环节
            bean.setNextNodeId(autoSubmitNodeId);
        } else if (StringUtils.isNotBlank(this.nodeLineService.getFirstNextNodeId(curNode.getFlowId(), nodeId))) {
            bean.setNextNodeId(this.nodeLineService.getFirstNextNodeId(curNode.getFlowId(), nodeId));
        }

        WfNode nextNode;
        if (WorkflowUtils.NODE_END.equals(bean.getNextNodeId())) {
        	//下个环节为结束点
        	//可能：子流程办结，进入下个主环节
        	if (curNodeInst.isSubInst()) {
        		this.completedSubflowInst(curNodeInst.getFlowInst());
        		if (isAllSubflowInstCompleted(curNodeInst.getParentNodeInstId())) {
        			//所有子流程实例都办结，自动跳转到主流程下一个环节
        			nextNode = updateParentNodeInstStatus(curNodeInst.getParentNodeInstId());
        	        bean.setNextNodeId(nextNode.getId());
        		} else {
        			return WfDoneResult.环节办结;
        		}
        	} else {
        		//可能：主流程办结
        		this.completedFlowInst(flowInst, bean.getDealer());
        		return WfDoneResult.流程办结;
        	}
        } else {
        	nextNode = nodeService.findOne(bean.getNextNodeId());
        	if (nextNode == null) {
        		throw new RuntimeException("操作失败！无法找到下一环节定义，请检查流程配置。");
        	}
        }

      	if (WfNodeType.子流程.equals(nextNode.getNodeType())) {
    		//如果下个环节是子流程，先创建主流程的子流程办理环节实例
    		WfNodeInst fni = createSubflowNodeInsts(bean, curNodeInst, nextNode);
    		//再启动子流程实例
    		this.startSubflow(nextNode.getSubflowId(), bean, fni.getId());
    	} else {
    		// 创建下一环节实例s
            this.createNextNodeInsts(bean, curNodeInst, nextNode);
    	}


        flowInst.setCurNodeId(nextNode.getId());
        // 检查流程是否结束
        if (this.isFlowInstCompleted(flowInst.getId())) {
        	this.completedFlowInst(flowInst, bean.getDealer());
        	return WfDoneResult.流程办结;
        } else {
        	 // 修改流程实例状态
            flowInstService.getRepository().save(flowInst);
        }

        return WfDoneResult.环节办结;

    }

	private void completedFlowInst(WfFlowInst flowInst, Long dealer) {
        flowInst.setInstStatus(WfInstStatus.办结);
        flowInst.setEndTime(new Date());
        flowInst.setEnderId(dealer);
        flowInst.setCurNodeId(WorkflowUtils.NODE_END);
        flowInst.setCurNode(null);

        flowInstService.getRepository().save(flowInst);
	}


	private void createNextNodeInsts(NodeInstDealBean bean, WfNodeInst curNodeInst, WfNode nextNode) {
		NodeInstEditBean nextNodeInstBean = new NodeInstEditBean();

        if (curNodeInst.isSubInst()) {
        	//当前环节是子流程环节
        	if (WfNodeType.子流程环节.equals(nextNode.getNodeType())) {
        		nextNodeInstBean.setFlowInstId(curNodeInst.getFlowInstId());
        		nextNodeInstBean.setParentNodeInstId(curNodeInst.getParentNodeInstId());
        	} else {
        		nextNodeInstBean.setFlowInstId(curNodeInst.getFlowInst().getParentFlowInstId());
        		nextNodeInstBean.setParentNodeInstId(null);
        	}
        } else {
        	nextNodeInstBean.setFlowInstId(curNodeInst.getFlowInstId());
    		nextNodeInstBean.setParentNodeInstId(null);
        }

        nextNodeInstBean.setArrivalTime(new Date());
        nextNodeInstBean.setCurNodeId(nextNode.getId());
        nextNodeInstBean.setPreDealerId(bean.getDealer());
        nextNodeInstBean.setFlowDir(WfFlowDir.普通);
        nextNodeInstBean.setInstStatus(WfInstStatus.在办);
        if (StringUtils.isNotBlank(nextNode.getSubflowId())) {
			//子流程
			nextNodeInstBean.setDealType(WfDealType.子流程办理);
		} else {
			nextNodeInstBean.setDealType(WfDealType.阅办);
		}

        nextNodeInstBean.setReadFlag(WfReadFlag.未读);
        String[] nextDealers;

        if (bean.getNextDealer() != null && bean.getNextDealer().length > 0) {
            if (WfEndCond.排队办结.equals(nextNode.getEndCond()) && nextNode.getId().equals(curNodeInst.getCurNodeId())) {
            	//继承上一个排队的待办人员列表，否则下个实例无法找到下一排队人员
            	nextNodeInstBean.setCandidates(curNodeInst.getCandidates());
            } else {
            	nextNodeInstBean.setCandidates(StringUtils.join(bean.getNextDealer(), ","));
            }

            // 下一环节处理对象为人员
        	if (WfEndCond.单人办结.equals(nextNode.getEndCond()) && bean.getNextDealer().length > 1) {
        		//写抢占key，目的是为了办理时，删除其它待办人相同key的记录
        		nextNodeInstBean.setExclusiveKey(UUID.randomUUID().toString());
        	}

        	//设置环节限办时间
        	nextNodeInstBean.setNodeExpiryMinute(bean.getNodeExpiryMinute());
        	if(bean.getEarlyWarnMinute() != null){
        		nextNodeInstBean.setNodeWarnMinute(bean.getEarlyWarnMinute());
        	}

            for (String dealerId : bean.getNextDealer()) {
                if (StringUtils.isBlank(dealerId)) {
                    continue;
                }
                nextNodeInstBean.setDealerId(Long.valueOf(dealerId));
                nextNodeInstBean.setDealDeptId(null);
                nextNodeInstBean.setEndCond(nextNode.getEndCond());
                nodeInstService.saveNodeInst(nextNodeInstBean);
                if (WfEndCond.排队办结.equals(nextNode.getEndCond())) {
                	break;
                }
            }
            nextDealers = bean.getNextDealer();
            //fd.setRecepters(bean.getNextDealer());
        } else if (bean.getNextDealDept() != null) {
            // 下一环节处理对象为部门
            // 先从接收定义获取处理部门
            List<WfNodeDept> nodeReceives = nodeDeptService.findByFlowIdAndNodeId(curNodeInst.getFlowInst().getFlowId(),
            				nextNode.getId());
            WfNodeDept depReceive = (nodeReceives != null && nodeReceives.size() > 0) ? nodeReceives.get(0)
                            : null;
            Long dealDepId = (depReceive != null) ? depReceive.getReceiveDepId() : null;

            // 若当前环节没有设置自动提交环节编号，则从办理环节轨迹中获取首节点的办理人ID
            if (dealDepId == null) {
                List<WfNodeInst> hisNodeInsts = nodeInstService.findByFlowInstId(curNodeInst.getFlowInstId());
                dealDepId = (hisNodeInsts != null && hisNodeInsts.size() > 0) ? hisNodeInsts.get(0).getDealer()
                                .getDepartment().getId() : null;
            }

            if (dealDepId == null) {
                throw new RuntimeException("处理失败，请检查流程接收配置是否正确。");
            }

            nextNodeInstBean.setDealDeptId(dealDepId);
            nextNodeInstBean.setEndCond(WfEndCond.单人办结);
            nextNodeInstBean.setDealerId(null);
            nodeInstService.saveNodeInst(nextNodeInstBean);

            List<SysUser> recepters = userService.findBydetpId(dealDepId);
            //fd.setParallel(false);
            nextNode.setEndCond(WfEndCond.单人办结);// 临时设置，为后续待办的抢占不改变
            //fd.setRecepters(Tools.changeArrayToString(recepters, "id", ",", false).split(","));
            nextDealers = Tools.changeArrayToString(recepters, "id", ",", false).split(",");
        } else {
            // 下一环节处理对象没有选择：先在历史环节中找到配置nextNodeId的历史办理人，如果没有历史办理人，则发回上一环节的处理人
            nextNodeInstBean.setDealDeptId(null);
            nextNodeInstBean.setEndCond(nextNode.getEndCond());

            NodeInstQueryBean hisNodeInstQuery = new NodeInstQueryBean();
            hisNodeInstQuery.setFlowInstId(curNodeInst.getFlowInstId());
            hisNodeInstQuery.setNodeInstStatus(WfInstStatus.办结);
            hisNodeInstQuery.setCurNodeId(bean.getNextNodeId());

            List<WfNodeInst> hisNodeInsts = nodeInstService.selectNodeInsts(hisNodeInstQuery);
            if (hisNodeInsts == null || hisNodeInsts.isEmpty()) {
            	if (StringUtils.isNotBlank(curNodeInst.getFlowInst().getParentFlowInstId())) {
            		//可能有父子流程，需要再往上查询
            		hisNodeInstQuery.setFlowInstId(curNodeInst.getFlowInst().getParentFlowInstId());
            		hisNodeInsts = nodeInstService.selectNodeInsts(hisNodeInstQuery);
            	}
            }

            if (hisNodeInsts != null && hisNodeInsts.size() > 0) {
                WfNodeInst hisNodeInst = hisNodeInsts.get(hisNodeInsts.size() - 1);// 倒序取最新一个

                nextNodeInstBean.setDealerId(hisNodeInst.getDealerId());// 下一环处理人为上一环处理人
                nodeInstService.saveNodeInst(nextNodeInstBean);

                //fd.setRecepters(new String[] { hisNodeInst.getDealerId().toString() });
                nextDealers = new String[] { hisNodeInst.getDealerId().toString() };
            } else {
                // 没有历史办理人，需要找到上一环节办理人，但是可能会出现上一环节办理人
                // 并不是nextNodeId应存在的办理人的错误，需要配置流程时注意避免这样的配置 by yuandl 20161215
                nextNodeInstBean.setDealerId(curNodeInst.getPreDealerId());// 下一环处理人为上一环处理人
                nodeInstService.saveNodeInst(nextNodeInstBean);

                //fd.setRecepters(new String[] { oldNodeInst.getPreDealerId().toString() });
                nextDealers = new String[] { curNodeInst.getPreDealerId().toString() };
            }
        }

        // 保存消息提醒
        if (nextDealers != null) {
            for (String userId : nextDealers) {
            	//判断内部请示文的消息提示
            	if(WfBusinessType.内部请示.getKey().equals(bean.getBusinessType())){
            		appMessageService.insertOaAppMsg(Long.valueOf(userId), bean.getDealer(), "公文《" + bean.getTitle()
                    	+ "》等待您的办理", MesType.内部请示);
            	}else if(WfBusinessType.发文.getKey().equals(bean.getBusinessType()) ||
            			WfBusinessType.收文.getKey().equals(bean.getBusinessType()) ||
            			WfBusinessType.便函.getKey().equals(bean.getBusinessType())){
            		appMessageService.insertOaAppMsg(Long.valueOf(userId), bean.getDealer(), "公文《" + bean.getTitle()
                    	+ "》等待您的办理", MesType.待办);
            	}else if(WfBusinessType.车辆申请.getKey().equals(bean.getBusinessType())){
            		appMessageService.insertOaAppMsg(Long.valueOf(userId), bean.getDealer(), "车辆申请等待您的办理", MesType.车辆申请);
            	}else if(WfBusinessType.故障报告申请.getKey().equals(bean.getBusinessType())){
            		appMessageService.insertOaAppMsg(Long.valueOf(userId), bean.getDealer(), "故障报告申请等待您的办理", MesType.故障报告);
            	}else if(WfBusinessType.休假申请.getKey().equals(bean.getBusinessType())){
            		appMessageService.insertOaAppMsg(Long.valueOf(userId), bean.getDealer(), "休假申请等待您的办理", MesType.休假);
            	}
            }
        }

	}


	/** 创建一个子流程办理环节（该环节可能会包含几个子流程）*/
	private WfNodeInst createSubflowNodeInsts(NodeInstDealBean bean, WfNodeInst curNodeInst, WfNode nextNode) {
		NodeInstEditBean nextNodeInstBean = new NodeInstEditBean();

    	nextNodeInstBean.setFlowInstId(curNodeInst.getFlowInstId());
		nextNodeInstBean.setParentNodeInstId(null);
	    nextNodeInstBean.setArrivalTime(new Date());
	    nextNodeInstBean.setCurNodeId(nextNode.getId());
	    nextNodeInstBean.setPreDealerId(bean.getDealer());
	    nextNodeInstBean.setFlowDir(WfFlowDir.普通);
	    nextNodeInstBean.setInstStatus(WfInstStatus.在办);
		nextNodeInstBean.setDealType(WfDealType.子流程办理);
		nextNodeInstBean.setReadFlag(WfReadFlag.未读);
        nextNodeInstBean.setDealDeptId(null);
        nextNodeInstBean.setEndCond(nextNode.getEndCond());
        return nodeInstService.saveNodeInst(nextNodeInstBean);

	}

	/**更新主流程中的子流程环节状态*/
	private WfNode updateParentNodeInstStatus(String nodeInstId) {
		WfNodeInst ni = nodeInstService.findOne(nodeInstId);
		ni.setDealTime(new Date());
        ni.setInstStatus(WfInstStatus.办结);
        nodeInstService.getRepository().save(ni);
        String firstNextNodeId = this.nodeLineService.getFirstNextNodeId(ni.getFlowInst().getFlowId(), ni.getCurNodeId());
        return nodeService.findOne(firstNextNodeId);
	}

	private void updateNodeInstStatus(NodeInstDealBean bean) {
		WfNodeInst ni = bean.getNodeInst();
		if (ni != null) {
            // 若当前环节的处理人为空则设置处理人为当前处理人
            if (ni.getDealerId() == null) {
                ni.setDealerId(bean.getDealer());
            }
            ni.setDealTime(new Date());
            ni.setNextNodeId(bean.getNextNodeId());
            ni.setInstStatus(WfInstStatus.办结);

            nodeInstService.getRepository().save(ni);

            //如果是抢占办理，则删除相同key的其它人的待办实例
            if(StringUtils.isNotBlank(ni.getExclusiveKey())) {
            	List<WfNodeInst> nlist = nodeInstService.findByExclusiveKey(ni.getExclusiveKey());
            	for(WfNodeInst node: nlist) {
            		if (!node.getDealerId().equals(ni.getDealerId())) {
            			nodeInstService.delete(node);
            		}
            	}
            }
        }

	}

    @Override
    public WfDoneResult readNodeInstAutoFinished(NodeInstDealBean bean) {
        WfFlowInst flowInst = bean.getFlowInst() == null ? flowInstService.getMainFlowInstByEntityId(bean.getEntityId()) : bean.getFlowInst();
        if (flowInst == null) {
            throw new RuntimeException("操作失败！无法找到流程实例。");
        }
        String nodeId = bean.getNodeId();
        WfNodeInst oldNodeInst = bean.getNodeInst();
        Date now = new Date();

        // 查找当前环节实例
        if (oldNodeInst == null) {
            NodeInstQueryBean nodeInstQueryBean = new NodeInstQueryBean();
            nodeInstQueryBean.setFlowInstId(flowInst.getId());
            nodeInstQueryBean.setCurNodeId(bean.getNodeId());
            nodeInstQueryBean.setDealerId(bean.getDealer());
            nodeInstQueryBean.setNodeInstStatus(WfInstStatus.在办);
            List<WfNodeInst> nodeInsts = nodeInstService.selectNodeInsts(nodeInstQueryBean);
            if (nodeInsts != null && nodeInsts.size() > 0) {
                oldNodeInst = nodeInsts.get(0);
            }
        }
        if (oldNodeInst == null) {
            throw new RuntimeException("操作失败！无法找到流程当前环节实例。");
        }

        // 保存审批意见
        if (bean.getAuditOpinion() != null) {
            AuditOpinionEditBean opinionEditBean = new AuditOpinionEditBean();
            opinionEditBean.setFlowInstId(flowInst.getId());
            opinionEditBean.setNodeId(nodeId);
            opinionEditBean.setApproverId(bean.getDealer());
            opinionEditBean.setApprResult(WfApprResult.通过);
            opinionEditBean.setApprOpinion(bean.getAuditOpinion().getApprOpinion());
            opinionEditBean.setNodeInstId(bean.getNodeInst().getId());
            if (bean.getAuditOpinion().getApprTime() != null) {
                opinionEditBean.setApprTime(bean.getAuditOpinion().getApprTime());
            } else if (opinionEditBean.getApprTime() == null) {
                opinionEditBean.setApprTime(now);
            }

            if (opinionEditBean.getApprOpinion() != null) {
                auditOpinionService.saveAuditOpinion(opinionEditBean);
            }
        }

        // 结束当前环节
        oldNodeInst.setDealTime(now);
        oldNodeInst.setNextNodeId(WorkflowUtils.NODE_END);
        oldNodeInst.setInstStatus(WfInstStatus.办结);
        nodeInstService.persist(oldNodeInst);

        // 检查是否有同一个人同一个环节，如果有，办结它
        NodeInstQueryBean queryBean = new NodeInstQueryBean();
        queryBean.setFlowInstId(flowInst.getId());
        queryBean.setCurNodeId(nodeId);
        queryBean.setDealerId(bean.getDealer());
        List<WfNodeInst> sameNodeInsts = nodeInstService.selectNodeInsts(queryBean);
        for (WfNodeInst sameInst : sameNodeInsts) {
            if (!sameInst.getId().equals(oldNodeInst.getId())) {
                sameInst.setDealTime(now);
                sameInst.setNextNodeId(WorkflowUtils.NODE_END);
                sameInst.setInstStatus(WfInstStatus.办结);
                nodeInstService.persist(sameInst);
            }
        }

        // 检查是否还有其他未完成的环节
        queryBean = new NodeInstQueryBean();
        queryBean.setFlowInstId(flowInst.getId());
        queryBean.setNodeInstStatus(WfInstStatus.在办);
        List<WfNodeInst> unNodeInsts = nodeInstService.selectNodeInsts(queryBean);
        if (unNodeInsts == null || (unNodeInsts != null && unNodeInsts.size() == 0)) {
            // 没有未办环节了，结束当前流程
            flowInst.setInstStatus(WfInstStatus.办结);
            flowInst.setEndTime(now);
            flowInstService.persist(flowInst);
            return WfDoneResult.流程办结;
        }

        return WfDoneResult.环节办结;
    }

    @Override
    public boolean isTheNodeInstFinished(NodeInstCheckBean bean) {
        WfFlowInst flowInst = bean.getFlowInst();
        WfNodeInst nodeInst = bean.getNodeInst();
        // 查找流程环节定义
        WfNode node = nodeService.findOne(nodeInst.getCurNodeId());

        NodeInstQueryBean queryBean;
        // 判断环节办结条件
        if (StringUtils.isNotBlank(node.getSubflowId())) {
        	//该环节是子流程
        	List<WfFlowInst> list = flowInstService.findByParentFlowInstId(flowInst.getId());
        	int finish = 0;
        	for (WfFlowInst fi: list) {
        		if (WfInstStatus.办结.equals(fi.getInstStatus()))  finish ++;
        	}
        	return !list.isEmpty() && finish == list.size();
        } else {
        	if (WfEndCond.会签.equals(node.getEndCond())) {
        		// 上个环节所有人都完成
        		return getUndealNodeInsts(flowInst.getId(), nodeInst.getCurNodeId()) == 0;
            } else {
            	//其它方式如多人排队办结，直接
                return true;
            }
        }

    }


    /**取得某个环节上未办的实例数量（多实例）*/
    private int getUndealNodeInsts(String flowInstId, String nodeId) {
    	// 查找环节实例完成情况
    	NodeInstQueryBean queryBean = new NodeInstQueryBean();
        queryBean.setFlowInstId(flowInstId);
        queryBean.setCurNodeId(nodeId);
        queryBean.setDealType(WfDealType.阅办);
        List<WfNodeInst> list = nodeInstService.selectNodeInsts(queryBean);

        int finish = 0;
        for (WfNodeInst inst : list) {
            if (WfInstStatus.办结.equals(inst.getInstStatus())) finish ++;
        }

        return list.size() - finish;
    }

    /**办结一个子流程实例*/
    private void completedSubflowInst(WfFlowInst subflowInst) {
    	subflowInst.setEndTime(new Date());
    	subflowInst.setInstStatus(WfInstStatus.办结);
    	this.flowInstService.persist(subflowInst);
    }

    @Override
    public boolean completeWorkflow(FlowInstCompleteBean bean) {
        WfFlowInst flowInst = bean.getFlowInst();
        if (flowInst == null) {
            throw new RuntimeException("操作失败！无法找到流程实例。");
        }
        Date now = new Date();

        // 查找当前环节实例
        NodeInstQueryBean queryBean = new NodeInstQueryBean();
        queryBean.setFlowInstId(flowInst.getId());
        queryBean.setCurNodeId(bean.getCurNodeId());
        queryBean.setDealerId(bean.getDealerId());
        queryBean.setNodeInstStatus(WfInstStatus.在办);
        queryBean.setDealType(WfDealType.阅办);
        List<WfNodeInst> oldNodeInsts = nodeInstService.selectNodeInsts(queryBean);
        WfNodeInst oldNodeInst = (oldNodeInsts != null && oldNodeInsts.size() > 0) ? oldNodeInsts.get(0) : null;
        if (oldNodeInst == null) {
            throw new RuntimeException("操作失败！无法找到流程当前环节实例。");
        }

        // 清理退回意见，不在下一环节继续显示
        this.clearBackNodeOpinion(flowInst);

        // 完成当前环节
        oldNodeInst.setNextNodeId(WorkflowUtils.NODE_END);
        if (oldNodeInst.getDealDeptId() != null && oldNodeInst.getDealerId() == null) {
            oldNodeInst.setDealerId(bean.getDealerId());
        }
        oldNodeInst.setDealTime(now);
        oldNodeInst.setInstStatus(WfInstStatus.办结);
        nodeInstService.persist(oldNodeInst);

        // 完成流程实例
        this.completedFlowInst(flowInst, bean.getDealerId());

        // 保存意见
        AuditOpinionEditBean opinionEditBean = new AuditOpinionEditBean();
        opinionEditBean.setFlowInstId(flowInst.getId());
        opinionEditBean.setNodeId(oldNodeInst.getCurNodeId());
        opinionEditBean.setApproverId(bean.getDealerId());
        opinionEditBean.setApprResult(WfApprResult.通过);
        opinionEditBean.setApprOpinion(bean.getOpinion());
        opinionEditBean.setNodeInstId(oldNodeInst.getId());
        if (bean.getOpinionDate() != null) {
            opinionEditBean.setApprTime(bean.getOpinionDate());
        } else if (opinionEditBean.getApprTime() == null) {
            opinionEditBean.setApprTime(now);
        }

        auditOpinionService.saveAuditOpinion(opinionEditBean);

        return true;
    }

    @Override
    public void delWorkflow(String entityId) {
        // 删除所有环节实例
        nodeInstService.delNodeInstsByEntityId(entityId);

        // 删除所有审批意见
        auditOpinionService.delAuditOpinionsByEntityId(entityId);

        // 删除所有流程实例
        flowInstService.delFlowInstByEntityId(entityId);
    }

    @Override
    public String saveNodeOpinion(AuditOpinionEditBean bean) {
        AuditOpinionEditBean opinionEditBean = bean;
        // 从历史审批意见读取，若存在同一环节同一人历史意见，则覆盖
        WfAuditOpinion auditOpinion = auditOpinionService.getByNodeInstId(bean.getNodeInstId());
        if (auditOpinion != null) {
            auditOpinion.setApprOpinion(bean.getApprOpinion());
            auditOpinionService.persist(auditOpinion);
            return auditOpinion.getId();
        } else {
            return auditOpinionService.saveAuditOpinion(opinionEditBean);
        }
    }

    @Override
    public boolean addReadNodeInst(NodeInstReadBean bean) {
        WfFlowInst flowInst = bean.getFlowInst() == null ? flowInstService.getMainFlowInstByEntityId(bean.getEntityId()) : bean
                        .getFlowInst();

        if (flowInst != null && StringUtils.isNotBlank(bean.getCoDealers())) {
            String[] coDealerArr = bean.getCoDealers().split(",");
            NodeInstEditBean nodeInstBean = new NodeInstEditBean();
            nodeInstBean.setFlowInstId(flowInst.getId());
            nodeInstBean.setArrivalTime(new Date());
            nodeInstBean.setPreDealerId(bean.getDealerId());// 设置发送人
            nodeInstBean.setCurNodeId(WorkflowUtils.NODE_READ);
            nodeInstBean.setNextNodeId(WorkflowUtils.NODE_END);
            nodeInstBean.setInstStatus(WfInstStatus.在办);
            nodeInstBean.setDealType(WfDealType.阅知);
            nodeInstBean.setFlowDir(WfFlowDir.普通);
            nodeInstBean.setEndCond(WfEndCond.单人办结);
            nodeInstBean.setReadFlag(WfReadFlag.未读);

            // 保存阅知环节
            for (String userId : coDealerArr) {
                nodeInstBean.setDealerId(Long.valueOf(userId));
                nodeInstService.saveNodeInst(nodeInstBean);
            }


            // 保存消息提醒
            if (coDealerArr != null) {
                for (String userId : coDealerArr) {
                    appMessageService.insertOaAppMsg(Long.valueOf(userId), bean.getDealerId(),
                                    "公文《" + flowInst.getTitle() + "》等待您的查阅", MesType.待阅);
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean dealNodeInstRead(NodeInstReadDealBean bean) {
        WfFlowInst flowInst = bean.getFlowInst() == null ? flowInstService.getMainFlowInstByEntityId(bean.getEntityId()) : bean
                        .getFlowInst();
        Date now = new Date();

        if (flowInst == null) {
            // TODO by yuandl 完善提示
            return false;
        }
        if (bean.getDealerId() != null) {
            // TODO by yuandl 完善提示
            return false;
        }

        WfNodeInst oldNodeInst = null;
        // 查找阅知环节
        NodeInstQueryBean nodeInstQueryBean = new NodeInstQueryBean();
        nodeInstQueryBean.setFlowInstId(flowInst.getId());
        nodeInstQueryBean.setCurNodeId(WorkflowUtils.NODE_READ);
        nodeInstQueryBean.setDealerId(bean.getDealerId());
        nodeInstQueryBean.setNodeInstStatus(WfInstStatus.在办);
        List<WfNodeInst> nodeInsts = nodeInstService.selectNodeInsts(nodeInstQueryBean);
        if (nodeInsts != null && nodeInsts.size() > 0) {
            oldNodeInst = nodeInsts.get(0);
        }

        if (oldNodeInst != null) {
            // 保存阅知环节信息
            oldNodeInst.setDealTime(now);
            oldNodeInst.setInstStatus(WfInstStatus.办结);
            nodeInstService.persist(oldNodeInst);

            // 保存阅知意见
            AuditOpinionEditBean opinionEditBean = new AuditOpinionEditBean();
            opinionEditBean.setFlowInstId(flowInst.getId());
            opinionEditBean.setNodeId(oldNodeInst.getCurNodeId());
            opinionEditBean.setApproverId(bean.getDealerId());
            opinionEditBean.setApprResult(WfApprResult.通过);
            opinionEditBean.setApprOpinion(StringUtils.isBlank(bean.getOpinion())? "已阅" : bean.getOpinion());
            opinionEditBean.setNodeInstId(oldNodeInst.getId());
            if (opinionEditBean.getApprTime() == null) {
                opinionEditBean.setApprTime(now);
            }

            auditOpinionService.saveAuditOpinion(opinionEditBean);

        }

        return true;
    }


    //退回
    @Override
    public boolean returnNodeInst(NodeInstReturnBean bean, String backNodeId) {
    	WfFlowInst flowInst = flowInstService.findOne(bean.getFlowInstId());
        if (flowInst == null || WfInstStatus.办结.equals(flowInst.getInstStatus())) {
            throw new RuntimeException("操作失败！当前流程实例不存在或已办结。");
        }

        boolean isSubflow = StringUtils.isNotBlank(flowInst.getParentFlowInstId());
        String nodeId = bean.getNodeInst().getCurNodeId();

        WfNode nd = nodeService.findOne(nodeId);
        WfNode backNode = nodeService.findOne(backNodeId);
        if (backNode == null) {
            throw new RuntimeException("操作失败！当前流程中并没有这个环节：" + backNodeId);
        }

        if (isSubflow) {
        	//子流程的，只能在子流程的各环节内退回，不允许跳出子流程
        	if (!WfNodeType.子流程环节.equals(nd.getNodeType()) || !WfNodeType.子流程环节.equals(backNode.getNodeType())) {
        		throw new RuntimeException("只能在子流程内进行退回操作！");
        	}
        } else {
            if (!WfNodeType.普通.equals(nd.getNodeType())) {
            	throw new RuntimeException("操作失败！当前环节配置错误，不应该配置可退回操作：" + nodeId);
            }
            if (!WfNodeType.普通.equals(backNode.getNodeType())) {
            	throw new RuntimeException("操作失败！不允许退回目标环节：" + backNodeId);
            }
        }

        Long dealerId = bean.getDealerId();
        Date now = new Date();

        // 查找要处理的环节实例
        NodeInstQueryBean queryBean = new NodeInstQueryBean();
        queryBean.setFlowInstId(flowInst.getId());
        queryBean.setCurNodeId(backNodeId);
        queryBean.setNodeInstStatus(WfInstStatus.办结);
        List<WfNodeInst> backNodeInsts = nodeInstService.selectNodeInsts(queryBean);
        // 取最后一个，是最新的
        WfNodeInst backNodeInst = (backNodeInsts != null && backNodeInsts.size() > 0) ? backNodeInsts.get(backNodeInsts
                        .size() - 1) : null;
        if (backNodeInst == null) {
        	//表示您要退回的目标环节在办理过程中并没有走过，说明是不应该的
        	throw new RuntimeException("不允许退回未曾办理的目标环节：" + backNodeId);
        }

        // 查找该流程所有在办的环节实例
        List<WfNodeInst> nodeInsts = nodeInstService.findByFlowInstIdAndInstStatus(bean.getFlowInstId(), WfInstStatus.在办);
        //List<WfNodeInst> nodeInsts = nodeInstService.findByEntityIdAndInstStatus(bean.getEntityId(), WfInstStatus.在办);
        // 完成当前环节：是本人的就完成，不是本人的就删除
        for (WfNodeInst nodeInst : nodeInsts) {
            if (dealerId.equals(nodeInst.getDealerId()) && WfInstStatus.在办.equals(nodeInst.getInstStatus())) {
                nodeInst.setNextNodeId(backNodeInst.getCurNodeId());
                nodeInst.setDealTime(now);
                nodeInst.setInstStatus(WfInstStatus.办结);
                nodeInst.setReadFlag(WfReadFlag.已读);
                nodeInstService.persist(nodeInst);
            } else if (WfInstStatus.在办.equals(nodeInst.getInstStatus())) {
                nodeInstService.delete(nodeInst);
            }
        }

        // 复制退回的目标环节
        NodeInstEditBean sNodeInstBean = new NodeInstEditBean();
        sNodeInstBean.setFlowInstId(flowInst.getId());
        sNodeInstBean.setParentNodeInstId(backNodeInst.getParentNodeInstId());
        sNodeInstBean.setCurNodeId(backNodeInst.getCurNodeId());
        sNodeInstBean.setDealerId(backNodeInst.getDealerId());
        sNodeInstBean.setPreDealerId(dealerId);
        sNodeInstBean.setArrivalTime(now);
        sNodeInstBean.setEndCond(WfEndCond.单人办结);
        sNodeInstBean.setFlowDir(WfFlowDir.普通);
        sNodeInstBean.setDealType(WfDealType.阅办);
        sNodeInstBean.setInstStatus(WfInstStatus.在办);
        sNodeInstBean.setReadFlag(WfReadFlag.未读);
        nodeInstService.saveNodeInst(sNodeInstBean);
        // 更新流程实例
        flowInst.setCurNodeId(backNodeInst.getCurNodeId());
        flowInst.setInstStatus(WfInstStatus.在办);
        flowInstService.persist(flowInst);

        // 保存退回意见
        AuditOpinionEditBean backOpinion = new AuditOpinionEditBean();
        if (isSubflow) {
        	//意见表中只记录父流程实例ID
        	backOpinion.setFlowInstId(flowInst.getParentFlowInstId());
        } else {
        	backOpinion.setFlowInstId(flowInst.getId());
        }
        backOpinion.setNodeId(nodeId);
        backOpinion.setNodeInstId(bean.getNodeInst().getId());;
        backOpinion.setApproverId(bean.getDealerId());
        backOpinion.setApprResult(WfApprResult.通过);
        backOpinion.setApprTime(now);
        backOpinion.setApprOpinion(bean.getOpinion());
        auditOpinionService.saveAuditOpinion(backOpinion);

        return true;
    }


    @Override
    public String bringBackNodeInst(NodeInstBringBackBean bean) {
        WfFlowInst flowInst = bean.getFlowInst() == null ? flowInstService.getMainFlowInstByEntityId(bean.getEntityId()) : bean
                        .getFlowInst();
        if (flowInst == null) {
            return "操作失败！无法找到流程实例。";
        }
        String curNodeId = StringUtils.isBlank(bean.getCurNodeId()) ? flowInst.getCurNodeId() : bean.getCurNodeId();

        // 查找由当前用户发送出来的环节实例，检查这些实例是否未阅读过，全部未阅读的才能取回
        NodeInstQueryBean nodeInstQuery = new NodeInstQueryBean();
        nodeInstQuery.setFlowInstId(flowInst.getId());
        nodeInstQuery.setNodeInstStatus(WfInstStatus.在办);
        nodeInstQuery.setCurNodeId(curNodeId);
        List<WfNodeInst> nodeInsts = nodeInstService.selectNodeInsts(nodeInstQuery);
        int count = 0;
        for(WfNodeInst nd: nodeInsts) {
        	if (WfReadFlag.未读.equals(nd.getReadFlag())) {
        		count ++;
        	}
        	if (bean.getDealer().getId().equals(nd.getDealerId())) {
        		return "您是当前办理人，无需取回！";
        	}
        }
        if (count < nodeInsts.size() ) {
            // 有人阅读过，则无法取回
            return "该公文已被办理人收悉，无法取回！";
        }

        // 找到当前环节的上一个节点
        nodeInstQuery.setCurNodeId(null);
        nodeInstQuery.setNextNodeId(curNodeId);
        nodeInstQuery.setNodeInstStatus(WfInstStatus.办结);
        List<WfNodeInst> preNodeInsts = nodeInstService.selectNodeInsts(nodeInstQuery);
        // 取最后一个，是最新的
        WfNodeInst preNodeInst = (preNodeInsts != null && preNodeInsts.size() > 0) ? preNodeInsts.get(preNodeInsts
                        .size() - 1) : null;
        if (preNodeInst == null) {
//            throw new RuntimeException("处理失败，无法取回公文。preNodeInst==null");
            throw new RuntimeException("处理失败，无法取回公文。");
        }

        // 若当前登录人与上一节点的办理人的所在部门不同则不允许取回。按本人创建的公文只能由本人取回
        if (!preNodeInst.getDealerId().equals(bean.getDealer().getId())) {
            return "您不是上一步办理人，无法无法取回公文。";
        }

        // 更新实例状态
        preNodeInst.setInstStatus(WfInstStatus.在办);
        preNodeInst.setFlowDir(WfFlowDir.普通);
        preNodeInst.setReadFlag(WfReadFlag.未读);
        preNodeInst.setNextNodeId(null);
        preNodeInst.setDealTime(null);
        nodeInstService.persist(preNodeInst);
        // 删除历史环节
        for (WfNodeInst oldNodeInst : nodeInsts) {
            nodeInstService.delete(oldNodeInst);
        }
        flowInst.setCurNodeId(preNodeInst.getCurNodeId());
        flowInstService.persist(flowInst);

        return null;
    }

    @Override
    public List<WfNode> selectBranchNodeByFlowIdAndNodeId(String flowId, String nodeId) {
        // 获取流程环节定义
        WfNode node = nodeService.findOne(nodeId);
        if (node == null) {
            throw new RuntimeException("流程节点配置不正确(flowId:" + flowId + "[nodeId:" + nodeId + "])。");
        }
        List<WfNode> result = new ArrayList<WfNode>();
        List<WfNodeLine> list = this.nodeLineService.getNextNodes(flowId, nodeId);
        for (WfNodeLine line: list) {
            result.add(line.getToNode());
        }
        return result;
    }

    @Override
    public String getNodeIdByFlowInstIdAndDealer(String instId, Long dealerId) {
        NodeInstQueryBean queryBean = new NodeInstQueryBean();
        queryBean.setFlowInstId(instId);
        queryBean.setDealerId(dealerId);
        queryBean.setDealType(WfDealType.阅办);
        queryBean.setNodeInstStatus(WfInstStatus.在办);
        List<WfNodeInst> insts = nodeInstService.selectNodeInsts(queryBean);

        return (insts != null && insts.size() > 0) ? insts.get(0).getCurNodeId() : "";
    }

    @Override
    public WfAuditOpinion getAuditOpinionByNodeInstId(String instId) {
        return auditOpinionService.getByNodeInstId(instId);
    }

    @Override
    public List<WfAuditOpinion> selectAuditOpinionsByEntityIdForLetter(String entityId, WfBusinessType fileType, Integer verNum) {
        WfFlowInst flowInst = flowInstService.getMainFlowInstByEntityId(entityId);
        if (flowInst == null) {
            throw new RuntimeException("操作失败！无法找到流程实例。");
        }

        return auditOpinionService.findAuditOpinionsByFlowInstIdAndResult(flowInst.getId(),WfApprResult.通过);
        // 只查询有效意见，不查询暂存的意见。如果不写verNum，则查询所有版本
    }

    @Override
    public List<String> updateNodeReadStatus(String instId, String nodeId, Long dealerId) {
        NodeInstQueryBean queryBean = new NodeInstQueryBean();
        queryBean.setFlowInstId(instId);
        queryBean.setCurNodeId(nodeId);
        queryBean.setDealerId(dealerId);
        List<WfNodeInst> list = nodeInstService.selectNodeInsts(queryBean);
        List<String> aoIds = new ArrayList<String>();
        for (WfNodeInst inst : list) {
            if (!WfReadFlag.已读.equals(inst.getReadFlag())) {
                inst.setReadFlag(WfReadFlag.已读);
                //打开即自动阅知
                if (WfDealType.阅知.equals(inst.getDealType())) {
                	inst.setDealTime(new Date());
                	inst.setInstStatus(WfInstStatus.办结);

                	// 保存阅知意见
                    WfAuditOpinion ao = new WfAuditOpinion();
                    ao.setFlowInstId(inst.getFlowInstId());
                    ao.setNodeInstId(inst.getId());
                    ao.setNodeId(WorkflowUtils.NODE_READ);
                    ao.setApproverId(inst.getDealerId());
                    ao.setApprover(inst.getDealer());
                    ao.setApprResult(WfApprResult.通过);
                    ao.setApprOpinion("已阅");
                    ao.setApprTime(new Date());
                    ao = auditOpinionService.getRepository().save(ao);
                    aoIds.add(ao.getId());
                }
                nodeInstService.persist(inst);
            }
        }
        return aoIds;
    }


    /**
     * 清理指定流程实例的退回节点意见：为了不在下一环节显示，设置为未审
     *
     * @param flowInst
     */
    private void clearBackNodeOpinion(WfFlowInst flowInst) {
        AuditOpinionQueryBean bean = new AuditOpinionQueryBean();
        bean.setFlowInstId(flowInst.getId());
        bean.setNodeId(WorkflowUtils.NODE_OPNION_BACK);
        bean.setApprResult(WfApprResult.通过);

        List<WfAuditOpinion> list = auditOpinionService.selectAuditOpinions(bean);
        if (list != null && list.size() > 0) {
            for (WfAuditOpinion opinion : list) {
                opinion.setApprResult(WfApprResult.不通过);
                auditOpinionService.persist(opinion);
            }
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.workflow.event.service.WorkflowService#getWfNodeInstUndo(java.lang.String, java.lang.String)
     */
    @Override
    public List<WfNodeInst> getWfNodeInstUndo(String instId, String nodeId) {
        return nodeInstService.selectByCurNodeIds(instId, nodeId);
    }
}
