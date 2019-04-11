/*
 * FileName:    OaNoticeService.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.datachange.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.oa.datachange.domain.DataChange;
import cn.com.chaochuang.oa.datachange.reference.DataChangeTable;
import cn.com.chaochuang.oa.datachange.reference.OperationType;

/**
 * @author huangwq
 *
 */
public interface DataChangeService extends CrudRestService<DataChange, Long> {
    public List<DataChange> getData();

    /**
     * 保存数据变动记录
     * 
     * @param changeScript
     * @param changeTable
     * @param operationType
     */
    public void saveDataChange(String changeScript, DataChangeTable changeTable, OperationType operationType);
}
