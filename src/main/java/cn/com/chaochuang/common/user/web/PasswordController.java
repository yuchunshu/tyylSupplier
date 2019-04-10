package cn.com.chaochuang.common.user.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.chaochuang.mobile.service.MobileRegisterService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.chaochuang.common.bean.Result;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.HashUtil;

@Controller
@RequestMapping("system/pwd")
public class PasswordController {

    private final static int PASSWORD_MIN_LENGTH = 6;

    @Value("${user.initpassword}")
    private String           initPasswordPlainText;

    @Autowired
    private SysUserService   userService;

    @Autowired
    private MobileRegisterService mobileRegisterService;

    @Autowired
    private LogService       logService;

    @RequestMapping("/edit")
    public ModelAndView editPage() {
        ModelAndView mav = new ModelAndView("system/pwd/updPwd");
        mav.addObject("user", UserTool.getInstance().getCurrentUser());
        return mav;
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public Result update(String oldPwd, String newPwd, String confirmPwd, HttpServletRequest request) {
        List<String> errMsgs = new ArrayList<String>();
        if (checkOld(oldPwd, errMsgs) && checkNew(oldPwd, newPwd, confirmPwd, errMsgs)) {
            if (updatePwd(newPwd)) {
                logService.saveLog(SjType.部门用户, "修改本账户密码：流水号/" + UserTool.getInstance().getCurrentUserId(),LogStatus.成功, request);
                return new Result("success", null);
            }
        }
        return new Result("error", errMsgs.get(0));
    }

    // 检查是否是原密码
    public boolean checkOld(String oldPwd, List<String> errMsgs) {
        String uId = UserTool.getInstance().getCurrentUserId();
        SysUser curUser = userService.findOne(Long.valueOf(uId));
        if (null != curUser) {
            String md5 = null;
            if (null != oldPwd && !oldPwd.isEmpty())
                md5 = HashUtil.md5Text(oldPwd);
            if (curUser.getPassword().equals(md5)) {
                return true;
            } else {
                errMsgs.add("原密码不正确！");
            }
        }
        return false;
    }

    // 新密码检查
    public boolean checkNew(String oldPwd, String newPwd, String confirmPwd, List<String> errMsgs) {
        if (StringUtils.equals(oldPwd, newPwd)) {
            errMsgs.add("新密码不能与原密码一致！");
            return false;
        }
        if (!StringUtils.equals(confirmPwd, newPwd)) {
            errMsgs.add("新密码与确认密码不一致！");
            return false;
        }
        if (StringUtils.length(newPwd) < PASSWORD_MIN_LENGTH) {
            errMsgs.add("新密码长度不能少于6位！");
            return false;
        }
        String regex = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{6,16}$";// "^[A-Za-z].*[0-9]|[0-9].*[A-Za-z]{6,16}$";
        if (!newPwd.matches(regex)) {
            errMsgs.add("新密码长度为6至16，且至少含有一位数字和字母！");
            return false;
        }
        return true;
    }

    // 更新当前用户的密码
    private boolean updatePwd(String password) {
        SysUser curUser = (SysUser) UserTool.getInstance().getCurrentUser();
        if (null != curUser) {
            curUser.setPassword(HashUtil.md5Text(password));
            curUser.setLastPasswdDate(new Date());
            this.userService.persist(curUser);

            this.mobileRegisterService.clearUserTokenId(curUser);
            return true;
        }
        return false;
    }

    @RequestMapping("/adminUpdatePwd")
    public ModelAndView updPwdPage(Long userId) {
        ModelAndView mav = new ModelAndView("system/pwd/adminUpdatePwd");
        mav.addObject("userId", userId);
        return mav;
    }

    // 管理员修改用户密码
    @RequestMapping("/updatePwdByAdmin.json")
    @ResponseBody
    public Result adminUpdateUserPwd(String newPwd, Long userId, HttpServletRequest request) {
        if (this.userService.updatePassword(newPwd, userId)) {
            logService.saveLog(SjType.部门用户, "修改了用户“" + this.userService.findOne(userId).getUserName() + "”的密码。",LogStatus.成功, request);
            return new Result("success", null);
        }
        return new Result("error", "保存失败！");
    }

    // 管理员重置用户密码
    @RequestMapping("/dealResetPwd.json")
    @ResponseBody
    public Result resetPwd(Long userId, HttpServletRequest request) {
        if (this.userService.updatePassword(null, userId)) {
            logService.saveLog(SjType.部门用户, "重置了用户“" + this.userService.findOne(userId).getUserName() + "”的密码。",LogStatus.成功, request);
            return new Result("success", null);
        }
        return new Result("error", "保存失败！");
    }
}
