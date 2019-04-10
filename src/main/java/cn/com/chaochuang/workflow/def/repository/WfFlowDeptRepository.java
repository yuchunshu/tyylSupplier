package cn.com.chaochuang.workflow.def.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.workflow.def.domain.WfFlowDept;

/**
 * @author yuchunshu 2017-11-21
 *
 */
public interface WfFlowDeptRepository extends SimpleDomainRepository<WfFlowDept, Long> {

    List<WfFlowDept> findByFlowId(String flowId);

    void deleteByFlowId(String flowId);
}
