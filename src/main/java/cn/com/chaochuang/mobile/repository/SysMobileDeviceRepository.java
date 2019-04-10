/*
 * FileName:    SysMobileDeviceRepository.java
 * Description: ${DESCRIPTION}
 * Company:     南宁超创信息工程有限公司
 * Copyright:    ChaoChuang (c) 2017
 * History:     2017年09月13日 (cookie) 1.0 Create
 */
package cn.com.chaochuang.mobile.repository;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.reference.IsValid;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.mobile.domain.SysMobileDevice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author cookie
 */
public interface SysMobileDeviceRepository extends SimpleDomainRepository<SysMobileDevice, String> {

    /**
     * 通过用户和设备uuid,查询用户的设备信息
     * @param user
     * @param uuid
     * @param valid
     * @return
     */
    List<SysMobileDevice> findByUserAndDeviceUuidAndValid(SysUser user, String uuid, IsValid valid);

    /**
     * 通过用户，查询用户的设备信息
     * @param user
     * @param valid
     * @return
     */
    List<SysMobileDevice> findByUserAndValid(SysUser user, IsValid valid);

    /**
     * 通过用户，查询用户的设备信息
     * @param user
     * @return
     */
    List<SysMobileDevice> findByUser(SysUser user);

    /**
     * 查询用户有效的设备信息
     * @param uid
     * @param ukey
     * @param valid
     * @return
     */
    @Query("select device from SysMobileDevice device where device.user.id =:uid and device.ukey=:ukey and device.valid=:valid")
    List<SysMobileDevice> findByUserIdAndUkeyAndValid(@Param("uid") Long uid, @Param("ukey") String ukey, @Param("valid") IsValid valid);

}
