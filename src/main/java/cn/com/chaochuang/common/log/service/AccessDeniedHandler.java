/*
 * FileName:    AccessDeniedHandler.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年4月19日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.log.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;

/**
 * @author LJX
 *
 */
public class AccessDeniedHandler extends AccessDeniedHandlerImpl {

    private String     errorPage;

    @Autowired
    private LogService logService;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                    AccessDeniedException accessDeniedException) throws IOException, ServletException {
        this.logService.saveLog(SjType.违规访问, "违规访问URL:" + request.getRequestURI(),LogStatus.失败, request);
        super.setErrorPage(errorPage);
        super.handle(request, response, accessDeniedException);
    }

    public void setErrorPage(String errorPage) {
//        super.setErrorPage(errorPage);
        this.errorPage = errorPage;
    }

}
