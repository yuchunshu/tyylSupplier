package cn.com.chaochuang.doc.archive.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author shiql 2017.11.22
 *
 */
public enum BasicType implements IDictionary {

	档案柜("1", "档案柜"), 案卷类型("2", "案卷类型"), 介质类型("3", "介质类型");

    private String key;
    private String value;

    private BasicType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private BasicType(String key, String value) {
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
