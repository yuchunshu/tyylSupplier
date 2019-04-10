/*
 * FileName:    SysApkInfoService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年08月29日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.mobile.service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.reference.IsValid;
import cn.com.chaochuang.common.upload.support.UploadFileInfoPersistence;
import cn.com.chaochuang.common.upload.support.UploadFileItem;
import cn.com.chaochuang.common.util.AttachUtils;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.mobile.bean.AppResponseInfo;
import cn.com.chaochuang.mobile.domain.SysApkInfo;
import cn.com.chaochuang.mobile.reference.AppPlatform;
import cn.com.chaochuang.mobile.repository.SysApkInfoRepository;
import cn.com.chaochuang.mobile.util.MobileTool;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.Date;

/**
 * @author Shicx
 */
@Service
@Transactional
public class SysApkInfoServiceImpl extends SimpleUuidCrudRestService<SysApkInfo> implements SysApkInfoService,UploadFileInfoPersistence {

    //文件根路径
    @Value(value = "${upload.rootpath}")
    private String           rootPath;
    //apk文件存放目录
    @Value(value = "${upload.appid.apkfile}")
    private String           appId;

    @Autowired
    private SysApkInfoRepository repository;

    @Override
    public SimpleDomainRepository<SysApkInfo, String> getRepository() {
        return repository;
    }

    @Override
    public String getAppId() {
        return appId;
    }

    @Override
    public String saveUploadFileInfo(UploadFileItem fileItem) {
        AppResponseInfo info;

        if(fileItem.getTrueName().toLowerCase().contains(".zip")){
            //如果是压缩文件，则解压缩
            info = this.unzipHotCodeFile(fileItem);
        }else{
            //保存apk信息
            info = this.saveApkInfo(fileItem);
        }

        return JSONObject.toJSONString(info);
    }

    private AppResponseInfo saveApkInfo(UploadFileItem fileItem){
        AppResponseInfo info;
        SysApkInfo apkInfo = new SysApkInfo();
        apkInfo.setTrueName(fileItem.getTrueName());
        apkInfo.setSaveName(fileItem.getSaveName());
        apkInfo.setFilePath(fileItem.getSavePath());
        apkInfo.setFileSize(fileItem.getFileSize());

        String fullPath = rootPath+"/"+apkInfo.getFilePath()+apkInfo.getSaveName();
        // 获取上传文件的MD5码
        String md5 = AttachUtils.getFileMD5(fullPath);
        if (Tools.isEmptyString(md5)) {
            AttachUtils.removeFile(apkInfo.getFilePath());
            info=new AppResponseInfo("0","无效的APK文件",null);
            return info;
        }
        apkInfo.setMdfCode(md5);
        // 仅比较MD5码的后8位
        md5 = md5.substring(md5.length() - 8);
        // 对文件的名字进行分解，文件名组成：安装包名_{版本号_MD5后8位}.apk
        int st = apkInfo.getTrueName().indexOf("{");
        int ed = apkInfo.getTrueName().indexOf("}");
        if (st <= 0 || ed <= 0) {
            AttachUtils.removeFile(fullPath);
            info=new AppResponseInfo("0","无效的APK文件命名",null);
            return info;
        }
        String[] fileNames = apkInfo.getTrueName().substring(st + 1, ed).split("_");
        if (fileNames.length != 2) {
            AttachUtils.removeFile(fullPath);
            info=new AppResponseInfo("0","无效的APK文件命名",null);
            return info;
        }
        // 对符合规范的文件进行MD5校验，若MD5码与文件计算的MD5不一致则为非法文件
        if (!md5.equalsIgnoreCase(fileNames[1])) {
            AttachUtils.removeFile(fullPath);
            info=new AppResponseInfo("0","上传的APK文件MD5校验值不一致",null);
            return info;
        }

        apkInfo.setPackageName(apkInfo.getTrueName().substring(0, apkInfo.getTrueName().indexOf("_")));
        //版本号
        apkInfo.setAppVersion(fileNames[0]);
        //上传时默认无效，保存后才设置是否有效
        apkInfo.setStatus(IsValid.无效);
        //上传时间
        apkInfo.setUploadTime(new Date());
        this.repository.saveAndFlush(apkInfo);
        return new AppResponseInfo("1","保存成功",apkInfo);
    }

    /**
     * 解压缩文件
     * @param fileItem
     * @return
     */
    private AppResponseInfo unzipHotCodeFile(UploadFileItem fileItem){
        //获取classes路径
        String classesPath=this.getClass().getClassLoader().getResource("/").getPath();
        String mobileFolderPath = classesPath.replace("WEB-INF/classes/","mobile/");
        File mobileFolder = new File(mobileFolderPath);
        if(!mobileFolder.exists()){
            mobileFolder.mkdirs();
        }

        File zipFile = new File(this.rootPath+"/"+fileItem.getSavePath()+"/"+fileItem.getSaveName());
        String folderName = fileItem.getTrueName().substring(0,fileItem.getTrueName().lastIndexOf("."));
        MobileTool.unzip(zipFile,mobileFolder,folderName);

        return new AppResponseInfo("1","上传成功",null);
    }

    @Override
    public UploadFileItem getUploadFileInfo(String id) {
        if (null != id) {
            SysApkInfo apkInfo = getRepository().findOne(id);
            if (null != apkInfo) {
                UploadFileItem fileItem = new UploadFileItem();

                fileItem.setTrueName(apkInfo.getTrueName());
                fileItem.setSaveName(apkInfo.getSaveName());
                fileItem.setSavePath(apkInfo.getFilePath());
                fileItem.setFileSize(apkInfo.getFileSize());

                return fileItem;
            }
        }
        return null;
    }

    @Override
    public String updateApkInfo(SysApkInfo apkInfo) {
        SysApkInfo sysApkInfo = null;
        //id不为空
        if(StringUtils.isNotEmpty(apkInfo.getId())){
            sysApkInfo = this.repository.findOne(apkInfo.getId());
        }
        if(sysApkInfo==null){
            //新增
            sysApkInfo = apkInfo;
            sysApkInfo.setUploadTime(new Date());
        }else{
            sysApkInfo.setPackageScript(apkInfo.getPackageScript());
            sysApkInfo.setUpdateFlag(apkInfo.getUpdateFlag());
            sysApkInfo.setStatus(apkInfo.getStatus());
            sysApkInfo.setAppPlatform(apkInfo.getAppPlatform());
            sysApkInfo.setAppVersion(apkInfo.getAppVersion());
        }
        this.repository.saveAndFlush(sysApkInfo);
        return sysApkInfo.getId();
    }

    @Override
    public boolean deleteApkInfo(SysApkInfo apkInfo) {
        if(apkInfo!=null&&apkInfo.getId()!=null){
            SysApkInfo sysApkInfo = this.repository.findOne(apkInfo.getId());
            if(sysApkInfo!=null){
                String fullPath = rootPath+"/"+apkInfo.getFilePath()+apkInfo.getSaveName();
                AttachUtils.removeFile(fullPath);
                this.repository.delete(apkInfo);
                return true;
            }
        }
        return false;
    }

    @Override
    public SysApkInfo findLastApkInfoByPlatform(AppPlatform platform) {
        if(platform!=null) {
            Page<SysApkInfo> page = this.repository.findByStatusAndAppPlatformOrderByUploadTimeDesc(IsValid.有效, platform, new PageRequest(0, 1));
            if (Tools.isNotEmptyList(page.getContent())) {
                SysApkInfo apkInfo = page.getContent().get(0);
                return apkInfo;
            }
        }
        return null;
    }
}

