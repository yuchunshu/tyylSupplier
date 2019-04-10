package cn.com.chaochuang.workflow.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

public enum WfEditFlag implements IDictionary {
    不可编辑("0", "不可编辑"), 编辑意见("1", "编辑意见"), 编写文号("2", "编写文号"), 校对("3", "校对"), 打印("4", "打印");

    private String key;
    private String value;

    private WfEditFlag(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private WfEditFlag(String key, String value) {
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
