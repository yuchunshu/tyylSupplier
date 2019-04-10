/*
 * FileName:    RemoteEnvType.java
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
public enum RemoteEnvType implements IDictionary {
    cooperate("0", "cooperate"), feedback("1", "feedback"), download("2", "download"), notify("3", "notify"), receipt("4", "receipt")
    , 正文反馈("5", "feedback"), 办结反馈("6", "feedback"), 撤销("7", "cooperate")
    , 作废("8", "cooperate"), 传阅("9", "feedback"), 归档("10", "feedback"), 退文("11", "feedback"), 修改时限("12", "cooperate"), 重复读取的公文("99", "重复读取的公文");
        	
    private String key;
    private String value;

    private RemoteEnvType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private RemoteEnvType(String key, String value) {
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
