package cn.com.chaochuang.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import cn.com.chaochuang.common.syspower.domain.SysRoleModule;
/**
 *
 * @author RDX 2017年9月25日
 * 权限模块相关工具
 *
 */
public class ModuleUtils {

	/** 已选操作中英文键值对("CRE", "添加")... */
	private static final Map<String, String> operateMap = new HashMap<String, String>();

	/** 规则URL键值对("CRE", "/new,/add")... */
	private static final Map<String, String> urlMap = new HashMap<String, String>();

	static{
		operateMap.put("CRE", "添加");
		operateMap.put("UPD", "修改");
		operateMap.put("DEL", "删除");
		operateMap.put("RET", "查询");
		operateMap.put("STA", "统计");
		operateMap.put("DEA", "办理");
		operateMap.put("AUD", "审核");
		operateMap.put("PRN", "打印");
		operateMap.put("EXP", "导出");
		operateMap.put("IMP", "导入");

		urlMap.put("CRE", "/new,/add,/insert,/save,/send");             //添加
		urlMap.put("UPD", "/edit,/update");                             //修改
		urlMap.put("DEL", "/delete");                                   //删除
		urlMap.put("RET", "/select,/query,/search,/list,/get,/detail"); //查询
		urlMap.put("STA", "/stat");                                     //统计
		urlMap.put("DEA", "/deal,/done");                               //办理
		urlMap.put("AUD", "/audit,/check");                             //审核
		urlMap.put("PRN", "/print");                                    //打印
		urlMap.put("EXP", "/export");                                   //导出
		urlMap.put("IMP", "/import");                                   //导入
	}

	/**
	 * 根据已选操作，返回所有规则url规则集合
	 * @param operates
	 * @return
	 */
	public static List<String> getURLs(String operates){
		List<String> urlList = new ArrayList<String>();
		String[] opArr = operates.split(",");
		for(String op:opArr){
			splitURL(urlMap.get(op), urlList);
		}
		return urlList;
	}

	/**
	 * 分解单个规则url,放入list
	 */
	private static List<String> splitURL(String URLs, List<String> list){
		if(StringUtils.isNotBlank(URLs)){
			String[] urlArr = URLs.split(",");
			for(String url:urlArr){
				list.add(url);
			}
		}
		return list;
	}


	/**
	 * 匹配url规则
	 */
	public static boolean matchURL(String url, List<SysRoleModule> roleModules, Long moduleId){
		List<String> urlList = null;
		for(SysRoleModule rm:roleModules){
			if(rm.getModuleId().equals(moduleId)){
				urlList = getURLs(rm.getOperates());
			}
		}
		if(urlList != null){
			for(String u:urlList){
				if(url.indexOf(u) >= 0){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 根据URL匹配属性
	 */
	public static String matchOperate(String url){
		for(String key:urlMap.keySet()){
			String[] urls = urlMap.get(key).split(",");
			for(String u:urls){
				if(url.indexOf(u) >= 0){
					return key;
				}
			}
		}
		return null;
	}

	public static String translateOperate(String operate){
		return operateMap.get(operate);
	}

	public static Map<String, String> getOperateMap(){
		return operateMap;
	}

	/**
	 * 根据operates判断URL权限
	 */
	public static boolean validateUrloperatesPower(String url,String operates){
		if(StringUtils.isBlank(url) || StringUtils.isBlank(operates)){
			return false;
		}
		List<String> urls = getURLs(operates);
		for(String u:urls){
			if(url.indexOf(u) >= 0){
				return true;
			}
		}
		return false;
	}


}
