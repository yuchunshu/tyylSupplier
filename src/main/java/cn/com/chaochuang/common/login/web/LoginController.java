/*
 * FileName:    LoginCtrl.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年4月20日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.login.web;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * @author LJX
 *
 */
@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    private Producer captchaProducer;

    @RequestMapping("/validateCode")
    public ModelAndView validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        response.setDateHeader("Expires", 0);

        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        response.setHeader("Pragma", "no-cache");

        response.setContentType("image/jpeg");

        String capText = captchaProducer.createText();

        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

        BufferedImage bi = captchaProducer.createImage(capText);

        ServletOutputStream out = response.getOutputStream();

        ImageIO.write(bi, "jpg", out);

        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }
}
