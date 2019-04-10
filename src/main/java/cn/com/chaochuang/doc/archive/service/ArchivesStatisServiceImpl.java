package cn.com.chaochuang.doc.archive.service;

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
import cn.com.chaochuang.doc.archive.bean.ArchivesStatisShowBean;
import cn.com.chaochuang.doc.archive.domain.DocArchives;

/**
 * @author dengl 2017.12.01
 *
 */
@Service
@Transactional
public class ArchivesStatisServiceImpl implements ArchivesStatisService {
	
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
	public Map<String, Object> statArchives(Collection<SearchFilter> searchFilters) {
		String extraQuery = this.whereSqlBuilder(searchFilters);
		StringBuffer sql = new StringBuffer();
		sql.append("select a.dept.deptName, COUNT(a.id), "); // 档案总数
		sql.append("COUNT(CASE WHEN a.archStatus = '1' THEN 1 END), "); // 在库数量
		sql.append("COUNT(CASE WHEN a.archStatus = '2' THEN 1 END), "); // 在借数量
		sql.append("COUNT(CASE WHEN a.archStatus = '3' THEN 1 END) "); // 销毁数量
		sql.append("FROM ").append(DocArchives.class.getSimpleName()).append(" AS a where 1=1 ");
		if (extraQuery.length() > 0) {
			sql.append(extraQuery);
		}
		sql.append("GROUP BY a.depId ");

		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<ArchivesStatisShowBean> dataList = new ArrayList<ArchivesStatisShowBean>();
		Query query = em.createQuery(sql.toString());
		List list = query.getResultList();

		String[] names = { "deptName", "totalFiles", "stockTotal", "borrowTotal", "destroyTotal"};
		if (list != null && list.size() > 0) {
			for (Object o : list) {
				Map map = this.toMap(names, (Object[]) o);
				ArchivesStatisShowBean bean = new ArchivesStatisShowBean();
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
