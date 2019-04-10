/*
 * FileName:    NoticeController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.event.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.desktop.service.UserAgentService;
import cn.com.chaochuang.common.dictionary.DictionaryPool;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.reference.AttachFlag;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.syspower.domain.SysRole;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.event.bean.FileShowBean;
import cn.com.chaochuang.doc.event.bean.OaDocFileDispatchBean;
import cn.com.chaochuang.doc.event.bean.OaDocFileEditBean;
import cn.com.chaochuang.doc.event.bean.OaDocFileFromLetterBean;
import cn.com.chaochuang.doc.event.bean.OaDocFileQueryBean;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.reference.DateFlag;
import cn.com.chaochuang.doc.event.reference.FileStatusFlag;
import cn.com.chaochuang.doc.event.reference.OpenFlag;
import cn.com.chaochuang.doc.event.service.DocEventService;
import cn.com.chaochuang.doc.event.service.OaDocFileService;
import cn.com.chaochuang.doc.remotedocswap.service.DocStatisticService;
import cn.com.chaochuang.doc.template.domain.DocTemplate;
import cn.com.chaochuang.doc.template.service.DocTemplateService;
import cn.com.chaochuang.oa.mail.bean.MailEditBean;
import cn.com.chaochuang.oa.mail.reference.EmailType;
import cn.com.chaochuang.oa.mail.reference.MailStatus;
import cn.com.chaochuang.oa.mail.service.EmMainService;
import cn.com.chaochuang.oa.message.app.reference.MesType;
import cn.com.chaochuang.oa.message.app.service.AppMessageService;
import cn.com.chaochuang.oa.workingday.domain.WorkingDay;
import cn.com.chaochuang.workflow.event.bean.TaskShowBean;
import cn.com.chaochuang.workflow.inst.bean.InstCommonShowBean;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfAuditOpinionService;
import cn.com.chaochuang.workflow.inst.service.WfFlowInstService;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfApprResult;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfDealType;
import cn.com.chaochuang.workflow.reference.WfInstStatus;
import cn.com.chaochuang.workflow.util.WorkflowUtils;

/**
 * @author hzr 2016.6.6
 *
 */
@Controller
@RequestMapping("doc/event")
public class DocEventController {

    @Autowired
    private EmMainService          emMainService;

    @Autowired
    protected ConversionService    conversionService;
    
    @Autowired
    private LogService             logService;

    @Autowired
    private DocEventService        docEventService;

    @Autowired
    private OaDocFileService       docFileService;

    @Autowired
    private AppMessageService      appMessageService;

    @Autowired
    protected UserAgentService     userAgentService;

    @Autowired
    protected DocTemplateService   docTemplateService;

    @Autowired
    private WfNodeInstService      nodeInstService;
    
    @Autowired
    private WfFlowInstService      flowInstService;

    @Autowired
    private WfAuditOpinionService  auditOpinionService;

    @Autowired
    private DocStatisticService    docStatisticService;

    @Autowired
    private SysUserService         sysUserService;
    
    @RequestMapping("/listTmp")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/doc/docevent/tmplist");
        return mav;
    }

    @RequestMapping("/listTmp.json")
    @ResponseBody
    public Page tmplistjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
        SearchBuilder<OaDocFile, String> searchBuilder = new SearchBuilder<OaDocFile, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("status", FileStatusFlag.暂存);
        searchBuilder.getFilterBuilder().equal("creatorId", UserTool.getInstance().getCurrentUserId());
        
        if(StringUtils.isNotBlank(sort)){
        	sort = sort.replace("Show", "");
        	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
        }
        else{
        	searchBuilder.appendSort(Direction.DESC, "createDate");
        }
        
        SearchListHelper<OaDocFile> listhelper = new SearchListHelper<OaDocFile>();
        listhelper.execute(searchBuilder, docFileService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), FileShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }


    /**查询自己拟稿的文*/
    @RequestMapping("/querylist")
    public ModelAndView queryList() {
        ModelAndView mav = new ModelAndView("/doc/docevent/querylist");
        return mav;
    }

    @RequestMapping("/querylist.json")
    @ResponseBody
    public Page querylistjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
    	return getDocDataPages(user.getId(), null, page, rows, request,sort,order);
    }

    private Page getDocDataPages(Long userId, OpenFlag openFlag, Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
        String title = request.getParameter("title");
        String fileCode = request.getParameter("fileCode");
        String docCode = request.getParameter("docCode");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date dt1 = null, dt2 = null;
        Page p = new Page();
        try {
            if (StringUtils.isNotBlank(fromDate)) {
                dt1 = df.parse(fromDate + " 00:00");
            }
            if (StringUtils.isNotBlank(toDate)) {
                dt2 = df.parse(toDate + " 23:59");
            }
            OaDocFileQueryBean bean = new OaDocFileQueryBean();
            if (StringUtils.isNotBlank(title)) {
                bean.setTitle("%" + title + "%");
            }
            if (StringUtils.isNotBlank(fileCode)) {
                bean.setFileCode("%" + fileCode + "%");
            }
            if (StringUtils.isNotBlank(docCode)) {
                bean.setDocCode("%" + docCode + "%");
            }
            if (openFlag != null) {
            	bean.setOpenFlag(openFlag);
            }
            bean.setCreateDateBegin(dt1);
            bean.setCreateDateEnd(dt2);
            if (userId != null) {
            	bean.setCreatorId(userId);
            }

            org.springframework.data.domain.Page<OaDocFile> data = docFileService.selectDocFilesPage(bean, page, rows, sort, order);
            
            if (data != null) {
                p.setRows(BeanCopyBuilder.buildList(data.getContent(), FileShowBean.class));
                p.setTotal(data.getTotalElements());
            }
        } catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文查询-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;
    }

    /**公开查询的文*/
    @RequestMapping("/opendoclist")
    public ModelAndView openDocList() {
        ModelAndView mav = new ModelAndView("/doc/docevent/opendoclist");
        return mav;
    }

    @RequestMapping("/listOpendoc.json")
    @ResponseBody
    public Page openDocListJson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	return getDocDataPages(null, OpenFlag.公开, page, rows, request, sort, order);
    }

    /**
     * 待办公文列表
     */
    @RequestMapping("/tasklist")
    public ModelAndView taskList() {
        ModelAndView mav = new ModelAndView("/doc/docevent/tasklist");
        return mav;
    }

    @RequestMapping(value = "listTask.json")
    @ResponseBody
    public Page taskListjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	Page p = new Page();
    	Object[] wfBusinessType = {WfBusinessType.收文,WfBusinessType.发文,WfBusinessType.便函};
        try {
        	SearchBuilder<WfNodeInst, Long> searchBuilder = new SearchBuilder<WfNodeInst, Long>(conversionService);
            searchBuilder.clearSearchBuilder().findSearchParam(request);
            String title = request.getParameter("title");
            String sncode = request.getParameter("sncode");
            String preDealerName = request.getParameter("preDealerName");
            String superviseFlag = request.getParameter("superviseFlag");
            String businessType = request.getParameter("businessType");
            if (StringUtils.isNotBlank(title)) {
                searchBuilder.getFilterBuilder().like("flowInst.title", title.trim() + "%");
            }
            if (StringUtils.isNotBlank(sncode)) {
            	searchBuilder.getFilterBuilder().like("flowInst.sncode", sncode.trim() + "%");
            }
            if (StringUtils.isNotBlank(preDealerName)) {
            	searchBuilder.getFilterBuilder().like("preDealer.userName", preDealerName.trim() + "%");
            }
            if (StringUtils.isNotBlank(businessType)){
            	if(businessType.equals(WfBusinessType.收文.getKey())){
            		searchBuilder.getFilterBuilder().equal("curNode.flow.flowType", WfBusinessType.收文);
            	}else if(businessType.equals(WfBusinessType.发文.getKey())){
            		searchBuilder.getFilterBuilder().equal("curNode.flow.flowType", WfBusinessType.发文);
            	}else{
            		searchBuilder.getFilterBuilder().equal("curNode.flow.flowType", WfBusinessType.便函);
            	}
            } else{
            	searchBuilder.getFilterBuilder().in("curNode.flow.flowType",wfBusinessType);
            }
            SysUser user = this.sysUserService.findOne(Long.parseLong(UserTool.getInstance().getCurrentUserId()));
            Set<SysRole> roles = user.getRoles();
            boolean flag = true;
            for (SysRole role : roles) {
				if (role.getRoleName().equals("系统管理员")) {
					flag = false;
				}
			}
            if(flag){//系统管理员角色查看所有的待办公文
            	searchBuilder.getFilterBuilder().equal("dealerId", UserTool.getInstance().getCurrentUserId());
            }
            searchBuilder.getFilterBuilder().equal("instStatus", WfInstStatus.在办);
            searchBuilder.getFilterBuilder().notEqual("curNodeId", WorkflowUtils.NODE_READ);
            
            if(StringUtils.isNotBlank(sort)){
            	sort = sort.replace("Show", "");
            	if ("title".equals(sort)) {
            		sort = "flowInst.title";
                }else if ("startTime".equals(sort)) {
            		sort = "flowInst.startTime";
                }else if ("sncode".equals(sort)) {
            		sort = "flowInst.sncode";
                }else if ("restTime".equals(sort)) {
                	sort = "flowInst.expiryDate";
                }
            	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
            }
            else{
            	searchBuilder.appendSort(Direction.DESC, "arrivalTime");
            }
            SearchListHelper<WfNodeInst> listhelper = new SearchListHelper<WfNodeInst>();
            listhelper.execute(searchBuilder, nodeInstService.getRepository(), page, rows);
            
            p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), TaskShowBean.class));
            p.setTotal(listhelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "待办公文-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;
    }


    /**
     * 待阅公文列表
     */
    @RequestMapping("/readlist")
    public ModelAndView readList() {
        ModelAndView mav = new ModelAndView("/doc/docevent/readlist");
        return mav;
    }

    @RequestMapping(value = "listRead.json")
    @ResponseBody
    public Page readListjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	Page p = new Page();
    	try {
    		SearchBuilder<WfNodeInst, Long> searchBuilder = new SearchBuilder<WfNodeInst, Long>(conversionService);
            searchBuilder.clearSearchBuilder().findSearchParam(request);
            String title = request.getParameter("title");
            String sncode = request.getParameter("sncode");
            String preDealerName = request.getParameter("preDealerName");
            String superviseFlag = request.getParameter("superviseFlag");
            if (StringUtils.isNotBlank(title)) {
                searchBuilder.getFilterBuilder().like("flowInst.title", title.trim() + "%");
            }
            if (StringUtils.isNotBlank(sncode)) {
            	searchBuilder.getFilterBuilder().like("flowInst.sncode", sncode.trim() + "%");
            }
            if (StringUtils.isNotBlank(preDealerName)) {
            	searchBuilder.getFilterBuilder().like("preDealer.userName", preDealerName.trim() + "%");
            }
            searchBuilder.getFilterBuilder().equal("dealerId", UserTool.getInstance().getCurrentUserId());
            searchBuilder.getFilterBuilder().equal("instStatus", WfInstStatus.在办);
            searchBuilder.getFilterBuilder().equal("curNodeId", WorkflowUtils.NODE_READ);

            if(StringUtils.isNotBlank(sort)){
            	sort = sort.replace("Show", "");
            	if ("title".equals(sort)) {
            		sort = "flowInst.title";
                }else if ("startTime".equals(sort)) {
            		sort = "flowInst.startTime";
                }
            	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
            }
            else{
            	searchBuilder.appendSort(Direction.DESC, "arrivalTime");
            }
            SearchListHelper<WfNodeInst> listhelper = new SearchListHelper<WfNodeInst>();
            listhelper.execute(searchBuilder, nodeInstService.getRepository(), page, rows);
            
            p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), TaskShowBean.class));
            p.setTotal(listhelper.getCount());
		} catch (Exception e) {
			logService.saveLog(SjType.普通操作, "待阅公文-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;
    }


    /** 流程监控（所有文） */
    @RequestMapping("/docmonitor")
    public ModelAndView flowMonitor() {
        ModelAndView mav = new ModelAndView("/doc/docevent/docmonitor");
        return mav;
    }

    @RequestMapping("/listDocMonitor.json")
    @ResponseBody
    public Page docMonitorJson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
        String title = request.getParameter("title");
        String businessType = request.getParameter("businessType");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dt1=null, dt2=null;
        Page p = new Page();
        try {
            if(StringUtils.isNotBlank(fromDate)) {
                dt1 = df.parse(fromDate+" 00:00");
            }
            if(StringUtils.isNotBlank(toDate)) {
                dt2 = df.parse(toDate+" 23:59");
            }
            OaDocFileQueryBean bean = new OaDocFileQueryBean();
            bean.setTitle(title);
            bean.setBusinessType(businessType);
            bean.setCreateDateBegin(dt1);
            bean.setCreateDateEnd(dt2);

            p = docEventService.selectMylinkDocs(bean, page, rows, true,sort,order);

            //获取在办公文当前的办理人姓名（可能有多人）
            List<InstCommonShowBean> dataList = p.getRows();
            List<String> entityIds = new ArrayList();
            for(InstCommonShowBean ic: dataList){
            	entityIds.add(ic.getEntityId());
            }

            List<WfNodeInst> nlist = new ArrayList<WfNodeInst>();
            if(entityIds != null && entityIds.size() > 0){
            	nlist = findNodeInstByEntityIds(entityIds.toArray());
            }
            
            for(InstCommonShowBean obj: dataList){
            	for(WfNodeInst node: nlist) {
            		if (obj.getEntityId().equals(node.getFlowInst().getEntityId())) {
            			if (obj.getDealerName() == null || obj.getDealerName().trim().equals("")) {
            				obj.setDealerName(node.getDealer().getUserName());
            			} else {
            				obj.setDealerName(obj.getDealerName() + "，" + node.getDealer().getUserName());
            			}
            		}
            	}
            }
        } catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文监控-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}

        return p;
    }

    //根据公文实体IDs取得在办的环节实例列表
    private List<WfNodeInst> findNodeInstByEntityIds(Object[] entityIds) {
        SearchBuilder<WfNodeInst, String> searchBuilder = new SearchBuilder<WfNodeInst, String>(conversionService);
        searchBuilder.getFilterBuilder().equal("instStatus", WfInstStatus.在办);
        searchBuilder.getFilterBuilder().equal("dealType", WfDealType.阅办);
        searchBuilder.getFilterBuilder().in("flowInst.entityId", entityIds);
        SearchListHelper<WfNodeInst> listhelper = new SearchListHelper<WfNodeInst>();
        listhelper.execute(searchBuilder, nodeInstService.getRepository(), 1, 100);   //过滤条件已限定，反正就只返回一页
        return listhelper.getList();
    }


    /** 经办箱（显示经过本人办理的所有文） */
    @RequestMapping("/mylinkdoclist")
    public ModelAndView myLinkDocList() {
        ModelAndView mav = new ModelAndView("/doc/docevent/mylinkdoclist");
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        mav.addObject("currentUser", user);
        return mav;
    }
    
    /** 本部门公文 （显示经过本部门办理的所有文），勾选本处室共享 */
    @RequestMapping("/mydeptlinkdoclist")
    public ModelAndView myDeptLinkDocList() {
        ModelAndView mav = new ModelAndView("/doc/docevent/mydeptlinkdoclist");
        return mav;
    }

    /** 经办箱（显示经过本人办理的所有文） */
    @RequestMapping("/listMyLinkDoc.json")
    @ResponseBody
    public Page myLinkDocListjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {

        String title = request.getParameter("title");
        String businessType = request.getParameter("businessType");
        String createDateBegin = request.getParameter("createDateBegin");
        String createDateEnd = request.getParameter("createDateEnd");
        String expiryDateBegin = request.getParameter("expiryDateBegin");
        String expiryDateEnd = request.getParameter("expiryDateEnd");
        String sendUnit = request.getParameter("sendUnit");
        String creatorName = request.getParameter("creatorName");
        String instStatus = request.getParameter("instStatus");
        String deptName = request.getParameter("deptName");
        String queryReadFlag = request.getParameter("queryReadFlag");
        String superviseFlag = request.getParameter("superviseFlag");
        String dealerId = request.getParameter("dealerId");
        String dealerDeptId = request.getParameter("dealerDeptId");
        
        String queryType = request.getParameter("queryType");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dt1=null, dt2=null,dt3=null, dt4=null;
        Page p = new Page();
        try {
            if(StringUtils.isNotBlank(createDateBegin)) {
                dt1 = df.parse(createDateBegin+" 00:00");
            }
            if(StringUtils.isNotBlank(createDateEnd)) {
                dt2 = df.parse(createDateEnd+" 23:59");
            }
            if(StringUtils.isNotBlank(expiryDateBegin)) {
            	dt3 = df.parse(expiryDateBegin+" 00:00");
            }
            if(StringUtils.isNotBlank(expiryDateEnd)) {
            	dt4 = df.parse(expiryDateEnd+" 23:59");
            }
            
            OaDocFileQueryBean bean = new OaDocFileQueryBean();
            bean.setTitle(title);
            bean.setBusinessType(businessType);
            bean.setCreateDateBegin(dt1);
            bean.setCreateDateEnd(dt2);
            bean.setDealerId("".equals(dealerId)||null==dealerId ? null : Long.parseLong(dealerId));

            bean.setExpiryDateBegin(dt3);
            bean.setExpiryDateEnd(dt4);
            bean.setSendUnit(sendUnit);
            bean.setCreatorName(creatorName);
            bean.setInstStatus(instStatus);
            bean.setDeptName(deptName);
            bean.setQueryReadFlag(queryReadFlag);
            
            if("dept".equals(queryType)){
            	SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
                bean.setDealerDeptId(user.getDepartment().getId() + "");
            }else{
            	bean.setDealerDeptId(dealerDeptId);
            }
            
            bean.setSuperviseFlag(superviseFlag);
            p = docEventService.selectMylinkDocs(bean, page, rows, false,sort,order);
        }
        catch (Exception e) {
			logService.saveLog(SjType.普通操作, "经办箱-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;

    }

    /** 本部门公文 （显示经过本部门办理的所有文，勾选本处室共享） */
    @RequestMapping("/listMyDeptLinkDoc.json")
    @ResponseBody
    public Page myDeptLinkDocListJson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {

        String title = request.getParameter("title");
        String businessType = request.getParameter("businessType");
        String createDateBegin = request.getParameter("createDateBegin");
        String createDateEnd = request.getParameter("createDateEnd");
        String expiryDateBegin = request.getParameter("expiryDateBegin");
        String expiryDateEnd = request.getParameter("expiryDateEnd");
        String sendUnit = request.getParameter("sendUnit");
        String creatorName = request.getParameter("creatorName");
        String instStatus = request.getParameter("instStatus");
        String deptName = request.getParameter("deptName");
        String queryReadFlag = request.getParameter("queryReadFlag");
        String superviseFlag = request.getParameter("superviseFlag");
        

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dt1=null, dt2=null,dt3=null, dt4=null;
        Page p = new Page();
        try {
            if(StringUtils.isNotBlank(createDateBegin)) {
                dt1 = df.parse(createDateBegin+" 00:00");
            }
            if(StringUtils.isNotBlank(createDateEnd)) {
                dt2 = df.parse(createDateEnd+" 23:59");
            }
            if(StringUtils.isNotBlank(expiryDateBegin)) {
            	dt3 = df.parse(expiryDateBegin+" 00:00");
            }
            if(StringUtils.isNotBlank(expiryDateEnd)) {
            	dt4 = df.parse(expiryDateEnd+" 23:59");
            }
            OaDocFileQueryBean bean = new OaDocFileQueryBean();
            bean.setTitle(title);
            bean.setBusinessType(businessType);
            bean.setCreateDateBegin(dt1);
            bean.setCreateDateEnd(dt2);

            bean.setExpiryDateBegin(dt3);
            bean.setExpiryDateEnd(dt4);
            bean.setSendUnit(sendUnit);
            bean.setCreatorName(creatorName);
            bean.setInstStatus(instStatus);
            bean.setDeptName(deptName);
            bean.setQueryReadFlag(queryReadFlag);
            
            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            bean.setDealerDeptId(user.getDepartment().getId() + "");
            
            bean.setSuperviseFlag(superviseFlag);
            
            p = docEventService.selectMyDeptLinkDocs(bean, page, rows);
        }catch (Exception e) {
			logService.saveLog(SjType.普通操作, "本部门公文-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;

    }
    
    /** 公文监控列表导出 */
    @RequestMapping("/exportDocMonitorList.json")
    public ModelAndView docmonitorListExport(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/doc/docevent/excel/docmonitorListExport");
        mav.addObject("page", this.docMonitorJson(1, 1000000000, request,null,null));
        mav.addObject("fileName", "公文监控列表");

        return mav;
    }

    /** 经办箱列表导出 */
    @RequestMapping("/exportMyLinkDocList.json")
    public ModelAndView myLinkDocListExport(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/doc/docevent/excel/myLinkDocListExport");
        Page page = this.myLinkDocListjson(1, 1000000000, request,null,null);

        List<InstCommonShowBean> dataList = page.getRows();
        for(InstCommonShowBean obj:dataList){
        	if(null!=obj.getRestTime()){
        		Long resTime = obj.getRestTime();
            	String display = "";

            	//是否超时,1：是；0：否
    			boolean isTimeOut = false;
    			if (resTime < 0) {
                     isTimeOut = true;
                     resTime = -resTime;
                }

    			Double day = Math.floor(resTime / (24 * 60 * 60));
                display += day.intValue() + "天";
                int hour = (int)Math.round((resTime - day * 24 * 60 * 60) / (60 * 60));
                display += hour + "小时";
                if (isTimeOut) {
                	display = "超时"+display;
                }

                obj.setRestTimeShow(display);
        	}

        }
        mav.addObject("page",page);
        String fileName = "经办箱";
        mav.addObject("fileName", fileName + Tools.DATE_FORMAT3.format(new Date()));
        mav.addObject("title", fileName);

        return mav;
    }

    /**
     * 检查来文字号是否重复
     */
    @RequestMapping("/checkDocCode.json")
    @ResponseBody
    public ReturnInfo checkDocCode(String id, String docCode, String fileType, HttpServletRequest request) {
        try {
            if (StringUtils.isNotBlank(docCode)) {
                Integer count = docFileService.selectCountByDocCodeAndFileTypeExpId(docCode, WfBusinessType.valueOf(fileType), id);
                if (count != null && count > 0) {
                    return new ReturnInfo("当前文件编号有相同，不能使用", null);
                } else {
                    return new ReturnInfo(null, "当前文件编号没有相同，可以使用");
                }
            }
            return new ReturnInfo("", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(e.getMessage(), null);
        }
    }


    /**
     * 催办页面
     */
    @RequestMapping("/sendReminders")
    public ModelAndView remindersPage(String fileId, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/doc/fileparse/reminders");
        OaDocFile obj = docFileService.findOne(fileId);
        if (obj == null) {
            mav = new ModelAndView("/doc/docevent/message");
            mav.addObject("hints", "公文不存在，无法执行操作。");
        }

        String mans = "";
        String mansname = "";
        // 获取当前公文未完成环节办理人
        List<WfNodeInst> nlist = findNodeInstByEntityIds(fileId.split(","));
    	for(WfNodeInst node: nlist) {
            mans += node.getDealerId() + ",";
            mansname += node.getDealer().getUserName() + "，";
    	}
        if (mans.length() > 1) {// 去掉最后一个逗号
            mans = mans.substring(0, mans.length() - 1);
            mansname = mansname.substring(0, mansname.length() - 1);
        }
        mav.addObject("mans", mans);
        mav.addObject("mansname", mansname);

        mav.addObject("obj", obj);

        return mav;
    }

    // 催办
    @RequestMapping("/sendReminders.json")
    @ResponseBody
    public ReturnInfo reminders(MailEditBean bean, String fileId, HttpServletRequest request,
                    HttpServletResponse response) {
        try {
            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();

            OaDocFile obj = docFileService.findOne(fileId);
            if (obj == null) {
                return new ReturnInfo("公文不存在，无法执行操作。", null);
            }

            // 设置邮件信息
            bean.setSender(user.getId());
            bean.setStatus(MailStatus.已发送);
            bean.setEmailType(EmailType.个人邮件);
            bean.setAttachFlag(AttachFlag.无附件);
            // 保存邮件
            String mailId = this.emMainService.saveMail(bean);
            if (mailId != null) {
                // 发送系统消息提醒
                appMessageService.insertOaAppMsg(null, user.getId(), user.getUserName() + "发送公文《" + bean.getTitle()
                                + "》催办邮件给您，请及时办理！", MesType.邮件);
                // TODO 发送催办短信信息

                logService.saveUserLog(user, SjType.普通操作, "发送催办邮件《" + bean.getTitle() + "》信息",LogStatus.成功, request);

                return new ReturnInfo(null, "发送成功！");
            } else {
                return new ReturnInfo("发送失败！", null);
            }
        } catch (Exception e) {
			logService.saveLog(SjType.普通操作, "发送催办邮件《" + bean.getTitle() + "》信息：失败！",LogStatus.失败, request);
			e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    // /**
    // * dialog取历史选择人员的数据
    // *
    // * @param page
    // * @param rows
    // * @param request
    // * @return
    // *
    // */
    // @RequestMapping("/historyIncepter.json")
    // @ResponseBody
    // // 对页面Pag的操作
    // public List listjson(@RequestParam("flowId") String flowId, Integer page, Integer rows,
    // HttpServletRequest request) {
    //
    // List<SysUser> users = flowIncepterService.getHistoryIncepters(flowId);
    // return BeanCopyBuilder.buildList(users, UserSelectShowBean.class);
    // }


    /**
     * 续签显示
     *
     * @param id
     * @param verNum
     * @param request
     * @return
     */
    @RequestMapping("/detailHissign")
    public ModelAndView signPage(String id, Integer verNum, HttpServletRequest request) {
        ModelAndView mav = null;
        OaDocFile obj = docFileService.findOne(id);
        if (obj == null) {
            mav = new ModelAndView("/doc/docevent/message");
            mav.addObject("hints", "公文不存在，无法执行操作。");
            return mav;
        }

        mav = new ModelAndView("/doc/fileparse/hissign");
        mav.addObject("obj", obj);
        // 读取审批意见
        mav.addObject("opinionList", auditOpinionService.findAuditOpinionsByFlowInstIdAndResult(obj.getFlowInstId(), WfApprResult.通过));

        return mav;
    }


    /**
     * 文档编辑
     */
    @RequestMapping("/editDoc")
    public ModelAndView docEdit(HttpServletRequest request) {
        String docpath = request.getParameter("docpath");
        String docname = request.getParameter("docname");
        String id = request.getParameter("id");
        String flowId = request.getParameter("flowId");
        String taskKey = request.getParameter("taskKey");

        ModelAndView mav = new ModelAndView("/doc/fileparse/docEdit");
        mav.addObject("docpath", docpath);
        mav.addObject("docname", docname);
        // 读取附件
        // mav.addObject("attachMap", attachService.getAttachMap(id, OaDocFile.DOC_ATTACH_OWNER_DOC));
        mav.addObject("readOnly", request.getParameter("readOnly"));
        return mav;
    }

    /**
     * 文档首次编辑
     */
    @RequestMapping("/editDocFirst")
    public ModelAndView docFirstEdit(String type, String docpath, String docname, HttpServletRequest request) {
        String id = request.getParameter("id");
        ModelAndView mav = new ModelAndView("/doc/fileparse/docFirstEdit");
        if ("send".equals(type)) {
            // 红头模版
            // List<DocTemplate> tempList = docTemplateService
            // .findByDeptId(Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            List<DocTemplate> tempList = docTemplateService.findByDeptId(user.getDepartment().getUnitId());
            mav.addObject("tempList", tempList);
        }

        mav.addObject("docpath", docpath);
        mav.addObject("docname", docname);
        mav.addObject("type", type);
        // Map<String, List<OaDocFileAttach>> cc = attachService.getAttachMap(id, OaDocFile.DOC_ATTACH_OWNER_DOC);

        // 设置金格插件参数
        mav.addObject("userName", request.getParameter("userName"));
        mav.addObject("editType", request.getParameter("editType"));
        mav.addObject("commonType", request.getParameter("commonType"));
        mav.addObject("showType", request.getParameter("showType"));
        // mav.addObject("attachMap", attachService.getAttachMap(id, OaDocFile.DOC_ATTACH_OWNER_DOC));
        return mav;
    }

    /**
     * 暂存
     */
    @RequestMapping("/saveTemp.json")
    @ResponseBody
    public ReturnBean saveTemp(OaDocFileEditBean bean, HttpServletRequest request, HttpServletResponse response) {
    	SysUser user = (SysUser)UserTool.getInstance().getCurrentUser();
        bean.setCurrentUser(user);
        if(null!=bean.getExpiryDateOther()){
        	bean.setExpiryDate(bean.getExpiryDateOther());
        }
        ReturnBean ret = this.docFileService.saveDocFile(bean);
        if (ret.isSuccess()) logService.saveUserLog(user, SjType.普通操作, "暂存公文：" + bean.getTitle(),LogStatus.成功, request);
        return ret;
    }

    /**
     * 启动收发文流程
     */
    @RequestMapping(value = "dealStartflow", method = RequestMethod.POST)
    @ResponseBody
    public ReturnBean startWorkflow(OaDocFileEditBean bean, HttpServletResponse response, HttpServletRequest request) {
    	bean.setCurrentUser((SysUser)UserTool.getInstance().getCurrentUser());
    	if(null!=bean.getExpiryDateOther()){
    		bean.setExpiryDate(bean.getExpiryDateOther());
        }
        // 先判断该文件状态，只有暂存状态下才能启动流程，不允许重复启动
        if (StringUtils.isNotBlank(bean.getId())) {
            OaDocFile obj = this.docFileService.findOne(bean.getId());
            if (obj == null) {
                return new ReturnBean("公文不存在，无法执行操作！");
            }
            if (!FileStatusFlag.暂存.equals(obj.getStatus()) && StringUtils.isBlank(bean.getParentId())) {
                return new ReturnBean("不允许重复启动流程！");
            }
        }

        try {
	        // 启动流程
	        return docEventService.createFlowData(bean, request);
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "启动流程：《" + bean.getTitle() + "》失败！",LogStatus.失败,request);
            return new ReturnBean("流程启动失败！");
        }

    }

    /**
     * 办结
     */
    @RequestMapping(value = "dealCompleteflow", method = RequestMethod.POST)
    @ResponseBody
    public ReturnInfo completeWorkflow(OaDocFileEditBean bean, HttpServletRequest request) {
    	bean.setCurrentUser((SysUser)UserTool.getInstance().getCurrentUser());
    	OaDocFile obj = this.docFileService.findOne(bean.getId());
        try {
            if (obj == null) {
                return new ReturnInfo("公文不存在，无法执行操作！", null);
            }
            docEventService.completeFlowData(bean, request);
            return new ReturnInfo("1", null, "办结成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "公文：《" + obj.getTitle() + "》办结失败！",LogStatus.失败,request);
            return new ReturnInfo(null, "结束流程失败：" + e.getMessage(), null);
        }

    }


    /**
     * 删除、撤文
     */
    @RequestMapping(value = "deleteflow", method = RequestMethod.POST)
    @ResponseBody
    public ReturnInfo deleteWorkflow(String id, HttpServletRequest request) {
        OaDocFile obj = this.docFileService.findOne(id);
        try {
            if (obj == null) {
                return new ReturnInfo("公文不存在，无法执行操作！", null);
            }
            docEventService.deleteFlowData(id, request);
            return new ReturnInfo("1", null, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "公文：《" + obj.getTitle() + "》删除失败！",LogStatus.失败,request);
            return new ReturnInfo(null, "结束流程失败：" + e.getMessage(), null);
        }
    }


    /**
     * 删除公文
     *
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ReturnInfo delete(String ids, HttpServletRequest request) {
        try {
            if (ids != null) {
                String[] idArr = ids.split(",");
                for (String id : idArr) {
                    this.docEventService.deleteFlowData(id, request);
                }
            }
            return new ReturnInfo(null, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo("服务器连接不上，删除失败！", null);
        }
    }

    //独立阅知表（本项目目前暂不用）
    @RequestMapping("/saveReadMatter.json")
    @ResponseBody
    public ReturnInfo saveReadMatter(FileShowBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {
            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            String userIds = request.getParameter("userIds");
            this.docFileService.saveReadMatter(userIds, bean);

            // 暂时阅知不加入环节，待测试有什么问题 by yuandl 20161202
            // OaDocFile obj = docFileService.findOne(bean.getId());
            // docEventService.addReadNodeInst(obj, userIds, request);

            logService.saveUserLog(user, SjType.普通操作, "发送阅知：" + bean.getTitle(),LogStatus.成功, request);
            return new ReturnInfo(null, null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }


    @RequestMapping("/saveReadNodes.json")
    @ResponseBody
    public ReturnInfo saveReadNodes(OaDocFileEditBean bean, HttpServletRequest request, HttpServletResponse response) {
    	SysUser user = (SysUser)UserTool.getInstance().getCurrentUser();
        try {
            String userIds = request.getParameter("userIds");

            //OaDocFile obj = docFileService.findOne(bean.getId());
            docEventService.addReadNodeInst(bean.getId(), bean.getTitle(), userIds, user, request);

            logService.saveUserLog(user, SjType.普通操作, "发送阅知：" + bean.getTitle(),LogStatus.成功, request);
            return new ReturnInfo(null, null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "发送阅知：《" + bean.getTitle() + "》失败！",LogStatus.失败, request);
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }


    @RequestMapping("/getNewLimitTime.json")
    @ResponseBody
    public String getNewLimitTime(OaDocFileEditBean bean, HttpServletRequest request, HttpServletResponse response) {
    	bean.setCurrentUser((SysUser)UserTool.getInstance().getCurrentUser());
        // 工作日
        String limitDay = request.getParameter("limitDay");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String baseDate = sd.format(new Date());
        Date limitTime = null;
        try {

            Date lt = new Date();
            if (!StringUtils.isNotBlank(baseDate)) {
                lt = Tools.DATE_MINUTE_FORMAT.parse(baseDate);
            }
            limitTime = this.getWorkingDate(Integer.valueOf(limitDay), lt);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return sd.format(limitTime);
    }

    /**
     * @Title: getLimitTime
     * @Description: 公文交换获取限时办结时间
     * @param request
     * @param response
     * @return
     * @return: String
     */
    @RequestMapping("/getLimitTime.json")
    @ResponseBody
    public String getLimitTime(HttpServletRequest request, HttpServletResponse response) {
        // 工作日
        String limitDay = request.getParameter("limitDay");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String baseDate = sd.format(new Date());
        String limitTimeStr = null;
        Date limitTime = null;
        try {

            Date lt = new Date();
            if (!StringUtils.isNotBlank(baseDate)) {
                lt = Tools.DATE_MINUTE_FORMAT.parse(baseDate);
            }

            //根据限办工日和当前日期获取 区政府工作日限时时间，如果获取不到区政府工作日限时时间，则获取自定义保存的工作日限时时间
            //如果区政府和自定义的时间都获取不到，则获取本系统计算的时间
            limitTimeStr = docStatisticService.getWorkingDate(Integer.valueOf(limitDay), lt);

            if(Tools.isEmptyString(limitTimeStr)){
            	limitTime = this.getWorkingDate(Integer.valueOf(limitDay), lt);
            	limitTimeStr = sd.format(limitTime);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return limitTimeStr;
    }

    /**
     * 指定工作日数，返回绝对日期
     *
     * @param limitDay
     * @param baseDate
     * @return
     */
    public Date getWorkingDate(Integer limitDay, Date baseDate) {
        Date result = null;
        int idx = 0;
        Date now = (baseDate == null) ? new Date() : baseDate;
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        WorkingDay f = null;
        Map<String, WorkingDay> dict = DictionaryPool.getInstance().get(WorkingDay.class.getSimpleName());
        while (idx < limitDay) {
            c.add(Calendar.DAY_OF_YEAR, 1);
            if (dict != null)
                f = dict.get(Tools.DATE_FORMAT4.format(c.getTime()));

            if (f == null) {
                if (!Tools.checkWeekend(c.getTime())) {
                    idx++;
                    result = c.getTime();
                }
            } else {
                if (DateFlag.工作日.equals(f.getDateFlag())) {
                    idx++;
                    result = c.getTime();
                }
            }
        }
        return result;
    }

    /**
     * 处理任务
     */
    @RequestMapping(value = "task/done")
    @ResponseBody
    public ReturnInfo taskDone(OaDocFileEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        bean.setCurrentUser(user);
        
        WfNodeInst ni = nodeInstService.findOne(bean.getCurNodeInstId());

        if (ni == null) {
        	return new ReturnInfo(null, "流程错误，没有找到任务实例，请联系管理员！", null);
        } else if (ni != null && WfInstStatus.办结.equals(ni.getInstStatus())) {
            return new ReturnInfo(null, "任务已失效，可能已被其它人抢先执行！", null);
        } else {
            docEventService.completeNodeDone(bean, request);
            return new ReturnInfo("1", null, "处理完成！");
        }
    }

    /**
     * 暂存任务
     */
    @RequestMapping(value = "task/doneTemp")
    @ResponseBody
    public ReturnInfo taskDoneTemp(OaDocFileEditBean bean, HttpServletRequest request, HttpServletResponse response) {
    	bean.setCurrentUser((SysUser)UserTool.getInstance().getCurrentUser());
        WfNodeInst ni = nodeInstService.findOne(bean.getCurNodeInstId());

        if (ni != null && WfInstStatus.办结.equals(ni.getInstStatus())) {
            return new ReturnInfo(null, "任务已失效，可能已被其它人抢先执行！", null);
        } else {
            docEventService.completeNodeDoneTemp(bean, request);
            return new ReturnInfo("1", null, "保存成功！");
        }
    }

    /**
     * 阅知处理（打开直接自动完成阅知）
     */
    @RequestMapping(value = "task/doneRead")
    @ResponseBody
    public ReturnBean taskReadDone(OaDocFileEditBean bean, HttpServletRequest request, HttpServletResponse response) {
    	bean.setCurrentUser((SysUser)UserTool.getInstance().getCurrentUser());
        return docEventService.completeNodeReadDone(bean, request);
    }


    //退回操作
    @RequestMapping("task/doneReturnNodeInst")
    @ResponseBody
    public ReturnInfo doTaskReturn(String id, String curNodeInstId, String backNodeId, String backOpinion, HttpServletRequest request) {
    	SysUser user = (SysUser)UserTool.getInstance().getCurrentUser();
        OaDocFile docFile = docFileService.findOne(id);
        try {
            WfNodeInst nodeInst = nodeInstService.findOne(curNodeInstId);
            if (docFile != null && nodeInst != null) {
                docEventService.returnNodeInst(docFile, nodeInst, backNodeId, backOpinion, user.getId(), request);
                return new ReturnInfo("1", null, "退回成功！");
            } else {
                return new ReturnInfo("公文不存在，无法执行操作。", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "公文《" + docFile.getTitle() + "》退回失败！",LogStatus.失败, request);
            return new ReturnInfo("操作失败！" + e.getMessage(), null);
        }
    }


    /**
     * 主动取回到当前环节
     */
    @RequestMapping("task/dealBringBack")
    @ResponseBody
    public ReturnInfo taskBringBack(String id, String curNodeId, HttpServletRequest request) {
    	SysUser user = (SysUser)UserTool.getInstance().getCurrentUser();
    	OaDocFile docFile = docFileService.findOne(id);
        try {
            if (docFile != null) {
                String res = docEventService.bringBackNodeInst(docFile, curNodeId, user, request);
                if (res == null) {
                	return new ReturnInfo(null, "取回成功！");
                } else {
                	return new ReturnInfo(res, null);
                }
            } else {
                return new ReturnInfo("公文不存在，无法取回公文", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "公文《" + docFile.getTitle() + "》取回失败！",LogStatus.失败, request);
            return new ReturnInfo("操作失败！" + e.getMessage(), null);
        }
    }

    /**
     * 公文办结取回
     */
    @RequestMapping("dealFinishedBack")
    @ResponseBody
    public ReturnInfo finishedBack(String id, HttpServletRequest request) {
    	OaDocFile docFile = docFileService.findOne(id);
        try {
            if (docFile != null) {
                SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
                // 办结取回条件：1. 公文办结待归档，2. 用户为拟稿人
                if (FileStatusFlag.待归档.equals(docFile.getStatus()) && docFile.getCreatorId().equals(user.getId())) {
                    docEventService.bringFinishedDocBack(docFile, request);
                    return new ReturnInfo(null, "取回成功！");
                } else {
                    return new ReturnInfo("公文取回失败，只有拟稿人才能取回已办结的公文！", null);
                }

            } else {
                return new ReturnInfo("公文不存在，无法取回公文。", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "公文《" + docFile.getTitle() + "》取回失败！",LogStatus.失败, request);
            return new ReturnInfo("操作失败！" + e.getMessage(), null);
        }
    }

    /**
     * 公文转办部门函件
     */
    @RequestMapping("dealDispatch")
    @ResponseBody
    public ReturnInfo dispatchDocFile(String id, @RequestParam("limitDay") Integer limitDay,
                    @RequestParam("expiryDate") Date expiryDate, HttpServletRequest request) {
        try {
            OaDocFile docFile = docFileService.findOne(id);
            if (docFile != null) {
                String content = request.getParameter("content");
                String incepterDeptNames = request.getParameter("incepterDeptNames");
                String incepterDepts = request.getParameter("incepterDepts");

                if (StringUtils.isNotBlank(incepterDepts)) {
                    OaDocFileDispatchBean bean = new OaDocFileDispatchBean();
                    bean.setDocFile(docFile);
                    bean.setLimitDay(limitDay);
                    bean.setExpiryDate(expiryDate);
                    bean.setIncepterDeptNames(incepterDeptNames);
                    bean.setIncepterDepts(incepterDepts);
                    bean.setContent(content);

                    docEventService.dispatchDocFile(bean, request);
                    return new ReturnInfo(null, "转办成功！");
                } else {
                    return new ReturnInfo("获取接收部门失败，无法执行操作。fileID:" + id, null);
                }
            } else {
                return new ReturnInfo("公文不存在，无法执行操作。", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo("操作失败！" + e.getMessage(), null);
        }
    }

    /**
     * 公文转阅
     */
    @RequestMapping("dealDispatchDocRead")
    @ResponseBody
    public ReturnInfo dispatchDocRead(String id, String readmans, HttpServletRequest request) {
        try {
            OaDocFile docFile = docFileService.findOne(id);
            if (docFile != null) {
                docEventService.dispatchDocRead(docFile, readmans, request);
                return new ReturnInfo(null, "转阅成功！");
            } else {
                return new ReturnInfo("公文不存在，无法执行操作。", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo("操作失败！" + e.getMessage(), null);
        }
    }

    /**
     * 函件转收文
     */
    @RequestMapping("/dealLetterToDoc")
    @ResponseBody
    public ReturnBean letterToDoc(String id, String receiverId, HttpServletRequest request) {
        try {
            OaDocFileFromLetterBean bean = new OaDocFileFromLetterBean();
            bean.setLetterId(id);
            bean.setLetterReceiverId(receiverId);
            ReturnBean ret = docEventService.saveLetterToDocFile(bean, request);
            if (ret.isSuccess()) {
                return new ReturnBean("函件转收文成功！", ret.getObject(), true);
            } else {
                return new ReturnBean("公文不存在，无法执行操作。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnBean("操作失败！" + e.getMessage());
        }
    }

    /** 公文编辑（查询所有文） */
    @RequestMapping("/docmodify")
    public ModelAndView docModify() {
        ModelAndView mav = new ModelAndView("/doc/docevent/docmodify");
        return mav;
    }
    
    /** 公文编辑（查询所有文） */
    @RequestMapping("/listDocModify.json")
    @ResponseBody
    public Page docModifyListJson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {

        String title = request.getParameter("title");
        String docCode = request.getParameter("docCode");
        String createDateBegin = request.getParameter("createDateBegin");
        String createDateEnd = request.getParameter("createDateEnd");
        String sendUnit = request.getParameter("sendUnit");
        String fileCode = request.getParameter("fileCode");
        String instStatus = request.getParameter("instStatus");
        
        Date dt1=null, dt2=null;
        Page p = new Page();
        try {
            if(StringUtils.isNotBlank(createDateBegin)) {
                dt1 = Tools.DATE_MINUTE_FORMAT.parse(createDateBegin+" 00:00");
            }
            if(StringUtils.isNotBlank(createDateEnd)) {
                dt2 = Tools.DATE_MINUTE_FORMAT.parse(createDateEnd+" 23:59");
            }
            OaDocFileQueryBean bean = new OaDocFileQueryBean();
            bean.setTitle(title);
            bean.setDocCode(docCode);
            bean.setCreateDateBegin(dt1);
            bean.setCreateDateEnd(dt2);

            bean.setSendUnit(sendUnit);
            bean.setFileCode(fileCode);
            bean.setInstStatus(instStatus);
            
            p = docEventService.selectDocModifyList(bean, page, rows, sort, order);
        } catch (Exception e) {
			logService.saveLog(SjType.普通操作, "公文编辑-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;
    }
    
    /**
     * 公文办理迁移
     */
    @RequestMapping(value = "dealDocMove", method = RequestMethod.POST)
    @ResponseBody
    public ReturnBean docMove(String nodeInstIds,String flowInstIds,Long userId,HttpServletRequest request) {

        try {
        	
        	String[] nodeInstIdsArr = nodeInstIds.split(",");
        	String[] flowInstIdsArr = flowInstIds.split(",");
        	if(nodeInstIdsArr.length==flowInstIdsArr.length){
	        	for(int i = 0;i<nodeInstIdsArr.length;i++){
	        		docFileService.docMove(nodeInstIdsArr[i], flowInstIdsArr[i], userId);
	        	}
        	}
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "办理公文迁移：失败！,流程id：" + flowInstIds,LogStatus.失败,request);
            return new ReturnBean("办理公文迁移失败！");
        }
		return new ReturnBean("公文办理迁移成功！", null, true);

    }
    
    /**
     * 公文办理转移
     */
    @RequestMapping(value = "dealDocTransfer", method = RequestMethod.POST)
    @ResponseBody
    public ReturnBean docTransfer(String nodeInstIds,String flowInstIds,Long userId,HttpServletRequest request) {

        try {
        	
        	String[] nodeInstIdsArr = nodeInstIds.split(",");
        	String[] flowInstIdsArr = flowInstIds.split(",");
        	if(nodeInstIdsArr.length==flowInstIdsArr.length){
	        	for(int i = 0;i<nodeInstIdsArr.length;i++){
	        		docFileService.docTransfer(nodeInstIdsArr[i], flowInstIdsArr[i], userId);
	        	}
        	}
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "办理公文转移：失败！,流程id：" + flowInstIds,LogStatus.失败,request);
            return new ReturnBean("办理公文转移失败！");
        }
		return new ReturnBean("公文办理转移成功！", null, true);

    }
    
    /**
     * 转办公文列表
     */
    @RequestMapping("/listTurn")
    public ModelAndView turnList() {
        ModelAndView mav = new ModelAndView("/doc/docevent/turnlist");
        return mav;
    }

    @RequestMapping(value = "listTurn.json")
    @ResponseBody
    public Page turnListjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	String title = request.getParameter("title");
    	String sncode = request.getParameter("sncode");
        String businessType = request.getParameter("businessType");
        String createDateBegin = request.getParameter("createDateBegin");
        String createDateEnd = request.getParameter("createDateEnd");
        String expiryDateBegin = request.getParameter("expiryDateBegin");
        String expiryDateEnd = request.getParameter("expiryDateEnd");
        String instStatus = request.getParameter("instStatus");
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dt1=null, dt2=null,dt3=null, dt4=null;
        Page p = new Page();
        try {
            if(StringUtils.isNotBlank(createDateBegin)) {
                dt1 = df.parse(createDateBegin+" 00:00");
            }
            if(StringUtils.isNotBlank(createDateEnd)) {
                dt2 = df.parse(createDateEnd+" 23:59");
            }
            if(StringUtils.isNotBlank(expiryDateBegin)) {
            	dt3 = df.parse(expiryDateBegin+" 00:00");
            }
            if(StringUtils.isNotBlank(expiryDateEnd)) {
            	dt4 = df.parse(expiryDateEnd+" 23:59");
            }
            
            OaDocFileQueryBean bean = new OaDocFileQueryBean();
            bean.setTitle(title);
            bean.setSncode(sncode);
            bean.setBusinessType(businessType);
            bean.setCreateDateBegin(dt1);
            bean.setCreateDateEnd(dt2);
            bean.setDealerId(Long.parseLong(UserTool.getInstance().getCurrentUserId()));

            bean.setExpiryDateBegin(dt3);
            bean.setExpiryDateEnd(dt4);
            bean.setInstStatus(instStatus);
            
            p = docEventService.selectTurnDocs(bean, page, rows,sort,order);
        }
        catch (Exception e) {
			logService.saveLog(SjType.普通操作, "转办公文-列表查询：失败！",LogStatus.失败, request);
			e.printStackTrace();
		}
        
        return p;
    }
    
    /**
     * 自动生成字号
     */
    @RequestMapping("/createDocCode.json")
    @ResponseBody
    public String createDocCode(String id, String docCodeName, String year, String fileType, HttpServletRequest request){
    	if (StringUtils.isNotBlank(docCodeName) && StringUtils.isNotBlank(year)) {
    		return docFileService.createDocCode(docCodeName, year, WfBusinessType.valueOf(fileType), id);
    	}
    	return null;
    }
}
