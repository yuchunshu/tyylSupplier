package cn.com.chaochuang.common.syspower.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

public enum IsMenu implements IDictionary {

    否("0"), 是("1");
    private String key;
    private String value;

    private IsMenu(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private IsMenu(String key, String value) {
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
