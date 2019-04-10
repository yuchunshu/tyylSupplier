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
 * @ClassName: RemoteDocSwapLogStatus 
 * @Description: 限时日志状态
 * @author: yuchunshu
 * @date: 2017年6月12日 下午2:31:45  
 */
public enum RemoteDocSwapLogStatus implements IDictionary {
	失败("0", "失败"), 成功("1", "成功"), 已处理("2", "已处理"), 重复("3", "重复"), 错误("4", "错误");
    private String key;
    private String value;

    private RemoteDocSwapLogStatus(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private RemoteDocSwapLogStatus(String key, String value) {
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
