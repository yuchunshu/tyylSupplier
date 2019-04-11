/*
 * FileName:    PublishType.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.message.app.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author HM
 *
 */
public enum MesType implements IDictionary {

    待办("0", "待办"), 待阅("1", "待阅"), 邮件("2", "邮件"), 会议("3", "会议"), 日程("4", "日程"), 其余业务("5", "其余业务"), 内部请示("6", "内部请示"),消息("9", "消息");

    private String key;
    private String value;

    private MesType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private MesType(String key, String value) {
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
