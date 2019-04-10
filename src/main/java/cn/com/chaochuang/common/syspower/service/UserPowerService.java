package cn.com.chaochuang.common.syspower.service;

import java.util.List;
import java.util.Set;

import cn.com.chaochuang.common.syspower.domain.SysPowerModule;

public interface UserPowerService {
    public Set<String> userPowerUrlSet(String userId);
    
    /**
     * 验证url权限
     * @param url
     * @param userId
     * @return
     */
    public boolean validateUrlPower(String url, String userId);
    
    public List<SysPowerModule> powerModuleList();
}
