package cn.com.chaochuang.common.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author rdx 2017-10-17
 *
 */
public enum AdministrativeLevel implements IDictionary {

    区级("1", "区级"), 市级("2", "市级"), 县级("3", "县级");

    private String key;
    private String value;

    private AdministrativeLevel(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private AdministrativeLevel(String key, String value) {
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
