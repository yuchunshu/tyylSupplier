/*
 * FileName:    CheckPasswordChangeTimeFilter.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2014
 * History:     2014年12月23日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.user.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.filter.GenericFilterBean;

import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.WebUtils;

import com.google.common.collect.Sets;

/**
 * @author guig
 *
 */
public class CheckPasswordChangeTimeFilter extends GenericFilterBean {

    private final static String SESSION_CHECK_PASSWORD_TIME_OUT_OK_FLAG = CheckPasswordChangeTimeFilter.class.getName();

    private final static String SESSION_ACCOUNT                         = "account";

    /* 一天的毫秒数 */
    private final static long   A_DAY_MSEC                              = 1000L * 60 * 60 * 24;

    private String              changePasswordUrl                       = "";

    private Set<String>         ignoreUrlSet                            = Sets.newHashSet();

    private RedirectStrategy    redirectStrategy                        = new DefaultRedirectStrategy();

    /* 密码过期天数 */
    private Integer             passwordTimeoutDays                     = 90;

    /**
     * (non-Javadoc)
     *
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     *      javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
                    ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);

        if (session != null) {

            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            if (user != null) {
                if (!user.getAccount().equals(session.getAttribute(SESSION_ACCOUNT))) {
                    session.removeAttribute(SESSION_CHECK_PASSWORD_TIME_OUT_OK_FLAG);
                }
                if (session.getAttribute(SESSION_CHECK_PASSWORD_TIME_OUT_OK_FLAG) == null) {
                    String url = WebUtils.getPowerUrlFromRequest(request);
                    if (!ignoreUrlSet.contains(url)) {
                        session.setAttribute(SESSION_ACCOUNT, user.getAccount());

                        boolean needChangePassword = true;
                        if (null != user.getLastPasswdDate()) {
                            long nextChangePasswordTime = user.getLastPasswdDate().getTime() + A_DAY_MSEC
                                            * passwordTimeoutDays;

                            if (System.currentTimeMillis() <= nextChangePasswordTime) {
                                needChangePassword = false;
                                session.setAttribute(SESSION_CHECK_PASSWORD_TIME_OUT_OK_FLAG, new Long(
                                                nextChangePasswordTime));
                            }
                        }

                        if (needChangePassword) {
                            redirectStrategy.sendRedirect(request, response, changePasswordUrl);

                            return;
                        }
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }

    public String getChangePasswordUrl() {
        return changePasswordUrl;
    }

    public void setChangePasswordUrl(String changePasswordUrl) {
        this.changePasswordUrl = changePasswordUrl;
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    public Integer getPasswordTimeoutDays() {
        return passwordTimeoutDays;
    }

    public void setPasswordTimeoutDays(Integer passwordTimeoutDays) {
        this.passwordTimeoutDays = passwordTimeoutDays;
    }

    public void setIgnoreUrlSet(String[] ignoreUrlList) {
        Set<String> ss = Sets.newHashSet();
        ss.addAll(Arrays.asList(ignoreUrlList));
        this.ignoreUrlSet = ss;
    }
}
