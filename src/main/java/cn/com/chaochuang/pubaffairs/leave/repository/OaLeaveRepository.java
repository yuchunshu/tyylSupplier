package cn.com.chaochuang.pubaffairs.leave.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.pubaffairs.leave.domain.OaLeave;

/**
 * @author dengl 2018.08.08
 *
 */

public interface OaLeaveRepository extends SimpleDomainRepository<OaLeave, String> {

	@Query(" select count(*) from OaLeave b where b.creatorId=:creatorId and b.status = '1' ")
    Integer countOaLeave(@Param("creatorId") Long creatorId);
}
