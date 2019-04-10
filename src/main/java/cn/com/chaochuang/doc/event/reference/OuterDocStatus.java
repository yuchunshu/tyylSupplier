package cn.com.chaochuang.doc.event.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author yuchunshu 2017.11.25
 *
 */
public enum OuterDocStatus implements IDictionary {

	未签收("1", "未签收"), 已签收("2", "已签收"), 已办结("3", "已办结"), 已撤销("4", "已撤销"), 已作废("5", "已作废"), 不办理("6", "不办理"), 转阅件("7", "转阅件")
    , 办结传阅("8", "办结（传阅）"), 办结归档("9", "办结（归档）"), 退文("10", "退文");
    private String key;
    private String value;

    private OuterDocStatus(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private OuterDocStatus(String key, String value) {
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
