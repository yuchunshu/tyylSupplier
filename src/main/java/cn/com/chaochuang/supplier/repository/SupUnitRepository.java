package cn.com.chaochuang.supplier.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.supplier.domain.SupUnit;

public interface SupUnitRepository extends SimpleDomainRepository<SupUnit, Long> {

    public List<SupUnit> findByGroupId(Long groupId);

}
