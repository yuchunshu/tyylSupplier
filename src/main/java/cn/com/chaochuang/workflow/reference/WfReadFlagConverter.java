package cn.com.chaochuang.workflow.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class WfReadFlagConverter extends EnumDictConverter<WfReadFlag> implements AttributeConverter<WfReadFlag, String> {

}
