package cn.com.chaochuang.oa.mail.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

public enum MailStatus implements IDictionary {

    草稿("1", "草稿"), 已发送("2", "已发送"), 收件("3", "收件"), 垃圾("4", "垃圾"), 归档("5", "归档"), 逻辑删除("6", "逻辑删除");

    private String key;
    private String value;

    private MailStatus(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private MailStatus(String key, String value) {
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
