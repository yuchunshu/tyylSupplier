/*
 * FileName:    MailController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.mail.web;

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
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.oa.mail.bean.InboxShowBean;
import cn.com.chaochuang.oa.mail.domain.EmIncepter;
import cn.com.chaochuang.oa.mail.reference.IncepterStatus;
import cn.com.chaochuang.oa.mail.reference.MailStatus;
import cn.com.chaochuang.oa.mail.service.EmAttachService;
import cn.com.chaochuang.oa.mail.service.EmDustbinService;
import cn.com.chaochuang.oa.mail.service.EmIncepterService;
import cn.com.chaochuang.oa.mail.service.EmMainService;
import cn.com.chaochuang.oa.mail.service.EmTempStorageService;

/**
 * @author HM
 *
 */
@Controller
@RequestMapping("oa/mail/inbox")
public class MailInboxController {

    @Autowired
    private EmIncepterService    incepterService;

    @Autowired
    private EmMainService        emMainService;

    @Autowired
    private EmAttachService      attachService;

    @Autowired
    private EmTempStorageService tempStorageService;

    @Autowired
    private EmDustbinService     dustbinService;

    @Autowired
    protected ConversionService  conversionService;

    @Autowired
    private LogService           logService;

    // 收件箱
    @RequestMapping("/list")
    public ModelAndView inboxlist() {
        ModelAndView mav = new ModelAndView("/oa/mail/inbox/list");
        mav.addObject("type", "inbox");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page inboxlistjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<EmIncepter, Long> searchBuilder = new SearchBuilder<EmIncepter, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        searchBuilder.getFilterBuilder().equal("incepter", user);
        searchBuilder.getFilterBuilder().in("status", new Object[] { IncepterStatus.已收件, IncepterStatus.未处理 });
        searchBuilder.getFilterBuilder().notEqual("mail.status", MailStatus.垃圾);
        searchBuilder.appendSort(Direction.DESC, "mail.sendDate");
        SearchListHelper<EmIncepter> listhelper = new SearchListHelper<EmIncepter>();
        listhelper.execute(searchBuilder, incepterService.getRepository(), page, rows);
        Page p = new Page();

        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), InboxShowBean.class));
        p.setTotal(listhelper.getCount());

        return p;
    }

    // 回复
    @RequestMapping("/dealReplay")
    public ModelAndView replay(String emailId) {
        ModelAndView mav = new ModelAndView("/oa/mail/replay");
        mav.addObject("obj", this.emMainService.mailReplay(emailId));
        return mav;
    }

    // 存档
    @RequestMapping("/dealArchive.json")
    @ResponseBody
    public ReturnInfo archiveMail(String incIds, HttpServletRequest request, HttpServletResponse response) {
        try {
            this.tempStorageService.archiveMails(incIds);
            return new ReturnInfo("1", null, "存档成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    // 移动到垃圾箱
    @RequestMapping("/dealTrash.json")
    @ResponseBody
    public ReturnInfo moveToDustbin(String mailIds, HttpServletRequest request, HttpServletResponse response) {
        try {
            String incepterId = UserTool.getInstance().getCurrentUserId();
            if (dustbinService.inboxTrash(mailIds, Long.valueOf(incepterId))) {
                return new ReturnInfo("1", null, "保存成功!");
            } else {
                return new ReturnInfo(null, "服务器连接不上！", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    // 阅读
    @RequestMapping("/dealRead.json")
    @ResponseBody
    public boolean readMail(String emailId, HttpServletRequest request, HttpServletResponse response) {
        try {
            this.emMainService.readMail(emailId, Long.valueOf(UserTool.getInstance().getCurrentUserId()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 查看邮件
    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detail(String incId) {
        ModelAndView mav = new ModelAndView("/oa/mail/inbox/detail");
        if (incId != null) {
            EmIncepter incepter = null;
            incepter = this.incepterService.findOne(incId);
            if (incepter != null) {
                mav.addObject("obj", incepter);
                mav.addObject("attachMap", this.attachService.getAttachMap(incepter.getEmailId()));
                this.emMainService.readMail(incepter.getEmailId(),
                                Long.valueOf(UserTool.getInstance().getCurrentUserId()));
            }
        }
        return mav;
    }
}
