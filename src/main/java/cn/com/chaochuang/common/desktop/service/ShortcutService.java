/*
 * FileName:    ShortcutService.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年7月21日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.desktop.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.desktop.bean.ShortcutInfo;
import cn.com.chaochuang.common.desktop.domain.DesktopShortcut;
import cn.com.chaochuang.common.user.domain.SysUser;

/**
 * @author LJX
 *
 */
public interface ShortcutService extends CrudRestService<DesktopShortcut, Long> {

    /**
     * 当前用户快捷菜单
     *
     * @return
     */
    List<DesktopShortcut> findShortcuts(Long userId);

    public boolean saveShortcut(ShortcutInfo info);

}
