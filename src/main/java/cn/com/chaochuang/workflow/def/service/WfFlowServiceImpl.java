package cn.com.chaochuang.workflow.def.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleCrudRestService;
import cn.com.chaochuang.common.reference.AdministrativeLevel;
import cn.com.chaochuang.common.reference.EnableStatus;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.workflow.def.bean.FlowDeptEditBean;
import cn.com.chaochuang.workflow.def.bean.FlowEditBean;
import cn.com.chaochuang.workflow.def.bean.NodeDeptEditBean;
import cn.com.chaochuang.workflow.def.domain.WfFlow;
import cn.com.chaochuang.workflow.def.domain.WfFlowDept;
import cn.com.chaochuang.workflow.def.repository.WfFlowRepository;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * @author yuandl 2016-11-24
 *
 */
@Service
@Transactional
public class WfFlowServiceImpl extends SimpleCrudRestService<WfFlow, String> implements WfFlowService {

	@PersistenceContext
    private EntityManager          em;
	
    @Autowired
    private WfFlowRepository       repository;
    
    @Autowired
    private SysDepartmentService   deptService;
    
    @Autowired
    private WfFlowDeptService      flowDeptService;

    @Override
    public SimpleDomainRepository<WfFlow, String> getRepository() {
        return this.repository;
    }

    @Override
    public String saveFlow(FlowEditBean bean) {
        if (bean == null || StringUtils.isBlank(bean.getId())) {
            return null;
        }
        WfFlow flow = repository.findOne(bean.getId());
        if (flow == null) {
            flow = new WfFlow();
        }
        //避免保存的时候把流程图jason清空了
        String flowJason = flow.getFlowJason();
        
        BeanUtils.copyProperties(bean, flow);
        flow.setFlowJason(flowJason);
        if (bean.getFlowStatus() == null) {
        	flow.setFlowStatus(EnableStatus.启用);
        } else {
        	flow.setFlowStatus(bean.getFlowStatus());
        }
        //保存所属单位id
        SysDepartment curDept = (SysDepartment)UserTool.getInstance().getCurrentUserDepartment();
        flow.setDeptId(curDept.getUnitId().toString());
        repository.save(flow);
        
        //有通用流程-归属部门定义
        if(YesNo.否.equals(flow.getShareFlag())){
        	if(StringUtils.isNotBlank(bean.getFlowDeptIds())) {
        		flowDeptService.deleteByFlowId(flow.getId());
        		String[] ids = bean.getFlowDeptIds().split(",");
        		for (String id: ids) {
        			SysDepartment dept = this.deptService.findOne(new Long(id));
        			if (dept != null) {
        				FlowDeptEditBean obj = new FlowDeptEditBean();
        				obj.setFlowId(flow.getId());
        				obj.setDepId(dept.getId());
        				obj.setAncestorDepId(dept.getUnitId());
        				this.flowDeptService.saveFlowDept(obj);
        			}
        		}
        	}
        }
        
        return flow.getId();
    }


    @Override
	public void updateFlowJason(FlowEditBean bean) {
        WfFlow flow = null;
        if (bean != null && StringUtils.isNotBlank(bean.getId())) {
            flow = repository.findOne(bean.getId());
            if (flow == null) {
                return;
            }
        }
        flow.setFlowJason(bean.getFlowJason());

        this.persist(flow);
	}

	@Override
    public void delFlow(String flowId) {
        if (StringUtils.isNotBlank(flowId)) {
            WfFlow flow = repository.findOne(flowId);
            if (flow != null) {
                flow.setFlowStatus(EnableStatus.未启用);
                repository.save(flow);
            }
        }
    }

	@Override
	public WfFlow getByParentFlowId(String parentFlowId) {
		return repository.getByParentFlowId(parentFlowId);
	}

	@Override
	public List<WfFlow> findByFlowType(WfBusinessType flowType) {
		return this.repository.findByFlowTypeAndFlowStatus(flowType, EnableStatus.启用);
	}

	@Override
	public List<WfFlow> findAllExceptOneself(String flowId){
		return this.repository.findAllExceptOneself(flowId);
	}

	@Override
	public List<WfFlow> selectCanWorkFlow(WfBusinessType flowType) {
		SysDepartment dept = (SysDepartment)UserTool.getInstance().getCurrentUserDepartment();
		Map<String, Object> condMap = new HashedMap();
		StringBuilder sqlBuilder = new StringBuilder(" select ");
        sqlBuilder.append(" a.id, a.flowName");
        sqlBuilder.append(" from WfFlow a");
        sqlBuilder.append(" where (a.parentFlowId is null or a.parentFlowId = '') and a.flowStatus = '1' ");
		
        if (dept!= null) {
            sqlBuilder.append(" and (a.shareFlag = '1' or a.id in (select flowId from WfFlowDept d where d.depId =:depId or d.depId =:parentId or d.depId =:unitId)) ");
            condMap.put("depId", dept.getId());
            condMap.put("parentId", dept.getDeptParent());
            condMap.put("unitId", dept.getUnitId());

            if (dept.getDeptLevel() != null) {
                sqlBuilder.append(" and a.flowLevel=:flowLevel ");
                condMap.put("flowLevel", dept.getDeptLevel());
            }
        }
        
        if (flowType != null) {
        	sqlBuilder.append(" and a.flowType=:flowType ");
        	condMap.put("flowType", flowType);
        }
        

    	sqlBuilder.append(" order by a.id asc ");
        
    	Query query = em.createQuery(sqlBuilder.toString());

        // 遍历条件map, 设置查询条件
        if (Tools.isNotEmptyMap(condMap)) {
            for (String key : condMap.keySet()) {
                query.setParameter(key, condMap.get(key));
            }
        }

        List<WfFlow> dataList = new ArrayList<WfFlow>();
        List list = query.getResultList();
        String[] names = { "id", "flowName" };
        for (Object o : list) {
            Map<String, Object> map = Tools.changeListToMap(names, (Object[]) o);
            WfFlow b = new WfFlow();
            try {
                PropertyUtils.copyProperties(b, map);
                dataList.add(b);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        
		return dataList;
	}

	@Override
    public Page selectSubFlow(FlowEditBean bean, Integer page, Integer rows) {
		Map<String, Object> condMap = new HashedMap();
		StringBuilder sqlBuilder = new StringBuilder(" select ");
        sqlBuilder.append(" a.id, a.flowName");
        sqlBuilder.append(" from WfFlow a");
        sqlBuilder.append(" where a.parentFlowId is not null and a.parentFlowId <> '' and a.flowStatus = '1' ");
        
        if (StringUtils.isNotBlank(bean.getFlowName())) {
            sqlBuilder.append(" and a.flowName like concat('%',:flowName,'%') ");
            condMap.put("flowName", bean.getFlowName());
        }
        
        if (bean.getFlowLevel() != null) {
        	sqlBuilder.append(" and a.flowLevel=:flowLevel ");
        	condMap.put("flowLevel", bean.getFlowLevel());
        }
        
        if (bean.getFlowType() != null) {
            sqlBuilder.append(" and a.flowType=:flowType ");
            condMap.put("flowType", bean.getFlowType());
        }
        
    	sqlBuilder.append(" order by a.id asc ");
        
        Query query = em.createQuery(sqlBuilder.toString());

        // 遍历条件map, 设置查询条件
        if (Tools.isNotEmptyMap(condMap)) {
            for (String key : condMap.keySet()) {
                query.setParameter(key, condMap.get(key));
            }
        }

        List totalList = query.getResultList();
        int total = 0;
        if (totalList != null) {
            total = totalList.size();
        }
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        
        List<FlowEditBean> dataList = new ArrayList<FlowEditBean>();
        List list = query.getResultList();
        String[] names = { "id", "flowName" };
        for (Object o : list) {
            Map<String, Object> map = Tools.changeListToMap(names, (Object[]) o);
            FlowEditBean b = new FlowEditBean();
            try {
                PropertyUtils.copyProperties(b, map);
                dataList.add(b);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        
        Page p = new Page();
        p.setRows(dataList);
        p.setTotal(Long.valueOf(total));
        return p;
    }

	@Override
	public List<WfFlow> findByFlowLevelAndParentFlowIdNotNull(AdministrativeLevel flowLevel,String flowId) {
		return this.repository.findByParentFlowIdNotNull(EnableStatus.启用,flowLevel,flowId);
	}
}
