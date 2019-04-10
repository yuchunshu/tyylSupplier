package cn.com.chaochuang.workflow.inst.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;

public interface WfFlowInstService extends CrudRestService<WfFlowInst, String> {

    /**
     * 删除流实例
     *
     * @param instId
     */
    void delFlowInst(String instId);

    /**
     * 根据公文Id查询流实例
     *
     * @param entityId
     * @return
     */
    List<WfFlowInst> findByEntityId(String entityId);

    WfFlowInst getMainFlowInstByEntityId(String entityId);

    WfFlowInst getByFlowIdAndEntityId(String flowId, String entityId);

    /**
     * 删除流程实例
     *
     * @param entityId
     *            公文id
     * @return
     */
    int delFlowInstByEntityId(String entityId);

    /**根据父流程实例id查询子流程实例*/
    List<WfFlowInst> findByParentFlowInstId(String flowInstId);


}
