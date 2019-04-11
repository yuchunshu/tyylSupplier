/*
 * FileName:    LoginSuccessLog.java
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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysUserRepository;
import cn.com.chaochuang.common.util.UserHelper;

/**
 * @author LaoZhiYong
 *
 */
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private LogService        service;

    private Boolean           alwaysUseDefaultTargetUrl;

    private String            defaultTargetUrl;

    @Autowired
    private SysUserRepository uRepository;

    // @Autowired
    // private CMSService cmsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {
        SysUser u = UserHelper.getCurrentUser();
        u.setLoginFailCount(0);
//        uRepository.save(u);
        // 日志
        service.saveLog(SjType.普通操作, "登录系统",LogStatus.成功, request);
        super.setAlwaysUseDefaultTargetUrl(alwaysUseDefaultTargetUrl);
        super.setDefaultTargetUrl(defaultTargetUrl);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    public void setAlwaysUseDefaultTargetUrl(Boolean alwaysUseDefaultTargetUrl) {
        this.alwaysUseDefaultTargetUrl = alwaysUseDefaultTargetUrl;
    }

    public void setDefaultTargetUrl(String defaultTargetUrl) {
        this.defaultTargetUrl = defaultTargetUrl;
    }

}
