/*
 * FileName:    EmIncepterRepository.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.oa.mail.domain.EmIncepter;
import cn.com.chaochuang.oa.mail.reference.IncepterStatus;

/**
 * @author HM
 *
 */
public interface EmIncepterRepository extends SimpleDomainRepository<EmIncepter, String> {

    EmIncepter findByEmailIdAndIncepterId(String emailId, Long incepterId);

    List<EmIncepter> findByEmailIdAndStatusIn(String emailId, IncepterStatus status[]);

    List<EmIncepter> findByEmailId(String emailId);

    @Query("select e from EmIncepter e where e.status in(0,3) and e.mail.saveDate >:time")
    List<EmIncepter> selectEmIncepterBySaveDate(@Param("time") Date lastInputTime);

    @Query("select e from EmIncepter e where e.status in(0,3) and e.mail.id >:id")
    List<EmIncepter> selectEmIncepterById(@Param("id") String id);
}
