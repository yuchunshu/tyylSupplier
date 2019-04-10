package cn.com.chaochuang.workflow.inst.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.workflow.inst.bean.AuditOpinionEditBean;
import cn.com.chaochuang.workflow.inst.bean.AuditOpinionQueryBean;
import cn.com.chaochuang.workflow.inst.domain.WfAuditOpinion;
import cn.com.chaochuang.workflow.reference.WfApprResult;

public interface WfAuditOpinionService extends CrudRestService<WfAuditOpinion, String> {

    /**
     * 保存环节审批意见
     *
     * @param bean
     * @return
     */
    public String saveAuditOpinion(AuditOpinionEditBean bean);

    /**
     * 删除环节审批意见
     *
     * @param id
     */
    public void delAuditOpinion(String id);

    /**
     * 根据流程实例、环节实例、处理人查询环节审批意见
     *
     * @param instId
     * @param nodeId
     * @param Dealer
     * @return
     */
    public WfAuditOpinion getByNodeInstId(String instId);

    /**
     * 查找所有审批意见
     *
     */
    public List<WfAuditOpinion> findAuditOpinionsByEntityId(String entityId);

    /**
     * 查找所有审批意见
     *
     * @param instId
     *            流程实例id
     * @return
     */
    public List<WfAuditOpinion> findAuditOpinionsByFlowInstIdAndResult(String instId, WfApprResult result);

    List<WfAuditOpinion> findAuditOpinionsByFlowInstId(String instId);

    /**
     * 删除所有审批意见
     *
     * @param instId
     *            流程实例id
     * @return
     */
    public int delAuditOpinionsByFlowInstId(String instId);

    /**
     * 删除所有审批意见
     *
     */
    public int delAuditOpinionsByEntityId(String entityId);

    /**
     * 查询审批意见
     *
     * @param bean
     * @return
     */
    public List<WfAuditOpinion> selectAuditOpinions(AuditOpinionQueryBean bean);
}
