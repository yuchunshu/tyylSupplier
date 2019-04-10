package cn.com.chaochuang.workflow.inst.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.workflow.inst.domain.WfAuditOpinion;
import cn.com.chaochuang.workflow.reference.WfApprResult;

/**
 * @author yuandl 2016-11-27
 *
 */
public interface WfAuditOpinionRepository extends SimpleDomainRepository<WfAuditOpinion, String> {

    public WfAuditOpinion getByNodeInstId(String instId);

    public List<WfAuditOpinion> findByFlowInstIdAndApprResultOrderByApprTimeAsc(String instId, WfApprResult result);

    List<WfAuditOpinion> findByFlowInstIdOrderByApprTimeAsc(String instId);

    public List<WfAuditOpinion> findByFlowInstEntityIdOrderByApprTimeAsc(String entityId);

    public int deleteByFlowInstId(String instId);

    public int deleteByFlowInstEntityId(String entityId);
}
