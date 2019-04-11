/*
 * FileName:    MailSendboxController.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
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
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.oa.mail.bean.MailShowBean;
import cn.com.chaochuang.oa.mail.domain.EmMain;
import cn.com.chaochuang.oa.mail.reference.MailStatus;
import cn.com.chaochuang.oa.mail.service.EmAttachService;
import cn.com.chaochuang.oa.mail.service.EmMainService;

/**
 * @author HM
 *
 */
@Controller
@RequestMapping("oa/mail/sendbox")
public class MailSendboxController {

    @Autowired
    private EmMainService       emMainService;

    @Autowired
    private EmAttachService     attachService;

    @Autowired
    protected ConversionService conversionService;

    @Autowired
    private LogService          logService;

    // 发件箱
    @RequestMapping("/list")
    public ModelAndView sendboxlist() {
        ModelAndView mav = new ModelAndView("/oa/mail/sendbox/list");
        mav.addObject("type", "sendbox");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page sendboxlistjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<EmMain, Long> searchBuilder = new SearchBuilder<EmMain, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        searchBuilder.getFilterBuilder().equal("sender", user);
        searchBuilder.getFilterBuilder().equal("status", MailStatus.已发送);

        searchBuilder.appendSort(Direction.DESC, "sendDate");
        SearchListHelper<EmMain> listhelper = new SearchListHelper<EmMain>();
        listhelper.execute(searchBuilder, emMainService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), MailShowBean.class));
        p.setTotal(listhelper.getCount());

        return p;
    }

    // 查看邮件
    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detail(String id) {
        ModelAndView mav = new ModelAndView("/oa/mail/sendbox/detail");
        if (id != null) {
            EmMain mail = null;
            mail = this.emMainService.findOne(id);
            if (mail != null) {
                mav.addObject("obj", mail);
                mav.addObject("attachMap", this.attachService.getAttachMap(mail.getId()));
            }
        }
        return mav;
    }

    // 撤回
    @RequestMapping("/getback.json")
    @ResponseBody
    public ReturnInfo getback(String emailId, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (this.emMainService.getBack(emailId)) {
                SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
                logService.saveLog(SjType.普通操作, "撤回发送邮件信息，撤回id=" + emailId + ", 登录名：" + user.getAccount(),LogStatus.成功, request);
                return new ReturnInfo("1", null, "邮件撤回成功!");
            } else {
                return new ReturnInfo(null, "收件人已对该邮件进行处理，无法撤回！", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    // 批量删除
    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo delMail(String emailIds, HttpServletRequest request, HttpServletResponse response) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        try {
            if (this.emMainService.delMail(emailIds)) {
                logService.saveLog(SjType.普通操作, "删除发送邮件信息，删除id为'" + emailIds + "'的记录",LogStatus.成功, request);
                return new ReturnInfo("1", null, "邮件删除成功");
            } else {
                return new ReturnInfo(null, "服务器连接不上！", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
}
