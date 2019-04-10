/*
 * FileName:    EmMainRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.oa.mail.domain.EmMain;

/**
 * @author HM
 *
 */
public interface EmMainRepository extends SimpleDomainRepository<EmMain, String> {

    /**
     * 查询saveDated大于lastOutputTime已发送的邮件
     *
     * @param lastOutputTime
     * @return
     */
    @Query("select e from EmMain e where e.saveDate > :time "
                    + "and ((e.status in(2,3) and e.saveFlag = 1) or (e.status=1 and e.saveFlag = 0))")
    List<EmMain> selectEmMainBysaveDate(@Param("time") Date lastOutputTime);

    /**
     * 查询邮件id大于id已发送的邮件
     *
     * @param
     * @return
     */
    @Query("select e from EmMain e where e.id > :id "
                    + "and ((e.status in(2,3) and e.saveFlag = 1) or (e.status=1 and e.saveFlag = 0))")
    List<EmMain> selectEmMainById(@Param("id") Long id);
}
