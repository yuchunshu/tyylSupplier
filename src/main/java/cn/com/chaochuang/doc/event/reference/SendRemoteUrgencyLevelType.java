/*
 * FileName:    urgencyLevelType.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月16日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.event.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author huangwq
 *
 */
public enum SendRemoteUrgencyLevelType implements IDictionary {

    特急("1", "特急"), 加急("2", "加急"), 普通一类("3", "普通一类"), 普通二类("4", "普通二类"), 普通三类("5", "普通三类"), 其它("9", "其它");

    private String key;
    private String value;

    private SendRemoteUrgencyLevelType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private SendRemoteUrgencyLevelType(String key, String value) {
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
