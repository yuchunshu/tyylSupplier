package cn.com.chaochuang.doc.event.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class DateFlagConverter extends EnumDictConverter<DateFlag> implements AttributeConverter<DateFlag, String> {
}
