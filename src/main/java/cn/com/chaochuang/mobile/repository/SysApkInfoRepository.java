/*
 * FileName:    SysApkInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年08月29日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.mobile.repository;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.reference.IsValid;
import cn.com.chaochuang.mobile.domain.SysApkInfo;
import cn.com.chaochuang.mobile.reference.AppPlatform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Shicx
 */
public interface SysApkInfoRepository extends SimpleDomainRepository<SysApkInfo, String> {

    /**
     * 分页查询apk文件信息,根据上传时间降序排序
     * @param valid
     * @param page
     * @return
     */
    Page<SysApkInfo> findByStatusOrderByUploadTimeDesc(IsValid valid, Pageable page);

    Page<SysApkInfo> findByStatusAndAppPlatformOrderByUploadTimeDesc(IsValid valid, AppPlatform platform, Pageable page);
}
