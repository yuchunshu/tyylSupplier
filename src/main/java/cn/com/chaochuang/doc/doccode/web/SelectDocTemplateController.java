/*
 * FileName:    SelectMailIncepterController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年8月3日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.doccode.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.doc.template.bean.DocTemplateShowBean;
import cn.com.chaochuang.doc.template.domain.DocTemplate;
import cn.com.chaochuang.doc.template.service.DocTemplateService;

/**
 * 文号选模板控制器
 *
 * @author 余春澍
 *
 */
@Controller
@RequestMapping("/doc/doccode/select")
public class SelectDocTemplateController {

	@Autowired
    protected DocTemplateService docTemplateService;
     
    @Autowired
    protected ConversionService  conversionService;

    @RequestMapping("/getIncepters")
    public ModelAndView selectDocTemplateSingle() {
        ModelAndView mav = new ModelAndView("/doc/doccode/selectdialogtemplate");
        // 取数据的url
        mav.addObject("url", "/doc/doccode/select/selectDocTemplateByDept.json");
        // 是否单选
        mav.addObject("single", true);
        // 显示名称
        mav.addObject("dataText", "userName");
        // 标题
        mav.addObject("selectDialogTitle", "选择模板");
        // 窗口宽度
        mav.addObject("dialogWidth", "530");
        // 窗口高度
        mav.addObject("dialogHeight", "470");

        return mav;
    }

    /**
     * 取发文模板数据的JSON
     *
     * @param page
     * @param rows
     * @param request
     * @return
     */
    @RequestMapping("/selectDocTemplateByDept.json")
    @ResponseBody
    public Page selectDocTemplateByDept(Integer page, Integer rows, HttpServletRequest request) {
    	SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        SearchBuilder<DocTemplate, Long> searchBuilder = new SearchBuilder<DocTemplate, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.appendSort(Direction.ASC, "id");
        // searchBuilder.getFilterBuilder().equal("deptId", user.getDepartment().getDeptParent());
        // 查询条件改为祖先部门id
        searchBuilder.getFilterBuilder().equal("deptId", user.getDepartment().getUnitDept().getId());
        SearchListHelper<DocTemplate> listhelper = new SearchListHelper<DocTemplate>();
        listhelper.execute(searchBuilder, docTemplateService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocTemplateShowBean.class));
        p.setTotal(listhelper.getCount());

        return p;
    }
     
}
