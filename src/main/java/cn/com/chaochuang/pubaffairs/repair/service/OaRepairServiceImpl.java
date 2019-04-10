package cn.com.chaochuang.pubaffairs.repair.service;

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
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.repair.bean.OaRepairEditBean;
import cn.com.chaochuang.pubaffairs.repair.bean.OaRepairShowBean;
import cn.com.chaochuang.pubaffairs.repair.domain.OaEquipmentRepair;
import cn.com.chaochuang.pubaffairs.repair.repository.OaRepairRepository;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfFlowInstService;

/**
 * @author dengl 2018.08.10
 *
 */
@Service
@Transactional
public class OaRepairServiceImpl extends SimpleUuidCrudRestService<OaEquipmentRepair> implements OaRepairService {

	@Autowired
	private OaRepairRepository 		repository;

	@Autowired
	private WfFlowInstService 		flowInstService;

	@PersistenceContext
	private EntityManager 			em;
	
	@Autowired
    private SysAttachService        attachService;
	
	@Autowired
    private LogService             	logService;

	@Override
	public SimpleDomainRepository<OaEquipmentRepair, String> getRepository() {
		return this.repository;
	}

	@Override
	public void save(OaEquipmentRepair obj) {
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
						sqlBuilder.append(" AND " + filter.fieldName + " >= '" + filter.value.toString()+ "'");
						break;
					case "LTE":
						sqlBuilder.append(" AND " + filter.fieldName + " <= '" + filter.value.toString()+ "'");
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
	public ReturnBean saveRepair(OaRepairEditBean bean,HttpServletRequest request) {
		OaEquipmentRepair repair = null;
		int version = 0;

		boolean isNew = (StringUtils.isBlank(bean.getId()));
		if (!isNew) {
			repair = this.repository.findOne(bean.getId());
			// 保护一些信息不被修改
			bean.setCreateDate(repair.getCreateDate());
			bean.setCreatorId(repair.getCreatorId());
			bean.setCreatorName(repair.getCreatorName());
			if (repair.getVersion_() != null) {
				version = repair.getVersion_();
			}
		} else {
			repair = new OaEquipmentRepair();
			if (bean.getStatus() == null) {
				bean.setStatus(CarStatus.暂存);
				bean.setCreateDate(new Date());
			}
			bean.setVersion_(0);
		}
		if (version != bean.getVersion_().intValue()) {
			// 如果版本号不一致，说明有冲突
			return new ReturnBean("数据冲突，有其它人员同时对数据进行操作，请重新打开页面进行操作！", repair, false);
		}
		// 允许编辑申请信息：新增或设置了可编辑标识
		if (isNew || YesNo.是.equals(bean.getDocEditable())) {
			BeanUtils.copyProperties(bean, repair);
			repair.setVersion_(version + 1);
			// 时间设置
			Date date = new Date();
			String dateString = Tools.DATE_MINUTE_FORMAT.format(date);
			try {
				date = Tools.DATE_MINUTE_FORMAT.parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (repair.getCreateDate() == null) {
				repair.setCreateDate(date);
			}
		}
		OaEquipmentRepair repairs = this.repository.save(repair);
		bean.setId(repairs.getId());
		
		String attachIds = bean.getAttach();

        // 连接附件
        List<String> oldIdsForDel = new ArrayList<String>();
        if (StringUtils.isNotBlank(bean.getId())) {
            // 旧的附件id
            List<SysAttach> oldAttachs = this.attachService.findByOwnerIdAndOwnerType(bean.getId(),
            				OaEquipmentRepair.class.getSimpleName());
            if (oldAttachs != null) {
                for (SysAttach a : oldAttachs) {
                    oldIdsForDel.add(a.getId().toString());
                }
            }
        }
        if (StringUtils.isNotBlank(attachIds)) {
            String[] idArray = StringUtils.split(attachIds, ",");
            String ownerId = repairs.getId();
            for (String aIdStr : idArray) {
                this.attachService.linkAttachAndOwner(aIdStr, ownerId, OaEquipmentRepair.class.getSimpleName());
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
            
            logService.saveDefaultLog("删除了仪器设备故障报修申请《" + bean.getDeviceName() + "》中的" + oldIdsForDel.size() + "个附件", request);

        }
		
		return new ReturnBean("保存成功", repairs, true);
	}

	@Override
	public ReturnBean updateRepairInfo(OaRepairEditBean bean,HttpServletRequest request) {
		if (StringUtils.isBlank(bean.getId()))
			return null;
		OaEquipmentRepair repair = this.repository.findOne(bean.getId());
		if (repair == null) {
			return new ReturnBean("该故障报告申请不存在，请重新进入页面进行操作！");
		}
		int curVersion = repair.getVersion_().intValue();
		if (curVersion != bean.getVersion_().intValue()) {
			// 如果版本号不一致，说明有冲突
			return new ReturnBean("数据冲突，有其它人员同时对数据进行操作，请重新打开页面进行操作！", repair, false);
		}

		if (StringUtils.isNotBlank(bean.getFailureReason()) && !bean.getFailureReason().trim().equals(repair.getFailureReason())) {
			repair.setFailureReason(bean.getFailureReason());
			// 修改故障原因的时候同时修改流程实例的故障原因
			List<WfFlowInst> list = flowInstService.findByEntityId(bean.getId());
			for (WfFlowInst inst : list) {
				inst.setTitle(bean.getFailureReason());
				flowInstService.getRepository().save(inst);
			}
		}
		repair.setVersion_(curVersion + 1);
		if (StringUtils.isNotBlank(bean.getDeviceName()))
			repair.setDeviceName(bean.getDeviceName());
		if (StringUtils.isNotBlank(bean.getDeviceModel()))
			repair.setDeviceModel(bean.getDeviceModel());
		if (StringUtils.isNotBlank(bean.getDeviceNumber()))
			repair.setDeviceNumber(bean.getDeviceNumber());
		if (StringUtils.isNotBlank(bean.getFailureReason()))
			repair.setFailureReason(bean.getFailureReason());
		if (bean.getRepairType() != null)
			repair.setRepairType(bean.getRepairType());
		if (StringUtils.isNotBlank(bean.getExpectCost()))
			repair.setExpectCost(bean.getExpectCost());
		this.persist(repair);

		bean.setId(repair.getId());
		String attachIds = bean.getAttach();

        // 连接附件
        List<String> oldIdsForDel = new ArrayList<String>();
        if (StringUtils.isNotBlank(bean.getId())) {
            // 旧的附件id
            List<SysAttach> oldAttachs = this.attachService.findByOwnerIdAndOwnerType(bean.getId(),
            				OaEquipmentRepair.class.getSimpleName());
            if (oldAttachs != null) {
                for (SysAttach a : oldAttachs) {
                    oldIdsForDel.add(a.getId().toString());
                }
            }
        }
        if (StringUtils.isNotBlank(attachIds)) {
            String[] idArray = StringUtils.split(attachIds, ",");
            String ownerId = repair.getId();
            for (String aIdStr : idArray) {
                this.attachService.linkAttachAndOwner(aIdStr, ownerId, OaEquipmentRepair.class.getSimpleName());
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
            
            logService.saveDefaultLog("删除了仪器设备故障报修申请《" + bean.getDeviceName() + "》中的" + oldIdsForDel.size() + "个附件", request);

        }
		
		return new ReturnBean("故障报告申请信息更新成功！", repair, true);
	}

	@Override
	public void finishTheRepair(String id) {
		OaEquipmentRepair obj = this.repository.findOne(id);
        if (obj != null) {
            obj.setStatus(CarStatus.办结);
            this.repository.save(obj);
        }
	}

	@Override
	public boolean delRepair(String id, boolean force) {
		if (id != null) {
			OaEquipmentRepair obj = repository.findOne(id);
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

	@Override
	public Map<String, Object> repairSelect(Collection<SearchFilter> searchFilters, SysUser user, Integer page,
			Integer rows) {
		String extraQuery = this.whereSqlBuilder(searchFilters);
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT DISTINCT wni.id,wni.instStatus,adf.failureReason,wfi.startTime,wfi.businessType,wfi.entityId,adf.createDate,adf.creatorName,adf.deptName,wni.arrivalTime,adf.deviceName,adf.repairType ");
		sql.append("FROM ").append(WfNodeInst.class.getSimpleName()).append(" AS wni, ")
				.append(WfFlowInst.class.getSimpleName()).append(" AS wfi, ").append(OaEquipmentRepair.class.getSimpleName())
				.append(" AS adf, ").append(SysUser.class.getSimpleName()).append(" AS u, ")
				.append(SysDepartment.class.getSimpleName()).append(" AS d ");
		sql.append(
				"WHERE wni.flowInstId = wfi.id AND wfi.entityId = adf.id AND wfi.businessType='8' AND wni.instStatus='0' AND wni.curNodeId <> 'N888' AND wni.preDealerId = u.id ");
		sql.append("AND u.deptId = d.id AND wni.dealerId = ?1 ");
		if (extraQuery.length() > 0) {
			sql.append(extraQuery);
		}
		sql.append("ORDER BY wni.arrivalTime DESC");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<OaRepairShowBean> dataList = new ArrayList<OaRepairShowBean>();
		Query query = em.createQuery(sql.toString());
		
        query.setParameter(1, user.getId());
		
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        
		List list = query.getResultList();
		String[] names = { "id", "instStatus", "failureReason", "startTime", "businessType", "entityId", "createDate",
				"creatorName", "deptName", "arrivalTime", "deviceName", "repairType" };
		if (list != null && list.size() > 0) {
			for (Object o : list) {
				Map map = this.toMap(names, (Object[]) o);
				OaRepairShowBean bean = new OaRepairShowBean();
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
	public void delRepairByIds(String ids) {
		if(StringUtils.isNotEmpty(ids)){
			String[] idArr=ids.split(",");
			for (String id : idArr) {
				this.repository.delete(id);
			}
		}
	}
}
