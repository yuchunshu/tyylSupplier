/*
 * FileName:    ShortcutInfo.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月22日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.desktop.bean;

import cn.com.chaochuang.common.desktop.domain.DesktopShortcut;

/**
 * @author LJX
 *
 */
public class ShortcutInfo {
    DesktopShortcut[] shortcut;

    /**
     * @return the shortcut
     */
    public DesktopShortcut[] getShortcut() {
        return shortcut;
    }

    /**
     * @param shortcut
     *            the shortcut to set
     */
    public void setShortcut(DesktopShortcut[] shortcut) {
        this.shortcut = shortcut;
    }

}
