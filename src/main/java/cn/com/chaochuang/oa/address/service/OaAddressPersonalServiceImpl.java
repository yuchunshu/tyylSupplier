/*
 * FileName:    OaAddressPersonalServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.address.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.oa.address.domain.OaAddressPersonal;
import cn.com.chaochuang.oa.address.repository.OaAddressPersonalRepository;

/**
 * @author HM
 *
 */
@Service
@Transactional
public class OaAddressPersonalServiceImpl extends SimpleLongIdCrudRestService<OaAddressPersonal> implements
                OaAddressPersonalService {

    @Autowired
    private OaAddressPersonalRepository repository;

    @Override
    public SimpleDomainRepository<OaAddressPersonal, Long> getRepository() {
        return this.repository;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.oa.address.service.OaAddressPersonalService#getOaAddressPersonal(java.lang.Long)
     */
    @Override
    public List<OaAddressPersonal> getOaAddressPersonal(Long id) {
        if (id != null) {// 若id不为空则按倒叙查找新增的内容
            // System.out.println("getContactCard id:" + id);
            return this.repository.findByIdGreaterThanOrderByIdAsc(id);
        }// 若id为空则返回所有数据
        return this.repository.findAll();
    }

}
