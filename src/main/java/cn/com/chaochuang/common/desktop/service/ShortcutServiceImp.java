/*
 * FileName:    ShortcutServiceImp.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年7月21日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.desktop.service;

import java.util.List;

import javax.transaction.Transactional;

import cn.com.chaochuang.common.syspower.domain.SysPower;
import cn.com.chaochuang.common.syspower.repository.SysPowerRepository;
import cn.com.chaochuang.common.user.domain.SysUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.desktop.bean.ShortcutInfo;
import cn.com.chaochuang.common.desktop.domain.DesktopShortcut;
import cn.com.chaochuang.common.desktop.repository.ShortcutRepository;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.util.Tools;

/**
 * @author LJX
 *
 */
@Transactional
@Service
public class ShortcutServiceImp extends SimpleLongIdCrudRestService<DesktopShortcut> implements ShortcutService {

    @Autowired
    private ShortcutRepository repository;
    @Autowired
    private SysPowerRepository powerRepository;

    @Override
    public SimpleDomainRepository<DesktopShortcut, Long> getRepository() {
        return this.repository;
    }

    @Override
    public List<DesktopShortcut> findShortcuts(Long userId) {
        List<DesktopShortcut> curShortcut = this.repository.findByUserIdOrderBySortAsc(userId);
        if (Tools.isNotEmptyList(curShortcut)) {
            return curShortcut;
        } else {
            List<DesktopShortcut> defaultShortcut = this.repository.findByUserIdOrderBySortAsc(0L);
            return defaultShortcut;
        }
    }

    @Override
    public boolean saveShortcut(ShortcutInfo info) {
        this.repository.delete(this.repository.findByUserIdOrderBySortAsc(Long.valueOf(UserTool.getInstance()
                        .getCurrentUserId())));
        if (info.getShortcut() != null && info.getShortcut().length > 0) {
            for (DesktopShortcut d : info.getShortcut()) {
                DesktopShortcut ds = new DesktopShortcut();
                ds.setIcon(d.getIcon());
                ds.setTitle(d.getTitle());
                ds.setUrl(d.getUrl());
                ds.setAppurl(d.getAppurl());
                ds.setSort(d.getSort());
                ds.setUserId(Long.valueOf(UserTool.getInstance().getCurrentUserId()));
                this.repository.save(ds);
            }
        }
        return true;
    }

}
