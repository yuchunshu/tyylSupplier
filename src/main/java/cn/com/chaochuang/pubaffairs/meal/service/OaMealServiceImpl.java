package cn.com.chaochuang.pubaffairs.meal.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.data.persistence.SearchFilter;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysDepartmentRepository;
import cn.com.chaochuang.common.user.repository.SysUserRepository;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.meal.bean.OaMealAllPersonBean;
import cn.com.chaochuang.pubaffairs.meal.bean.OaMealEditBean;
import cn.com.chaochuang.pubaffairs.meal.bean.OaMealShowBean;
import cn.com.chaochuang.pubaffairs.meal.bean.OaMealStatbean;
import cn.com.chaochuang.pubaffairs.meal.domain.OaMeal;
import cn.com.chaochuang.pubaffairs.meal.reference.MealPlace;
import cn.com.chaochuang.pubaffairs.meal.reference.MealType;
import cn.com.chaochuang.pubaffairs.meal.repository.OaMealRepository;

/**
 * @author dengl 2018.08.13
 *
 */
@Service
@Transactional
public class OaMealServiceImpl extends SimpleUuidCrudRestService<OaMeal> implements OaMealService {

	@Autowired
	private OaMealRepository 				repository;

	@PersistenceContext
	private EntityManager 					em;

	@Autowired
	private SysUserRepository 				sysUserRepository;
	
	@Autowired
	private SysDepartmentRepository			departmentRepository;
	
	@Autowired
	private LogService 						logService;

	@Override
	public SimpleDomainRepository<OaMeal, String> getRepository() {
		return this.repository;
	}

	@Override
	public ReturnInfo saveMeal(OaMealEditBean bean,HttpServletRequest request,SysUser u) {
		if (bean.getStatus() == null) {
			bean.setStatus(CarStatus.在办);
		}
		if (StringUtils.isNotBlank(bean.getId())) {// 修改
			OaMeal meal = this.repository.findOne(bean.getId());
			BeanUtils.copyProperties(bean, meal);
			if (bean.getMealType() != null) {
				meal.setMealType(bean.getMealType()[0]);
			}
			OaMeal meals = this.repository.save(meal);
        	this.logService.saveLog(SjType.普通操作, "修改预约订餐成功，ID为：" + meals.getId(), LogStatus.成功, request);
			
		} else {// 新增
			
			List<OaMeal> saveList = new ArrayList<OaMeal>();
			StringBuffer repeat = new StringBuffer();
			
			// 内部人员新增，内部人员一定会有id
			if (bean.getMealUserId() != null) {
				for (int i = 0; i < bean.getMealUserId().length; i++) {
					SysUser user = this.sysUserRepository.findOne(bean.getMealUserId()[i]);// 获取用餐人员信息
					OaMeal meal = new OaMeal();
					BeanUtils.copyProperties(bean, meal);
					meal.setMealUserId(bean.getMealUserId()[i]);// 用餐人员Id
					meal.setMealUserName(bean.getMealUserName()[i]);// 用餐人员姓名
					meal.setMealDeptId(user.getDeptId());// 用餐人员部门Id
					meal.setMealDeptName(user.getDepartment().getDeptName());// 用餐人员部门名称
					meal.setMealType(bean.getMealType()[i]);// 用餐类型
					meal.setCheckId(Long.valueOf(10));// 审核人id，暂时写死10
					
					List<OaMeal> list = this.repository.findByMealUserNameAndMealDateAndMealType(bean.getMealUserName()[i], 
							bean.getMealDate(),bean.getMealType()[i]);
					if(list.size() > 0){
						repeat.append("'").append(bean.getMealUserName()[i]).append("'");
						repeat.append("'已预约了'").append(bean.getMealType()[i].getValue()).append("'<br>");
					}else{
						saveList.add(meal);
					}
					
				}
			}
			// 通讯录人员新增，通信录人员一定没有id，并且bean中的mailUserName字段一定不为空。
			if (bean.getMailUserName() != null) {
				for (int i = 0; i < bean.getMailUserName().length; i++) {
					OaMeal meal = new OaMeal();
					meal.setCreateTime(bean.getCreateTime());// 申请时间
					meal.setCreatorId(bean.getCreatorId());// 申请人Id
					meal.setDeptId(bean.getDeptId());// 申请人部门Id
					meal.setMealDate(bean.getMealDate());// 用餐时间
					meal.setStatus(bean.getStatus());// 状态
					meal.setMealUserName(bean.getMailUserName()[i]);// 用餐人员姓名（通讯录）
					meal.setMealDeptName(bean.getMailDeptName()[i]);// 用餐人员部门名称（通讯录）
					meal.setMealPlace(bean.getMealPlace());// 用餐地点
					meal.setMealType(bean.getMealType1()[i]);// 用餐类型（通讯录）
					meal.setCheckId(Long.valueOf(10));// 审核人id，暂时写死10
					
					List<OaMeal> list = this.repository.findByMealUserNameAndMealDateAndMealType(bean.getMailUserName()[i], 
							bean.getMealDate(),bean.getMealType1()[i]);
					if(list.size() > 0){
						repeat.append("'").append(bean.getMailUserName()[i]).append("'");
						repeat.append("'已预约了'").append(bean.getMealType1()[i].getValue()).append("'<br>");
					}else{
						saveList.add(meal);
					}
					
				}
			}
			
			if (repeat.length()>0) {
				
				repeat.append("不能重复预约！");
	            return new ReturnInfo(repeat.toString(), null);
	        }else{
	        	this.repository.save(saveList);
	        	this.logService.saveUserLog(u,SjType.普通操作, "保存预约订餐成功，预约人数为：" + saveList.size(), LogStatus.成功, request);
	        }
		}
		
		
		
		
		return new ReturnInfo("1", null, "提交成功!");
	}

	@Override
	public void delMealByIds(String ids) {
		if (StringUtils.isNotEmpty(ids)) {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				this.repository.delete(id);
			}
		}
	}

	@Override
	public Map<String, Object> mealSelect(Collection<SearchFilter> searchFilters, SysUser user, String mealDate, String mealType, Integer page,
			Integer rows) {
		String extraQuery = this.whereSqlBuilder(searchFilters);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT m.id,m.mealUserName,m.mealDeptName,m.mealPlace,m.mealType,m.mealDate,m.status ");
		sql.append("FROM ").append(OaMeal.class.getSimpleName()).append(" AS m ");
		sql.append("WHERE (m.creatorId = ?1 or m.mealUserId = ?2) ");
		if (extraQuery.length() > 0) {
			sql.append(extraQuery);
		}
		if(StringUtils.isNotBlank(mealDate)){
			sql.append("AND m.mealDate = '").append(mealDate).append("' ");
		}
		if (StringUtils.isNotBlank(mealType) && MealType.加班餐.getKey().equals(mealType)) {
			sql.append("AND m.mealType in(2) ");
		} else if (StringUtils.isNotBlank(mealType) && !MealType.加班餐.getKey().equals(mealType)) {
			sql.append("AND m.mealType in(0,1,3) ");
		}
		sql.append("ORDER BY m.createTime DESC");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<OaMealShowBean> dataList = new ArrayList<OaMealShowBean>();
		Query query = em.createQuery(sql.toString());

		query.setParameter(1, user.getId());
		query.setParameter(2, user.getId());
		
		List<?> totalList = query.getResultList();
        int total = 0;
        if (totalList != null) {
            total = totalList.size();
        }
		query.setFirstResult((page - 1) * rows);
		query.setMaxResults(rows);

		List<?> list = query.getResultList();
		String[] names = { "id", "mealUserName", "mealDeptName", "mealPlace", "mealType", "mealDate", "status" };
		if (list != null && list.size() > 0) {
			for (Object o : list) {
				Map<String, Object> map = this.toMap(names, (Object[]) o);
				OaMealShowBean bean = new OaMealShowBean();
				try {
					PropertyUtils.copyProperties(bean, map);
				} catch (Exception e) {
					e.printStackTrace();
				}
				dataList.add(bean);
			}
		}
		dataMap.put("total", Long.valueOf(total));
		dataMap.put("rows", dataList);
		return dataMap;
	}

	// where语句构造
	private String whereSqlBuilder(Collection<SearchFilter> searchFilters) {
		StringBuffer sqlBuilder = new StringBuffer();
		if (CollectionUtils.isNotEmpty(searchFilters)) {
			Iterator<SearchFilter> cons = searchFilters.iterator();
			while (cons.hasNext()) {
				SearchFilter filter = (SearchFilter) cons.next();
				if (filter != null) {
					switch (filter.operator.name()) {
					case "EQ":
						sqlBuilder.append(" AND " + filter.fieldName + " = '" + filter.value.toString() + "' ");
						break;
					case "LIKE":
						sqlBuilder.append(" AND " + filter.fieldName + " like '%" + filter.value.toString() + "%' ");
						break;
					case "GTE":
						sqlBuilder.append(" AND " + filter.fieldName + " >= '" + filter.value.toString() + "'");
						break;
					case "LTE":
						sqlBuilder.append(" AND " + filter.fieldName + " <= '" + filter.value.toString() + "'");
						break;
					default:
						break;
					}
				}
			}
		}
		return sqlBuilder.toString();
	}

	private Map<String, Object> toMap(String[] propertyName, Object[] list) {
		Map<String, Object> map = new HashedMap();
		for (int i = 0; i < list.length; i++) {
			for (int j = 0; j < propertyName.length; j++) {
				map.put(propertyName[j], list[j]);
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> mealStat(String mealType, String beginDate, String endDate, Integer page, Integer rows, SysUser user) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT m.mealDate,");
		sql.append("SUM(CASE WHEN m.mealType = '0' AND m.mealPlace='0' THEN 1 ELSE 0 END),");
		sql.append("SUM(CASE WHEN m.mealType = '1' AND m.mealPlace='0' THEN 1 ELSE 0 END),");
		sql.append("SUM(CASE WHEN m.mealType = '2' AND m.mealPlace='0' THEN 1 ELSE 0 END),");
		sql.append("SUM(CASE WHEN m.mealType = '3' AND m.mealPlace='0' THEN 1 ELSE 0 END),");
		sql.append("SUM(CASE WHEN m.mealType = '0' AND m.mealPlace='1' THEN 1 ELSE 0 END),");
		sql.append("SUM(CASE WHEN m.mealType = '1' AND m.mealPlace='1' THEN 1 ELSE 0 END),");
		sql.append("SUM(CASE WHEN m.mealType = '2' AND m.mealPlace='1' THEN 1 ELSE 0 END),");
		sql.append("SUM(CASE WHEN m.mealType = '3' AND m.mealPlace='1' THEN 1 ELSE 0 END),");
		sql.append("MIN(m.status) ");
		sql.append("FROM ").append(OaMeal.class.getSimpleName()).append(" AS m ");
		sql.append("WHERE m.checkId = ?1  ");

		if (StringUtils.isNotBlank(mealType) && MealType.加班餐.getKey().equals(mealType)) {
			sql.append("AND m.mealType in(2) ");
		} else if (StringUtils.isNotBlank(mealType) && !MealType.加班餐.getKey().equals(mealType)) {
			sql.append("AND m.mealType in(0,1,3) ");
		}
		if (StringUtils.isNotBlank(beginDate)) {
			sql.append("AND m.mealDate >= '" + beginDate + "'");
		}
		if (StringUtils.isNotBlank(endDate)) {
			sql.append("AND m.mealDate <= '" + endDate + "'");
		}

		sql.append("GROUP BY m.mealDate");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<OaMealStatbean> dataList = new ArrayList<OaMealStatbean>();
		Query query = em.createQuery(sql.toString());
		query.setParameter(1, user.getId());
		List<?> totalList = query.getResultList();
        int total = 0;
        if (totalList != null) {
            total = totalList.size();
        }
		query.setFirstResult((page - 1) * rows);
		query.setMaxResults(rows);

		List<?> list = query.getResultList();
		String[] names = { "mealDate", "staffMealTotalMX", "recepMealTotalMX", "overtimeMealTotalMX",
				"practiceMealTotalMX", "staffMealTotalQH", "recepMealTotalQH", "overtimeMealTotalQH",
				"practiceMealTotalQH", "status" };
		if (list != null && list.size() > 0) {
			for (Object o : list) {
				Map<String, Object> map = this.toMap(names, (Object[]) o);
				OaMealStatbean bean = new OaMealStatbean();
				try {
					PropertyUtils.copyProperties(bean, map);
				} catch (Exception e) {
					e.printStackTrace();
				}
				dataList.add(bean);
			}
		}
		dataMap.put("total", Long.valueOf(total));
		dataMap.put("rows", dataList);
		return dataMap;
	}

	@Override
	public Boolean doFinish(String ids) {
		boolean a = false;
		if (StringUtils.isNotEmpty(ids)) {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				OaMeal meal = this.repository.findOne(id);
				if(CarStatus.在办.equals(meal.getStatus())){
					meal.setStatus(CarStatus.办结);
				}else{
					return a;
				}
				this.persist(meal);
			}
			a = true;
		}
		return a;
	}
	
	@Override
	public Boolean doBack(String ids) {
		boolean a = false;
		if (StringUtils.isNotEmpty(ids)) {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				OaMeal meal = this.repository.findOne(id);
				if(CarStatus.办结.equals(meal.getStatus())){
					meal.setStatus(CarStatus.在办);
				}else{
					return a;
				}
				this.persist(meal);
			}
			a = true;
		}
		return a;
	}

	@Override
	public List<OaMealAllPersonBean> getAllPersonData(Long deptId, Integer valid, String mealDate, String mealType, SysUser user) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT u.user_name,u.dept_id,m.meal_place ");
		sql.append("FROM sys_user AS u ");
		sql.append("LEFT JOIN oa_meal AS m ");
		sql.append("ON m.meal_user_name = u.user_name and m.meal_date = '").append(mealDate).append("' ");
		sql.append("AND m.status = '2'");
		if (StringUtils.isNotBlank(mealType) && MealType.加班餐.getKey().equals(mealType)) {
			sql.append("AND m.meal_type in(2) ");
		} else if (StringUtils.isNotBlank(mealType) && !MealType.加班餐.getKey().equals(mealType)) {
			sql.append("AND m.meal_type in(0,1,3) ");
		}
		sql.append("AND m.check_id = ?1 ");
		sql.append("WHERE u.dept_id != ?2 and u.valid = ?3 ");
		sql.append("ORDER BY u.dept_id asc,u.sort asc");
		List<OaMealAllPersonBean> dataList = new ArrayList<OaMealAllPersonBean>();
		Query query = em.createNativeQuery(sql.toString());
		
		query.setParameter(1, user.getId());
		query.setParameter(2, deptId);
		query.setParameter(3, valid);

		List<?> list = query.getResultList();
		String[] names = { "userName", "deptId", "mealPlace" };
		if (list != null && list.size() > 0) {
			for (Object o : list) {
				Map<String, Object> map = this.toMap(names, (Object[]) o);
				OaMealAllPersonBean bean = new OaMealAllPersonBean();
				try {
					PropertyUtils.copyProperties(bean, map);
					String deptName = this.departmentRepository.findOne(bean.getDeptId().longValue()).getDeptName();
					bean.setDeptName(deptName);
					if(bean.getMealPlace() != null){
						if("1".equals(bean.getMealPlace().toString())){
							bean.setPlace(MealPlace.青湖.getValue());
						}else{
							bean.setPlace(MealPlace.明秀.getValue());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				dataList.add(bean);
			}
		}
		return dataList;
	}

	@Override
	public List<OaMealAllPersonBean> getMealData(String type, String mealDate, String mealPlace, SysUser user) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT m.meal_user_name,m.meal_place,m.meal_dept_name ");
		sql.append("FROM oa_meal AS m ");
		sql.append("LEFT JOIN sys_user AS u ");
		sql.append("ON m.meal_user_name = u.user_name ");
		sql.append("WHERE ");
		sql.append("m.meal_date = '").append(mealDate).append("' ");
		sql.append("AND m.meal_type in (").append(type).append(") ");
		sql.append("AND m.status = '2'");
		if(mealPlace.equals(MealPlace.青湖.getKey())){
			sql.append("AND m.meal_place = '1' ");
		}
		if(mealPlace.equals(MealPlace.明秀.getKey())){
			sql.append("AND m.meal_place = '0' ");
		}
		sql.append("AND m.check_id = ?1 ");
		sql.append("ORDER BY u.dept_id asc,u.sort asc");
		List<OaMealAllPersonBean> dataList = new ArrayList<OaMealAllPersonBean>();
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter(1, user.getId());

		List<?> list = query.getResultList();
		String[] names = { "userName", "mealPlace", "deptName" };
		if (list != null && list.size() > 0) {
			for (Object o : list) {
				Map<String, Object> map = this.toMap(names, (Object[]) o);
				OaMealAllPersonBean bean = new OaMealAllPersonBean();
				try {
					PropertyUtils.copyProperties(bean, map);
					if(bean.getMealPlace() != null){
						if("1".equals(bean.getMealPlace().toString())){
							bean.setPlace(MealPlace.青湖.getValue());
						}else{
							bean.setPlace(MealPlace.明秀.getValue());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				dataList.add(bean);
			}
		}
		return dataList;
	}

}
