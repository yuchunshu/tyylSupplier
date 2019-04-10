package cn.com.chaochuang.workflow.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class WfDealTypeConverter extends EnumDictConverter<WfDealType>
                implements AttributeConverter<WfDealType, String> {

}
