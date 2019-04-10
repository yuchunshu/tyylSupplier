/*
 * FileName:    LogRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2014
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
