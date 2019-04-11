/*
 * FileName:    LogInfoFilter.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年12月10日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.log.service;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author LaoZhiYong
 *
 */
public class LogInfoFilter extends HttpServlet implements Filter {
    private static final long serialVersionUID = -5414875314278648821L;

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException,
                    ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        logInfo.get().setIp(request.getRemoteAddr());
        logInfo.get().setUrl(request.getRequestURI());
        arg2.doFilter(arg0, arg1);
        return;
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    public static LogInfo getLogInfo() {
        return logInfo.get();
    }

    private static ThreadLocal<LogInfo> logInfo = new ThreadLocal<LogInfo>() {
                                                    public LogInfo initialValue() {
                                                        return new LogInfo();
                                                    }
                                                };

}
