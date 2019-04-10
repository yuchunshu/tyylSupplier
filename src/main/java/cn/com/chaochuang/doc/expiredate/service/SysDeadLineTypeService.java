package cn.com.chaochuang.doc.expiredate.service;

import java.util.Map;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.expiredate.bean.SysDeadLineTypeEditBean;
import cn.com.chaochuang.doc.expiredate.domain.SysDeadLineType;

/**
 * @author yuchunshu
 *
 */

public interface SysDeadLineTypeService extends CrudRestService<SysDeadLineType, String> {

	/** 
	 * @Title: saveSysDeadLineType 
	 * @Description: 保存限时办结要求初始化设置
	 * @param bean
	 * @return
	 * @return: String
	 */
	public String saveSysDeadLineType(SysDeadLineTypeEditBean bean);
	
	/** 
	 * @Title: getSysDeadLineTypeMap 
	 * @Description: 获取限时办结要求初始化设置Map
	 * @return
	 * @return: Map<String,String>
	 */
	Map<String, String> getSysDeadLineTypeMap();
	
	/** 
	 * @Title: delSysDeadLineType 
	 * @Description: 删除限时办结要求初始化设置
	 * @param id
	 * @return
	 * @return: boolean
	 */
	public boolean delSysDeadLineType(String id);
	
	/** 
	 * @Title: repeatInfoCheck 
	 * @Description: 检查数据重复性
	 * @param deadlineType
	 * @param documentType
	 * @param id
	 * @return
	 * @return: boolean
	 */
	public boolean repeatInfoCheck(String deadlineType,String documentType,String id);
}