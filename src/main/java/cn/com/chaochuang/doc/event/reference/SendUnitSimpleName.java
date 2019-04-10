package cn.com.chaochuang.doc.event.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

public enum SendUnitSimpleName implements IDictionary {

    中办国办("中办国办", "中办国办"), 国家部委("国家部委", "国家部委"), 自治区常委系统("自治区常委系统","自治区常委系统"),
    自治区政府系统("自治区政府系统","自治区政府系统"), 区内来文("区内来文","区内来文"), 其它("其它","其它");

    private String key;
    private String value;

    private SendUnitSimpleName(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private SendUnitSimpleName(String key, String value) {
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
