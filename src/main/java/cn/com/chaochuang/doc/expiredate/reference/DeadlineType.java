package cn.com.chaochuang.doc.expiredate.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;


/** 
 * @ClassName: DeadlineType 
 * @Description: 限时办结-截止日期类型
 * @author: yuchunshu
 * @date: 2017年10月16日 下午2:13:50  
 */
public enum DeadlineType implements IDictionary {

	特急("1", "特急"), 急件("2", "急件"), 普通("3", "普通"), 普通一类("4", "普通一类"), 普通二类("5", "普通二类"), 普通三类("6", "普通三类");

    private String key;
    private String value;

    private DeadlineType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private DeadlineType(String key, String value) {
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
