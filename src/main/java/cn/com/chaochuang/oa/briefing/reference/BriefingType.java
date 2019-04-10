package cn.com.chaochuang.oa.briefing.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

public enum BriefingType implements IDictionary {

    国家法律法规("1", "国家法律法规"), 地方法规("2", "地方法规"), 中外条约("3", "中外条约"), 政策参考("4", "政策参考"), 立法追踪("5", "立法追踪"), 司法解释("6", "司法解释");

    private String key;
    private String value;

    private BriefingType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private BriefingType(String key, String value) {
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
