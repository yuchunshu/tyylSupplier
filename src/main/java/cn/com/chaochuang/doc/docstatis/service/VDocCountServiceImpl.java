/*
 * FileName:    VDocCountServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年12月5日 (ndy) 1.0 Create
 */

package cn.com.chaochuang.doc.docstatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleCrudRestService;
import cn.com.chaochuang.doc.docstatis.domain.VDocCount;
import cn.com.chaochuang.doc.docstatis.repository.VDocCountRepository;

/**
 * @author ndy
 *
 */
@Service
public class VDocCountServiceImpl extends SimpleCrudRestService<VDocCount, Long> implements VDocCountService {

    @Autowired
    private VDocCountRepository docCountRepository;

    @Override
    public SimpleDomainRepository<VDocCount, Long> getRepository() {
        return this.docCountRepository;
    }

}
