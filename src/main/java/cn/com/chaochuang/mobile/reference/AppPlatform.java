/*
 * FileName:    AppPlatform.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2018
 * History:     2018年01月19日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.mobile.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author Shicx
 */
public enum AppPlatform implements IDictionary {
    Android("1"),IOS("2");

    private String key;
    private String value;

    /**
     * @param key
     */
    private AppPlatform(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    /**
     * @param key
     * @param value
     */
    private AppPlatform(String key, String value) {
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

