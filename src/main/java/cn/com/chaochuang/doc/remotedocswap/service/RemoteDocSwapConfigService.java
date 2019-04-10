/*
 * FileName:    RemoteDocSwapConfigService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2017年9月18日 (yuchunshu) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.service;

import java.util.Map;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapConfig;


/** 
 * @ClassName: RemoteDocSwapConfigService 
 * @Description: 限时公文配置接口
 * @author: yuchunshu
 * @date: 2017年9月18日 下午5:30:11  
 */
public interface RemoteDocSwapConfigService extends CrudRestService<RemoteDocSwapConfig, Long> {

	/**
     * 查找配置数据
     * @param configType
     * @return
     */
    Map<String,String> findConfigByType(String configType);
    
    /** 
     * @Title: saveSwapConfig 
     * @Description: 保存配置数据
     * @param configId
     * @param configValue
     * @return
     * @return: boolean
     */
    boolean saveSwapConfig(Long configId, String configValue);
    
    /** 
     * @Title: saveSwapConfigList 
     * @Description: 保存配置数据
     * @param configIdStr
     * @param configValue
     * @return
     * @return: boolean
     */
    boolean saveSwapConfigList(String[] configIdStr, String[] configValue);
}
