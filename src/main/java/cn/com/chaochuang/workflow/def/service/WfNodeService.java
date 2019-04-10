package cn.com.chaochuang.workflow.def.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.workflow.def.bean.NodeChartBean;
import cn.com.chaochuang.workflow.def.bean.NodeEditBean;
import cn.com.chaochuang.workflow.def.domain.WfNode;

/**
 * @author yuandl 2016-11-24
 *
 */
public interface WfNodeService extends CrudRestService<WfNode, String> {

    /**
     * 保存流环节定义
     *
     * @param bean
     * @return
     */
    String saveNode(NodeEditBean bean);

    /**
     * 删除流环节定义
     */
    void delNode(String id);

    /**
     * 查找流程环节定义
     *
     * @param flowId
     * @param nodeId
     * @return
     */
    WfNode getByNodeId(String nodeId);

    /**
     * 根据流程定义查找流程环节定义
     *
     * @param flowId
     * @return
     */
    List<WfNode> findByFlowId(String flowId);

    /**
     * 根据子流程定义查找流程环节定义
     *
     * @param flowId
     * @return
     */
    List<WfNode> findBySubflowId(String subflowKId);

    /**
     * 根据流程定义和汇聚标识查找流程环节定义
     *
     * @param flowId
     * @param comFlag
     * @return
     */
    List<WfNode> findByFlowIdAndComFlag(String flowId, YesNo comFlag);


    /**取得流程的第一个环节（N0000指向的下个环节）*/
    WfNode getFirstTrueNode(String flowId);

    /** 根据flowId和nodeIds查找不在参数nodeIds里的数据 */
    List<NodeChartBean> findByFlowIdAndNodeIdIsNotExist(String flowId, String nodeIds);
    
    /** 查找本流程环节，可以包括开始结束环 */
    List<WfNode> findByFlowIdOrStartIdOrEndId(String flowId, String startId, String endId);

}
