package cn.com.chaochuang.doc.remotedocswap.repository;

import java.util.List;


import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapConfig;


/** 
 * @ClassName: RemoteDocSwapConfigRepository 
 * @Description: 限时公文配置jpa
 * @author: yuchunshu
 * @date: 2017年8月31日 下午5:18:21  
 */
public interface RemoteDocSwapConfigRepository extends SimpleDomainRepository<RemoteDocSwapConfig, Long> {

    public List<RemoteDocSwapConfig> findByConfigType(String configType);

    public RemoteDocSwapConfig findByConfigName(String configName);
    
    public RemoteDocSwapConfig findByConfigNameAndConfigType(String configName,String configType);
}
