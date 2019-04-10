/*
 * FileName:    ReceiveFileController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.oa.workingday.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.oa.workingday.bean.WorkingDayBean;
import cn.com.chaochuang.oa.workingday.domain.WorkingDay;
import cn.com.chaochuang.oa.workingday.service.WorkingDayService;

/**
 * @author huangwq
 *
 */
@Controller
@RequestMapping("oa/workingday")
public class WorkingDayController {
    @Autowired
    private WorkingDayService   workingDayService;
    @Autowired
    protected ConversionService conversionService;

    @RequestMapping("/returnWorkDay.json")
    @ResponseBody
    public List<WorkingDayBean> returnWorkDay(String flag) {
        SearchBuilder<WorkingDay, Date> searchBuilder = new SearchBuilder<WorkingDay, Date>(conversionService);
        searchBuilder.getFilterBuilder().equal("dateFlag", flag);
        return BeanCopyBuilder.buildList(searchBuilder.findAll(workingDayService.getRepository()), WorkingDayBean.class);
    }

}
