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
 * @ClassName: RemoteDocSwapLogType 
 * @Description: 限时日志类型
 * @author: chunshu
 * @date: 2017年7月24日 下午6:10:33  
 */
public enum RemoteDocSwapLogType implements IDictionary {
	公文交换("docswap", "公文交换"), 工作日("workday", "工作日"), 反馈("feedback", "反馈"), 发送短信("sendMsg", "发送短信");
    
    private String key;
    private String value;

    private RemoteDocSwapLogType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private RemoteDocSwapLogType(String key, String value) {
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
