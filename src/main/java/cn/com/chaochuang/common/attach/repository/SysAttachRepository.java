/*
 * FileName:    SysAttachRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月26日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.attach.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.attach.domain.SysAttach;

/**
 * @author HM
 *
 */
public interface SysAttachRepository extends SimpleDomainRepository<SysAttach, String> {

    List<SysAttach> findByOwnerIdAndOwnerType(String ownerId, String ownerType);

    List<SysAttach> findByOwnerIdAndOwnerTypeIn(String ownerId, String[] ownerType);
    
    List<SysAttach> findByOwnerId(String ownerId);

    List<SysAttach> findByOwnerIdAndSaveName(String ownerId,String saveName);


    //
    List<SysAttach> findByOwnerIdAndOwnerTypeAndAttachType(String ownerId, String ownerType, String attachType);

    int deleteByOwnerIdAndOwnerType(String ownerId, String ownerType);
}
