/*
 * FileName:    DataChangeTable.java
 * Description:
 * Company:     
 * Copyright:    (c) 2015
 * History:     2015年4月8日 (LLM) 1.0 Create
 */

package cn.com.chaochuang.oa.datachange.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author LLM
 *
 */
public enum DataChangeTable implements IDictionary {
    词条("oa_personal_word"), 待办事宜("oa_fordo"), 公文办结("oa_wf_flo_hisno"), 组织结构("sys_department"), 人员("sys_user"), 通讯录(
                    "oa_address_personal"), 通知公告("oa_notice"), 邮件接收表("oa_em_incepter"), 内部邮件主表("oa_em_main"), 法律法规(
                    "oa_law"), 会议基础信息("oa_meeting"), 会议详细信息("oa_meeting_attendee");

    private String key;
    private String value;

    /**
     * @param key
     */
    private DataChangeTable(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    /**
     * @param key
     * @param value
     */
    private DataChangeTable(String key, String value) {
        this.key = key;
        this.value = value;
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    /**
     * @see cn.com.chaochuang.common.dictionary.IDictionary#getKey()
     */
    public String getKey() {
        return key;
    }

    /**
     * @see cn.com.chaochuang.common.dictionary.IDictionary#getValue()
     */
    public String getValue() {
        return (null == value) ? name() : value;
    }

    /**
     * @see cn.com.chaochuang.common.dictionary.IDictionary#getObject()
     */
    public Object getObject() {
        return this;
    }

    /**
     * @see java.lang.Enum#toString()
     */
    public String toString() {
        return this.key;
    }
}
