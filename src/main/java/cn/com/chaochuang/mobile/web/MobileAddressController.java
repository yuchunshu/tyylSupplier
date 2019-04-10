package cn.com.chaochuang.mobile.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.chaochuang.mobile.util.AesTool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.chaochuang.common.bean.Page;
import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.domain.ValidAble;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.user.bean.DepartmentShowBean;
import cn.com.chaochuang.common.user.bean.UserShowBean;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.SearchListHelper;
import cn.com.chaochuang.mobile.bean.AppResponseInfo;

/**
 *
 * @category 移动通讯录接口
 * @author ZhongLi
 * @date 2017-08-14 09:08
 *
 */
@Controller
@RequestMapping(value = "/mobile/address")
public class MobileAddressController extends MobileController {

    @Autowired
    private ConversionService    	 conversionService;

    /**
     * 1、全部部门列表
     */
    @RequestMapping(value = {"/addresslist.mo"})
    @ResponseBody
    public AppResponseInfo addresslist(HttpServletRequest request, Boolean aesFlag_) {
        SysUser currentUser = this.loadCurrentUser(request);
        if (currentUser == null) {
            return AesTool.responseData("0", "用户不存在！", null,aesFlag_);
        }
    	return addresslistjson(request,aesFlag_);
    }

    private AppResponseInfo addresslistjson(HttpServletRequest request, Boolean aesFlag_) {
    	List<SysDepartment>  sysDeptartments =  new ArrayList<SysDepartment>();
    	// 父级菜单id
    	String parentId = request.getParameter("parentId");
    	if(StringUtils.isEmpty(parentId)){
    		/**
    		 * 所有菜单
    		 */
    		sysDeptartments = this.deptService.findAllValid();
    	}else{

    	    /**
    	     * 按部门读取全部（子菜单）
    	     */
    		sysDeptartments = this.deptService.getSubDeps(Long.valueOf(parentId));
    	}

    	// 获取是否是父节点内容
    	List<DepartmentShowBean> showBeans = new ArrayList<DepartmentShowBean>();
    	List<DepartmentShowBean> beans = BeanCopyBuilder.buildList(sysDeptartments, DepartmentShowBean.class);
    	for(DepartmentShowBean bean:beans){
    		List list = this.deptService.getSubDeps(bean.getId());
    		bean.setHasChild((list != null && list.size() >= 1));
    		showBeans.add(bean);
    	}

        Page p = new Page();
        p.setRows(showBeans);
        p.setTotal(Long.valueOf(sysDeptartments.size()));
        return AesTool.responseData(p,aesFlag_);
    }

    /**
     * 2、按部门id读取用户列表
     */
    @RequestMapping(value = {"/getAddressByDeptId.mo"})
    @ResponseBody
    public AppResponseInfo getAddressByDeptId(HttpServletRequest request, Boolean aesFlag_) {
    	return getAddressByDeptIdJson(request,aesFlag_);
    }

    public AppResponseInfo getAddressByDeptIdJson(HttpServletRequest request, Boolean aesFlag_) {
        String pageStr = request.getParameter("page");
        String rowsStr = request.getParameter("rows");

        int page=1,rows=25;
        if(StringUtils.isNotBlank(pageStr)){
            page = Integer.valueOf(pageStr);
        }
        if(StringUtils.isNotBlank(rowsStr)){
            rows = Integer.valueOf(rowsStr);
        }

    	// 部门id
    	String deptId = request.getParameter("deptId");

        SearchBuilder<SysUser, Long> searchBuilder = new SearchBuilder<SysUser, Long>(conversionService);
        searchBuilder.clearSearchBuilder().findSearchParam(request);
        if(StringUtils.isNotBlank(deptId)) {
            searchBuilder.getFilterBuilder().equal("department.id", Long.valueOf(deptId));
        }
        searchBuilder.getFilterBuilder().equal("valid", ValidAble.VALID);
        searchBuilder.appendSort(Sort.Direction.ASC, "sort");
        SearchListHelper<SysUser> listhelper = new SearchListHelper<SysUser>();
        listhelper.execute(searchBuilder, userService.getRepository(), page, rows);

        Page p = new Page();
        p.setRows(BeanCopyBuilder.buildList(listhelper.getList(), UserShowBean.class));
        p.setTotal(listhelper.getCount());
        return AesTool.responseData(p,aesFlag_);
    }

    /**
     * 3、按id读取通讯录用户通讯录详情
     */
    @RequestMapping(value = {"/detail.mo"})
    @ResponseBody
    public AppResponseInfo getAddressByUserId(HttpServletRequest request, Boolean aesFlag_) {
    	return getAddressByUserIdJson(request,aesFlag_);
    }

    public AppResponseInfo getAddressByUserIdJson(HttpServletRequest request, Boolean aesFlag_) {
    	SysUser sysUser = new SysUser();
    	String userId = request.getParameter("userId");
    	if(StringUtils.isNotEmpty(userId)){
        	sysUser = this.userService.findOne(Long.valueOf(userId));
    	}
    	UserShowBean userBean = BeanCopyBuilder.buildObject(sysUser, UserShowBean.class);

        return AesTool.responseData(userBean,aesFlag_);
    }

}
