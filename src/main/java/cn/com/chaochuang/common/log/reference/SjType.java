package cn.com.chaochuang.common.log.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

public enum SjType implements IDictionary {

    普通操作("1", "普通操作"), 部门用户("2", "部门用户"), 角色权限("3", "角色权限"), 违规访问("4", "违规访问"), 其他("5", "其他"), 移动端("8", "移动端");

    private String key;
    private String value;

    private SjType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private SjType(String key, String value) {
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
