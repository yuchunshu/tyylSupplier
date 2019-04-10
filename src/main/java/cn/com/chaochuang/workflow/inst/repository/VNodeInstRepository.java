package cn.com.chaochuang.workflow.inst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.workflow.inst.domain.VNodeInst;
import cn.com.chaochuang.workflow.reference.WfDealType;
import cn.com.chaochuang.workflow.reference.WfInstStatus;

public interface VNodeInstRepository extends SimpleDomainRepository<VNodeInst, Long> {
    @Query("select v.dealerId from VNodeInst v where v.entityId=:entityId and v.instStatus=:instStatus and v.nodeInstStatus=:nodeInstStatus and v.dealType=:dealType")
    public List<Long> selectByFileIdAndInstStatusAndNodeInstStatusAndDealType(@Param("entityId") String entityId,
                    @Param("instStatus") WfInstStatus instStatus, @Param("nodeInstStatus") WfInstStatus nodeInstStatus,
                    @Param("dealType") WfDealType dealType);
}
