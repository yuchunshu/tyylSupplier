/*
 * FileName:    SyncDict.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月14日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.velocity;

import java.util.Map;

import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author LJX
 *
 */
@DefaultKey("syncDict")
@ValidScope(Scope.APPLICATION)
public class SyncDict {

    public String value(String name, String key) {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        Map<String, Map<String, String>> syncDictMap = (Map<String, Map<String, String>>) webApplicationContext
                        .getBean("syncDictMap");
        if (syncDictMap != null && syncDictMap.get(name) != null) {
            return syncDictMap.get(name).get(key);
        }
        return null;
    }

}
