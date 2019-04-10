package cn.com.chaochuang.workflow.inst.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.reference.FlowSort;
import cn.com.chaochuang.workflow.inst.bean.NodeInstEditBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstQueryBean;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.repository.WfNodeInstRepository;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfInstStatus;

@Service
@Transactional
public class WfNodeInstServiceImpl extends SimpleUuidCrudRestService<WfNodeInst> implements WfNodeInstService {

    @PersistenceContext
    private EntityManager       em;

    @Autowired
    private WfNodeInstRepository repository;

    @Override
    public SimpleDomainRepository<WfNodeInst, String> getRepository() {
        return repository;
    }

    @Override
    public WfNodeInst saveNodeInst(NodeInstEditBean bean) {
        WfNodeInst nodeInst = null;
        if (bean != null && StringUtils.isNotBlank(bean.getId())) {
            nodeInst = repository.findOne(bean.getId());
        } else {
            nodeInst = new WfNodeInst();
        }
        BeanUtils.copyProperties(bean, nodeInst);
        repository.save(nodeInst);

        return nodeInst;
    }

    @Override
    public void delNodeInst(String id) {
        if (StringUtils.isNotBlank(id)) {
            WfNodeInst nodeInst = repository.findOne(id);
            if (nodeInst != null) {
                repository.delete(nodeInst);
            }
        }
    }

    @Override
    public WfNodeInst getByFlowInstIdAndCurNodeId(String instId, String curNodeId) {
        return repository.getByFlowInstIdAndCurNodeId(instId, curNodeId);
    }

    @Override
    public WfNodeInst getByFlowInstIdAndCurNodeIdAndDealer(String instId, String curNodeId, Long dealer) {
        return repository.getByFlowInstIdAndCurNodeIdAndDealerId(instId, curNodeId, dealer);
    }

    @Override
    public List<WfNodeInst> selectNodeInsts(NodeInstQueryBean bean) {
        List<Order> orders = new ArrayList<Order>();
        orders.add(new Order(Direction.ASC, "arrivalTime"));
        orders.add(new Order(Direction.ASC, "curNodeId"));
        return repository.findAll(getWhereClause(bean), new Sort(orders));
    }

    @Override
    public List<WfNodeInst> selectByCurNodeIds(String instId, String NodeIds) {
        return repository.selectByCurNodeIds(instId, NodeIds);
    }


    @Override
	public List<String> selectNodeInstIds(String entityId) {
    	List<WfNodeInst> list = this.findByEntityId(entityId);
    	List<String> resList = new ArrayList();
    	if (list == null || list.isEmpty()) {
    		return null;
    	} else {
    		for(WfNodeInst ni: list) {
    			resList.add(ni.getCurNodeId());
    		}
    		return resList;
    	}
	}

	@Override
    public List<WfNodeInst> findByFlowInstId(String instId) {
        return repository.findByFlowInstIdOrderByArrivalTimeAscCurNodeIdAsc(instId);
    }

    @Override
    public List<WfNodeInst> findByParentNodeInstId(String instId) {
        return repository.findByParentNodeInstId(instId);
    }

    @Override
    public List<WfNodeInst> findByEntityId(String entityId) {
        return repository.findByFlowInstEntityIdOrderByArrivalTimeAscCurNodeIdAsc(entityId);
    }

    @Override
    public List<WfNodeInst> findByEntityIdDesc(String entityId) {
        return repository.findByFlowInstEntityIdOrderByArrivalTimeDescCurNodeIdDesc(entityId);
    }

    @Override
	public List<WfNodeInst> findByEntityIdAndInstStatus(String entityId, WfInstStatus status) {
		return repository.findByFlowInstEntityIdAndInstStatus(entityId, status);
	}

	@Override
	public List<WfNodeInst> findByFlowInstIdAndInstStatus(String flowInstId, WfInstStatus status) {
		return repository.findByFlowInstIdAndInstStatus(flowInstId, status);
	}

	@Override
	public List<WfNodeInst> findByExclusiveKey(String exclusiveKey) {
    	return repository.findByExclusiveKey(exclusiveKey);
	}

	@Override
    public int delNodeInstsByFlowInstId(String instId) {
        return repository.deleteByFlowInstId(instId);
    }

    @Override
    public int delNodeInstsByEntityId(String entityId) {
        return repository.deleteByFlowInstEntityId(entityId);
    }

    private Specification<WfNodeInst> getWhereClause(final NodeInstQueryBean bean) {
        return new Specification<WfNodeInst>() {
            @Override
            public Predicate toPredicate(Root<WfNodeInst> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(bean.getFlowInstId())) {
                    predicate.getExpressions().add(cb.equal(root.get("flowInstId"), bean.getFlowInstId()));
                }
                if (StringUtils.isNotBlank(bean.getCurNodeId())) {
                    predicate.getExpressions().add(cb.equal(root.get("curNodeId"), bean.getCurNodeId()));
                }
                if (StringUtils.isNotBlank(bean.getNextNodeId())) {
                    predicate.getExpressions().add(cb.equal(root.get("nextNodeId"), bean.getNextNodeId()));
                }
                if (bean.getDealType() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("dealType"), bean.getDealType()));
                }
                if (bean.getDealerId() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("dealerId"), bean.getDealerId()));
                }
                if (bean.getDealDeptId() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("dealDeptId"), bean.getDealDeptId()));
                }
                if (bean.getNodeInstStatus() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("instStatus"), bean.getNodeInstStatus()));
                }
                if (StringUtils.isNotBlank(bean.getInCurNodeIds())) {
                    String[] idArr = bean.getInCurNodeIds().split(",");
                    In<Object> in = cb.in(root.get("curNodeId"));
                    for (String nodeId : idArr) {
                        in.value(nodeId);
                    }
                    predicate.getExpressions().add(in);
                }
                return predicate;
            }

        };
    }

    @Override
    public String isCurDealerByFlowInstId(String instId, Long dealerId) {
        List<WfNodeInst> list = repository.selectCurNodeInstByFlowInstIdAndDealerId(instId, dealerId, WfInstStatus.在办);
        if (list != null && list.size() > 0) {
            return list.get(0).getCurNodeId();
        } else {
            return "";
        }
    }

    @Override
    public List<WfNodeInst> getByFlowInstIdAndCurNodeIdAndDealerIdAndInstStatus(String instId, String curNodeId,
                    Long dealer, WfInstStatus instStatus) {

        return this.repository.getByFlowInstIdAndCurNodeIdAndDealerIdAndInstStatus(instId, curNodeId, dealer, instStatus);
    }

	@Override
	public String getFlowIdByEntityId(String entityId) {
		List<WfNodeInst> list = this.findByEntityId(entityId);
    	if (list == null || list.isEmpty()) {
    		return null;
    	} else {
    		return list.get(0).getFlowInst().getFlowId();
    	}
	}

    //公文待办数统计
	@Override
	public long getTaskingCount(Long userId) {
    	StringBuilder sqlBuilder = new StringBuilder(" select count(*) from ");
    	sqlBuilder.append(WfNodeInst.class.getName()).append(" as a, ");
    	sqlBuilder.append(WfFlowInst.class.getName()).append(" as b ");
    	sqlBuilder.append(" where a.instStatus='0' and a.curNodeId <> 'N888' and a.dealerId = ").append(userId);
    	sqlBuilder.append(" and b.businessType in (1,2,3,5) ");
    	sqlBuilder.append(" and b.id = a.flowInstId ");
    	Query query = em.createQuery(sqlBuilder.toString());
    	List list = query.getResultList();
    	if (list != null && !list.isEmpty()) {
    		return (Long)list.get(0);
    	}

    	return 0;
	}
	
	//流程环节待办数统计
	@Override
	public long getTaskingCount(Long userId,WfBusinessType flowType,FlowSort flowSort) {
	    StringBuilder sqlBuilder = new StringBuilder(" select count(*) from ");
	    sqlBuilder.append(WfNodeInst.class.getName()).append(" as a ");
	    sqlBuilder.append(" where a.instStatus='0' and a.curNodeId <> 'N888' and a.dealerId = ").append(userId);
	    sqlBuilder.append(" and a.flowInst.flow.flowType = ").append(flowType);
	    sqlBuilder.append(" and a.flowInst.flow.flowSort = ").append(flowSort);
	    Query query = em.createQuery(sqlBuilder.toString());
	    List list = query.getResultList();
	    if (list != null && !list.isEmpty()) {
	    	return (Long)list.get(0);
	    }

	    return 0;
	}


    //公文待阅数统计
	@Override
	public long getReadingCount(Long userId) {
    	StringBuilder sqlBuilder = new StringBuilder(" select count(*) from ");
    	sqlBuilder.append(WfNodeInst.class.getName()).append(" as a ");
    	sqlBuilder.append(" where a.instStatus='0' and a.curNodeId = 'N888' and a.dealerId = ").append(userId);
    	Query query = em.createQuery(sqlBuilder.toString());
    	List list = query.getResultList();
    	if (list != null && !list.isEmpty()) {
    		return (Long)list.get(0);
    	}

    	return 0;
	}

}
