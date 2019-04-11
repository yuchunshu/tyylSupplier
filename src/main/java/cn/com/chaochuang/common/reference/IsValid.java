/*
 * FileName:    IsValid.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年3月30日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.common.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author yuandl
 *
 */
public enum IsValid implements IDictionary {

    无效("0", "无效"), 有效("1", "有效");

    private String key;
    private String value;

    private IsValid(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private IsValid(String key, String value) {
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
        return key;
    }
}
