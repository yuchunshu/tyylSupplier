package cn.com.chaochuang.common.syspower.service;

import java.util.List;
import java.util.Set;


public interface UserPowerService {
    public Set<String> userPowerUrlSet(String userId);
    
    /**
     * 验证url权限
     * @param url
     * @param userId
     * @return
     */
    public boolean validateUrlPower(String url, String userId);
    
}
