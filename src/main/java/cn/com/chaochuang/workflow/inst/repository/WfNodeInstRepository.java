package cn.com.chaochuang.workflow.inst.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfInstStatus;

public interface WfNodeInstRepository extends SimpleDomainRepository<WfNodeInst, String> {

    WfNodeInst getByFlowInstIdAndCurNodeId(String instId, String curNodeId);

    List<WfNodeInst> getByFlowInstIdAndCurNodeIdAndDealerIdAndInstStatus(String instId, String curNodeId,
                    Long dealerId, WfInstStatus instStatus);

    WfNodeInst getByFlowInstIdAndCurNodeIdAndDealerId(String instId, String curNodeId, Long dealerId);

    @Query("select n from WfNodeInst n where n.flowInstId=:instId and curNodeId=:nodeId and nextNodeId=:nextNodeId and dealerId=:dealer and instStatus=:status order by arrivalTime asc")
    List<WfNodeInst> selectNodeInsts(@Param("instId") String instId, @Param("nodeId") String nodeId,
                    @Param("nextNodeId") String nextNodeId, @Param("dealer") Long dealer,
                    @Param("status") WfInstStatus status);

    @Query("select n from WfNodeInst n where n.flowInstId=:instId and curNodeId in (':nodeIds')")
    List<WfNodeInst> selectByCurNodeIds(@Param("instId") String instId, @Param("nodeIds") String nodeIds);

    List<WfNodeInst> findByFlowInstIdOrderByArrivalTimeAscCurNodeIdAsc(String instId);
    List<WfNodeInst> findByParentNodeInstId(String instId);

    List<WfNodeInst> findByFlowInstEntityIdOrderByArrivalTimeAscCurNodeIdAsc(String entityId);
    List<WfNodeInst> findByFlowInstEntityIdOrderByArrivalTimeDescCurNodeIdDesc(String entityId);

    List<WfNodeInst> findByFlowInstEntityIdAndInstStatus(String entityId, WfInstStatus status);
    List<WfNodeInst> findByFlowInstIdAndInstStatus(String flowInstId, WfInstStatus status);

    List<WfNodeInst> findByExclusiveKey(String exclusiveKey);

    int deleteByFlowInstId(String instId);

    int deleteByFlowInstEntityId(String entityId);

    @Query("select n from WfNodeInst n where n.flowInstId=:instId and n.dealerId=:dealerId and n.flowInst.curNodeId = n.curNodeId and n.instStatus=:status")
    List<WfNodeInst> selectCurNodeInstByFlowInstIdAndDealerId(@Param("instId") String instId,
                    @Param("dealerId") Long dealerId, @Param("status") WfInstStatus status);

    /**
     * 查询用户准备过期的待办
     * @param userId
     * @param flowTypeNotEqu
     * @param nodeIdNotEqu
     * @return
     */
    @Query("select nodeInst from WfNodeInst nodeInst where nodeInst.instStatus = '0' and nodeInst.dealer.id=:userId and nodeInst.curNode.flow.flowType <> :flowTypeNotEqu and nodeInst.curNodeId <> :nodeIdNotEqu and nodeInst.arrivalTime is not null and nodeInst.nodeExpiryMinute is not null and nodeInst.nodeWarnMinute is not null and (to_date(:queryTime,'yyyy-mm-dd hh24:mi:ss') - nodeInst.arrivalTime)*24*60 between nodeInst.nodeExpiryMinute-nodeInst.nodeWarnMinute and nodeInst.nodeExpiryMinute")
    Page<WfNodeInst> findWarnNodeInst(@Param("userId") Long userId, @Param("flowTypeNotEqu") WfBusinessType flowTypeNotEqu, @Param("nodeIdNotEqu") String nodeIdNotEqu, @Param("queryTime") String queryTime, Pageable pageable);

    /**
     * 查询用户超期的待办
     * @param userId
     * @param flowTypeNotEqu
     * @param nodeIdNotEqu
     * @return
     */
    @Query("select nodeInst from WfNodeInst nodeInst where nodeInst.instStatus = '0' and nodeInst.dealer.id=:userId and nodeInst.curNode.flow.flowType <> :flowTypeNotEqu and nodeInst.curNodeId <> :nodeIdNotEqu and nodeInst.arrivalTime is not null and nodeInst.nodeExpiryMinute is not null and nodeInst.nodeWarnMinute is not null and (to_date(:queryTime,'yyyy-mm-dd hh24:mi:ss') - nodeInst.arrivalTime)*24*60> nodeInst.nodeExpiryMinute")
    Page<WfNodeInst> findExpireNodeInst(@Param("userId") Long userId, @Param("flowTypeNotEqu") WfBusinessType flowTypeNotEqu, @Param("nodeIdNotEqu") String nodeIdNotEqu, @Param("queryTime") String queryTime, Pageable pageable);

    //@Query("select n from WfNodeInst n where n.flowInstId=:instId and curNodeId =:nodeIds and n.instStatus=:instStatus")
    //List<WfNodeInst> selectUndoNodeInst(@Param("instId") String instId, @Param("nodeIds") String nodeIds,
    //                @Param("instStatus") WfInstStatus instStatus);


//    @Query("select n from WfNodeInst n where n.flowInstId=:instId and n.subflowInstId like :subflowKey and n.instStatus=:instStatus")
//    List<WfNodeInst> selectUndealSubflowNodeInst(@Param("instId") String instId, @Param("subflowKey") String subflowKey,
//                    @Param("instStatus") WfInstStatus instStatus);
//
}
