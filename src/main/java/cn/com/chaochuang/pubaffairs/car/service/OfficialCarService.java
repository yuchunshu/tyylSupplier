package cn.com.chaochuang.pubaffairs.car.service;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.common.data.persistence.SearchFilter;
import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.pubaffairs.car.bean.OfficialCarEditBean;
import cn.com.chaochuang.pubaffairs.car.domain.OfficialCar;

/**
 * @author dengl 2018.08.08
 *
 */
public interface OfficialCarService extends CrudRestService<OfficialCar, String>{
	
	public void save(OfficialCar obj);
	
	/** 保存用车申请*/
	ReturnBean saveOfficialCar(OfficialCarEditBean bean,HttpServletRequest request);
	
	/** 非启动环节，更新用车申请信息*/
	public ReturnBean updateOfficialCarInfo(OfficialCarEditBean bean,HttpServletRequest request);
	
	/** 车辆申请查询 */
    public Map<String, Object> carSelect(Collection<SearchFilter> searchFilters,SysUser user,Integer page, Integer rows);
    
    /** 办结 */
    public void finishTheCar(OfficialCarEditBean bean);
    
    /**批量删除暂存状态休假申请*/
    void delOffcialCarByIds(String ids);
    
    public boolean delCar(String id, boolean force);
}
