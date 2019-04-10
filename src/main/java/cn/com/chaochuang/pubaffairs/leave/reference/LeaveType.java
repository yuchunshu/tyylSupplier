package cn.com.chaochuang.pubaffairs.leave.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author dengl 2018.08.08
 *
 */

public enum LeaveType implements IDictionary{
	
	公休假("0", "公休假"), 补休假("1", "补休假"),事假("2","事假"),产假("3","产假"),陪护假("4","陪护假"),婚假("5","婚假");

    private String key;
    private String value;

    private LeaveType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private LeaveType(String key, String value) {
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
