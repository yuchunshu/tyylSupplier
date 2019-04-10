/*
 * FileName:    RemoteContentFeedback.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author yuandl
 *
 */
public enum RemoteContentFeedback implements IDictionary {

    请求方要求反馈办理情况("A", "请求方要求反馈办理情况"), 请求方要求反馈业务数据("B", "请求方要求反馈业务数据"), 请求方要求反馈征求意见数据("C", "请求方要求反馈征求意见数据"), 请求方要求反馈调研业务数据(
                    "D", "请求方要求反馈调研业务数据"), 无反馈要求("Z", "无反馈要求");

    private String key;
    private String value;

    private RemoteContentFeedback(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private RemoteContentFeedback(String key, String value) {
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
