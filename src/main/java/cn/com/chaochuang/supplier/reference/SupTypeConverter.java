package cn.com.chaochuang.supplier.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class SupTypeConverter extends EnumDictConverter<SupType> implements AttributeConverter<SupType, String> {
}
