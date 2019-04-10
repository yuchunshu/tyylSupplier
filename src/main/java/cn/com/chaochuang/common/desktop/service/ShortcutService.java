/*
 * FileName:    ShortcutService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月21日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.desktop.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.desktop.bean.ShortcutInfo;
import cn.com.chaochuang.common.desktop.domain.DesktopShortcut;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.mobile.bean.AppShortcut;

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

    /**
     * 保存用户快捷菜单
     * @param shortcutList
     * @param deleteIds
     * @param user
     * @return
     */
    boolean saveShortcutForMobile(List<AppShortcut> shortcutList, String deleteIds, SysUser user);
}
