package cn.com.chaochuang.pubaffairs.car.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.common.data.persistence.SearchFilter;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.pubaffairs.car.bean.OfficialCarEditBean;
import cn.com.chaochuang.pubaffairs.car.bean.OfficialCarShowBean;
import cn.com.chaochuang.pubaffairs.car.domain.OfficialCar;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.car.repository.OfficialCarRepository;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfFlowInstService;

/**
 * @author dengl 2018.08.08
 *
 */
@Service
@Transactional
public class OfficialCarServiceImpl extends SimpleUuidCrudRestService<OfficialCar> implements OfficialCarService {

	@Autowired
	private OfficialCarRepository 		repository;

	@Autowired
	private WfFlowInstService 			flowInstService;
	
	@PersistenceContext
	private EntityManager 				em;
	
	@Autowired
    private SysAttachService        	attachService;
	
	@Autowired
    private LogService             		logService;

	@Override
	public SimpleDomainRepository<OfficialCar, String> getRepository() {
		return this.repository;
	}

	@Override
	public void save(OfficialCar obj) {
		this.repository.save(obj);
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
	public ReturnBean saveOfficialCar(OfficialCarEditBean bean,HttpServletRequest request) {
		OfficialCar car = null;
		int version = 0;

		boolean isNew = (StringUtils.isBlank(bean.getId()));
		if (!isNew) {
			car = this.repository.findOne(bean.getId());
			// 保护一些信息不被修改
			bean.setCreateDate(car.getCreateDate());
			bean.setCreatorId(car.getCreatorId());
			bean.setCreatorName(car.getCreatorName());
			if (car.getVersion_() != null) {
				version = car.getVersion_();
			}
		} else {
			car = new OfficialCar();
			if (bean.getStatus() == null) {
				bean.setStatus(CarStatus.暂存);
				bean.setCreateDate(new Date());
			}
			bean.setVersion_(0);
		}
		if (version != bean.getVersion_().intValue()) {
			// 如果版本号不一致，说明有冲突
			return new ReturnBean("数据冲突，有其它人员同时对数据进行操作，请重新打开页面进行操作！", car, false);
		}
		// 允许编辑申请信息：新增或设置了可编辑标识
		if (isNew || YesNo.是.equals(bean.getDocEditable())) {
			BeanUtils.copyProperties(bean, car);
			car.setVersion_(version + 1);
			// 时间设置
			Date date = new Date();
			String dateString = Tools.DATE_MINUTE_FORMAT.format(date);
			try {
				date = Tools.DATE_MINUTE_FORMAT.parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (car.getCreateDate() == null) {
				car.setCreateDate(date);
			}
		}
		// 处理星期字段，根据开始时间来获取星期
		if(bean.getBeginTime() != null){
			String week = Tools.chineseWeekDay(bean.getBeginTime());
			car.setWeek(week);
		}
		
		OfficialCar cars = this.repository.save(car);
		bean.setId(cars.getId());
		
		String attachIds = bean.getAttach();

        // 连接附件
        List<String> oldIdsForDel = new ArrayList<String>();
        if (StringUtils.isNotBlank(bean.getId())) {
            // 旧的附件id
            List<SysAttach> oldAttachs = this.attachService.findByOwnerIdAndOwnerType(bean.getId(),
            		OfficialCar.class.getSimpleName());
            if (oldAttachs != null) {
                for (SysAttach a : oldAttachs) {
                    oldIdsForDel.add(a.getId().toString());
                }
            }
        }
        if (StringUtils.isNotBlank(attachIds)) {
            String[] idArray = StringUtils.split(attachIds, ",");
            String ownerId = cars.getId();
            for (String aIdStr : idArray) {
                // Long aId = Long.valueOf(aIdStr);
                this.attachService.linkAttachAndOwner(aIdStr, ownerId, OfficialCar.class.getSimpleName());
                if (oldIdsForDel.contains(aIdStr)) {
                    oldIdsForDel.remove(aIdStr);
                }
            }
        }
        // 删除剩余的
        if (oldIdsForDel.size() > 0) {
            for (String delAttachId : oldIdsForDel) {
                this.attachService.deleteAttach(delAttachId);
            }
            
            logService.saveDefaultLog("删除了车辆申请《" + bean.getReason() + "》中的" + oldIdsForDel.size() + "个附件", request);

        }
		
		return new ReturnBean("保存成功", cars, true);
	}

	@Override
	public ReturnBean updateOfficialCarInfo(OfficialCarEditBean bean,HttpServletRequest request) {
		if (StringUtils.isBlank(bean.getId()))
			return null;
		OfficialCar car = this.repository.findOne(bean.getId());
		if (car == null) {
			return new ReturnBean("该用车申请不存在，请重新进入页面进行操作！");
		}
		int curVersion = car.getVersion_().intValue();
		if (curVersion != bean.getVersion_().intValue()) {
			// 如果版本号不一致，说明有冲突
			return new ReturnBean("数据冲突，有其它人员同时对数据进行操作，请重新打开页面进行操作！", car, false);
		}

		if (StringUtils.isNotBlank(bean.getReason()) && !bean.getReason().trim().equals(car.getReason())) {
			car.setReason(bean.getReason());
			// 修改用车事由的时候同时修改流程实例的用车事由
			List<WfFlowInst> list = flowInstService.findByEntityId(bean.getId());
			for (WfFlowInst inst : list) {
				inst.setTitle(bean.getReason());
				flowInstService.getRepository().save(inst);
			}
		}
		car.setVersion_(curVersion + 1);
		if (StringUtils.isNotBlank(bean.getReason()))
			car.setReason(bean.getReason());
		if (bean.getBeginTime() != null)
			car.setBeginTime(bean.getBeginTime());
		if (bean.getEndTime() != null)
			car.setEndTime(bean.getEndTime());
		if (StringUtils.isNotBlank(bean.getCarNumber()))
			car.setCarNumber(bean.getCarNumber());
		if (StringUtils.isNotBlank(bean.getDriver()))
			car.setDriver(bean.getDriver());
		if(StringUtils.isNotBlank(bean.getRentalPlace()))
			car.setRentalPlace(bean.getRentalPlace());
		// 处理星期字段，根据开始时间来获取星期
		if(bean.getBeginTime() != null){
			String week = Tools.chineseWeekDay(bean.getBeginTime());
			car.setWeek(week);
		}
		this.persist(car);

		bean.setId(car.getId());
		
		String attachIds = bean.getAttach();

        // 连接附件
        List<String> oldIdsForDel = new ArrayList<String>();
        if (StringUtils.isNotBlank(bean.getId())) {
            // 旧的附件id
            List<SysAttach> oldAttachs = this.attachService.findByOwnerIdAndOwnerType(bean.getId(),
            		OfficialCar.class.getSimpleName());
            if (oldAttachs != null) {
                for (SysAttach a : oldAttachs) {
                    oldIdsForDel.add(a.getId().toString());
                }
            }
        }
        if (StringUtils.isNotBlank(attachIds)) {
            String[] idArray = StringUtils.split(attachIds, ",");
            String ownerId = car.getId();
            for (String aIdStr : idArray) {
                // Long aId = Long.valueOf(aIdStr);
                this.attachService.linkAttachAndOwner(aIdStr, ownerId, OfficialCar.class.getSimpleName());
                if (oldIdsForDel.contains(aIdStr)) {
                    oldIdsForDel.remove(aIdStr);
                }
            }
        }
        // 删除剩余的
        if (oldIdsForDel.size() > 0) {
            for (String delAttachId : oldIdsForDel) {
                this.attachService.deleteAttach(delAttachId);
            }
            
            logService.saveDefaultLog("删除了车辆申请《" + bean.getReason() + "》中的" + oldIdsForDel.size() + "个附件", request);

        }
		return new ReturnBean("用车申请信息更新成功！", car, true);
	}

	@Override
	public Map<String, Object> carSelect(Collection<SearchFilter> searchFilters,SysUser user,Integer page, Integer rows) {
		String extraQuery = this.whereSqlBuilder(searchFilters);
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT DISTINCT wni.id,wni.instStatus,adf.reason,wfi.startTime,wfi.businessType,wfi.entityId,adf.createDate,adf.creatorName,adf.deptName,adf.beginTime,adf.endTime,wni.arrivalTime,adf.businessType ");
		sql.append("FROM ").append(WfNodeInst.class.getSimpleName()).append(" AS wni, ")
				.append(WfFlowInst.class.getSimpleName()).append(" AS wfi, ").append(OfficialCar.class.getSimpleName())
				.append(" AS adf, ").append(SysUser.class.getSimpleName()).append(" AS u, ")
				.append(SysDepartment.class.getSimpleName()).append(" AS d ");
		sql.append(
				"WHERE wni.flowInstId = wfi.id AND wfi.entityId = adf.id AND (wfi.businessType='6' or wfi.businessType='9') AND wni.instStatus='0' AND wni.curNodeId <> 'N888' AND wni.preDealerId = u.id ");
		sql.append("AND u.deptId = d.id AND wni.dealerId = ?1 ");
		if (extraQuery.length() > 0) {
			sql.append(extraQuery);
		}
		sql.append("ORDER BY wni.arrivalTime DESC");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<OfficialCarShowBean> dataList = new ArrayList<OfficialCarShowBean>();
		Query query = em.createQuery(sql.toString());
		
        query.setParameter(1, user.getId());
		
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        
		List list = query.getResultList();
		String[] names = { "id", "instStatus", "reason", "startTime", "businessType", "entityId", "createDate",
				"creatorName", "deptName", "beginTime", "endTime", "arrivalTime", "type" };
		if (list != null && list.size() > 0) {
			for (Object o : list) {
				Map map = this.toMap(names, (Object[]) o);
				OfficialCarShowBean bean = new OfficialCarShowBean();
				try {
					PropertyUtils.copyProperties(bean, map);
				} catch (Exception e) {
					e.printStackTrace();
				}
				dataList.add(bean);
			}
		}
		dataMap.put("total", (long) list.size());
		dataMap.put("rows", dataList);
		return dataMap;
	}

	@Override
	public void finishTheCar(OfficialCarEditBean bean) {
		OfficialCar obj = this.repository.findOne(bean.getId());
        if (obj != null) {
            obj.setStatus(CarStatus.办结);
            obj.setDriver(bean.getDriver());
            obj.setCarNumber(bean.getCarNumber());
            this.repository.save(obj);
        }
	}


	@Override
	public void delOffcialCarByIds(String ids) {
		if(StringUtils.isNotEmpty(ids)){
			String[] idArr=ids.split(",");
			for (String id : idArr) {
				this.repository.delete(id);
			}
		}
		
	}

	@Override
	public boolean delCar(String id, boolean force) {
		if (id != null) {
			OfficialCar obj = repository.findOne(id);
            if (obj != null) {
                // 只有强制标志=ture或暂存状态下，才能删除
                if (force || CarStatus.暂存.equals(obj.getStatus())) {
                    this.repository.delete(obj);
                }
                return true;
            }
        }
		return false;
	}
}
