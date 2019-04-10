package cn.com.chaochuang.workflow.inst.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.workflow.inst.domain.VNodeInst;

public interface VNodeInstService extends CrudRestService<VNodeInst, Long> {
    /**
     * 查询公文未完成的处理人Id
     *
     */
    public List<Long> selectUndoDealerIdByEntityId(String entityId);
}
