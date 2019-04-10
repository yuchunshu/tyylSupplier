/*
 * FileName:    RemoteDocSwapEnvelopeController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.syspower.service.SysPowerService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.DocCodeUtil;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.common.util.UserHelper;
import cn.com.chaochuang.doc.doccode.domain.OaDocCode;
import cn.com.chaochuang.doc.doccode.service.OaDocCodeService;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.reference.DocCate;
import cn.com.chaochuang.doc.event.service.OaDocFileService;
import cn.com.chaochuang.doc.expiredate.domain.SysDeadLineType;
import cn.com.chaochuang.doc.expiredate.service.SysDeadLineTypeService;
import cn.com.chaochuang.doc.remotedocswap.bean.DocSwapEnvelopeEditBean;
import cn.com.chaochuang.doc.remotedocswap.bean.DocSwapEnvelopeShowBean;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapContent;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapEnvelope;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteAttachType;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvRecFlag;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteEnvStatus;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteDocSwapEnvelopeRepository;
import cn.com.chaochuang.doc.remotedocswap.service.RemoteDocSwapAttachService;
import cn.com.chaochuang.doc.remotedocswap.service.RemoteDocSwapContentService;
import cn.com.chaochuang.doc.remotedocswap.service.RemoteDocSwapEnvelopeService;
import cn.com.chaochuang.workflow.def.service.WfFlowService;
import cn.com.chaochuang.workflow.inst.service.WfAuditOpinionService;
import cn.com.chaochuang.workflow.reference.WfApprResult;
import cn.com.chaochuang.workflow.reference.WfBusinessType;


/** 
 * @ClassName: RemoteDocSwapEnvelopeController 
 * @Description: 公文交换——控制跳转
 * @author: chunshu
 * @date: 2017年7月13日 上午9:54:46  
 */
@Controller
@RequestMapping("remotedocswap")
public class RemoteDocSwapEnvelopeController {

    /** 封首存放路径 */
    @Value(value = "${remote.docswap.envelopePath}")
    private String                       	envelopePath;

    @Autowired
    private RemoteDocSwapEnvelopeService 	envelopeService;

    @Autowired
    private RemoteDocSwapContentService  	contentService;

    @Autowired
    private RemoteDocSwapAttachService   	attachService;

    @Autowired
    private ConversionService            	conversionService;

    @Autowired
    private OaDocCodeService       		 	docCodeService;
    
    @Autowired
    private RemoteDocSwapEnvelopeRepository repository;
    
    @Autowired
    private OaDocFileService       			docFileService;
    
    @Autowired
    private WfAuditOpinionService  			auditOpinionService;
    
    @Autowired
    private SysAttachService 	   			docAttachService;
    
    @Autowired
    private LogService             			logService;
    
    @Autowired
    private SysDeadLineTypeService          sysDeadLineTypeService;
    
    @Autowired
    private WfFlowService          			flowService;
    
    @Autowired
    private SysPowerService        			sysPowerService;
    /**
     * 列表页面
     */
    @RequestMapping("{type}/list")
    public ModelAndView list(@PathVariable String type) {
    	ModelAndView mav = new ModelAndView("/doc/remotedocswap/list");
        switch (type) {
        case "send":// 发送查询
        	mav = new ModelAndView("/doc/remotedocswap/send/docSendList");
            break;
        case "receive":// 公文签收
        	mav = new ModelAndView("/doc/remotedocswap/receive/signList");
            break;
        case "docReceive":// 已签收查询
        	mav = new ModelAndView("/doc/remotedocswap/receive/docReceiveList");
        	break;
        case "docReceiveDoing":// 电子公文办理情况查询
        	mav = new ModelAndView("/doc/remotedocswap/receive/docReceiveDoingList");
        	break;
        case "docReceiveFinish":// 归档查询
        	mav = new ModelAndView("/doc/remotedocswap/receive/docReceiveFinishList");
        	break;
        case "statistic":// 公文统计
        	
        	break;
        case "paperDoc":// 纸质公文办理情况查询
        	mav = new ModelAndView("/doc/remotedocswap/paperDoc/paperDocList");
            mav.addObject("receiveFlag", RemoteEnvRecFlag.纸质公文);
        	break;
        default:
            return null;
        }
        return mav;
    }

    /**
     * 列表数据——发送查询
     */
    @RequestMapping("/listDocSend.json")
    @ResponseBody
    public Page docSendListJson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	Page p = new Page();
    	try {
    		SearchBuilder<RemoteDocSwapEnvelope, Long> searchBuilder = new SearchBuilder<RemoteDocSwapEnvelope, Long>(
                    conversionService);
		    searchBuilder.clearSearchBuilder().findSearchParam(request);
		    //查询接收记录的默认条件是：收发标志为0（receiveFlag）
		    searchBuilder.getFilterBuilder().equal("receiveFlag", RemoteEnvRecFlag.发送);
		    
		    if(StringUtils.isNotBlank(sort)){
	        	sort = sort.replace("Show", "");
	        	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
	        }
	        else{
	        	// 排序
			    List<String> orders = new ArrayList<String>();
			    orders.add("id");
			    orders.add("envStatus");
			    orders.add("sendTime");
			    searchBuilder.appendSort(Direction.DESC, orders);
	        }
		    
		    SearchListHelper<RemoteDocSwapEnvelope> listHelper = new SearchListHelper<RemoteDocSwapEnvelope>();
		    listHelper.execute(searchBuilder, envelopeService.getRepository(), page, rows);
		    
		    p.setRows(BeanCopyBuilder.buildList(listHelper.getList(), DocSwapEnvelopeShowBean.class));
		    p.setTotal(listHelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文交换-发送查询-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
    	
        return p;
    }
    
    /**
     * 列表数据——公文签收
     */
    @RequestMapping("/listSign.json")
    @ResponseBody
    public Page signListJson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	Page p = new Page();
    	try {
    		SearchBuilder<RemoteDocSwapEnvelope, Long> searchBuilder = new SearchBuilder<RemoteDocSwapEnvelope, Long>(
                    conversionService);
		    searchBuilder.clearSearchBuilder().findSearchParam(request);
		    //查询接收记录的默认条件是：收发标志为1（receiveFlag）
		    searchBuilder.getFilterBuilder().equal("receiveFlag", RemoteEnvRecFlag.接收);
		    searchBuilder.getFilterBuilder().equal("envStatus", RemoteEnvStatus.未签收);
		    
		    if(StringUtils.isNotBlank(sort)){
	        	sort = sort.replace("Show", "");
	        	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
	        }
	        else{
	        	searchBuilder.appendSort(Direction.DESC, "sendTime");
	        }
		    
		    
		
		    SearchListHelper<RemoteDocSwapEnvelope> listHelper = new SearchListHelper<RemoteDocSwapEnvelope>();
		    listHelper.execute(searchBuilder, envelopeService.getRepository(), page, rows);
		
		    p.setRows(BeanCopyBuilder.buildList(listHelper.getList(), DocSwapEnvelopeShowBean.class));
		    p.setTotal(listHelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文交换-公文签收-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
    	
        return p;
    }
    
    /**
     * 列表数据——已签收查询
     */
    @RequestMapping("/listDocReceive.json")
    @ResponseBody
    public Page docReceiveListJson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	Page p = new Page();
    	try {
    		SearchBuilder<RemoteDocSwapEnvelope, Long> searchBuilder = new SearchBuilder<RemoteDocSwapEnvelope, Long>(
                    conversionService);
		    searchBuilder.clearSearchBuilder().findSearchParam(request);
		    //查询接收记录的默认条件是：收发标志为1（receiveFlag）
		    searchBuilder.getFilterBuilder().equal("receiveFlag", RemoteEnvRecFlag.接收);
		    searchBuilder.getFilterBuilder().equal("envStatus", RemoteEnvStatus.已签收);
		    searchBuilder.getFilterBuilder().isNull("instId");
		    
		    if(StringUtils.isNotBlank(sort)){
	        	sort = sort.replace("Show", "");
	        	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
	        }
	        else{
	        	searchBuilder.appendSort(Direction.DESC, "sendTime");
	        }
		
		    SearchListHelper<RemoteDocSwapEnvelope> listHelper = new SearchListHelper<RemoteDocSwapEnvelope>();
		    listHelper.execute(searchBuilder, envelopeService.getRepository(), page, rows);
		
		    p.setRows(BeanCopyBuilder.buildList(listHelper.getList(), DocSwapEnvelopeShowBean.class));
		    p.setTotal(listHelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文交换-已签收查询-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
    	
        return p;
    }
    
    /**
     * 列表数据——电子公文办理查询
     */
    @RequestMapping("/listDocReceiveDoing.json")
    @ResponseBody
    public Page docReceiveDoingListJson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	Page p = new Page();
    	try {
    		SearchBuilder<RemoteDocSwapEnvelope, Long> searchBuilder = new SearchBuilder<RemoteDocSwapEnvelope, Long>(
                    conversionService);
		    searchBuilder.clearSearchBuilder().findSearchParam(request);
		    //查询接收记录的默认条件是：收发标志为1（receiveFlag）
		    searchBuilder.getFilterBuilder().equal("receiveFlag", RemoteEnvRecFlag.接收);
		    searchBuilder.getFilterBuilder().in("envStatus",
		            new Object[] { RemoteEnvStatus.未签收,RemoteEnvStatus.已签收 });
		    searchBuilder.getFilterBuilder().isNotNull("instId");
		    
		    if(StringUtils.isNotBlank(sort)){
	        	sort = sort.replace("Show", "");
	        	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
	        }
	        else{
	        	searchBuilder.appendSort(Direction.ASC, "limitTime");
			    searchBuilder.appendSort(Direction.DESC, "sendTime");
	        }
		    
		    SearchListHelper<RemoteDocSwapEnvelope> listHelper = new SearchListHelper<RemoteDocSwapEnvelope>();
		    listHelper.execute(searchBuilder, envelopeService.getRepository(), page, rows);
		
		    p.setRows(BeanCopyBuilder.buildList(listHelper.getList(), DocSwapEnvelopeShowBean.class));
		    p.setTotal(listHelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文交换-电子公文办理查询-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
    	
        return p;
    }

    /**
     * 列表数据——纸质公文办理查询
     */
    @RequestMapping("/listPaperDoc.json")
    @ResponseBody
    public Page paperDocListJson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	Page p = new Page();
    	try {
    		SearchBuilder<RemoteDocSwapEnvelope, Long> searchBuilder = new SearchBuilder<RemoteDocSwapEnvelope, Long>(
                    conversionService);
		    searchBuilder.clearSearchBuilder().findSearchParam(request);
		    //查询接收记录的默认条件是：收发标志为3（receiveFlag）
		    searchBuilder.getFilterBuilder().equal("receiveFlag", RemoteEnvRecFlag.纸质公文);
		    //status in（0,1）
		    searchBuilder.getFilterBuilder().in("envStatus",
		            new Object[] { RemoteEnvStatus.未签收,RemoteEnvStatus.未签收 });
		    
		    if(StringUtils.isNotBlank(sort)){
	        	sort = sort.replace("Show", "");
	        	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
	        }
	        else{
	        	searchBuilder.appendSort(Direction.ASC, "limitTime");
			    searchBuilder.appendSort(Direction.DESC, "sendTime");
	        }
		    
		    SearchListHelper<RemoteDocSwapEnvelope> listHelper = new SearchListHelper<RemoteDocSwapEnvelope>();
		    listHelper.execute(searchBuilder, envelopeService.getRepository(), page, rows);
		
		    p.setRows(BeanCopyBuilder.buildList(listHelper.getList(), DocSwapEnvelopeShowBean.class));
		    p.setTotal(listHelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文交换-纸质公文办理查询-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
    	
        return p;
    }
    
    /**
     * 列表数据——归档查询
     */
    @RequestMapping("/listDocReceiveFinish.json")
    @ResponseBody
    public Page docReceiveFinishListJson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
        Page p = new Page();
         
        String title = request.getParameter("title");
        String senderAncestorName = request.getParameter("senderAncestorName");
        String signerName = request.getParameter("signerName");
        String sendBeginTime = request.getParameter("sendBeginTime");
        String sendEndTime = request.getParameter("sendEndTime");
        String limitBeginTime = request.getParameter("limitBeginTime");
        String limitEndTime = request.getParameter("limitEndTime");
        String signBeginDate = request.getParameter("signBeginDate");
        String signEndDate = request.getParameter("signEndDate");
        String documentType = request.getParameter("documentType");
        String envStatus = request.getParameter("envStatus");
        String receiveFlag = request.getParameter("receiveFlag");
        String overTimeFlag = request.getParameter("overTimeFlag");
        Date dt1 = null, dt2 = null,dt3 = null, dt4 = null,dt5 = null, dt6 = null;
        
            try {
            	if (!Tools.isEmptyString(sendBeginTime)) {
            		dt1 = Tools.DATE_MINUTE_FORMAT.parse(sendBeginTime + " 00:00");
            	}
            	if (!Tools.isEmptyString(sendEndTime)) {
                    dt2 = Tools.DATE_MINUTE_FORMAT.parse(sendEndTime + " 23:59");
                }
                if (!Tools.isEmptyString(limitBeginTime)) {
                	dt3 = Tools.DATE_MINUTE_FORMAT.parse(limitBeginTime + " 00:00");
                }
                if (!Tools.isEmptyString(limitEndTime)) {
                	dt4 = Tools.DATE_MINUTE_FORMAT.parse(limitEndTime + " 23:59");
                }
                if (!Tools.isEmptyString(signBeginDate)) {
                	dt5 = Tools.DATE_MINUTE_FORMAT.parse(signBeginDate + " 00:00");
                }
                if (!Tools.isEmptyString(signEndDate)) {
                	dt6 = Tools.DATE_MINUTE_FORMAT.parse(signEndDate + " 23:59");
                }
                p = envelopeService.selectDocReceiveFinishPage(title, senderAncestorName, signerName, dt1,
                		dt2, dt3, dt4, dt5, dt6, documentType, 
                		envStatus, receiveFlag, overTimeFlag, page, rows, sort, order);
			}catch (Exception e) {
    			logService.saveLog(SjType.普通操作, "公文交换-归档查询-列表查询：失败！",LogStatus.失败, request);
    			e.printStackTrace();
    		}
        
        return p;
    }
    
    /**
     * 新增、编辑页面
     */
    @RequestMapping("/edit")
    public ModelAndView editPage(Long id) {
        ModelAndView mav = new ModelAndView("/doc/remotedocswap/edit");

        SysUser currentUser = UserHelper.getCurrentUser();
        if (id != null) {// 编辑
            RemoteDocSwapEnvelope envelope = this.envelopeService.findOne(id);
            RemoteDocSwapContent envelopeContent = this.contentService.getByEnvelopeId(id);
            List docCodes = DocCodeUtil.splitDocCode(envelopeContent.getDocNumber());
            if (Tools.isNotEmptyList(docCodes)) {
                mav.addObject("docNumbers", docCodes);
            }

            mav.addObject("envelope", envelope);
            mav.addObject("envelopeContent", envelopeContent);
            // 读取附件
            mav.addObject("attachMap", attachService.getAttachMap(envelopeContent.getId(), RemoteAttachType.其他));
        } else {// 新增
            RemoteDocSwapContent envelopeContent = new RemoteDocSwapContent();
            envelopeContent.setPrintOrg(currentUser.getDepartment().getParentDepartment().getDeptName());
            envelopeContent.setContactName(currentUser.getUserName());
            envelopeContent.setContactPhone(currentUser.getMobile());
            mav.addObject("envelopeContent", envelopeContent);

            Map<String, String> deadLineTypeMap = sysDeadLineTypeService.getSysDeadLineTypeMap();
            RemoteDocSwapEnvelope envelope = new RemoteDocSwapEnvelope();
//            envelope.setSecondaryUnitIdentifier("007665865-N-1");
//            envelope.setSecondaryUnitIdentifierName("梧州市政府");
            
            // 读取模板
            List<DocCate> docCodeList = docCodeService.selectCodeTypeGroupByDocCate(WfBusinessType.发文);
            
            mav.addObject("docCodeList", docCodeList);
            
            mav.addObject("envelope", envelope);
            mav.addObject("deadLineTypeMap", deadLineTypeMap);
        }

        return mav;
    }

    @RequestMapping({"/getCodeNameByDocCate.json"})
    @ResponseBody
    public List<OaDocCode> getCodeNameByDocCate(DocCate docCate) { 
    	return docCodeService.selectDocCateAndCodeTypeOrderByCodeSortAsc(docCate,WfBusinessType.发文); 
    	}
    
    /**
     * 保存数据
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(DocSwapEnvelopeEditBean bean, HttpServletRequest request, HttpServletResponse response) {

        try {
            if (bean != null) {
                bean.setEditType(DocSwapEnvelopeEditBean.EDIT_TYPE_APPLY);
//                bean.setEnvStatus(RemoteEnvStatus.未反馈);
            }
            boolean result = envelopeService.createMultiRemoteDocSwap(bean);

            return new ReturnInfo("1", null, "暂存成功！");
        } catch (Exception e) {
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    /**
     * 公文交换详情
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/detailDocSwap")
    public ModelAndView docSwapDetail(Long id,String queryFlag) {
        ModelAndView mav = null;
        
        //签收的页面
        if("sign".equals(queryFlag)){
			mav= new ModelAndView("/doc/remotedocswap/receive/signDetail");
		}else if("paperDoc".equals(queryFlag)){//纸质公文的页面
			mav= new ModelAndView("/doc/remotedocswap/paperDoc/paperDocDetail");
			if (id != null) {
	            mav.addObject("envelopeMap",envelopeService.findEnvelopeMapByLinkIdAndType(id));
	        }
			RemoteDocSwapEnvelope envelope = this.repository.findOne(id);
			if(null != envelope){
				if(!Tools.isEmptyString(envelope.getInstId())){
		 			// 基本信息
		 			OaDocFile obj = this.docFileService.findOne(envelope.getInstId());
		 			mav.addObject("obj", obj);
		 			// 读取附件
		 			mav.addObject("docFileAttachMap", docAttachService.getAttachMap(envelope.getInstId(), OaDocFile.DOC_ATTACH_OWNER_DOC));
		 			// 读取审批意见
		 			mav.addObject("opinionList", auditOpinionService.findAuditOpinionsByFlowInstIdAndResult(obj.getFlowInstId(), WfApprResult.通过));
		 		}
			}
			
		}else{//已签收查询、电子公文办理查询、归档查询、发送查询的页面
		 	mav= new ModelAndView("/doc/remotedocswap/docSwapDetail");
		 	if (id != null) {
	            mav.addObject("envelopeMap",envelopeService.findEnvelopeMapByLinkIdAndType(id));
	        }
		 	RemoteDocSwapEnvelope envelope = this.repository.findOne(id);
		 	if(null != envelope){
		 		if(!Tools.isEmptyString(envelope.getInstId())){
		 			// 基本信息
		 			OaDocFile obj = this.docFileService.findOne(envelope.getInstId());
		 			mav.addObject("obj", obj);
		 			// 读取附件
		 			mav.addObject("docFileAttachMap", docAttachService.getAttachMap(envelope.getInstId(), OaDocFile.DOC_ATTACH_OWNER_DOC));
		 			// 读取审批意见
		 			mav.addObject("opinionList", auditOpinionService.findAuditOpinionsByFlowInstIdAndResult(obj.getFlowInstId(), WfApprResult.通过));
		 		}
			}
		}
        
        
        if (id != null) {
            mav.addAllObjects(envelopeService.selectRemoteDocSwapEnvelopeDetail(id));
        }
        
        return mav;
    }

    /**
     * 删除公文交换
     */
    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo del(Long[] ids, HttpServletRequest request) {
        try {
            for (Long delId : ids) {
                this.envelopeService.deleteRemoteDocSwapEnvelope(delId);
            }
            return new ReturnInfo("1", null, "删除成功！");
        } catch (Exception e) {
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
    
    /**
     * 公文反馈控制跳转
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/detailFeedBackPage")
    public ModelAndView detailFeedBackPage(Long id,String type,String returnType) {
        ModelAndView mav = null;
        SysUser currentUser = UserHelper.getCurrentUser();
        //撤销
        if("undoEnvelope".equals(type)){
			mav= new ModelAndView("/doc/remotedocswap/feedback/feedBack_cxgw");
		}else if("invalidEnvelope".equals(type)){//作废
			mav= new ModelAndView("/doc/remotedocswap/feedback/feedBack_zfgw");
		}else if("feedBackDialog".equals(type)){//修改时限
			mav= new ModelAndView("/doc/remotedocswap/feedback/feedBack_xgsx");
		}else if("sendDocSwapInner".equals(type)){//转内部公文
			mav = new ModelAndView("/doc/receivefile/docSwapEdit");
			//保存公文交换的附件到系统附件表中
			mav.addObject("sysAttachList", contentService.copyDocSwapAttachToSysAttach(id));
	        mav.addObject("flowList", flowService.selectCanWorkFlow(WfBusinessType.收文));
	        boolean isJy = sysPowerService.selectPowerByPowerNameAndCurrentUser("机要收文编号");
        	if(isJy){
        		mav.addObject("isJy", "1");
        	}else{
        		mav.addObject("isJy", "0");
        	}

		}else if("toReadEnvelope".equals(type)){//阅件传阅
			mav= new ModelAndView("/doc/remotedocswap/feedback/feedBack_ysxx");
			mav.addObject("returnType",returnType);
		}else if("sendFinishInfo".equals(type)){//办结反馈
			mav= new ModelAndView("/doc/remotedocswap/feedback/sendFinishInfo");
			mav.addObject("returnType",returnType);
		}else if("sendContentInfo".equals(type)){//正文反馈
			mav= new ModelAndView("/doc/remotedocswap/feedback/sendContentInfo");
			mav.addObject("returnType",returnType);
			// 读取模板
            List<DocCate> docCodeList = docCodeService.selectCodeTypeGroupByDocCate(WfBusinessType.发文);
            mav.addObject("docCodeList", docCodeList);
		}else if("returnEnvelope".equals(type)){//退回
			mav= new ModelAndView("/doc/remotedocswap/feedback/feedBack_tw");
		}else if("receiveDocSwap".equals(type)){//签收
			mav= new ModelAndView("/doc/remotedocswap/feedback/signInfo");
		}else{
		 	 
		}
        
    	if (id != null) {
			mav.addAllObjects(envelopeService.selectRemoteDocSwapEnvelopeDetail(id));
        }
        mav.addObject("currentUser",currentUser);
		mav.addObject("currentDate",new Date());
		
        return mav;
    }
    
    
    /**
     * 公文反馈发送
     */
    @RequestMapping("/send.json")
    @ResponseBody
    public ReturnInfo send(DocSwapEnvelopeEditBean bean, HttpServletRequest request, HttpServletResponse response) {

        try {
             
            boolean result = envelopeService.createRemoteDocSwapFeedback(bean);
 
            return new ReturnInfo("1", null, "发送成功！");
        } catch (Exception e) {
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
    
    /**
	 * 签收公文交换
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping("/saveReceiveDocSwap.json")
    @ResponseBody
	public ReturnInfo receiveDocSwap(HttpServletRequest request, HttpServletResponse response, DocSwapEnvelopeEditBean bean){
		//当前用户
		SysUser currentUser = (SysUser) UserTool.getInstance().getCurrentUser();
		//公文交换流水号
		if(bean.getEnvelopeIds()!=null){
			//批量签收
			envelopeService.signDocSwapList(bean,currentUser);
		}else{
			envelopeService.signDocSwap(bean,currentUser);
		}
		return new ReturnInfo("1", null, "签收成功！");
	}
    
}
