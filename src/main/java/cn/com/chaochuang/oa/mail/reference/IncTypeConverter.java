package cn.com.chaochuang.oa.mail.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class IncTypeConverter extends EnumDictConverter<IncType> implements AttributeConverter<IncType, String> {
}
