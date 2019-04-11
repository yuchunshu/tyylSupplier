/*
 * FileName:    LogServiewceImpl.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年12月8日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.log.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleCrudRestService;
import cn.com.chaochuang.common.log.domain.SysLog;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.LogType;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.repository.LogRepository;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.syspower.domain.SysRole;
import cn.com.chaochuang.common.syspower.repository.SysRoleRepository;
import cn.com.chaochuang.common.user.domain.SysUser;

/**
 * @author LaoZhiYong
 *
 */
@Service
public class LogServiceImpl extends SimpleCrudRestService<SysLog, Long> implements LogService {
    @Autowired
    private LogRepository     repository;

    @Autowired
    private SysRoleRepository roleRepository;

    @PersistenceContext
    private EntityManager     em;

    @Override
    public SimpleDomainRepository<SysLog, Long> getRepository() {
        return repository;
    }

    @Override
    public void saveLog(SjType sjType, String content,LogStatus status, HttpServletRequest request) {
        SysUser u = (SysUser) UserTool.getInstance().getCurrentUser();
        SysLog l = new SysLog();
        l.setOperateDate(new Date());
        l.setOperator(u);
        l.setSjType(sjType);
        l.setContent(content);
        l.setIp(request.getRemoteAddr());
        l.setUrl(request.getRequestURI());
        l.setLogType(LogType.普通用户);
        l.setStatus(status);
        List<SysRole> roles = this.roleRepository.findByUsersId(u.getId());

        if (roles != null && roles.size() > 0) {
            for (SysRole r : roles) {
                if (LogType.安全保密员.getValue().equals(r.getRoleName())) {
                    l.setLogType(LogType.安全保密员);
                    break;
                }
                if (LogType.系统管理员.getValue().equals(r.getRoleName())) {
                    l.setLogType(LogType.系统管理员);
                    break;
                }
                if (LogType.安全审计员.getValue().equals(r.getRoleName())) {
                    l.setLogType(LogType.安全审计员);
                    break;
                }

            }
        }
        this.repository.save(l);

    }

    @Override
    public void saveUserLog(SysUser u, SjType sjType, String content,LogStatus status, HttpServletRequest request) {
        SysLog l = new SysLog();
        l.setOperateDate(new Date());
        l.setOperator(u);
        l.setSjType(sjType);
        l.setContent(content);
        l.setIp(request.getRemoteAddr());
        l.setUrl(request.getRequestURI());
        l.setLogType(LogType.普通用户);
        l.setStatus(status);
        List<SysRole> roles = this.roleRepository.findByUsersId(u.getId());

        if (roles != null && roles.size() > 0) {
            for (SysRole r : roles) {
                if (LogType.安全保密员.getValue().equals(r.getRoleName())) {
                    l.setLogType(LogType.安全保密员);
                    break;
                }
                if (LogType.系统管理员.getValue().equals(r.getRoleName())) {
                    l.setLogType(LogType.系统管理员);
                    break;
                }
                if (LogType.安全审计员.getValue().equals(r.getRoleName())) {
                    l.setLogType(LogType.安全审计员);
                    break;
                }

            }
        }
        this.repository.save(l);
    }
    
    
    @Override
    public void saveDefaultLog(String content, HttpServletRequest request) {
        saveLog(SjType.普通操作, content,LogStatus.成功, request);
    }
}
