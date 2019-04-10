package cn.com.chaochuang.workflow.inst.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleCrudRestService;
import cn.com.chaochuang.workflow.inst.domain.VNodeInst;
import cn.com.chaochuang.workflow.inst.repository.VNodeInstRepository;
import cn.com.chaochuang.workflow.reference.WfDealType;
import cn.com.chaochuang.workflow.reference.WfInstStatus;

@Service
public class VNodeInstServiceImpl extends SimpleCrudRestService<VNodeInst, Long> implements VNodeInstService {

    @Autowired
    private VNodeInstRepository repository;

    @Override
    public SimpleDomainRepository<VNodeInst, Long> getRepository() {
        return repository;
    }

    @Override
    public List<Long> selectUndoDealerIdByEntityId(String entityId) {
        return repository.selectByFileIdAndInstStatusAndNodeInstStatusAndDealType(entityId, WfInstStatus.在办,
                        WfInstStatus.在办, WfDealType.阅办);
    }

}
