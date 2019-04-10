/*
 * FileName:    OnlineFlag.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年10月25日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.mobile.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author Shicx
 */
public enum AppOnlineFlag implements IDictionary {
    离线("0"),在线("1");

    private String key;
    private String value;

    /**
     * @param key
     */
    private AppOnlineFlag(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    /**
     * @param key
     * @param value
     */
    private AppOnlineFlag(String key, String value) {
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

