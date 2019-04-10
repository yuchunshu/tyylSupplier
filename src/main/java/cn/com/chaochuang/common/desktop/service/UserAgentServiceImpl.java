/**
 *
 */
package cn.com.chaochuang.common.desktop.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.desktop.bean.UserAgentInfo;
import cn.com.chaochuang.common.desktop.domain.UserAgent;
import cn.com.chaochuang.common.desktop.repository.UserAgentRepository;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.Tools;

/**
 * @author hzr 2016年10月9日
 *
 */
@Service
@Transactional
public class UserAgentServiceImpl extends SimpleLongIdCrudRestService<UserAgent> implements UserAgentService {

    @Autowired
    private UserAgentRepository repository;

    @PersistenceContext
    private EntityManager       em;

    @Override
    public SimpleDomainRepository<UserAgent, Long> getRepository() {
        return this.repository;
    }

    @Override
    public Long getAgentByUserId(Long userId) {
        StringBuilder sql = new StringBuilder(" from ");
        sql.append(UserAgent.class.getName());
        sql.append(" as t where t.userId = ?1 and t.beginTime <= ?2 and t.endTime >= ?2 ");
        sql.append(" order by t.createTime desc ");

        Query query = em.createQuery(sql.toString());
        query.setParameter(1, userId);
        query.setParameter(2, new Date());
        List list = query.getResultList();

        if (list == null || list.size() == 0) {
            return userId;
        } else {
            return ((UserAgent) list.get(0)).getAgentId();
        }
    }

    @Override
    public Long saveUserAgent(UserAgentInfo info) {
        UserAgent agent = null;
        SysUser curUser = (SysUser) UserTool.getInstance().getCurrentUser();
        if (info != null && info.getId() != null) {
            agent = this.repository.findOne(info.getId());
        } else {
            agent = new UserAgent();
        }

        BeanUtils.copyProperties(info, agent);
        if (agent.getCreateTime() == null) {
            agent.setCreateTime(new Date());
        }
        if (curUser != null) {
            agent.setUserId(curUser.getId());
        }
        this.repository.save(agent);
        return agent.getId();
    }

    @Override
    public UserAgent getAgentByUser(Long userId) {
        StringBuilder sql = new StringBuilder(" from ");
        sql.append(UserAgent.class.getName());
        sql.append(" as t where t.userId = ?1 and t.beginTime <= ?2 and t.endTime >= ?2 ");
        sql.append(" order by t.createTime desc ");

        Query query = em.createQuery(sql.toString());
        query.setParameter(1, userId);
        query.setParameter(2, new Date());
        List list = query.getResultList();
        if (Tools.isNotEmptyList(list)) {
            return ((UserAgent) list.get(0));
        }
        return null;
    }

    @Override
    public boolean delAgent(Long id) {// 删除当前代理人id
        UserAgent agent = null;
        if (id != null) {
            agent = this.repository.findOne(id);
            if (agent != null) {
                this.repository.delete(agent);
                return true;
            }
        }
        return false;
    }

}
