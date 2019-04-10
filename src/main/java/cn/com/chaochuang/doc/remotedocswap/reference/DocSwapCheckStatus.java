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
 * @ClassName: DocSwapCheckStatus 
 * @Description: 对账公文状态字典
 * @author: chunshu
 * @date: 2017年7月18日 上午11:01:54  
 */
public enum DocSwapCheckStatus implements IDictionary {

	在办("0", "在办"), 已办("1", "已办"), 已撤销("2", "已撤销"), 已退文("3", "已退文"), 超时未办结("4", "超时未办结")
    , 超时办结("5", "超时办结"), 错误文("6", "错误文");
	 
    private String key;
    private String value;

    private DocSwapCheckStatus(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private DocSwapCheckStatus(String key, String value) {
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
