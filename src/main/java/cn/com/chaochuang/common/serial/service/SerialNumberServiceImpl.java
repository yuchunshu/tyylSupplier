package cn.com.chaochuang.common.serial.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.serial.domain.SysSerialNumber;
import cn.com.chaochuang.common.serial.repository.SerialNumberRepository;

/**
 * @author hzr 2017年10月16日
 *
 */
@Service
@Transactional
public class SerialNumberServiceImpl extends SimpleUuidCrudRestService<SysSerialNumber> implements SerialNumberService {

    @Autowired
    private SerialNumberRepository 	    repository;

	@Override
	public SimpleDomainRepository<SysSerialNumber, String> getRepository() {
		return this.repository;
	}

	@Override
	public String getNewSN(String ownerName, String prefix, String format) {
		int numberSize = format.length();
		String SNumber = "";//新建默认系列号 "0001\001\01.."
		for(int i = 0; i<numberSize; i++){
			if(i == numberSize - 1){
				SNumber += "1";
			}else{
				SNumber += "0";
			}
			if(SNumber.length() >= numberSize){
				break;
			}
		}
		SysSerialNumber serialNumber = this.repository.findByOwnerNameAndPrefix(ownerName, prefix);
		if(serialNumber == null){
			serialNumber = new SysSerialNumber(ownerName, prefix, Long.valueOf(SNumber));
			this.repository.save(serialNumber);
			return prefix + SNumber;//数据库无数据，返回新建第一个系列号,例如:"N0001"
		}
		Long nextNumber = serialNumber.getCurrentNum() + 1;
		
		//编号累加操作在保存后再执行
//		serialNumber.setCurrentNum(Long.valueOf(nextNumber));
//		this.repository.save(serialNumber);
		
		//补全"0"
		String nextStr = String.valueOf(nextNumber);
		for(int i = 0; i<numberSize; i++){
			nextStr = "0" + nextStr;
			if(nextStr.length() >= numberSize){
				break;
			}
		}
		
		return prefix + nextStr;
	}
	
	@Override
	public void saveNewSN(String ownerName, String prefix, String format) {
		int numberSize = format.length();
		String SNumber = "";//新建默认系列号 "0001\001\01.."
		for(int i = 0; i<numberSize; i++){
			if(i == numberSize - 1){
				SNumber += "1";
			}else{
				SNumber += "0";
			}
			if(SNumber.length() >= numberSize){
				break;
			}
		}
		SysSerialNumber serialNumber = this.repository.findByOwnerNameAndPrefix(ownerName, prefix);
		if(serialNumber == null){
			serialNumber = new SysSerialNumber(ownerName, prefix, Long.valueOf(SNumber));
			this.repository.save(serialNumber);
		}
		Long nextNumber = serialNumber.getCurrentNum() + 1;
		
		//编号累加操作在保存后再执行
		serialNumber.setCurrentNum(Long.valueOf(nextNumber));
		this.repository.save(serialNumber);
		
	}

	@Override
	public String getArchNoSN(String ownerName, String prefix, String format) {
		int numberSize = format.length();
		String SNumber = "";//新建默认系列号 "0001\001\01.."
		for(int i = 0; i<numberSize; i++){
			if(i == numberSize - 1){
				SNumber += "1";
			}else{
				SNumber += "0";
			}
			if(SNumber.length() >= numberSize){
				break;
			}
		}
		SysSerialNumber serialNumber = this.repository.findByOwnerNameAndPrefix(ownerName, prefix);
		if(serialNumber == null){
			serialNumber = new SysSerialNumber(ownerName, prefix, Long.valueOf(SNumber));
			this.repository.save(serialNumber);
			return prefix + SNumber;//数据库无数据，返回新建第一个系列号,例如:"N0001"
		}
		Long nextNumber = serialNumber.getCurrentNum() + 1;
		
		//编号累加操作在保存后再执行
		serialNumber.setCurrentNum(Long.valueOf(nextNumber));
		this.repository.save(serialNumber);
		
		//补全"0"
		String nextStr = String.valueOf(nextNumber);
		for(int i = 0; i<numberSize; i++){
			nextStr = "0" + nextStr;
			if(nextStr.length() >= numberSize){
				break;
			}
		}
		
		return prefix + nextStr;
	}
}
