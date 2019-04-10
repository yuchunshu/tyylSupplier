/*
 * FileName:    DocReadReceivesServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年12月6日 (ndy) 1.0 Create
 */

package cn.com.chaochuang.doc.docread.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleCrudRestService;
import cn.com.chaochuang.doc.docread.domain.DocReadReceives;
import cn.com.chaochuang.doc.docread.repository.DocReadReceivesRepository;

/**
 * @author ndy
 *
 */
@Service
public class DocReadReceivesServiceImpl extends SimpleCrudRestService<DocReadReceives, Long> implements
                DocReadReceivesService {

    @Autowired
    private DocReadReceivesRepository receivesRepository;

    @Override
    public SimpleDomainRepository<DocReadReceives, Long> getRepository() {
        return this.receivesRepository;
    }

}
