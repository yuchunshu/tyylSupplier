package cn.com.chaochuang.common.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class FlowSortConverter extends EnumDictConverter<FlowSort>
                implements AttributeConverter<FlowSort, String> {

}
