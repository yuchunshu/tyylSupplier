/*
 * FileName:    OaPersonalGroupRepository.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.personal.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.oa.personal.domain.OaPersonalGroup;

/**
 * @author HM
 *
 */
public interface OaPersonalGroupRepository extends SimpleDomainRepository<OaPersonalGroup, Long> {

    public List<OaPersonalGroup> findByUserOrderByOrderNumAsc(SysUser user);

}
