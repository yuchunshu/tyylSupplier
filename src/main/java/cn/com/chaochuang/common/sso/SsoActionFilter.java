/*
 * FileName:    SsoActionFilter.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年3月11日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.sso;

import com.google.common.collect.Maps;
import com.spower.sso.common.service.ISSOWebService;
import com.spower.sso.common.service.impl.UserSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;

/**
 * @author guig
 *
 */
public class SsoActionFilter extends GenericFilterBean implements InitializingBean {

    protected final Log                                          logger                      = LogFactory.getLog(getClass());

    /** 在 session 中标识当前用户. */
    public static final String CURRENT_USER_ID             = "sso.currentUserId";
    /** 在 session 中标识当前用户会话Id. */
    public static final String CURRENT_USER_SESSION        = "sso.currentUserSession";

    private final static String DEFAULT_NAVIGATEINURL       = "/sso/navigateIn.sso";
    private final static String DEFAULT_LOGOUTURL           = "/sso/logOut.sso";

    private final static String DEFAULT_NAVIGATEOUTURL      = "/sso/navigateOut.sso";

    private final RedirectStrategy                               redirectStrategy            = new DefaultRedirectStrategy();

    private String navigateInUrl               = DEFAULT_NAVIGATEINURL;

    private String logOutUrl                   = DEFAULT_LOGOUTURL;

    private String navigateOutUrl              = DEFAULT_NAVIGATEOUTURL;

    private String defaultTargetUrl            = "/";

    private String ssoLoginPageUrl;

    private String ssoServerNavigateinUrl;

    private String forwardLogOutUrl;

    private UserDetailsService                                   userDetailsService;

    protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    private AuthenticationManager                                authenticationManager;

    private String keySalt;

    private MessageDigestPasswordEncoder                         passwordEncoder;

    @Autowired(required = false)
    private ISSOWebService ssoWebService;

    private ConcurrentMap<String, Long> ssoSessionValidTimeMap      = Maps.newConcurrentMap();

    private long                                                 ssoSessionValidDelta        = 30 * 1000L;

    private boolean isSsoSessionValid(String ssoSessionId) {

        if (StringUtils.isBlank(ssoSessionId))
            return true;

        synchronized (ssoSessionValidTimeMap) {
            long nt = System.currentTimeMillis();

            if (ssoSessionValidTimeMap.containsKey(ssoSessionId)) {
                Long origT = ssoSessionValidTimeMap.get(ssoSessionId);
                if (null != origT) {
                    if (nt < origT) {
                        return false;
                    }
                }
            }

            ssoSessionValidTimeMap.put(ssoSessionId, nt + ssoSessionValidDelta);
        }

        return true;
    }

    protected String buildAutoLoginPassword(String encPass) {

        long lt = System.currentTimeMillis();
        String sa = Long.toString(lt);

        String ap = passwordEncoder.encodePassword(encPass + keySalt + sa, null);

        return "{" + ap + sa + "}";

    }

    protected boolean doLoginIn(HttpServletRequest request, final String userId, final String ssoSessionId) {
        UserDetails ud = userDetailsService.loadUserByUsername(userId);
        if (null != ud) {
            String pwd = ud.getPassword();

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userId,
                            buildAutoLoginPassword(pwd));

            setDetails(request, authRequest, ssoSessionId, userId);

            Authentication authResult = this.getAuthenticationManager().authenticate(authRequest);

            if (authResult.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authResult);
                return true;
            }
        }

        return false;

    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest,
                              final String ssoSessionId, final String userId) {
        Object detail = authenticationDetailsSource.buildDetails(request);
        if (detail instanceof WebAuthenticationDetails) {
            detail = new SsoWebAuthenticationDetails(request, (WebAuthenticationDetails) detail, ssoSessionId, userId);
        }
        authRequest.setDetails(detail);
    }

    protected boolean ssoSessionIdValid(HttpServletRequest request, final String ssoSessionId, final String userId) {
        if (StringUtils.isNotBlank(ssoSessionId) && StringUtils.isNotBlank(userId)) {
            return ssoSessionIdValid(ssoSessionId, userId, request.getRemoteAddr());
        }
        return false;
    }

    protected boolean ssoSessionIdValid(final String ssoSessionId, final String userId, final String clientId) {
        if (null != ssoWebService) {
            UserSession us = new UserSession();
            us.setSessionId(ssoSessionId);
            us.setUserId(userId);
            us.setClientId(clientId);
            return ssoWebService.validate(us);
        }
        return false;
    }

    protected void doNavigateIn(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                    throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("doNavigateIn");
        }

        String ssoSessionId = request.getParameter(CURRENT_USER_SESSION);
        String userId = request.getParameter(CURRENT_USER_ID);

        if (StringUtils.isNotBlank(ssoSessionId) && StringUtils.isNotBlank(userId)) {
            if (ssoSessionIdValid(request, ssoSessionId, userId)) {
                if (doLoginIn(request, userId.trim(), ssoSessionId.trim())) {
                    doRedirect(getDefaultTargetUrl(), request, response);
                }
                return;
            }
        }

        if (StringUtils.isNotBlank(getSsoLoginPageUrl())) {
            doRedirect(getSsoLoginPageUrl(), request, response);
        }
    }

    protected void doRedirect(final String redirectUrl, HttpServletRequest request, HttpServletResponse response)
                    throws IOException {

        String targetUrl;

        if (UrlUtils.isAbsoluteUrl(redirectUrl)) {
            targetUrl = redirectUrl;
        } else {
            RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();

            urlBuilder.setScheme(request.getScheme());
            urlBuilder.setServerName(request.getServerName());
            urlBuilder.setPort(request.getServerPort());
            urlBuilder.setContextPath(request.getContextPath());
            urlBuilder.setPathInfo(redirectUrl);

            targetUrl = urlBuilder.getUrl();
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected void doLogOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("doLogOut");
        }

        if (StringUtils.isBlank(getForwardLogOutUrl())) {
            request.getSession().invalidate();

            if (StringUtils.isNotBlank(getSsoLoginPageUrl())) {
                doRedirect(getSsoLoginPageUrl(), request, response);
            }
        } else {
            doRedirect(getForwardLogOutUrl(), request, response);
        }

    }

    protected void doNavigateOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (StringUtils.isNotBlank(getSsoServerNavigateinUrl())) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (null != authentication) {
                Object detail = authentication.getDetails();
                if (detail instanceof SsoWebAuthenticationDetails) {
                    SsoWebAuthenticationDetails ssoDetail = (SsoWebAuthenticationDetails) detail;

                    String ssoSessionId = ssoDetail.getSsoSessionId();
                    String userId = ssoDetail.getUserId();

                    if (StringUtils.isNotBlank(ssoSessionId) && StringUtils.isNotBlank(userId)) {
                        StringBuilder paramBuilder = new StringBuilder();

                        paramBuilder.append("?");
                        paramBuilder.append(CURRENT_USER_SESSION).append("=").append(ssoSessionId);
                        paramBuilder.append("&").append(CURRENT_USER_ID).append("=").append(userId);

                        // 添加一个随机值，防止浏览器直接显示以前缓存的页面
                        paramBuilder.append("&_t=").append(Long.toString(System.currentTimeMillis()));

                        for (Iterator iterator = request.getParameterMap().keySet().iterator(); iterator.hasNext();) {
                            String key = (String) iterator.next();
                            paramBuilder.append("&").append(key).append("=").append(request.getParameter(key));
                        }

                        doRedirect(getSsoServerNavigateinUrl() + paramBuilder.toString(), request, response);
                    }

                }
            }

        }
    }

    protected boolean checkSsoSessionId(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            Object detail = authentication.getDetails();
            if (detail instanceof SsoWebAuthenticationDetails) {
                SsoWebAuthenticationDetails ssoDetail = (SsoWebAuthenticationDetails) detail;

                if (isSsoSessionValid(ssoDetail.getSsoSessionId())) {
                    if (!ssoSessionIdValid(request, ssoDetail.getSsoSessionId(), ssoDetail.getUserId())) {
                        request.getSession().invalidate();
                        return false;
                    }
                }

            }
        }

        return true;
    }

    protected String getMatchUri(final HttpServletRequest request) {
        String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(';');

        if (pathParamIndex > 0) {
            uri = uri.substring(0, pathParamIndex);
        }

        return uri;
    }

    public boolean matchUrl(final String uri, final String contextPath, final String filterProcessesUrl) {
        if ("".equals(contextPath)) {
            return uri.endsWith(filterProcessesUrl);
        }

        return uri.endsWith(contextPath + filterProcessesUrl);
    }

    /**
     * (non-Javadoc)
     *
     * @see javax.servlet.Filter#doFilter(ServletRequest, ServletResponse,
     *      FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        if (null != getSsoWebService()) {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            String uri = getMatchUri(request);
            String contextPath = request.getContextPath();

            if (matchUrl(uri, contextPath, this.getNavigateInUrl())) {
                doNavigateIn(request, response, chain);
            } else if (matchUrl(uri, contextPath, this.getNavigateOutUrl())) {
                doNavigateOut(request, response);
            } else if (matchUrl(uri, contextPath, this.getLogOutUrl())) {
                doLogOut(request, response);
            } else {
                if (checkSsoSessionId(request, response)) {
                    chain.doFilter(request, response);
                }
            }
        } else {
            chain.doFilter(req, res);
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see GenericFilterBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
    }

    /**
     * @return the navigateInUrl
     */
    public String getNavigateInUrl() {
        return navigateInUrl;
    }

    /**
     * @param navigateInUrl
     *            the navigateInUrl to set
     */
    public void setNavigateInUrl(String navigateInUrl) {
        this.navigateInUrl = navigateInUrl;
    }

    /**
     * @return the logOutUrl
     */
    public String getLogOutUrl() {
        return logOutUrl;
    }

    /**
     * @param logOutUrl
     *            the logOutUrl to set
     */
    public void setLogOutUrl(String logOutUrl) {
        this.logOutUrl = logOutUrl;
    }

    /**
     * @return the navigateOutUrl
     */
    public String getNavigateOutUrl() {
        return navigateOutUrl;
    }

    /**
     * @param navigateOutUrl
     *            the navigateOutUrl to set
     */
    public void setNavigateOutUrl(String navigateOutUrl) {
        this.navigateOutUrl = navigateOutUrl;
    }

    /**
     * @return the ssoLoginPageUrl
     */
    public String getSsoLoginPageUrl() {
        return ssoLoginPageUrl;
    }

    /**
     * @param ssoLoginPageUrl
     *            the ssoLoginPageUrl to set
     */
    public void setSsoLoginPageUrl(String ssoLoginPageUrl) {
        this.ssoLoginPageUrl = ssoLoginPageUrl;
    }

    /**
     * @return the ssoServerNavigateinUrl
     */
    public String getSsoServerNavigateinUrl() {
        return ssoServerNavigateinUrl;
    }

    /**
     * @param ssoServerNavigateinUrl
     *            the ssoServerNavigateinUrl to set
     */
    public void setSsoServerNavigateinUrl(String ssoServerNavigateinUrl) {
        this.ssoServerNavigateinUrl = ssoServerNavigateinUrl;
    }

    /**
     * @return the forwardLogOutUrl
     */
    public String getForwardLogOutUrl() {
        return forwardLogOutUrl;
    }

    /**
     * @param forwardLogOutUrl
     *            the forwardLogOutUrl to set
     */
    public void setForwardLogOutUrl(String forwardLogOutUrl) {
        this.forwardLogOutUrl = forwardLogOutUrl;
    }

    /**
     * @return the userDetailsService
     */
    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    /**
     * @param userDetailsService
     *            the userDetailsService to set
     */
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * @return the authenticationDetailsSource
     */
    public AuthenticationDetailsSource<HttpServletRequest, ?> getAuthenticationDetailsSource() {
        return authenticationDetailsSource;
    }

    /**
     * @param authenticationDetailsSource
     *            the authenticationDetailsSource to set
     */
    public void setAuthenticationDetailsSource(
                    AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    /**
     * @return the authenticationManager
     */
    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    /**
     * @param authenticationManager
     *            the authenticationManager to set
     */
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * @return the keySalt
     */
    public String getKeySalt() {
        return keySalt;
    }

    /**
     * @param keySalt
     *            the keySalt to set
     */
    public void setKeySalt(String keySalt) {
        this.keySalt = keySalt;
    }

    /**
     * @return the passwordEncoder
     */
    public MessageDigestPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    /**
     * @return the defaultTargetUrl
     */
    public String getDefaultTargetUrl() {
        return defaultTargetUrl;
    }

    /**
     * @param defaultTargetUrl
     *            the defaultTargetUrl to set
     */
    public void setDefaultTargetUrl(String defaultTargetUrl) {
        this.defaultTargetUrl = defaultTargetUrl;
    }

    /**
     * @param passwordEncoder
     *            the passwordEncoder to set
     */
    public void setPasswordEncoder(MessageDigestPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @return the ssoWebService
     */
    public ISSOWebService getSsoWebService() {
        return ssoWebService;
    }

    /**
     * @param ssoWebService
     *            the ssoWebService to set
     */
    public void setSsoWebService(ISSOWebService ssoWebService) {
        this.ssoWebService = ssoWebService;
    }

    private static class SsoWebAuthenticationDetails extends WebAuthenticationDetails {

        private static final long              serialVersionUID = -2535858507973783621L;

        private final WebAuthenticationDetails detail;
        private final String ssoSessionId;
        private final String userId;

        public SsoWebAuthenticationDetails(HttpServletRequest request, WebAuthenticationDetails source,
                                           String ssoSessionId, String userId) {
            super(request);
            this.detail = source;
            this.ssoSessionId = ssoSessionId;
            this.userId = userId;
        }

        /**
         * (non-Javadoc)
         *
         * @see WebAuthenticationDetails#getRemoteAddress()
         */
        @Override
        public String getRemoteAddress() {
            return detail.getRemoteAddress();
        }

        /**
         * (non-Javadoc)
         *
         * @see WebAuthenticationDetails#getSessionId()
         */
        @Override
        public String getSessionId() {
            return detail.getSessionId();
        }

        /**
         * @return the ssoSessionId
         */
        public String getSsoSessionId() {
            return this.ssoSessionId;
        }

        /**
         * @return the userId
         */
        public String getUserId() {
            return userId;
        }
    }

}
