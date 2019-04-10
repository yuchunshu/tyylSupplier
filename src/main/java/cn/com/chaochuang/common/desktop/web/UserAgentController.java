/*
 * FileName:    UserAgentController.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年10月10日 (pangj) 1.0 Create
 */

package cn.com.chaochuang.common.desktop.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.desktop.bean.UserAgentInfo;
import cn.com.chaochuang.common.desktop.domain.UserAgent;
import cn.com.chaochuang.common.desktop.service.UserAgentService;
import cn.com.chaochuang.common.security.util.UserTool;

/**
 * @author pangj 代理设置
 *
 */

@Controller
@RequestMapping("desktop/agent")
public class UserAgentController {

    @Autowired
    private UserAgentService userAgentService;

    // 代理设置
    @RequestMapping("/edit")
    public ModelAndView userAgent() {
        UserAgent agent = userAgentService.getAgentByUser(Long.valueOf(UserTool.getInstance().getCurrentUserId()));
        ModelAndView mav = new ModelAndView("/desktop/agent/userAgent");
        mav.addObject("obj", agent);
        return mav;
    }

    // 保存代理设置
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo saveUserAgent(UserAgentInfo info) {
        try {
            Long id = this.userAgentService.saveUserAgent(info);
            return new ReturnInfo(id.toString(), null, "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReturnInfo(null, "服务器连接不上！", null);
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public ReturnInfo del(Long id, HttpServletRequest request) {
        try {
            if (id != null) {
                this.userAgentService.delAgent(Long.valueOf(id));
            }
            return new ReturnInfo("1", null, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnInfo(null, "服务器连接不上！", null);
        }
    }

}
