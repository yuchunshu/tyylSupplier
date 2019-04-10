package cn.com.chaochuang.workflow.def.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.workflow.def.domain.WfNode;

/**
 * @author yuandl 2016-12-03
 *
 */
public interface WfNodeRepository extends SimpleDomainRepository<WfNode, String> {

    /**
     * 查找流程所有环节定义
     *
     * @param flowId
     * @return
     */
    List<WfNode> findByFlowIdOrderByIdAsc(String flowId);
    
    /**
     * 查找流程根据环节名称过滤后的环节定义
     *
     * @param flowId
     * @param nodeNames
     * @return
     */
    List<WfNode> findByFlowIdAndNodeNameNotInOrderByIdAsc(String flowId,String[] nodeNames);

    /**
     * 根据子流程查找所有环节流程定义
     *
     * @param subflowKey
     * @return
     */
    List<WfNode> findBySubflowIdOrderByIdAsc(String subflowId);

    /**
     * 根据流程定义和汇聚标识查找流程环节定义
     *
     * @param flowId
     * @param comFlag
     * @return
     */
    List<WfNode> findByFlowIdAndComFlag(String flowId, YesNo comFlag);


    /**
     * 根据子环节ID查询流程环节
     */
    List<WfNode> findBySubflowId(String subflowId);


    /**取得流程环节的sort最大值*/
    @Query("select max(n.sort) from WfNode n where n.flowId=:flowId")
    Long getMaxNodeSort(String flowId);
    
    /** 查找本流程环节，可以包括开始结束环 */
    @Query("select n from WfNode n where n.flowId=:flowId or n.id=:startId or n.id=:endId")
    List<WfNode> findByFlowIdAndEndId(@Param("flowId")String flowId, @Param("startId") String startId, @Param("endId") String endId);

}
