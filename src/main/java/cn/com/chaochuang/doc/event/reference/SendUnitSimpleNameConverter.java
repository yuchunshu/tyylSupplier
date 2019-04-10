package cn.com.chaochuang.doc.event.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class SendUnitSimpleNameConverter extends EnumDictConverter<SendUnitSimpleName> implements AttributeConverter<SendUnitSimpleName, String> {
}
