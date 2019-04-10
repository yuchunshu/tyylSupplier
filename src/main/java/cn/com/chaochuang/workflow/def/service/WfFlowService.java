package cn.com.chaochuang.workflow.def.service;

import java.util.List;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.reference.AdministrativeLevel;
import cn.com.chaochuang.workflow.def.bean.FlowEditBean;
import cn.com.chaochuang.workflow.def.domain.WfFlow;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * @author yuandl 2016-11-24
 *
 */
public interface WfFlowService extends CrudRestService<WfFlow, String> {

    String saveFlow(FlowEditBean bean);

    void updateFlowJason(FlowEditBean bean);

    /**
     * 删除流（逻辑删除）
     *
     * @param flowId
     */
    void delFlow(String flowId);


    WfFlow getByParentFlowId(String parentFlowId);

    List<WfFlow> findByFlowType(WfBusinessType flowType);
    
    List<WfFlow> findAllExceptOneself(String flowId);
    
    /** 
     * @Title: selectCanWorkFlow 
     * @Description: 根据流程分类、部门级别查询可用的流程。如果不是通用流程，再加入部门进行过滤
     * @param flowType
     * @return
     * @return: List<WfFlow>
     */
    List<WfFlow> selectCanWorkFlow(WfBusinessType flowType);
    
    /** 
     * @Title: findByFlowLevelAndParentFlowIdNotNull 
     * @Description: 根据流程级别查询父流程
     * @param flowLevel
     * @return
     * @return: List<WfFlow>
     */
    List<WfFlow> findByFlowLevelAndParentFlowIdNotNull(AdministrativeLevel flowLevel,String flowId);


    /** 
     * @Title: selectSubFlow 
     * @Description: 查询子流程
     * @param bean
     * @param page
     * @param rows
     * @return
     * @return: Page
     */
    Page selectSubFlow(FlowEditBean bean, Integer page, Integer rows);

}
