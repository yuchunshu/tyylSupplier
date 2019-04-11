/*
 * FileName:    SubmitStatusFlag.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年8月18日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.common.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author yuandl
 *
 */
public enum SubmitStatusFlag implements IDictionary {

    暂存("0", "暂存"), 已提交("1", "已提交");
    private String key;
    private String value;

    private SubmitStatusFlag(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private SubmitStatusFlag(String key, String value) {
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
