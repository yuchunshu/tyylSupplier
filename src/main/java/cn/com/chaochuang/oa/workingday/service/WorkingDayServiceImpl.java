/*
 * FileName:    OaNoticeServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.workingday.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.IDictionaryBuilder;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.oa.workingday.domain.WorkingDay;
import cn.com.chaochuang.oa.workingday.repository.WorkingDayRepository;

/**
 * @author huangwq
 *
 */
@Service
@Transactional
public class WorkingDayServiceImpl extends SimpleLongIdCrudRestService<WorkingDay>
                implements WorkingDayService, IDictionaryBuilder {
    @Autowired
    private WorkingDayRepository repository;

    @Override
    public SimpleDomainRepository<WorkingDay, Long> getRepository() {

        return repository;
    }

    @Override
    public WorkingDay findAllWorkingDay(Date dayDate) {
        return repository.findByDayDate(dayDate);
    }

    @Override
    public String getDictionaryName() {
        return WorkingDay.class.getSimpleName();
    }

    @Override
    public Map<String, IDictionary> getDictionaryMap() {
        List<WorkingDay> list = repository.findAll();
        Map<String, IDictionary> data = new HashMap<String, IDictionary>();
        if (Tools.isNotEmptyList(list)) {
            for (WorkingDay day : list) {
                data.put(day.getKey(), day);
            }
        }
        return data;
    }

    @Override
    public boolean isRefreshable() {
        return false;
    }

    @Override
    public void refresh() {
        DictionaryRefresher.getInstance().refreshIDictionaryBuilder(this);
    }

    @Override
    public Class getDictionaryClass() {
        return WorkingDay.class;
    }

}
