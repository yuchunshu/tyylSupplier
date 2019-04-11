/*
 * FileName:    OaAddressPersonalRepository.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.address.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.oa.address.domain.OaAddressPersonal;

/**
 * @author HM
 *
 */
public interface OaAddressPersonalRepository extends SimpleDomainRepository<OaAddressPersonal, Long> {
    List<OaAddressPersonal> findByIdGreaterThanOrderByIdAsc(Long id);

    /**
     * 查询个人添加的通讯录
     * @param creator
     * @return
     */
    List<OaAddressPersonal> findByCreatorIdOrderByOrderNumAsc(Long creator);

}
