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
 * @ClassName: RemoteFeedBackType 
 * @Description: 公文交换——反馈类型
 * @author: chunshu
 * @date: 2017年7月5日 上午11:21:30  
 */
public enum RemoteFeedBackType implements IDictionary {
	正文反馈("0", "正文反馈"), 办结反馈("1", "办结反馈"), 撤销("2", "撤销"), 作废("3", "作废"), 修改限时("4", "修改限时"), 办结传阅("5", "办结（传阅）")
		, 办结归档("6", "办结（归档）"), 退文("7", "退文");
    private String key;
    private String value;

    private RemoteFeedBackType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private RemoteFeedBackType(String key, String value) {
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
