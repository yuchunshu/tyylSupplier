package cn.com.chaochuang.pubaffairs.meal.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author dengl 2018.08.13
 *
 */

public enum MealType implements IDictionary{
	
	员工餐("0", "员工餐"), 接待餐("1", "接待餐"),加班餐("2", "加班餐"), 自费餐("3", "自费餐");

    private String key;
    private String value;

    private MealType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private MealType(String key, String value) {
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
