/*
 * FileName:    DocDepLetterController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年11月25日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.doc.letter.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.AttachUtils;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.service.OaDocFileService;
import cn.com.chaochuang.doc.letter.bean.DepLetterInfo;
import cn.com.chaochuang.doc.letter.bean.DepLetterReceiverShowBean;
import cn.com.chaochuang.doc.letter.bean.DepLetterShowBean;
import cn.com.chaochuang.doc.letter.domain.DocDepLetter;
import cn.com.chaochuang.doc.letter.domain.DocDepLetterReceiver;
import cn.com.chaochuang.doc.letter.reference.LetterReceiverStatus;
import cn.com.chaochuang.doc.letter.service.DocDepLetterReceiverService;
import cn.com.chaochuang.doc.letter.service.DocDepLetterService;
import cn.com.chaochuang.workflow.event.service.WorkflowService;
import cn.com.chaochuang.workflow.reference.WfBusinessType;
import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;

/**
 * @author LJX
 *
 */
@Controller
@RequestMapping("doc/letter")
public class DocDepLetterController {

    @Value(value = "${upload.rootpath}")
    private String                      rootPath;

    @Value(value = "${upload.appid.docFileAttach}")
    private String                      docAppid;
    @Autowired
    private DocDepLetterService         service;

    @Autowired
    private DocDepLetterReceiverService receiverService;

    @Autowired
    private SysAttachService            attachService;

    @Autowired
    protected ConversionService         conversionService;

    @Autowired
    private OaDocFileService            fileService;


    @Autowired
    private WorkflowService             workflowService;

    @RequestMapping("/edit")
    public ModelAndView edit() {
        ModelAndView mav = new ModelAndView("/doc/letter/edit");
        // if (StringUtils.isNotBlank(id)) {
        // mav.addObject("obj", service.findOne(id));
        // mav.addObject("attachMap", this.attachService.getAttachMap(id, DocDepLetter.class.getSimpleName()));
        // }
        return mav;
    }

    @RequestMapping("/detail")
    public ModelAndView detail(String id, Long fordoId) {
        ModelAndView mav = new ModelAndView("/doc/letter/detail");
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();

        if (StringUtils.isBlank(id)) {
            mav = new ModelAndView("/doc/docevent/message");
            mav.addObject("hints", "无法获取部门函件信息，请刷新或重新进入列表页面");
            return mav;
        }

        DocDepLetter letter = service.findOne(id);
        if (letter == null) {
            mav = new ModelAndView("/doc/docevent/message");
            mav.addObject("hints", "部门函件信息读取失败，该函件可能已被删除，请刷新或重新进入列表页面");
            return mav;
        }

        mav.addObject("letter", letter);
        mav.addObject("attachs", this.attachService.findByOwnerIdAndOwnerType(id, DocDepLetter.class.getSimpleName()));

        List<DocDepLetterReceiver> receivers = service.selectReceiversByLetterId(id);

        if (receivers != null && receivers.size() > 0) {
            SysDepartment userDept = (SysDepartment) UserTool.getInstance().getCurrentUserDepartment();
            Map<String, List<SysAttach>> receiverAttachs = new HashMap<String, List<SysAttach>>();
            for (DocDepLetterReceiver r : receivers) {// 接收部门反馈附件
                if (userDept != null && userDept.getUnitId().equals(r.getReceiverAncestorDep())
                                && LetterReceiverStatus.未签收.equals(r.getStatus())) {
                    // 祖先部门相同，则签收
                    DepLetterInfo info = new DepLetterInfo();
                    info.setReceiveId(r.getId());
                    info.setReceiverStatus(LetterReceiverStatus.已签收);
                    this.service.changeReceiveStatus(info);
                }
                // 读取附件
                receiverAttachs.put(r.getId(), attachService.findByOwnerIdAndOwnerType(r.getId(),
                                DocDepLetterReceiver.class.getSimpleName()));

            }
            if (StringUtils.isNotBlank(letter.getFileId())) {
                OaDocFile file = this.fileService.findOne(letter.getFileId());
                if (file != null) {
                    // 读取审批意见
                    mav.addObject("opinionList", workflowService.selectAuditOpinionsByEntityIdForLetter(file.getId(), file.getFileType(), null));
                    mav.addObject("fileAttach", this.attachService.findByOwnerIdAndOwnerType(file.getId(),
                                    OaDocFile.DOC_ATTACH_OWNER_DOC));
                    if (StringUtils.isNotBlank(file.getDocId())) {
                        mav.addObject("fileMainAttach", this.attachService.findOne(file.getDocId()));
                    }
                }
            }
            mav.addObject("receivers", receivers);
            mav.addObject("receiverAttachs", receiverAttachs);
        }

        return mav;
    }

    /**
     * 发送
     *
     * @param info
     * @return
     */
    @RequestMapping("/send.json")
    @ResponseBody
    public ReturnInfo send(DepLetterInfo info) {
        try {
            return new ReturnInfo(this.service.saveDepLetter(info), null, "发送成功。");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "发送失败！", null);
        }
    }

    /**
     * 反馈
     *
     * @param info
     * @return
     */
    @RequestMapping("/saveReversion.json")
    @ResponseBody
    public ReturnInfo reversion(DepLetterInfo info) {
        try {
            return new ReturnInfo(this.service.reversion(info), null, "保存成功。");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "保存失败！", null);
        }
    }

    /**
     * 补充
     *
     * @param info
     * @return
     */
    @RequestMapping("/saveReplenish.json")
    @ResponseBody
    public ReturnInfo replenish(DepLetterInfo info) {
        try {
            info.setReceiverStatus(LetterReceiverStatus.需补充);
            return new ReturnInfo(this.service.changeReceiveStatus(info), null, "成功。");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "失败！", null);
        }
    }

    /**
     * 列表
     *
     * @param type
     * @return
     */
    @RequestMapping("/list/{type}")
    // send/receive
    public ModelAndView list(@PathVariable("type") String type) {
        ModelAndView mav = new ModelAndView("/doc/letter/list");
        mav.addObject("type", type);
        return mav;
    }

    @RequestMapping("/listSendLtter.json")
    @ResponseBody
    public Page receiveList(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<DocDepLetter, String> searchBuilder = new SearchBuilder<DocDepLetter, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.appendSort(Direction.DESC, "createDate");
        searchBuilder.getFilterBuilder().equal("creatorId", UserTool.getInstance().getCurrentUserId());
        SearchListHelper<DocDepLetter> listhelper = new SearchListHelper<DocDepLetter>();
        listhelper.execute(searchBuilder, service.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DepLetterShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/listReceiveLtter.json")
    @ResponseBody
    public Page sendList(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<DocDepLetterReceiver, String> searchBuilder = new SearchBuilder<DocDepLetterReceiver, String>(
                        conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.appendSort(Direction.DESC, "receiveDate");
        Long deptId = ((SysUser) UserTool.getInstance().getCurrentUser()).getDepartment().getId();
        searchBuilder.getFilterBuilder().equal("receiverDeptId", deptId);
        SearchListHelper<DocDepLetterReceiver> listhelper = new SearchListHelper<DocDepLetterReceiver>();
        listhelper.execute(searchBuilder, receiverService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), DepLetterReceiverShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public Boolean delete(String[] ids) {
        try {
            this.service.deleteLetter(ids);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 会办意见（部门函件）列表
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/listReceiver")
    @ResponseBody
    public ModelAndView receiverList(String id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/doc/letter/receiverlist");
        if (StringUtils.isNotBlank(id)) {
            List<DocDepLetterReceiver> receivers = service.selectReceiversByFileId(id);
            for (DocDepLetterReceiver r : receivers) {
                r.setAttachs(attachService.findByOwnerIdAndOwnerType(r.getId(),
                                DocDepLetterReceiver.class.getSimpleName()));
            }
            mav.addObject("list", receivers);
        }
        return mav;
    }

    /**
     * 请示政府页面
     */
    @RequestMapping("/ask")
    public ModelAndView askPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/doc/letter/askEdit");

        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        OaDocFile obj = new OaDocFile();

        String id = request.getParameter("id");
        String rid = request.getParameter("receiverId");
        DocDepLetter letter = null;
        if (StringUtils.isNotBlank(id)) {
            letter = service.findOne(id);
        } else if (StringUtils.isNotBlank(rid)) {
            DocDepLetterReceiver receiver = receiverService.findOne(rid);
            if (receiver != null) {
                letter = receiver.getLetter();
            }
        }
        if (letter == null) {
            mav = new ModelAndView("/doc/docevent/message");
            mav.addObject("hints", "部门函件信息读取失败！");
            return mav;
        }

        obj.setTitle(letter.getTitle());
        obj.setSendUnit(letter.getCreatorDep().getDeptName());
        obj.setFileType(WfBusinessType.收文);
        obj.setUrgencyLevel(letter.getUrgencyLevel());
        obj.setDocDepLetterId(id);

        mav.addObject("letter", letter);

        String docname = AttachUtils.getDocUniqueFileName("MainFile_", "doc");
        String docpath = this.rootPath + "/" + this.docAppid + "/" + Tools.DATE_FORMAT.format(new Date());

        mav.addObject("docpath", docpath);
        mav.addObject("docname", docname);
        mav.addObject("currUser", user);
        mav.addObject("obj", obj);
        return mav;
    }
}
