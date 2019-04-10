package cn.com.chaochuang.workflow.def.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.select.bean.UserSelectShowBean;
import cn.com.chaochuang.workflow.def.bean.NodeDeptEditBean;
import cn.com.chaochuang.workflow.def.bean.NodeDeptSelectTreeBean;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.def.domain.WfNodeDept;

public interface WfNodeDeptService extends CrudRestService<WfNodeDept, Long> {

    /**
     * 保存环节接收定义
     *
     * @param bean
     * @return
     */
    Long saveNodeDept(NodeDeptEditBean bean);

    /**
     * 删除环节接收定义
     *
     * @param id
     */
    void delNodeDept(Long id);

    /**
     * 根据流程定义、环节定义查找接收定义
     *
     * @param flowId
     * @param nodeId
     * @param receiveFlag
     * @return
     */
    List<WfNodeDept> findByFlowIdAndNodeId(String flowId, String nodeId);

    /**
     * 查找下一步可选环节的相关数据(包括可选部门、可选人员和)
     *
     * @param flowId
     * @param nodeId
     * @return
     */
    String allNodeDept(String flowId, List<WfNode> nodes);

    String nextNodeManOrDept(String flowId, String nodeId);

    /**
     * 查找下一环节接收人
     *
     * @param flowId
     * @param nodeId
     * @return
     */
    List<UserSelectShowBean> selectReceiver(String flowId, String nodeId,String userNameLike);
    /**
     * 查找下一环节接收部门
     * @param flowId
     * @param nodeId
     * @return
     */
    List<NodeDeptSelectTreeBean> selectDeps(String flowId, String nodeId);
    /**
     * 下一环节历史选择人员
     * @param flowId
     * @param nodeId
     * @return
     */
    List<UserSelectShowBean> selectHistory(String nodeId);

}
