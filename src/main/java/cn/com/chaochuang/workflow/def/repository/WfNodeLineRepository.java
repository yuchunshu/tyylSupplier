package cn.com.chaochuang.workflow.def.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.workflow.def.domain.WfNodeLine;

/**
 * @author hzr 2017-10-12
 *
 */
public interface WfNodeLineRepository extends SimpleDomainRepository<WfNodeLine, String> {

	List<WfNodeLine> findByFlowIdAndFromNodeIdAndDirFlag(String flowId, String fromNodeId, String dirFlag);

	List<WfNodeLine> findByFlowIdAndFromNodeId(String flowId, String fromNodeId);

	List<WfNodeLine> findByFlowIdAndDirFlag(String flowId, String dirFlag);

}
