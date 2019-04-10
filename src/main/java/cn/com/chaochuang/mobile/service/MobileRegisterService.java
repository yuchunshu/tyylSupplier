/*
 * FileName:    MobileCommonService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年09月13日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.mobile.service;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.reference.IsValid;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.mobile.bean.AppRegisterInfo;
import cn.com.chaochuang.mobile.bean.AppResponseInfo;
import cn.com.chaochuang.mobile.domain.SysMobileDevice;
import cn.com.chaochuang.mobile.reference.AppOnlineFlag;
import cn.com.chaochuang.mobile.reference.RegisterStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Shicx
 */
public interface MobileRegisterService extends CrudRestService<SysMobileDevice,String> {

    /**
     * 查询用户的设备绑定情况
     * @param account
     * @param valid =null表示查询所有有效和无效的
     * @return 返回null表示错误，返回空集合表示未绑定任何设备
     */
    List<SysMobileDevice> findDeviceRegisterByAccount(String account, IsValid valid);

    /**
     * 查询用户的设备绑定情况
     * @param user
     * @param valid =null表示查询所有有效和无效的
     * @return 返回null表示错误，返回空集合表示未绑定任何设备
     */
    List<SysMobileDevice> findDeviceRegisterByUser(SysUser user, IsValid valid);

    /**
     * 查询用户的设备是否已经注册
     * @param account
     * @param uuid
     * @return
     */
    SysMobileDevice findDeviceRegisterInfo(String account, String uuid);

    /**
     * 查询用户的设备是否已经注册
     * @param user
     * @param uuid
     * @return
     */
    SysMobileDevice findDeviceRegisterInfo(SysUser user, String uuid);

    /**
     * 注册申请
     * @param registerInfo
     * @return
     */
    AppResponseInfo deviceStartRegister(AppRegisterInfo registerInfo);

    /**
     * 绑定设备申请
     * @param registerInfo
     * @return
     */
    SysMobileDevice deviceBindRegister(AppRegisterInfo registerInfo);

    /**
     * 保存注册信息
     * @param registerInfo
     * @param aesFlag_
     * @return
     */
    AppResponseInfo saveRegister(AppRegisterInfo registerInfo, Boolean aesFlag_);

    /**
     * 重新发送短信
     * @param deviceId
     * @param mobile
     * @return
     */
    AppResponseInfo sendSmsCode(String deviceId, String mobile);

    /**
     * 登出app时更新ukey
     * @param uid
     * @param uuid
     * @return
     */
    boolean mobileLogout(Long uid, String uuid);

    /**
     * 不进行短信验证，直接保存设备信息，返回ukey
     * @param account
     * @param deviceUuid
     * @return
     */
    String saveRegisterByUuid(String account, String deviceUuid);

    boolean deleteMobileDevice(String deviceId, SysUser currentUser, HttpServletRequest request);

    /**
     * 修改设备状态
     * @param deviceId
     * @param registerStatus
     * @param currentUser
     * @param request
     * @return
     */
    boolean changeDeviceStatus(String deviceId, RegisterStatus registerStatus, SysUser currentUser, HttpServletRequest request);

    /**
     * 修改有效状态
     * @param deviceId
     * @param isValid
     * @param currentUser
     * @param request
     * @return
     */
    boolean changeDeviceValidable(String deviceId, IsValid isValid, SysUser currentUser, HttpServletRequest request);

    /**
     * 设置在线状态
     * @param device
     * @param onlineFlag
     */
    void saveDeviceOnlineStatus(SysMobileDevice device, AppOnlineFlag onlineFlag, AppRegisterInfo registerInfo);

    void saveDeviceOnlineStatus(SysUser user, AppOnlineFlag onlineFlag, AppRegisterInfo registerInfo);

    /**
     * 保存推送服务信息
     * @param deviceUuid
     * @param pushToken
     * @param user
     */
    boolean savePushRegister(String deviceUuid,String pushToken, SysUser user);

    /**
     * 推送信息
     * @param content
     * @param user
     * @return
     */
    void pushMessageToUser(SysUser user,String content,String stateName,Map<String,String> params);
    void pushMessageToUser(SysUser user,String content);
    /**
     * 推送信息
     * @param content
     * @param userId
     * @return
     */
    void pushMessageToUser(Long userId,String content,String stateName,Map<String,String> params);
    void pushMessageToUser(Long userId,String content);
    /**
     * 推送信息
     * @param content
     * @param userIds
     * @return
     */
    void pushMessageToUser(String userIds,String content,String stateName,Map<String,String> params);
    void pushMessageToUser(String userIds,String content);

    /**
     * 推送给所有人
     * @param content
     * @param stateName
     * @param params
     */
    void pushMessageToAll(String content,String stateName,Map<String,String> params);
    void pushMessageToAll(String content);

    void clearUserTokenId(SysUser user);

    void pushMessageToDept(Long deptId, String content, String stateName, Map<String, String> params);

    /**
     * 删除用户的所有设备记录
     * @param user
     */
    void deleteAllDevicesByUser(SysUser user);
}
