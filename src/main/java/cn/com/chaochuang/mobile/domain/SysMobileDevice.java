package cn.com.chaochuang.mobile.domain;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.reference.IsValid;
import cn.com.chaochuang.common.reference.IsValidConverter;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.mobile.reference.AppOnlineFlag;
import cn.com.chaochuang.mobile.reference.AppOnlineFlagConverter;
import cn.com.chaochuang.mobile.reference.RegisterStatus;
import cn.com.chaochuang.mobile.reference.RegisterStatusConverter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Shicx 2017-9-1
 * 用户设备信息表
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "register_id")) })
public class SysMobileDevice extends StringUuidEntity {

    /**用户*/
    @ManyToOne
    @JoinColumn(name = "userId")
    private SysUser user;
    /**密钥*/
    private String 	ukey;
    /**设备uuid*/
    private String 	deviceUuid;
    /**设备操作系统*/
    private String 	deviceOs;
    /**设备操作系统版本*/
    private String 	deviceOsVersion;
    /**安装的app版本*/
    private String 	appVersion;
    /**热更新版本*/
    private String 	chcpVersion;
    /**注册时间*/
    @Temporal(TemporalType.TIMESTAMP)
    private Date 	registerTime;
    /**短信验证码*/
    private String 	smsCode;
    /**注册状态*/
    @Convert(converter = RegisterStatusConverter.class)
    private RegisterStatus registerStatus;
    /**备注*/
    private String 	remark;
    /**短信发送时间*/
    @Temporal(TemporalType.TIMESTAMP)
    private Date   	smsSendTime;
    @Convert(converter = IsValidConverter.class)
    private IsValid valid;
    /**是否在线 0:离线 1：在线*/
    @Convert(converter = AppOnlineFlagConverter.class)
    private AppOnlineFlag onlineFlag;
    /**最近登录时间*/
    @Temporal(TemporalType.TIMESTAMP)
    private Date   	lastLoginTime;
    /**推送token*/
    private String pushToken;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public String getUkey() {
        return ukey;
    }

    public void setUkey(String ukey) {
        this.ukey = ukey;
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

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
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

    public Date getSmsSendTime() {
        return smsSendTime;
    }

    public void setSmsSendTime(Date smsSendTime) {
        this.smsSendTime = smsSendTime;
    }

    public IsValid getValid() {
        return valid;
    }

    public void setValid(IsValid valid) {
        this.valid = valid;
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
