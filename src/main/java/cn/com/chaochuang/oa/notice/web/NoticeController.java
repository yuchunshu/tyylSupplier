/*
 * FileName:    NoticeController.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.notice.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.reference.StatusFlag;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.oa.notice.bean.NoticeEditBean;
import cn.com.chaochuang.oa.notice.bean.NoticeShowBean;
import cn.com.chaochuang.oa.notice.domain.OaNotice;
import cn.com.chaochuang.oa.notice.service.OaNoticeService;

/**
 * @author HM
 *
 */
@Controller
@RequestMapping("oa/notice")
public class NoticeController {

    @Autowired
    private OaNoticeService     noticeService;

    @Autowired
    private SysUserService      userService;

    @Autowired
    private SysAttachService    attachService;

    @Autowired
    protected ConversionService conversionService;

    @Autowired
    private LogService          logService;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/oa/notice/list");
        return mav;
    }

    @RequestMapping("/query")
    public ModelAndView query() {
        ModelAndView mav = new ModelAndView("/oa/notice/queryList");
        mav.addObject("type", "query");
        return mav;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    // 对页面Pag的操作
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<OaNotice, Long> searchBuilder = new SearchBuilder<OaNotice, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        // 按部门查询
        searchBuilder.getFilterBuilder().notEqual("status", StatusFlag.已删除);
        searchBuilder.getFilterBuilder().equal("creatorId", UserTool.getInstance().getCurrentUserId());
        searchBuilder.appendSort(Direction.DESC, "displayType");
        searchBuilder.appendSort(Direction.DESC, "createDate");
        SearchListHelper<OaNotice> listhelper = new SearchListHelper<OaNotice>();
        listhelper.execute(searchBuilder, noticeService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), NoticeShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/querylist.json")
    @ResponseBody
    public Page querylistjson(Integer page, Integer rows, HttpServletRequest request) {
        String title = request.getParameter("title");
        String departmentId = request.getParameter("departmentId");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String currentDeptId = UserTool.getInstance().getCurrentUserDepartmentId();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dt1 = null, dt2 = null;
        try {
            if (StringUtils.isNotBlank(fromDate)) {
                dt1 = df.parse(fromDate + " 00:00");
            }
            if (StringUtils.isNotBlank(toDate)) {
                dt2 = df.parse(toDate + " 23:59");
            }
        } catch (ParseException e) {

        }
        return noticeService.selectAllForDeptShow(title, departmentId, dt1, dt2, currentDeptId, page, rows);
    }

    @RequestMapping("/new")
    public ModelAndView newPage() {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        ModelAndView mav = new ModelAndView("/oa/notice/edit");
        mav.addObject("currUser", user);
        mav.addObject("createTime", new Date());
        return mav;
    }

    @RequestMapping("/edit")
    public ModelAndView editPage(String id) {
        ModelAndView mav = new ModelAndView("/oa/notice/edit");
        OaNotice notice = this.noticeService.findOne(id);
        mav.addObject("notice", notice);
        mav.addObject("attachMap", this.attachService.getAttachMap(notice.getId(), OaNotice.class.getSimpleName()));
        if (notice.getCreatorId() != null) {
            mav.addObject("createName", userService.findOne(notice.getCreatorId()).getUserName());
        }
        return mav;
    }

    // 发布
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo save(NoticeEditBean bean, HttpServletRequest request, HttpServletResponse response) {

        try {
            if (bean != null) {
                bean.setStatus(StatusFlag.已发布);
            }
            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            if (user != null) {
                if (Tools.isEmptyString(bean.getId())) {
                    logService.saveLog(SjType.普通操作, "新增公告信息",LogStatus.成功, request);
                } else {
                    logService.saveLog(SjType.普通操作, "修改公告信息，修改id为'" + bean.getId() + "'的记录",LogStatus.成功, request);
                }
            }
            String noticeId = this.noticeService.saveNotice(bean);

            return new ReturnInfo(noticeId, null, "发布成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    // 暂存
    @RequestMapping("/saveTemp.json")
    @ResponseBody
    public ReturnInfo saveTemp(NoticeEditBean bean, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (bean != null) {
                bean.setStatus(StatusFlag.暂存);
            }
            SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
            String noticeId = this.noticeService.saveNotice(bean);
            logService.saveLog(SjType.普通操作, "暂存公告信息",LogStatus.成功, request);
            return new ReturnInfo(noticeId, null, "暂存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo del(String[] ids, HttpServletRequest request) {
        try {
            if (ids != null && ids.length > 0) {
                for (String noticeId : ids) {
                    this.noticeService.delNotice(noticeId);
                    logService.saveLog(SjType.普通操作, "删除公告信息：,删除id为'" + noticeId + "'的记录",LogStatus.成功, request);
                }
            }

            return new ReturnInfo("1", null, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detail(String id) {
        ModelAndView mav = new ModelAndView("/oa/notice/detail");
        OaNotice notice = null;
        if (id != null) {
            notice = this.noticeService.findOne(id);
            if (notice != null) {
                mav.addObject("notice", notice);
                mav.addObject("attachMap",
                                this.attachService.getAttachMap(String.valueOf(notice.getId()),
                                                OaNotice.class.getSimpleName()));
                if (notice.getCreatorId() != null) {
                    mav.addObject("createName", this.userService.findOne(notice.getCreatorId()).getUserName());
                }
            }
        }
        return mav;
    }

}
