package cn.com.chaochuang.mobile.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.data.persistence.SearchFilter;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysDepartmentRepository;
import cn.com.chaochuang.common.user.repository.SysUserRepository;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.mobile.bean.AppMealInfo;
import cn.com.chaochuang.mobile.bean.AppResponseInfo;
import cn.com.chaochuang.mobile.util.AesTool;
import cn.com.chaochuang.pubaffairs.meal.bean.OaMealEditBean;
import cn.com.chaochuang.pubaffairs.meal.bean.OaMealShowBean;
import cn.com.chaochuang.pubaffairs.meal.bean.OaMealStatbean;
import cn.com.chaochuang.pubaffairs.meal.domain.OaMeal;
import cn.com.chaochuang.pubaffairs.meal.reference.MealType;
import cn.com.chaochuang.pubaffairs.meal.service.OaMealService;

/**
 * @author dengl 2018.9.5
 * 移动端订餐管理接口
 */

@Controller
@RequestMapping(value = "/mobile/meal")
public class MobileMealController extends MobileController {
	
	@Autowired
	private OaMealService 				oaMealService;
	
	@Autowired
	private SysDepartmentRepository 	departmentRepository;

	@Autowired
	private SysUserRepository 			userRepository;
	
	@Autowired
    protected ConversionService    		conversionService;

	 /** 预约订餐列表页面*/
    @SuppressWarnings("unchecked")
	@RequestMapping(value = {"/mealList.mo"})
    @ResponseBody
    public AppResponseInfo mealList(HttpServletRequest request, Boolean aesFlag_) {
    	SysUser user = this.loadCurrentUser(request);
    	if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
    	String page = request.getParameter("page");
        String rows = request.getParameter("rows");
    	
        //查询条件（可选）
    	String status = request.getParameter("status");
    	String mealPlace = request.getParameter("mealPlace");
    	String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String mealUserName = request.getParameter("mealUserName");
        
        Map<String, SearchFilter> thisFilters = new HashMap<String, SearchFilter>();
        Map<String, Object> searchParams = new TreeMap<String, Object>();
        
        if (StringUtils.isNotBlank(fromDate)) {
        	searchParams.put("GTE__m.mealDate", fromDate);
        }
        if (StringUtils.isNotBlank(toDate)) {
        	searchParams.put("LTE__m.mealDate", toDate);
        }
        if (StringUtils.isNotBlank(mealPlace)) {
        	searchParams.put("EQ__m.mealPlace", mealPlace);
        }
        if (StringUtils.isNotBlank(status)) {
        	searchParams.put("EQ__m.status", status);
        }
        if (StringUtils.isNotBlank(mealUserName)) {
        	searchParams.put("LIKE__m.mealUserName", mealUserName);
        }
        
        
        
        if (MapUtils.isNotEmpty(searchParams)) {
            thisFilters = SearchFilter.parse(searchParams);
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Page p = new Page();
        dataMap = this.oaMealService.mealSelect(thisFilters.values(), user, null, null, Integer.parseInt(page), Integer.parseInt(rows));
        p.setRows((ArrayList<OaMealShowBean>) dataMap.get("rows"));
        p.setTotal(dataMap.get("total") == null ? (long) 0 : (long) dataMap.get("total"));
        
        return AesTool.responseData(p,aesFlag_);
    }
    
    /** 删除订餐*/
    @RequestMapping("/delete.mo")
    @ResponseBody
    public AppResponseInfo delete(String ids, HttpServletRequest request, Boolean aesFlag_) {
        try {
            SysUser user = this.loadCurrentUser(request);
            if (user == null) {
                return AesTool.responseData("0", "用户不存在！", null,aesFlag_);
            }
            if (StringUtils.isNotBlank(ids)) {
            	this.oaMealService.delMealByIds(ids);// 占不写入日志
                return AesTool.responseData("1","删除成功",null,aesFlag_);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AesTool.responseData("0","操作失败",null,aesFlag_);
    }
    
    /** 订餐详情页面 */
    @RequestMapping(value = { "/detail.mo" })
    @ResponseBody
    public AppResponseInfo detail(HttpServletRequest request, Boolean aesFlag_) {
        String entityId = request.getParameter("id");
        if (StringUtils.isBlank(entityId)) {
            return AesTool.responseData("0", "申请ID为空，参数错误！", null, aesFlag_);
        }
        SysUser user = this.loadCurrentUser(request);
        if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }

        OaMeal obj = this.oaMealService.findOne(entityId);
        if (obj == null) {
            return AesTool.responseData("0", "找不到相应的申请！", null, aesFlag_);
        }
        AppMealInfo bean = BeanCopyUtil.copyBean(obj, AppMealInfo.class);
        bean.setCreatorName(this.userRepository.findOne(obj.getCreatorId()).getUserName());
        bean.setDeptName(this.departmentRepository.findOne(obj.getDeptId()).getDeptName());
        return AesTool.responseData(bean, aesFlag_);
    }
    
    /** 保存订餐*/
    @RequestMapping("/save.mo")
    @ResponseBody
    public AppResponseInfo save(OaMealEditBean bean, HttpServletRequest request, Boolean aesFlag_) {
    	try {
    		SysUser user = this.loadCurrentUser(request);
            if (user == null) {
                return AesTool.responseData("0", "用户不存在！", null,aesFlag_);
            }
            bean.setCreateTime(new Date());
            ReturnInfo info = this.oaMealService.saveMeal(bean,request,user);
    		if(StringUtils.isNotBlank(info.getError())){
    			String result = info.getError().replace("<br>", ",");
    			return AesTool.responseData("0",result,null,aesFlag_);
    		}
    		return AesTool.responseData("1","保存成功",null,aesFlag_);
    		
//    		return AesTool.responseData("0","保存失败",null,aesFlag_);
    	}catch (Exception e) {
            e.printStackTrace();
            return AesTool.responseData("0","连接不上服务器",null,aesFlag_);
        }
    }
    
    /** 订餐统计列表*/
    @SuppressWarnings("unchecked")
	@RequestMapping("/mealStatList.mo")
	@ResponseBody
	public AppResponseInfo mealStatList(HttpServletRequest request, Boolean aesFlag_) {
    	SysUser user = this.loadCurrentUser(request);
    	if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
    	String page = request.getParameter("page");
        String rows = request.getParameter("rows");
		String mealType = request.getParameter("mealType");
		String beginDate = request.getParameter("fromDate");
		String endDate = request.getParameter("toDate");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Page p = new Page();
		dataMap = this.oaMealService.mealStat(mealType, beginDate, endDate, Integer.parseInt(page), Integer.parseInt(rows), user);
		p.setRows((ArrayList<OaMealStatbean>) dataMap.get("rows"));
		p.setTotal(dataMap.get("total") == null ? (long) 0 : (long) dataMap.get("total"));
		return AesTool.responseData(p,aesFlag_);
	}
    
    /** 订餐办理详情列表 */
	@RequestMapping("/listDetail.mo")
    @ResponseBody
    public AppResponseInfo listjson(HttpServletRequest request, Boolean aesFlag_) {
		SysUser user = this.loadCurrentUser(request);
    	if (user == null) {
            return AesTool.responseData("0", "用户不存在！", null, aesFlag_);
        }
    	String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        SearchBuilder<OaMeal, String> searchBuilder = new SearchBuilder<OaMeal, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        
        String mealType = request.getParameter("mealType");
		String mealDate = request.getParameter("mealDate");
		String mealUserName = request.getParameter("mealUserName");
        
		if(StringUtils.isNotBlank(mealDate)){
			searchBuilder.getFilterBuilder().equal("mealDate", mealDate);
		}
		if (StringUtils.isNotBlank(mealType) && MealType.加班餐.getKey().equals(mealType)) {
			searchBuilder.getFilterBuilder().in("mealType",new Object[] { MealType.加班餐});
		} else if (StringUtils.isNotBlank(mealType) && !MealType.加班餐.getKey().equals(mealType)) {
			searchBuilder.getFilterBuilder().in("mealType",new Object[] { MealType.员工餐,MealType.接待餐,MealType.自费餐 });
		}
		
		if (StringUtils.isNoneBlank(mealUserName)){
			searchBuilder.getFilterBuilder().like("mealUserName", mealUserName);
		}
        
        searchBuilder.appendSort(Direction.ASC, "status");
        searchBuilder.appendSort(Direction.DESC, "createTime");
        SearchListHelper<OaMeal> listhelper = new SearchListHelper<OaMeal>();
        listhelper.execute(searchBuilder, oaMealService.getRepository(), Integer.parseInt(page), Integer.parseInt(rows));
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), OaMealShowBean.class));
        p.setTotal(listhelper.getCount());
        return AesTool.responseData(p,aesFlag_);
    }
	
	/** 订餐办结*/
	@RequestMapping("/doFinish.mo")
	@ResponseBody
	public AppResponseInfo doFinish(String ids, HttpServletRequest request, Boolean aesFlag_) {
		try {
			SysUser user = this.loadCurrentUser(request);
            if (user == null) {
                return AesTool.responseData("0", "用户不存在！", null,aesFlag_);
            }
			boolean a = this.oaMealService.doFinish(ids);// 占不写入日志
			if (a) {
				return AesTool.responseData("1","办结成功",null,aesFlag_);
			} else {
				return AesTool.responseData("0","办结失败",null,aesFlag_);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return AesTool.responseData("0","连接不上服务器",null,aesFlag_);
		}
	}

	/** 订餐退回*/
	@RequestMapping("/doBack.mo")
	@ResponseBody
	public AppResponseInfo doBack(String ids, HttpServletRequest request, Boolean aesFlag_) {
		try {
			SysUser user = this.loadCurrentUser(request);
            if (user == null) {
                return AesTool.responseData("0", "用户不存在！", null,aesFlag_);
            }
			boolean a = this.oaMealService.doBack(ids);// 占不写入日志
			if (a) {
				return AesTool.responseData("1","退回成功",null,aesFlag_);
			} else {
				return AesTool.responseData("0","退回失败",null,aesFlag_);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return AesTool.responseData("0","连接不上服务器",null,aesFlag_);
		}
	}
}
