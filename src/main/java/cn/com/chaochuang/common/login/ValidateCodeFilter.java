/*
 * FileName:    VerificationCode.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年4月19日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.google.code.kaptcha.Constants;

/**
 * @author LJX
 *
 */
public class ValidateCodeFilter extends UsernamePasswordAuthenticationFilter {

    private String  validateCodeParameter;

    private boolean enableValidateCode;

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
                    throws AuthenticationException {
        if (enableValidateCode) {
            String code = request.getParameter(validateCodeParameter);
            String sCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (code == null || !code.equalsIgnoreCase(sCode)) {
                throw new ValidateCodeException("验证码错误");
            }
        }
        return super.attemptAuthentication(request, response);
    }

    public void setValidateCodeParameter(String validateCodeParameter) {
        this.validateCodeParameter = validateCodeParameter;
    }

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#setUsernameParameter(java.lang.String)
     */
    @Override
    public void setUsernameParameter(String usernameParameter) {
        // TODO Auto-generated method stub
        super.setUsernameParameter(usernameParameter);
    }

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#setPasswordParameter(java.lang.String)
     */
    @Override
    public void setPasswordParameter(String passwordParameter) {
        // TODO Auto-generated method stub
        super.setPasswordParameter(passwordParameter);
    }

    /**
     * @param enableValidateCode
     *            the enableValidateCode to set
     */
    public void setEnableValidateCode(boolean enableValidateCode) {
        this.enableValidateCode = enableValidateCode;
    }

}
