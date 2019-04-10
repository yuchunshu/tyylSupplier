package cn.com.chaochuang.doc.event.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.syspower.service.SysPowerService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.common.util.UserHelper;
import cn.com.chaochuang.doc.event.bean.OaDocFileEditBean;
import cn.com.chaochuang.doc.event.bean.OuterDocFileEditBean;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.domain.OaOuterDocFile;
import cn.com.chaochuang.doc.event.reference.DocCate;
import cn.com.chaochuang.doc.event.reference.OuterDocStatus;
import cn.com.chaochuang.doc.event.service.OaDocFileService;
import cn.com.chaochuang.doc.event.service.OaOuterDocFileFeedBackService;
import cn.com.chaochuang.doc.event.service.OaOuterDocFileService;
import cn.com.chaochuang.doc.expiredate.service.SysDeadLineTypeService;
import cn.com.chaochuang.workflow.def.service.WfFlowService;
import cn.com.chaochuang.workflow.inst.service.WfAuditOpinionService;
import cn.com.chaochuang.workflow.reference.WfApprResult;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * @author yuchunshu 2017.11.10
 *
 */
@Controller
@RequestMapping("doc/outer")
public class OuterDocController {

    @Autowired
    protected ConversionService    			conversionService;
    
    @Autowired
    private LogService             			logService;

    @Autowired
    private OaOuterDocFileService  		    outerDocFileService;
    
    @Autowired
    private OaOuterDocFileFeedBackService   feedBackService;

    @Autowired
    private SysAttachService 	   			docAttachService;
    
    @Autowired
    private WfFlowService          			flowService;
    
    @Autowired
    private SysPowerService        			sysPowerService;
    
    @Autowired
    private OaDocFileService       			docFileService;
    
    @Autowired
    private WfAuditOpinionService  			auditOpinionService;
    
    @Autowired
    private SysDeadLineTypeService          sysDeadLineTypeService;
    /**
     * 外单位来文公文列表（未签收）
     */
    @RequestMapping("/listSign")
    public ModelAndView signList() {
        ModelAndView mav = new ModelAndView("/doc/outer/signList");
        return mav;
    }

    @RequestMapping(value = "listSign.json")
    @ResponseBody
    public Page signListjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	Page p = new Page();
        try {
        	SearchBuilder<OaOuterDocFile, Long> searchBuilder = new SearchBuilder<OaOuterDocFile, Long>(conversionService);
            searchBuilder.clearSearchBuilder().findSearchParam(request);
            
        	searchBuilder.getFilterBuilder().equal("receiveDeptId", UserTool.getInstance().getCurrentUserDepartmentId());
            searchBuilder.getFilterBuilder().equal("status", OuterDocStatus.未签收);
            
            if(StringUtils.isNotBlank(sort)){
            	sort = sort.replace("Show", "");
            	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
            }
            else{
            	searchBuilder.appendSort(Direction.DESC, "sendTime");
            }
            SearchListHelper<OaOuterDocFile> listhelper = new SearchListHelper<OaOuterDocFile>();
            listhelper.execute(searchBuilder, outerDocFileService.getRepository(), page, rows);
            
            p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), OaOuterDocFile.class));
            p.setTotal(listhelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文传输-公文签收-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;
    }

    /**
     * 外单位来文公文列表（已签收）
     */
    @RequestMapping("/listSigned")
    public ModelAndView signedList() {
        ModelAndView mav = new ModelAndView("/doc/outer/signedList");
        return mav;
    }

    @RequestMapping(value = "listSigned.json")
    @ResponseBody
    public Page signedListjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	Page p = new Page();
        try {
        	SearchBuilder<OaOuterDocFile, Long> searchBuilder = new SearchBuilder<OaOuterDocFile, Long>(conversionService);
            searchBuilder.clearSearchBuilder().findSearchParam(request);
            
        	searchBuilder.getFilterBuilder().equal("receiveDeptId", UserTool.getInstance().getCurrentUserDepartmentId());
            searchBuilder.getFilterBuilder().equal("status", OuterDocStatus.已签收);
            searchBuilder.getFilterBuilder().isNull("insideId");
            
            if(StringUtils.isNotBlank(sort)){
            	sort = sort.replace("Show", "");
            	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
            }
            else{
            	searchBuilder.appendSort(Direction.DESC, "sendTime");
            }
            SearchListHelper<OaOuterDocFile> listhelper = new SearchListHelper<OaOuterDocFile>();
            listhelper.execute(searchBuilder, outerDocFileService.getRepository(), page, rows);
            
            p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), OaOuterDocFile.class));
            p.setTotal(listhelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文传输-已签收查询-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;
    }
    
    /**
     * 发送查询
     */
    @RequestMapping("/listSend")
    public ModelAndView sendList() {
        ModelAndView mav = new ModelAndView("/doc/outer/sendList");
        return mav;
    }

    @RequestMapping(value = "listSend.json")
    @ResponseBody
    public Page sendListjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	Page p = new Page();
        try {
        	SearchBuilder<OaOuterDocFile, Long> searchBuilder = new SearchBuilder<OaOuterDocFile, Long>(conversionService);
            searchBuilder.clearSearchBuilder().findSearchParam(request);
        	searchBuilder.getFilterBuilder().equal("senderDeptId", UserTool.getInstance().getCurrentUserDepartmentId());
            
            if(StringUtils.isNotBlank(sort)){
            	sort = sort.replace("Show", "");
            	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
            }
            else{
            	searchBuilder.appendSort(Direction.DESC, "sendTime");
            }
            SearchListHelper<OaOuterDocFile> listhelper = new SearchListHelper<OaOuterDocFile>();
            listhelper.execute(searchBuilder, outerDocFileService.getRepository(), page, rows);
            
            p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), OaOuterDocFile.class));
            p.setTotal(listhelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文传输-发送列表-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;
    }
    
    
    /**
     * 公文办理查询
     */
    @RequestMapping("/listReceiveDoing")
    public ModelAndView docReceiveDoingList() {
        ModelAndView mav = new ModelAndView("/doc/outer/docReceiveDoingList");
        return mav;
    }

    @RequestMapping(value = "listReceiveDoing.json")
    @ResponseBody
    public Page docReceiveDoingListjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	Page p = new Page();
        try {
        	SearchBuilder<OaOuterDocFile, Long> searchBuilder = new SearchBuilder<OaOuterDocFile, Long>(conversionService);
            searchBuilder.clearSearchBuilder().findSearchParam(request);
            
        	searchBuilder.getFilterBuilder().equal("receiveDeptId", UserTool.getInstance().getCurrentUserDepartmentId());
        	
            searchBuilder.getFilterBuilder().in("status",
		            new Object[] { OuterDocStatus.未签收,OuterDocStatus.已签收 });
		    searchBuilder.getFilterBuilder().isNotNull("insideId");
            
            if(StringUtils.isNotBlank(sort)){
            	sort = sort.replace("Show", "");
            	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
            }
            else{
            	searchBuilder.appendSort(Direction.DESC, "sendTime");
            }
            SearchListHelper<OaOuterDocFile> listhelper = new SearchListHelper<OaOuterDocFile>();
            listhelper.execute(searchBuilder, outerDocFileService.getRepository(), page, rows);
            
            p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), OaOuterDocFile.class));
            p.setTotal(listhelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文传输-公文办理查询-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;
    }
    
    /**
     * 归档查询
     */
    @RequestMapping("/listDocReceiveFinish")
    public ModelAndView docReceiveFinishList() {
        ModelAndView mav = new ModelAndView("/doc/outer/docReceiveFinishList");
        return mav;
    }

    @RequestMapping(value = "listDocReceiveFinish.json")
    @ResponseBody
    public Page docReceiveFinishListjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	Page p = new Page();
        try {
        	SearchBuilder<OaOuterDocFile, Long> searchBuilder = new SearchBuilder<OaOuterDocFile, Long>(conversionService);
            searchBuilder.clearSearchBuilder().findSearchParam(request);
            
        	searchBuilder.getFilterBuilder().equal("receiveDeptId", UserTool.getInstance().getCurrentUserDepartmentId());
        	
            searchBuilder.getFilterBuilder().in("status",
		            new Object[] { OuterDocStatus.不办理,OuterDocStatus.办结传阅,OuterDocStatus.办结归档,OuterDocStatus.已作废
		            		,OuterDocStatus.已办结,OuterDocStatus.已撤销,OuterDocStatus.转阅件,OuterDocStatus.退文});
        	
            if(StringUtils.isNotBlank(sort)){
            	sort = sort.replace("Show", "");
            	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
            }
            else{
            	searchBuilder.appendSort(Direction.DESC, "sendTime");
            }
            SearchListHelper<OaOuterDocFile> listhelper = new SearchListHelper<OaOuterDocFile>();
            listhelper.execute(searchBuilder, outerDocFileService.getRepository(), page, rows);
            
            p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), OaOuterDocFile.class));
            p.setTotal(listhelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文传输-归档查询-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;
    }
    
    /**
     * 公文传输详情
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/detailOuter")
    public ModelAndView outerDetail(String id,String queryFlag) {
        ModelAndView mav = null;
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();

        //未签收的页面
        if("sign".equals(queryFlag)){
			mav= new ModelAndView("/doc/outer/signDetail");
		}else if("other".equals(queryFlag)){////已签收查询、电子公文办理查询、归档查询、发送查询的详情页面
			mav= new ModelAndView("/doc/outer/otherDetail");
			if (id != null) {//反馈情况
	            mav.addObject("feedBackMap",feedBackService.findOuterDocMapByOuterId(id));
	        }
			
		}
        
        OaOuterDocFile outer = outerDocFileService.findOne(id);
        
        if(!Tools.isEmptyString(outer.getInsideId())){
 			// 基本信息
 			OaDocFile obj = this.docFileService.findOne(outer.getInsideId());
 			mav.addObject("obj", obj);
 			// 读取附件
 			mav.addObject("docFileAttachMap", docAttachService.getAttachMap(outer.getInsideId(), OaDocFile.DOC_ATTACH_OWNER_DOC));
 			// 读取审批意见
 			mav.addObject("opinionList", auditOpinionService.findAuditOpinionsByFlowInstIdAndResult(obj.getFlowInstId(), WfApprResult.通过));
 		}
        
        mav.addObject("outer", outer);
        mav.addObject("currUser", user);
        mav.addObject("attachMap", docAttachService.getAttachMap(outer.getFileId(), OaDocFile.DOC_ATTACH_OWNER_DOC));

        return mav;
    }
    
    /**
	 * 签收-外单位来文
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping("/dealOuterSign.json")
    @ResponseBody
	public ReturnInfo dealOuterSign(HttpServletRequest request, HttpServletResponse response, OuterDocFileEditBean bean){
		
    	//当前用户
		SysUser currentUser = (SysUser) UserTool.getInstance().getCurrentUser();
//    	if(!Tools.isEmptyString(ids)){
//			//批量签收
//			outerDocFileService.signOuterDocList(ids);
//		}
    	if(bean.getIds()!=null){
			//批量签收
    		outerDocFileService.signOuterDocList(bean,currentUser);
		}else{
			outerDocFileService.signOuterDoc(bean,currentUser);
		}
    	
		return new ReturnInfo("1", null, "签收成功！");
	}
    
    /**
     * 系统外发编辑
     */
	@RequestMapping("/editOuterDoc")
    public ModelAndView editOuterDoc(String id) {
        ModelAndView mav = new ModelAndView("/doc/outer/edit");;
        SysUser currentUser = UserHelper.getCurrentUser();
        OaDocFile obj = docFileService.findOne(id);
        Map<String, String> deadLineTypeMap = sysDeadLineTypeService.getSysDeadLineTypeMap();

        mav.addObject("obj",obj);
        mav.addObject("currentUser",currentUser);
		mav.addObject("currentDate",new Date());
        mav.addObject("deadLineTypeMap", deadLineTypeMap);

        return mav;
    }
    
    /**
     * 系统外发操作
     */
    @RequestMapping(value = "saveOuterDoc", method = RequestMethod.POST)
    @ResponseBody
    public ReturnInfo saveOuterDoc(OaDocFileEditBean bean,HttpServletRequest request, HttpServletResponse response) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        bean.setCurrentUser(user);

        outerDocFileService.saveOuterDocFile(bean);
        return new ReturnInfo("1", null, "处理完成！");
    }
    
    /**
     * 公文反馈控制跳转
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/detailFeedBackPage")
    public ModelAndView detailFeedBackPage(String id,String type,String returnType) {
        ModelAndView mav = null;
        SysUser currentUser = UserHelper.getCurrentUser();
        
        if("undo".equals(type)){//撤销
			mav= new ModelAndView("/doc/outer/feedback/feedBack_undo");
		}else if("invalid".equals(type)){//作废
			mav= new ModelAndView("/doc/outer/feedback/feedBack_invalid");
		}else if("sendInner".equals(type)){//转内部公文
			mav = new ModelAndView("/doc/outer/inner/edit");
			//保存公文传输的附件到系统附件表中
			mav.addObject("sysAttachList", outerDocFileService.copyAttach(id));
	        mav.addObject("flowList", flowService.selectCanWorkFlow(WfBusinessType.收文));
	        boolean isJy = sysPowerService.selectPowerByPowerNameAndCurrentUser("机要收文编号");
        	if(isJy){
        		mav.addObject("isJy", "1");
        	}else{
        		mav.addObject("isJy", "0");
        	}
		}else if("toRead".equals(type)){//阅件传阅
			mav= new ModelAndView("/doc/outer/feedback/feedBack_toRead");
			mav.addObject("returnType",returnType);
		}else if("sendFinish".equals(type)){//办结反馈
			mav= new ModelAndView("/doc/outer/feedback/feedBack_sendFinish");
			mav.addObject("returnType",returnType);
		}else if("return".equals(type)){//退回
			mav= new ModelAndView("/doc/outer/feedback/feedBack_return");
		}else if("sign".equals(type)){//签收
			mav= new ModelAndView("/doc/outer/feedback/signInfo");
		}else{
		 	 
		}
        
    	if (id != null) {
    		mav.addObject("obj", outerDocFileService.findOne(id));
        }
        mav.addObject("currentUser",currentUser);
		mav.addObject("currentDate",new Date());
		
        return mav;
    }
    
    /**
     * 公文反馈保存
     */
    @RequestMapping("/saveFeedBack.json")
    @ResponseBody
    public ReturnInfo saveFeedBack(OuterDocFileEditBean bean, HttpServletRequest request, HttpServletResponse response) {

        try {
             
            boolean result = feedBackService.saveFeedBack(bean);
 
            return new ReturnInfo("1", null, "发送成功！");
        } catch (Exception e) {
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
}
