/*
 * FileName:    LogRepository.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年12月8日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.log.repository;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.log.domain.SysLog;

/**
 * @author LaoZhiYong
 *
 */
public interface LogRepository extends SimpleDomainRepository<SysLog, Long> {

}
