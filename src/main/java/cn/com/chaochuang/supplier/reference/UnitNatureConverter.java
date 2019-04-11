package cn.com.chaochuang.supplier.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class UnitNatureConverter extends EnumDictConverter<UnitNature> implements AttributeConverter<UnitNature, String> {
}
