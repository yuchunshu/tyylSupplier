package cn.com.chaochuang.workflow.def.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.reference.AdministrativeLevel;
import cn.com.chaochuang.common.reference.EnableStatus;
import cn.com.chaochuang.workflow.def.domain.WfFlow;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * @author yuandl 2016年11月24日
 *
 */
public interface WfFlowRepository extends SimpleDomainRepository<WfFlow, String> {

	public WfFlow getByParentFlowId(String parentFlowId);

	public List<WfFlow> findByFlowTypeInOrderByFlowTypeAsc(WfBusinessType[] nodeId);
	
	List<WfFlow> findByFlowTypeAndFlowStatus(WfBusinessType flowType, EnableStatus flowStatus);
	
	@Query("select f from WfFlow f where f.id <> :flowId")
	List<WfFlow> findAllExceptOneself(@Param("flowId") String flowId);
	
	@Query("select f from WfFlow f where (f.parentFlowId is null or f.parentFlowId = '') and f.flowType =:flowType and f.flowStatus =:flowStatus and f.flowLevel =:flowLevel")
	List<WfFlow> findByParentFlowIdNotNull(@Param("flowType") WfBusinessType flowType,@Param("flowStatus") EnableStatus flowStatus,@Param("flowLevel") AdministrativeLevel flowLevel);

	@Query("select f from WfFlow f where (f.parentFlowId is null or f.parentFlowId = '')  and f.flowStatus =:flowStatus and f.flowLevel =:flowLevel and f.id <> :flowId")
	List<WfFlow> findByParentFlowIdNotNull(@Param("flowStatus") EnableStatus flowStatus,@Param("flowLevel") AdministrativeLevel flowLevel,@Param("flowId") String flowId);

}
