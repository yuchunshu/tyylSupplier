/*
 * FileName:    RemoteWorkDateType.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;


/** 
 * @ClassName: RemoteWorkDateType 
 * @Description: 限时办结工作日类型
 * @author: yuchunshu
 * @date: 2017年8月1日 上午2:31:45
 */
public enum RemoteWorkDateType implements IDictionary {
	工作日("0", "工作日"), 节假日("1", "节假日"), 加班日("2", "加班日");
    private String key;
    private String value;

    private RemoteWorkDateType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private RemoteWorkDateType(String key, String value) {
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
