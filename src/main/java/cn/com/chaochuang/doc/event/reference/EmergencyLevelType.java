/*
 * FileName:    urgencyLevelType.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.event.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/** 
 * @ClassName: EmergencyLevelType 
 * @Description: 公文交换——紧急程度
 * @author: chunshu
 * @date: 2017年6月28日 下午4:54:29  
 */
public enum EmergencyLevelType implements IDictionary {

	特急("1", "特急"), 急件("2", "急件"), 无("3", "无");

    private String key;
    private String value;

    private EmergencyLevelType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private EmergencyLevelType(String key, String value) {
        this.key = key;
        this.value = value;
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return (null == value) ? name() : value;
    }

    @Override
    public Object getObject() {
        return this;
    }

    @Override
    public String toString() {
        return this.key;
    }

}
