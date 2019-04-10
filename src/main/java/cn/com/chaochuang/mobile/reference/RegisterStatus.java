/*
 * FileName:    RegisterStatus.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年09月13日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.mobile.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author Shicx
 * 短信注册状态
 */
public enum RegisterStatus implements IDictionary {
    未注册("0"), 未完成注册("1"),注册通过("2"), 注册不通过("3"),重新申请("4");

    private String key;
    private String value;

    /**
     * @param key
     */
    private RegisterStatus(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    /**
     * @param key
     * @param value
     */
    private RegisterStatus(String key, String value) {
        this.key = key;
        this.value = value;
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    /**
     * @see IDictionary#getKey()
     */
    public String getKey() {
        return key;
    }

    /**
     * @see IDictionary#getValue()
     */
    public String getValue() {
        return (null == value) ? name() : value;
    }

    /**
     * @see IDictionary#getObject()
     */
    public Object getObject() {
        return this;
    }

    /**
     * @see Enum#toString()
     */
    public String toString() {
        return this.key;
    }
}

