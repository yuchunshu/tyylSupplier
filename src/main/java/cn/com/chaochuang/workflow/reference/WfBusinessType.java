package cn.com.chaochuang.workflow.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author hzr 2017-4-25
 *
 */
public enum WfBusinessType implements IDictionary {

    收文("1", "收文"), 发文("2", "发文"), 便函("3", "便函"),内部请示("5", "内部请示"),车辆申请("6", "车辆申请"),休假申请("7", "休假申请"),故障报告申请("8", "故障报告申请"),外出申请("9", "外出申请");
	//隐藏通报和部门函件，本系统暂时使用不到
	//收文("1", "收文"), 发文("2", "发文"), 便函("3", "便函"), 通报("4", "通报"), 内部请示("5", "内部请示"), 部门函件("6", "部门函件");

    private String key;
    private String value;

    private WfBusinessType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private WfBusinessType(String key, String value) {
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
