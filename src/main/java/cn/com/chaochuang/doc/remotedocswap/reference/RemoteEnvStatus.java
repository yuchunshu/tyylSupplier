/*
 * FileName:    RemoteEnvStatus.java
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
public enum RemoteEnvStatus implements IDictionary {

	未反馈("0", "未反馈"), 未签收("1", "未签收"), 已签收("2", "已签收"), 已办结("3", "已办结"), 已撤销("4", "已撤销"), 已作废("5", "已作废"), 不办理("6", "不办理"), 转阅件("7", "转阅件")
    , 办结传阅("8", "办结（传阅）"), 办结归档("9", "办结（归档）"), 退文("10", "退文");
    private String key;
    private String value;

    private RemoteEnvStatus(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private RemoteEnvStatus(String key, String value) {
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
