package cn.com.chaochuang.doc.event.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class DenseTypeConverter extends EnumDictConverter<DenseType> implements AttributeConverter<DenseType, String> {
}
