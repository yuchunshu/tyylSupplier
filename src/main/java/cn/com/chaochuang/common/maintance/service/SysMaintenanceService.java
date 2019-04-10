/*
 * FileName:    SysMaintanceService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年11月17日 (huangm) 1.0 Create
 */

package cn.com.chaochuang.common.maintance.service;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.maintance.bean.MaintenanceEditBean;
import cn.com.chaochuang.common.maintance.domain.SysMaintenance;

/**
 * @author huangm
 *
 */
public interface SysMaintenanceService extends CrudRestService<SysMaintenance, String> {

    /**
     * 保存提问
     *
     * @param bean
     * @return
     */
    String saveQuestion(MaintenanceEditBean bean);

    /**
     * 处理提问（回答）
     *
     * @param bean
     * @return
     */
    String saveAnswer(MaintenanceEditBean bean);
}
