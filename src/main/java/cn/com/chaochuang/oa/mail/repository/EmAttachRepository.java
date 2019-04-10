/*
 * FileName:    EmAttachRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月26日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.oa.mail.domain.EmAttach;

/**
 * @author HM
 *
 */
public interface EmAttachRepository extends SimpleDomainRepository<EmAttach, String> {

    List<EmAttach> findByEmailId(String emailId);
}
