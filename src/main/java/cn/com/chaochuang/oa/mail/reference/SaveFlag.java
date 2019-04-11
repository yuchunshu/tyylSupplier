package cn.com.chaochuang.oa.mail.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

public enum SaveFlag implements IDictionary {

    不保存("0", "不保存"), 保存("1", "保存");

    private String key;
    private String value;

    private SaveFlag(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private SaveFlag(String key, String value) {
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
