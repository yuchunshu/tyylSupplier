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
 * @author yuandl
 *
 */
public enum RemoteUnitFlag implements IDictionary {
    默认发送单位("0", "默认发送单位"), 自治区政府办公厅("1", "自治区政府办公厅"),
    自治区政府组成部门("2", "自治区政府组成部门"), 自治区政府直属特设机构("3", "自治区政府直属特设机构"),
    自治区政府直属机构("4", "自治区政府直属机构"), 自治区政府部门管理机构("5", "自治区政府部门管理机构"),
    自治区政府派出机构("6", "自治区政府派出机构"), 自治区政府直属事业单位("7", "自治区政府直属事业单位"),
    自治区政府部门管理事业单位("8", "自治区政府部门管理事业单位"), 自治区政府增挂牌子("9", "自治区政府增挂牌子"),
    自治区政府内部设立单位("10", "自治区政府内部设立单位"), 自治区直属单位企业("11", "自治区直属单位企业"),
    高等院校("12", "高等院校"), 中直驻桂单位("13", "中直驻桂单位"),
    市人民政府("14", "市人民政府"), 县人民政府("15", "县人民政府"),
    城区人民政府("16", "城区人民政府"), 其他("17", " 其他");
    private String key;
    private String value;

    private RemoteUnitFlag(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private RemoteUnitFlag(String key, String value) {
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
