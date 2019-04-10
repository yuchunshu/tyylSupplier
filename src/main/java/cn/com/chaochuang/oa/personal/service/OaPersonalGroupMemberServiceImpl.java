/*
 * FileName:    OaPersonalGroupMemberServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.personal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.oa.personal.domain.OaPersonalGroupMember;
import cn.com.chaochuang.oa.personal.repository.OaPersonalGroupMemberRepository;

/**
 * @author HM
 *
 */
@Service
@Transactional
public class OaPersonalGroupMemberServiceImpl extends SimpleLongIdCrudRestService<OaPersonalGroupMember> implements
                OaPersonalGroupMemberService {

    // private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    private OaPersonalGroupMemberRepository repository;

    @Autowired
    private SysUserService                  userService;

    @Autowired
    private SysAttachService                attachService;

    @PersistenceContext
    private EntityManager                   em;

    @Override
    public SimpleDomainRepository<OaPersonalGroupMember, Long> getRepository() {
        return this.repository;
    }

    @Override
    public List<OaPersonalGroupMember> findByGroupId(Long groupId) {
        if (groupId != null) {
            return this.repository.findByGroupId(groupId);
        }
        return null;
    }

    @Override
    public Map<String, Object> selectGroupMembers(Long groupId, Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" SELECT m.member FROM ").append(OaPersonalGroupMember.class.getSimpleName()).append(" AS m ");
        sqlBuffer.append(" WHERE m.groupId=:groupId ");
        Query query = em.createQuery(sqlBuffer.toString());
        query.setParameter("groupId", groupId);
        List totalList = query.getResultList();
        if (totalList != null) {
            map.put("total", totalList.size());
        } else {
            map.put("total", 0);
            return map;
        }
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        map.put("datas", query.getResultList());
        return map;
    }

}
