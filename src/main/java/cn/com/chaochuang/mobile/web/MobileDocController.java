package cn.com.chaochuang.mobile.web;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.AttachHelper;
import cn.com.chaochuang.common.util.EncryptUtils;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.WordUtil;
import cn.com.chaochuang.doc.docread.bean.DocreadRecShowBean;
import cn.com.chaochuang.doc.docread.domain.DocReadReceives;
import cn.com.chaochuang.doc.docread.service.DocReadReceivesService;
import cn.com.chaochuang.doc.docread.service.DocReadService;
import cn.com.chaochuang.doc.event.bean.FileShowBean;
import cn.com.chaochuang.doc.event.bean.OaDocFileEditBean;
import cn.com.chaochuang.doc.event.bean.OaDocFileQueryBean;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.service.DocEventService;
import cn.com.chaochuang.doc.event.service.OaDocFileService;
import cn.com.chaochuang.mobile.bean.*;
import cn.com.chaochuang.mobile.util.AesTool;
import cn.com.chaochuang.workflow.def.bean.NodeCommonBean;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.def.domain.WfNodeDept;
import cn.com.chaochuang.workflow.def.domain.WfNodeLine;
import cn.com.chaochuang.workflow.def.service.WfNodeDeptService;
import cn.com.chaochuang.workflow.def.service.WfNodeLineService;
import cn.com.chaochuang.workflow.def.service.WfNodeService;
import cn.com.chaochuang.workflow.event.bean.TaskShowBean;
import cn.com.chaochuang.workflow.event.service.WorkflowService;
import cn.com.chaochuang.workflow.inst.domain.WfAuditOpinion;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfAuditOpinionService;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfDealType;
import cn.com.chaochuang.workflow.reference.WfInstStatus;
import cn.com.chaochuang.workflow.util.WorkflowUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.CipherInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author hzr 2017.7.18 移动端公文接口
 */

@Controller
@RequestMapping(value = "/mobile/doc")
public class MobileDocController extends MobileController {

    @Autowired
    private ConversionService         conversionService;

    @Autowired
    private DocEventService           docEventService;

    @Autowired
    protected OaDocFileService        docFileService;

    @Autowired
    private WfNodeLineService         nodeLineService;

    @Autowired
    private WfNodeDeptService         nodeDeptService;

    @Autowired
    private WfAuditOpinionService     auditOpinionService;

    @Autowired
    private WfNodeService             nodeService;

    @Autowired
    private WfNodeInstService         nodeInstService;

    @Autowired
    private SysAttachService          attachService;

    @Autowired
    private WorkflowService           workflowService;

    @Autowired
    private DocReadService            docReadService;

    @Autowired
    private DocReadReceivesService    readReceiveService;

    @Value(value = "${upload.rootpath}")
    private String                    wordImageTempPath;

    /** 取得下一环节（可多个） */
    @RequestMapping(value = { "/nextnodes.mo" })
    @ResponseBody
    public AppResponseInfo getNextNodes(HttpServletRequest request, Boolean aesFlag_) {
        String flowId = request.getParameter("flowId");
        String nodeId = request.getParameter("nodeId");
        String curNodeInstId = request.getParameter("tid");
        String startflow = request.getParameter("startflow");
        List<NodeCommonBean> list = new ArrayList();
        NodeCommonBean bean = workflowService.getNextNodeDataBeforeDealing(flowId, nodeId, curNodeInstId);
        if (bean.getNextOpenSel()) {
        	if (YesNo.是.getKey().equals(startflow)) {
            	nodeId = this.nodeService.getFirstTrueNode(flowId).getId();
            }
            // 需要选择环节和办理人
            List<WfNode> nodes = workflowService.selectBranchNodeByFlowIdAndNodeId(flowId, nodeId);
            for (WfNode nd : nodes) {
                NodeCommonBean ncb = new NodeCommonBean();
                ncb.setNodeId(nd.getId());
                ncb.setNodeName(nd.getNodeName());
                list.add(ncb);
            }
        } else {
            // 不需要选择环节和办理人，直接往下提交
            list.add(bean);
        }

        return AesTool.responseData(list, aesFlag_);
    }

    /** 取得该环节可办理的部门或人员列表（来源于流程部门配置表） */
    @RequestMapping(value = { "/task/getdealers.mo" })
    @ResponseBody
    public AppResponseInfo getDealersJson(HttpServletRequest request, Boolean aesFlag_) {
        String flowId = request.getParameter("flowId");
        String nodeId = request.getParameter("nodeId");
        if (StringUtils.isBlank(flowId) || StringUtils.isBlank(nodeId)) {
            return AesTool.responseData("0", "流程ID和环节ID不能为空！", null, aesFlag_);
        }

        List dataList = new ArrayList();
        WfNode nodeDef = nodeService.findOne(nodeId);
        String revs = nodeDef.getReceiverIds();
        AppReturnBean bean;
        if (StringUtils.isNotBlank(revs)) {
            // 环节配置里有指定办理人的
            String[] rlist = revs.split(",");
            for (String re : rlist) {
                SysUser u = userService.findOne(new Long(re));
                if (u != null) {
                    dataList.add(new AppUserInfo(u.getId(), u.getUserName(), u.getDepartment().getId(), u
                                    .getDepartment().getDeptName()));
                }
            }
            bean = new AppReturnBean("1", dataList);
        } else {
            List<WfNodeDept> list = nodeDeptService.findByFlowIdAndNodeId(flowId, nodeId);
            if (list == null || list.isEmpty()) {
                // 没有定义办理部门，则返回该单位部门根节点
                SysDepartment dept = deptService.findOne(SysDepartment.ROOT_DEPT);
                dataList.add(deptToShowBean(dept));
                bean = new AppReturnBean("0", dataList);
            } else if (list.size() > 1) {
                for (WfNodeDept nd : list) {
                    SysDepartment dept = deptService.findOne(nd.getReceiveDepId());
                    dataList.add(deptToShowBean(dept));
                }
                bean = new AppReturnBean("0", dataList);
            } else {
                // 只有一个部门的情况
                Long returnDeptId = list.get(0).getReceiveDepId();
                List<SysDepartment> dlist = deptService.getSubDeps(returnDeptId);
                if (dlist != null && dlist.size() > 0) {
                    // 如果该部门有子部门，那就只返回该部门节点
                    SysDepartment dept = deptService.findOne(returnDeptId);
                    dataList.add(deptToShowBean(dept));
                    bean = new AppReturnBean("0", dataList);
                } else {
                    // 没有子部门的情况下，返回该部门的人员列表
                    List<SysUser> ulist = userService.findBydetpId(returnDeptId);
                    for (SysUser u : ulist) {
                        dataList.add(new AppUserInfo(u.getId(), u.getUserName(), u.getDepartment().getId(), u
                                        .getDepartment().getDeptName()));
                    }
                    bean = new AppReturnBean("1", dataList);
                }
            }
        }

        return AesTool.responseData(bean, aesFlag_);

    }

    /** 公文待办事项列表 */
    @RequestMapping(value = { "/tasklist.mo" })
    @ResponseBody
    public AppResponseInfo taskListjson(HttpServletRequest request, Boolean aesFlag_) {
        return taskOrReadListJson("task", request, aesFlag_);
    }

    /** 公文待阅事项列表 */
    @RequestMapping(value = { "/readlist.mo" })
    @ResponseBody
    public AppResponseInfo readListjson(HttpServletRequest request, Boolean aesFlag_) {
        return taskOrReadListJson("read", request, aesFlag_);
    }

    private AppResponseInfo taskOrReadListJson(String flag, HttpServletRequest request, Boolean aesFlag_) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String uid = request.getParameter("uid");

        // 查询条件（可选）
        String title = request.getParameter("title");

        if (!StringUtils.isNumeric(page) || !StringUtils.isNumeric(rows)) {
            return AesTool.responseData("0", "页码错误！", null, aesFlag_);
        }

        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
        Object[] wfBusinessType = {WfBusinessType.收文,WfBusinessType.发文,WfBusinessType.便函,WfBusinessType.内部请示};
        SearchBuilder<WfNodeInst, String> searchBuilder = new SearchBuilder<WfNodeInst, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        if (StringUtils.isNotBlank(title)) {
            searchBuilder.getFilterBuilder().like("flowInst.title", title.trim() + "%");
        }
        searchBuilder.getFilterBuilder().in("curNode.flow.flowType",wfBusinessType);
        searchBuilder.getFilterBuilder().equal("dealerId", user.getId());
        searchBuilder.getFilterBuilder().equal("instStatus", WfInstStatus.在办);
        if ("task".equals(flag)) {
            // 不显示阅知
            searchBuilder.getFilterBuilder().notEqual("curNodeId", WorkflowUtils.NODE_READ);
        } else {
            // 只显示阅知
            searchBuilder.getFilterBuilder().equal("curNodeId", WorkflowUtils.NODE_READ);
        }
        searchBuilder.appendSort(Direction.DESC, "arrivalTime");
        SearchListHelper<WfNodeInst> listhelper = new SearchListHelper<WfNodeInst>();
        listhelper.execute(searchBuilder, nodeInstService.getRepository(), new Integer(page), new Integer(rows));
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), TaskShowBean.class));
        p.setTotal(listhelper.getCount());
        return AesTool.responseData(p, aesFlag_);
    }

    /** 取得某个公文的审批环节过程及意见列表 */
    @RequestMapping(value = { "/task/historylist.mo" })
    @ResponseBody
    public AppResponseInfo getTaskHistoryListJson(HttpServletRequest request, Boolean aesFlag_) {
        String entityId = request.getParameter("id");
        OaDocFile docFile = this.docFileService.findOne(entityId);
        if (docFile == null) {
            return AesTool.responseData("0", "找不到相应的公文！", null, aesFlag_);
        }
        // 读取审批意见
        List<WfAuditOpinion> opinionList = auditOpinionService.findAuditOpinionsByFlowInstId(docFile.getFlowInstId());
        List<WfNodeInst> nodeList = nodeInstService.findByEntityId(entityId);
        List<AppDocNodeInfo> list = new ArrayList(); // BeanCopyBuilder.buildList(nodeList, AppDocNodeInfo.class);

        for (WfNodeInst node : nodeList) {
            AppDocNodeInfo bean = new AppDocNodeInfo();
            bean.setArrivalTime(node.getArrivalTime());
            bean.setCandidates(node.getCandidates());
            bean.setDealerId(node.getDealerId());
            if(node.getDealType().equals(WfDealType.子流程办理)){
            	bean.setDealerName("");
            }else{
            	bean.setDealerName(node.getDealer().getUserName());
            }
            bean.setNodeId(node.getCurNodeId());
            bean.setNodeName(node.getCurNode().getNodeName());
            bean.setStatus(node.getInstStatus().getKey());
            bean.setReadFlag(node.getReadFlag().getKey());
            if(node.getDealType().equals(WfDealType.子流程办理)){
            	bean.setDealTime(null);
            }else{
            	bean.setDealTime(node.getDealTime());
            }
            bean.setArrivalTime(node.getArrivalTime());
            bean.setOpinion("");

            if(opinionList!=null){
                for (WfAuditOpinion op : opinionList) {
                    if (node.getId().equals(op.getNodeInstId())) {
                        bean.setOpinion(StringUtils.isBlank(op.getApprOpinion()) ? "" : op.getApprOpinion());
                    }
                }
            }
            list.add(bean);
        }

        return AesTool.responseData(list, aesFlag_);
    }

    /** 公文详细信息 */
    @RequestMapping(value = { "/detail.mo" })
    @ResponseBody
    public AppResponseInfo getDocFileJson(HttpServletRequest request, Boolean aesFlag_) {

        String entityId = request.getParameter("id");
        if (StringUtils.isBlank(entityId)) {
            return AesTool.responseData("0", "公文ID为空，参数错误！", null, aesFlag_);
        }

        OaDocFile obj = docFileService.findOne(entityId);
        if (obj == null) {
            return AesTool.responseData("0", "找不到相应的公文！", null, aesFlag_);
        }
        AppDocFileInfo bean = BeanCopyUtil.copyBean(obj, AppDocFileInfo.class);

        // 读取审批意见
        List<WfAuditOpinion> opinionList = auditOpinionService.findAuditOpinionsByFlowInstId(obj.getFlowInstId());
        List<AppDocOpinion> opinionShowList = BeanCopyBuilder.buildList(opinionList,AppDocOpinion.class);

        bean.setOpinionList(opinionShowList);

        // 加载正文和附件
        List<SysAttach> listAttach = new ArrayList();
        if (StringUtils.isNotBlank(obj.getDocId())) {
            SysAttach mainAttach = attachService.findOne(obj.getDocId());
            if (mainAttach != null) {
                listAttach.add(mainAttach);
            }
        }
        listAttach.addAll(attachService.findByOwnerIdAndOwnerType(entityId, OaDocFile.class.getSimpleName()));
        bean.setAttachList(listAttach);

        return AesTool.responseData(bean, aesFlag_);
    }

    /** 打开公文办理页面 */
    @RequestMapping(value = { "/task/dealpage.mo" })
    @ResponseBody
    public AppResponseInfo taskDealPageJson(HttpServletRequest request, Boolean aesFlag_) {
        String taskId = request.getParameter("tid");
        String entityId = request.getParameter("id");
        if (StringUtils.isBlank(entityId)) {
            return AesTool.responseData("0", "公文ID为空，参数错误！", null, aesFlag_);
        }
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }

        WfNodeInst nodeInst = nodeInstService.findOne(taskId);
        if (nodeInst == null) {
            return AesTool.responseData("0", "该任务出现异常，编号为空！", null, aesFlag_);
        }

        boolean isReadNode = WorkflowUtils.NODE_READ.equals(nodeInst.getCurNodeId());

        if (!user.getId().equals(nodeInst.getDealerId())) {
            return AesTool.responseData("0", "该任务不在您的办理范围内！", null, aesFlag_);
        }
        if (!isReadNode && WfInstStatus.办结.equals(nodeInst.getInstStatus())) {
            return AesTool.responseData("0", "该任务已经被其他人办理，请刷新或重新进入待办页面！", null, aesFlag_);
        }

        OaDocFile obj = docFileService.findOne(entityId);
        if (obj == null) {
            return AesTool.responseData("0", "找不到相应的公文！", null, aesFlag_);
        }
        AppDocFileInfo bean = BeanCopyUtil.copyBean(obj, AppDocFileInfo.class);
        if(nodeInst.getCurNode()!=null&&nodeInst.getCurNode().getEditFlag()!=null) {
            bean.setEditFlag(nodeInst.getCurNode().getEditFlag().getKey());
        }


        // 加载正文和附件
        List<SysAttach> listAttach = new ArrayList();
        if (StringUtils.isNotBlank(obj.getDocId())) {
            SysAttach mainAttach = attachService.findOne(obj.getDocId());
            if (mainAttach != null) {
                listAttach.add(mainAttach);
            }
        }
        listAttach.addAll(attachService.findByOwnerIdAndOwnerType(entityId, OaDocFile.class.getSimpleName()));
        bean.setAttachList(listAttach);

        // 获取办理节点信息
        String curNodeId = workflowService.getNodeIdByFlowInstIdAndDealer(obj.getFlowInstId(), user.getId());
        // 更新节点已读状态
        workflowService.updateNodeReadStatus(obj.getFlowInstId(), nodeInst.getCurNodeId(), user.getId());

        // 读取审批意见
        List<WfAuditOpinion> opinionList = auditOpinionService.findAuditOpinionsByFlowInstId(obj.getFlowInstId());
        List<AppDocOpinion> opinionShowList = BeanCopyBuilder.buildList(opinionList,AppDocOpinion.class);

        bean.setOpinionList(opinionShowList);

        bean.setCurNodeId(curNodeId);
        if (!isReadNode) {
            // 获取当前节点功能定义
            WfNode curNode = nodeService.findOne(nodeInst.getCurNodeId());
            List<String> funcList = new ArrayList<String>();
            if (curNode.getFuncBtns() != null) {
                funcList = Arrays.asList(curNode.getFuncBtns().split(","));
            }
            bean.setCurNodeId(curNode.getId());
            bean.setFlowId(curNode.getFlowId());
            bean.setFuncList(funcList);

            // 定义有回退环节
            List<WfNodeLine> blist = this.nodeLineService.getBackNodes(curNode.getFlowId(), curNode.getId());
            if (blist != null && !blist.isEmpty()) {
                List list = new ArrayList();
                for (WfNodeLine line : blist) {
                    list.add(line.getToNode());
                }
                bean.setBackNodeList(list);
            }

        }

        bean.setCurNodeInstId(taskId);

        return AesTool.responseData(bean, aesFlag_);
    }

    /** 提交公文办理 */
    @RequestMapping(value = { "/task/submit.mo" })
    @ResponseBody
    public AppResponseInfo taskSubmitJson(HttpServletRequest request, Boolean aesFlag_) {
        String taskId = request.getParameter("tid");
        String entityId = request.getParameter("id");
        String nextNodeId = request.getParameter("nextNodeId");
        String nextDealers = request.getParameter("nextDealers");
        String opinion = request.getParameter("opinion");
        //拟办意见
        String suggestion = request.getParameter("suggestion");
        String writeFields = request.getParameter("writeFields");
        // 签批内容
        String signContent = request.getParameter("signContent");
        // 文件版本号，以避免提交冲突
        String ver = request.getParameter("ver");

        // 在归档环节需要提交“归档类型”
        String arcflag = request.getParameter("arcflag");

        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }

        WfNodeInst ni = nodeInstService.findOne(taskId);

        if (ni == null) {
            return AesTool.responseData("0", "流程错误，没有找到任务实例，请联系管理员！", null, aesFlag_);
        } else if (ni != null && WfInstStatus.办结.equals(ni.getInstStatus())) {
            return AesTool.responseData("0", "任务已失效，可能已被其他人抢先执行！", null, aesFlag_);
        } else {
            OaDocFileEditBean bean = new OaDocFileEditBean();
            bean.setNextNodeId(nextNodeId);
            bean.setNextDealers(nextDealers);
            bean.setId(entityId);
            bean.setOpinion(opinion);
            bean.setStartFlag(false);
            bean.setFromMobile(true);
            bean.setCurNodeId(ni.getCurNodeId());
            bean.setCurNodeInstId(ni.getId());
            bean.setFlowId(ni.getFlowInst().getFlowId());
            bean.setFlowInstId(ni.getFlowInstId());
            bean.setCurrentUser(user);
             if (StringUtils.isNotBlank(suggestion)) {
                 bean.setSuggestion(suggestion);
             }
            bean.setVersion_(StringUtils.isBlank(ver) ? 1 : new Integer(ver));

            docEventService.completeNodeDone(bean, request);

            //如果nextNodeId为空则为提交会签的提交（pc端后台接口代码同样在提交会签的环节nextNodeId为空，所以暂时这样处理nextNodeId为空的情况）
            if(StringUtils.isNoneBlank(nextNodeId)){
            	if (nextNodeId.startsWith("N999")) {
                    this.logService.saveMobileLog(user, "公文办结：" + ni.getFlowInst().getTitle(), request);
                } else {
                    this.logService.saveMobileLog(user, "办理公文：" + ni.getFlowInst().getTitle(), request);
                }
            }else{
            	this.logService.saveMobileLog(user, "办理公文：" + ni.getFlowInst().getTitle(), request);
            }

            return AesTool.responseData("", aesFlag_);
        }

    }

    /**
     * 提交阅知批示
     *
     * @param request
     *            flowInstId curNodeId opinion
     * @return
     */
    @RequestMapping(value = { "/task/instruction.mo" })
    @ResponseBody
    public AppResponseInfo instructionJson(HttpServletRequest request, Boolean aesFlag_) {

        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在!", null, aesFlag_);
        }

        String flowInstId = request.getParameter("flowInstId");
        String curNodeId = request.getParameter("curNodeId");
        String opinion = request.getParameter("opinion");
        if (StringUtils.isEmpty(flowInstId) || StringUtils.isEmpty(curNodeId)) {
            return AesTool.responseData("0", "参数错误!", null, aesFlag_);
        }
        List<String> aoIds = workflowService.updateNodeReadStatus(flowInstId, curNodeId, user.getId());
        for (String aoId : aoIds) {
            WfAuditOpinion ao = this.auditOpinionService.findOne(aoId);
            // 保存阅知意见
            ao.setApprOpinion(StringUtils.isNotEmpty(opinion) ? opinion : "已阅");
            ao.setApprTime(new Date());
            auditOpinionService.persist(ao);
        }
        return AesTool.responseData("1", "批示成功！", null, aesFlag_);

    }

    /** 公文退回操作 */
    @RequestMapping(value = { "/task/return.mo" })
    @ResponseBody
    public AppResponseInfo doTaskReturn(HttpServletRequest request, Boolean aesFlag_) {
        String taskId = request.getParameter("tid");
        String entityId = request.getParameter("id");
        String backNodeId = request.getParameter("backNodeId");
        String opinion = request.getParameter("opinion");
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }

        try {
            OaDocFile docFile = docFileService.findOne(entityId);
            WfNodeInst nodeInst = nodeInstService.findOne(taskId);
            if (docFile != null && nodeInst != null) {
                docEventService.returnNodeInst(docFile, nodeInst, backNodeId, opinion, user.getId(), request);
                logService.saveMobileLog(user, "退回公文《" + docFile.getTitle() + "》到" + backNodeId, request);
                return AesTool.responseData("", aesFlag_);
            } else {
                return AesTool.responseData("0", "公文不存在，无法执行操作。", null, aesFlag_);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AesTool.responseData("0", "操作失败！" + e.getMessage(), null, aesFlag_);
        }
    }

    /** 发起阅知 */
    @RequestMapping(value = { "/task/toreading.mo" })
    @ResponseBody
    public AppResponseInfo toReadingNodes(HttpServletRequest request, Boolean aesFlag_) {
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
        String entityId = request.getParameter("id");
        String title = request.getParameter("title"); // 公文标题
        String readers = request.getParameter("readers"); // 阅知者ID，多人用逗号分开
        try {
            docEventService.addReadNodeInst(entityId, title,readers, user, request);
            logService.saveMobileLog(user, "发送阅知：" + title, request);
            return AesTool.responseData("", aesFlag_);
        } catch (Exception e) {
            return AesTool.responseData("0", "发起阅知失败！", null, aesFlag_);
        }
    }

    /** 经办公文列表 */
    @RequestMapping(value = { "/mydoclist.mo" })
    @ResponseBody
    public AppResponseInfo mydocListJson(HttpServletRequest request,String sort,String order, Boolean aesFlag_) {
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }

        String page = request.getParameter("page");
        String rows = request.getParameter("rows");

        // 查询条件（可选）
        String title = request.getParameter("title");
        String docCode = request.getParameter("docCode");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        Date dt1 = strToDatetime(fromDate, "00:00");
        Date dt2 = strToDatetime(toDate, "23:59");

        OaDocFileQueryBean bean = new OaDocFileQueryBean();
        bean.setTitle(title);
        bean.setDocCode(docCode);
        bean.setCreateDateBegin(dt1);
        bean.setCreateDateEnd(dt2);
        bean.setDealerId(user.getId());
        Page p = docEventService.selectMylinkDocs(bean, new Integer(page), new Integer(rows), false,sort,order);

        return AesTool.responseData(p, aesFlag_);
    }

    /** 公文监控列表 */
    @RequestMapping("/monitorlist.mo")
    @ResponseBody
    public AppResponseInfo docMonitorJson(HttpServletRequest request, Boolean aesFlag_) {
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");

        String title = request.getParameter("title");
        String businessType = request.getParameter("businessType");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        Date dt1 = strToDatetime(fromDate, "00:00");
        Date dt2 = strToDatetime(toDate, "23:59");

        OaDocFileQueryBean bean = new OaDocFileQueryBean();
        bean.setTitle(title);
        bean.setBusinessType(businessType);
        bean.setCreateDateBegin(dt1);
        bean.setCreateDateEnd(dt2);

        Page p = docEventService.selectMylinkDocs(bean, new Integer(page), new Integer(rows), true,null,null);

        return AesTool.responseData(p, aesFlag_);
    }

    /** 公文查询列表（自己拟稿的公文） */
    @RequestMapping(value = { "/querylist.mo" })
    @ResponseBody
    public AppResponseInfo queryListJson(HttpServletRequest request,String sort,String order, Boolean aesFlag_) {
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }

        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        // 查询条件（可选）
        String title = request.getParameter("title");
        String sncode = request.getParameter("sncode");
        String docCode = request.getParameter("docCode");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        Date dt1 = strToDatetime(fromDate, "00:00");
        Date dt2 = strToDatetime(toDate, "23:59");

        OaDocFileQueryBean bean = new OaDocFileQueryBean();
        if (StringUtils.isNotBlank(title)) {
            bean.setTitle("%" + title + "%");
        }
        if (StringUtils.isNotBlank(sncode)) {
            bean.setSncode("%" + sncode + "%");
        }
        if (StringUtils.isNotBlank(docCode)) {
            bean.setDocCode("%" + docCode + "%");
        }
        bean.setCreateDateBegin(dt1);
        bean.setCreateDateEnd(dt2);
        bean.setCreatorId(user.getId());

        org.springframework.data.domain.Page<OaDocFile> data = docFileService.selectDocFilesPage(bean, new Integer(page), new Integer(rows), sort, order);
        Page p = new Page();
        if (data != null) {
            p.setRows(BeanCopyBuilder.buildList(data.getContent(), FileShowBean.class));
            p.setTotal(data.getTotalElements());
        }
        return AesTool.responseData(p, aesFlag_);
    }

    /** 我的公文待办事项数量 */
    @RequestMapping(value = { "/task/count.mo" }, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String taskCountJson(HttpServletRequest request) {
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return JSONObject.toJSONString(new AppResponseInfo("0", "用户不存在！", null));
        }

        return String.valueOf(this.nodeInstService.getTaskingCount(user.getId()));
    }

    /** 公文传阅列表 */
    @RequestMapping(value = { "/docread/readlist.mo" })
    @ResponseBody
    public AppResponseInfo docreadListjson(HttpServletRequest request, Boolean aesFlag_) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");

        // 查询条件（可选）
        String title = request.getParameter("title");

        if (!StringUtils.isNumeric(page) || !StringUtils.isNumeric(rows)) {
            return AesTool.responseData("0", "页码错误！", null, aesFlag_);
        }

        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
        SearchBuilder<DocReadReceives, String> searchBuilder = new SearchBuilder<DocReadReceives, String>(
                        conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("receiverId", user.getId());
        if (StringUtils.isNotEmpty(title)) {
            searchBuilder.getFilterBuilder().like("docread.docTitle", title);
        }
        searchBuilder.appendSort(Direction.DESC, "docread.sendTime");
        SearchListHelper<DocReadReceives> listhelper = new SearchListHelper<DocReadReceives>();
        listhelper.execute(searchBuilder, readReceiveService.getRepository(), new Integer(page), new Integer(rows));
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocreadRecShowBean.class));
        p.setTotal(listhelper.getCount());
        return AesTool.responseData(p, aesFlag_);
    }

    /**
     * word附件转html,返回html字符串
     *
     * @param id
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = { "/preview.mo" }, produces = "text/html;charset=utf-8")
    @ResponseBody
    public void previewDocHtml(String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        SysUser user = this.loadCurrentUser(request);

        InputStream is = null;
        CipherInputStream cis = null;
        OutputStream os = null;

        try {
            os = response.getOutputStream();
            if (user == null) {
                os.write(new String("<!doctype html><html><head><meta charset='utf-8' />"
                                + "<meta http-equiv='X-UA-Compatible' content='IE=edge' />"
                                + "<meta name='viewport' content='width=device-width, initial-scale=1' /></head>"
                                + "<body>无法预览此文档</body></html>").getBytes("utf-8"));
                os.flush();
                return;
            }
            SysAttach attach = this.attachService.findOne(id);
            String attachUrl = AttachHelper.getAttacheFileFullPath(attach.getSavePath(), attach.getSaveName(), "");
            String fileExt = FilenameUtils.getExtension(attach.getSaveName());
            boolean crypted = StringUtils.isNotBlank(fileExt) ? false : true;// 根据保存文件名的后缀判断文件是否被加密：无后缀是加密文件

            if (crypted) {// 需要解密
                cis = EncryptUtils.decryptInputStream(EncryptUtils.ALGORITHM_DES, new FileInputStream(attachUrl));
                WordUtil.convert2Html(cis, wordImageTempPath, os);
            } else {
                is = new FileInputStream(attachUrl);
                WordUtil.convert2Html(is, wordImageTempPath, os);
            }

        } catch (Exception e) {
            if (os != null) {
                os.write(new String("<!doctype html><html><head><meta charset='utf-8' />"
                                + "<meta http-equiv='X-UA-Compatible' content='IE=edge' />"
                                + "<meta name='viewport' content='width=device-width, initial-scale=1' /></head>"
                                + "<body>无法预览此文档</body></html>").getBytes("utf-8"));
                os.flush();
            }
            e.printStackTrace();
        } finally {
            if (cis != null) {
                cis.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

}
