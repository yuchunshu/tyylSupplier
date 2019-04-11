/**
 *
 */
package cn.com.chaochuang.common.serial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.serial.domain.SysSerialNumber;

/**
 * @author hzr 2017年10月16日
 *
 */
public interface SerialNumberRepository extends SimpleDomainRepository<SysSerialNumber, String> {
	
	SysSerialNumber findByOwnerNameAndPrefix(String ownerName, String prefix);
	
}
