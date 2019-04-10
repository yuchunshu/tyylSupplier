/*
 * FileName:    RemoteDocSwapConfigServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2017年9月18日 (yuchunshu) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapConfig;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteDocSwapConfigRepository;


/** 
 * @ClassName: RemoteDocSwapConfigServiceImpl 
 * @Description: 限时公文配置接口实现
 * @author: yuchunshu
 * @date: 2017年9月18日 下午5:30:51  
 */
@Service
@Transactional
public class RemoteDocSwapConfigServiceImpl extends SimpleLongIdCrudRestService<RemoteDocSwapConfig> implements
RemoteDocSwapConfigService {

    @Autowired
    private RemoteDocSwapConfigRepository repository;

    @Override
    public SimpleDomainRepository<RemoteDocSwapConfig, Long> getRepository() {
        return this.repository;
    }

	@Override
	public Map<String, String> findConfigByType(String configType) {
		List<RemoteDocSwapConfig> configList = repository.findByConfigType(configType);
		Map<String,String> configMap = new HashMap<String, String>();
		for(RemoteDocSwapConfig config:configList){
			configMap.put(config.getConfigName(),config.getConfigValue());
		}
		return configMap;
	}

	@Override
	public boolean saveSwapConfigList(String[] configIdStr, String[] configValue) {
		if(configIdStr!=null&&configValue!=null&&configIdStr.length==configValue.length){
			for(int i=0;i<configIdStr.length;i++){
				Long configId = Long.parseLong(configIdStr[i]);
				this.saveSwapConfig(configId,configValue[i]);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean saveSwapConfig(Long configId, String configValue) {
		if(configId==null){
			return false;
		}
		RemoteDocSwapConfig swapConfig = repository.findOne(configId);
		if(swapConfig == null){
			return false;
		}
		swapConfig.setConfigValue(configValue);
		repository.save(swapConfig);
		return true;
	}

}
