/*
 * FileName:    DocStatisServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年10月12日 ( HeYunTao) 1.0 Create
 */

package cn.com.chaochuang.doc.docstatis.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.docstatis.bean.DocStatChartBean;
import cn.com.chaochuang.doc.docstatis.bean.DocStatisShowBean;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.readmatter.domain.ReadMatter;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;

/**
 * @author hzr 2017.6.6
 *
 */
@Service
@Transactional
public class DocStatisServiceImpl implements DocStatisService {

    @PersistenceContext
    private EntityManager       em;


    public List<DocStatisShowBean> summary(String deptId, String userId, Date fromDay, Date toDay, boolean showUsers) {
        List<DocStatisShowBean> createNumList = createNumStat(deptId, userId, fromDay, toDay);
        List<DocStatisShowBean> dealedList = dealedStat(deptId, userId, fromDay, toDay);
        List<DocStatisShowBean> finishedList = finishedStat(deptId, userId, fromDay, toDay);
        List<DocStatisShowBean> fordoList = fordoStat(deptId, userId, fromDay, toDay);
        List<DocStatisShowBean> readingList = readingStat(deptId, userId, fromDay, toDay);
        List<DocStatisShowBean> readedList = readedStat(deptId, userId, fromDay, toDay);

        boolean flag;
        DocStatisShowBean t = null;
        DocStatisShowBean o = null;
    	for (int i=0; i<dealedList.size(); i++) {
    		t = dealedList.get(i);
        	flag = false;
            for (int j=createNumList.size()-1; j>=0; j--) {
            	o = createNumList.get(j);
        		if (o.getUserId().equals(t.getUserId())) {
        			o.setDealed(t.getDealed());
        			flag = true;
        			break;
        		}
        	}
        	if (!flag)  createNumList.add(t);
        }


    	for (int i=0; i<finishedList.size(); i++) {
    		t = finishedList.get(i);
        	flag = false;
            for (int j=createNumList.size()-1; j>=0; j--) {
            	o = createNumList.get(j);
        		if (o.getUserId().equals(t.getUserId())) {
        			o.setFinished(t.getFinished());
        			flag = true;
        			break;
        		}
        	}
        	if (!flag)  createNumList.add(t);
        }


    	for (int i=0; i<fordoList.size(); i++) {
    		t = fordoList.get(i);
        	flag = false;
            for (int j=createNumList.size()-1; j>=0; j--) {
            	o = createNumList.get(j);
        		if (o.getUserId().equals(t.getUserId())) {
        			o.setFordo(t.getFordo());
        			flag = true;
        			break;
        		}
        	}
        	if (!flag)  createNumList.add(t);
        }

    	for (int i=0; i<readingList.size(); i++) {
    		t = readingList.get(i);
        	flag = false;
            for (int j=createNumList.size()-1; j>=0; j--) {
            	o = createNumList.get(j);
        		if (o.getUserId().equals(t.getUserId())) {
        			o.setReading(t.getReading());
        			flag = true;
        			break;
        		}
        	}
        	if (!flag)  createNumList.add(t);
        }

    	for (int i=0; i<readedList.size(); i++) {
    		t = readedList.get(i);
        	flag = false;
            for (int j=createNumList.size()-1; j>=0; j--) {
            	o = createNumList.get(j);
        		if (o.getUserId().equals(t.getUserId())) {
        			o.setReaded(t.getReaded());
        			flag = true;
        			break;
        		}
        	}
        	if (!flag)  createNumList.add(t);
        }

    	//按部门归纳排序
    	Collections.sort(createNumList);


        //返回部门统计结果
        if(!showUsers) {
        	for (int i=createNumList.size()-1; i>0; i--) {
        		o = createNumList.get(i);
        		t = createNumList.get(i-1);
    			o.setUserId(null);	o.setUserName(null);
    			t.setUserId(null);	t.setUserName(null);
        		if (o.getDeptId().equals(t.getDeptId())) {
        			t.setCreateNum(adjustNum(t.getCreateNum()) + adjustNum(o.getCreateNum()));
        			t.setDealed(adjustNum(t.getDealed()) + adjustNum(o.getDealed()));
        			t.setFinished(adjustNum(t.getFinished()) + adjustNum(o.getFinished()));
        			t.setFordo(adjustNum(t.getFordo()) + adjustNum(o.getFordo()));
        			t.setReading(adjustNum(t.getReading()) + adjustNum(o.getReading()));
        			t.setReaded(adjustNum(t.getReaded()) + adjustNum(o.getReaded()));
        			createNumList.remove(i);
        		}
        	}
        }

        //避免数量为null的时候在页面不显示
        for(DocStatisShowBean d:createNumList){
        	d.setCreateNum(null != d.getCreateNum()?d.getCreateNum():0);
        	d.setDealed(null != d.getDealed()?d.getDealed():0);
        	d.setFinished(null != d.getFinished()?d.getFinished():0);
        	d.setFordo(null != d.getFordo()?d.getFordo():0);
        	d.setReading(null != d.getReading()?d.getReading():0);
        	d.setReaded(null != d.getReaded()?d.getReaded():0);
        }
        
        return createNumList;
    }

    private long adjustNum(Long num) {
    	if (num == null) return 0; else return num.longValue();
    }


    //公文创建数统计
    private List<DocStatisShowBean> createNumStat(String deptId, String userId, Date fromDate, Date toDate) {
    	StringBuilder sqlBuilder = new StringBuilder(" select ");
    	sqlBuilder.append(" b.department.id,b.department.deptName, a.creatorId, b.userName, count(*) from ");
    	sqlBuilder.append(OaDocFile.class.getName()).append(" as a, ");
    	sqlBuilder.append(SysUser.class.getName()).append(" as b ");
    	//sqlBuilder.append(SysDepartment.class.getName()).append(" as c ");
    	sqlBuilder.append(" where a.creatorId = b.id ");
    	// 以下是动态查询
    	if (StringUtils.isNotBlank(deptId)) {
    	    sqlBuilder.append(" and b.department.id = ?1 ");
    	}
    	if (StringUtils.isNotBlank(userId)) {
    	    sqlBuilder.append(" and a.creatorId = ?2 ");
    	}
    	if (fromDate != null) {
    	    sqlBuilder.append(" and a.createDate >= ?3 ");
    	}
    	if (toDate != null) {
    	    sqlBuilder.append(" and a.createDate <= ?4 ");
    	}
    	sqlBuilder.append(" group by b.department.id,b.department.deptName,a.creatorId,b.userName ");

    	Query query = em.createQuery(sqlBuilder.toString());

    	if (StringUtils.isNotBlank(deptId)) {
    	    query.setParameter(1, new Long(deptId));
    	}
    	if (StringUtils.isNotBlank(userId)) {
    	    query.setParameter(2, new Long(userId));
    	}
    	if (fromDate != null) {
    	    query.setParameter(3, fromDate);
    	}
    	if (toDate != null) {
    	    query.setParameter(4, toDate);
    	}

    	List<DocStatisShowBean> resList = new ArrayList<DocStatisShowBean>();
    	List list = query.getResultList();
    	String[] names = { "deptId", "deptName", "userId", "userName", "createNum" };
    	for (Object o : list) {
    	    Map<String, Object> map = toMap(names, (Object[]) o);
    	    DocStatisShowBean b = new DocStatisShowBean();
    	    try {
    	        PropertyUtils.copyProperties(b, map);
    	        resList.add(b);
    	    } catch (IllegalAccessException e) {
    	        e.printStackTrace();
    	    } catch (InvocationTargetException e) {
    	        e.printStackTrace();
    	    } catch (NoSuchMethodException e) {
    	        e.printStackTrace();
    	    }
    	}
    	return resList;
    }


    //公文经办数统计
    private List<DocStatisShowBean> dealedStat(String deptId, String userId, Date fromDate, Date toDate) {
    	StringBuilder sqlBuilder = new StringBuilder(" select ");
    	sqlBuilder.append(" b.department.id,b.department.deptName, a.dealerId, b.userName, count(*), a.flowInst.entityId from ");
    	sqlBuilder.append(WfNodeInst.class.getName()).append(" as a, ");
    	sqlBuilder.append(SysUser.class.getName()).append(" as b ");
    	sqlBuilder.append(" where a.dealerId = b.id  ");
    	// 以下是动态查询
    	if (StringUtils.isNotBlank(deptId)) {
    	    sqlBuilder.append(" and b.department.id = ?1 ");
    	}
    	if (StringUtils.isNotBlank(userId)) {
    	    sqlBuilder.append(" and a.dealerId = ?2 ");
    	}
    	if (fromDate != null) {
    	    sqlBuilder.append(" and a.arrivalTime >= ?3 ");
    	}
    	if (toDate != null) {
    	    sqlBuilder.append(" and a.arrivalTime <= ?4 ");
    	}
    	sqlBuilder.append(" group by b.department.id,b.department.deptName,a.dealerId,b.userName,a.flowInst.entityId order by b.department.id,a.dealerId");

    	Query query = em.createQuery(sqlBuilder.toString());

    	if (StringUtils.isNotBlank(deptId)) {
    	    query.setParameter(1, new Long(deptId));
    	}
    	if (StringUtils.isNotBlank(userId)) {
    	    query.setParameter(2, new Long(userId));
    	}
    	if (fromDate != null) {
    	    query.setParameter(3, fromDate);
    	}
    	if (toDate != null) {
    	    query.setParameter(4, toDate);
    	}

    	List<DocStatisShowBean> resList = new ArrayList<DocStatisShowBean>();
    	List list = query.getResultList();
    	String[] names = { "deptId", "deptName", "userId", "userName", "dealed" };
    	for (Object o : list) {
    	    Map<String, Object> map = toMap(names, (Object[]) o);
    	    DocStatisShowBean b = new DocStatisShowBean();
    	    try {
    	        PropertyUtils.copyProperties(b, map);
    	        resList.add(b);
    	    } catch (IllegalAccessException e) {
    	        e.printStackTrace();
    	    } catch (InvocationTargetException e) {
    	        e.printStackTrace();
    	    } catch (NoSuchMethodException e) {
    	        e.printStackTrace();
    	    }
    	}

    	//去掉重复记录
    	for (DocStatisShowBean t : resList) {
    		//重复的环节都是同一个公文实例
    		t.setDealed(new Long(1));
    	}
    	for (int i=resList.size()-1; i>0; i--) {
    		DocStatisShowBean o = resList.get(i);
    		DocStatisShowBean p = resList.get(i-1);
    		if (o.getUserId().equals(p.getUserId())) {
    			p.setDealed(adjustNum(o.getDealed()) + 1);
    			resList.remove(i);
    		}
    	}

    	return resList;
    }

    //公文办结数统计
    private List<DocStatisShowBean> finishedStat(String deptId, String userId, Date fromDate, Date toDate) {
    	StringBuilder sqlBuilder = new StringBuilder(" select ");
    	sqlBuilder.append(" b.department.id,b.department.deptName, a.enderId, b.userName, count(*) from ");
    	sqlBuilder.append(WfFlowInst.class.getName()).append(" as a, ");
    	sqlBuilder.append(SysUser.class.getName()).append(" as b ");
    	sqlBuilder.append(" where a.enderId = b.id and a.instStatus='1' ");
    	// 以下是动态查询
    	if (StringUtils.isNotBlank(deptId)) {
    	    sqlBuilder.append(" and b.department.id = ?1 ");
    	}
    	if (StringUtils.isNotBlank(userId)) {
    	    sqlBuilder.append(" and a.enderId = ?2 ");
    	}
    	if (fromDate != null) {
    	    sqlBuilder.append(" and a.endTime >= ?3 ");
    	}
    	if (toDate != null) {
    	    sqlBuilder.append(" and a.endTime <= ?4 ");
    	}
    	sqlBuilder.append(" group by b.department.id,b.department.deptName,a.enderId,b.userName ");

    	Query query = em.createQuery(sqlBuilder.toString());

    	if (StringUtils.isNotBlank(deptId)) {
    	    query.setParameter(1, new Long(deptId));
    	}
    	if (StringUtils.isNotBlank(userId)) {
    	    query.setParameter(2, new Long(userId));
    	}
    	if (fromDate != null) {
    	    query.setParameter(3, fromDate);
    	}
    	if (toDate != null) {
    	    query.setParameter(4, toDate);
    	}

    	List<DocStatisShowBean> resList = new ArrayList<DocStatisShowBean>();
    	List list = query.getResultList();
    	String[] names = { "deptId", "deptName", "userId", "userName", "finished" };
    	for (Object o : list) {
    	    Map<String, Object> map = toMap(names, (Object[]) o);
    	    DocStatisShowBean b = new DocStatisShowBean();
    	    try {
    	        PropertyUtils.copyProperties(b, map);
    	        resList.add(b);
    	    } catch (IllegalAccessException e) {
    	        e.printStackTrace();
    	    } catch (InvocationTargetException e) {
    	        e.printStackTrace();
    	    } catch (NoSuchMethodException e) {
    	        e.printStackTrace();
    	    }
    	}
    	return resList;
    }


    //公文待办数统计
    private List<DocStatisShowBean> fordoStat(String deptId, String userId, Date fromDate, Date toDate) {
    	StringBuilder sqlBuilder = new StringBuilder(" select ");
    	sqlBuilder.append(" b.department.id,b.department.deptName, a.dealerId, b.userName, count(*) from ");
    	sqlBuilder.append(WfNodeInst.class.getName()).append(" as a, ");
    	sqlBuilder.append(SysUser.class.getName()).append(" as b ");
    	sqlBuilder.append(" where a.dealerId = b.id and a.instStatus='0' and a.curNodeId <> 'N888'");
    	// 以下是动态查询
    	if (StringUtils.isNotBlank(deptId)) {
    	    sqlBuilder.append(" and b.department.id = ?1 ");
    	}
    	if (StringUtils.isNotBlank(userId)) {
    	    sqlBuilder.append(" and a.dealerId = ?2 ");
    	}
    	if (fromDate != null) {
    	    sqlBuilder.append(" and a.arrivalTime >= ?3 ");
    	}
    	if (toDate != null) {
    	    sqlBuilder.append(" and a.arrivalTime <= ?4 ");
    	}
    	sqlBuilder.append(" group by b.department.id,b.department.deptName,a.dealerId,b.userName ");

    	Query query = em.createQuery(sqlBuilder.toString());

    	if (StringUtils.isNotBlank(deptId)) {
    	    query.setParameter(1, new Long(deptId));
    	}
    	if (StringUtils.isNotBlank(userId)) {
    	    query.setParameter(2, new Long(userId));
    	}
    	if (fromDate != null) {
    	    query.setParameter(3, fromDate);
    	}
    	if (toDate != null) {
    	    query.setParameter(4, toDate);
    	}

    	List<DocStatisShowBean> resList = new ArrayList<DocStatisShowBean>();
    	List list = query.getResultList();
    	String[] names = { "deptId", "deptName", "userId", "userName", "fordo" };
    	for (Object o : list) {
    	    Map<String, Object> map = toMap(names, (Object[]) o);
    	    DocStatisShowBean b = new DocStatisShowBean();
    	    try {
    	        PropertyUtils.copyProperties(b, map);
    	        resList.add(b);
    	    } catch (IllegalAccessException e) {
    	        e.printStackTrace();
    	    } catch (InvocationTargetException e) {
    	        e.printStackTrace();
    	    } catch (NoSuchMethodException e) {
    	        e.printStackTrace();
    	    }
    	}
    	return resList;
    }


    //公文待阅数统计
    private List<DocStatisShowBean> readingStat(String deptId, String userId, Date fromDate, Date toDate) {
    	StringBuilder sqlBuilder = new StringBuilder(" select ");
    	sqlBuilder.append(" b.department.id,b.department.deptName, a.dealerId, b.userName, count(*) from ");
    	sqlBuilder.append(WfNodeInst.class.getName()).append(" as a, ");
    	sqlBuilder.append(SysUser.class.getName()).append(" as b ");
    	sqlBuilder.append(" where a.dealerId = b.id and a.instStatus='0' and a.curNodeId = 'N888' ");
    	// 以下是动态查询
    	if (StringUtils.isNotBlank(deptId)) {
    	    sqlBuilder.append(" and b.department.id = ?1 ");
    	}
    	if (StringUtils.isNotBlank(userId)) {
    	    sqlBuilder.append(" and a.dealerId = ?2 ");
    	}
    	if (fromDate != null) {
    	    sqlBuilder.append(" and a.arrivalTime >= ?3 ");
    	}
    	if (toDate != null) {
    	    sqlBuilder.append(" and a.arrivalTime <= ?4 ");
    	}
    	sqlBuilder.append(" group by b.department.id,b.department.deptName,a.dealerId,b.userName ");

    	Query query = em.createQuery(sqlBuilder.toString());

    	if (StringUtils.isNotBlank(deptId)) {
    	    query.setParameter(1, new Long(deptId));
    	}
    	if (StringUtils.isNotBlank(userId)) {
    	    query.setParameter(2, new Long(userId));
    	}
    	if (fromDate != null) {
    	    query.setParameter(3, fromDate);
    	}
    	if (toDate != null) {
    	    query.setParameter(4, toDate);
    	}

    	List<DocStatisShowBean> resList = new ArrayList<DocStatisShowBean>();
    	List list = query.getResultList();
    	String[] names = { "deptId", "deptName", "userId", "userName", "reading" };
    	for (Object o : list) {
    	    Map<String, Object> map = toMap(names, (Object[]) o);
    	    DocStatisShowBean b = new DocStatisShowBean();
    	    try {
    	        PropertyUtils.copyProperties(b, map);
    	        resList.add(b);
    	    } catch (IllegalAccessException e) {
    	        e.printStackTrace();
    	    } catch (InvocationTargetException e) {
    	        e.printStackTrace();
    	    } catch (NoSuchMethodException e) {
    	        e.printStackTrace();
    	    }
    	}
    	return resList;
    }


    //公文已阅数统计
    private List<DocStatisShowBean> readedStat(String deptId, String userId, Date fromDate, Date toDate) {
    	StringBuilder sqlBuilder = new StringBuilder(" select ");
    	sqlBuilder.append(" b.department.id,b.department.deptName, a.dealerId, b.userName, count(*) from ");
    	sqlBuilder.append(WfNodeInst.class.getName()).append(" as a, ");
    	sqlBuilder.append(SysUser.class.getName()).append(" as b ");
    	sqlBuilder.append(" where a.dealerId = b.id and a.readFlag='1' and a.curNodeId = 'N888' ");
    	// 以下是动态查询
    	if (StringUtils.isNotBlank(deptId)) {
    	    sqlBuilder.append(" and b.department.id = ?1 ");
    	}
    	if (StringUtils.isNotBlank(userId)) {
    	    sqlBuilder.append(" and a.dealerId = ?2 ");
    	}
    	if (fromDate != null) {
    	    sqlBuilder.append(" and a.arrivalTime >= ?3 ");
    	}
    	if (toDate != null) {
    	    sqlBuilder.append(" and a.arrivalTime <= ?4 ");
    	}
    	sqlBuilder.append(" group by b.department.id,b.department.deptName,a.dealerId,b.userName ");

    	Query query = em.createQuery(sqlBuilder.toString());

    	if (StringUtils.isNotBlank(deptId)) {
    	    query.setParameter(1, new Long(deptId));
    	}
    	if (StringUtils.isNotBlank(userId)) {
    	    query.setParameter(2, new Long(userId));
    	}
    	if (fromDate != null) {
    	    query.setParameter(3, fromDate);
    	}
    	if (toDate != null) {
    	    query.setParameter(4, toDate);
    	}

    	List<DocStatisShowBean> resList = new ArrayList<DocStatisShowBean>();
    	List list = query.getResultList();
    	String[] names = { "deptId", "deptName", "userId", "userName", "readed" };
    	for (Object o : list) {
    	    Map<String, Object> map = toMap(names, (Object[]) o);
    	    DocStatisShowBean b = new DocStatisShowBean();
    	    try {
    	        PropertyUtils.copyProperties(b, map);
    	        resList.add(b);
    	    } catch (IllegalAccessException e) {
    	        e.printStackTrace();
    	    } catch (InvocationTargetException e) {
    	        e.printStackTrace();
    	    } catch (NoSuchMethodException e) {
    	        e.printStackTrace();
    	    }
    	}
    	return resList;
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
    public Map columnStatistical(String deptId, String userId, Date fromDay, Date toDay) {
        List<DocStatisShowBean> list = this.summary(deptId, userId, fromDay, toDay, false);
        List dataList = new ArrayList();
        List cate = new ArrayList();
        if (Tools.isNotEmptyList(list)) {
            DocStatChartBean cBean = new DocStatChartBean();
            DocStatChartBean dobyBean = new DocStatChartBean();
            DocStatChartBean doingBean = new DocStatChartBean();
            List cList = new ArrayList();
            List dobyList = new ArrayList();
            List doingList = new ArrayList();
            cBean.setName("创建");
            dobyBean.setName("经办");
            doingBean.setName("待办");
            for (int j = 0; j < list.size(); j++) {
                cList.add(list.get(j).getCreateNum());
                dobyList.add(list.get(j).getDealed());
                doingList.add(list.get(j).getFordo());
                cate.add(list.get(j).getUserName());
            }
            cBean.setData(cList);
            dobyBean.setData(dobyList);
            doingBean.setData(doingList);
            dataList.add(cBean);
            dataList.add(dobyBean);
            dataList.add(doingBean);
        }
        Map map = new HashMap();
        JSONArray jsonArray = JSONArray.fromObject(dataList);
        String datas = jsonArray.toString();
        map.put("datas", datas);
        map.put("categories", JSONArray.fromObject(cate).toString());
        return map;
    }

    public Map pieStatistical(String deptId, String userId, Date fromDay, Date toDay) {
    	List<DocStatisShowBean> list = this.summary(deptId, userId, fromDay, toDay, false);
        /**
         * 数据格式 [ ['Firefox', 45.0], ['IE', 26.8], ['Safari', 8.5], ['Opera', 6.2], ['Others', 0.7] ]
         */
        List dataList = new ArrayList();
        if (Tools.isNotEmptyList(list)) {
            for (int j = 0; j < list.size(); j++) {
                List cList = new ArrayList();
                cList.add(list.get(j).getUserName() + " ," + "创建");
                cList.add(list.get(j).getCreateNum());
                dataList.add(cList);

                List dobyList = new ArrayList();
                dobyList.add(list.get(j).getUserName() + "," + "经办");
                dobyList.add(list.get(j).getDealed());
                dataList.add(dobyList);

                List doingList = new ArrayList();
                doingList.add(list.get(j).getUserName() + "," + "待办");
                doingList.add(list.get(j).getFordo());
                dataList.add(doingList);
            }
        }
        Map map = new HashMap();
        JSONArray jsonArray = JSONArray.fromObject(dataList);
        String datas = jsonArray.toString();
        map.put("datas", datas);
        return map;
    }


}