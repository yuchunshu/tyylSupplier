/*
 * FileName:    AppMessageController.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年5月24日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.message.im.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.syspower.bean.DepAndUserTreeBean;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.oa.message.im.bean.ImMessageInfo;
import cn.com.chaochuang.oa.message.im.reference.ImMesStatus;
import cn.com.chaochuang.oa.message.im.service.ImMessageService;

/**
 * @author HM
 *
 */
@Controller
@RequestMapping("oa/immessage")
public class ImMessageController {

    @Autowired
    private ImMessageService    imMsgService;

    @Autowired
    private LogService          logService;

    @Autowired
    protected ConversionService conversionService;

    /**
     * 打开页面
     *
     * @return
     */
    @RequestMapping("/imDialog")
    public ModelAndView im() {
        ModelAndView mav = new ModelAndView("oa/message/im/imMsg");
        mav.addObject("curUserName", UserTool.getInstance().getCurrentUserName());
        return mav;
    }

    /**
     * 人员树
     *
     * @return
     */
    @RequestMapping("/getUserTree.json")
    public @ResponseBody List<DepAndUserTreeBean> userTree() {
        return imMsgService.getUserTree();
    }

    /**
     * 发送一条消息
     *
     * @param incepterId
     * @param content
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/send.json")
    @ResponseBody
    public String sendMsg(Long incepterId, String content, HttpServletRequest request, HttpServletResponse response) {

        // 插入数据库
        imMsgService.sendMessage(incepterId, content, request);

        // 页面
        return "<div style='text-align:left'><span style='color:green;'>" + UserTool.getInstance().getCurrentUserName()
                        + "&nbsp;&nbsp;&nbsp;" + Tools.DATE_TIME_FORMAT.format(new Date())
                        + "</span><br><span style='left:10px;'>" + content + "</span><br></div>";
    }

    /**
     * 接收消息
     *
     * @param senderId
     * @return
     */
    @RequestMapping("/getReceive.json")
    @ResponseBody
    public List<ImMessageInfo> receiveMsg(Long senderId, HttpServletRequest request, HttpServletResponse response) {

        List<ImMessageInfo> msgList = BeanCopyBuilder.buildList(imMsgService.receiveMessage(senderId),
                        ImMessageInfo.class);

        return msgList;
    }

    /**
     * 消息提醒
     *
     * @return
     */
    @RequestMapping("/getAlert.json")
    @ResponseBody
    public List<Long> alert(HttpServletRequest request, HttpServletResponse response) {

        List<Long> idList = imMsgService.messageAlert();

        return idList;
    }

    /**
     * 历史记录页面页面
     *
     * @return
     */
    @RequestMapping("/popupHistory")
    public ModelAndView history(Long senderId) {
        ModelAndView mav = new ModelAndView("oa/message/im/history");
        mav.addObject("curUser", UserTool.getInstance().getCurrentUser());
        if (senderId != null) {
            mav.addObject("senderId", senderId);
        }
        return mav;
    }

    /**
     * 历史记录
     *
     * @return
     */
    @RequestMapping("/listHistory.json")
    @ResponseBody
    public Page historyMsg(Long senderId, Integer page, Integer rows, HttpServletRequest request,
                    HttpServletResponse response) {
        Long curUserId = Long.valueOf(UserTool.getInstance().getCurrentUserId());
        Map<String, Object> map = imMsgService.historyMsg(senderId, curUserId, page, 20, null);
        Page p = new Page();
        p.setRows((List) map.get("datas"));
        p.setTotal((Long) map.get("total"));
        return p;
    }

    /**
     * 取3条已读历史记录
     *
     * @return
     */
    @RequestMapping("/getThreeHistory.json")
    @ResponseBody
    public List threeHistoryMsg(Long senderId, HttpServletRequest request, HttpServletResponse response) {
        Long curUserId = Long.valueOf(UserTool.getInstance().getCurrentUserId());
        Map<String, Object> map = imMsgService.historyMsg(senderId, curUserId, 1, 3, " and t.status=" + ImMesStatus.已读);
        return (List) map.get("datas");
    }

    /**
     * 删除消息（逻辑删除）
     *
     * @return
     */
    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo deleteMsg(Long[] ids, HttpServletRequest request, HttpServletResponse response) {
        try {
            imMsgService.deleteMsg(ids);
            return new ReturnInfo("1", null, "删除成功！");
        } catch (Exception e) {
            return new ReturnInfo(null, "服务器连接失败", null);
        }
    }
}
