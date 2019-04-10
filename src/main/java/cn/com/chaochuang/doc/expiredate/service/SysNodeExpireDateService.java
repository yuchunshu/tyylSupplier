package cn.com.chaochuang.doc.expiredate.service;




import java.util.Map;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.expiredate.bean.SysNodeExpireDateEditBean;
import cn.com.chaochuang.doc.expiredate.domain.SysNodeExpireDate;

/**
 * @author yuchunshu
 *
 */

public interface SysNodeExpireDateService extends CrudRestService<SysNodeExpireDate, String> {

	/** 
	 * @Title: saveSysNodeExpireDate 
	 * @Description: 保存环节限办时间设置
	 * @param bean
	 * @return
	 * @return: String
	 */
	public String saveSysNodeExpireDate(SysNodeExpireDateEditBean bean);
	
	/** 
	 * @Title: getSysNodeExpireDateMap 
	 * @Description: 获取环节限办时间设置Map
	 * @param deadlineType
	 * @return
	 * @return: Map<String,SysNodeExpireDate>
	 */
	Map<String, SysNodeExpireDate> getSysNodeExpireDateMap(String deadlineType);
	
	/** 
	 * @Title: delSysNodeExpireDate 
	 * @Description: 删除环节限办时间设置
	 * @param id
	 * @return
	 * @return: boolean
	 */
	public boolean delSysNodeExpireDate(String id);
	
	/** 
	 * @Title: repeatInfoCheck 
	 * @Description: 检查数据重复性
	 * @param documentType
	 * @param deadlineType
	 * @param nodeId
	 * @param id
	 * @return
	 * @return: boolean
	 */
	public boolean repeatInfoCheck(String documentType,String deadlineType,String nodeId,String id);
	
	
	/** 
	 * @Title: getExpireDay 
	 * @Description: 获取总限办时间
	 * @param documentType
	 * @param deadlineType
	 * @return
	 * @return: Integer
	 */
	public Integer getExpireDay(String documentType,String deadlineType);
	
	/** 
	 * @Title: getRemainingTime 
	 * @Description: 获取剩余时间
	 * @param documentType
	 * @param deadlineType
	 * @param id
	 * @return
	 * @return: Integer
	 */
	public Integer getRemainingTime(String documentType,String deadlineType,String id);

}