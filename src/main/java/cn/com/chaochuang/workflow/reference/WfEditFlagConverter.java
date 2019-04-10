package cn.com.chaochuang.workflow.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class WfEditFlagConverter extends EnumDictConverter<WfEditFlag>
                implements AttributeConverter<WfEditFlag, String> {

}
