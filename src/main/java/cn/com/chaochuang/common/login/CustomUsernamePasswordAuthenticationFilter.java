/*
 * FileName:    CustomUsernamePasswordAuthenticationFilter.java
 * Description:
 * Company:     
 * Copyright:    (c) 2015
 * History:     2015年9月11日 (frt) 1.0 Create
 */

package cn.com.chaochuang.common.login;

/**
 * @author frt
 *
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired(required = false)
    public void setSessionAuthenticationStrategy(SessionAuthenticationStrategy sessionStrategy) {
        super.setSessionAuthenticationStrategy(sessionStrategy);
    }

}
