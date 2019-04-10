package cn.com.chaochuang.pubaffairs.repair.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.doc.readmatter.service.ReadMatterService;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.repair.bean.OaMyRepairShowBean;
import cn.com.chaochuang.pubaffairs.repair.bean.OaRepairEditBean;
import cn.com.chaochuang.pubaffairs.repair.bean.OaRepairShowBean;
import cn.com.chaochuang.pubaffairs.repair.domain.OaEquipmentRepair;
import cn.com.chaochuang.pubaffairs.repair.service.OaRepairService;
import cn.com.chaochuang.pubaffairs.repair.service.PubRepairService;
import cn.com.chaochuang.pubaffairs.web.PubCommonController;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfAuditOpinionService;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfApprResult;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfInstStatus;

/**
 * @author dengl 2018.08.10
 *
 */
@Controller
@RequestMapping("pubaffairs/repair")
public class OaRepairController extends PubCommonController{
	
	@Autowired
	private OaRepairService  			repairService;
	
	@Autowired
	private PubRepairService			pubRepairService;
	
    @Autowired
    private LogService             		logService;
    
    @Autowired
    private WfNodeInstService      		nodeInstService;
    
    @Autowired
	private WfAuditOpinionService 		auditOpinionService;
    
    @Autowired
	private ReadMatterService 			readMatterService;
    
    @Autowired
    private SysUserService         		sysUserService;
    
    @Autowired
    private SysAttachService    		attachService;
	
	/**
     * 故障报告申请审核列表页面
     */
    @RequestMapping("/list")
    public ModelAndView taskList() {
        ModelAndView mav = new ModelAndView("/pubaffairs/repair/repairlist");
        return mav;
    }
    
    @RequestMapping(value = "listTask.json")
    @ResponseBody
    public Page repairListjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
    	SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
    	Map<String, SearchFilter> thisFilters = new HashMap<String, SearchFilter>();

        Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "__");
        if (MapUtils.isNotEmpty(searchParams)) {
            thisFilters = SearchFilter.parse(searchParams);
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Page p = new Page();
        dataMap = this.repairService.repairSelect(thisFilters.values(),user,page, rows);
        p.setRows((ArrayList<OaRepairShowBean>) dataMap.get("rows"));
        p.setTotal(dataMap.get("total") == null ? (long) 0 : (long) dataMap.get("total"));
        return p;
    }
    
    /**
     * 故障报告申请新增页面
     */
	@RequestMapping("/new")
    public ModelAndView newPage() {
		return super.openNewPage(WfBusinessType.故障报告申请);
	}
	
	/**
     * 启动故障报告流程
     */
    @RequestMapping(value = "dealStartflow", method = RequestMethod.POST)
    @ResponseBody
    public ReturnBean startWorkflow(OaRepairEditBean bean, HttpServletResponse response, HttpServletRequest request) {
    	bean.setCurrentUser((SysUser)UserTool.getInstance().getCurrentUser());
        // 先判断该故障报告状态，只有暂存状态下才能启动流程，不允许重复启动
        if (StringUtils.isNotBlank(bean.getId())) {
        	OaEquipmentRepair obj = this.repairService.findOne(bean.getId());
            if (obj == null) {
                return new ReturnBean("故障报告申请不存在，无法执行操作！");
            }
            if (!CarStatus.暂存.equals(obj.getStatus())) {
                return new ReturnBean("不允许重复启动流程！");
            }
        }

        try {
	        // 启动流程
	        return pubRepairService.createFlowData(bean, request);
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "启动流程：《" + bean.getFailureReason() + "》失败！",LogStatus.失败,request);
            return new ReturnBean("流程启动失败！");
        }
    }
    
    /** 故障报告审核页面 */
    @RequestMapping("task/deal")
    public ModelAndView dealPage(String opflag, String id, String fileId, HttpServletRequest request) {
    	return super.openDealPage(opflag, id, fileId, WfBusinessType.故障报告申请);
    }
    
    /**
     * 处理任务
     */
    @RequestMapping(value = "task/done")
    @ResponseBody
    public ReturnInfo taskDone(OaRepairEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        bean.setCurrentUser(user);
        
        WfNodeInst ni = nodeInstService.findOne(bean.getCurNodeInstId());

        if (ni == null) {
        	return new ReturnInfo(null, "流程错误，没有找到任务实例，请联系管理员！", null);
        } else if (ni != null && WfInstStatus.办结.equals(ni.getInstStatus())) {
            return new ReturnInfo(null, "任务已失效，可能已被其它人抢先执行！", null);
        } else {
        	pubRepairService.completeNodeDone(bean, request);
            return new ReturnInfo("1", null, "处理完成！");
        }
    }
    
    /**
     * 办结
     */
    @RequestMapping(value = "dealCompleteflow", method = RequestMethod.POST)
    @ResponseBody
    public ReturnInfo completeWorkflow(OaRepairEditBean bean, HttpServletRequest request) {
    	bean.setCurrentUser((SysUser)UserTool.getInstance().getCurrentUser());
    	OaEquipmentRepair obj = this.repairService.findOne(bean.getId());
        try {
            if (obj == null) {
                return new ReturnInfo("故障报告申请不存在，无法执行操作！", null);
            }
            pubRepairService.completeFlowData(bean, request);
            return new ReturnInfo("1", null, "办结成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "故障报告申请：《" + obj.getFailureReason() + "》办结失败！",LogStatus.失败,request);
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
    	OaEquipmentRepair obj = this.repairService.findOne(id);
        try {
            WfNodeInst nodeInst = nodeInstService.findOne(curNodeInstId);
            if (obj != null && nodeInst != null) {
            	pubRepairService.returnNodeInst(obj, nodeInst, backNodeId, backOpinion, user.getId(), request);
                return new ReturnInfo("1", null, "退回成功！");
            } else {
                return new ReturnInfo("申请不存在，无法执行操作。", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "故障报告申请《" + obj.getFailureReason() + "》退回失败！",LogStatus.失败, request);
            return new ReturnInfo("操作失败！" + e.getMessage(), null);
        }
    }
    
    /**
     * 我的故障报告申请列表页面
     */
    @RequestMapping("/listRepair")
    public ModelAndView myRepairList() {
        ModelAndView mav = new ModelAndView("/pubaffairs/repair/myrepairlist");
        return mav;
    }
    
    /**
     * 故障申请已办结列表页面
     */
    @RequestMapping("/listEnd")
    public ModelAndView listEnd() {
        ModelAndView mav = new ModelAndView("/pubaffairs/repair/endList");
        return mav;
    }
    
    @RequestMapping("/listTmp.json")
    @ResponseBody
    public Page tmplistjson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
        SearchBuilder<OaEquipmentRepair, String> searchBuilder = new SearchBuilder<OaEquipmentRepair, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        
        boolean flag = this.sysUserService.isOwnByRoleName("系统管理员");
        if(!flag){//系统管理员角色查看所有的车辆申请
        	searchBuilder.getFilterBuilder().equal("creatorId", UserTool.getInstance().getCurrentUserId());
        }
        
        if(StringUtils.isNotBlank(sort)){
        	sort = sort.replace("Show", "");
        	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
        }
        else{
        	searchBuilder.appendSort(Direction.DESC, "createDate");
        }
        
        SearchListHelper<OaEquipmentRepair> listhelper = new SearchListHelper<OaEquipmentRepair>();
        listhelper.execute(searchBuilder, repairService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), OaMyRepairShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }
    
    @RequestMapping("/listEnd.json")
    @ResponseBody
    public Page listEndJson(Integer page, Integer rows, HttpServletRequest request,String sort,String order) {
        SearchBuilder<OaEquipmentRepair, String> searchBuilder = new SearchBuilder<OaEquipmentRepair, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        
        searchBuilder.getFilterBuilder().equal("status", CarStatus.办结);
        
        if(StringUtils.isNotBlank(sort)){
        	sort = sort.replace("Show", "");
        	searchBuilder.appendSort("asc".equals(order)? Direction.ASC:Direction.DESC, sort);
        }
        else{
        	searchBuilder.appendSort(Direction.DESC, "createDate");
        }
        
        SearchListHelper<OaEquipmentRepair> listhelper = new SearchListHelper<OaEquipmentRepair>();
        listhelper.execute(searchBuilder, repairService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), OaMyRepairShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }
    
    /**
     * 我的故障报告申请详情页面
     */
    @RequestMapping("/detail")
    public ModelAndView detailPage(String id) {
    	ModelAndView mav = new ModelAndView("/pubaffairs/repair/repair_detail");
    	// 基本信息
    	OaEquipmentRepair obj = this.repairService.findOne(id);
    	if (obj == null) {
			mav = new ModelAndView("/doc/docevent/message");
			mav.addObject("hints", "公文信息读取失败，该公文可能已被删除，请刷新或重新进入列表页面。");
			return mav;
		}
    	// 读取审批意见
    	mav.addObject("opinionList",
    			auditOpinionService.findAuditOpinionsByFlowInstIdAndResult(obj.getFlowInstId(), WfApprResult.通过));
    	mav.addObject("readCount", this.readMatterService.selectReadMatterCount(id));

		mav.addObject("obj", obj);
		mav.addObject("type", WfBusinessType.故障报告申请.getKey());
        mav.addObject("attachMap", this.attachService.getAttachMap(obj.getId(), OaEquipmentRepair.class.getSimpleName()));

        return mav;
    }
   /**
    * 暂存状态故障报告申请删除
    * @param ids
    * @param request
    * @return
    */
    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo del(String ids, HttpServletRequest request) {
        try {
        	this.repairService.delRepairByIds(ids);//占不写入日志
            return new ReturnInfo("1", null, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
    
    
    /**
     * 故障报告申请编辑页面
     */
    @RequestMapping("/edit")
    public ModelAndView editPage(String id) {
    	ModelAndView mav = new ModelAndView("/pubaffairs/repair/new");
    	SysDepartment dept = (SysDepartment)UserTool.getInstance().getCurrentUserDepartment();
		SysUser currUser = (SysUser)UserTool.getInstance().getCurrentUser();
		OaEquipmentRepair obj = this.repairService.findOne(id);
    	mav.addObject("obj",obj);
    	mav.addObject("dept",dept);
		mav.addObject("currUser",currUser);
        mav.addObject("attachMap", this.attachService.getAttachMap(obj.getId(), OaEquipmentRepair.class.getSimpleName()));

        return mav;
    }
    
    /**
     * 故障报告申请暂存
     */
    @RequestMapping("/saveTemp.json")
    @ResponseBody
    public ReturnInfo saveTemp(OaRepairEditBean bean, HttpServletRequest request, HttpServletResponse response) {
    	try {
    		SysUser user = (SysUser)UserTool.getInstance().getCurrentUser();
    		bean.setCurrentUser(user);
    		ReturnBean ret = this.repairService.saveRepair(bean,request);
    		logService.saveLog(SjType.普通操作, "暂存故障报告申请",LogStatus.成功, request);
    		if(ret!=null && ret.getObject()!=null){
    			OaEquipmentRepair car=(OaEquipmentRepair) ret.getObject();
    			return new ReturnInfo(car.getId(), null, "暂存成功!");
    		}else{
    			return new ReturnInfo(null, "暂存失败！", null);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		return new ReturnInfo(null, "服务器连接不上！", null);
    	}
    }
    
    /**
     * 主动取回到当前环节
     */
    @RequestMapping("task/dealBringBack")
    @ResponseBody
    public ReturnInfo taskBringBack(String id, String curNodeId, HttpServletRequest request) {
    	SysUser user = (SysUser)UserTool.getInstance().getCurrentUser();
    	OaEquipmentRepair obj = this.repairService.findOne(id);
        try {
            if (obj != null) {
                String res = pubRepairService.bringBackNodeInst(obj, curNodeId, user, request);
                if (res == null) {
                	return new ReturnInfo(null, "取回成功！");
                } else {
                	return new ReturnInfo(res, null);
                }
            } else {
                return new ReturnInfo("故障报告申请不存在，无法取回", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "故障报告申请编号ID:" + obj.getId() + "取回失败！",LogStatus.失败, request);
            return new ReturnInfo("操作失败！" + e.getMessage(), null);
        }
    }
    
    
    /**
     * 删除、撤文
     */
    @RequestMapping(value = "deleteflow", method = RequestMethod.POST)
    @ResponseBody
    public ReturnInfo deleteWorkflow(String id, HttpServletRequest request) {
    	OaEquipmentRepair obj = this.repairService.findOne(id);
        try {
            if (obj == null) {
                return new ReturnInfo("故障报告申请不存在，无法执行操作！", null);
            }
            pubRepairService.deleteFlowData(id, request);
            return new ReturnInfo("1", null, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logService.saveLog(SjType.普通操作, "故障报告申请编号ID：" + obj.getId() + "删除失败！",LogStatus.失败,request);
            return new ReturnInfo(null, "结束流程失败：" + e.getMessage(), null);
        }
    }

}
