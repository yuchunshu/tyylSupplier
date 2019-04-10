/*
 * FileName:    AppDeviceInfo.java
 * Description: ${DESCRIPTION}
 * Company:     南宁超创信息工程有限公司
 * Copyright:    ChaoChuang (c) 2017
 * History:     2017年10月19日 (cookie) 1.0 Create
 */
package cn.com.chaochuang.mobile.bean;

import cn.com.chaochuang.common.reference.IsValid;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.mobile.reference.AppOnlineFlag;
import cn.com.chaochuang.mobile.reference.RegisterStatus;
import org.dozer.Mapping;

import java.util.Date;

/**
 * @author shicx
 */
public class AppDeviceInfo {

    private String 			id;
    @Mapping("user.userName")
    private String  		userName;
    //设备uuid
    private String 			deviceUuid;
    //设备操作系统
    private String 			deviceOs;
    //设备操作系统版本
    private String 			deviceOsVersion;
    //安装的app版本
    private String 			appVersion;
    //注册时间
    private Date 			registerTime;
    private String 			registerTimeShow;
    //注册状态
    private RegisterStatus 	registerStatus;
    //备注
    private String 			remark;
    private IsValid 		valid;
    private AppOnlineFlag 	onlineFlag;
    private Date   			lastLoginTime;
    private String   		lastLoginTimeShow;
    /**推送token*/
    private String pushToken;
    //热更新版本
    private String 	chcpVersion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceUuid() {
        return deviceUuid;
    }

    public void setDeviceUuid(String deviceUuid) {
        this.deviceUuid = deviceUuid;
    }

    public String getDeviceOs() {
        return deviceOs;
    }

    public void setDeviceOs(String deviceOs) {
        this.deviceOs = deviceOs;
    }

    public String getDeviceOsVersion() {
        return deviceOsVersion;
    }

    public void setDeviceOsVersion(String deviceOsVersion) {
        this.deviceOsVersion = deviceOsVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterTimeShow() {
        if(this.registerTime!=null){
            return Tools.DATE_MINUTE_FORMAT.format(this.registerTime);
        }
        return registerTimeShow;
    }

    public RegisterStatus getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(RegisterStatus registerStatus) {
        this.registerStatus = registerStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public IsValid getValid() {
        return valid;
    }

    public void setValid(IsValid valid) {
        this.valid = valid;
    }

    public void setRegisterTimeShow(String registerTimeShow) {
        this.registerTimeShow = registerTimeShow;
    }

    public AppOnlineFlag getOnlineFlag() {
		return onlineFlag;
	}

	public void setOnlineFlag(AppOnlineFlag onlineFlag) {
		this.onlineFlag = onlineFlag;
	}

	public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginTimeShow() {
        if(this.lastLoginTime!=null){
            return Tools.DATE_MINUTE_FORMAT.format(this.lastLoginTime);
        }
        return lastLoginTimeShow;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public String getChcpVersion() {
        return chcpVersion;
    }

    public void setChcpVersion(String chcpVersion) {
        this.chcpVersion = chcpVersion;
    }
}
