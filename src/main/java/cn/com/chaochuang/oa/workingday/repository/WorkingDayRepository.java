/*
 * FileName:    OaReceiveFileRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.oa.workingday.repository;

import java.util.Date;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.oa.workingday.domain.WorkingDay;

/**
 * @author huangwq
 *
 */
public interface WorkingDayRepository extends SimpleDomainRepository<WorkingDay, Long> {
    public WorkingDay findByDayDate(Date dayDate);
}
