package cn.com.chaochuang.workflow.inst.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.workflow.inst.bean.AuditOpinionEditBean;
import cn.com.chaochuang.workflow.inst.bean.AuditOpinionQueryBean;
import cn.com.chaochuang.workflow.inst.domain.WfAuditOpinion;
import cn.com.chaochuang.workflow.inst.repository.WfAuditOpinionRepository;
import cn.com.chaochuang.workflow.reference.WfApprResult;

@Service
@Transactional
public class WfAuditOpinionServiceImpl extends SimpleUuidCrudRestService<WfAuditOpinion>
                implements WfAuditOpinionService {

    @Autowired
    private WfAuditOpinionRepository 	repository;

    @Value("${workflow.opinion.showLastOnly}")
    private Boolean           			opinionShowLastOnly;

    @Override
    public SimpleDomainRepository<WfAuditOpinion, String> getRepository() {
        return repository;
    }

    @Override
    public String saveAuditOpinion(AuditOpinionEditBean bean) {
        WfAuditOpinion opinion = null;
        if (bean != null && StringUtils.isNotBlank(bean.getId())) {
            opinion = repository.findOne(bean.getId());
        } else {
            opinion = new WfAuditOpinion();
        }
        BeanUtils.copyProperties(bean, opinion);
        repository.save(opinion);

        return opinion.getId();
    }

    @Override
    public void delAuditOpinion(String id) {
    	if (StringUtils.isNotBlank(id)) {
            WfAuditOpinion opinion = repository.findOne(id);
            if (opinion != null) {
                repository.delete(opinion);
            }
        }
    }

    @Override
    public WfAuditOpinion getByNodeInstId(String instId) {
        return repository.getByNodeInstId(instId);
    }

    @Override
    public List<WfAuditOpinion> findAuditOpinionsByEntityId(String entityId) {
    	List<WfAuditOpinion> list = repository.findByFlowInstEntityIdOrderByApprTimeAsc(entityId);
    	if (this.opinionShowLastOnly) {
    		return getLastTimeOpinions(list);
    	}
    	return list;
    }

    @Override
    public List<WfAuditOpinion> findAuditOpinionsByFlowInstIdAndResult(String instId, WfApprResult result) {
    	List<WfAuditOpinion> list = repository.findByFlowInstIdAndApprResultOrderByApprTimeAsc(instId, result);
    	if (this.opinionShowLastOnly) {
    		return getLastTimeOpinions(list);
    	}
    	return list;
    }

    @Override
    public List<WfAuditOpinion> findAuditOpinionsByFlowInstId(String instId) {
        List<WfAuditOpinion> list = repository.findByFlowInstIdOrderByApprTimeAsc(instId);
        if (this.opinionShowLastOnly) {
            return getLastTimeOpinions(list);
        }
        return list;
    }

    private List<WfAuditOpinion> getLastTimeOpinions(List<WfAuditOpinion> list) {
    	//环节ID和审核人ID均相同，则只取时间最新的记录
    	List<WfAuditOpinion> newlist = new ArrayList();

    	for (int i=0; i<list.size(); i++) {
    		boolean flag = false;
    		for (WfAuditOpinion ao: newlist) {
    			if (list.get(i).getNodeId().equals(ao.getNodeId()) && list.get(i).getApproverId().equals(ao.getApproverId())) {
    				//因为时间排序可能有正序或反序，如果newlist已加有元素且时间较早的话，则先删除
    				if (list.get(i).getApprTime().after(ao.getApprTime())) {
    					newlist.remove(ao);
    					break;
    				}
    				flag = true;
    			}
    		}

    		if (!flag) {
    			newlist.add(list.get(i));
    			flag = false;
    		}
    	}

    	return newlist;
    }


    @Override
    public int delAuditOpinionsByFlowInstId(String instId) {
        return repository.deleteByFlowInstId(instId);
    }

    @Override
    public int delAuditOpinionsByEntityId(String entityId) {
        return repository.deleteByFlowInstEntityId(entityId);
    }

    private Specification<WfAuditOpinion> getWhereCluse(final AuditOpinionQueryBean bean) {
        return new Specification<WfAuditOpinion>() {

            @Override
            public Predicate toPredicate(Root<WfAuditOpinion> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(bean.getFlowInstId())) {
                    predicate.getExpressions().add(cb.equal(root.get("flowInstId"), bean.getFlowInstId()));
                }
                if (StringUtils.isNotBlank(bean.getNodeId())) {
                    predicate.getExpressions().add(cb.equal(root.get("nodeId"), bean.getNodeId()));
                }
                if (bean.getApproverId() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("approverId"), bean.getApproverId()));
                }
                if (bean.getApprResult() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("apprResult"), bean.getApprResult()));
                }
                return predicate;
            }

        };
    }

    @Override
    public List<WfAuditOpinion> selectAuditOpinions(AuditOpinionQueryBean bean) {
        return repository.findAll(getWhereCluse(bean));
    }

}
