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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.oa.mail.domain.EmDustbin;
import cn.com.chaochuang.oa.mail.service.EmAttachService;
import cn.com.chaochuang.oa.mail.service.EmDustbinService;
import cn.com.chaochuang.oa.mail.service.EmMainService;

/**
 * @author HM
 *
 */
@Controller
@RequestMapping("oa/mail/dustbin")
public class MailDustbinController {

    @Autowired
    private EmMainService       emMainService;

    @Autowired
    private EmDustbinService    dustbinService;

    @Autowired
    private EmAttachService     attachService;

    @Autowired
    protected ConversionService conversionService;

    // 垃圾箱
    @RequestMapping("/list")
    public ModelAndView dustbinlist() {
        ModelAndView mav = new ModelAndView("/oa/mail/dustbin/list");
        return mav;
    }

    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detail(String delId) {
        ModelAndView mav = new ModelAndView("/oa/mail/dustbin/detail");
        if (StringUtils.isNotBlank(delId)) {
            EmDustbin dust = null;
            dust = this.dustbinService.findOne(delId);
            if (dust != null) {
                mav.addObject("obj", dust);
                mav.addObject("attachMap", this.attachService.getAttachMap(dust.getEmailId()));
            }
        }
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page draftslistjson(Integer page, Integer rows, HttpServletRequest request) {
        Page p = new Page();
        p.setRows(this.dustbinService.seleceEmDustbin(page, rows));
        p.setTotal(this.dustbinService.coutEmDustbin());
        return p;
    }

    // 邮件恢复
    @RequestMapping("/dealRecover.json")
    @ResponseBody
    public ReturnInfo recover(String delIds, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(delIds)) {
                String curUserId = UserTool.getInstance().getCurrentUserId();
                if (this.dustbinService.recoverTrash(delIds, Long.valueOf(curUserId))) {
                    return new ReturnInfo("1", null, "邮件恢复成功!");
                } else {
                    return new ReturnInfo(null, "服务器连接不上！", null);
                }
            }
            return new ReturnInfo(null, "服务器连接不上！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    // 邮件删除
    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo delete(String delIds, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(delIds)) {
                String curUserId = UserTool.getInstance().getCurrentUserId();
                if (this.dustbinService.deleteTrash(delIds, Long.valueOf(curUserId))) {
                    return new ReturnInfo("1", null, "邮件删除成功!");
                } else {
                    return new ReturnInfo(null, "服务器连接不上！", null);
                }
            }
            return new ReturnInfo(null, "服务器连接不上！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
}
