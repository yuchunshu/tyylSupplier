/*
 * FileName:    LogAspect.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年12月12日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.log.service;

import java.util.Map;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.chaochuang.common.log.repository.LogRepository;
import cn.com.chaochuang.common.syspower.repository.SysPowerRepository;

/**
 * @author LaoZhiYong
 *
 */
// @Aspect
@Component
public class LogAspect {
    @Autowired
    private LogRepository       repository;
    @Autowired
    private SysPowerRepository  powerRepository;

    // @Value("${role.aqbmgly}")
    // private String aqbmgly;

    // @Value("${role.admin}")
    // private String admin;

    @Resource(name = "logUrlMap")
    private Map<String, String> logUrlMap;
    @Resource(name = "logIgnoreMap")
    private Map<String, String> logIgnoreMap;

    @Pointcut("execution(public org.springframework.web.servlet.ModelAndView cn.com.chaochuang..*Controller.*(..))")
    public void logPointCut() {

    }

    @AfterReturning("logPointCut()")
    public void doLog(JoinPoint jp) {
        // SysUser user = UserHelper.getCurrentUser();
        // LogInfo info = LogInfoFilter.getLogInfo();
        // String url = info.getUrl().substring(6);
        // // 清理不需要记录的url
        // if (url.indexOf("/list") > 0 || url.indexOf("/info") > 0 || logIgnoreMap.containsKey(url)) {
        // return;
        // }
        // String operate = logUrlMap.get(url);
        // SysPower power = powerRepository.findByUrl(url);
        // // SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        // if (null != power) {
        // operate = power.getPowerName();
        // }
        // if (null != operate) {
        // Log log = new Log();
        // log.setOperator(user);
        // log.setOperateIp(info.getIp());
        // log.setOperateDate(new Date());
        // log.setOperateType(operate);
        // Set<SysRole> roles = user.getRoles();
        // boolean flag = false;
        // if (roles != null && roles.size() > 0) {
        // for (SysRole role : roles) {
        // // if (role.getRoleName().equals(admin) || role.getRoleName().equals(aqbmgly)) {
        // // flag = true;
        // // }
        // }
        // }
        // if (flag) {
        // log.setLogType(LogType.审计事件);
        // }
        // repository.save(log);
        // }
    }
}
