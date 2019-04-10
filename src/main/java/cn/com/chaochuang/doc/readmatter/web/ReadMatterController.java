/*
 * FileName:    ReceiveFileController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.readmatter.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.reference.ReadStatus;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.upload.support.PluploadController;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.service.OaDocFileService;
import cn.com.chaochuang.doc.letter.service.DocDepLetterService;
import cn.com.chaochuang.doc.readmatter.bean.ReadMatterOppBean;
import cn.com.chaochuang.doc.readmatter.bean.ReadMatterShowBean;
import cn.com.chaochuang.doc.readmatter.domain.ReadMatter;
import cn.com.chaochuang.doc.readmatter.service.ReadMatterService;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;

/**
 * @author huangwq
 *
 */
@Controller
@RequestMapping("doc/readMatter")
public class ReadMatterController extends PluploadController {
    @Autowired
    private ReadMatterService   readMatterService;
    @Autowired
    protected ConversionService conversionService;

    @Autowired
    private OaDocFileService    docFileService;

    @Autowired
    private SysAttachService    attachService;
    @Autowired
    private DocDepLetterService depLetterService;

    @RequestMapping("/list/{type}")
    public ModelAndView list(@PathVariable("type") String type) {
        ModelAndView mav = new ModelAndView("/doc/readmatter/list");
        // SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        // mav.addObject("creatorId", user.getId());
        mav.addObject("type", type);
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        SearchBuilder<ReadMatter, Long> searchBuilder = new SearchBuilder<ReadMatter, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);

        searchBuilder.getFilterBuilder().equal("readMan", user.getId());
        searchBuilder.appendSort(Direction.ASC, "status");

        String type = request.getParameter("type");
        if ("todo".equals(type)) {
            searchBuilder.getFilterBuilder().equal("status", ReadStatus.未阅);
        } else if ("done".equals(type)) {
            searchBuilder.getFilterBuilder().equal("status", ReadStatus.已阅);
        }

        SearchListHelper<ReadMatter> listhelper = new SearchListHelper<ReadMatter>();
        listhelper.execute(searchBuilder, readMatterService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), ReadMatterShowBean.class));
        p.setTotal(listhelper.getCount());

        return p;
    }

    @RequestMapping("/listFileRead")
    public ModelAndView filereadlist(String id, String type) {
        ModelAndView mav = new ModelAndView("/doc/fileparse/readmatter_list");
        List<ReadMatter> rmlist = readMatterService.findByFileId(id);
        mav.addObject("rmlist", rmlist);
        return mav;
    }

    /** 阅知办理页面 */
    @RequestMapping("read/detail")
    public ModelAndView dealPage(@RequestParam("fileType") String fileType, @RequestParam("id") String id,
                    @RequestParam("readId") String readId) {
    	SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        ModelAndView mav = null;

        // ModelAndView mav = new ModelAndView("/doc/sendfile/form_" + task.getTaskDefinitionKey());
        List<SysAttach> attcllist = null;
        if (WfBusinessType.收文.getKey().equals(fileType)) {
            mav = new ModelAndView("/doc/receivefile/form_noderead");
        } else {
            mav = new ModelAndView("/doc/sendfile/form_noderead");
        }
        OaDocFile docFile = this.docFileService.findOne(id);
        attcllist = attachService.findByOwnerIdAndOwnerType(id, OaDocFile.DOC_ATTACH_OWNER_MAIN);
        mav.addObject("obj", docFile);
        // 读取相关部门函件数量，以显示会办信息
        mav.addObject("depLettersCount", depLetterService.countLettersByFileId(id));

        mav.addObject("attachMap", this.attachService.getAttachMap(id, "OaReceiveFile"));

        String docname = null;
        String docpath = null;
        if (attcllist != null) {
            for (SysAttach att : attcllist) {
                docname = att.getSaveName();
                docpath = att.getSavePath();
            }

        }

        // 自动阅知
        ReadMatter rm = this.readMatterService.findOne(readId);
        if (rm != null && !ReadStatus.已阅.equals(rm.getStatus())) {
        	rm.setReadMan(user.getId());
            rm.setReadTime(new Date());
            rm.setStatus(ReadStatus.已阅);
            this.readMatterService.persist(rm);
        }

        mav.addObject("readCount", this.readMatterService.selectReadMatterCount(id));
        //mav.addObject("comId", readId);
        //mav.addObject("readstatus", rm.getStatus().getKey());
        mav.addObject("docpath", docpath);
        mav.addObject("docname", docname);
        //mav.addObject("oppr", rm.getOpinion());

        return mav;
    }

    @RequestMapping("/saveReadInfo")
    @ResponseBody
    public ReturnInfo saveReadInfo(ReadMatterOppBean bean) {
        try {
            String id = readMatterService.saveReadMatter(bean);
            return new ReturnInfo(id.toString(), null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
}
