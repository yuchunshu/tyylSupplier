/*
 * FileName:    AppRegisterInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年09月13日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.mobile.bean;

/**
 * @author Shicx
 */
public class AppRegisterInfo {

    private String account;
    private String deviceId;
    //设备uuid
    private String deviceUuid;
    //设备操作系统
    private String deviceOs;
    //设备操作系统版本
    private String deviceOsVersion;
    //安装的app版本
    private String appVersion;
    //短信验证码
    private String smsCode;
    //手机
    private String mobile;
    //推送token
    private String pushToken;
    //热更新版本
    private String 	chcpVersion;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

