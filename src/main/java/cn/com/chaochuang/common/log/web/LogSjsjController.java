/*
 * FileName:    LogController.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年12月12日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.log.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.chaochuang.common.log.service.LogService;

/**
 * @author LJX
 *
 */
@Controller
@RequestMapping("log/sjsj")
public class LogSjsjController {

    @Autowired
    private LogService service;

}
