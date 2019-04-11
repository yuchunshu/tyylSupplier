/*
 * FileName:    MailDraftsController.java
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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.oa.mail.bean.MailEditBean;
import cn.com.chaochuang.oa.mail.bean.MailShowBean;
import cn.com.chaochuang.oa.mail.domain.EmMain;
import cn.com.chaochuang.oa.mail.reference.MailStatus;
import cn.com.chaochuang.oa.mail.service.EmAttachService;
import cn.com.chaochuang.oa.mail.service.EmDustbinService;
import cn.com.chaochuang.oa.mail.service.EmMainService;

/**
 * @author HM
 *
 */
@Controller
@RequestMapping("oa/mail/drafts")
public class MailDraftsController {

    @Autowired
    private EmMainService       emMainService;

    @Autowired
    private EmDustbinService    dustbinService;

    @Autowired
    private EmAttachService     attachService;

    @Autowired
    protected ConversionService conversionService;

    // 移动到垃圾箱
    @RequestMapping("/dealTrash.json")
    @ResponseBody
    public ReturnInfo moveToDustbin(String mailIds, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (dustbinService.draftsTrash(mailIds)) {
                return new ReturnInfo("1", null, "保存成功!");
            } else {
                return new ReturnInfo(null, "服务器连接不上！", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
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
            return new ReturnInfo(mailId.toString(), null, "保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    // 查看邮件
    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detail(String id) {
        ModelAndView mav = new ModelAndView("/oa/mail/drafts/edit");
        if (StringUtils.isNotBlank(id)) {
            EmMain mail = null;
            mail = this.emMainService.findOne(id);
            if (mail != null) {
                mav.addObject("obj", mail);
                mav.addObject("attachMap", this.attachService.getAttachMap(mail.getId()));
            }
        }
        return mav;
    }

    // 草稿箱
    @RequestMapping("/list")
    public ModelAndView draftslist() {
        ModelAndView mav = new ModelAndView("/oa/mail/drafts/list");
        mav.addObject("type", "drafts");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page draftslistjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<EmMain, String> searchBuilder = new SearchBuilder<EmMain, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        searchBuilder.getFilterBuilder().equal("sender", user);
        searchBuilder.getFilterBuilder().equal("status", MailStatus.草稿);

        searchBuilder.appendSort(Direction.DESC, "saveDate");
        SearchListHelper<EmMain> listhelper = new SearchListHelper<EmMain>();
        listhelper.execute(searchBuilder, emMainService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), MailShowBean.class));
        p.setTotal(listhelper.getCount());

        return p;
    }

}
