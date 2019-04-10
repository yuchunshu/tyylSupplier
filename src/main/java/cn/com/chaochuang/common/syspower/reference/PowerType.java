package cn.com.chaochuang.common.syspower.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

public enum PowerType implements IDictionary {

    自动增加("0"), 不用授权("1"), 公用授权("2"), 需要授权("3")/* , 非菜单不用授权("6"), 非菜单公用授权("7"), 非菜单需要授权("8") */;

    private String key;
    private String value;

    private PowerType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private PowerType(String key, String value) {
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
