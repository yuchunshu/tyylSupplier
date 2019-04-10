package cn.com.chaochuang.workflow.inst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;

public interface WfFlowInstRepository extends SimpleDomainRepository<WfFlowInst, String> {

    @Query("select n from WfFlowInst n where n.entityId=:entityId and (n.parentFlowInstId is null or n.parentFlowInstId = '') ")
    WfFlowInst getMainFlowInstByEntityId(@Param("entityId") String entityId);

    List<WfFlowInst> findByEntityId(String entityId);

    WfFlowInst getByFlowIdAndEntityId(String flowId, String entityId);

    int deleteByEntityId(String entityId);

    List<WfFlowInst> findByParentFlowInstId(String flowInstId);

}
