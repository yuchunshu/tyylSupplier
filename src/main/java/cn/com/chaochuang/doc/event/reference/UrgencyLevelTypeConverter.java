package cn.com.chaochuang.doc.event.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class UrgencyLevelTypeConverter extends EnumDictConverter<UrgencyLevelType> implements
                AttributeConverter<UrgencyLevelType, String> {
}
