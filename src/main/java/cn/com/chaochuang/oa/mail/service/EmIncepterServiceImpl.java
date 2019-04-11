/*
 * FileName:    EmIncepterServiceImpl.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.oa.mail.domain.EmIncepter;
import cn.com.chaochuang.oa.mail.repository.EmIncepterRepository;

/**
 * @author HM
 *
 */
@Service
@Transactional
public class EmIncepterServiceImpl extends SimpleUuidCrudRestService<EmIncepter> implements EmIncepterService {

    @Autowired
    private EmIncepterRepository repository;

    @PersistenceContext
    private EntityManager        em;

    @Override
    public SimpleDomainRepository<EmIncepter, String> getRepository() {
        return this.repository;
    }

    @Override
    public EmIncepter findByEmailIdAndIncepterId(String emailId, Long incepterId) {

        return this.repository.findByEmailIdAndIncepterId(emailId, incepterId);
    }

    @Override
    public List<EmIncepter> findByEmailId(String emailId) {
        return this.repository.findByEmailId(emailId);
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.oa.mail.service.EmIncepterService#selectMailInbox(java.lang.String)
     */
    @Override
    public List<EmIncepter> selectMailInbox(String lastOutputTime) {
        Date lastSendTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<EmIncepter> list = null;
        try {
            if (Tools.isEmptyString(lastOutputTime)) {
                // 时间为空，根据id来查
                list = this.repository.selectEmIncepterById("0");
            } else {
                lastSendTime = sdf.parse(lastOutputTime);
                list = this.repository.selectEmIncepterBySaveDate(lastSendTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

}