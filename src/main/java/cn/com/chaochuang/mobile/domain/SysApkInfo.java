package cn.com.chaochuang.mobile.domain;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.reference.IsValid;
import cn.com.chaochuang.common.reference.IsValidConverter;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.mobile.reference.AppPlatform;
import cn.com.chaochuang.mobile.reference.AppPlatformConverter;
import cn.com.chaochuang.mobile.reference.AppUpdateFlag;
import cn.com.chaochuang.mobile.reference.AppUpdateFlagConverter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Shicx
 * 2017-9-1
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "apk_info_id")) })
public class SysApkInfo extends StringUuidEntity {

    //包名
    private String 	packageName;
    //文件真实名
    private String 	trueName;
    //保存文件名
    private String 	saveName;
    //保存路径
    private String 	filePath;
    //文件大小
    private Long   	fileSize;
    //版本号
    private String 	appVersion;
    //更新状态
    @Convert(converter = AppUpdateFlagConverter.class)
    private AppUpdateFlag updateFlag;
    //apk描述
    private String 	packageScript;
    //文件md5值
    private String 	mdfCode;
    //apk状态
    @Convert(converter = IsValidConverter.class)
    private IsValid status;
    //上传时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date   	uploadTime;
    //所属平台
    @Convert(converter = AppPlatformConverter.class)
    private AppPlatform appPlatform;
    @Transient
    private String 	uploadTimeShow;
    @Transient
    private String 	updateFlagKey;
    //旧的版本号，字段名称已改成appVersion
    @Transient
    private String 	version;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public AppUpdateFlag getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(AppUpdateFlag updateFlag) {
        this.updateFlag = updateFlag;
    }

    public String getPackageScript() {
        return packageScript;
    }

    public void setPackageScript(String packageScript) {
        this.packageScript = packageScript;
    }

    public String getMdfCode() {
        return mdfCode;
    }

    public void setMdfCode(String mdfCode) {
        this.mdfCode = mdfCode;
    }

    public IsValid getStatus() {
        return status;
    }

    public void setStatus(IsValid status) {
        this.status = status;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getUploadTimeShow() {
        if(uploadTime!=null){
            return Tools.DATE_MINUTE_FORMAT.format(uploadTime);
        }
        return null;
    }

    public String getUpdateFlagKey() {
        if(updateFlag!=null){
            return updateFlag.getKey();
        }
        return null;
    }

    public AppPlatform getAppPlatform() {
        return appPlatform;
    }

    public void setAppPlatform(AppPlatform appPlatform) {
        this.appPlatform = appPlatform;
    }

    public String getVersion() {
        return this.appVersion;
    }
}
