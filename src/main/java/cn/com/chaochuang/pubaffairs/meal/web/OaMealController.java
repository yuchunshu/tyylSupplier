package cn.com.chaochuang.pubaffairs.meal.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysDepartmentRepository;
import cn.com.chaochuang.common.user.repository.SysUserRepository;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.pubaffairs.meal.bean.OaMealAllPersonBean;
import cn.com.chaochuang.pubaffairs.meal.bean.OaMealEditBean;
import cn.com.chaochuang.pubaffairs.meal.bean.OaMealShowBean;
import cn.com.chaochuang.pubaffairs.meal.bean.OaMealStatbean;
import cn.com.chaochuang.pubaffairs.meal.domain.OaMeal;
import cn.com.chaochuang.pubaffairs.meal.reference.MealType;
import cn.com.chaochuang.pubaffairs.meal.service.OaMealService;
import cn.com.chaochuang.pubaffairs.web.PubCommonController;
import net.sf.json.JSONArray;

/**
 * @author dengl 2018.08.13
 *
 */
@Controller
@RequestMapping("pubaffairs/meal")
public class OaMealController extends PubCommonController {

	@Autowired
	private OaMealService 				oaMealService;

	@Autowired
	private LogService 					logService;

	@Autowired
	private SysDepartmentRepository 	departmentRepository;

	@Autowired
	private SysUserRepository 			userRepository;

	/**
	 * 预约订餐列表页面
	 */
	@RequestMapping("/list")
	@ResponseBody
	public ModelAndView taskList() {
		ModelAndView mav = new ModelAndView("/pubaffairs/meal/list");
		return mav;
	}

	@RequestMapping("/list.json")
	@ResponseBody
	public Page listJson(Integer page, Integer rows, HttpServletRequest request, String sort, String order) {
		String mealType = request.getParameter("mealType");
		String mealDate = request.getParameter("mealDate");
		SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
		Map<String, SearchFilter> thisFilters = new HashMap<String, SearchFilter>();

		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "__");
		if (MapUtils.isNotEmpty(searchParams)) {
			thisFilters = SearchFilter.parse(searchParams);
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Page p = new Page();
		dataMap = this.oaMealService.mealSelect(thisFilters.values(), user, mealDate, mealType, page, rows);
		p.setRows((ArrayList<OaMealShowBean>) dataMap.get("rows"));
		p.setTotal(dataMap.get("total") == null ? (long) 0 : (long) dataMap.get("total"));
		return p;
	}
	
	/**
	 * 预约订餐新增页面
	 */
	@RequestMapping("/new")
	public ModelAndView newPage() {
		return super.openNewPage(null);
	}

	/**
	 * 订餐保存
	 */
	@RequestMapping("/save.json")
	@ResponseBody
	public ReturnInfo save(OaMealEditBean bean, HttpServletRequest request, HttpServletResponse response) {
		try {
			SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
			bean.setCreateTime(new Date());
			bean.setCreatorId(user.getId());
			bean.setDeptId(user.getDeptId());
			
			return this.oaMealService.saveMeal(bean, request,user);
    	} catch (Exception e) {
    		e.printStackTrace();
    		logService.saveLog(SjType.普通操作, "保存预约订餐失败！",LogStatus.失败, request);
    		return new ReturnInfo(null, "服务器连接不上！", null);
    	}
		
		
	}

	/**
	 * 通讯录选择列表页面
	 */
	@RequestMapping("/mailList")
	public ModelAndView mailList() {
		ModelAndView mav = new ModelAndView("/pubaffairs/meal/mailList");
		return mav;
	}

	/**
	 * 预约订餐编辑页面
	 */
	@RequestMapping("/edit")
	public ModelAndView editPage(String id) {
		ModelAndView mav = new ModelAndView("/pubaffairs/meal/edit");
		SysDepartment dept = (SysDepartment) UserTool.getInstance().getCurrentUserDepartment();
		SysUser currUser = (SysUser) UserTool.getInstance().getCurrentUser();
		OaMeal obj = this.oaMealService.findOne(id);
		mav.addObject("obj", obj);
		mav.addObject("dept", dept);
		mav.addObject("currUser", currUser);
		return mav;
	}

	/**
	 * 预约订餐删除
	 * 
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete.json")
	@ResponseBody
	public ReturnInfo del(String ids, HttpServletRequest request) {
		try {
			this.oaMealService.delMealByIds(ids);// 占不写入日志
			return new ReturnInfo("1", null, "删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnInfo(null, "服务器连接不上！", null);
		}
	}

	/**
	 * 订餐详情页面
	 */
	@RequestMapping("/detail")
	public ModelAndView detailPage(String id) {
		ModelAndView mav = new ModelAndView("/pubaffairs/meal/detail");
		// 基本信息
		OaMeal obj = this.oaMealService.findOne(id);
		if (obj == null) {
			mav = new ModelAndView("/doc/docevent/message");
			mav.addObject("hints", "订餐信息读取失败，该订餐可能已被删除，请刷新或重新进入列表页面。");
			return mav;
		}
		SysDepartment dept = this.departmentRepository.findOne(obj.getDeptId());
		SysUser user = this.userRepository.findOne(obj.getCreatorId());
		mav.addObject("obj", obj);
		mav.addObject("dept", dept);
		mav.addObject("user", user);
		return mav;
	}

	/**
	 * 订餐统计页面
	 */
	@RequestMapping("/listStat")
	public ModelAndView statList() {
		ModelAndView mav = new ModelAndView("/pubaffairs/meal/stat/list");
		return mav;
	}

	@RequestMapping("/listStat.json")
	@ResponseBody
	public Page listStatJson(Integer page, Integer rows, HttpServletRequest request, String sort, String order) {
		String mealType = request.getParameter("mealType");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Page p = new Page();
		dataMap = this.oaMealService.mealStat(mealType, beginDate, endDate, page, rows, user);
		p.setRows((ArrayList<OaMealStatbean>) dataMap.get("rows"));
		p.setTotal(dataMap.get("total") == null ? (long) 0 : (long) dataMap.get("total"));
		return p;
	}
	
	/** 订餐统计列表导出 */
    @RequestMapping("/exportMealStaList.json")
    public ModelAndView mealStaListExport(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/pubaffairs/meal/stat/excel/mealStaListExport");
        mav.addObject("page", this.listStatJson(1, 1000000000, request,null,null));
        mav.addObject("fileName", "订餐统计列表");

        return mav;
    }

	/** 订餐办理页面 */
	@RequestMapping("task/deal")
	public ModelAndView dealPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/pubaffairs/meal/stat/detail");
		String mealType = request.getParameter("mealType");
		String mealDate = request.getParameter("mealDate");
		if (MealType.加班餐.equals(mealType)) {
			mav.addObject("mealType", MealType.加班餐);
		} else {
			mav.addObject("mealType", mealType);
		}
		mav.addObject("mealDate", mealDate);
		return mav;
	}
	
	/** 订餐办理详情列表 */
	@RequestMapping("/listDetail.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
    	SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        SearchBuilder<OaMeal, String> searchBuilder = new SearchBuilder<OaMeal, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        
        String mealType = request.getParameter("mealType");
		String mealDate = request.getParameter("mealDate");
        
		if(StringUtils.isNotBlank(mealDate)){
			searchBuilder.getFilterBuilder().equal("mealDate", mealDate);
		}
		if (StringUtils.isNotBlank(mealType) && MealType.加班餐.getKey().equals(mealType)) {
			searchBuilder.getFilterBuilder().in("mealType",new Object[] { MealType.加班餐});
		} else if (StringUtils.isNotBlank(mealType) && !MealType.加班餐.getKey().equals(mealType)) {
			searchBuilder.getFilterBuilder().in("mealType",new Object[] { MealType.员工餐,MealType.接待餐,MealType.自费餐 });
		}
        
        searchBuilder.appendSort(Direction.ASC, "status");
        searchBuilder.appendSort(Direction.DESC, "createTime");
        SearchListHelper<OaMeal> listhelper = new SearchListHelper<OaMeal>();
        listhelper.execute(searchBuilder, oaMealService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), OaMealShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

	/**
	 * 订餐办结
	 * 
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping("/doFinish.json")
	@ResponseBody
	public ReturnInfo doFinish(String ids, HttpServletRequest request) {
		try {
			boolean a = this.oaMealService.doFinish(ids);// 占不写入日志
			if (a) {
				return new ReturnInfo("1", null, "办结成功!");
			} else {
				return new ReturnInfo(null, "办结失败!", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnInfo(null, "服务器连接不上！", null);
		}
	}

	/**
	 * 订餐退回
	 * 
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping("/doBack.json")
	@ResponseBody
	public ReturnInfo doBack(String ids, HttpServletRequest request) {
		try {
			boolean a = this.oaMealService.doBack(ids);// 占不写入日志
			if (a) {
				return new ReturnInfo("1", null, "退回成功!");
			} else {
				return new ReturnInfo(null, "退回失败!", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnInfo(null, "服务器连接不上！", null);
		}
	}

	/**
	 * 全体人员订餐统计页面
	 */
	@RequestMapping("/statAllPersonnel")
	public ModelAndView statAllPersonnel(String mealDate, String mealType, String mealPlace) {
		ModelAndView mav = new ModelAndView("/pubaffairs/meal/stat/parse/allPersonSwsign");
		SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
		// 获取全中心用餐人员信息集合
		List<OaMealAllPersonBean> allDataList = this.oaMealService.getAllPersonData(Long.valueOf(1), 1, mealDate,
				mealType, user);
		// 如果是查询非加班，则获取实习、接待用餐人员信息集合
		int count = 0;
		List<OaMealAllPersonBean> receptDataList = new ArrayList<OaMealAllPersonBean>();
		List<OaMealAllPersonBean> practiceDataList = new ArrayList<OaMealAllPersonBean>();
		if(StringUtils.isNotBlank(mealType) && !MealType.加班餐.getKey().equals(mealType)){
			receptDataList = this.oaMealService.getMealData(
					Tools.changeArrayToString(new String[] { MealType.接待餐.getKey() }, ",", false), mealDate, mealPlace, user);
			practiceDataList = this.oaMealService.getMealData(
					Tools.changeArrayToString(new String[] { MealType.自费餐.getKey() }, ",", false), mealDate, mealPlace, user);
			// 把全中心用餐人员信息和实习、接待用餐人员信息合并
			allDataList.addAll(receptDataList);
			allDataList.addAll(practiceDataList);
			// 获取全中心人员总数和实习、接待用餐人员总数
			count = this.userRepository.findByDeptIdAndValid(Long.valueOf(1), 1) + receptDataList.size() + practiceDataList.size();
		}else{
			// 获取全中心人员总数
			count = this.userRepository.findByDeptIdAndValid(Long.valueOf(1), 1);
		}
		
		String tableChartDatas = JSONArray.fromObject(allDataList).toString();
		
		// 获取每列人员总数
		int columnCount = (int) Math.ceil(((double) count) / 3);
		int workCount = 0;
		mav.addObject("tableChartDatas", tableChartDatas);
		mav.addObject("count", count);
		mav.addObject("columnCount", columnCount);
		mav.addObject("mealDate", mealDate);
		if (StringUtils.isNotBlank(mealType) && MealType.加班餐.getKey().equals(mealType)) {
			workCount = this.oaMealService.getMealData(Tools.changeArrayToString(new String[] { MealType.加班餐.getKey() }, ",", false), mealDate, mealPlace, user).size();
			// 加班餐总数
			mav.addObject("workCount", workCount);
		} else {
			workCount = this.oaMealService.getMealData(Tools.changeArrayToString(new String[] { MealType.员工餐.getKey() }, ",", false), mealDate, mealPlace, user).size();
			// 员工餐总数
			mav.addObject("workCount", workCount);
		}
		// 自费餐总数
		mav.addObject("practiceCount", practiceDataList.size());
		// 接待餐总数
		mav.addObject("receptCount", receptDataList.size());
		// 总计用餐总数
		mav.addObject("number", workCount + receptDataList.size() + practiceDataList.size());
		// 用餐类型
		mav.addObject("mealType", mealType);
		return mav;
	}

	/**
	 * 青湖加班订餐/员工订餐统计页面
	 */
	@RequestMapping("/statQH")
	public ModelAndView statQH(String mealDate, String mealType, String mealPlace) {
		ModelAndView mav = new ModelAndView("/pubaffairs/meal/stat/parse/QHSwsign");
		SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
		// 获取青湖全中心用餐人员信息集合
		List<OaMealAllPersonBean> allDataList = new ArrayList<OaMealAllPersonBean>();
		if (StringUtils.isNotBlank(mealType) && MealType.加班餐.getKey().equals(mealType)) {
			allDataList = this.oaMealService.getMealData(
					Tools.changeArrayToString(new String[] { MealType.加班餐.getKey() }, ",", false), mealDate, mealPlace, user);
		} else {
			allDataList = this.oaMealService.getMealData(
					Tools.changeArrayToString(new String[] { MealType.员工餐.getKey() }, ",", false), mealDate, mealPlace, user);
		}
		
		
		List<OaMealAllPersonBean> receptDataList = new ArrayList<OaMealAllPersonBean>();
		List<OaMealAllPersonBean> practiceDataList = new ArrayList<OaMealAllPersonBean>();
		if (StringUtils.isNotBlank(mealType) && !MealType.加班餐.getKey().equals(mealType)) {
			// 获取青湖实习、接待用餐人员信息集合
			receptDataList = this.oaMealService.getMealData(
					Tools.changeArrayToString(new String[] { MealType.接待餐.getKey() }, ",", false), mealDate, mealPlace, user);
			practiceDataList = this.oaMealService.getMealData(
					Tools.changeArrayToString(new String[] { MealType.自费餐.getKey() }, ",", false), mealDate, mealPlace, user);
		}
		
		// 青湖加班、员工餐总数
		mav.addObject("workCount", allDataList.size());
		// 青湖总计用餐总数
		mav.addObject("number", allDataList.size() + receptDataList.size() + practiceDataList.size());
		// 获取青湖全中心人员总数和实习、接待用餐人员总数
		int count = allDataList.size() + receptDataList.size() + practiceDataList.size();
		// 获取每列人员总数
		int columnCount = (int) Math.ceil(((double) count) / 3);
		mav.addObject("count", count);
		mav.addObject("columnCount", columnCount);
		// 把青湖全中心用餐人员信息和实习、接待用餐人员信息合并
		allDataList.addAll(receptDataList);
		allDataList.addAll(practiceDataList);
		String tableChartDatas = JSONArray.fromObject(allDataList).toString();

		mav.addObject("tableChartDatas", tableChartDatas);
		mav.addObject("mealDate", mealDate);

		// 青湖自费餐总数
		mav.addObject("practiceCount", practiceDataList.size());
		// 青湖接待餐总数
		mav.addObject("receptCount", receptDataList.size());
		// 用餐类型
		mav.addObject("mealType", mealType);
		return mav;
	}

	/**
	 * 明秀加班订餐/员工订餐统计页面
	 */
	@RequestMapping("/statMX")
	public ModelAndView statMX(String mealDate, String mealType, String mealPlace) {
		ModelAndView mav = new ModelAndView("/pubaffairs/meal/stat/parse/MXSwsign");
		SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
		// 获取明秀全中心用餐人员信息集合
		List<OaMealAllPersonBean> allDataList = new ArrayList<OaMealAllPersonBean>();
		if (StringUtils.isNotBlank(mealType) && MealType.加班餐.getKey().equals(mealType)) {
			allDataList = this.oaMealService.getMealData(
					Tools.changeArrayToString(new String[] { MealType.加班餐.getKey() }, ",", false), mealDate, mealPlace, user);
		} else {
			allDataList = this.oaMealService.getMealData(
					Tools.changeArrayToString(new String[] { MealType.员工餐.getKey() }, ",", false), mealDate, mealPlace, user);
		}
		List<OaMealAllPersonBean> receptDataList = new ArrayList<OaMealAllPersonBean>();
		List<OaMealAllPersonBean> practiceDataList = new ArrayList<OaMealAllPersonBean>();
		if (StringUtils.isNotBlank(mealType) && !MealType.加班餐.getKey().equals(mealType)) {
			// 获取青湖实习、接待用餐人员信息集合
			receptDataList = this.oaMealService.getMealData(
					Tools.changeArrayToString(new String[] { MealType.接待餐.getKey() }, ",", false), mealDate, mealPlace, user);
			practiceDataList = this.oaMealService.getMealData(
					Tools.changeArrayToString(new String[] { MealType.自费餐.getKey() }, ",", false), mealDate, mealPlace, user);
		}
		// 明秀加班、员工餐总数
		mav.addObject("workCount", allDataList.size());
		// 明秀总计用餐总数
		mav.addObject("number", allDataList.size() + receptDataList.size() + practiceDataList.size());
		// 获取明秀全中心人员总数和实习、接待用餐人员总数
		int count = allDataList.size() + receptDataList.size() + practiceDataList.size();
		// 获取每列人员总数
		int columnCount = (int) Math.ceil(((double) count) / 3);
		mav.addObject("count", count);
		mav.addObject("columnCount", columnCount);
		// 把明秀全中心用餐人员信息和实习、接待用餐人员信息合并
		allDataList.addAll(receptDataList);
		allDataList.addAll(practiceDataList);
		String tableChartDatas = JSONArray.fromObject(allDataList).toString();

		mav.addObject("tableChartDatas", tableChartDatas);
		mav.addObject("mealDate", mealDate);

		// 明秀自费餐总数
		mav.addObject("practiceCount", practiceDataList.size());
		// 明秀接待餐总数
		mav.addObject("receptCount", receptDataList.size());
		// 用餐类型
		mav.addObject("mealType", mealType);
		return mav;
	}
}
