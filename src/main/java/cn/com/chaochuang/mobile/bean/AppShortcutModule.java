package cn.com.chaochuang.mobile.bean;

import java.util.List;

/**
 * @author hzr 2017年8月17日
 *
 */
public class AppShortcutModule {

    /** 模块名称 */
    private String                 module;

    /** 链接 */
    private List<AppShortcut>      shorcuts;


	public AppShortcutModule() {
		super();
	}

	public AppShortcutModule(String module, List<AppShortcut> shorcuts) {
		super();
		this.module = module;
		this.shorcuts = shorcuts;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public List<AppShortcut> getShorcuts() {
		return shorcuts;
	}

	public void setShorcuts(List<AppShortcut> shorcuts) {
		this.shorcuts = shorcuts;
	}


}
