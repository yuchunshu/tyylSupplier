package cn.com.chaochuang.pubaffairs.leave.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.data.persistence.SearchFilter;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.doc.readmatter.service.ReadMatterService;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.leave.bean.OaLeaveEditBean;
import cn.com.chaochuang.pubaffairs.leave.bean.OaLeaveQueryBean;
import cn.com.chaochuang.pubaffairs.leave.bean.OaLeaveShowBean;
import cn.com.chaochuang.pubaffairs.leave.domain.OaLeave;
import cn.com.chaochuang.pubaffairs.leave.service.OaLeaveService;
import cn.com.chaochuang.pubaffairs.leave.service.PubLeaveService;
import cn.com.chaochuang.pubaffairs.web.PubCommonController;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfAuditOpinionService;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfApprResult;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfInstStatus;

/**
 * @author dengl 2018.08.08
 *
 */
@Controller
@RequestMapping("pubaffairs/leave")
public class OaLeaveController extends PubCommonController{
	
	@Autowired
    private OaLeaveService      		oaLeaveService;
	
    @Autowired
    private SysUserService      		userService;

    @Autowired
    protected ConversionService 		conversionService;

    @Autowired
    private LogService          		logService;
    
    @Autowired
    private	PubLeaveService				pubLeaveService;
    
    @Autowired
    private WfNodeInstService   		nodeInstService;
    
    @Autowired
	private WfAuditOpinionService 		auditOpinionService;
    
    @Autowired
	private ReadMatterService 			readMatterService;
    
    @Autowired
    private SysUserService         		sysUserService;
    
    @Autowired
    private SysAttachService    		attachService;
    
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/pubaffairs/leave/list");
        return mav;
    }

    @RequestMapping("/query")
    public ModelAndView query() {
        ModelAndView mav = new ModelAndView("/pubaffairs/leave/queryList");
        mav.addObject("query", "1");
        return mav;
    }
    
    /**
     * 休假申请已办结列表页面
     */
    @RequestMapping("/listEnd")
    public ModelAndView listEnd() {
        ModelAndView mav = new ModelAndView("/pubaffairs/leave/endList");
        return mav;
    }
    
    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
    	SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        SearchBuilder<OaLeave, String> searchBuilder = new SearchBuilder<OaLeave, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        
        boolean flag = this.sysUserService.isOwnByRoleName("系统管理员");
        if(!flag){//系统管理员角色查看所有的休假申请
        	searchBuilder.getFilterBuilder().equal("creatorId", user.getId());
        }
        
        searchBuilder.appendSort(Direction.DESC, "createDate");
        SearchListHelper<OaLeave> listhelper = new SearchListHelper<OaLeave>();
        listhelper.execute(searchBuilder, oaLeaveService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), OaLeaveShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/querylist.json")
    @ResponseBody
    public Page querylistjson(Integer page, Integer rows, HttpServletRequest request) {
    	SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
    	
    	Map<String, SearchFilter> thisFilters = new HashMap<String, SearchFilter>();
        Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "__");
        if (MapUtils.isNotEmpty(searchParams)) {
            thisFilters = SearchFilter.parse(searchParams);
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Page p = new Page();
        dataMap = this.oaLeaveService.leaveSelect(thisFilters.values(),user,page, rows);
        p.setRows((ArrayList<OaLeaveQueryBean>) dataMap.get("rows"));
        p.setTotal(dataMap.get("total") == null ? (long) 0 : (long) dataMap.get("total"));
        return p;
    }
    
    @RequestMapping("/listEnd.json")
    @ResponseBody
    public Page listEndJson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
        SearchBuilder<OaLeave, String> searchBuilder = new SearchBuilder<OaLeave, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
    	searchBuilder.getFilterBuilder().equal("status", CarStatus.办结);
        
    	if(StringUtils.isNotBlank(sort)){
        	sort = sort.replace("Show", "");
        	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
        }
        else{
        	searchBuilder.appendSort(Direction.DESC, "createDate");
        }
        
        SearchListHelper<OaLeave> listhelper = new SearchListHelper<OaLeave>();
        listhelper.execute(searchBuilder, oaLeaveService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), OaLeaveShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    
    @RequestMapping("/new")
    public ModelAndView newPage() {
        return super.openNewPage(WfBusinessType.休假申请);
    }

    /**
     * 暂存状态休假删除
     * @param ids
     * @param request
     * @return
     */
     @RequestMapping("/del.json")
     @ResponseBody
     public ReturnInfo del(String ids, HttpServletRequest request) {
         try {
        	 this.oaLeaveService.delOaleaveByIds(ids);//占不写入日志
             return new ReturnInfo("1", null, "删除成功!");
         } catch (Exception e) {
             e.printStackTrace();
             return new ReturnInfo(null, "服务器连接不上！", null);
         }
     }
     
    @RequestMapping("/edit")
    public ModelAndView editPage(String id) {
        ModelAndView mav = new ModelAndView("/pubaffairs/leave/edit");
        OaLeave leave = this.oaLeaveService.findOne(id);
        mav.addObject("leave", leave);
        if (leave.getCreatorId() != null) {
            mav.addObject("createName", userService.findOne(leave.getCreatorId()).getUserName());
        }
        mav.addObject("attachMap", this.attachService.getAttachMap(leave.getId(), OaLeave.class.getSimpleName()));

        return mav;
    }

    // 暂存
    @RequestMapping("/saveTemp.json")
    @ResponseBody
    public ReturnInfo saveTemp(OaLeaveEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (bean != null) {
                bean.setStatus(CarStatus.暂存);
            }
            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            ReturnBean returnBean = this.oaLeaveService.saveOaLeaveFlow(bean,request);
            logService.saveLog(SjType.普通操作, "暂存休假申请信息",LogStatus.成功, request);
            if(returnBean!=null && returnBean.getObject()!=null){
            	OaLeave leave=(OaLeave) returnBean.getObject();
            	return new ReturnInfo(leave.getId(), null, "暂存成功!");
            }
            return new ReturnInfo(null, "暂存失败！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo del(String[] ids, HttpServletRequest request) {
        try {
            if (ids != null && ids.length > 0) {
                for (String id : ids) {
                    this.oaLeaveService.delOaLeave(id);
                    logService.saveLog(SjType.普通操作, "删除视频信息：,删除id为'" + id + "'的记录",LogStatus.成功, request);
                }
            }

            return new ReturnInfo("1", null, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detail(String id) {
        ModelAndView mav = new ModelAndView("/pubaffairs/leave/leave_detail");
        OaLeave leave = null;
        if (id != null) {
        	leave = this.oaLeaveService.findOne(id);
            if (leave != null) {
                mav.addObject("obj", leave);
            }
        }
    	// 基本信息
    	if (leave == null) {
			mav = new ModelAndView("/doc/docevent/message");
			mav.addObject("hints", "请假信息读取失败，该请假条可能已被删除，请刷新或重新进入列表页面。");
			return mav;
		}
    	// 读取审批意见
    	mav.addObject("opinionList",
    			auditOpinionService.findAuditOpinionsByFlowInstIdAndResult(leave.getFlowInstId(), WfApprResult.通过));
    	mav.addObject("readCount", this.readMatterService.selectReadMatterCount(id));
    	mav.addObject("type", WfBusinessType.休假申请.getKey());
        mav.addObject("attachMap", this.attachService.getAttachMap(leave.getId(), OaLeave.class.getSimpleName()));

        return mav;
    }

    /**
     * 启动休假申请流程
     */
    @RequestMapping(value = "dealStartflow", method = RequestMethod.POST)
    @ResponseBody
    public ReturnBean startWorkflow(OaLeaveEditBean bean, HttpServletResponse response, HttpServletRequest request) {
    	bean.setCurrentUser((SysUser)UserTool.getInstance().getCurrentUser());
    	// 先判断该休假状态，只有暂存状态下才能启动流程，不允许重复启动
        if (StringUtils.isNotBlank(bean.getId())) {
        	OaLeave obj = this.oaLeaveService.findOne(bean.getId());
            if (obj == null) {
                return new ReturnBean("假期申请不存在，无法执行操作！");
            }
            if (!CarStatus.暂存.equals(obj.getStatus())) {
                return new ReturnBean("不允许重复启动流程！");
            }
        }

        try {
	        // 启动流程
	        return pubLeaveService.createFlowData(bean, request);
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "启动流程：《" + bean.getReason() + "》失败！",LogStatus.失败,request);
            return new ReturnBean("流程启动失败！");
        }

    }
    
    /** 休假审核页面 */
    @RequestMapping("task/deal")
    public ModelAndView dealPage(String opflag, String id, String fileId, HttpServletRequest request) {
    	return super.openDealPage(opflag, id, fileId, WfBusinessType.休假申请);
    }
    
    
    /**
     * 处理任务
     */
    @RequestMapping(value = "task/done")
    @ResponseBody
    public ReturnInfo taskDone(OaLeaveEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        bean.setCurrentUser(user);
        
        WfNodeInst ni = nodeInstService.findOne(bean.getCurNodeInstId());

        if (ni == null) {
        	return new ReturnInfo(null, "流程错误，没有找到任务实例，请联系管理员！", null);
        } else if (ni != null && WfInstStatus.办结.equals(ni.getInstStatus())) {
            return new ReturnInfo(null, "任务已失效，可能已被其它人抢先执行！", null);
        } else {
        	pubLeaveService.completeNodeDone(bean, request);
            return new ReturnInfo("1", null, "处理完成！");
        }
    }
    
    /**
     * 办结
     */
    @RequestMapping(value = "dealCompleteflow", method = RequestMethod.POST)
    @ResponseBody
    public ReturnInfo completeWorkflow(OaLeaveEditBean bean, HttpServletRequest request) {
    	bean.setCurrentUser((SysUser)UserTool.getInstance().getCurrentUser());
    	OaLeave obj = this.oaLeaveService.findOne(bean.getId());
        try {
            if (obj == null) {
                return new ReturnInfo("用车申请不存在，无法执行操作！", null);
            }
            pubLeaveService.completeFlowData(bean, request);
            return new ReturnInfo("1", null, "办结成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "休假申请：《" + obj.getReason() + "》办结失败！",LogStatus.失败,request);
            return new ReturnInfo(null, "结束流程失败：" + e.getMessage(), null);
        }
    }
    
    /**
     * 退回操作
     */
    @RequestMapping("task/doneReturnNodeInst")
    @ResponseBody
    public ReturnInfo doTaskReturn(String id, String curNodeInstId, String backNodeId, String backOpinion, HttpServletRequest request) {
    	SysUser user = (SysUser)UserTool.getInstance().getCurrentUser();
    	OaLeave obj = this.oaLeaveService.findOne(id);
        try {
            WfNodeInst nodeInst = nodeInstService.findOne(curNodeInstId);
            if (obj != null && nodeInst != null) {
            	pubLeaveService.returnNodeInst(obj, nodeInst, backNodeId, backOpinion, user.getId(), request);
                return new ReturnInfo("1", null, "退回成功！");
            } else {
                return new ReturnInfo("申请不存在，无法执行操作。", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "休假申请《" + obj.getReason() + "》退回失败！",LogStatus.失败, request);
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
    	OaLeave obj = this.oaLeaveService.findOne(id);
        try {
            if (obj != null) {
                String res = pubLeaveService.bringBackNodeInst(obj, curNodeId, user, request);
                if (res == null) {
                	return new ReturnInfo(null, "取回成功！");
                } else {
                	return new ReturnInfo(res, null);
                }
            } else {
                return new ReturnInfo("休假申请不存在，无法取回", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "休假申请编号ID:" + obj.getId() + "取回失败！",LogStatus.失败, request);
            return new ReturnInfo("操作失败！" + e.getMessage(), null);
        }
    }
    
    /**
     * 删除、撤文
     */
    @RequestMapping(value = "deleteflow", method = RequestMethod.POST)
    @ResponseBody
    public ReturnInfo deleteWorkflow(String id, HttpServletRequest request) {
    	SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
    	OaLeave obj = this.oaLeaveService.findOne(id);
        try {
            if (obj == null) {
                return new ReturnInfo("休假申请不存在，无法执行操作！", null);
            }
            pubLeaveService.deleteFlowData(user, id, request);
            return new ReturnInfo("1", null, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "休假申请：《" + obj.getReason() + "》删除失败！",LogStatus.失败,request);
            return new ReturnInfo(null, "结束流程失败：" + e.getMessage(), null);
        }
    }
}
