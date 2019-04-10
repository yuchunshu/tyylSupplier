package cn.com.chaochuang.doc.event.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.syspower.service.SysPowerService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.AttachUtils;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.doccode.domain.OaDocCode;
import cn.com.chaochuang.doc.doccode.service.OaDocCodeService;
import cn.com.chaochuang.doc.event.bean.OaDocFileShowBean;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.reference.FileStatusFlag;
import cn.com.chaochuang.doc.event.service.OaDocFileService;
import cn.com.chaochuang.doc.expiredate.service.SysDeadLineTypeService;
import cn.com.chaochuang.doc.letter.service.DocDepLetterService;
import cn.com.chaochuang.doc.template.service.DocTemplateService;
import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.workflow.def.domain.WfNode;
import cn.com.chaochuang.workflow.def.domain.WfNodeLine;
import cn.com.chaochuang.workflow.def.repository.WfFlowRepository;
import cn.com.chaochuang.workflow.def.service.WfFlowService;
import cn.com.chaochuang.workflow.def.service.WfNodeLineService;
import cn.com.chaochuang.workflow.def.service.WfNodeService;
import cn.com.chaochuang.workflow.event.service.WorkflowService;
import cn.com.chaochuang.workflow.inst.domain.WfAuditOpinion;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfAuditOpinionService;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfApprResult;
import cn.com.chaochuang.workflow.reference.WfInstStatus;
import cn.com.chaochuang.workflow.util.WorkflowUtils;

/**
 * @author hzr 2016-10-12
 *
 */
public class DocFileCommonController {

    @Autowired
    protected SysUserService       userService;

    @Autowired
    private OaDocFileService       docFileService;

    @Autowired
    protected ConversionService    conversionService;

    @Autowired
    private SysDepartmentService   deptService;

    @Autowired
    private SysAttachService       attachService;

    @Value(value = "${upload.rootpath}")
    private String                 rootPath;

    @Value(value = "${upload.appid.docFileAttach}")
    private String                 appid;
    @Autowired
    private WorkflowService        workFlowService;

    @Autowired
    private WfNodeInstService      nodeInstService;

    @Autowired
    private WfNodeLineService      nodeLineService;
    
    @Autowired
    private OaDocCodeService       oaDocCodeService;

    @Autowired
    protected DocTemplateService   docTemplateService;

    @Autowired
    private WfAuditOpinionService  auditOpinionService;

    @Autowired
    private WfNodeService          nodeService;
    
    @Autowired
    private SysPowerService        sysPowerService;
    
    @Autowired
    private SysDeadLineTypeService sysDeadLineTypeService;
    
    @Autowired
    private WfFlowRepository 	   flowRepository;
    
    @Autowired
    private WfFlowService          flowService;

    public ModelAndView newFilePage(WfBusinessType busType) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        ModelAndView mav = null;
        if (WfBusinessType.收文.equals(busType)) {
        	mav = new ModelAndView("/doc/receivefile/edit");
        	boolean isJy = sysPowerService.selectPowerByPowerNameAndCurrentUser("机要收文编号");
        	if(isJy){
        		mav.addObject("isJy", "1");
        	}else{
        		mav.addObject("isJy", "0");
        	}
        	
        } else if (WfBusinessType.发文.equals(busType)) {
        	mav = new ModelAndView("/doc/sendfile/edit");
        } else if (WfBusinessType.便函.equals(busType)) {
        	mav = new ModelAndView("/doc/simplefile/edit");
        } else if(WfBusinessType.内部请示.equals(busType)){
        	mav = new ModelAndView("/doc/interequest/edit");
        }else {
        	return null;
        }
        String docname = AttachUtils.getDocUniqueFileName("MainFile_", "doc");
        String docpath = this.rootPath + "/" + this.appid + "/" + Tools.DATE_FORMAT.format(new Date());
        //System.out.println("==========================" + docpath);
        File file = new File(docpath);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 读取文号
        List<OaDocCode> codeList = oaDocCodeService.selectDocCodesByCodeType(busType);
        mav.addObject("docpath", docpath);
        mav.addObject("docname", docname);
        mav.addObject("currUser", user);
        mav.addObject("codeList", codeList);
        mav.addObject("newOperate", "1");

        mav.addObject("flowList", flowService.selectCanWorkFlow(busType));

        OaDocFile obj = new OaDocFile();
        obj.setStatus(FileStatusFlag.暂存);
        obj.setReceiveDate(new Date());
        obj.setPrintDate(new Date());
        obj.setCreateDate(new Date());
        obj.setCreatorId(user.getId());
        obj.setCreatorName(user.getUserName());
        obj.setCreaterDept(user.getDepartment());
        mav.addObject("obj", obj);

        Map<String, String> deadLineTypeMap = sysDeadLineTypeService.getSysDeadLineTypeMap();
        mav.addObject("deadLineTypeMap", deadLineTypeMap);
        return mav;
    }

    public ModelAndView editFilePage(String id, WfBusinessType busType,String parentId) {
        ModelAndView mav = null;
        if (WfBusinessType.收文.equals(busType)) {
        	mav = new ModelAndView("/doc/receivefile/edit");
        	boolean isJy = sysPowerService.selectPowerByPowerNameAndCurrentUser("机要收文编号");
        	if(isJy){
        		mav.addObject("isJy", "1");
        	}else{
        		mav.addObject("isJy", "0");
        	}
        } else if (WfBusinessType.发文.equals(busType)) {
        	mav = new ModelAndView("/doc/sendfile/edit");
        } else if (WfBusinessType.便函.equals(busType)) {
        	mav = new ModelAndView("/doc/simplefile/edit");
        } else if(WfBusinessType.内部请示.equals(busType)){
        	mav = new ModelAndView("/doc/interequest/edit");
        } else {
        	return null;
        }

        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        OaDocFile obj = docFileService.findOne(id);
        if (obj == null) {
            mav = new ModelAndView("/doc/docevent/message");
            mav.addObject("hints", "公文信息读取失败，该公文可能已被删除，请刷新或重新进入列表页面。");
            return mav;
        }
        Map<String,Map<String,String>> attachMap = Maps.newLinkedHashMap();
        // 查找正文附件
        String docname = null;
        String docpath = null;
        Map<String,String> docAttach= Maps.newHashMap();
        if (StringUtils.isNotBlank(obj.getDocId())) {
            SysAttach docFileAttach = attachService.findOne(obj.getDocId());
            if (docFileAttach != null) {
                // docname = docFileAttach.getTrueName();
                docname = docFileAttach.getSaveName();
                docpath = this.rootPath + "/" + docFileAttach.getSavePath();

                docAttach.put("id",obj.getDocId());
                docAttach.put("name",docFileAttach.getTrueName());
                docAttach.put("__serverPath",docpath+docname);
                docAttach.put("__fileType","mainFile");
                attachMap.put(obj.getDocId(),docAttach);
            }
        }
        if (StringUtils.isBlank(docname) && StringUtils.isBlank(docpath)) {
            // 附件不存在，或者被删除，创建新的
            docname = AttachUtils.getDocUniqueFileName("MainFile_", "doc");
            docpath = this.rootPath + "/" + this.appid + "/" + Tools.DATE_FORMAT.format(new Date());
            File file = new File(docpath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }

        //转办时，把正文当做附件
        if(StringUtils.isNotBlank(parentId)){
        	// 读取附件和正文
            mav.addObject("attachMap", attachService.getAttachMap(id, new String[]{OaDocFile.DOC_ATTACH_OWNER_DOC,OaDocFile.DOC_ATTACH_OWNER_MAIN}));
        }else{
        	// 读取附件
            Map<String, List<SysAttach>> sysAttachMap = attachService.getAttachMap(id, new String[]{OaDocFile.DOC_ATTACH_OWNER_DOC,OaDocFile.DOC_ATTACH_OWNER_MAIN_PDF});
            mav.addObject("attachMap", sysAttachMap);

            if(sysAttachMap!=null) {
                for (SysAttach attach : sysAttachMap.get(id)) {
                    Map<String,String> attachInfo= Maps.newHashMap();
                    attachInfo.put("id",attach.getId());
                    attachInfo.put("name",attach.getTrueName());
                    attachInfo.put("__fileType",attach.getOwnerType());
                    attachInfo.put("__serverPath",this.rootPath + "/" +attach.getSavePath()+attach.getSaveName());
                    attachMap.put(attach.getId(),attachInfo);
                }
            }
        }

        try {
            //附件数据的json格式
            mav.addObject("attachJson", new ObjectMapper().writeValueAsString(attachMap));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 读取模板
        List<OaDocCode> codeList = oaDocCodeService.selectDocCodesByCodeType(busType);

        // 设置来文字号
        if (StringUtils.isNotBlank(obj.getDocCode())) {
            String docCode = obj.getDocCode().trim();
            int i = docCode.indexOf("[");
            int j = docCode.indexOf("]");
            int k = docCode.indexOf("号");
            try {
                mav.addObject("docCode1", docCode.substring(0, i));
                mav.addObject("docCode2", docCode.substring(i+1, j));
                mav.addObject("docCode3", docCode.substring(j+1, docCode.length()-1));
            } catch (Exception e) {
            	//避免有非法文号，导致文档打不开
            }
        }

        mav.addObject("docpath", docpath);
        mav.addObject("docname", docname);
        mav.addObject("currUser", user);
        mav.addObject("obj", obj);
        mav.addObject("codeList", codeList);
        mav.addObject("parentId", parentId);
        Map<String, String> deadLineTypeMap = sysDeadLineTypeService.getSysDeadLineTypeMap();
        mav.addObject("deadLineTypeMap", deadLineTypeMap);
        mav.addObject("flowList", flowService.selectCanWorkFlow(busType));
        return mav;
    }



    /** 任务办理页面 */
    public ModelAndView openDealPage(String returnPageType,String opflag, String id, String fileId, WfBusinessType busType) {
        ModelAndView mav = null;
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();

        WfNodeInst nodeInst = nodeInstService.findOne(id);
        if (nodeInst == null) {
        	mav = new ModelAndView("/doc/docevent/message");
            mav.addObject("hints", "该任务出现异常，编号为空！");
            return mav;
        }

        boolean isReadNode = WorkflowUtils.NODE_READ.equals(nodeInst.getCurNodeId());

        if (!user.getId().equals(nodeInst.getDealerId())) {
            mav = new ModelAndView("/doc/docevent/message");
            mav.addObject("hints", "该任务不在您的办理范围内！");
            return mav;
        }
        if (!isReadNode && WfInstStatus.办结.equals(nodeInst.getInstStatus())) {
            mav = new ModelAndView("/doc/docevent/message");
            mav.addObject("hints", "该任务已经被其它人办理，请刷新或重新进入待办页面！");
            return mav;
        }

        String vm = "";
        if (isReadNode) {
        	vm = "form_noderead";
        } else {
        	vm = "form_nodedeal";
        }
        if (WfBusinessType.收文.equals(busType)) {
        	mav = new ModelAndView("/doc/receivefile/" + vm);
        } else if (WfBusinessType.发文.equals(busType)) {
        	mav = new ModelAndView("/doc/sendfile/" + vm);
        } else if (WfBusinessType.便函.equals(busType)) {
        	mav = new ModelAndView("/doc/simplefile/" + vm);
        } else if (WfBusinessType.内部请示.equals(busType)){
        	mav = new ModelAndView("/doc/interequest/" + vm);
        } else {
        	return null;
        }
        OaDocFile obj = this.docFileService.findOne(fileId);
        if (obj == null) {
            mav = new ModelAndView("/doc/docevent/message");
            mav.addObject("hints", "公文信息读取失败，该公文可能已被删除，请刷新或重新进入列表页面。");
            return mav;
        }

        Map<String,Map<String,String>> attachMap = Maps.newLinkedHashMap();
        // 查找正文附件
        String docname = null;
        String docpath = null;
        Map<String,String> docAttach= Maps.newHashMap();
        if (StringUtils.isNotBlank(obj.getDocId())) {
            SysAttach docFileAttach = attachService.findOne(obj.getDocId());
            if (docFileAttach != null) {
                // docname = docFileAttach.getTrueName();
                docname = docFileAttach.getSaveName();
                docpath = this.rootPath + "/" + docFileAttach.getSavePath();

                docAttach.put("id",obj.getDocId());
                docAttach.put("name",docFileAttach.getTrueName());
                docAttach.put("__serverPath",docpath+docname);
                docAttach.put("__fileType","mainFile");
                attachMap.put(obj.getDocId(),docAttach);
            }
        }
        //标记正文是否可以转pdf
        if(StringUtils.isNotEmpty(docname)){
            String subfix = docname.substring(docname.lastIndexOf("."));
            if(!".pdf".equals(subfix.toLowerCase())){
                mav.addObject("canConvertPdfFlag",true);
            }
        }
        if (StringUtils.isBlank(docname) && StringUtils.isBlank(docpath)) {
            // 附件不存在，或者被删除，创建新的
            docname = AttachUtils.getDocUniqueFileName("MainFile_", "doc");
            docpath = this.rootPath + "/" + this.appid + "/" + Tools.DATE_FORMAT.format(new Date());
            File file = new File(docpath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        // 读取附件
        Map<String, List<SysAttach>> sysAttachMap = attachService.getAttachMap(fileId, new String[]{OaDocFile.DOC_ATTACH_OWNER_DOC,OaDocFile.DOC_ATTACH_OWNER_MAIN_PDF});
        mav.addObject("attachMap", sysAttachMap);

        if(sysAttachMap!=null) {
            for (SysAttach attach : sysAttachMap.get(fileId)) {
                Map<String,String> attachInfo= Maps.newHashMap();
                attachInfo.put("id",attach.getId());
                attachInfo.put("name",attach.getTrueName());
                attachInfo.put("__fileType",attach.getOwnerType());
                attachInfo.put("__serverPath",this.rootPath + "/" +attach.getSavePath()+attach.getSaveName());
                attachMap.put(attach.getId(),attachInfo);
            }
        }


        try {
            //附件数据的json格式
            mav.addObject("attachJson", new ObjectMapper().writeValueAsString(attachMap));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String flowId = obj.getFlowId();
        // 获取办理节点信息
        String curNodeId = workFlowService.getNodeIdByFlowInstIdAndDealer(obj.getFlowInstId(), user.getId());
        // 更新节点已读状态
        workFlowService.updateNodeReadStatus(obj.getFlowInstId(), nodeInst.getCurNodeId(), user.getId());
        // 获取当前节点意见
        WfAuditOpinion auditOpinion = workFlowService.getAuditOpinionByNodeInstId(nodeInst.getId());

        if (!isReadNode) {
            // 获取当前节点功能定义
            WfNode curNode = nodeService.findOne(nodeInst.getCurNodeId());
            List<String> funcList = new ArrayList<String>();
            if (curNode.getFuncBtns() != null) {
                funcList = Arrays.asList(curNode.getFuncBtns().split(","));
            }
            mav.addObject("curNode", curNode);
            mav.addObject("funcList", funcList);

        	//定义有回退环节
            int backBtns = 0;
            List<WfNodeLine> blist = this.nodeLineService.getBackNodes(curNode.getFlowId(), curNode.getId());
            if (blist != null && !blist.isEmpty()) {
            	List list = new ArrayList();
                for (WfNodeLine line: blist) {
              		list.add(line.getToNode());
                   	backBtns++;
                }
            	mav.addObject("backNodeList", list);
            }
            mav.addObject("backBtns", backBtns);
        }

        // 设置来文字号
        mav.addObject("codeList", oaDocCodeService.selectDocCodesByCodeType(busType));

        if (StringUtils.isNotBlank(obj.getDocCode())) {
            String docCode = obj.getDocCode().trim();
            int i = docCode.indexOf("[");
            int j = docCode.indexOf("]");
            int k = docCode.indexOf("号");
            try {
                mav.addObject("docCode1", docCode.substring(0, i));
                mav.addObject("docCode2", docCode.substring(i+1, j));
                mav.addObject("docCode3", docCode.substring(j+1, docCode.length()-1));
            } catch (Exception e) {
            	//避免有非法文号，导致文档打不开
            }
        }
        // 读取审批意见
        mav.addObject("opinionList", auditOpinionService.findAuditOpinionsByFlowInstIdAndResult(obj.getFlowInstId(), WfApprResult.通过));

        // 读取相关部门函件数量，以显示会办信息
        //mav.addObject("depLettersCount", depLetterService.countLettersByFileId(fileId));
        //mav.addObject("depLetterReceivers", depLetterService.selectReceiversByFileId(fileId));

        mav.addObject("docpath", docpath);
        mav.addObject("docname", docname);
        mav.addObject("opflag", opflag);
        mav.addObject("returnPageType", returnPageType);
        mav.addObject("obj", obj);
        mav.addObject("curNodeInstId", id);
        mav.addObject("oppr", (auditOpinion == null) ? "" : auditOpinion.getApprOpinion());
        mav.addObject("flowList", flowRepository.findByFlowTypeInOrderByFlowTypeAsc(new WfBusinessType[]{WfBusinessType.发文,WfBusinessType.收文}));

        //mav.addObject("readCount", this.readMatterService.selectReadMatterCount(fileId));

        // 红头模版
        //List<DocTemplate> tempList = docTemplateService.findByDeptId(user.getDepartment().getUnitId());
        //mav.addObject("tempList", tempList);
        return mav;
    }


    public ModelAndView listPage(WfBusinessType busType) {
        ModelAndView mav = null;
        if (WfBusinessType.收文.equals(busType)) {
        	mav = new ModelAndView("/doc/receivefile/list");
        } else if (WfBusinessType.发文.equals(busType)) {
        	mav = new ModelAndView("/doc/sendfile/list");
        } else if (WfBusinessType.便函.equals(busType)) {
        	mav = new ModelAndView("/doc/simplefile/list");
        } else {
        	return null;
        }
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        mav.addObject("creatorId", user.getId());
        return mav;
    }

    public Page listPageJson(Integer page, Integer rows, HttpServletRequest request) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        SearchBuilder<OaDocFile, Long> searchBuilder = new SearchBuilder<OaDocFile, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        // 按部门查询
        // String departmentId = request.getParameter("department");
        searchBuilder.getFilterBuilder().equal("createrDept", deptService.findOne(user.getDepartment().getId()));
        searchBuilder.appendSort(Direction.DESC, "createDate");
        // searchBuilder.getFilterBuilder().equal("status", FileStatusFlag.暂存);
        SearchListHelper<OaDocFile> listhelper = new SearchListHelper<OaDocFile>();
        listhelper.execute(searchBuilder, docFileService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), OaDocFileShowBean.class));
        p.setTotal(listhelper.getCount());

        return p;
    }

}
