package cn.com.chaochuang.pubaffairs.leave.service;

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
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.pubaffairs.car.reference.CarStatus;
import cn.com.chaochuang.pubaffairs.leave.bean.OaLeaveEditBean;
import cn.com.chaochuang.pubaffairs.leave.bean.OaLeaveQueryBean;
import cn.com.chaochuang.pubaffairs.leave.domain.OaLeave;
import cn.com.chaochuang.pubaffairs.leave.repository.OaLeaveRepository;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;
import cn.com.chaochuang.workflow.inst.service.WfFlowInstService;

/**
 * @author dengl 2018.08.08
 *
 */
@Service
@Transactional
public class OaLeaveServiceImpl extends SimpleUuidCrudRestService<OaLeave> implements OaLeaveService {

	@Autowired
	private OaLeaveRepository 				repository;
	
	@PersistenceContext
	private EntityManager 					em;
	
	@Autowired
	private SysUserService 					userService;
	
	@Autowired
	private WfFlowInstService 				flowInstService;
	
	@Autowired
    private SysAttachService        		attachService;
	
	@Autowired
    private LogService             			logService;
	
	@Override
	public SimpleDomainRepository<OaLeave, String> getRepository() {
		return this.repository;
	}

	@Override
	public boolean delOaLeave(String id) {
		if (StringUtils.isNotBlank(id)) {
            repository.delete(id);
            return true;
        }
        return false;
	}

	@Override
	public ReturnBean saveOaLeaveFlow(OaLeaveEditBean bean,HttpServletRequest request) {
        OaLeave leave = null;
		int version = 0;

		boolean isNew = (StringUtils.isBlank(bean.getId()));
		SysUser creator = this.userService.findOne(bean.getCreatorId());
		if (!isNew) {
			leave = this.repository.findOne(bean.getId());
			// 保护一些信息不被修改
			bean.setCreateDate(leave.getCreateDate());
			bean.setCreatorId(leave.getCreatorId());
			bean.setCreatorName(leave.getCreatorName());
			if (leave.getVersion_() != null) {
				version = leave.getVersion_();
			}
		} else {
			leave = new OaLeave();
			if (bean.getStatus() == null) {
				bean.setStatus(CarStatus.暂存);
				bean.setCreateDate(new Date());
			}
			bean.setVersion_(0);
		}
		if (version != bean.getVersion_().intValue()) {
			// 如果版本号不一致，说明有冲突
			return new ReturnBean("数据冲突，有其它人员同时对数据进行操作，请重新打开页面进行操作！", leave, false);
		}
		
		if (isNew) {
			BeanUtils.copyProperties(bean, leave);
		}else{
			leave.setBeginTime(bean.getBeginTime());
			leave.setEndTime(bean.getEndTime());
			leave.setLeaveDay(bean.getLeaveDay());
			leave.setReason(bean.getReason());
			leave.setLeaveType(bean.getLeaveType());
			leave.setStatus(bean.getStatus());
		}
		leave.setVersion_(version + 1);
		OaLeave newLeave = this.repository.save(leave);
		bean.setId(newLeave.getId());
		
		
		String attachIds = bean.getAttach();

        // 连接附件
        List<String> oldIdsForDel = new ArrayList<String>();
        if (StringUtils.isNotBlank(bean.getId())) {
            // 旧的附件id
            List<SysAttach> oldAttachs = this.attachService.findByOwnerIdAndOwnerType(bean.getId(),
            				OaLeave.class.getSimpleName());
            if (oldAttachs != null) {
                for (SysAttach a : oldAttachs) {
                    oldIdsForDel.add(a.getId().toString());
                }
            }
        }
        if (StringUtils.isNotBlank(attachIds)) {
            String[] idArray = StringUtils.split(attachIds, ",");
            String ownerId = newLeave.getId();
            for (String aIdStr : idArray) {
                // Long aId = Long.valueOf(aIdStr);
                this.attachService.linkAttachAndOwner(aIdStr, ownerId, OaLeave.class.getSimpleName());
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
            
            logService.saveDefaultLog("删除了请假申请《" + bean.getReason() + "》中的" + oldIdsForDel.size() + "个附件", request);

        }
		
		
		return new ReturnBean("假期申请保存成功！", newLeave, true);
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
						sqlBuilder.append(" AND " + filter.fieldName + " >= '" + filter.value.toString()+"'");
						break;
					case "LTE":
						sqlBuilder.append(" AND " + filter.fieldName + " <= '" + filter.value.toString()+"'");
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
	public Map<String, Object> leaveSelect(Collection<SearchFilter> searchFilters,SysUser user,Integer page, Integer rows) {
		String extraQuery = this.whereSqlBuilder(searchFilters);
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT DISTINCT wni.id,wni.instStatus,al.reason,wfi.startTime,wfi.businessType,wfi.entityId,al.createDate,al.creatorName,al.deptName,al.beginTime,al.endTime,al.leaveType,wni.arrivalTime,al.leaveDay,al.status,al.id ");
		sql.append("FROM ").append(WfNodeInst.class.getSimpleName()).append(" AS wni, ")
				.append(WfFlowInst.class.getSimpleName()).append(" AS wfi, ").append(OaLeave.class.getSimpleName())
				.append(" AS al, ").append(SysUser.class.getSimpleName()).append(" AS u, ")
				.append(SysDepartment.class.getSimpleName()).append(" AS d ");
		sql.append(
				"WHERE wni.flowInstId = wfi.id AND wfi.entityId = al.id AND wfi.businessType='7' AND wni.instStatus='0' AND wni.curNodeId <> 'N888' AND wni.preDealerId = u.id ");
		sql.append("AND u.deptId = d.id AND wni.dealerId = ?1 ");
		if (extraQuery.length() > 0) {
			sql.append(extraQuery);
		}
		sql.append(" ORDER BY wni.arrivalTime DESC");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<OaLeaveQueryBean> dataList = new ArrayList<OaLeaveQueryBean>();
		Query query = em.createQuery(sql.toString());
		
		query.setParameter(1, user.getId());
		
		query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
		
		List list = query.getResultList();
		String[] names = { "wfNodeInstId", "instStatus", "reason", "startTime","businessType", "entityId", "createDate",
				"creatorName", "deptName", "beginTime", "endTime", "leaveType","arrivalTime","leaveDay","status","id" };
		if (list != null && list.size() > 0) {
			for (Object o : list) {
				Map map = this.toMap(names, (Object[]) o);
				OaLeaveQueryBean bean = new OaLeaveQueryBean();
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
	public ReturnBean updateLeaveInfo(OaLeaveEditBean bean, HttpServletRequest request) {
		if (StringUtils.isBlank(bean.getId()))
			return null;
		OaLeave obj = this.repository.findOne(bean.getId());
		if (obj == null) {
			return new ReturnBean("该休假申请不存在，请重新进入页面进行操作！");
		}
		int curVersion = obj.getVersion_().intValue();
		if (curVersion != bean.getVersion_().intValue()) {
			// 如果版本号不一致，说明有冲突
			return new ReturnBean("数据冲突，有其它人员同时对数据进行操作，请重新打开页面进行操作！", obj, false);
		}

		if (StringUtils.isNotBlank(bean.getReason()) && !bean.getReason().trim().equals(obj.getReason())) {
			obj.setReason(bean.getReason());
			// 修改休假备注的时候同时修改流程实例的休假备注
			List<WfFlowInst> list = flowInstService.findByEntityId(bean.getId());
			for (WfFlowInst inst : list) {
				inst.setTitle(bean.getReason());
				flowInstService.getRepository().save(inst);
			}
		}
		obj.setVersion_(curVersion + 1);
		if (bean.getBeginTime() != null)
			obj.setBeginTime(bean.getBeginTime());
		if (bean.getEndTime() != null)
			obj.setEndTime(bean.getEndTime());
		if(bean.getLeaveType() != null)
			obj.setLeaveType(bean.getLeaveType());
		if(bean.getLeaveDay() != null)
			obj.setLeaveDay(bean.getLeaveDay());
		this.persist(obj);
		
		bean.setId(obj.getId());
		
		
		String attachIds = bean.getAttach();

        // 连接附件
        List<String> oldIdsForDel = new ArrayList<String>();
        if (StringUtils.isNotBlank(bean.getId())) {
            // 旧的附件id
            List<SysAttach> oldAttachs = this.attachService.findByOwnerIdAndOwnerType(bean.getId(),
            				OaLeave.class.getSimpleName());
            if (oldAttachs != null) {
                for (SysAttach a : oldAttachs) {
                    oldIdsForDel.add(a.getId().toString());
                }
            }
        }
        if (StringUtils.isNotBlank(attachIds)) {
            String[] idArray = StringUtils.split(attachIds, ",");
            String ownerId = obj.getId();
            for (String aIdStr : idArray) {
                // Long aId = Long.valueOf(aIdStr);
                this.attachService.linkAttachAndOwner(aIdStr, ownerId, OaLeave.class.getSimpleName());
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
            
            logService.saveDefaultLog("删除了请假申请《" + bean.getReason() + "》中的" + oldIdsForDel.size() + "个附件", request);

        }
		
		return new ReturnBean("休假申请信息更新成功！", obj, true);
	}
	
	@Override
	public void finishLeave(String id) {
		OaLeave obj = this.repository.findOne(id);
        if (obj != null) {
            obj.setStatus(CarStatus.办结);
            this.repository.save(obj);
        }
	}
	
	@Override
	public void save(OaLeave obj) {
		this.repository.save(obj);
	}


	@Override
	public void delOaleaveByIds(String ids) {
		if(!StringUtils.isEmpty(ids)){
			String[] idArr=ids.split(",");
			for (String id : idArr) {
				this.repository.delete(id);
			}
		}
	}

	@Override
	public boolean delLeave(String id, boolean force) {
		if (id != null) {
			OaLeave obj = repository.findOne(id);
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
