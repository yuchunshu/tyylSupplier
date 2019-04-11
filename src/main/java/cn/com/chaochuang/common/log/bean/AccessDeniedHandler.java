/*
 * FileName:    AccessDeniedHandler.java
 * Description:
 * Company:     
 * Copyright:    (c) 2015
 * History:     2015年9月18日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.log.bean;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import cn.com.chaochuang.common.log.repository.LogRepository;
import cn.com.chaochuang.common.syspower.domain.SysPower;
import cn.com.chaochuang.common.syspower.repository.SysPowerRepository;

/**
 * @author LJX
 *
 */
public class AccessDeniedHandler extends AccessDeniedHandlerImpl {

    private String             errorPage;

    @Autowired
    private SysPowerRepository powerRepository;

    @Autowired
    private LogRepository      logRepository;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                    AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String uri = request.getRequestURI();
        String url = uri.substring(uri.indexOf("/", 2));
        SysPower power = this.powerRepository.findByUrl(url);
        if (power != null) {
            // this.logRepository.save(new Log((SysUser) UserTool.getInstance().getCurrentUser(),
            // request.getRemoteAddr(),
            // null, "非权限访问,url:" + power.getUrl() + ",名称：" + power.getPowerName(), new Date(),
            // LogType.审计事件, SjType.违规操作));
        }
        super.setErrorPage(errorPage);
        super.handle(request, response, accessDeniedException);
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

}
