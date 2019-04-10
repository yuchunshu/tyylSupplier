/*
 * FileName:    OaDocDeptFileServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年9月14日 (yuchunshu) 1.0 Create
 */

package cn.com.chaochuang.doc.event.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.doc.event.domain.OaDocDeptFile;
import cn.com.chaochuang.doc.event.repository.OaDocDeptFileRepository;

/**
 * @author yuchunshu
 *
 */
@Service
@Transactional
public class OaDocDeptFileServiceImpl extends SimpleUuidCrudRestService<OaDocDeptFile> implements OaDocDeptFileService {

    @Autowired
    private OaDocDeptFileRepository repository;

    @Override
    public SimpleDomainRepository<OaDocDeptFile, String> getRepository() {
        return this.repository;
    }
    
}
