/*
 * FileName:    LoginFailureHandler.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年9月17日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.log.bean;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.chaochuang.mobile.service.MobileRegisterService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.login.ValidateCodeException;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysUserRepository;

/**
 * @author LJX
 *
 */
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @SuppressWarnings("unused")
    private String            defaultFailureUrl;

    private String            loginFailureLockedUrl;

    /** 登录错误1次 */
    private String            loginFailure_1;

    /** 登录错误2次 */
    private String            loginFailure_2;

    /** 登录错误3次 */
    private String            loginFailure_3;

    /** 登录错误4次 */
    private String            loginFailure_4;

    /** 验证码错误 */
    private String            validatecodeErrorPage;

    private String            accountRequestName     = "account";

    private Integer           loginFailureLimitTimes = 5;

    @Autowired
    private SysUserRepository uRepository;

    @Autowired
    private LogService        logService;

    @Autowired
    private MobileRegisterService mobileRegisterService;

    @Value(value = "${super.admin.user}")
    private Long              superUserId;

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                    AuthenticationException exception) throws IOException, ServletException {

        if (exception instanceof LockedException) {
            super.setDefaultFailureUrl(loginFailureLockedUrl);
        } else if (exception instanceof BadCredentialsException) {
            Integer failureTimes = 0;
            String account = request.getParameter(accountRequestName);
            if (StringUtils.isNotBlank(account)) {
                SysUser user = this.uRepository.findByAccountAndValid(account, SysUser.VALID);
                if (user != null) {
                    if (user.getLoginFailCount() == null) {
                        user.setLoginFailCount(1);
                    } else {
                        user.setLoginFailCount(user.getLoginFailCount() + 1);
                    }
                    this.logService.saveUserLog(user, SjType.普通操作, "连续登录失败" + user.getLoginFailCount() + "次",LogStatus.失败, request);
                    if (!user.getId().equals(this.superUserId) && user.getLoginFailCount() >= loginFailureLimitTimes) {
                        // 超级管理员不锁定
                        user.setAccountLocked(true);
                        //删除移动端token
                        this.mobileRegisterService.clearUserTokenId(user);
                        this.logService.saveUserLog(user, SjType.普通操作, "连续登录失败超过" + loginFailureLimitTimes + "次,用户被锁定",LogStatus.失败,
                                        request);
                    }
                    //this.uRepository.save(user);
                    failureTimes = user.getLoginFailCount();
                } else {
                    super.setDefaultFailureUrl(defaultFailureUrl);
                }
            }
            switch (failureTimes) {
            case 1:
                super.setDefaultFailureUrl(loginFailure_1);
                break;
            case 2:
                super.setDefaultFailureUrl(loginFailure_2);
                break;
            case 3:
                super.setDefaultFailureUrl(loginFailure_3);
                break;
            case 4:
                super.setDefaultFailureUrl(loginFailure_4);
                break;
            case 5:
                super.setDefaultFailureUrl(loginFailureLockedUrl);
                break;
            default:
                super.setDefaultFailureUrl(defaultFailureUrl);
                break;
            }

        } else if (exception instanceof ValidateCodeException) {
            super.setDefaultFailureUrl(validatecodeErrorPage);
        }
        super.setUseForward(true);

        super.onAuthenticationFailure(request, response, exception);
    }

    @Override
    public void setDefaultFailureUrl(String defaultFailureUrl) {
        this.defaultFailureUrl = defaultFailureUrl;
    }

    public void setLoginFailureLockedUrl(String loginFailureLockedUrl) {
        this.loginFailureLockedUrl = loginFailureLockedUrl;
    }

    public void setAccountRequestName(String accountRequestName) {
        this.accountRequestName = accountRequestName;
    }

    public void setLoginFailureLimitTimes(Integer loginFailureLimitTimes) {
        this.loginFailureLimitTimes = loginFailureLimitTimes;
    }

    public void setLoginFailure_1(String loginFailure_1) {
        this.loginFailure_1 = loginFailure_1;
    }

    public void setLoginFailure_2(String loginFailure_2) {
        this.loginFailure_2 = loginFailure_2;
    }

    public void setLoginFailure_3(String loginFailure_3) {
        this.loginFailure_3 = loginFailure_3;
    }

    public void setLoginFailure_4(String loginFailure_4) {
        this.loginFailure_4 = loginFailure_4;
    }

    /**
     * @param validatecodeErrorPage
     *            the validatecodeErrorPage to set
     */
    public void setValidatecodeErrorPage(String validatecodeErrorPage) {
        this.validatecodeErrorPage = validatecodeErrorPage;
    }

}
