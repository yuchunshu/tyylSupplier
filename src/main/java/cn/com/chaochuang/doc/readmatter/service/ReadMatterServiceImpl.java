/*
 * FileName:    OaReceiveFileService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.readmatter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.reference.ReadStatus;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.doc.docstatis.bean.DocStatisShowBean;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.readmatter.bean.ReadMatterOppBean;
import cn.com.chaochuang.doc.readmatter.domain.ReadMatter;
import cn.com.chaochuang.doc.readmatter.repository.ReadMatterRepository;

/**
 * @author huangwq
 *
 */
@Service
@Transactional
public class ReadMatterServiceImpl extends SimpleUuidCrudRestService<ReadMatter> implements ReadMatterService {

    @PersistenceContext
    private EntityManager       em;

    @Autowired
    private ReadMatterRepository repository;

    @Override
    public SimpleDomainRepository<ReadMatter, String> getRepository() {
        return repository;
    }

    @Override
    public List<ReadMatter> findByFileId(String fileId) {

        return this.repository.findByFileId(fileId);
    }

    @Override
    public void deleteReadMatterByFileId(String fileId) {
        this.repository.delete(this.findByFileId(fileId));
    }

    @Override
    public String saveReadMatter(ReadMatterOppBean bean) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        ReadMatter readMatter = null;
        if (StringUtils.isNotBlank(bean.getComId())) {
            readMatter = this.repository.findOne(bean.getComId());
        } else {
            readMatter = new ReadMatter();
        }

        readMatter.setReadMan(user.getId());
        readMatter.setReadTime(new Date());
        readMatter.setStatus(ReadStatus.已阅);
        readMatter.setOpinion(bean.getOpinion());

        if (bean.getTaskId() != null)
            readMatter.setTaskId(bean.getTaskId().toString());
        this.repository.save(readMatter);
        return readMatter.getId();
    }

	@Override
	public long selectReadMatterCount(String fileId) {
    	StringBuilder sqlBuilder = new StringBuilder(" select ");
    	sqlBuilder.append(" count(*) from ").append(ReadMatter.class.getName()).append(" as a where a.fileId='"+fileId+"'");
    	Query query = em.createQuery(sqlBuilder.toString());
    	List list = query.getResultList();
    	if (list != null && !list.isEmpty()) {
    		return (Long)list.get(0);
    	}
		return 0;
	}


}
