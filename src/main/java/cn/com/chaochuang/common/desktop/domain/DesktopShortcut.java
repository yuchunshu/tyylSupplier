/**
 *
 */
package cn.com.chaochuang.common.desktop.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;

/**
 * @author hzr 2016年7月21日 用户个人桌面自定义功能快捷方式 当userId=0，表示是默认的设置，也就是当用户没有设置个人桌面时调用userId=0的记录来显示。
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
@Table(name = "sys_desktop_shortcut")
public class DesktopShortcut extends LongIdEntity {

    private static final long serialVersionUID = 1044044465319886655L;

    /** 用户ID */
    private Long              userId;

    /** 标题 */
    private String            title;

    /** 链接 */
    private String            url;
    /** 图标 */
    private String            icon;

    /** app链接 */
    private String            appurl;
    /** app图标 */
    private String            appicon;
    /**app图标背景颜色*/
    private String            appcolor;

    /** 排序号 */
    private Long              sort;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppurl() {
		return appurl;
	}

	public void setAppurl(String appurl) {
		this.appurl = appurl;
	}

	public String getAppicon() {
		return appicon;
	}

	public void setAppicon(String appicon) {
		this.appicon = appicon;
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
