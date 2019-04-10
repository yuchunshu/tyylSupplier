package cn.com.chaochuang.doc.letter.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 *
 * @author LJX
 *
 */
public enum LetterReceiverStatus implements IDictionary {

    未签收("1", "未签收"), 已签收("2", "已签收"), 已反馈("3", "已反馈"), 需补充("a", "需补充");
    private String key;
    private String value;

    private LetterReceiverStatus(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private LetterReceiverStatus(String key, String value) {
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
