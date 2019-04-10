package cn.com.chaochuang.pubaffairs.leave.service;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.common.data.persistence.SearchFilter;
import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.pubaffairs.leave.bean.OaLeaveEditBean;
import cn.com.chaochuang.pubaffairs.leave.domain.OaLeave;

/**
 * @author dengl 2018.08.08
 *
 */

public interface OaLeaveService extends CrudRestService<OaLeave, String>{
	
    boolean delOaLeave(String id);
    
    /** 保存流程假期申请*/
	ReturnBean saveOaLeaveFlow(OaLeaveEditBean bean,HttpServletRequest request);
	
    /** 休假申请查询 */
    public Map<String, Object> leaveSelect(Collection<SearchFilter> searchFilters,SysUser user,Integer page, Integer rows);
    
    /** 非启动环节，更新休假申请信息*/
	public ReturnBean updateLeaveInfo(OaLeaveEditBean bean, HttpServletRequest request);
	
	void finishLeave(String id);
	
	void save(OaLeave obj);
	
    /**批量删除暂存状态休假申请*/
    void delOaleaveByIds(String ids);
    
    public boolean delLeave(String id, boolean force);
}
