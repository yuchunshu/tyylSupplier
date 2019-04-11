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
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.oa.mail.bean.ArchiveShowBean;
import cn.com.chaochuang.oa.mail.domain.EmTempStorage;
import cn.com.chaochuang.oa.mail.service.EmAttachService;
import cn.com.chaochuang.oa.mail.service.EmDustbinService;
import cn.com.chaochuang.oa.mail.service.EmMainService;
import cn.com.chaochuang.oa.mail.service.EmTempStorageService;

/**
 * @author HM
 *
 */
@Controller
@RequestMapping("oa/mail/archive")
public class MailArchiveController {

    @Autowired
    private EmMainService        emMainService;

    @Autowired
    private EmDustbinService     dustbinService;

    @Autowired
    private EmTempStorageService tempStorageService;

    @Autowired
    private EmAttachService      attachService;

    @Autowired
    private SysUserService       userService;

    @Autowired
    protected ConversionService  conversionService;

    @Autowired
    private LogService           logService;

    // 存档箱
    @RequestMapping("/list")
    public ModelAndView archivelist() {
        ModelAndView mav = new ModelAndView("/oa/mail/archive/list");
        mav.addObject("type", "archive");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Page draftslistjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<EmTempStorage, String> searchBuilder = new SearchBuilder<EmTempStorage, String>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        searchBuilder.getFilterBuilder().equal("incepter", user);
        String senderId = request.getParameter("senderId");
        if (StringUtils.isNotBlank(senderId)) {
            searchBuilder.getFilterBuilder().equal("sender", this.userService.findOne(Long.valueOf(senderId)));
        }

        searchBuilder.appendSort(Direction.DESC, "pigeDate");
        SearchListHelper<EmTempStorage> listhelper = new SearchListHelper<EmTempStorage>();
        listhelper.execute(searchBuilder, tempStorageService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), ArchiveShowBean.class));
        p.setTotal(listhelper.getCount());

        return p;
    }

    // 移动到垃圾箱
    @RequestMapping("/dealTrash.json")
    @ResponseBody
    public ReturnInfo moveToDustbin(String tmpIds, HttpServletRequest request, HttpServletResponse response) {
        try {
            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            if (dustbinService.archiveTrash(tmpIds)) {
                logService.saveLog(SjType.普通操作, "移动到垃圾箱，邮件id为'" + tmpIds + "'的记录",LogStatus.成功, request);
                return new ReturnInfo("1", null, "保存成功!");
            } else {
                return new ReturnInfo(null, "服务器连接不上！", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    // 批量删除
    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo delArchive(String tmpIds, HttpServletRequest request, HttpServletResponse response) {
        try {
            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            if (this.tempStorageService.deleteArchive(tmpIds)) {
                logService.saveLog(SjType.普通操作, "删除邮件，删除id为'" + tmpIds + "'的记录",LogStatus.成功, request);
                return new ReturnInfo("1", null, "删除成功!");

            } else {
                return new ReturnInfo(null, "服务器连接不上！", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    // 查看邮件
    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detail(String tmpId) {
        ModelAndView mav = new ModelAndView("/oa/mail/archive/detail");
        if (tmpId != null) {
            EmTempStorage ts = null;
            ts = this.tempStorageService.findOne(tmpId);
            if (ts != null) {
                mav.addObject("obj", ts);
                mav.addObject("attachMap", this.attachService.getAttachMap(ts.getEmailId()));
            }
        }
        return mav;
    }
}
