/*
 * FileName:    GoldgridController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月14日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.goldgrid.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.chaochuang.goldgrid.service.OfficeServer;

/**
 * @author LJX
 *
 */
@Controller
@RequestMapping("common/goldgrid")
public class GoldgridController {

    @Autowired
    private OfficeServer officeServer;

    @RequestMapping(value = "/OfficeServer", method = RequestMethod.POST)
    @ResponseBody
    public void list(HttpServletRequest request, HttpServletResponse response, HttpSession jsessionid)
                    throws ServletException, IOException {
        this.officeServer.service(request, response);
    }

    @RequestMapping(value = "/getJsessionId", method = RequestMethod.POST)
    @ResponseBody
    public String getJsessionId(HttpServletRequest request, HttpServletResponse response, HttpSession jsessionid)
                    throws ServletException, IOException {
        return jsessionid.getId();
    }
}
