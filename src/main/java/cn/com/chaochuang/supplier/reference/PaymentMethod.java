package cn.com.chaochuang.supplier.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

public enum PaymentMethod implements IDictionary {

    现金("1", "现金"), 电汇("2", "电汇"), 网转("3", "网转"), 汇票("4", "汇票"), 支票("5", "支票");

    private String key;
    private String value;

    private PaymentMethod(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private PaymentMethod(String key, String value) {
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
