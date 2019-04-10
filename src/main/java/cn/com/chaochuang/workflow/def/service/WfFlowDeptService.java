package cn.com.chaochuang.workflow.def.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.workflow.def.bean.FlowDeptEditBean;
import cn.com.chaochuang.workflow.def.domain.WfFlowDept;

/**
 * @author yuchunshu 2017-11-21
 *
 */
public interface WfFlowDeptService extends CrudRestService<WfFlowDept, Long> {

    /**
     * 保存流程部门定义
     *
     * @param bean
     * @return
     */
    Long saveFlowDept(FlowDeptEditBean bean);

    /**
     * 删除环节接收定义
     *
     * @param id
     */
    void delFlowDept(Long id);

    /**
     * 根据流程定义查找部门定义
     *
     * @param flowId
     * @return
     */
    List<WfFlowDept> findByFlowId(String flowId);
    
    void deleteByFlowId(String flowId);


}
