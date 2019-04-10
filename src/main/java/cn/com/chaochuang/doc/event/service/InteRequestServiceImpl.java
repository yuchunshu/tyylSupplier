package cn.com.chaochuang.doc.event.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.persistence.SearchFilter;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.workflow.event.bean.TaskShowBean;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;

/**
 * @author dengl 2017.12.17
 *
 */
@Service
@Transactional
public class InteRequestServiceImpl implements InteRequestService {
	
	@PersistenceContext
	private EntityManager 	em;
	
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
						sqlBuilder.append(" AND " + filter.fieldName + " >= '" + filter.value.toString() + "' ");
						break;
					case "LTE":
						sqlBuilder.append(" AND " + filter.fieldName + " <= '" + filter.value.toString() + "' ");
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
	public Map<String, Object> inteRequest(Collection<SearchFilter> searchFilters) {
		String extraQuery = this.whereSqlBuilder(searchFilters);
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT DISTINCT wni.id,wni.instStatus,wfi.title,wfi.startTime,wfi.businessType,wfi.entityId,adf.sendUnit,u.userName,d.deptName,wfi.urgencyLevel,wfi.expiryDate,d.id,wni.arrivalTime ");
		sql.append("FROM ").append(WfNodeInst.class.getSimpleName()).append(" AS wni, ")
				.append(WfFlowInst.class.getSimpleName()).append(" AS wfi, ").append(OaDocFile.class.getSimpleName())
				.append(" AS adf, ").append(SysUser.class.getSimpleName()).append(" AS u, ")
				.append(SysDepartment.class.getSimpleName()).append(" AS d ");
		sql.append(
				"WHERE wni.flowInstId = wfi.id AND wfi.title = adf.title AND wfi.businessType='5' AND wni.instStatus='0' AND wni.curNodeId <> 'N888' AND wni.preDealerId = u.id ");
		sql.append("AND u.deptId = d.id AND wni.dealerId = '").append(UserTool.getInstance().getCurrentUserId())
				.append("' ");
		if (extraQuery.length() > 0) {
			sql.append(extraQuery);
		}
		sql.append("ORDER BY wni.arrivalTime DESC");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<TaskShowBean> dataList = new ArrayList<TaskShowBean>();
		Query query = em.createQuery(sql.toString());
		List list = query.getResultList();
		String[] names = { "id", "instStatus", "title", "startTime", "businessType", "entityId", "sendUnit", "preDealerName", "deptName", "urgencyLevel", "expiryDate", "deptId", "arrivalTime"};
		if (list != null && list.size() > 0) {
			for (Object o : list) {
				Map map = this.toMap(names, (Object[]) o);
				TaskShowBean bean = new TaskShowBean();
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

}
