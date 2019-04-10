package cn.com.chaochuang.workflow.def.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.workflow.def.bean.FlowDeptEditBean;
import cn.com.chaochuang.workflow.def.domain.WfFlowDept;
import cn.com.chaochuang.workflow.def.repository.WfFlowDeptRepository;

/**
 * @author yuchunshu 2017-11-21
 *
 */
@Service
@Transactional
public class WfFlowDeptServiceImpl extends SimpleLongIdCrudRestService<WfFlowDept>
                implements WfFlowDeptService {

    @Autowired
    private WfFlowDeptRepository    repository;

    @Override
    public SimpleDomainRepository<WfFlowDept, Long> getRepository() {
        return repository;
    }

    @Override
    public Long saveFlowDept(FlowDeptEditBean bean) {
        WfFlowDept receive = null;
        if (bean != null && bean.getId() != null) {
            receive = repository.findOne(bean.getId());
        } else {
            receive = new WfFlowDept();
        }
        BeanUtils.copyProperties(bean, receive);
        repository.save(receive);

        return receive.getId();
    }

    @Override
    public void delFlowDept(Long id) {
        if (id != null) {
            WfFlowDept receive = repository.findOne(id);
            if (receive != null) {
                repository.delete(receive);
            }
        }
    }

    @Override
    public List<WfFlowDept> findByFlowId(String flowId) {
        return repository.findByFlowId(flowId);
    }

	@Override
	public void deleteByFlowId(String flowId) {
		 repository.deleteByFlowId(flowId);
	}

}
