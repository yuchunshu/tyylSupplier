package cn.com.chaochuang.doc.event.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**收文中的“文种”属性*/

public enum DocCate implements IDictionary {

//    党组文件("1", "党组文件"), 党组函("2", "党组函"), 局文件("3", "局文件"), 局函("4", "局函"), 公告("5", "公告"), 通告("6", "通告"),
//    纪要("7","纪要"), 办公室文件("8", "办公室文件"), 办公室函("9", "办公室函");
//	只保留“党组文件”“公告”“纪要”“办公室文件”“办公室函”
	党组文件("1", "党组文件"), 公告("5", "公告"),纪要("7","纪要"), 办公室文件("8", "办公室文件"), 办公室函("9", "办公室函");

    private String key;
    private String value;

    private DocCate(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private DocCate(String key, String value) {
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
