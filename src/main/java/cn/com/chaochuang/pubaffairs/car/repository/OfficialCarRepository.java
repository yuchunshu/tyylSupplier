package cn.com.chaochuang.pubaffairs.car.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.pubaffairs.car.domain.OfficialCar;

/**
 * @author dengl 2018.08.08
 *
 */
public interface OfficialCarRepository extends SimpleDomainRepository<OfficialCar, String> {

	@Query(" select count(*) from OfficialCar b where b.creatorId=:creatorId and b.status = '1' ")
    Integer countOfficialCar(@Param("creatorId") Long creatorId);
}
