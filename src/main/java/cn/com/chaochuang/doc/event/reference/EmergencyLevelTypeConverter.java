package cn.com.chaochuang.doc.event.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class EmergencyLevelTypeConverter extends EnumDictConverter<EmergencyLevelType> implements
                AttributeConverter<EmergencyLevelType, String> {
}
