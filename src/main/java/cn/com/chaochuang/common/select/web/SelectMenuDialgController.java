/*
 * FileName:    SelectMenuDialgController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年2月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.select.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.syspower.bean.PowerTreeBean;
import cn.com.chaochuang.common.syspower.service.SysPowerService;

/**
 * @author HM
 *
 */
@Controller
@RequestMapping("/select/menu")
public class SelectMenuDialgController {

    @Autowired
    private SysPowerService     powerService;

    @Autowired
    protected ConversionService conversionService;

    @RequestMapping("/selectMenuTree")
    public ModelAndView selectDepTree() {
        ModelAndView mav = new ModelAndView("/selectdialog/selectmenutreedialog");
        // 取数据的url
        mav.addObject("url", "select/menu/selectMenuTree.json");
        // 标题
        mav.addObject("selectDialogTitle", "选择菜单");
        return mav;
    }

    @RequestMapping("/selectMenuTree.json")
    @ResponseBody
    public List<PowerTreeBean> selectTreeJson(Integer page, Integer rows, HttpServletRequest request) {

        return this.powerService.getMenuTree();
    }
}
