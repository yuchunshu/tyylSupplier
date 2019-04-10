package cn.com.chaochuang.pubaffairs.repair.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author yucs 2018.08.09
 *
 */

public enum RepairType implements IDictionary{
	
	自行维修("0", "自行维修"), 返厂维修("1", "返厂维修"), 上门维修("2", "上门维修");
	
	private String key;
    private String value;
    
    private RepairType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private RepairType(String key, String value) {
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
