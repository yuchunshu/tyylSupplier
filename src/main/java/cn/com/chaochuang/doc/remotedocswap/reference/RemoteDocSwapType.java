/*
 * FileName:    RemoteUnitFlag.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;


/** 
 * @ClassName: RemoteDocSwapType 
 * @Description: 新建公文交换类型
 * @author: yuchunshu
 * @date: 2017年6月12日 下午2:31:45  
 */
public enum RemoteDocSwapType implements IDictionary {
	办件("0", "办件"), 阅件("1", "阅件");
    private String key;
    private String value;

    private RemoteDocSwapType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private RemoteDocSwapType(String key, String value) {
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
