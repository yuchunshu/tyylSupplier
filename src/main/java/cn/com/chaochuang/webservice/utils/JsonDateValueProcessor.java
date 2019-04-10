/*
 * FileName:    JsonDateValueProcessor.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年3月27日 (LLM) 1.0 Create
 */

package cn.com.chaochuang.webservice.utils;

import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import cn.com.chaochuang.common.util.Tools;

/**
 * @author LLM
 *
 */
public class JsonDateValueProcessor implements JsonValueProcessor {
    /**  */
    private boolean timeFlag = false;

    /**
     * 构造函数
     */
    public JsonDateValueProcessor() {
        super();
    }

    /**
     * 构造函数
     *
     * @param timeFlag
     */
    public JsonDateValueProcessor(boolean timeFlag) {
        super();
        this.timeFlag = timeFlag;
    }

    /**
     * @see net.sf.json.processors.JsonValueProcessor#processArrayValue(java.lang.Object, net.sf.json.JsonConfig)
     */
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    /**
     * @see net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang.String, java.lang.Object,
     *      net.sf.json.JsonConfig)
     */
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    /**
     * 处理日期/时间
     *
     * @param value
     * @return
     */
    private Object process(Object value) {
        try {
            if (value instanceof Date) {
                if (this.timeFlag) {

                    return Tools.DATE_TIME_FORMAT.format(value);
                }
                return Tools.DATE_FORMAT.format(value);
            }
        } catch (Exception e) {
        }
        return "";
    }

}
