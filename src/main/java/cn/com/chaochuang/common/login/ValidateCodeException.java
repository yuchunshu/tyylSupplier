/*
 * FileName:    ValidateCodeException.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年4月20日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.login;

import org.springframework.security.core.AuthenticationException;

/**
 * @author LJX
 *
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -6784140550010545043L;

    public ValidateCodeException(String msg) {
        super(msg);
    }

    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    @Deprecated
    protected ValidateCodeException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }
}
