package cn.com.chaochuang.workflow.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class WfBusinessTypeConverter extends EnumDictConverter<WfBusinessType>
                implements AttributeConverter<WfBusinessType, String> {

}
