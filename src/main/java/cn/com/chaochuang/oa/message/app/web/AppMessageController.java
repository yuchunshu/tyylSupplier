/*
 * FileName:    AppMessageController.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.message.app.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import cn.com.chaochuang.oa.message.app.bean.MessageInfoBean;
import cn.com.chaochuang.oa.message.app.bean.MessageShowBean;
import cn.com.chaochuang.oa.message.app.domain.OaAppMessage;
import cn.com.chaochuang.oa.message.app.reference.MesType;
import cn.com.chaochuang.oa.message.app.service.AppMessageService;

/**
 * @author HM
 *
 */
@Controller
@RequestMapping("oa/appmessage")
public class AppMessageController {

    @Autowired
    private AppMessageService   messageService;

    @Autowired
    protected ConversionService conversionService;

    @Value(value = "${messageUrl.mailPath}")
    private String              mailPath;

    /** 清除：所有 */
    private final String        _CLEAR_TYPE_ALL               = "1";

    /** 清除：除最后一个月 */
    private final String        _CLEAR_TYPE_EXCEPT_LAST_MONTH = "2";

    /** 清除：已读 */
    private final String        _CLEAR_TYPE_READ              = "3";

    /**
     * 短信提醒列表
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/oa/message/list");
        return mav;
    }

    /**
     * 短信提醒列表数据
     *
     * @param page
     * @param rows
     * @param request
     * @return
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public Page listjson(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<OaAppMessage, Long> searchBuilder = new SearchBuilder<OaAppMessage, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("incepterId", UserTool.getInstance().getCurrentUserId());
        searchBuilder.appendSort(Direction.ASC, "isRead");// 未阅消息在前
        searchBuilder.appendSort(Direction.DESC, "createTime");
        SearchListHelper<OaAppMessage> listhelper = new SearchListHelper<OaAppMessage>();
        listhelper.execute(searchBuilder, messageService.getRepository(), page, rows);
        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), MessageShowBean.class));
        p.setTotal(listhelper.getCount());
        return p;
    }

    @RequestMapping("/query.json")
    @ResponseBody
    public MessageInfoBean oaAppMsgQuery(HttpServletRequest request, HttpServletResponse response) {
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        Long userId = user.getId();
        Integer mailSum = this.messageService.updateMsg(userId, MesType.邮件); // 邮件
        Integer imMessageSum = this.messageService.updateMsg(userId, MesType.消息); // 即时消息
        Integer otherSum = this.messageService.updateMsg(userId, MesType.其余业务); // 其余业务模块消息

        MessageInfoBean msgInfo = new MessageInfoBean();
        if (mailSum > 0) {
            msgInfo.setMailPath(mailPath);
            msgInfo.setMailSum(mailSum);
        }

        if (imMessageSum > 0) {
            msgInfo.setImMessageSum(imMessageSum);
        }

        if (otherSum > 0) {
            msgInfo.setOtherSum(otherSum);
        }

        msgInfo.setNotReadSum(messageService.countNotRead());
        return msgInfo;
    }

    /**
     * 获取用户待显示的信息
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/userQuery")
    public ModelAndView oaGetUserMsgToShow(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 根据登录人编号获取5个需提醒消息，按时间正序排列
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        if (user != null) {
            String message = this.messageService.saveUserMessage(user.getId());
            PrintWriter pw = response.getWriter();
            pw.println(message);
        }
        return null;
    }

    /**
     * 首页历史消息
     *
     * @param id
     * @return
     */
    @RequestMapping("/getWelcomeMsg")
    @ResponseBody
    public ModelAndView welcomeMsg(MesType mesType) {
        ModelAndView mav = new ModelAndView("/desktop/contentParse/msgContent");
        mav.addObject("mailPath", mailPath);
        if (mesType != null) {
            mav.addObject("mesType", mesType);
        }
        return mav;
    }

    /**
     * 首页历史消息数据
     *
     * @param page
     * @param rows
     * @param request
     * @return
     */
    @RequestMapping("/getWelcomeMsg.json")
    @ResponseBody
    public Page welcomeMsg(Integer page, Integer rows, HttpServletRequest request) {
        SearchBuilder<OaAppMessage, Long> searchBuilder = new SearchBuilder<OaAppMessage, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        searchBuilder.getFilterBuilder().equal("incepterId", UserTool.getInstance().getCurrentUserId());
        searchBuilder.appendSort(Direction.ASC, "isRead");// 未阅消息在前
        searchBuilder.appendSort(Direction.DESC, "createTime");
        SearchListHelper<OaAppMessage> listhelper = new SearchListHelper<OaAppMessage>();
        listhelper.execute(searchBuilder, messageService.getRepository(), page, rows);
        Page p = new Page();
        p.setTotal(listhelper.getCount());
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), MessageShowBean.class));
        return p;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo del(String ids, HttpServletRequest request) {
        try {
            if (StringUtils.isNotBlank(ids)) {
                String idArr[] = ids.split(",");
                for (String idStr : idArr) {
                    messageService.delete(Long.valueOf(idStr));
                }
                return new ReturnInfo("1", null, "删除成功!");
            } else {
                return new ReturnInfo(null, "服务器连接不上！", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    /**
     * 消息阅知
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/dealRead.json")
    @ResponseBody
    public ReturnInfo read(Long id, HttpServletRequest request) {
        try {
            return new ReturnInfo(messageService.readMsg(id).toString(), null, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    /**
     * 批量阅知
     *
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping("/dealReadBatch.json")
    @ResponseBody
    public ReturnInfo readBatch(Long[] ids, HttpServletRequest request) {
        try {
            messageService.readMsgs(ids);
            return new ReturnInfo("1", null, "操作成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

    /**
     * 清理
     *
     * @param cleartype
     * @return
     */
    @RequestMapping("/dealClearMsg")
    @ResponseBody
    public ReturnInfo clearMsg(String cleartype) {
        try {
            if (StringUtils.isNotBlank(cleartype)) {
                switch (cleartype) {
                case _CLEAR_TYPE_ALL:
                    messageService.cleanAll();
                    break;
                case _CLEAR_TYPE_EXCEPT_LAST_MONTH:
                    messageService.cleanExceptLastThirty();
                    break;
                case _CLEAR_TYPE_READ:
                    messageService.readAll();
                    break;
                }
            }
            return new ReturnInfo("1", null, "清理完成！");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }
}
