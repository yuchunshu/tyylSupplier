package cn.com.chaochuang.doc.event.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

public enum DocSource implements IDictionary {

    外单位来文("1", "外单位来文"), 区局内部收文("2", "区局内部收文"), 经费请示("3", "经费请示"), 审批表("4", "审批表"), 群众来信("5", "群众来信"), 上级转来信("6",
                    "上级转来信"), 处室外单位来文("7", "处室外单位来文"), 处室内部收文("8",
                                    "处室内部收文"), 区局发文("a", "区局发文"), 代拟发文("b", "代拟发文"), 处室发文("c", "处室发文");

    private String key;
    private String value;

    private DocSource(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private DocSource(String key, String value) {
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
