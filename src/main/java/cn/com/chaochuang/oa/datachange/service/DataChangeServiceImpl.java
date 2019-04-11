/*
 * FileName:    OaNoticeServiceImpl.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.datachange.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.oa.datachange.domain.DataChange;
import cn.com.chaochuang.oa.datachange.reference.DataChangeTable;
import cn.com.chaochuang.oa.datachange.reference.OperationType;
import cn.com.chaochuang.oa.datachange.repository.DataChangeRepository;

/**
 * @author huagnwq
 *
 */
@Service
@Transactional
public class DataChangeServiceImpl extends SimpleLongIdCrudRestService<DataChange> implements DataChangeService {

    @Autowired
    private DataChangeRepository repository;

    @Override
    public SimpleDomainRepository<DataChange, Long> getRepository() {
        return this.repository;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.oa.datachange.service.DataChangeService#getData()
     */
    @Override
    public List<DataChange> getData() {
        // TODO Auto-generated method stub

        return repository.findAll();
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.oa.datachange.service.DataChangeService#saveDataChange(cn.com.chaochuang.oa.datachange.bean.DataChangeBean)
     */
    @Override
    public void saveDataChange(String changeScript, DataChangeTable changeTable, OperationType operationType) {
        DataChange change = new DataChange();
        change.setChangeDate(new Date());
        change.setChangeScript(changeScript);
        change.setChangeTableName(changeTable);
        change.setOperationType(operationType);
        this.repository.save(change);

    }

}
