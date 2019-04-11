/*
 * FileName:    GoldGridController.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年3月1日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.goldgrid.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author LJX
 *
 */
@Controller
@RequestMapping("goldgrid")
public class GoldGridDemoController {

    @RequestMapping("/edit")
    public ModelAndView export(HttpServletRequest request, HttpSession jsessionid) {
        ModelAndView mav = new ModelAndView("/goldgrid/edit");
        System.out.print("====================" + jsessionid.getId()
                        + "===============================================================================");
        mav.addObject("jsessionid", jsessionid.getId());
        return mav;
    }

    @RequestMapping("/edit2009")
    public ModelAndView export2009(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/goldgrid/edit2009");
        return mav;
    }

}
