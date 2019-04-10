package cn.com.chaochuang.workflow.def.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.workflow.def.domain.WfNodeDept;

/**
 * @author yuandl 2016-11-24
 *
 */
public interface WfNodeDeptRepository extends SimpleDomainRepository<WfNodeDept, Long> {

    public List<WfNodeDept> findByFlowId(String flowId);

    public List<WfNodeDept> findByFlowIdAndNodeId(String flowId, String nodeId);

}
