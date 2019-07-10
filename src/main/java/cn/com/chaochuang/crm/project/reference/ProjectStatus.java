package cn.com.chaochuang.crm.project.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

public enum ProjectStatus implements IDictionary {

	洽谈期("1", "洽谈期"), 签约("2", "签约"), 执行("3", "执行"), 验收("4", "验收"), 项目完结("5", "项目完结");

    private String key;
    private String value;

    private ProjectStatus(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private ProjectStatus(String key, String value) {
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
