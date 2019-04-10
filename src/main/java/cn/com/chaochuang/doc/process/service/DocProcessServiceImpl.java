/*
 * FileName:    ReceiveProcessServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年4月27日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.process.service;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchFilter;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.process.bean.ProcessShowBean;
import cn.com.chaochuang.doc.process.domain.DocProcess;
import cn.com.chaochuang.doc.process.repository.DocProcessRepository;
import cn.com.chaochuang.workflow.inst.domain.WfFlowInst;
import cn.com.chaochuang.workflow.inst.domain.WfNodeInst;

/**
 * @author huangwq
 *
 */
@Service
@Transactional
public class DocProcessServiceImpl extends SimpleLongIdCrudRestService<DocProcess> implements DocProcessService {

    @Autowired
    private DocProcessRepository repository;

    @PersistenceContext
    protected EntityManager      em;

    @Override
    public SimpleDomainRepository<DocProcess, Long> getRepository() {
        return this.repository;
    }

    @Override
    public void delDocProcess(OaDocFile obj) {
        this.repository.delete(this.repository.findByDocFile(obj));
    }

    @Override
    public void saveDocProcess(SysUser currentUser, OaDocFile receiveFile) {
        DocProcess obj = this.repository.findByDepIdAndDocFile(currentUser.getDepartment().getId(), receiveFile);
        if (obj == null) {
            obj = new DocProcess();
        }
        obj.setFileId(receiveFile.getId());
        obj.setDepId(currentUser.getDepartment().getId());
        obj.setProcessUserId(currentUser.getId());
        obj.setProcessUserName(currentUser.getUserName());
        obj.setProcessDate(new Date());
        obj.setProcessNumber(receiveFile.getFileCode());
        obj.setProcessDepName(currentUser.getDepartment().getDeptName());
        // 改变该公文在收文登记簿所有记录的当前办理科室
        List list = this.repository.findByDocFile(receiveFile);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                DocProcess dp = (DocProcess) list.get(i);
                dp.setProcessDepName(currentUser.getDepartment().getDeptName());
                this.repository.save(dp);
            }
        }
        this.repository.save(obj);

    }

    @Override
    public Page selectProcessPage(Collection<SearchFilter> values, Integer page, Integer rows) {

        String whereSql = this.processWhereSqlBuilder(values);
        StringBuffer hsql = new StringBuffer();
        hsql.append("select process from ").append(DocProcess.class.getName()).append(" as process where 1=1 ");
        hsql.append(whereSql).append(" order by process.processDate desc ");

        Query query = em.createQuery(hsql.toString());
        List<DocProcess> list = query.getResultList();
        Integer total = 0;
        if (list != null && list.size() > 0) {
            total = list.size();
        }

        query.setFirstResult(page - 1);
        query.setMaxResults(rows);
        list = query.getResultList();

        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(list, ProcessShowBean.class));
        p.setTotal((long) total);
        return p;
    }

    /**
     * 构造登记簿查询语句
     *
     * @param searchFilter
     * @return
     */
    private String processWhereSqlBuilder(Collection<SearchFilter> searchFilter) {
        StringBuffer whereSql = new StringBuffer();
        Iterator<SearchFilter> it = searchFilter.iterator();
        while (it.hasNext()) {
            SearchFilter filter = (SearchFilter) it.next();
            if (filter != null) {
                switch (filter.operator.name()) {
                case ("EQ"):
                    if (filter.fieldName.equals("node.dealer")) {
                        whereSql.append(" and exists(");
                        whereSql.append(" select flow.fileId from ").append(WfFlowInst.class.getName())
                                        .append(" as flow, ").append(WfNodeInst.class.getName()).append(" as node ");
                        whereSql.append(" where flow.id = node.instId and node.dealer = ").append(filter.value)
                                        .append(" and process.fileId = flow.fileId ");
                        whereSql.append(" )");
                    } else if (filter.fieldName.equals("process.depId")) {
                        whereSql.append(" and " + filter.fieldName + " = " + filter.value);
                    } else {
                        whereSql.append(" and " + filter.fieldName + " = '" + filter.value.toString() + "' ");
                    }
                    break;
                case ("LIKE"):
                    whereSql.append(" and " + filter.fieldName + " like '%" + filter.value.toString() + "%' ");
                    break;
                case "GTE":
                    whereSql.append(" and " + filter.fieldName + " >= '" + filter.value.toString() + "' ");
                    break;
                case "LTE":
                    whereSql.append(" and " + filter.fieldName + " <= '" + filter.value.toString() + "' ");
                    break;
                case "NOTEQ":
                    whereSql.append(" and " + filter.fieldName + " != '" + filter.value.toString() + "' ");
                    break;
                case "IN":
                    whereSql.append(" and " + filter.fieldName + " in (" + filter.value.toString() + ") ");
                    break;
                default:
                    break;
                }
            }
        }

        return whereSql.toString();
    }
}
