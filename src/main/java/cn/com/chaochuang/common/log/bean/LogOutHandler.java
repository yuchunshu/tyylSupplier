/*
 * FileName:    LogOutHandler.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年12月25日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.log.bean;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.UserInfo;
import cn.com.chaochuang.common.user.domain.SysUser;

/**
 * @author LaoZhiYong
 *
 */
public class LogOutHandler extends SimpleUrlLogoutSuccessHandler {

    private Boolean      alwaysUseDefaultTargetUrl;

    private String       defaultTargetUrl;

    @Autowired
    private LogService   logService;
    
    // @Autowired
    // private CMSService cmsService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                    throws IOException, ServletException {

        if (authentication != null) {
            UserInfo uinfo = (UserInfo) authentication.getPrincipal();
            SysUser u = (SysUser) uinfo.getUserObject();
            logService.saveUserLog(u, SjType.普通操作, "用户：" + u.getUserName() + "/账户名：" + u.getAccount() + "登出系统。",LogStatus.成功, request);
        }

        super.setAlwaysUseDefaultTargetUrl(alwaysUseDefaultTargetUrl);
        super.setDefaultTargetUrl(defaultTargetUrl);
        super.onLogoutSuccess(request, response, authentication);
    }

    public void setAlwaysUseDefaultTargetUrl(Boolean alwaysUseDefaultTargetUrl) {
        this.alwaysUseDefaultTargetUrl = alwaysUseDefaultTargetUrl;
    }

    public void setDefaultTargetUrl(String defaultTargetUrl) {
        this.defaultTargetUrl = defaultTargetUrl;
    }

}
