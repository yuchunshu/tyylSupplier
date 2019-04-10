package cn.com.chaochuang.mobile.bean;

/**
 * @author hzr 2017年8月17日
 *
 */
public class AppShortcut {

	/**桌面快捷菜单id*/
	private Long              shortcutId;
	/**权限id*/
	private Long              powerId;
    /** 标题 */
    private String            title;

    /** 链接 */
    private String            url;

    /** 图标 */
    private String            icon;
    /**排序*/
    private Long              sort;
	/**app图标背景颜色*/
	private String            appcolor;

	public AppShortcut() {
	}

	public AppShortcut(String title, String url, String icon) {
		super();
		this.title = title;
		this.url = url;
		this.icon = icon;
	}

	public Long getShortcutId() {
		return shortcutId;
	}

	public void setShortcutId(Long shortcutId) {
		this.shortcutId = shortcutId;
	}

	public Long getPowerId() {
		return powerId;
	}

	public void setPowerId(Long powerId) {
		this.powerId = powerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getAppcolor() {
		return appcolor;
	}

	public void setAppcolor(String appcolor) {
		this.appcolor = appcolor;
	}
}
