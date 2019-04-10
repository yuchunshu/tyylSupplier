/*
 * FileName:    OaNoticeService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.workingday.service;

import java.util.Date;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.oa.workingday.domain.WorkingDay;

/**
 * @author HM
 *
 */
public interface WorkingDayService extends CrudRestService<WorkingDay, Long> {
    WorkingDay findAllWorkingDay(Date dayDate);

}
