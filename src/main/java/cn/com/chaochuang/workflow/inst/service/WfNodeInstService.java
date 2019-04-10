package cn.com.chaochuang.workflow.inst.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.reference.FlowSort;
import cn.com.chaochuang.workflow.inst.bean.NodeInstEditBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstQueryBean;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfInstStatus;

public interface WfNodeInstService extends CrudRestService<WfNodeInst, String> {

    /**
     * 保存环节实例
     *
     * @param bean
     * @return
     */
    WfNodeInst saveNodeInst(NodeInstEditBean bean);

    /**
     * 删除环节实例
     *
     * @param id
     */
    void delNodeInst(String id);

    /**
     * 查找流程实例的环节实例
     *
     * @param instId
     * @param curNodeId
     * @return
     */
    WfNodeInst getByFlowInstIdAndCurNodeId(String instId, String curNodeId);

    /**
     * 根据流程实例、当前环节定义、处理人查找环节实例
     *
     * @param instId
     * @param curNodeId
     * @param dealer
     * @return
     */
    WfNodeInst getByFlowInstIdAndCurNodeIdAndDealer(String instId, String curNodeId, Long dealer);

    /**
     * 查询环节实例
     *
     * @param bean
     * @return
     */
    List<WfNodeInst> selectNodeInsts(NodeInstQueryBean bean);

    /**
     * 根据流程实例和环节定义查询环节实例
     *
     * @param instId
     * @param NodeIds
     * @return
     */
    List<WfNodeInst> selectByCurNodeIds(String instId, String NodeIds);

    /**
     * 根据流程实例查找所有环节实例，按时间升序
     *
     * @param instId
     * @return
     */

    List<WfNodeInst> findByFlowInstId(String instId);
    List<WfNodeInst> findByParentNodeInstId(String instId);

    /**
     * 根据公文实例查找所有环节实例，按时间升序
     *
     * @param entityId
     * @return
     */
    List<WfNodeInst> findByEntityId(String entityId);

    /**
     * 根据公文实例查找所有环节实例，按时间降序
     *
     * @param entityId
     * @return
     */
    List<WfNodeInst> findByEntityIdDesc(String entityId);

    List<WfNodeInst> findByEntityIdAndInstStatus(String entityId, WfInstStatus status);

    List<WfNodeInst> findByFlowInstIdAndInstStatus(String flowInstId, WfInstStatus status);

    List<WfNodeInst> findByExclusiveKey(String exclusiveKey);

    /** 返回办理过的流程环节ID列表（列表中最后一个ID为当前办理环节），用于流程图显示。注意：因为是实例，所以有重复办理过的ID */
    List<String> selectNodeInstIds(String entityId);

    /**
     * 根据流程实例删除环节实例
     *
     * @param instId
     * @return
     */
    int delNodeInstsByFlowInstId(String instId);

    /**
     * 判断用户是否当前待处理环节的用户，如果是，则返回当前环节taskId
     *
     * @param instId
     * @param dealerId
     * @return
     */
    String isCurDealerByFlowInstId(String instId, Long dealerId);

    /**
     * 根据公文id删除环节实例
     *
     * @param entityId
     * @return
     */
    int delNodeInstsByEntityId(String entityId);

    /**
     * 查未完成的子流程环节
     *
     */
    //List<WfNodeInst> selectUndealSubflowNodeInst(String instId, String subflowKey, WfInstStatus instStatus);

    /**
     * 根据流程实例、当前环节定义、处理人查找环节实例
     *
     * @param instId
     * @param curNodeId
     * @param dealer
     * @return
     */
    List<WfNodeInst> getByFlowInstIdAndCurNodeIdAndDealerIdAndInstStatus(String instId, String curNodeId,
                    Long dealer, WfInstStatus instStatus);

    /**
     * 根据WfNodeInst id查找WfFlow id
     * @param entityId WfNodeInst的id
     * @return
     */
    String getFlowIdByEntityId(String entityId);


    /** 待办数量 */
    long getTaskingCount(Long userId);
    
    /** 根据流程类别、流程类型查询待办数量 */
    long getTaskingCount(Long userId,WfBusinessType flowType,FlowSort flowSort);

    /** 待阅数量*/
    long getReadingCount(Long userId);


}
