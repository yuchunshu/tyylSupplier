package cn.com.chaochuang.mobile.web;

import cn.com.chaochuang.common.desktop.domain.DesktopShortcut;
import cn.com.chaochuang.common.desktop.service.ShortcutService;
import cn.com.chaochuang.common.syspower.domain.SysPower;
import cn.com.chaochuang.common.syspower.service.SysPowerService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.mobile.bean.AppResponseInfo;
import cn.com.chaochuang.mobile.bean.AppShortcut;
import cn.com.chaochuang.mobile.bean.AppShortcutModule;
import cn.com.chaochuang.mobile.util.AesTool;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hzr 2017.7.18
 * 移动端桌面接口
 */

@Controller
@RequestMapping(value = "/mobile/desktop")
public class MobileDesktopController extends MobileController {

    @Autowired
    private SysPowerService powerService;

    @Autowired
    private ShortcutService shortcutService;


    /** 取得我的移动端桌面自定义图标（常用功能）*/
	@RequestMapping(value = {"/myshortcuts.mo"})
    @ResponseBody
    public AppResponseInfo myShortcutsJson(HttpServletRequest request, Boolean aesFlag_) {
    	SysUser user = this.loadCurrentUser(request);
    	if (user == null) {
    		return AesTool.responseData("0", "用户不存在！", null,aesFlag_);
    	}

    	List<DesktopShortcut> dslist = this.shortcutService.findShortcuts(user.getId());
    	List<AppShortcut> list = new ArrayList();
    	for(DesktopShortcut ds: dslist) {
    		if (StringUtils.isNotBlank(ds.getAppurl())) {
				AppShortcut shortcut = new AppShortcut(ds.getTitle(), ds.getAppurl(), ds.getAppicon());
				shortcut.setShortcutId(ds.getId());
				shortcut.setSort(ds.getSort());
				shortcut.setAppcolor(ds.getAppcolor());
				list.add(shortcut);
    		}
    	}
    	return AesTool.responseData(list,aesFlag_);
    }


    /** 移动端所有桌面功能列表*/
	@RequestMapping(value = {"/shortcutlist.mo"})
    @ResponseBody
    public AppResponseInfo shortcutsListJson(HttpServletRequest request, Boolean aesFlag_) {
    	SysUser user = this.loadCurrentUser(request);
    	if (user == null) {
    		return AesTool.responseData("0", "用户不存在！", null,aesFlag_);
    	}

    	List<AppShortcutModule> list = new ArrayList();
    	List<SysPower> plist = this.powerService.selectAllAppMenus(user.getId());
    	List<AppShortcut> cutList;
    	Map<String,List<AppShortcut>> tempMap = Maps.newLinkedHashMap();
    	AppShortcutModule obj;
    	for(SysPower sp: plist) {
			cutList = tempMap.get(sp.getModule());
    		if (cutList==null) {
            	cutList = new ArrayList();
        		obj = new AppShortcutModule(sp.getModule(), cutList);
        		list.add(obj);
    		}
			AppShortcut shortcut = new AppShortcut(sp.getPowerName(), sp.getAppurl(), sp.getAppicon());
			shortcut.setPowerId(sp.getId());
			shortcut.setAppcolor(sp.getAppcolor());
			cutList.add(shortcut);
			tempMap.put(sp.getModule(),cutList);
    	}

    	return AesTool.responseData(list,aesFlag_);
    }

	/**
	 * 移动端桌面编辑,获取用户的所有功能数据
	 * */
	@RequestMapping(value = {"/listmenu.mo"})
	@ResponseBody
	public AppResponseInfo allMenuData(HttpServletRequest request, Boolean aesFlag_) {
		SysUser user = this.loadCurrentUser(request);
		if (user == null) {
			return AesTool.responseData("0", "用户不存在！", null,aesFlag_);
		}

		List<SysPower> plist = this.powerService.selectAllAppMenus(user.getId());
		List<AppShortcut> shortcutList = Lists.newArrayList();
		for(SysPower sp: plist) {
			if (StringUtils.isNotBlank(sp.getAppurl())) {
				AppShortcut shortcut = new AppShortcut(sp.getPowerName(), sp.getAppurl(), sp.getAppicon());
				shortcut.setPowerId(sp.getId());
				shortcut.setAppcolor(sp.getAppcolor());
				shortcutList.add(shortcut);
			}
		}

		return AesTool.responseData(shortcutList,aesFlag_);
	}

	/**
	 * 移动端桌面数据保存
	 * */
	@RequestMapping(value = {"/savemenu.mo"})
	@ResponseBody
	public AppResponseInfo saveDesktopMenu(String editJson, String deleteIds, HttpServletRequest request, Boolean aesFlag_){
		try {
			SysUser user = this.loadCurrentUser(request);
			if (user == null) {
				return AesTool.responseData("0", "用户不存在！", null,aesFlag_);
			}
			List<AppShortcut> shortcutList = JSONArray.parseArray(editJson,AppShortcut.class);
			shortcutService.saveShortcutForMobile(shortcutList,deleteIds,user);
			return AesTool.responseData("1", "", null, aesFlag_);
		}catch (Exception e){
			e.printStackTrace();
			return AesTool.responseData("0", "服务器连接失败", null, aesFlag_);
		}
	}

}
