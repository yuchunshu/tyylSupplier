/*
 * FileName:    OaPersonalGroupMemberRepository.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.personal.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.oa.personal.domain.OaPersonalGroupMember;

/**
 * @author HM
 *
 */
public interface OaPersonalGroupMemberRepository extends SimpleDomainRepository<OaPersonalGroupMember, Long> {

    List<OaPersonalGroupMember> findByGroupId(Long groupId);

}
