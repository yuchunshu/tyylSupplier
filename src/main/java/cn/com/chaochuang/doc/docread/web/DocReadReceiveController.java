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
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.reference.SignStatus;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.upload.support.PluploadController;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.doc.docread.bean.DocreadRecShowBean;
import cn.com.chaochuang.doc.docread.bean.ReceivesReadEditBean;
import cn.com.chaochuang.doc.docread.domain.DocRead;
import cn.com.chaochuang.doc.docread.domain.DocReadReceives;
import cn.com.chaochuang.doc.docread.repository.DocReadReceivesRepository;
import cn.com.chaochuang.doc.docread.service.DocReadService;
import cn.com.chaochuang.common.attach.service.SysAttachService;

/**
 * @author HeYunTao
 *
 */
@Controller
@RequestMapping("doc/docread/receive")
public class DocReadReceiveController extends PluploadController {
    /** 附件所属附件对象名 */
    private final String              _OWNER_TYPE = "DocRead";

    @Autowired
    protected DocReadService          docReadService;

    @Autowired
    protected ConversionService       conversionService;

    @Autowired
    private SysAttachService          attachService;

    @Autowired
    private DocReadReceivesRepository receivesRepository;

    @Autowired
    private LogService                logService;

    @RequestMapping("/detailRead")
    public ModelAndView editPage(Long id, Long docreadId, HttpServletRequest request) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        ModelAndView mav = new ModelAndView("/doc/docread/receive/detail");
        this.docReadService.readDocRead(id);
        if (docreadId != null) {
            DocRead docread = this.docReadService.findOne(docreadId);
            mav.addObject("readManNames", this.docReadService.getReadManNames(docreadId));
            mav.addObject("docread", docread);
            mav.addObject("attachMap", this.attachService.getAttachMap(String.valueOf(docread.getId()), _OWNER_TYPE));
        }
        mav.addObject("doreadReceiveId", id);
        return mav;
    }

    // 回复
    @RequestMapping("/saveRead.json")
    @ResponseBody
    public ReturnInfo saveTemp(ReceivesReadEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (bean != null) {
                bean.setStatus(SignStatus.已回复);
            }
            Long id = docReadService.saveDocreadReceives(bean);
            logService.saveDefaultLog("签收公文传阅：公文传阅流水号为：" + bean.getDocReadId(), request);
            return new ReturnInfo(id.toString(), null, "签收成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/doc/docread/receive/list");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<DocReadReceives, Long> searchBuilder = new SearchBuilder<DocReadReceives, Long>(
                        conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("receiverId", UserTool.getInstance().getCurrentUserId());
        searchBuilder.appendSort(Direction.DESC, "docread.sendTime");
        SearchListHelper<DocReadReceives> listhelper = new SearchListHelper<DocReadReceives>();
        listhelper.execute(searchBuilder, this.receivesRepository, page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocreadRecShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    // 导出EXCEL
    @RequestMapping("/export.json")
    public ModelAndView publishlistjson(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/doc/docread/excel/receiveListExport");
        mav.addObject("page", this.listjson(1, 1000000000, request));
        mav.addObject("fileName", "接收公文传阅列表");
        return mav;
    }
}
