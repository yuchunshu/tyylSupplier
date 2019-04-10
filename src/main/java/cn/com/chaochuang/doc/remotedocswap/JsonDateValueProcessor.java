/*
 * FileName:    JsonDateValueProcessor.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年3月27日 (LLM) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap;

import java.util.Date;

import cn.com.chaochuang.common.util.Tools;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;



/** 
 * @ClassName: JsonDateValueProcessor 
 * @Description: 限时办结工作日查询json日期转换
 * @author: chunshu
 * @date: 2017年8月4日 下午3:39:32  
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
                    return Tools.DATE_FORMAT8.format(value);
                }
                return Tools.DATE_FORMAT.format(value);
            }
        } catch (Exception e) {
        }
        return "";
    }

}
