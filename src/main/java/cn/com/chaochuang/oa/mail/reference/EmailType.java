package cn.com.chaochuang.oa.mail.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

public enum EmailType implements IDictionary {

    个人邮件("1", "个人邮件"), 科室邮件("2", "科室邮件"), 群组邮件("3", "群组邮件");

    private String key;
    private String value;

    private EmailType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private EmailType(String key, String value) {
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
