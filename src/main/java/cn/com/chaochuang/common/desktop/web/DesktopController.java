/**
 *
 */
package cn.com.chaochuang.common.desktop.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.com.chaochuang.workflow.inst.repository.WfNodeInstRepository;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.data.persistence.SearchFilter;
import cn.com.chaochuang.common.desktop.bean.DisplayModelInfo;
import cn.com.chaochuang.common.desktop.bean.ShortcutInfo;
import cn.com.chaochuang.common.desktop.domain.DesktopShortcut;
import cn.com.chaochuang.common.desktop.service.ShortcutService;
import cn.com.chaochuang.common.desktop.service.UserProfileService;
import cn.com.chaochuang.common.reference.FlowSort;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.syspower.domain.SysPower;
import cn.com.chaochuang.common.syspower.service.SysPowerService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.oa.mail.bean.InboxShowBean;
import cn.com.chaochuang.oa.mail.domain.EmIncepter;
import cn.com.chaochuang.oa.mail.reference.IncepterStatus;
import cn.com.chaochuang.oa.mail.reference.MailStatus;
import cn.com.chaochuang.oa.mail.service.EmIncepterService;
import cn.com.chaochuang.oa.notice.service.OaNoticeService;
import cn.com.chaochuang.pubaffairs.car.bean.OfficialCarShowBean;
import cn.com.chaochuang.pubaffairs.car.service.OfficialCarService;
import cn.com.chaochuang.pubaffairs.leave.bean.OaLeaveQueryBean;
import cn.com.chaochuang.pubaffairs.leave.service.OaLeaveService;
import cn.com.chaochuang.pubaffairs.repair.bean.OaRepairShowBean;
import cn.com.chaochuang.pubaffairs.repair.service.OaRepairService;
import cn.com.chaochuang.workflow.event.bean.TaskShowBean;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfNodeInstService;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.workflow.reference.WfInstStatus;
import cn.com.chaochuang.workflow.util.WorkflowUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author hzr 2016年7月21日 个人桌面
 */
@Controller
@RequestMapping("desktop")
public class DesktopController {

    @Autowired
    private OaNoticeService          noticeService;

    @Autowired
    protected ConversionService      conversionService;

    @Autowired
    private SysPowerService          powerService;

    @Autowired
    private EmIncepterService        incepterService;

    @Autowired
    private ShortcutService          shortcutService;

    @Autowired
    private UserProfileService       profileService;

    @Autowired
    private WfNodeInstService        nodeInstService;

    @Autowired
    private WfNodeInstRepository     nodeInstRepository;
    
    @Autowired
    private OfficialCarService		 carService;
    
    @Autowired
    private OaLeaveService      	 oaLeaveService;
    
	@Autowired
	private OaRepairService  		 repairService;
    
    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/desktop/index");

        // mav.addObject("model",
        // this.powerService.selectPower(null, Long.valueOf(UserTool.getInstance().getCurrentUserId())));

        mav.addObject("model", this.powerService.getCurAllMenus());
        return mav;
    }

    @RequestMapping("/menus")
    public ModelAndView menus(Long powerId) {
        ModelAndView mav = new ModelAndView("/desktop/menus");
        mav.addObject("menus", this.powerService.getMenus(powerId));
        mav.addObject("powerId", powerId);
        return mav;
    }

    // 保存模块顺序
    @RequestMapping("/saveDisplayModels.json")
    @ResponseBody
    public ReturnInfo saveDisplayModels(String displayModels) {
        try {
            Long id = profileService.saveDisplayModels(displayModels);
            return new ReturnInfo(id.toString(), null, "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReturnInfo(null, "服务器连接不上！", null);
    }

    // 首页入口
    @RequestMapping("/content/index")
    public ModelAndView index(HttpServletRequest request) {
        Long userId = Long.valueOf(UserTool.getInstance().getCurrentUserId());
        ModelAndView mav = new ModelAndView("/desktop/indexContent");
        List<DisplayModelInfo> displayModels = profileService.getUserDisplayModels();
        addDisplayModelsToMAV(mav, displayModels, request);
        List<DisplayModelInfo> left = new ArrayList<DisplayModelInfo>();
        List<DisplayModelInfo> right = new ArrayList<DisplayModelInfo>();
        if (Tools.isNotEmptyList(displayModels)) {
            for (DisplayModelInfo info : displayModels) {
                if ("left".equals(info.getLocation())) {
                    left.add(info);
                }
                if ("right".equals(info.getLocation())) {
                    right.add(info);
                }
            }
        }
        mav.addObject("curShortcuts", this.shortcutService.findShortcuts(userId));          // 我的桌面
        mav.addObject("displayModelsLeft", left);
        mav.addObject("displayModelsRight", right);

        //首页固定的两个模块
        mav.addObject("taskContentList", this.taskContent(request));
        mav.addObject("noticeContentList", this.noticeContent(request));

        mav.addObject("taskContentList", this.taskContent(request));
        mav.addObject("passreadContentList", this.passreadContent(request));
        mav.addObject("readContentList", this.readContent(request));
        mav.addObject("notReadMailContentList", this.notReadMailContent(request));
        
        mav.addObject("carContentList", this.carContent(request));
        mav.addObject("leaveContentList", this.leaveContent(request));
        mav.addObject("repairContentList", this.repairContent(request));
        mav.addObject("mealContentList", this.mealContent(request));
        return mav;
    }

    // 自定义快捷图标
    @RequestMapping("/setShortcut")
    public ModelAndView setShortcut(HttpServletRequest request) {
        Long userId = Long.valueOf(UserTool.getInstance().getCurrentUserId());

        ModelAndView mav = new ModelAndView("/desktop/setShortcut");
        mav.addObject("curAllMenus", JSONObject.toJSONString(this.powerService.selectCurAllMenus(userId, null)));
        mav.addObject("curShortcuts", JSONObject.toJSONString(this.shortcutService.findShortcuts(userId)));
        return mav;
    }

    // 所有权限菜单
    @RequestMapping("/curAllMenus.json")
    @ResponseBody
    public List<SysPower> curAllMenus(String title, HttpServletRequest request) {
        Long userId = Long.valueOf(UserTool.getInstance().getCurrentUserId());
        return this.powerService.selectCurAllMenus(userId, title);
    }

    // 保存我的桌面图标
    @RequestMapping("/saveShortcut.json")
    @ResponseBody
    public boolean saveShortcut(ShortcutInfo info, HttpServletRequest request) {
        return this.shortcutService.saveShortcut(info);
    }

    // 我的桌面图标s
    @RequestMapping("/curShortcuts.json")
    @ResponseBody
    public List<DesktopShortcut> curShortcuts(HttpServletRequest request) {
        Long userId = Long.valueOf(UserTool.getInstance().getCurrentUserId());
        return this.shortcutService.findShortcuts(userId);
    }

    // 将桌面显示模块数据添加到mav中
    private void addDisplayModelsToMAV(ModelAndView mav, List<DisplayModelInfo> displayModels,
                    HttpServletRequest request) {
        if (mav != null) {
            if (Tools.isNotEmptyList(displayModels)) {
                Method method = null;
                Class<DesktopController> clazz = DesktopController.class;
                Class<HttpServletRequest> requestClazz = HttpServletRequest.class;
                for (DisplayModelInfo model : displayModels) {
                    try {
                        method = clazz.getDeclaredMethod(model.getName(), requestClazz);
                        if (method != null) {
                            mav.addObject(model.getName() + "List", method.invoke(this, request));
                        }
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException
                                    | IllegalArgumentException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 以下是桌面显示模块取数据的方法 方法名与vm页面 同名。

    // 通知
    @SuppressWarnings("unused")
    private Page noticeContent(HttpServletRequest request) {
    	String title = request.getParameter("title");
        String departmentId = request.getParameter("departmentId");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String currentDeptId = UserTool.getInstance().getCurrentUserDepartmentId();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dt1 = null, dt2 = null;
        try {
            if (StringUtils.isNotBlank(fromDate)) {
                dt1 = df.parse(fromDate + " 00:00");
            }
            if (StringUtils.isNotBlank(toDate)) {
                dt2 = df.parse(toDate + " 23:59");
            }
        } catch (ParseException e) {

        }
        return noticeService.selectAllForDeptShow(title, departmentId, dt1, dt2, currentDeptId, 1, 100000);
    }

    // 待办收发文
    @SuppressWarnings("unused")
    private Page taskContent(HttpServletRequest request) {
        SearchBuilder<WfNodeInst, Long> searchBuilder = new SearchBuilder<WfNodeInst, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("dealerId", UserTool.getInstance().getCurrentUserId());
        searchBuilder.getFilterBuilder().equal("instStatus", WfInstStatus.在办);
        searchBuilder.getFilterBuilder().notEqual("curNode.flow.flowType", WfBusinessType.内部请示);
        searchBuilder.getFilterBuilder().equal("curNode.flow.flowSort", FlowSort.公文);
        searchBuilder.getFilterBuilder().notEqual("curNodeId", WorkflowUtils.NODE_READ);
        searchBuilder.appendSort(Direction.DESC, "arrivalTime");
        SearchListHelper<WfNodeInst> listhelper = new SearchListHelper<WfNodeInst>();
        listhelper.execute(searchBuilder, nodeInstService.getRepository(), 1, 5);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), TaskShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }
    
    // 待办内部请示
    @SuppressWarnings("unused")
    private Page passreadContent(HttpServletRequest request) {
        SearchBuilder<WfNodeInst, Long> searchBuilder = new SearchBuilder<WfNodeInst, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("dealerId", UserTool.getInstance().getCurrentUserId());
        searchBuilder.getFilterBuilder().equal("instStatus", WfInstStatus.在办);
        searchBuilder.getFilterBuilder().equal("curNode.flow.flowType", WfBusinessType.内部请示);
        searchBuilder.getFilterBuilder().notEqual("curNodeId", WorkflowUtils.NODE_READ);
        searchBuilder.appendSort(Direction.DESC, "arrivalTime");
        SearchListHelper<WfNodeInst> listhelper = new SearchListHelper<WfNodeInst>();
        listhelper.execute(searchBuilder, nodeInstService.getRepository(), 1, 5);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), TaskShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    // 待阅
    @SuppressWarnings("unused")
    private Page readContent(HttpServletRequest request) {
        SearchBuilder<WfNodeInst, Long> searchBuilder = new SearchBuilder<WfNodeInst, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("dealerId", UserTool.getInstance().getCurrentUserId());
        searchBuilder.getFilterBuilder().equal("instStatus", WfInstStatus.在办);
        searchBuilder.getFilterBuilder().equal("curNodeId", WorkflowUtils.NODE_READ);
        searchBuilder.appendSort(Direction.DESC, "arrivalTime");
        SearchListHelper<WfNodeInst> listhelper = new SearchListHelper<WfNodeInst>();
        listhelper.execute(searchBuilder, nodeInstService.getRepository(), 1, 5);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), TaskShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    // 邮件
    @SuppressWarnings("unused")
    private Page mailContent(HttpServletRequest request) {
        SearchBuilder<EmIncepter, Long> searchBuilder = new SearchBuilder<EmIncepter, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        searchBuilder.getFilterBuilder().equal("incepter", user);
        searchBuilder.getFilterBuilder().in("status", new Object[] { IncepterStatus.已收件, IncepterStatus.未处理 });
        searchBuilder.getFilterBuilder().notEqual("mail.status", MailStatus.垃圾);
        searchBuilder.appendSort(Direction.DESC, "mail.sendDate");
        SearchListHelper<EmIncepter> listhelper = new SearchListHelper<EmIncepter>();
        listhelper.execute(searchBuilder, incepterService.getRepository(), 1, 5);

        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), InboxShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    /**
     * 预警待办,查询用户即将过期的待办数据
     * @param request
     * @return
     */
    private Page warnTaskContent(HttpServletRequest request) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        org.springframework.data.domain.Page<WfNodeInst> pageData = nodeInstRepository.findWarnNodeInst(user.getId(),WfBusinessType.内部请示,WorkflowUtils.NODE_READ,Tools.DATE_TIME_FORMAT.format(new Date()),new PageRequest(0,10));
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(pageData.getContent(),TaskShowBean.class));
        p.setTotal(pageData.getTotalElements());
        return p;
    }

    /**
     * 超期待办,查询用户即过期的待办数据
     * @param request
     * @return
     */
    private Page expireTaskContent(HttpServletRequest request) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        org.springframework.data.domain.Page<WfNodeInst> pageData = nodeInstRepository.findExpireNodeInst(user.getId(),WfBusinessType.内部请示,WorkflowUtils.NODE_READ,Tools.DATE_TIME_FORMAT.format(new Date()),new PageRequest(0,10));
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(pageData.getContent(),TaskShowBean.class));
        p.setTotal(pageData.getTotalElements());
        return p;
    }

    // 未读邮件
    @RequestMapping("/notReadMailContent")
    @ResponseBody
    private Page notReadMailContent(HttpServletRequest request) {
        SearchBuilder<EmIncepter, Long> searchBuilder = new SearchBuilder<EmIncepter, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        searchBuilder.getFilterBuilder().equal("incepter", user);
        searchBuilder.getFilterBuilder().in("status", new Object[] { IncepterStatus.未处理 });
        searchBuilder.getFilterBuilder().notEqual("mail.status", MailStatus.垃圾);
        searchBuilder.appendSort(Direction.DESC, "mail.sendDate");
        SearchListHelper<EmIncepter> listhelper = new SearchListHelper<EmIncepter>();
        listhelper.execute(searchBuilder, incepterService.getRepository(), 1, 5);

        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), InboxShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }
    
    // 车辆审核
    @RequestMapping(value = "carContent")
    @ResponseBody
    public Page carContent(HttpServletRequest request) {
    	SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
    	Map<String, SearchFilter> thisFilters = new HashMap<String, SearchFilter>();

        Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "__");
        if (MapUtils.isNotEmpty(searchParams)) {
            thisFilters = SearchFilter.parse(searchParams);
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Page p = new Page();
        dataMap = this.carService.carSelect(thisFilters.values(),user,1,5);
        p.setRows((ArrayList<OfficialCarShowBean>) dataMap.get("rows"));
        p.setTotal(dataMap.get("total") == null ? (long) 0 : (long) dataMap.get("total"));
        return p;
    }
    
    // 休假审核
    @RequestMapping("/leaveContent")
    @ResponseBody
    public Page leaveContent(HttpServletRequest request) {
    	SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        Map<String, SearchFilter> thisFilters = new HashMap<String, SearchFilter>();
        Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "__");
        if (MapUtils.isNotEmpty(searchParams)) {
            thisFilters = SearchFilter.parse(searchParams);
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Page p = new Page();
        dataMap = this.oaLeaveService.leaveSelect(thisFilters.values(),user,1,5);
        p.setRows((ArrayList<OaLeaveQueryBean>) dataMap.get("rows"));
        p.setTotal(dataMap.get("total") == null ? (long) 0 : (long) dataMap.get("total"));
        return p;
    }
    
    // 故障审核
    @RequestMapping("/repairContent")
    @ResponseBody
    public Page repairContent(HttpServletRequest request) {
    	SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        Map<String, SearchFilter> thisFilters = new HashMap<String, SearchFilter>();
        Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "__");
        if (MapUtils.isNotEmpty(searchParams)) {
            thisFilters = SearchFilter.parse(searchParams);
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Page p = new Page();
        dataMap = this.repairService.repairSelect(thisFilters.values(),user,1,5);
        p.setRows((ArrayList<OaRepairShowBean>) dataMap.get("rows"));
        p.setTotal(dataMap.get("total") == null ? (long) 0 : (long) dataMap.get("total"));
        return p;
    }
    
    // 订餐审核
    @RequestMapping(value = "mealContent")
	@ResponseBody
	public Page mealContent(HttpServletRequest request) {
//		Map<String, SearchFilter> thisFilters = new HashMap<String, SearchFilter>();
//
//		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "__");
//		if (MapUtils.isNotEmpty(searchParams)) {
//			thisFilters = SearchFilter.parse(searchParams);
//		}
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		Page p = new Page();
//		dataMap = this.purchaseService.purSelect(thisFilters.values(),1,20);
//		p.setRows((ArrayList<PurchaseAuditShowBean>) dataMap.get("rows"));
//		p.setTotal(dataMap.get("total") == null ? (long) 0 : (long) dataMap.get("total"));
		return null;
	}
    
}
