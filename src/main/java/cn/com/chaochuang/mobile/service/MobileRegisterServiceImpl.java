/*
 * FileName:    MobileCommonServiceImpl.java
 * Description: ${DESCRIPTION}
 * Company:     南宁超创信息工程有限公司
 * Copyright:    ChaoChuang (c) 2017
 * History:     2017年09月13日 (cookie) 1.0 Create
 */
package cn.com.chaochuang.mobile.service;

import cn.com.chaochuang.common.data.domain.ValidAble;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.reference.IsValid;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysUserRepository;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.mobile.bean.AppRegisterInfo;
import cn.com.chaochuang.mobile.bean.AppResponseInfo;
import cn.com.chaochuang.mobile.domain.SysMobileDevice;
import cn.com.chaochuang.mobile.reference.AppOnlineFlag;
import cn.com.chaochuang.mobile.reference.RegisterStatus;
import cn.com.chaochuang.mobile.repository.SysMobileDeviceRepository;
import cn.com.chaochuang.mobile.util.AesTool;
import cn.com.chaochuang.mobile.util.JPushUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @author Shicx
 */
@Service
@Transactional
public class MobileRegisterServiceImpl extends SimpleUuidCrudRestService<SysMobileDevice> implements MobileRegisterService {

    @Autowired
    private SysMobileDeviceRepository sysMobileDeviceRepository;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysDepartmentService deptService;
    @Autowired
    private LogService logService;

    @Override
    public SimpleDomainRepository<SysMobileDevice, String> getRepository() {
        return sysMobileDeviceRepository;
    }

    @Override
    public List<SysMobileDevice> findDeviceRegisterByAccount(String account, IsValid valid) {
        SysUser user = sysUserRepository.findByAccountAndValid(account, ValidAble.VALID);
        return this.findDeviceRegisterByUser(user, valid);
    }

    @Override
    public List<SysMobileDevice> findDeviceRegisterByUser(SysUser user, IsValid valid) {
        if (user == null) {
            return null;
        }
        List<SysMobileDevice> mobileDeviceList = null;
        if (valid == null) {
            //查询所有状态的
            mobileDeviceList = sysMobileDeviceRepository.findByUser(user);
        } else {
            mobileDeviceList = sysMobileDeviceRepository.findByUserAndValid(user, valid);
        }
        if (!Tools.isNotEmptyList(mobileDeviceList)) {
            return Lists.newArrayList();
        }
        return mobileDeviceList;
    }

    @Override
    public SysMobileDevice findDeviceRegisterInfo(String account, String uuid) {
        SysUser user = sysUserRepository.findByAccountAndValid(account, ValidAble.VALID);
        if (user == null) {
            return null;
        }
        return this.findDeviceRegisterInfo(user, uuid);
    }

    @Override
    public SysMobileDevice findDeviceRegisterInfo(SysUser user, String uuid) {
        List<SysMobileDevice> mobileDeviceList = sysMobileDeviceRepository.findByUserAndDeviceUuidAndValid(user, uuid, IsValid.有效);
        SysMobileDevice mobileDevice = null;
        //一个用户的一台设备，有效的注册信息只有一个
        if (Tools.isNotEmptyList(mobileDeviceList)) {
            mobileDevice = mobileDeviceList.get(0);
            //防止ukey为空
            if (StringUtils.isBlank(mobileDevice.getUkey())) {
                mobileDevice.setUkey(AesTool.get16Uuid());
                sysMobileDeviceRepository.save(mobileDevice);
            }

        }
        return mobileDevice;
    }

    @Override
    public AppResponseInfo deviceStartRegister(AppRegisterInfo registerInfo) {
        if (registerInfo == null || StringUtils.isBlank(registerInfo.getDeviceUuid())) {
            //设备uuid不能为空
            return new AppResponseInfo("0", "未获取到设备信息", null);
        }
        SysUser user = sysUserRepository.findByAccountAndValid(registerInfo.getAccount(), ValidAble.VALID);
        if (user == null) {
            return new AppResponseInfo("0", "没有用户信息", null);
        }

        //查找设备信息
        SysMobileDevice mobileDevice = this.findDeviceRegisterInfo(user, registerInfo.getDeviceUuid());
        if (mobileDevice == null) {
            mobileDevice = new SysMobileDevice();
            mobileDevice.setRegisterStatus(RegisterStatus.未完成注册);
            mobileDevice.setUser(user);
        } else {
            mobileDevice.setRegisterStatus(RegisterStatus.重新申请);
        }
        mobileDevice.setDeviceUuid(registerInfo.getDeviceUuid());

        mobileDevice.setValid(IsValid.有效);
        mobileDevice.setRegisterTime(new Date());
        mobileDevice.setDeviceOs(registerInfo.getDeviceOs());
        mobileDevice.setDeviceOsVersion(registerInfo.getDeviceOsVersion());
        mobileDevice.setAppVersion(registerInfo.getAppVersion());
        this.sysMobileDeviceRepository.saveAndFlush(mobileDevice);
        //返回id
        return new AppResponseInfo(mobileDevice.getId());
    }

    @Override
    public SysMobileDevice deviceBindRegister(AppRegisterInfo registerInfo) {
        if (registerInfo == null || StringUtils.isBlank(registerInfo.getDeviceUuid())) {
            //设备uuid不能为空
            return null;
        }
        SysUser user = sysUserRepository.findByAccountAndValid(registerInfo.getAccount(), ValidAble.VALID);
        if (user == null) {
            return null;
        }

        //查找设备信息
        SysMobileDevice mobileDevice = this.findDeviceRegisterInfo(user, registerInfo.getDeviceUuid());
        if (mobileDevice == null) {
            mobileDevice = new SysMobileDevice();
            mobileDevice.setUser(user);
        }
        mobileDevice.setDeviceUuid(registerInfo.getDeviceUuid());
        mobileDevice.setRegisterStatus(RegisterStatus.注册通过);
        mobileDevice.setUkey(AesTool.get16Uuid());
        mobileDevice.setValid(IsValid.有效);
        mobileDevice.setRegisterTime(new Date());
        mobileDevice.setDeviceOs(registerInfo.getDeviceOs());
        mobileDevice.setDeviceOsVersion(registerInfo.getDeviceOsVersion());
        mobileDevice.setAppVersion(registerInfo.getAppVersion());
        mobileDevice.setChcpVersion(registerInfo.getChcpVersion());
        mobileDevice.setLastLoginTime(new Date());
        this.sysMobileDeviceRepository.saveAndFlush(mobileDevice);
        //返回id
        return mobileDevice;
    }

    @Override
    public AppResponseInfo saveRegister(AppRegisterInfo registerInfo, Boolean aesFlag_) {
        AppResponseInfo responseInfo = new AppResponseInfo();
        String message = "";
        String success = "0";
        if (registerInfo == null) {
            message = "参数错误";
        } else if (StringUtils.isBlank(registerInfo.getSmsCode())) {
            message = "验证码不能为空";
        } else if (registerInfo.getDeviceId() == null) {
            message = "参数错误";
        } else {
            SysMobileDevice mobileDevice = this.sysMobileDeviceRepository.findOne(registerInfo.getDeviceId());
            if (mobileDevice != null) {
                //判断验证码是否过期(超过5分钟)
                if (this.isOverTime(mobileDevice.getSmsSendTime(), 5)) {
                    message = "验证码超时，请重新发送短信";
                } else if (!registerInfo.getSmsCode().equals(mobileDevice.getSmsCode())) {
                    message = "验证码错误";
                } else {
                    //保存验证信息
                    mobileDevice.setRegisterStatus(RegisterStatus.注册通过);
                    mobileDevice.setValid(IsValid.有效);
                    mobileDevice.setUkey(AesTool.get16Uuid());
                    this.sysMobileDeviceRepository.save(mobileDevice);
                    success = "1";
                    message = "注册成功";
                    responseInfo.setData(AesTool.getTokenConfig(mobileDevice, aesFlag_));
                }
            }
        }
        responseInfo.setSuccess(success);
        responseInfo.setMessage(message);
        return responseInfo;
    }

    @Override
    public AppResponseInfo sendSmsCode(String deviceId, String mobile) {
        if (deviceId == null) {
            return new AppResponseInfo("0", "未获取到设备信息", null);
        }
        SysMobileDevice mobileDevice = this.sysMobileDeviceRepository.findOne(deviceId);
        if (mobileDevice == null || mobileDevice.getUser() == null) {
            return new AppResponseInfo("0", "未获取到注册信息", null);
        }
        if (mobile == null || !mobile.equals(mobileDevice.getUser().getMobile())) {
            return new AppResponseInfo("0", "输入的手机号与原手机不符", null);
        }
        if (mobileDevice.getSmsSendTime() != null) {
            long timeInterval = System.currentTimeMillis() - mobileDevice.getSmsSendTime().getTime();
            if (Math.abs(timeInterval) / 1000 < 30) {
                //设置短信发送间隔
                return new AppResponseInfo("0", "请稍后重试", null);
            }
        }
        //产生短信验证码
        String smsCode = this.generateSmsCode();
        mobileDevice.setSmsCode(smsCode);
        mobileDevice.setSmsSendTime(new Date());
        //发送短信： 【电子政务】您正在用手机注册，验证码为 smsCode
        //this.sendSMSByUser(mobileDevice.getUser(),msgTemplate.replace("smsCode",smsCode));
        this.sysMobileDeviceRepository.save(mobileDevice);
        //截取手机号，只显示前3位和后4位
        if (mobile.length() > 7) {
            mobile = mobile.substring(0, 3) + "***" + mobile.substring(mobile.length() - 4);
        }
        return new AppResponseInfo(mobile);
    }

    /**
     * 将时间与当前时间比较，返回是否超过指定时间
     *
     * @param smsSendTime
     * @return
     */
    private boolean isOverTime(Date smsSendTime, int minutes) {
        long timeInterval = System.currentTimeMillis() - smsSendTime.getTime();
        return timeInterval / (1000 * 60) > minutes;
    }

    /**
     * 生产6位数的验证码
     *
     * @return
     */
    private String generateSmsCode() {
        int max = 999999;
        int min = 100000;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;

        return s + "";
    }

    @Override
    public boolean mobileLogout(Long uid, String uuid) {
        if (uid == null || StringUtils.isBlank(uuid)) {
            return false;
        }
        SysUser user = sysUserRepository.findOne(uid);
        if (user == null) {
            return false;
        }
        SysMobileDevice device = this.findDeviceRegisterInfo(user, uuid);
        if (device == null) {
            return false;
        }
        //重新给定一个ukey
        device.setUkey(AesTool.get16Uuid());
        //设置为离线
        this.saveDeviceOnlineStatus(device, AppOnlineFlag.离线, null);
        this.sysMobileDeviceRepository.save(device);
        return true;
    }

    /**
     * 不进行短信验证，直接保存设备信息，返回ukey
     *
     * @param account
     * @param deviceUuid
     * @return
     */
    @Override
    public String saveRegisterByUuid(String account, String deviceUuid) {
        SysUser user = sysUserRepository.findByAccountAndValid(account, ValidAble.VALID);
        if (user == null) {
            return null;
        }
        SysMobileDevice device = this.findDeviceRegisterInfo(user, deviceUuid);
        if (device == null) {
            device = new SysMobileDevice();
            device.setUser(user);
            device.setRegisterStatus(RegisterStatus.未完成注册);
            device.setValid(IsValid.有效);
            device.setUkey(AesTool.get16Uuid());
            this.sysMobileDeviceRepository.save(device);
        }
        //防止uuid为空
        if (StringUtils.isBlank(device.getUkey())) {
            device.setUkey(AesTool.get16Uuid());
            this.sysMobileDeviceRepository.save(device);
        }
        return device.getId();
    }

    @Override
    public boolean deleteMobileDevice(String deviceId, SysUser currentUser, HttpServletRequest request) {
        if (deviceId == null || currentUser == null) {
            return false;
        }
        SysMobileDevice mobileDevice = this.findOne(deviceId);
        if (mobileDevice != null) {
            String userName = "";
            if (mobileDevice.getUser() != null) {
                userName = mobileDevice.getUser().getUserName();
            }
            logService.saveUserLog(currentUser, SjType.其他, "删除用户" + userName + "移动设备信息:" + mobileDevice.getDeviceUuid(), LogStatus.成功, request);
            this.delete(mobileDevice);
            return true;
        }
        return false;
    }

    @Override
    public boolean changeDeviceStatus(String deviceId, RegisterStatus registerStatus, SysUser currentUser, HttpServletRequest request) {
        if (deviceId == null || currentUser == null || registerStatus == null) {
            return false;
        }
        SysMobileDevice mobileDevice = this.findOne(deviceId);
        if (mobileDevice != null) {
            mobileDevice.setRegisterStatus(registerStatus);
            //判断ukey
            if (StringUtils.isBlank(mobileDevice.getUkey())) {
                mobileDevice.setUkey(AesTool.get16Uuid());
            }
            sysMobileDeviceRepository.save(mobileDevice);
            String userName = "";
            if (mobileDevice.getUser() != null) {
                userName = mobileDevice.getUser().getUserName();
            }
            logService.saveUserLog(currentUser, SjType.其他, "修改用户" + userName + "移动设备状态为:" + registerStatus.getValue(),LogStatus.成功, request);
            return true;
        }
        return false;
    }

    @Override
    public boolean changeDeviceValidable(String deviceId, IsValid isValid, SysUser currentUser, HttpServletRequest request) {
        if (deviceId == null || currentUser == null || isValid == null) {
            return false;
        }
        SysMobileDevice mobileDevice = this.findOne(deviceId);
        if (mobileDevice != null) {
            mobileDevice.setValid(isValid);
            //判断ukey
            if (IsValid.有效.equals(isValid)) {
                mobileDevice.setUkey(AesTool.get16Uuid());
            } else {
                mobileDevice.setUkey(null);
                mobileDevice.setOnlineFlag(AppOnlineFlag.离线);
            }
            sysMobileDeviceRepository.save(mobileDevice);
            String userName = "";
            if (mobileDevice.getUser() != null) {
                userName = mobileDevice.getUser().getUserName();
            }
            logService.saveUserLog(currentUser, SjType.其他, "修改用户" + userName + "移动设备有效状态为:" + isValid.getValue(),LogStatus.成功, request);
            return true;
        }
        return false;
    }

    @Override
    public void saveDeviceOnlineStatus(SysMobileDevice device, AppOnlineFlag onlineFlag, AppRegisterInfo registerInfo) {
        if (device != null) {
            device.setOnlineFlag(onlineFlag);
            //设备信息
            if (registerInfo != null) {
                device.setAppVersion(registerInfo.getAppVersion());
                device.setChcpVersion(registerInfo.getChcpVersion());
                device.setDeviceOsVersion(registerInfo.getDeviceOsVersion());
                device.setDeviceOs(registerInfo.getDeviceOs());
            }
            if (AppOnlineFlag.在线.equals(onlineFlag)) {
                device.setLastLoginTime(new Date());
                if(IsValid.有效.equals(device.getValid())&&StringUtils.isEmpty(device.getUkey())){
                    //防止ukey为空
                    device.setUkey(AesTool.get16Uuid());
                }
            }
            this.sysMobileDeviceRepository.save(device);
        }
    }

    @Override
    public void saveDeviceOnlineStatus(SysUser user, AppOnlineFlag onlineFlag, AppRegisterInfo registerInfo) {
        SysMobileDevice device = this.findDeviceRegisterInfo(user, registerInfo.getDeviceUuid());
        this.saveDeviceOnlineStatus(device, onlineFlag, registerInfo);
    }

    @Override
    public boolean savePushRegister(String deviceUuid, String pushToken, SysUser user) {
        if (user == null) {
            return false;
        }
        SysMobileDevice device = this.findDeviceRegisterInfo(user, deviceUuid);
        if (device != null) {
            device.setPushToken(pushToken);
            sysMobileDeviceRepository.save(device);
            return true;
        }
        return false;
    }

    @Override
    public void pushMessageToUser(SysUser user, String content) {
        this.pushMessageToUser(user,content,null,null);
    }

    @Override
    public void pushMessageToUser(SysUser user, String content,String stateName,Map<String,String> params) {
        List<SysMobileDevice> deviceList = this.findDeviceRegisterByUser(user, IsValid.有效);
        if (Tools.isNotEmptyList(deviceList)) {

            for(SysMobileDevice device : deviceList){
                //有token才推送
                if(StringUtils.isNotEmpty(device.getPushToken())){
                    JPushUtils.pushByRegistrationID(device.getPushToken(),content,stateName,params);
                }
            }
        }
    }

    @Override
    public void pushMessageToUser(Long userId, String content) {
        this.pushMessageToUser(userId,content,null,null);
    }

    @Override
    public void pushMessageToUser(Long userId, String content,String stateName,Map<String,String> params) {
        if (userId != null) {
            SysUser user = this.sysUserRepository.findOne(userId);
            this.pushMessageToUser(user, content,stateName,params);
        }
    }

    @Override
    public void pushMessageToUser(String userIds, String content,String stateName,Map<String,String> params) {
        if (StringUtils.isNotEmpty(userIds)) {
            String[] idArr = userIds.split(",");
            for(String idStr : idArr) {
                SysUser user = this.sysUserRepository.findOne(Long.valueOf(idStr));
                this.pushMessageToUser(user, content,stateName,params);
            }
        }
    }

    @Override
    public void pushMessageToUser(String userIds, String content) {
        this.pushMessageToUser(userIds,content,null,null);
    }

    @Override
    public void pushMessageToAll(String content,String stateName,Map<String,String> params) {
        JPushUtils.pushToAll(content,stateName,params);
    }

    @Override
    public void pushMessageToAll(String content) {
        this.pushMessageToAll(content,null,null);
    }

    @Override
    public void clearUserTokenId(SysUser user) {
        if(user!=null){
            List<SysMobileDevice> mobileDeviceList = this.sysMobileDeviceRepository.findByUser(user);
            if(mobileDeviceList!=null){
                for(SysMobileDevice mobileDevice : mobileDeviceList){
                    mobileDevice.setUkey(null);
                    mobileDevice.setOnlineFlag(AppOnlineFlag.离线);
                }
                this.sysMobileDeviceRepository.save(mobileDeviceList);
            }
        }

    }

    @Override
    public void pushMessageToDept(Long deptId, String content, String stateName, Map<String, String> params) {
        if(deptId==null){
            return;
        }
        StringBuilder receiveMans = new StringBuilder();// 所有要发送的人id
        List<SysUser> uList = new ArrayList<SysUser>();// 所有要发送的人
        List<SysDepartment> sendDepts = new ArrayList<SysDepartment>();// 所有要发送的部门

        SysDepartment curDept = this.deptService.findOne(deptId);// 当前部门
        List<SysDepartment> childrens = this.deptService.getAllChildrens(deptId);// 所有子部门
        if (curDept != null) {
            sendDepts.add(curDept);
        }
        if (childrens != null) {
            sendDepts.addAll(childrens);
        }

        for (SysDepartment d : sendDepts) {
            uList.addAll(sysUserRepository.findByDepartmentIdAndValidOrderBySortAsc(d.getId(), ValidAble.VALID));
        }

        for (SysUser u : uList) {
            receiveMans.append(u.getId()).append(",");
        }
        if(receiveMans.length()>0) {
            this.pushMessageToUser(receiveMans.substring(0,receiveMans.length()-1), content, stateName, params);
        }
    }

    @Override
    public void deleteAllDevicesByUser(SysUser user) {
        List<SysMobileDevice> mobileDeviceList = sysMobileDeviceRepository.findByUser(user);
        if(mobileDeviceList!=null){
            sysMobileDeviceRepository.delete(mobileDeviceList);
        }
    }
}
