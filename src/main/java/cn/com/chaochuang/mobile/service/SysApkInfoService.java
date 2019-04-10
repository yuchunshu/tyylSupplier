/*
 * FileName:    SysApkInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年08月29日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.mobile.service;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.mobile.domain.SysApkInfo;
import cn.com.chaochuang.mobile.reference.AppPlatform;

/**
 * @author Shicx
 */
public interface SysApkInfoService extends CrudRestService<SysApkInfo, String> {

    /**
     * 保存apk信息
     * @param apkInfo
     * @return
     */
    String updateApkInfo(SysApkInfo apkInfo);

    boolean deleteApkInfo(SysApkInfo apkInfo);

    /**
     * 查找最新的apk信息
     * @return
     */
    SysApkInfo findLastApkInfoByPlatform(AppPlatform platform);
}

