package cn.com.chaochuang.workflow.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class WfNodeTypeConverter extends EnumDictConverter<WfNodeType> implements AttributeConverter<WfNodeType, String> {

}
