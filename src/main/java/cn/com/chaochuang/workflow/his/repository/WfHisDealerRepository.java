package cn.com.chaochuang.workflow.his.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.workflow.his.domain.WfHisDealer;

public interface WfHisDealerRepository extends SimpleDomainRepository<WfHisDealer, String> {

    public List<WfHisDealer> findByNodeIdOrderByUseTimeDesc(String nodeId);

    public WfHisDealer getByNodeIdAndOwnerIdAndDealerId(String nodeId, Long ownerId, Long dealerId);

    public List<WfHisDealer> findByNodeIdAndOwnerIdOrderByUseTimeDesc(String nodeId, Long ownerId);
}
