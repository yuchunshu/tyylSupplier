package cn.com.chaochuang.common.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

public enum FlowSort implements IDictionary{
	公文("0", "公文"), 其他("1", "其他");
	
	private String key;
    private String value;
    
    private FlowSort(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }
    
    private FlowSort(String key, String value) {
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
