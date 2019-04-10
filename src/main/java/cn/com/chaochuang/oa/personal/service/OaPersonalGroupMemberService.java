/*
 * FileName:    OaPersonalGroupMemberService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.personal.service;

import java.util.List;
import java.util.Map;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.oa.personal.domain.OaPersonalGroupMember;

/**
 * @author HM
 *
 */
public interface OaPersonalGroupMemberService extends CrudRestService<OaPersonalGroupMember, Long> {

    public List<OaPersonalGroupMember> findByGroupId(Long groupId);

    public Map<String, Object> selectGroupMembers(Long groupId, Integer page, Integer rows);
}
