package cn.com.chaochuang.doc.event.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.data.persistence.SearchFilter;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.reference.DocSource;
import cn.com.chaochuang.doc.event.service.InteRequestService;
import cn.com.chaochuang.doc.event.service.OaDocFileService;
import cn.com.chaochuang.doc.letter.service.DocDepLetterService;
import cn.com.chaochuang.doc.readmatter.service.ReadMatterService;
import cn.com.chaochuang.workflow.event.bean.TaskShowBean;
import cn.com.chaochuang.workflow.inst.bean.NodeInstQueryBean;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfAuditOpinionService;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfApprResult;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfInstStatus;


/**
 * @author dengl 2017.12.14
 *
 */
@Controller
@RequestMapping("doc/interequest")
public class DocInteRequestController extends DocFileCommonController{
	
    @Autowired
    private InteRequestService     		inteRequestService;
    
    @Autowired
	private OaDocFileService 	   		docFileService;
    
    @Autowired
	private SysAttachService 	   		docAttachService;
    
    @Autowired
	private WfAuditOpinionService 		auditOpinionService;
    
    @Autowired
	private DocDepLetterService 		depLetterService;
    
    @Autowired
	private WfNodeInstService 			nodeInstService;
    
    @Autowired
	private ReadMatterService 			readMatterService;
    
	@RequestMapping("/new")
    public ModelAndView newPage() {
		return super.newFilePage(WfBusinessType.内部请示);
    }
	
	/**
     * 内部请示公文新增页面
     */
    @RequestMapping("/tasklist")
    public ModelAndView taskList() {
        ModelAndView mav = new ModelAndView("/doc/interequest/tasklist");
        return mav;
    }
    
    /**
     * 内部请示公文编辑页面
     */
    @RequestMapping("/edit")
    public ModelAndView edit(String id,String parentId) {
    	return super.editFilePage(id, WfBusinessType.内部请示,parentId);
    }
	
	/** 任务办理页面 */
    @RequestMapping("task/deal")
    public ModelAndView dealPage(String returnPageType,String opflag, String id, String fileId, HttpServletRequest request) {
        return super.openDealPage(returnPageType,opflag, id, fileId, WfBusinessType.内部请示);
    }
    
    @RequestMapping(value = "listTask.json")
    @ResponseBody
    public Page taskListjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	Map<String, SearchFilter> thisFilters = new HashMap<String, SearchFilter>();

        Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "__");
        if (MapUtils.isNotEmpty(searchParams)) {
            thisFilters = SearchFilter.parse(searchParams);
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Page p = new Page();
        dataMap = this.inteRequestService.inteRequest(thisFilters.values());
        p.setRows((ArrayList<TaskShowBean>) dataMap.get("rows"));
        p.setTotal(dataMap.get("total") == null ? (long) 0 : (long) dataMap.get("total"));
        return p;
    }
    
    // 内部请示详情
 	@RequestMapping("/detailFile")
 	public ModelAndView detail(@RequestParam("id") String id, HttpServletRequest request) {
 		ModelAndView mav = null;

		// 基本信息
		OaDocFile obj = this.docFileService.findOne(id);
		if (obj == null) {
			mav = new ModelAndView("/doc/docevent/message");
			mav.addObject("hints", "公文信息读取失败，该公文可能已被删除，请刷新或重新进入列表页面。");
			return mav;
		}
		if (WfBusinessType.内部请示.equals(obj.getFileType())) {
			mav = new ModelAndView("/doc/archives/file/interequest_detail");
		}
		// 读取附件
		mav.addObject("attachMap", docAttachService.getAttachMap(id, OaDocFile.DOC_ATTACH_OWNER_DOC));
		// 读取审批意见
		mav.addObject("opinionList",
				auditOpinionService.findAuditOpinionsByFlowInstIdAndResult(obj.getFlowInstId(), WfApprResult.通过));
		// 读取相关部门函件数量，以显示会办信息
		mav.addObject("depLettersCount", depLetterService.countLettersByFileId(id));
		mav.addObject("depLetterReceivers", depLetterService.selectReceiversByFileId(id));
		// 读取核稿秘书环节（用于文笺显示）
		WfNodeInst msNodeInst = null;
		NodeInstQueryBean nodeInstQuery = new NodeInstQueryBean();
		nodeInstQuery.setFlowInstId(obj.getFlowInstId());
		nodeInstQuery.setNodeInstStatus(WfInstStatus.办结);
		if (DocSource.区局发文.equals(obj.getSourceType())) {
			nodeInstQuery.setCurNodeId("N202");
			List<WfNodeInst> msNodeInsts = nodeInstService.selectNodeInsts(nodeInstQuery);
			if (msNodeInsts != null && msNodeInsts.size() > 0) {
				msNodeInst = msNodeInsts.get(msNodeInsts.size() - 1);
			}
		}else if (DocSource.代拟发文.equals(obj.getSourceType())) {
			nodeInstQuery.setCurNodeId("N303");
			List<WfNodeInst> msNodeInsts = nodeInstService.selectNodeInsts(nodeInstQuery);
			if (msNodeInsts != null && msNodeInsts.size() > 0) {
				msNodeInst = msNodeInsts.get(msNodeInsts.size() - 1);
			}
		}
		if (msNodeInst != null) {
			mav.addObject("msNode", msNodeInst);
		}

		mav.addObject("readCount", this.readMatterService.selectReadMatterCount(id));

		mav.addObject("obj", obj);
		return mav;
 	}
}
