package cn.com.chaochuang.common.desktop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.desktop.bean.UserProfileInfo;
import cn.com.chaochuang.common.desktop.domain.UserProfile;
import cn.com.chaochuang.common.desktop.service.UserProfileService;
import cn.com.chaochuang.common.security.util.UserTool;

/**
 * @author HM 个人设置
 */
@Controller
@RequestMapping("desktop/setting")
public class UserProfileController {

    /** 默认显示顺序 */
    private final String          _DEFAULT_MODELS = "[{name:\"mailContent\",location:\"left\",status:\"block\"}]";

    @Autowired
    private UserProfileService    profileService;

    // 个人设置
    @RequestMapping("/edit")
    public ModelAndView setting() {
        ModelAndView mav = new ModelAndView("desktop/setting/profile");
        UserProfile profile = profileService.findOne(Long.valueOf(UserTool.getInstance().getCurrentUserId()));
        if (profile == null) {
            profile = new UserProfile(null, null, null, _DEFAULT_MODELS);
        }
        mav.addObject("profile", profile);
        return mav;
    }

    // 保存个人设置
    @RequestMapping("/save.json")
    @ResponseBody
    public ReturnInfo saveDisplayModels(UserProfileInfo info) {
        try {
            Long id = profileService.saveProfile(info);
            return new ReturnInfo(id.toString(), null, "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReturnInfo(null, "服务器连接不上！", null);
    }
}
