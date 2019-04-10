package cn.com.chaochuang.doc.event.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.doc.docsigned.bean.DocSignedShowBean;
import cn.com.chaochuang.doc.docsigned.domain.DocSigned;
import cn.com.chaochuang.doc.docsigned.service.DocSignedService;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * @author hzr 2016-10-12
 * 收文
 */
@Controller
@RequestMapping("doc/receivefile")
public class ReceiveFileController extends DocFileCommonController {

    @Autowired
    private DocSignedService    docSignedService;

    @RequestMapping("/new")
    public ModelAndView newPage(Long id, String type, HttpServletRequest request) {
        return super.newFilePage(WfBusinessType.收文);
    }

    @RequestMapping("/edit")
    public ModelAndView editPage(String id,String parentId) {
        return super.editFilePage(id, WfBusinessType.收文,parentId);
    }

    /** 任务办理页面 */
    @RequestMapping("task/deal")
    public ModelAndView dealPage(String returnPageType,String opflag, String id, String fileId, HttpServletRequest request) {
        return super.openDealPage(returnPageType,opflag, id, fileId, WfBusinessType.收文);
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        return super.listPage(WfBusinessType.收文);
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
        return super.listPageJson(page, rows, request);
    }


    @RequestMapping("/signed")
    public ModelAndView signedList() {
        ModelAndView mav = new ModelAndView("/doc/receivefile/signedlist");
        return mav;
    }

    @RequestMapping("/listSigned.json")
    @ResponseBody
    // 对页面Pag的操作
    public Page signedlistjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<DocSigned, Long> searchBuilder = new SearchBuilder<DocSigned, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        // 按部门查询
        // String departmentId = request.getParameter("department");
        // if (StringUtils.isNotBlank(departmentId)) {
        // searchBuilder.getFilterBuilder().equal("publishDept", deptService.findOne(Long.valueOf(departmentId)));
        // }
        // searchBuilder.getFilterBuilder().notEqual("status", StatusFlag.已删除);
        // searchBuilder.getFilterBuilder().equal("creatorId", UserTool.getInstance().getCurrentUserId());
        // searchBuilder.appendSort(Direction.DESC, "displayType");
        // searchBuilder.appendSort(Direction.ASC, "id");
        SearchListHelper<DocSigned> listhelper = new SearchListHelper<DocSigned>();
        listhelper.execute(searchBuilder, docSignedService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocSignedShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

}
