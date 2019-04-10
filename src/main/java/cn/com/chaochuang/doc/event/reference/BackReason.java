/*
 * FileName:    BackReason.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年12月7日 (leon) 1.0 Create
 */

package cn.com.chaochuang.doc.event.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author luobin
 *
 */
public enum BackReason implements IDictionary {

    a("a", "内容不符合国家法律、法规和现行政策规定。"), b("b", "行文理由不充分，依据不准确。"), c("c", "文种使用不当。"), d("d",
                    "请示事项不属于办公室职权范围内处理。"), e("e", "请示件一文多事、一事多文或请示事项不具体。"), f("f", "非请示件中夹带请示事项。"), g("g",
                                    "内容涉及其他部门职权范围内的事项应先行会办而没有会办，或已会办但未随文附上说明（包括协调情况，各方意见及理据，主办部门的倾向性意见）和会签部门意见原文。"), h(
                                                    "h",
                                                    "不是领导同志交办或不属紧急、重大、绝密事项而主送领导同志个人。"), i("i", "无特殊情况和理由而越级行为。"), j("j",
                                                                    "附件不全。"), k("k", "请示件未标识签发人姓名。"), l("l",
                                                                                    "纸质公文未加盖呈报机关印章。"), m("m",
                                                                                                    "请示件未标识联系人姓名、联系电话。"), n(
                                                                                                                    "n",
                                                                                                                    "应报送其它部门审批的事项而报送办公室。"), o(
                                                                                                                                    "o",
                                                                                                                                    "其他违反公文处理有关规定的情况。");
    private String key;
    private String value;

    private BackReason(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private BackReason(String key, String value) {
        this.key = key;
        this.value = value;
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    /**
     * (non-Javadoc)
     * 
     * @see cn.com.chaochuang.common.dictionary.IDictionary#getKey()
     */
    @Override
    public String getKey() {
        return key;
    }

    /**
     * (non-Javadoc)
     * 
     * @see cn.com.chaochuang.common.dictionary.IDictionary#getValue()
     */
    @Override
    public String getValue() {
        return (null == value) ? name() : value;
    }

    /**
     * (non-Javadoc)
     * 
     * @see cn.com.chaochuang.common.dictionary.IDictionary#getObject()
     */
    @Override
    public Object getObject() {
        return this;
    }

    @Override
    public String toString() {
        return this.key;
    }

}
