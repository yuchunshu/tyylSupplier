/*
 * FileName:    BackReasonController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年12月7日 (leon) 1.0 Create
 */

package cn.com.chaochuang.doc.event.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author luobin
 *
 */
@Controller
@RequestMapping("doc/backreason")
public class BackReasonController {

    @RequestMapping("/select")
    public ModelAndView backReasonSelect(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/selectdialog/selectBackReason");
        mav.addObject("selectDialogTitle", "退文词条");
        return mav;
    }

}
