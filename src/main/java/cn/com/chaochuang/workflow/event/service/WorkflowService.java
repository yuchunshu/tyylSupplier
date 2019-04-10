package cn.com.chaochuang.workflow.event.service;

import java.util.List;
import java.util.Map;

import cn.com.chaochuang.workflow.def.bean.NodeCommonBean;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.inst.bean.AuditOpinionEditBean;
import cn.com.chaochuang.workflow.inst.bean.FlowInstCompleteBean;
import cn.com.chaochuang.workflow.inst.bean.FlowInstStartBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstBringBackBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstCheckBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstDealBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstReadBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstReadDealBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstReturnBean;
import cn.com.chaochuang.workflow.inst.domain.WfAuditOpinion;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfDoneResult;

/**
 * @author yuandl 2016-11-24
 *
 */
public interface WorkflowService {

    /**
     * 启动流程实例
     */
    Map startWorkflow(FlowInstStartBean bean);

    /**
     * 处理环节实例
     *
     * @param flowInst
     * @param nodeInst
     * @return
     */
    WfDoneResult dealNodeInst(NodeInstDealBean bean);

    /**
     * 检查环节实例，是否可以前进下一环节
     *
     * @param bean
     * @return
     */
    boolean isTheNodeInstFinished(NodeInstCheckBean bean);

    /**
     * 完成当前环节（环节处理的一种，用于领导阅示或者并发提交，之后检测是否可以自动办结）
     *
     * @param bean
     * @return
     */
    WfDoneResult readNodeInstAutoFinished(NodeInstDealBean bean);

    /**
     * 增加阅知环节
     *
     * @param bean
     * @return
     */
    boolean addReadNodeInst(NodeInstReadBean bean);

    /**
     * 完成阅知环节
     *
     * @param bean
     * @return
     */
    boolean dealNodeInstRead(NodeInstReadDealBean bean);

    /**
     * 退回环节
     */
    boolean returnNodeInst(NodeInstReturnBean bean, String backNodeId);

    /**
     * 取回当前环节
     *
     * @param bean
     * @return
     */
    String bringBackNodeInst(NodeInstBringBackBean bean);

    /**
     * 完成流程实例
     *
     * @param bean
     * @return
     */
    boolean completeWorkflow(FlowInstCompleteBean bean);

    /**
     * 根据公文id删除流程实例所有相关实例
     *
     * @param entityId
     */
    void delWorkflow(String entityId);


    /**
     * 保存环节意见，如果已存在同一人员的同一环节，则意见覆盖
     *
     * @param bean
     * @return
     */
    String saveNodeOpinion(AuditOpinionEditBean bean);

    void saveNodeOpinion(NodeInstDealBean bean);


    /**
     * 根据公文Id查找审批意见：只为部门函件使用，分两类，拟办和批示意见
     *
     * @param entityId
     * @param fileType
     * @param verNum
     * @return
     */
    List<WfAuditOpinion> selectAuditOpinionsByEntityIdForLetter(String entityId, WfBusinessType fileType,Integer verNum);

    /**
     * 查找指定流程的环节实例
     *
     * @param instId
     * @param nodeId
     * @return
     */
    WfNodeInst getNodeInstByFlowInstIdAndNodeId(String instId, String nodeId);

    /**
     * 根据流定义和环节定义查找环节的可选分支
     *
     * @param flowId
     * @param nodeId
     * @return
     */
    List<WfNode> selectBranchNodeByFlowIdAndNodeId(String flowId, String nodeId);

    /**
     * 根据流定义和环节定义查找流程环节定义
     *
     * @param flowId
     * @param nodeId
     * @return
     */
    //WfNode getNodeByFlowIdAndNodeId(String flowId, String nodeId);

    /**
     * 根据流程实例和处理人查找环节id
     *
     * @param instId
     * @param dealerId
     * @return
     */
    String getNodeIdByFlowInstIdAndDealer(String instId, Long dealerId);

    /**
     * 更新环节实例阅读状态
     *
     * @param instId
     * @param nodeId
     * @param dealerId
     * @return
     */
    List<String> updateNodeReadStatus(String instId, String nodeId, Long dealerId);

    /**
     * 查找环节实例意见
     *
     * @param instId
     * @param nodeId
     * @param dealer
     * @return
     */
    WfAuditOpinion getAuditOpinionByNodeInstId(String instId);

    /**
     * 查找未完成的子环节
     *
     * @param instId
     * @param nodeId
     * @param dealer
     * @return
     */
    List<WfNodeInst> getWfNodeInstUndo(String instId, String nodeId);


    /**根据当前环节，预取下一环节的信息（主要是用于检查下一个环节是否需要弹出选择，还是直接通过配置信息锁定下一环节和办理人，不需要再弹出选择）
     * 返回值：包括下一环节ID、办理人、是否需要弹出选择等信息*/
    NodeCommonBean getNextNodeDataBeforeDealing(String flowId, String curNodeId, String curNodeInstId);



}
