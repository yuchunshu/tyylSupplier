package cn.com.chaochuang.common.log.service;

import javax.servlet.http.HttpServletRequest;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.log.domain.SysLog;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.user.domain.SysUser;

/**
 *
 * @author LJX
 *
 */
public interface LogService extends CrudRestService<SysLog, Long> {

    public void saveLog(SjType sjType, String content,LogStatus status, HttpServletRequest request);

    /**
     * 登录登出日志
     *
     * @param sjType
     * @param content
     * @param request
     */
    public void saveUserLog(SysUser u, SjType sjType, String content,LogStatus status, HttpServletRequest request);
    
    /** 缺省的普通日志 */
    public void saveDefaultLog(String content, HttpServletRequest request);

    /** 日志*/
    void saveMobileLog(SysUser u, String content, HttpServletRequest request);
}
