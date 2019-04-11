/*
 * FileName:    JsonUtil.java
 * Description:
 * Company:     
 * Copyright:    (c) 2015
 * History:     2015年12月2日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.util;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * @author LJX
 *
 */
public class JsonUtil {

    public static Map<String, String> json2Map(String json) {
        HashMap<String, String> data = new HashMap<String, String>();
        JSONObject jsonObject = JSONObject.fromObject(json);
        Iterator it = jsonObject.keys();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            String value = (String) jsonObject.get(key);
            data.put(key, value);
        }
        return data;
    }

    public static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                    if ("class java.util.Date".equals(descriptors[i].getPropertyType().toString())) {
                        Date date = (Date) propertyUtilsBean.getNestedProperty(obj, name);
                        if (date != null) {
                            params.put(name + ".y", date.getYear() + 1900);
                            params.put(name + ".m", date.getMonth() + 1);
                            params.put(name + ".d", date.getDate());
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }
}
