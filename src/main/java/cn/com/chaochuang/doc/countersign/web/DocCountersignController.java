package cn.com.chaochuang.doc.countersign.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.reference.SignStatus;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.doc.countersign.bean.DocCountersignEditBean;
import cn.com.chaochuang.doc.countersign.domain.DocCountersign;
import cn.com.chaochuang.doc.countersign.service.CountersignService;
import cn.com.chaochuang.doc.event.bean.FileShowBean;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.service.OaDocFileService;
import cn.com.chaochuang.doc.readmatter.bean.ReadMatterOppBean;
import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;

/**
 * @author hzr 2016年5月16日
 *
 */
@Controller
@RequestMapping("doc/countersign")
public class DocCountersignController {

    @Autowired
    private CountersignService  countersignService;

    @Autowired
    protected ConversionService conversionService;

    @Autowired
    private OaDocFileService    docFileService;

    @Autowired
    private SysAttachService    attachService;

    // @Autowired
    // private AuditOpinionService docOpinionService;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/doc/countersign/list");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    // 对页面Pag的操作
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<DocCountersign, Long> searchBuilder = new SearchBuilder<DocCountersign, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);

        // searchBuilder.getFilterBuilder().notEqual("status", StatusFlag.已删除);
        searchBuilder.getFilterBuilder().equal("cosignMan", UserTool.getInstance().getCurrentUserId());
        searchBuilder.appendSort(Direction.ASC, "status");
        searchBuilder.appendSort(Direction.DESC, "id");
        SearchListHelper<DocCountersign> listhelper = new SearchListHelper<DocCountersign>();
        listhelper.execute(searchBuilder, countersignService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DocCountersignEditBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/listFilecsing")
    public ModelAndView listFilecsing(String fileId) {
        List<DocCountersign> csignList = this.countersignService.selectDocCountersignByFileId(fileId);
        ModelAndView mav = new ModelAndView("/doc/fileparse/countersign_list");
        mav.addObject("csignList", csignList);
        return mav;
    }

    @RequestMapping("/save")
    @ResponseBody
    public ReturnInfo saveSign(FileShowBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {

            String userIds = request.getParameter("userIds");
            String type = request.getParameter("type");

            if (bean.getId() != null && userIds != null) {
                this.countersignService.saveDocCountersgin(userIds, type, bean);
            }

            return new ReturnInfo("", null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    /** 会签办理页面 */
    @RequestMapping("/dealCounterSign")
    public ModelAndView dealPage(@RequestParam("fileId") String fileId, @RequestParam("id") String id) {
        // 读取并选择路由，设置下一环节
        // 设置当前任务信息

        ModelAndView mav = new ModelAndView("/doc/sendfile/form_nodedeal");
        // 点击查看未阅时，自动签收
        DocCountersign docCountersign = this.countersignService.findOne(Long.valueOf(id));
        if (SignStatus.未签收.equals(docCountersign.getStatus())) {
            docCountersign.setStatus(SignStatus.已签收);
        }
        this.countersignService.getRepository().save(docCountersign);

        // ModelAndView mav = new ModelAndView("/doc/sendfile/form_" + task.getTaskDefinitionKey());
        List<SysAttach> attcllist = null;
        OaDocFile obj = this.docFileService.findOne(fileId);
        attcllist = attachService.findByOwnerIdAndOwnerType(fileId, OaDocFile.DOC_ATTACH_OWNER_MAIN);
        mav.addObject("attachMap", this.attachService.getAttachMap(fileId, OaDocFile.class.getSimpleName()));
        mav.addObject("obj", obj);

        String docname = null;
        String docpath = null;
        if (attcllist != null) {
            for (SysAttach att : attcllist) {
                docname = att.getSaveName();
                docpath = att.getSavePath();
            }

        }
        // mav.addObject("opinionList", this.docOpinionService.getOpinionList(fileId));
        mav.addObject("opflag", "4");
        mav.addObject("comId", id);
        mav.addObject("signtatus", docCountersign.getStatus());
        mav.addObject("docpath", docpath);
        mav.addObject("oppr", docCountersign.getOpinion());
        mav.addObject("docname", docname);
        return mav;
    }

    @RequestMapping("/saveSignInfo")
    @ResponseBody
    public ReturnInfo saveSignInfo(ReadMatterOppBean bean) {
        try {
            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            DocCountersign ds = this.countersignService.findOne(Long.valueOf(bean.getComId()));
            ds.setOpinion(bean.getOpinion());
            ds.setCosignTime(new Date());
            ds.setStatus(SignStatus.已回复);
            if (bean.getTaskId() != null)
                ds.setTaskId(bean.getTaskId().toString());
            this.countersignService.getRepository().save(ds);

            return new ReturnInfo("", null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
}
