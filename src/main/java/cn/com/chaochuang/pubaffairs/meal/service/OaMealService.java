package cn.com.chaochuang.pubaffairs.meal.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.data.persistence.SearchFilter;
import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.pubaffairs.meal.bean.OaMealAllPersonBean;
import cn.com.chaochuang.pubaffairs.meal.bean.OaMealEditBean;
import cn.com.chaochuang.pubaffairs.meal.domain.OaMeal;

/**
 * @author dengl 2018.08.13
 *
 */

public interface OaMealService extends CrudRestService<OaMeal, String>{
	
	/** 保存订餐*/
	public ReturnInfo saveMeal(OaMealEditBean bean,HttpServletRequest request,SysUser u);
	
	/**	批量删除暂存状态休假申请*/
	public void delMealByIds(String ids);
    
    /** 预约订餐查询*/
    public Map<String, Object> mealSelect(Collection<SearchFilter> searchFilters, SysUser user, String mealDate, String mealType, Integer page, Integer rows);
    
    /** 订餐统计查询*/
    public Map<String, Object> mealStat(String mealType, String beginDate, String endDate, Integer page, Integer rows, SysUser user);
    
    /** 订餐办结*/
    public Boolean doFinish(String ids);
    
    /** 订餐退回*/
    public Boolean doBack(String ids);
    
    /** 获取全体人员订餐人员名单*/
    public List<OaMealAllPersonBean> getAllPersonData(Long deptId, Integer valid, String mealDate, String mealType, SysUser user);
    
    /** 根据用餐时间和用餐类型获取用餐人员名单*/
	public List<OaMealAllPersonBean> getMealData(String type, String mealDate, String mealPlace, SysUser user);
}
