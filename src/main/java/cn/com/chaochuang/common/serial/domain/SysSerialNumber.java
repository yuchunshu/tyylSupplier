/**
 *
 */
package cn.com.chaochuang.common.serial.domain;

import javax.persistence.Entity;
import cn.com.chaochuang.common.data.domain.StringUuidEntity;

/**
 * @author hzr 2017年10月16日
 * 通用序列生成
 */
@Entity
public class SysSerialNumber extends StringUuidEntity {

	private static final long serialVersionUID = -8654865074000254572L;

	/**实体类名称*/
	private String		ownerName;

	/**前缀*/
	private String		prefix;

	/**当前顺序号*/
	private Long		currentNum;
	
	public SysSerialNumber(){
		super();
	}
	
	public SysSerialNumber(String ownerName, String prefix, Long currentNum){
		super();
		this.ownerName = ownerName;
		this.prefix = prefix;
		this.currentNum = currentNum;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Long getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(Long currentNum) {
		this.currentNum = currentNum;
	}


}
