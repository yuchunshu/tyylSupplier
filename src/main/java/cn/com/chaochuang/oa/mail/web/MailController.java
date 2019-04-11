/*
 * FileName:    MailController.java
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.oa.mail.bean.MailEditBean;
import cn.com.chaochuang.oa.mail.domain.EmMain;
import cn.com.chaochuang.oa.mail.reference.MailStatus;
import cn.com.chaochuang.oa.mail.service.EmAttachService;
import cn.com.chaochuang.oa.mail.service.EmMainService;

/**
 * @author HM
 *
 */
@Controller
@RequestMapping("oa/mail")
public class MailController {

    @Autowired
    private EmMainService       emMainService;

    @Autowired
    private EmAttachService     attachService;

    @Autowired
    protected ConversionService conversionService;
    @Autowired
    private LogService          logService;

    // 写邮件
    @RequestMapping("/edit")
    public ModelAndView edit() {
        ModelAndView mav = new ModelAndView("/oa/mail/edit");
        return mav;
    }

    // 存草稿
    @RequestMapping("/saveDrafts.json")
    @ResponseBody
    public ReturnInfo saveDrafts(MailEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (bean != null) {
                bean.setStatus(MailStatus.草稿);
            }
            String mailId = this.emMainService.saveMail(bean);
            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            logService.saveLog(SjType.普通操作, "保存到草稿箱",LogStatus.成功, request);
            return new ReturnInfo(mailId.toString(), null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    // 发送
    @RequestMapping("/send.json")
    @ResponseBody
    public ReturnInfo send(MailEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (bean != null) {
                bean.setStatus(MailStatus.已发送);
            }
            String mailId = this.emMainService.saveMail(bean);
            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            logService.saveLog(SjType.普通操作, "发送邮件信息",LogStatus.成功, request);
            return new ReturnInfo(mailId.toString(), null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    // 转发
    @RequestMapping("/dealForwarding")
    public ModelAndView forwarding(String emailId) {
        ModelAndView mav = new ModelAndView("/oa/mail/forwarding");
        mav.addObject("obj", this.emMainService.forwardingMail(emailId));
        mav.addObject("attachMap", this.attachService.copyAttachMap(emailId));
        mav.addObject("oldMailId", emailId);
        return mav;
    }

    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detail(String id, String type) {
        ModelAndView mav = new ModelAndView("/oa/mail/detail");
        if (id != null) {
            EmMain mail = null;
            mail = this.emMainService.findOne(id);
            if (mail != null) {
                mav.addObject("obj", mail);
                mav.addObject("attachMap", this.attachService.getAttachMap(mail.getId()));
            }
        }
        mav.addObject("type", type);
        return mav;
    }

}
