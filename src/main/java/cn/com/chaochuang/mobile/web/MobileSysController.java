package cn.com.chaochuang.mobile.web;

import cn.com.chaochuang.common.data.domain.ValidAble;
import cn.com.chaochuang.common.reference.IsValid;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.user.bean.DepartmentShowBean;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.HashUtil;
import cn.com.chaochuang.mobile.bean.AppRegisterInfo;
import cn.com.chaochuang.mobile.bean.AppResponseInfo;
import cn.com.chaochuang.mobile.bean.AppUserInfo;
import cn.com.chaochuang.mobile.domain.SysMobileDevice;
import cn.com.chaochuang.mobile.reference.AppOnlineFlag;
import cn.com.chaochuang.mobile.reference.RegisterStatus;
import cn.com.chaochuang.mobile.service.MobileRegisterService;
import cn.com.chaochuang.mobile.util.AesTool;
import com.google.code.kaptcha.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hzr 2017.7.18
 * 移动端系统接口
 */

@Controller
@RequestMapping(value = "/mobile/sys")
public class MobileSysController extends MobileController {

	@Autowired
	private MobileRegisterService 	mobileRegisterService;

	/**
	 * 移动端登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/login.mo"})
    @ResponseBody
    public AppResponseInfo loginForApp(HttpServletRequest request, Boolean aesFlag_) {
		String account  = request.getParameter("account");
		String password = request.getParameter("password");

		//验证码
		String code = request.getParameter("code");
        String sCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (code == null || !code.equalsIgnoreCase(sCode)) {
        	return AesTool.responseData("0", "验证码错误！", null,aesFlag_);
        }

        SysUser user = userService.findByAccount(account);
        if (user == null || StringUtils.isBlank(password)) {
        	return AesTool.responseData("0", "用户不存在！", null,aesFlag_);
        } else {
        	if (!ValidAble.VALID.equals(user.getValid()) || YesNo.是.getKey().equals(user.getAccountLocked())) {
        		return AesTool.responseData("0", "用户不存在或已锁定！", null,aesFlag_);
        	}
        	if (!HashUtil.md5Text(password).equals(user.getPassword())) {
        		return AesTool.responseData("0", "用户名或密码错误！", null,aesFlag_);
        	} else {
        		logService.saveMobileLog(user, "移动端登录", request);
				AppUserInfo userInfo = new AppUserInfo(user.getId(), user.getUserName(), user.getDepartment().getId(), user.getDepartment().getDeptName());
				return AesTool.responseData(userInfo, aesFlag_);
        	}
        }

    }


    /**
	 * 根据部门ID取得该部门的人员列表
	 * */
	@RequestMapping(value = {"/getusers.mo"})
    @ResponseBody
    public AppResponseInfo userListJson(HttpServletRequest request, Boolean aesFlag_) {
    	String deptId  = request.getParameter("deptid");
    	if (StringUtils.isBlank(deptId)) {
    		return AesTool.responseData("0", "部门ID不能为空！", "",aesFlag_);
    	}
    	List<SysUser> userList = userService.findBydetpId(new Long(deptId));
    	List<AppUserInfo> list = new ArrayList();
    	for (SysUser u: userList) {
    		AppUserInfo bean = new AppUserInfo(u.getId(), u.getUserName(), u.getDepartment().getId(), u.getDepartment().getDeptName());
    		list.add(bean);
    	}
    	return AesTool.responseData(list,aesFlag_);
    }


    /**
	 * 根据部门ID取得该部门的第一层子部门列表
	 * */
	@RequestMapping(value = {"/getdepts.mo"})
    @ResponseBody
    public AppResponseInfo deptListJson(HttpServletRequest request, Boolean aesFlag_) {
    	String deptId  = request.getParameter("deptid");
    	if (StringUtils.isBlank(deptId)) {
    		//不传参数，则返回根部门（单位节点）
    		SysDepartment dept = this.deptService.findOne(SysDepartment.ROOT_DEPT);
    		DepartmentShowBean bean = new DepartmentShowBean();
    		bean.setId(dept.getId());
    		bean.setDeptName(dept.getDeptName());
    		bean.setHasChild(true);
    		return AesTool.responseData(bean,aesFlag_);
    	}
    	List<SysDepartment> deptList = deptService.getSubDeps(new Long(deptId));
    	List<DepartmentShowBean> list = new ArrayList();
    	for (SysDepartment dp: deptList) {
    		if (dp.getId().toString().equals(deptId)) {
    			continue;
			}

    		DepartmentShowBean bean = new DepartmentShowBean();
    		bean.setId(dp.getId());
    		bean.setDeptName(dp.getDeptName());
    		List sublist = this.deptService.getSubDeps(bean.getId());
    		bean.setHasChild((sublist != null && sublist.size() >= 1));
    		list.add(bean);
    	}
    	return AesTool.responseData(list,aesFlag_);
    }


    /**
	 * 密码修改
	 * */
	@RequestMapping(value = {"/updatepwd.mo"})
    @ResponseBody
    public AppResponseInfo updatePasswd(HttpServletRequest request, Boolean aesFlag_) {
		String oldpw = request.getParameter("oldpw");
		String newpw = request.getParameter("newpw");
    	SysUser user = this.loadCurrentUser(request);
    	if (user == null) {
    		return AesTool.responseData("0", "用户不存在！", null,aesFlag_);
    	}
		if (StringUtils.isBlank(oldpw) || StringUtils.isBlank(newpw)) {
			return AesTool.responseData("0", "密码不能为空！", null,aesFlag_);
		}
    	if (!HashUtil.md5Text(oldpw).equals(user.getPassword())) {
    		return AesTool.responseData("0", "旧密码错误！", null,aesFlag_);
    	}
        if (StringUtils.equals(oldpw, newpw)) {
        	return AesTool.responseData("0", "新密码不能与原密码相同！", null,aesFlag_);
        }
        if (StringUtils.length(newpw) < 6) {
            return AesTool.responseData("0", "新密码长度不能少于6位！", null,aesFlag_);
        }
        String regex = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!,@#$^&*_])[a-zA-Z0-9!,@#$^&*_]{6,16}$";// "^[A-Za-z].*[0-9]|[0-9].*[A-Za-z]{6,16}$";
        if (!newpw.matches(regex)) {
            return AesTool.responseData("0", "新密码长度为6至16，且至少含有一位数字，特殊字符（!@#$^&*_）和字母！", null,aesFlag_);
        }
		this.userService.updatePasswordMobile(newpw,user);

        return AesTool.responseData("",aesFlag_);
	}

	/**
	 * 查询用户是否已经注册
	 * @param registerInfo
	 * @return
	 */
	@RequestMapping(value = {"/checkuser.mo"})
	@ResponseBody
	public AppResponseInfo checkUserRegister(HttpServletRequest request,AppRegisterInfo registerInfo, Boolean aesFlag_) {
		SysUser user = this.loadCurrentUser(request);
		if (user == null) {
			return AesTool.responseData("0", "用户不存在！", null,aesFlag_);
		}
		AppResponseInfo responseInfo = new AppResponseInfo("0","无信息",null);
		//查询用户绑定的设备信息
		List<SysMobileDevice> deviceList = mobileRegisterService.findDeviceRegisterByUser(user,null);
		//（deviceList 一定不为空）
		if(deviceList.size()==0){
			//用户未绑定，直接保存绑定申请
			SysMobileDevice device = mobileRegisterService.deviceBindRegister(registerInfo);
			logService.saveMobileLog(user, "用户绑定设备:"+device.getDeviceUuid(),request);
			responseInfo = new AppResponseInfo(AesTool.getTokenConfig(device, aesFlag_));
		}else{
			//验证设备是否与注册的相同
			SysMobileDevice device = null;
			for(SysMobileDevice mobileDevice :deviceList){
				if(mobileDevice.getDeviceUuid()!=null&&mobileDevice.getDeviceUuid().equals(registerInfo.getDeviceUuid())){
					device = mobileDevice;
				}
			}
			if(device!=null&&IsValid.无效.equals(device.getValid())) {
				responseInfo.setData(null);
				responseInfo.setSuccess("0");
				responseInfo.setMessage("该设备已无效。请联系管理员");
			} else if(device!=null&&RegisterStatus.注册通过.equals(device.getRegisterStatus())) {
				//返回uid,ukey
				responseInfo = new AppResponseInfo(AesTool.getTokenConfig(device, aesFlag_));
				mobileRegisterService.saveDeviceOnlineStatus(device,AppOnlineFlag.在线,registerInfo);
			}else {
				//没有有效的注册信息，先保存注册申请，不返回注册的id，提示由管理员审批通过
				responseInfo = mobileRegisterService.deviceStartRegister(registerInfo);
				responseInfo.setData(null);
				responseInfo.setSuccess("0");
				responseInfo.setMessage("该账号已绑定过设备，绑定其他设备请联系管理员。");
			}
		}
		AesTool.responseData(responseInfo,aesFlag_);
		return responseInfo;
	}


	/**
	 * 查询设备是否已经注册
	 * @param registerInfo
	 * @return
	 */
	@RequestMapping(value = {"/checkregister.mo"})
	@ResponseBody
	public AppResponseInfo checkRegister(AppRegisterInfo registerInfo, Boolean aesFlag_) {
		AppResponseInfo responseInfo;
		//查询验证信息
		SysMobileDevice mobileDevice = mobileRegisterService.findDeviceRegisterInfo(registerInfo.getAccount(),registerInfo.getDeviceUuid());
		if(mobileDevice!=null&& RegisterStatus.注册通过.equals(mobileDevice.getRegisterStatus())){
			//返回uid,ukey
			responseInfo = new AppResponseInfo(AesTool.getTokenConfig(mobileDevice,aesFlag_));
		}else{
			//注册不通过
			responseInfo = mobileRegisterService.deviceStartRegister(registerInfo);
			responseInfo.setSuccess("0");

		}
		AesTool.responseData(responseInfo,aesFlag_);
		return responseInfo;
	}

	/**
	 * 保存注册信息
	 * @param registerInfo
	 * @return
	 */
	@RequestMapping(value = {"/saveregister.mo"})
	@ResponseBody
	public AppResponseInfo saveRegister(AppRegisterInfo registerInfo, Boolean aesFlag_){
		AppResponseInfo responseInfo = mobileRegisterService.saveRegister(registerInfo,aesFlag_);
		return responseInfo;
	}

	/**
	 * 发送短信
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value = {"/sendcode.mo"})
	@ResponseBody
	public AppResponseInfo sendSmsCode(String deviceId, String mobile, Boolean aesFlag_){
		AppResponseInfo responseInfo = mobileRegisterService.sendSmsCode(deviceId,mobile);
		AesTool.responseData(responseInfo,aesFlag_);
		return responseInfo;
	}

	/**
	 * 移动端登出，更新ukey
	 * @param uid
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/logout.mo")
	@ResponseBody
	public AppResponseInfo mobileLogout(Long uid, String uuid){
		boolean result = mobileRegisterService.mobileLogout(uid,uuid);
		AppResponseInfo info = new AppResponseInfo();
		if(result){
			info.setSuccess("1");
		}else{
			info.setSuccess("0");
		}
		AesTool.responseData(info,false);
		return info;
	}


	/**
	 * 移动端修改在线状态
	 * @param onlineFlag
	 * @param onlineFlag
	 * @return
	 */
	@RequestMapping("/online.mo")
	@ResponseBody
	public AppResponseInfo mobileOut(AppOnlineFlag onlineFlag,AppRegisterInfo registerInfo,HttpServletRequest request){
		SysUser user = this.loadCurrentUser(request);
		mobileRegisterService.saveDeviceOnlineStatus(user,onlineFlag,registerInfo);
		AppResponseInfo info = new AppResponseInfo("1");
		AesTool.responseData(info,false);
		return info;
	}

	/**
	 * 注册推送服务
	 * @return
	 */
	@RequestMapping("/apppush.mo")
	@ResponseBody
	public AppResponseInfo savePushRegister(AppRegisterInfo registerInfo, HttpServletRequest request,Boolean aesFlag_){
		SysUser user = this.loadCurrentUser(request);
		if (user == null) {
			return AesTool.responseData("0", "用户不存在", null,aesFlag_);
		}
		mobileRegisterService.savePushRegister(registerInfo.getDeviceUuid(),registerInfo.getPushToken(),user);
		return AesTool.responseData("1", "保存成功", null,aesFlag_);
	}
}
