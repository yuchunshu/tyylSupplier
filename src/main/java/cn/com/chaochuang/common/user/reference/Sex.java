package cn.com.chaochuang.common.user.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

public enum Sex implements IDictionary {

    // 未知("0", "未知的性别"), 男("1", "男性"), 女("2", "女性"), 未说明("9", "未说明的性别");
    男("1", "男性"), 女("2", "女性");

    private String key;
    private String value;

    private Sex(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private Sex(String key, String value) {
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
