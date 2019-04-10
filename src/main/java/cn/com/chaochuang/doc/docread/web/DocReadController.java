/*
 * FileName:    ReceiveFileController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.docread.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.upload.support.PluploadController;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.doc.docread.bean.DocReadEditBean;
import cn.com.chaochuang.doc.docread.bean.DocReadShowBean;
import cn.com.chaochuang.doc.docread.domain.DocRead;
import cn.com.chaochuang.doc.docread.reference.DocReadStatus;
import cn.com.chaochuang.doc.docread.service.DocReadService;
import cn.com.chaochuang.common.attach.service.SysAttachService;

/**
 * @author HeYunTao
 *
 */
@Controller
@RequestMapping("doc/docread")
public class DocReadController extends PluploadController {
    /** 附件所属附件对象名 */
    private final String         _OWNER_TYPE = "DocRead";

    @Autowired
    private DocReadService       docReadService;

    @Autowired
    private LogService           logService;

    @Autowired
    protected ConversionService  conversionService;

    @Autowired
    private SysDepartmentService deptService;

    @Autowired
    private SysAttachService     attachService;

    @RequestMapping("/new")
    public ModelAndView newPage() {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        ModelAndView mav = new ModelAndView("/doc/docread/edit");
        return mav;
    }

    @RequestMapping("/edit")
    public ModelAndView editPage(Long id) {
        ModelAndView mav = new ModelAndView("/doc/docread/edit");
        DocRead docread = this.docReadService.findOne(id);
        mav.addObject("docread", docread);
        mav.addObject("readManNames", this.docReadService.getReadManNames(id));
        mav.addObject("attachMap", this.attachService.getAttachMap(String.valueOf(docread.getId()), _OWNER_TYPE));
        return mav;
    }

    @RequestMapping("/createdlist")
    public ModelAndView createdlist() {
        ModelAndView mav = new ModelAndView("/doc/docread/created/list");
        return mav;
    }

    @RequestMapping("/listCreated.json")
    @ResponseBody
    public Page createdlistjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<DocRead, Long> searchBuilder = new SearchBuilder<DocRead, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("status", DocReadStatus.暂存);
        searchBuilder.getFilterBuilder().equal("sendMan", UserTool.getInstance().getCurrentUserId());
        searchBuilder.appendSort(Direction.DESC, "sendTime");
        SearchListHelper<DocRead> listhelper = new SearchListHelper<DocRead>();
        listhelper.execute(searchBuilder, docReadService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocReadShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/publishlist")
    public ModelAndView publishlist() {
        ModelAndView mav = new ModelAndView("/doc/docread/publish/list");
        return mav;
    }

    @RequestMapping("/listPublish.json")
    @ResponseBody
    public Page publishlistjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<DocRead, Long> searchBuilder = new SearchBuilder<DocRead, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("status", DocReadStatus.发送);
        searchBuilder.getFilterBuilder().equal("sendMan", UserTool.getInstance().getCurrentUserId());
        searchBuilder.appendSort(Direction.DESC, "sendTime");
        SearchListHelper<DocRead> listhelper = new SearchListHelper<DocRead>();
        listhelper.execute(searchBuilder, docReadService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocReadShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    // 暂存
    @RequestMapping("/saveTemp.json")
    @ResponseBody
    public ReturnInfo saveTemp(DocReadEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (bean != null) {
                bean.setStatus(DocReadStatus.暂存);
            }
            Long docReadId = this.docReadService.saveDocread(bean);
            logService.saveLog(SjType.普通操作, "暂存公文传阅,公文传阅标题" + bean.getDocTitle() + "公文传阅id为：" + docReadId,LogStatus.成功, request);
            return new ReturnInfo(docReadId.toString(), null, "暂存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    // 发送
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(DocReadEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (bean != null) {
                bean.setStatus(DocReadStatus.发送);
            }
            Long docReadId = this.docReadService.saveDocread(bean);
            logService.saveLog(SjType.普通操作, "发送公文传阅,公文传阅标题" + bean.getDocTitle() + "公文传阅id为：" + docReadId,LogStatus.成功, request);
            return new ReturnInfo(docReadId.toString(), null, "发送成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    // 删除
    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo del(String ids) {
        try {
            if (ids != null) {
                String[] idArr = ids.split(",");
                for (String docReadId : idArr) {
                    this.docReadService.delete(Long.valueOf(docReadId));
                    this.attachService.deleteOwnerAttach(docReadId, _OWNER_TYPE);
                }
            }
            return new ReturnInfo("1", null, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/detail")
    public ModelAndView detail(Long id, String type, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/doc/docread/detail");
        DocRead docread = null;
        if (id != null) {
            docread = this.docReadService.findOne(id);
            mav.addObject("readManNames", this.docReadService.getReadManNames(id));
        }
        mav.addObject("docread", docread);
        mav.addObject("attachMap", this.attachService.getAttachMap(String.valueOf(docread.getId()), _OWNER_TYPE));
        return mav;
    }

    // 导出EXCEL
    @RequestMapping("/exportCreated.json")
    public ModelAndView createdExport(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/doc/docread/excel/createdListExport");
        mav.addObject("page", this.createdlistjson(1, 1000000000, request));
        mav.addObject("fileName", "已建公文传阅列表");
        return mav;
    }

    // 导出EXCEL
    @RequestMapping("/exportPublish.json")
    public ModelAndView publishExport(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/doc/docread/excel/publishListExport");
        mav.addObject("page", this.publishlistjson(1, 1000000000, request));
        mav.addObject("fileName", "已发公文传阅列表");
        return mav;
    }
}
