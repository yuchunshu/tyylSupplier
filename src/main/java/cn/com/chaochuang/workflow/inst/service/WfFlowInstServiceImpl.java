package cn.com.chaochuang.workflow.inst.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.inst.repository.WfFlowInstRepository;

@Service
@Transactional
public class WfFlowInstServiceImpl extends SimpleUuidCrudRestService<WfFlowInst> implements WfFlowInstService {

    @Autowired
    private WfFlowInstRepository repository;

    @Override
    public SimpleDomainRepository<WfFlowInst, String> getRepository() {
        return repository;
    }

    @Override
    public void delFlowInst(String instId) {
        if (StringUtils.isNotBlank(instId)) {
            WfFlowInst inst = repository.findOne(instId);
            if (inst != null) {
                repository.delete(inst);
            }
        }
    }


    @Override
	public List<WfFlowInst> findByEntityId(String entityId) {
		return repository.findByEntityId(entityId);
	}


    @Override
	public WfFlowInst getMainFlowInstByEntityId(String entityId) {
		return repository.getMainFlowInstByEntityId(entityId);
	}

	@Override
	public WfFlowInst getByFlowIdAndEntityId(String flowId, String entityId) {
		return repository.getByFlowIdAndEntityId(flowId, entityId);
	}

	@Override
    public int delFlowInstByEntityId(String entityId) {
        return repository.deleteByEntityId(entityId);
    }

	@Override
	public List<WfFlowInst> findByParentFlowInstId(String flowInstId) {
		return repository.findByParentFlowInstId(flowInstId);
	}

}
