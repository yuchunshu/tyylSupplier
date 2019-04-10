package cn.com.chaochuang.mobile.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.chaochuang.common.data.domain.ValidAble;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.user.bean.DepartmentShowBean;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.user.service.SysUserService;

/**
 * @author hzr 2017.7.18
 * 移动端接口父类
 */

public class MobileController {

    @Autowired
    protected SysUserService       		userService;

    @Autowired
    protected SysDepartmentService      deptService;

	@Autowired
	protected LogService 			   	logService;


    //大多数的移动端请求都必须把用户ID传过来
    protected SysUser loadCurrentUser(HttpServletRequest request) {
    	//用户ID
    	String uid = request.getParameter("uid");
        if (StringUtils.isBlank(uid)) {
        	return null;
        }
        SysUser user = userService.findOne(new Long(uid));
        if (user == null || !(ValidAble.VALID).equals(user.getValid())) {
        	return null;
        } else {
        	return user;
        }
    }


    //将dept对象转为showBean
    protected DepartmentShowBean deptToShowBean(SysDepartment dept) {
		DepartmentShowBean ds = new DepartmentShowBean();
		ds.setId(dept.getId());
		ds.setDeptName(dept.getDeptName());
		List sublist = this.deptService.getSubDeps(dept.getId());
		ds.setHasChild((sublist != null && sublist.size() >= 1));
		return ds;
    }


    //将日期参数  转换为  具体的日期+时间
    protected Date strToDatetime(String datestr, String timestr) {
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dt = null;
        try {
            if (StringUtils.isNotBlank(datestr)) {
                dt = df.parse(datestr + " " + timestr);
            }
        } catch (ParseException e) {
        }
        return dt;
    }



}
