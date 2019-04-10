package cn.com.chaochuang.common.serial.service;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.serial.domain.SysSerialNumber;

/**
 * @author hzr 2017年10月16日
 *
 */
public interface SerialNumberService extends CrudRestService<SysSerialNumber, String> {

	/**
	 * 比如：prefix="ABC"，format="0000"，首次返回值：ABC0001
	 * 
	 * */
	String getNewSN(String ownerName, String prefix, String format);
	
	/**
	 * 比如：prefix="ABC"，format="0000"，首次返回值：ABC0001
	 * 
	 * */
	void saveNewSN(String ownerName, String prefix, String format);
	
	/**
	 * 案卷号自增长
	 * @param ownerName
	 * @param prefix
	 * @param format
	 * @return
	 */
	String getArchNoSN(String ownerName, String prefix, String format);
}
