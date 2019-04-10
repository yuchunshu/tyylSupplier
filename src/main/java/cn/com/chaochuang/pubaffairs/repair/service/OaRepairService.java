package cn.com.chaochuang.pubaffairs.repair.service;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.common.data.persistence.SearchFilter;
import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.pubaffairs.repair.bean.OaRepairEditBean;
import cn.com.chaochuang.pubaffairs.repair.domain.OaEquipmentRepair;

/**
 * @author dengl 2018.08.10
 *
 */
public interface OaRepairService extends CrudRestService<OaEquipmentRepair, String>{

	public void save(OaEquipmentRepair obj);
	
	/** 保存故障报告申请*/
	ReturnBean saveRepair(OaRepairEditBean bean,HttpServletRequest request);
	
	/** 非启动环节，更新故障报告申请信息*/
	public ReturnBean updateRepairInfo(OaRepairEditBean bean,HttpServletRequest request);
	
	/** 办结 */
    public void finishTheRepair(String id);
    
    /** 删除故障报告申请信息*/
    public boolean delRepair(String id, boolean force);
    
    /** 故障报告申请查询 */
    public Map<String, Object> repairSelect(Collection<SearchFilter> searchFilters,SysUser user,Integer page, Integer rows);
    
    /**批量删除暂存状态故障报告申请*/
    void delRepairByIds(String ids);
}
