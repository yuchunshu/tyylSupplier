/**
 *
 */
package cn.com.chaochuang.doc.event.service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.common.desktop.service.UserAgentService;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.reference.FeedbackFlag;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.docread.bean.DocReadEditBean;
import cn.com.chaochuang.doc.docread.reference.DocReadStatus;
import cn.com.chaochuang.doc.docread.service.DocReadService;
import cn.com.chaochuang.doc.event.bean.OaDocFileDispatchBean;
import cn.com.chaochuang.doc.event.bean.OaDocFileEditBean;
import cn.com.chaochuang.doc.event.bean.OaDocFileFromLetterBean;
import cn.com.chaochuang.doc.event.bean.OaDocFileQueryBean;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.domain.OaOuterDocFile;
import cn.com.chaochuang.doc.event.reference.FileStatusFlag;
import cn.com.chaochuang.doc.event.repository.OaOuterDocFileRepository;
import cn.com.chaochuang.doc.expiredate.domain.OaDocNodeExpireDate;
import cn.com.chaochuang.doc.expiredate.domain.SysNodeExpireDate;
import cn.com.chaochuang.doc.expiredate.reference.DeadlineType;
import cn.com.chaochuang.doc.expiredate.reference.DeadlineTypeConverter;
import cn.com.chaochuang.doc.expiredate.repository.OaDocNodeExpireDateRepository;
import cn.com.chaochuang.doc.expiredate.repository.SysNodeExpireDateRepository;
import cn.com.chaochuang.doc.letter.bean.DepLetterInfo;
import cn.com.chaochuang.doc.letter.domain.DocDepLetter;
import cn.com.chaochuang.doc.letter.domain.DocDepLetterReceiver;
import cn.com.chaochuang.doc.letter.service.DocDepLetterReceiverService;
import cn.com.chaochuang.doc.letter.service.DocDepLetterService;
import cn.com.chaochuang.doc.process.service.DocProcessService;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapEnvelope;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvRecFlag;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteDocSwapEnvelopeRepository;
import cn.com.chaochuang.doc.remotedocswap.service.RemoteDocSwapEnvelopeService;
import cn.com.chaochuang.doc.util.DocFileUtils;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.def.repository.WfNodeRepository;
import cn.com.chaochuang.workflow.def.service.WfNodeService;
import cn.com.chaochuang.workflow.event.service.WorkflowService;
import cn.com.chaochuang.workflow.his.bean.HisDealerEditBean;
import cn.com.chaochuang.workflow.his.service.WfHisDealerService;
import cn.com.chaochuang.workflow.inst.bean.AuditOpinionEditBean;
import cn.com.chaochuang.workflow.inst.bean.FlowInstCompleteBean;
import cn.com.chaochuang.workflow.inst.bean.FlowInstStartBean;
import cn.com.chaochuang.workflow.inst.bean.InstCommonShowBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstBringBackBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstDealBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstReadBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstReadDealBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstReturnBean;
import cn.com.chaochuang.workflow.inst.domain.WfAuditOpinion;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.repository.WfFlowInstRepository;
import cn.com.chaochuang.workflow.inst.service.WfAuditOpinionService;
import cn.com.chaochuang.workflow.inst.service.WfFlowInstService;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfDoneResult;
import cn.com.chaochuang.workflow.reference.WfInstStatus;
import cn.com.chaochuang.workflow.reference.WfInstStatusConverter;
import cn.com.chaochuang.workflow.reference.WfReadFlag;
import cn.com.chaochuang.workflow.util.WorkflowUtils;
import cn.com.chaochuang.workflow.reference.WfApprResult;

/**
 * @author hzr 2016年5月28日
 *
 */
@Component
@Transactional
public class DocEventService {
    @PersistenceContext
    private EntityManager                	em;

    @Autowired
    private LogService                   	logService;

    @Autowired
    private OaDocFileService             	docFileService;

    @Autowired
    private DocProcessService            	docProcessService;

    @Autowired
    protected UserAgentService           	userAgentService;

    @Autowired
    private WorkflowService              	workflowService;
    @Autowired
    private WfAuditOpinionService        	opinionService;
    @Autowired
    private WfHisDealerService           	hisDealerService;

    @Autowired
    private DocDepLetterService          	depLetterService;
    @Autowired
    private DocDepLetterReceiverService  	depLetterReceiverService;
    @Autowired
    private DocReadService               	docReadService;

    @Autowired
    private WfNodeInstService            	nodeInstService;

    @Autowired
    private WfFlowInstService            	flowInstService;

    @Autowired
    private WfNodeService            		nodeService;

    @Autowired
    private RemoteDocSwapEnvelopeService 	envelopeService;

    @Autowired
    private RemoteDocSwapEnvelopeRepository envelopeRepository;
    
    @Autowired
    private OaDocNodeExpireDateRepository   docNodeExpireDateRepository;
    
    @Autowired
    private SysNodeExpireDateRepository   	nodeExpireDateRepository;
    
    @Autowired
    private OaOuterDocFileRepository   		outerDocFileRepository;
    
    @Autowired
    private WfFlowInstRepository			wfFlowInstRepository;
    
    @Autowired
    private WfNodeRepository				wfNodeRepository;
    
    /**
     * 启动公文流程
     *
     * @param bean
     * @param request
     * @return
     * @throws ParseException 
     */
    public ReturnBean createFlowData(OaDocFileEditBean bean, HttpServletRequest request) throws ParseException {
        SysUser user = bean.getCurrentUser();
        bean.setStartFlag(true);

        if(StringUtils.isNotBlank(bean.getParentId())){
        	bean.setId(null);
        }
        
        if (bean != null) {
            bean.setStatus(FileStatusFlag.在办);
        }
        // 保存公文信息
        bean.setDocEditable(YesNo.是);

        // 限时办结-转内部公文
        if(null != bean.getEnvelopeId()){
        	bean.setDocCode(bean.getDocCode().replace("〔", "[").replace("〕", "]"));
        }

        ReturnBean ret = this.docFileService.saveDocFile(bean);
        if (!ret.isSuccess()) return ret;

        OaDocFile obj = (OaDocFile)ret.getObject();

        // 新增纸质交换反馈
        if("true".equals(bean.getFeedBackFlag())){
            envelopeService.saveFeedBackPaperDoc(obj, bean);
        }

        // 启动流程
        FlowInstStartBean startBean = new FlowInstStartBean();
        
        // 限时办结-转内部公文
        if(null != bean.getEnvelopeId()){
        	RemoteDocSwapEnvelope envelope = this.envelopeRepository.findOne(bean.getEnvelopeId());
        	if(null != envelope){
        		envelope.setInstId(obj.getId());
        		envelope.setSelfLimitTime(bean.getExpiryDate());
        		startBean.setSendTime(envelope.getSendTime());//来文登记的送达时间等于限时办结的发送时间
        		this.envelopeRepository.save(envelope);
        	}

        }
        
        // 三级贯通-系统外发-转内部公文
        if(StringUtils.isNotBlank(bean.getOuterId())){
        	OaOuterDocFile outerDoc = this.outerDocFileRepository.findOne(bean.getOuterId());
        	if(null != outerDoc){
        		outerDoc.setInsideId(obj.getId());
        		this.outerDocFileRepository.save(outerDoc);
        	}
        	
        }
        
        startBean.setFlowId(bean.getFlowId());
        startBean.setEntityId(obj.getId());
        startBean.setTitle(obj.getTitle());
        startBean.setSncode(obj.getSncode());
        startBean.setBusinessType(obj.getFileType().getKey());
        startBean.setDealer(user);
        startBean.setExpiryDate(obj.getExpiryDate());
        startBean.setUrgencyLevel(obj.getUrgencyLevel().getKey());
        startBean.setParentEntityId(obj.getParentId());;
        Map startMap = workflowService.startWorkflow(startBean);
        WfFlowInst flowInst = (WfFlowInst)startMap.get("flowInst");
        WfNodeInst firstNodeInst = (WfNodeInst)startMap.get("firstNodeInst");
        WfNode node = (WfNode)startMap.get("node");
        if (flowInst == null) {
            throw new RuntimeException("流程启动失败，请检查流程配置。");
        }

        obj.setFlowId(flowInst.getFlowId());
        obj.setFlowInstId(flowInst.getId());
        docFileService.persist(obj);
        bean.setFlowInstId(flowInst.getId());

        //收文流程在来文呈送的时候就设置环节限办时间
        if(WfBusinessType.收文.equals(node.getFlow().getFlowType())){
        	//限办办结要求是"其他"
        	if("7".equals(bean.getDeadlineType())){
        		//保存环节限办时间设置
                saveOaDocNodeExpireDate(bean.getNodeIds(),bean.getNodeExpireMinutes(),obj.getId());
        	}
        	
        }
        
        // 处理首环节
        bean.setCurNodeInstId(firstNodeInst.getId());
        bean.setCurNodeId(flowInst.getCurNodeId());
        bean.setDocEditable(YesNo.否);// 已经保存过了，不再重复
        bean.setUrgencyLevel(obj.getUrgencyLevel());
        if (DocFileUtils.DEP_WENSHUKE.equals(user.getDepartment().getId()) && flowInst.getFlowId().equals("F01")) {
            // 文书科在外单位来文要跳过部门拟稿环节
            String nextNodeId = bean.getNextNodeId();
            String nextDealers = bean.getNextDealers();

            bean.setNextNodeId("N102");
            bean.setNextDealers(user.getId().toString());// 下一步自己办理
            this.completeNodeDone(bean, request);

            bean.setCurNodeId("N102");
            // 恢复原来的处理人数据
            bean.setNextNodeId(nextNodeId);
            bean.setNextDealers(nextDealers);
        }
        this.completeNodeDone(bean, request);

        //如果该文是限时办结-转内部公文，呈送时给待办人员发送短信
        RemoteDocSwapEnvelope envelope = this.envelopeRepository.findByInstIdAndReceiveFlag(obj.getId(),RemoteEnvRecFlag.接收);
        if(null!=envelope){
        	envelopeService.saveFordoForDocSwap(bean.getNextDealers(), bean.getNextNodeId(), envelope);
        }
        
        logService.saveUserLog(user, SjType.普通操作, "启动流程：" + bean.getTitle() + "，实例ID：" + flowInst.getId(),LogStatus.成功,request);

        return new ReturnBean("流程启动成功！", flowInst, true);

    }

    /**
     * 流程环节处理
     *
     * @param bean
     * @param request
     * @throws ParseException 
     */
    public void completeNodeDone(OaDocFileEditBean bean, HttpServletRequest request){
        String nextNodeId = bean.getNextNodeId();
        String nextDealers = bean.getNextDealers();
        // 保存公文信息
        OaDocFile obj = null;
        if (!bean.isStartFlag()) {
            // 非启动环节，更新公文信息
        	ReturnBean ret = this.docFileService.updateDocFileInfo(bean, request);
            if (ret.isSuccess()) {
                obj = (OaDocFile)ret.getObject();
            } else {
            	throw new RuntimeException(ret.getMessage());
            }
        } else {
        	obj = this.docFileService.findOne(bean.getId());
        }

        SysUser user = bean.getCurrentUser();

        // 设置审批意见
        WfAuditOpinion auditOpinion = new WfAuditOpinion();
        auditOpinion.setApprOpinion(bean.getOpinion());
        if (bean.getOpinionDate() != null) {
            auditOpinion.setApprTime(bean.getOpinionDate());
        }

        // 构成处理信息
        NodeInstDealBean dealBean = new NodeInstDealBean();
        dealBean.setEntityId(bean.getId());
        dealBean.setTitle(obj.getTitle());
        dealBean.setBusinessType(obj.getFileType().getKey());
        dealBean.setSncode(obj.getSncode());
        dealBean.setNodeId(bean.getCurNodeId());
        dealBean.setDealer(user.getId());
        dealBean.setNextNodeId(nextNodeId);// 设置下一环节
        if (StringUtils.isNotBlank(nextDealers)) {// 设置下一步处理人
            dealBean.setNextDealer(nextDealers.split(","));
        }

        dealBean.setNode(nodeService.findOne(bean.getCurNodeId()));
        dealBean.setNodeInst(nodeInstService.findOne(bean.getCurNodeInstId()));
        dealBean.setFlowInst(flowInstService.findOne(bean.getFlowInstId()));
        // dealBean.setNextDealDept(bean.getNextDealDept());// 设置下一步处理部门
        dealBean.setAuditOpinion(auditOpinion);// 设置审批意见
        dealBean.setDeptShare(bean.getDeptShare());//设置本处室共享标识
        //dealBean.setFd(fd);// 设置待办元素
        dealBean.setExpiryDate(bean.getExpiryDate());//设置流程限办时间
        
        //发文流程在局办公室核文的时候就设置环节限办时间
        if("局办公室核文".equals(dealBean.getNode().getNodeName())){
            //限办办结要求是"其他"
        	if("7".equals(bean.getDeadlineType())){
        		//保存环节限办时间设置
                saveOaDocNodeExpireDate(bean.getNodeIds(),bean.getNodeExpireMinutes(),obj.getId());
        	}
        }
        
        String deadlineType = Tools.isEmptyString(bean.getDeadlineType())?obj.getDeadlineType():bean.getDeadlineType();
        RemoteDocSwapType documentType = null==bean.getDocumentType()?obj.getDocumentType():bean.getDocumentType();
        
        //如果限办要求不是"其他"，环节限办时间以初始化数据为准
        if(!"7".equals(deadlineType) && null == bean.getEnvelopeId()){
        	List<SysNodeExpireDate> expireList = nodeExpireDateRepository.findByDocumentTypeAndDeadlineTypeAndNodeId(documentType, new DeadlineTypeConverter().convertToEntityAttribute(deadlineType), bean.getNextNodeId());
        	
        	dealBean.setNodeExpiryMinute(getNodeExpireMinute(expireList));
        	dealBean.setEarlyWarnMinute(getEarlyWarnMinute(expireList));
        }else{//如果限办要求是"其他"，环节限办时间以页面配置数据为准
        	OaDocNodeExpireDate dned= docNodeExpireDateRepository.findByFileIdAndNodeId(obj.getId(), bean.getNextNodeId());
			if(null!=dned){
				dealBean.setNodeExpiryMinute(dned.getNodeExpireMinute());
			}
        }
        
        if(bean.getUrgencyLevel()!=null){
        	dealBean.setUrgencyLevel(bean.getUrgencyLevel().getKey());
        }
        if(obj.getUrgencyLevel()!=null){
        	dealBean.setUrgencyLevel(obj.getUrgencyLevel().getKey());
        }
        // 完成当前环节
        WfDoneResult result = workflowService.dealNodeInst(dealBean);

        if (WfDoneResult.环节办结.equals(result)) {
            // 保存环节历史选择
            HisDealerEditBean mansBean = new HisDealerEditBean();
            mansBean.setNodeId(nextNodeId);
            mansBean.setOwnerId(user.getId());
            mansBean.setUseTime(new Date());
            mansBean.setDealerIds(nextDealers);
            hisDealerService.updateHisDealer(mansBean);

            // 写入登记簿
            docProcessService.saveDocProcess(user, obj);
        } else if (WfDoneResult.流程办结.equals(result)) {
            // 设置公文完结
            obj.setStatus(FileStatusFlag.待归档);
            docFileService.save(obj);
        }
    }

    /**
     * 阅知处理（打开直接自动完成阅知）
     */
    public ReturnBean completeNodeReadDone(OaDocFileEditBean bean, HttpServletRequest request) {
        WfNodeInst ni = nodeInstService.findOne(bean.getCurNodeInstId());
        if (ni != null && WfInstStatus.办结.equals(ni.getInstStatus())) {
            return new ReturnBean("任务已失效，可能已被其它人抢先执行！");
        } else {
        	ni.setDealTime(new Date());
        	ni.setReadFlag(WfReadFlag.已读);
        	ni.setInstStatus(WfInstStatus.办结);
        	nodeInstService.persist(ni);
        	return new ReturnBean("阅知完成！", ni, true);
        }
    }

    /**
     * 阅示、提交（自动办结）处理
     *
     * @param bean
     * @param request
     */
    public void completeNodeSignDone(OaDocFileEditBean bean, HttpServletRequest request) {
        SysUser user = bean.getCurrentUser();
        // 保存公文信息
        ReturnBean ret  = this.docFileService.saveDocFile(bean);
        if (!ret.isSuccess()) {
            throw new RuntimeException(ret.getMessage());
        }

        // 设置审批意见
        WfAuditOpinion auditOpinion = new WfAuditOpinion();
        auditOpinion.setApprOpinion(bean.getOpinion());
        if (bean.getOpinionDate() != null) {
            auditOpinion.setApprTime(bean.getOpinionDate());
        }

        NodeInstDealBean dealBean = new NodeInstDealBean();
        dealBean.setEntityId(bean.getId());
        dealBean.setNodeId(bean.getCurNodeId());
        dealBean.setNodeInst(nodeInstService.findOne(bean.getCurNodeInstId()));
        dealBean.setDealer(user.getId());
        dealBean.setAuditOpinion(auditOpinion);

        WfDoneResult result = workflowService.readNodeInstAutoFinished(dealBean);

        OaDocFile obj = (OaDocFile)ret.getObject();
        if (WfDoneResult.流程办结.equals(result)) {
            obj.setStatus(FileStatusFlag.待归档);
            docFileService.persist(obj);

            // 保存登记簿
        	docProcessService.saveDocProcess(user, obj);
        }
    }

    /**
     * 暂存任务处理
     */
    public void completeNodeDoneTemp(OaDocFileEditBean bean, HttpServletRequest request) {
        // 保存公文信息
        ReturnBean ret = this.docFileService.updateDocFileInfo(bean, request);
        if (!ret.isSuccess()) {
            throw new RuntimeException(ret.getMessage());
        }

        // 暂存意见
        AuditOpinionEditBean opinionBean = new AuditOpinionEditBean();
        opinionBean.setFlowInstId(((OaDocFile)ret.getObject()).getFlowInstId());
        opinionBean.setNodeInstId(bean.getCurNodeInstId());
        opinionBean.setNodeId(bean.getCurNodeId());
        opinionBean.setApproverId(bean.getCurrentUser().getId());
        opinionBean.setApprResult(WfApprResult.未审);
        opinionBean.setApprOpinion(bean.getOpinion());
        if (bean.getOpinionDate() != null) {
            opinionBean.setApprTime(bean.getOpinionDate());
        }
        workflowService.saveNodeOpinion(opinionBean);
    }

    /**
     * 完结公文流程
     *
     * @param bean
     * @param request
     */
    public void completeFlowData(OaDocFileEditBean bean, HttpServletRequest request) {
        // 保存公文信息
        //OaDocFile obj = this.docFileService.saveDocFile(bean);
        docFileService.finishTheDoc(bean.getId());

        // 完成流程
        FlowInstCompleteBean completeBean = new FlowInstCompleteBean();
        completeBean.setEntityId(bean.getId());
        completeBean.setCurNodeId(bean.getCurNodeId());
        completeBean.setDealerId(bean.getCurrentUser().getId());
        completeBean.setOpinion(bean.getOpinion());
        if (bean.getOpinionDate() != null) {
            completeBean.setOpinionDate(bean.getOpinionDate());
        }
        completeBean.setFlowInst(flowInstService.getByFlowIdAndEntityId(bean.getFlowId(),  bean.getId()));
        workflowService.completeWorkflow(completeBean);

        // 写入登记簿
        //docProcessService.saveDocProcess(user, obj);

        // 最后若公文有关联的意见反馈未办结则将公文的审批意见写入反馈意见明细记录的content字段
        //if (StringUtils.isNotBlank(obj.getDocDepLetterId())) {
        //    workflowService.saveDocLetterOpinions(obj, obj.getDocDepLetterId());
        //}
    }


    /**
     * 删除公文流程
     *
     * @param fileId
     * @param request
     */
    public void deleteFlowData(String fileId, HttpServletRequest request) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();

        OaDocFile obj = docFileService.findOne(fileId);
        // 删除流程实例
        workflowService.delWorkflow(fileId);

        // 删除相关的部门函件
        List<DocDepLetter> letters = depLetterService.selectLettersByFileId(fileId);
        String letterIds = Tools.changeArrayToString(letters, "id", ",", false);
        depLetterService.deleteLetter(letterIds.split(","));

        // 删除公文信息，同时删除登记簿，阅知事项
        docFileService.delDocFile(fileId, true);

        // 删除全文检索
        this.docFileService.removeLuceneIndex(obj);

        logService.saveUserLog(user, SjType.普通操作, "删除公文信息：" + (obj != null ? obj.getTitle() : "") + "，文件ID：'" + fileId,LogStatus.成功, request);
    }

    /**
     * 新增阅知
     *
     * @param fileId
     * @param codealers
     * @param request
     */
    public void addReadNodeInst(String fileId, String title, String codealers, SysUser user, HttpServletRequest request) {
        // 增加阅知环节
        NodeInstReadBean readBean = new NodeInstReadBean();
        readBean.setEntityId(fileId);
        readBean.setDealerId(user.getId());
        readBean.setCoDealers(codealers);
        boolean result = workflowService.addReadNodeInst(readBean);

        logService.saveUserLog(user, SjType.普通操作, "发送阅知：" + title + "，文件ID：'" + fileId,LogStatus.成功, request);
    }

    /**
     * 处理阅知环节
     *
     * @param file
     * @param opinion
     * @param request
     */
    public void dealReadNodeInst(OaDocFile file, String opinion, HttpServletRequest request) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();

        // 处理阅知环节
        NodeInstReadDealBean dealBean = new NodeInstReadDealBean();
        dealBean.setEntityId(file.getId());
        dealBean.setDealerId(user.getId());
        dealBean.setOpinion(opinion);
        boolean result = workflowService.dealNodeInstRead(dealBean);

        logService.saveUserLog(user, SjType.普通操作, "处理阅知环节：" + file.getTitle() + "，文件ID：'" + file.getId(),LogStatus.成功, request);
    }

    /**
     * 退回环节
     *
     * @param file
     * @param nodeId
     * @param opinion
     * @param request
     */
    public void returnNodeInst(OaDocFile file, WfNodeInst nodeInst, String backNodeId, String opinion, Long userId,
                    HttpServletRequest request) {
        // 环节退回
        NodeInstReturnBean returnBean = new NodeInstReturnBean();
        //returnBean.setFlowInstId(file.getFlowInstId());   //这个写法适用于非子流程
        returnBean.setFlowInstId(nodeInst.getFlowInstId());
        returnBean.setEntityId(file.getId());
        returnBean.setNodeInst(nodeInst);
        returnBean.setDealerId(userId);
        returnBean.setOpinion(opinion);
        workflowService.returnNodeInst(returnBean, backNodeId);

    }

    /**
     * 取回环节
     *
     * @param file
     * @param nodeId
     * @param opinion
     * @param request
     */
    public String bringBackNodeInst(OaDocFile file, String nodeId, SysUser user, HttpServletRequest request) {
        // 取回当前环节
        NodeInstBringBackBean backBean = new NodeInstBringBackBean();
        backBean.setEntityId(file.getId());
        backBean.setCurNodeId(nodeId);
        backBean.setDealer(user);
        String res = workflowService.bringBackNodeInst(backBean);
        if (res == null) {
        	logService.saveUserLog(user, SjType.普通操作, "取回当前环节：" + file.getTitle() + "，文件ID：'" + file.getId() + "，环节id：" + nodeId,LogStatus.成功, request);
        	return null;
        } else {
        	return res;
        }
    }

    /**
     * 公文办结取回
     *
     * @param file
     * @param request
     */
    public void bringFinishedDocBack(OaDocFile file, HttpServletRequest request) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();

        // 修改公文信息
        file.setStatus(FileStatusFlag.在办);
        docFileService.persist(file);

        // 退回到首环节
        NodeInstReturnBean returnBean = new NodeInstReturnBean();
        returnBean.setEntityId(file.getId());
        returnBean.setDealerId(user.getId());
        returnBean.setOpinion("");
        returnBean.setFlowInstId(file.getFlowInstId());
        WfNode node = nodeService.getFirstTrueNode(file.getFlowId());
        workflowService.returnNodeInst(returnBean, node.getId());

        // 删除所有审批意见
        opinionService.delAuditOpinionsByEntityId(file.getId());
    }

    /**
     * 保存转办
     *
     * @param file
     * @param incepterDepts
     * @param content
     * @param limitDay
     * @param expiryDate
     * @param request
     */
    public void dispatchDocFile(OaDocFileDispatchBean bean, HttpServletRequest request) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        // 保存部门函件
        DepLetterInfo letterInfo = new DepLetterInfo();
        BeanUtils.copyProperties(bean, letterInfo);
        letterInfo.setTitle(bean.getDocFile().getTitle());
        letterInfo.setUrgencyLevel(bean.getDocFile().getUrgencyLevel());
        letterInfo.setFileId(bean.getDocFile().getId());
        letterInfo.setIncepterDept(bean.getIncepterDeptNames());
        letterInfo.setApprVer(bean.getDocFile().getVersion_());
        String letterId = depLetterService.saveDepLetter(letterInfo);
        letterInfo.setApprVer(bean.getDocFile().getVersion_());
        if (StringUtils.isNotBlank(letterId)) {
            logService.saveUserLog(user, SjType.普通操作, "保存公文转办函件，公文名称：" + bean.getDocFile().getTitle() + "，文件ID：'"
                            + bean.getDocFile().getId(),LogStatus.成功, request);
        }
    }

    /**
     * 公文保存公文传阅
     *
     * @param docFile
     * @param request
     */
    public void dispatchDocRead(OaDocFile docFile, String readmans, HttpServletRequest request) {
        if (docFile == null) {
            return;
        }

        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();

        DocReadEditBean readBean = new DocReadEditBean();
        readBean.setDocTitle(docFile.getTitle());
        readBean.setDocFileId(docFile.getId());
        readBean.setToSigned(YesNo.否);// 不保存为来文签收
        readBean.setDocCate(docFile.getDocCate());
        readBean.setSendMan(user.getId());
        readBean.setSendManName(user.getUserName());
        readBean.setSendDeptId(user.getDepartment().getId());
        readBean.setSendTime(new Date());
        readBean.setStatus(DocReadStatus.发送);
        readBean.setReadMan(readmans);
        readBean.setDocNumber(docFile.getDocCode());
        readBean.setRemark(docFile.getRemark());
        readBean.setSendUnit(docFile.getSendUnit());
        Long readId = docReadService.saveDocread(readBean);

        if (readId != null) {
            logService.saveUserLog(user, SjType.普通操作, "保存公文转公文传阅，公文名称：" + docFile.getTitle() + "，文件ID：'" + docFile.getId(),LogStatus.成功, request);
        }
    }

    /**
     * 部门函件转收文
     */
    public ReturnBean saveLetterToDocFile(OaDocFileFromLetterBean bean, HttpServletRequest request) {
        // 获取函件信息
        DocDepLetter letter = depLetterService.findOne(bean.getLetterId());
        if (letter == null) {
            throw new RuntimeException("操作失败！无法获取部门函件信息。");
        }
        DocDepLetterReceiver receiver = depLetterReceiverService.findOne(bean.getLetterReceiverId());
        // if (receiver == null) {
        // throw new RuntimeException("操作失败！无法获取部门函件接收信息。");
        // }

        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();

        OaDocFileEditBean docBean = new OaDocFileEditBean();
        docBean.setTitle(letter.getTitle());
        docBean.setFileType(WfBusinessType.收文);// 默认收文
        docBean.setLimitDay(letter.getLimitDay());
        docBean.setExpiryDate(letter.getExpiryDate());
        docBean.setCreatorId(user.getId());
        docBean.setCreateDate(new Date());
        docBean.setStatus(FileStatusFlag.暂存);
        docBean.setReceiveDate(letter.getCreateDate());
        docBean.setSendUnit(letter.getCreatorDep().getDeptName());
        docBean.setDocDepLetterId(receiver == null ? letter.getId() : receiver.getId());// 可以存letterId也可以receiverId
        docBean.setLetterStatus(FeedbackFlag.未反馈);
        docBean.setRemark((receiver == null ? letter.getCreatorDep().getDeptName() : receiver.getReceiverDept()
                        .getDeptName()) + "：" + letter.getContent());
        docBean.setUrgencyLevel(letter.getUrgencyLevel());
        // 暂存公文
        ReturnBean ret = this.docFileService.saveDocFile(docBean);
        if (!ret.isSuccess()) {
            logService.saveUserLog(user, SjType.普通操作, "保存部门函件转收文，函件名称：" + letter.getTitle(),LogStatus.成功, request);
        }

        return ret;
    }

    private Map<String, Object> toMap(String[] propertyName, Object[] list) {
        Map<String, Object> map = new HashedMap();
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < propertyName.length; j++) {
                map.put(propertyName[j], list[j]);
            }
        }
        return map;
    }

    // 查询 暂存中的收发文列表
    public List selectTmpsavedDocList(String title, Long userId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT new map(a.id as id, a.title as title, a.docCode as docCode, a.fileType as fileType, a.createDate as createDate) ");
        sql.append(" FROM " + OaDocFile.class.getName() + " as a ");
        sql.append(" WHERE a.creatorId = ").append(userId);
        sql.append(" and a.status = '").append(FileStatusFlag.暂存).append("' ");
        if (StringUtils.isNotBlank(title)) {
            sql.append(" and a.title like '%").append(title).append("%' ");
        }
        sql.append(" order by a.createDate desc ");
        List<Map> list = em.createQuery(sql.toString()).getResultList();

        for (Map item : list) {
            item.put("createDateShow", Tools.DATE_FORMAT.format(item.get("createDate")));
        }

        return list;

    }


    /**查询经办公文（当前用户经手的文）*/
    public Page selectMylinkDocs(OaDocFileQueryBean bean, Integer page, Integer rows, boolean showDealingOnly,String sort,String order) {
        StringBuilder sqlBuilder = new StringBuilder(" select ");
        if(StringUtils.isNotBlank(sort)){
        	sort = sort.replace("Show", "");
        	if ("title".equals(sort)) {
        		sqlBuilder.append(" a.entityId,max(a.id),a.title");
        	}else if ("createDate".equals(sort)) {
        		sqlBuilder.append(" a.entityId,max(a.id),b.createDate");
        	}else if ("expiryDate".equals(sort)) {
        		sqlBuilder.append(" a.entityId,max(a.id),b.expiryDate");
            }else if ("restTime".equals(sort)) {
            	sqlBuilder.append(" a.entityId,max(a.id),b.expiryDate");
	        }
        }else{
        	sqlBuilder.append(" a.entityId,min(a.id),b.createDate");
        }
        sqlBuilder.append(" from WfFlowInst a, OaDocFile b, WfNode c ");
        sqlBuilder.append(" where a.entityId = b.id and a.curNodeId = c.id ");
        if (showDealingOnly) {
        	sqlBuilder.append(" and a.instStatus='0' ");
        }
        if (bean.getDealerId() != null) {
        	sqlBuilder.append(" and a.id in (select flowInstId from WfNodeInst where dealerId = ?1  ");
        	if (StringUtils.isNotBlank(bean.getQueryReadFlag())) {
            	if("1".equals(bean.getQueryReadFlag()))	{
            		sqlBuilder.append(" and curNodeId = 'N888' ");
            	}else{
            		sqlBuilder.append(" and curNodeId <> 'N888' ");
            	}
            }

        	if (StringUtils.isNotBlank(bean.getDealerDeptId())) {
            		sqlBuilder.append(" and dealer.department.id = ?13 ");
            }
        	sqlBuilder.append(")");
        } else {
        	//排除子流程
        	sqlBuilder.append(" and (a.parentFlowInstId is null or a.parentFlowInstId = '') ");

        }
        // 以下是动态查询
        if (StringUtils.isNotBlank(bean.getTitle())) {
            sqlBuilder.append(" and a.title like ?2 ");
        }
        if (StringUtils.isNotBlank(bean.getBusinessType())) {
            sqlBuilder.append(" and a.businessType like ?3 ");
        }
        if (bean.getCreateDateBegin() != null) {
            sqlBuilder.append(" and b.createDate >= ?4 ");
        }
        if (bean.getCreateDateEnd() != null) {
            sqlBuilder.append(" and b.createDate <= ?5 ");
        }
        if (bean.getExpiryDateBegin() != null) {
            sqlBuilder.append(" and b.expiryDate >= ?6 ");
        }
        if (bean.getExpiryDateEnd() != null) {
            sqlBuilder.append(" and b.expiryDate <= ?7 ");
        }
        if (StringUtils.isNotBlank(bean.getSendUnit())) {
            sqlBuilder.append(" and b.sendUnit like ?8 ");
        }
        if (StringUtils.isNotBlank(bean.getInstStatus())) {
            sqlBuilder.append(" and a.instStatus = ?9 ");
        }
        if (StringUtils.isNotBlank(bean.getDeptName())) {
        	sqlBuilder.append(" and b.createrDept.deptName like ?10 ");
        }
        if (StringUtils.isNotBlank(bean.getSuperviseFlag())) {
        	if("1".equals(bean.getSuperviseFlag())){
        		sqlBuilder.append(" and b.superviseFlag = ?11 ");
        	}else{
        		sqlBuilder.append(" and (b.superviseFlag <> ?11 or b.superviseFlag is null) ");
        	}

        }
        if (StringUtils.isNotBlank(bean.getCreatorName())) {
        	sqlBuilder.append(" and b.creatorName like ?12 ");
        }

        //过滤重复的公文数据（暂时这样做，只适合mysql）
        
        if(StringUtils.isNotBlank(sort)){
        	sort = sort.replace("Show", "");
        	if ("title".equals(sort)) {
        		sort = "a.title";
            }else if ("createDate".equals(sort)) {
        		sort = "b.createDate";
            }else if ("expiryDate".equals(sort)) {
        		sort = "b.expiryDate";
            }else if ("restTime".equals(sort)) {
	        	sort = "b.expiryDate";
	        }
        	sqlBuilder.append(" group by a.entityId,"+ sort);
        	sqlBuilder.append(" order by " + sort + " " + order);
        }else{
        	sqlBuilder.append(" group by a.entityId,b.createDate ");
        	sqlBuilder.append(" order by b.createDate desc ");
        }
        
        Query query = em.createQuery(sqlBuilder.toString());

        if (bean.getDealerId() != null) {
        	query.setParameter(1, bean.getDealerId());
        }

        if (StringUtils.isNotBlank(bean.getTitle())) {
            query.setParameter(2, "%" + bean.getTitle() + "%");
        }
        if (StringUtils.isNotBlank(bean.getBusinessType())) {
            query.setParameter(3, "%" + bean.getBusinessType() + "%");
        }
        if (bean.getCreateDateBegin() != null) {
            query.setParameter(4, bean.getCreateDateBegin());
        }
        if (bean.getCreateDateEnd() != null) {
            query.setParameter(5, bean.getCreateDateEnd());
        }
        if (bean.getExpiryDateBegin() != null) {
        	query.setParameter(6, bean.getExpiryDateBegin());
        }
        if (bean.getExpiryDateEnd() != null) {
        	query.setParameter(7, bean.getExpiryDateEnd());
        }
        if (StringUtils.isNotBlank(bean.getSendUnit())) {
        	query.setParameter(8, "%" + bean.getSendUnit() + "%");
        }
        if (StringUtils.isNotBlank(bean.getInstStatus())) {
        	if("0".equals(bean.getInstStatus())){
        		query.setParameter(9, WfInstStatus.在办);
        	}else{
        		query.setParameter(9, WfInstStatus.办结);
        	}

        }
        if (StringUtils.isNotBlank(bean.getDeptName())) {
        	query.setParameter(10, "%" + bean.getDeptName() + "%");
        }
        if (StringUtils.isNotBlank(bean.getSuperviseFlag())) {
        	query.setParameter(11, "1");
        }
        if (StringUtils.isNotBlank(bean.getCreatorName())) {
        	query.setParameter(12, "%" + bean.getCreatorName() + "%");
        }
        if (StringUtils.isNotBlank(bean.getDealerDeptId())) {
        	query.setParameter(13, Long.parseLong(bean.getDealerDeptId()));
        }

        List totalList = query.getResultList();
        int total = 0;
        if (totalList != null) {
            total = totalList.size();
        }
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        List<InstCommonShowBean> dataList = new ArrayList<InstCommonShowBean>();
        List list = query.getResultList();
        String[] names = { "entityId", "flowInstId"};
        for (Object o : list) {
            Map<String, Object> map = Tools.changeListToMap(names, (Object[]) o);
            InstCommonShowBean b = new InstCommonShowBean();
            try {
                PropertyUtils.copyProperties(b, map);
                WfFlowInst wfi = this.wfFlowInstRepository.findOne(b.getFlowInstId());
                OaDocFile file = this.docFileService.findOne(b.getEntityId());
                if(wfi!=null && file!=null){
                	WfNode wn = this.wfNodeRepository.findOne(wfi.getCurNodeId());
                	b.setTitle(wfi.getTitle());
                	b.setCreateDate(file.getCreateDate());
                	b.setBusinessType(wfi.getBusinessType());
                	b.setUrgencyLevel(wfi.getUrgencyLevel());
                	b.setDocCode(file.getDocCode());
                	b.setCreatorName(file.getCreatorName());
                	b.setExpiryDate(file.getExpiryDate());
                	b.setSendUnit(file.getSendUnit());
                	b.setCurNodeId(wfi.getCurNodeId());
                	b.setNodeName(wn.getNodeName());
                	b.setInstStatus(wfi.getInstStatus());
                	b.setDeptName(file.getCreaterDept().getDeptName());
                }
                dataList.add(b);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        Page p = new Page();
        p.setRows(dataList);
        p.setTotal(Long.valueOf(total));
        return p;
    }

    /**查询本部门公文（在局内会签勾选了本处室共享）*/
    public Page selectMyDeptLinkDocs(OaDocFileQueryBean bean, Integer page, Integer rows) {
        StringBuilder sqlBuilder = new StringBuilder(" select ");
        sqlBuilder.append(" a.entityId, a.title, a.businessType, a.urgencyLevel, b.docCode, b.creatorName, b.createDate, b.expiryDate, b.sendUnit, a.curNodeId, c.nodeName, a.instStatus, b.createrDept.deptName ");
        sqlBuilder.append(" from WfFlowInst a, OaDocFile b, WfNode c ");
        sqlBuilder.append(" where a.entityId = b.id and a.curNodeId = c.id ");
    	sqlBuilder.append(" and a.id in (select w.flowInstId from WfNodeInst w, OaDocDeptFile o where w.id = o.nodeInstId and o.deptId = ?1  ");
    	if (StringUtils.isNotBlank(bean.getQueryReadFlag())) {
        	if("1".equals(bean.getQueryReadFlag()))	{
        		sqlBuilder.append(" and w.curNodeId = 'N888' ");
        	}else{
        		sqlBuilder.append(" and w.curNodeId <> 'N888' ");
        	}
        }
    	sqlBuilder.append(")");
    	
        // 以下是动态查询
        if (StringUtils.isNotBlank(bean.getTitle())) {
            sqlBuilder.append(" and a.title like ?2 ");
        }
        if (StringUtils.isNotBlank(bean.getBusinessType())) {
            sqlBuilder.append(" and a.businessType like ?3 ");
        }
        if (bean.getCreateDateBegin() != null) {
            sqlBuilder.append(" and b.createDate >= ?4 ");
        }
        if (bean.getCreateDateEnd() != null) {
            sqlBuilder.append(" and b.createDate <= ?5 ");
        }
        if (bean.getExpiryDateBegin() != null) {
            sqlBuilder.append(" and b.expiryDate >= ?6 ");
        }
        if (bean.getExpiryDateEnd() != null) {
            sqlBuilder.append(" and b.expiryDate <= ?7 ");
        }
        if (StringUtils.isNotBlank(bean.getSendUnit())) {
            sqlBuilder.append(" and b.sendUnit like ?8 ");
        }
        if (StringUtils.isNotBlank(bean.getInstStatus())) {
            sqlBuilder.append(" and a.instStatus = ?9 ");
        }
        if (StringUtils.isNotBlank(bean.getDeptName())) {
        	sqlBuilder.append(" and b.createrDept.deptName like ?10 ");
        }
        if (StringUtils.isNotBlank(bean.getSuperviseFlag())) {
        	if("1".equals(bean.getSuperviseFlag())){
        		sqlBuilder.append(" and b.superviseFlag = ?11 ");
        	}else{
        		sqlBuilder.append(" and (b.superviseFlag <> ?11 or b.superviseFlag is null) ");
        	}

        }
        if (StringUtils.isNotBlank(bean.getCreatorName())) {
        	sqlBuilder.append(" and b.creatorName like ?12 ");
        }

        sqlBuilder.append(" order by b.createDate desc ");
        Query query = em.createQuery(sqlBuilder.toString());

        if (StringUtils.isNotBlank(bean.getDealerDeptId())) {
        	query.setParameter(1, Long.parseLong(bean.getDealerDeptId()));
        }

        if (StringUtils.isNotBlank(bean.getTitle())) {
            query.setParameter(2, "%" + bean.getTitle() + "%");
        }
        if (StringUtils.isNotBlank(bean.getBusinessType())) {
            query.setParameter(3, "%" + bean.getBusinessType() + "%");
        }
        if (bean.getCreateDateBegin() != null) {
            query.setParameter(4, bean.getCreateDateBegin());
        }
        if (bean.getCreateDateEnd() != null) {
            query.setParameter(5, bean.getCreateDateEnd());
        }
        if (bean.getExpiryDateBegin() != null) {
        	query.setParameter(6, bean.getExpiryDateBegin());
        }
        if (bean.getExpiryDateEnd() != null) {
        	query.setParameter(7, bean.getExpiryDateEnd());
        }
        if (StringUtils.isNotBlank(bean.getSendUnit())) {
        	query.setParameter(8, "%" + bean.getSendUnit() + "%");
        }
        if (StringUtils.isNotBlank(bean.getInstStatus())) {
        	if("0".equals(bean.getInstStatus())){
        		query.setParameter(9, WfInstStatus.在办);
        	}else{
        		query.setParameter(9, WfInstStatus.办结);
        	}

        }
        if (StringUtils.isNotBlank(bean.getDeptName())) {
        	query.setParameter(10, "%" + bean.getDeptName() + "%");
        }
        if (StringUtils.isNotBlank(bean.getSuperviseFlag())) {
        	query.setParameter(11, "1");
        }
        if (StringUtils.isNotBlank(bean.getCreatorName())) {
        	query.setParameter(12, "%" + bean.getCreatorName() + "%");
        }

        List totalList = query.getResultList();
        int total = 0;
        if (totalList != null) {
            total = totalList.size();
        }
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        List<InstCommonShowBean> dataList = new ArrayList<InstCommonShowBean>();
        List list = query.getResultList();
        String[] names = { "entityId", "title", "businessType", "urgencyLevel", "docCode", "creatorName", "createDate", "expiryDate", "sendUnit", "curNodeId", "nodeName", "instStatus", "deptName" };
        for (Object o : list) {
            Map<String, Object> map = Tools.changeListToMap(names, (Object[]) o);
            InstCommonShowBean b = new InstCommonShowBean();
            try {
                PropertyUtils.copyProperties(b, map);
                dataList.add(b);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        Page p = new Page();
        p.setRows(dataList);
        p.setTotal(Long.valueOf(total));
        return p;
    }
    
    /** 公文编辑（查询所有文）*/
    public Page selectDocModifyList(OaDocFileQueryBean bean, Integer page, Integer rows, String sort,String order) {
        StringBuilder sqlBuilder = new StringBuilder(" select ");
        sqlBuilder.append(" a.entityId, a.title, a.businessType, a.urgencyLevel, b.docCode, b.creatorName, b.createDate, b.expiryDate, b.sendUnit, a.curNodeId, c.nodeName, a.instStatus, b.createrDept.deptName ");
        sqlBuilder.append(" from WfFlowInst a, OaDocFile b, WfNode c ");
        sqlBuilder.append(" where a.entityId = b.id and a.curNodeId = c.id ");
        //排除子流程
    	sqlBuilder.append(" and (a.parentFlowInstId is null or a.parentFlowInstId = '') ");
       
        // 以下是动态查询
        if (StringUtils.isNotBlank(bean.getTitle())) {
            sqlBuilder.append(" and a.title like ?1 ");
        }
        if (StringUtils.isNotBlank(bean.getDocCode())) {
            sqlBuilder.append(" and b.docCode like ?2 ");
        }
        if (bean.getCreateDateBegin() != null) {
            sqlBuilder.append(" and b.createDate >= ?3 ");
        }
        if (bean.getCreateDateEnd() != null) {
            sqlBuilder.append(" and b.createDate <= ?4 ");
        }
        if (StringUtils.isNotBlank(bean.getSendUnit())) {
            sqlBuilder.append(" and b.sendUnit like ?5 ");
        }
        if (StringUtils.isNotBlank(bean.getFileCode())) {
        	sqlBuilder.append(" and b.sncode like ?6 ");
        }
        if (StringUtils.isNotBlank(bean.getInstStatus())) {
            sqlBuilder.append(" and a.instStatus = ?7 ");
        }
        
        if(StringUtils.isNotBlank(sort)){
        	sort = sort.replace("Show", "");
        	if ("title".equals(sort)) {
        		sort = "a.title";
            }else if ("createDate".equals(sort)) {
        		sort = "b.createDate";
            }
        	
        	sqlBuilder.append(" order by " + sort + " " + order);
        }else{
        	sqlBuilder.append(" order by b.createDate desc ");
        }
        
        Query query = em.createQuery(sqlBuilder.toString());

        if (StringUtils.isNotBlank(bean.getTitle())) {
            query.setParameter(1, "%" + bean.getTitle() + "%");
        }
        if (StringUtils.isNotBlank(bean.getDocCode())) {
            query.setParameter(2, "%" + bean.getDocCode() + "%");
        }
        if (bean.getCreateDateBegin() != null) {
            query.setParameter(3, bean.getCreateDateBegin());
        }
        if (bean.getCreateDateEnd() != null) {
            query.setParameter(4, bean.getCreateDateEnd());
        }
        if (StringUtils.isNotBlank(bean.getSendUnit())) {
        	query.setParameter(5, "%" + bean.getSendUnit() + "%");
        }
        if (StringUtils.isNotBlank(bean.getFileCode())) {
        	query.setParameter(6, "%" + bean.getFileCode() + "%");
        }
        if (StringUtils.isNotBlank(bean.getInstStatus())) {
        	if("0".equals(bean.getInstStatus())){
        		query.setParameter(7, WfInstStatus.在办);
        	}else{
        		query.setParameter(7, WfInstStatus.办结);
        	}
        	
        }
        
        List totalList = query.getResultList();
        int total = 0;
        if (totalList != null) {
            total = totalList.size();
        }
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        List<InstCommonShowBean> dataList = new ArrayList<InstCommonShowBean>();
        List list = query.getResultList();
        String[] names = { "entityId", "title", "businessType", "urgencyLevel", "docCode", "creatorName", "createDate", "expiryDate", "sendUnit", "curNodeId", "nodeName", "instStatus", "deptName" };
        for (Object o : list) {
            Map<String, Object> map = Tools.changeListToMap(names, (Object[]) o);
            InstCommonShowBean b = new InstCommonShowBean();
            try {
                PropertyUtils.copyProperties(b, map);
                dataList.add(b);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        Page p = new Page();
        p.setRows(dataList);
        p.setTotal(Long.valueOf(total));
        return p;
    }

    /** 
     * @Title: saveOaDocNodeExpireDate 
     * @Description: 保存环节限办时间，收文在来文呈送的时候初始化各个环节的限办时间
     * @param nodeIds
     * @param nodeExpireDates
     * @param fileId
     * @return: void
     */
    public void saveOaDocNodeExpireDate(String nodeIds,String nodeExpireMinutes,String fileId){
    	
    	if(!Tools.isEmptyString(nodeIds)){
    		String[] nodeIdsArr = nodeIds.split(",");
        	String[] nodeExpireMinutesArr = nodeExpireMinutes.split(",");
        	if(nodeIdsArr.length == nodeExpireMinutesArr.length){
        		for(int i=0;i<nodeIdsArr.length;i++){
        			if(!"0".equals(nodeExpireMinutesArr[i])){
        				OaDocNodeExpireDate obj = docNodeExpireDateRepository.findByFileIdAndNodeId(fileId, nodeIdsArr[i]);
        				if(null == obj){
        					obj = new OaDocNodeExpireDate();
        				}
        				obj.setFileId(fileId);
        				obj.setNodeId(nodeIdsArr[i]);
        				obj.setNodeExpireMinute(Long.parseLong(nodeExpireMinutesArr[i]));
        				docNodeExpireDateRepository.save(obj);
        			}
        		}
        	}
    	}
    	
    }
    
    /** 
     * @Title: getNodeExpireMinute 
     * @Description: 计算环节限办时间-分钟数
     * @param list
     * @return
     * @return: Long
     */
    public Long getNodeExpireMinute(List<SysNodeExpireDate> list){
    	Long minute = null;
    	SysNodeExpireDate obj = null;
    	if (list != null && list.size() > 0) {
    		obj = list.get(0);
    	}
    	if(null!=obj){
    		if(DeadlineType.特急.equals(obj.getDeadlineType())){
        		minute = Long.parseLong(obj.getExpireMinute());
        	}else{
        		minute = Long.parseLong(obj.getExpireDay())*24*60 + Long.parseLong(obj.getExpireHour())*60;
        	}
    	}
    	
		return minute;
    	
    }
    
    /** 
     * @Title: getEarlyWarnMinute 
     * @Description: 计算环节预警时间-分钟数
     * @param list
     * @return
     * @return: Long
     */
    public Long getEarlyWarnMinute(List<SysNodeExpireDate> list){
    	Long minute = null;
    	SysNodeExpireDate obj = null;
    	if (list != null && list.size() > 0) {
    		obj = list.get(0);
    	}
    	if(null!=obj){
    		if(DeadlineType.特急.equals(obj.getDeadlineType()) && StringUtils.isNotEmpty(obj.getEarlyWarnMinute())){
        		minute = Long.parseLong(obj.getEarlyWarnMinute());
        	}else{
        		if(StringUtils.isNotEmpty(obj.getEarlyWarnDay()) && StringUtils.isNotEmpty(obj.getEarlyWarnHour())){
        			minute = Long.parseLong(obj.getEarlyWarnDay())*24*60 + Long.parseLong(obj.getEarlyWarnHour())*60;
        		}
        	}
    	}
    	
		return minute;
    	
    }
    
    /**查询转办公文（当前用户转办的文）*/
    public Page selectTurnDocs(OaDocFileQueryBean bean, Integer page, Integer rows, String sort,String order) {
        StringBuilder sqlBuilder = new StringBuilder(" select ");
        sqlBuilder.append(" a.entityId, a.title, a.businessType, a.urgencyLevel, b.docCode, b.sncode, b.creatorName, b.createDate, b.expiryDate, b.sendUnit, a.curNodeId, c.nodeName, a.instStatus, b.createrDept.deptName ");
        sqlBuilder.append(" from WfFlowInst a, OaDocFile b, WfNode c ");
        sqlBuilder.append(" where a.entityId = b.id and a.curNodeId = c.id and a.parentEntityId is not null and a.parentEntityId <> '' ");

        if (bean.getDealerId() != null) {
        	sqlBuilder.append(" and a.creatorId = ?1 ");
        	
        }
        
        //排除子流程
    	sqlBuilder.append(" and (a.parentFlowInstId is null or a.parentFlowInstId = '') ");
    	
        // 以下是动态查询
        if (StringUtils.isNotBlank(bean.getTitle())) {
            sqlBuilder.append(" and a.title like ?2 ");
        }
        if (StringUtils.isNotBlank(bean.getSncode())) {
        	sqlBuilder.append(" and a.sncode like ?3 ");
        }
        if (StringUtils.isNotBlank(bean.getBusinessType())) {
            sqlBuilder.append(" and a.businessType like ?4 ");
        }
        if (bean.getCreateDateBegin() != null) {
            sqlBuilder.append(" and b.createDate >= ?5 ");
        }
        if (bean.getCreateDateEnd() != null) {
            sqlBuilder.append(" and b.createDate <= ?6 ");
        }
        if (bean.getExpiryDateBegin() != null) {
            sqlBuilder.append(" and b.expiryDate >= ?7 ");
        }
        if (bean.getExpiryDateEnd() != null) {
            sqlBuilder.append(" and b.expiryDate <= ?8 ");
        }
        if (StringUtils.isNotBlank(bean.getInstStatus())) {
            sqlBuilder.append(" and a.instStatus = ?9 ");
        }

        if(StringUtils.isNotBlank(sort)){
        	sort = sort.replace("Show", "");
        	if ("title".equals(sort)) {
        		sort = "a.title";
            }else if ("createDate".equals(sort)) {
        		sort = "b.createDate";
            }else if ("expiryDate".equals(sort)) {
        		sort = "b.expiryDate";
            }else if ("restTime".equals(sort)) {
	        	sort = "b.expiryDate";
	        }
        	
        	sqlBuilder.append(" order by " + sort + " " + order);
        }else{
        	sqlBuilder.append(" order by b.createDate desc ");
        }
        
        Query query = em.createQuery(sqlBuilder.toString());

        if (bean.getDealerId() != null) {
        	query.setParameter(1, bean.getDealerId());
        }

        if (StringUtils.isNotBlank(bean.getTitle())) {
            query.setParameter(2, "%" + bean.getTitle() + "%");
        }
        if (StringUtils.isNotBlank(bean.getSncode())) {
        	query.setParameter(3, "%" + bean.getSncode() + "%");
        }
        if (StringUtils.isNotBlank(bean.getBusinessType())) {
            query.setParameter(4, "%" + bean.getBusinessType() + "%");
        }
        if (bean.getCreateDateBegin() != null) {
            query.setParameter(5, bean.getCreateDateBegin());
        }
        if (bean.getCreateDateEnd() != null) {
            query.setParameter(6, bean.getCreateDateEnd());
        }
        if (bean.getExpiryDateBegin() != null) {
        	query.setParameter(7, bean.getExpiryDateBegin());
        }
        if (bean.getExpiryDateEnd() != null) {
        	query.setParameter(8, bean.getExpiryDateEnd());
        }
        if (StringUtils.isNotBlank(bean.getInstStatus())) {
    		query.setParameter(9, new WfInstStatusConverter().convertToEntityAttribute(bean.getInstStatus()));
        }

        List totalList = query.getResultList();
        int total = 0;
        if (totalList != null) {
            total = totalList.size();
        }
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        List<InstCommonShowBean> dataList = new ArrayList<InstCommonShowBean>();
        List list = query.getResultList();
        String[] names = { "entityId", "title", "businessType", "urgencyLevel", "docCode", "sncode", "creatorName", "createDate", "expiryDate", "sendUnit", "curNodeId", "nodeName", "instStatus", "deptName" };
        for (Object o : list) {
            Map<String, Object> map = Tools.changeListToMap(names, (Object[]) o);
            InstCommonShowBean b = new InstCommonShowBean();
            try {
                PropertyUtils.copyProperties(b, map);
                dataList.add(b);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        Page p = new Page();
        p.setRows(dataList);
        p.setTotal(Long.valueOf(total));
        return p;
    }
}
