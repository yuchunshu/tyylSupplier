/*
 * FileName:    SelectDataController.java
 * Description:
 * Company:     
 * Copyright:    (c) 2015
 * History:     2015年11月13日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.selectdataog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author LJX
 *
 */
@Controller
@RequestMapping("selectdata")
public class SelectDataController {

    @RequestMapping("/selectPrjSingle")
    public ModelAndView selectPrjSingle(String acceptMode, Long handleDepId) {
        ModelAndView mav = new ModelAndView("/selectdata/selectprjsingle");
        mav.addObject("acceptMode", acceptMode);
        mav.addObject("handleDepId", handleDepId);
        return mav;
    }

    @RequestMapping("/selectUser")
    public ModelAndView selectUser(String acceptMode) {
        ModelAndView mav = new ModelAndView("/selectdata/selectuser");
        // id名称
        mav.addObject("idName", "id");
        // 显示名称
        mav.addObject("showName", "userName");
        return mav;
    }

}
