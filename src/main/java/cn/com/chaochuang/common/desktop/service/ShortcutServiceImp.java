/*
 * FileName:    ShortcutServiceImp.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月21日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.desktop.service;

import java.util.List;

import javax.transaction.Transactional;

import cn.com.chaochuang.common.syspower.domain.SysPower;
import cn.com.chaochuang.common.syspower.repository.SysPowerRepository;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.mobile.bean.AppShortcut;
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

    @Override
    public boolean saveShortcutForMobile(List<AppShortcut> shortcutList, String deleteIds, SysUser user) {

        //删除要删除的桌面数据
        if(StringUtils.isNotEmpty(deleteIds)){
            for(String deleteId : deleteIds.split(",")){
                DesktopShortcut shortcut = repository.findOne(Long.valueOf(deleteId));
                if(shortcut!=null){
                    repository.delete(shortcut);
                }
            }
        }

        if(Tools.isNotEmptyList(shortcutList)){
            for(AppShortcut shortcutData:shortcutList){
                if(shortcutData.getShortcutId()!=null){
                    //修改原有的桌面菜单
                    DesktopShortcut shortcut = repository.findOne(shortcutData.getShortcutId());
                    if(shortcut!=null) {
                        shortcut.setSort(shortcutData.getSort());
                        repository.save(shortcut);
                    }
                }else if(shortcutData.getPowerId()!=null){
                    //添加新的桌面菜单
                    SysPower power = powerRepository.findOne(shortcutData.getPowerId());
                    if(power!=null){
                        DesktopShortcut ds = new DesktopShortcut();
                        ds.setIcon(shortcutData.getIcon());
                        ds.setTitle(power.getPowerName());
                        ds.setUrl(power.getUrl());
                        ds.setAppurl(power.getAppurl());
                        ds.setAppicon(power.getAppicon());
                        ds.setSort(shortcutData.getSort());
                        ds.setUserId(user.getId());
                        this.repository.save(ds);
                    }
                }

            }
        }

        return true;
    }
}
