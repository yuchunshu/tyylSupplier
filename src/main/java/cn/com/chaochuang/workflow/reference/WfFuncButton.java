package cn.com.chaochuang.workflow.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

public enum WfFuncButton implements IDictionary {
    提交("SUT", "提交"), 提交会签("COS", "提交会签"), 阅知("REA", "阅知"), 暂存("SAV", "暂存"), 删除("DEL", "删除"), 套红("RDT", "套红"), 办结("END", "办结"), 打印("PRN", "打印"), 本系统外发("OUT", "本系统外发");
	//隐藏转办操作，当前版本不开放转办功能
	//提交("SUT", "提交"), 提交会签("COS", "提交会签"), 转办("TUR", "转办"),阅知("REA", "阅知"), 暂存("SAV", "暂存"), 删除("DEL", "删除"), 套红("RDT", "套红"), 办结("END", "办结"), 打印("PRN", "打印"), 系统外发("OUT", "系统外发");
	
    private String key;
    private String value;

    private WfFuncButton(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private WfFuncButton(String key, String value) {
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
        return key;
    }
}
