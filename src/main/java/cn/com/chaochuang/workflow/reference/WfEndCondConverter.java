package cn.com.chaochuang.workflow.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class WfEndCondConverter extends EnumDictConverter<WfEndCond> implements AttributeConverter<WfEndCond, String> {

}
