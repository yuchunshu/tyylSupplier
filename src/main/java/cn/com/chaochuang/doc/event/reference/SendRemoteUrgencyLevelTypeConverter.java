package cn.com.chaochuang.doc.event.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class SendRemoteUrgencyLevelTypeConverter extends EnumDictConverter<SendRemoteUrgencyLevelType> implements
                AttributeConverter<SendRemoteUrgencyLevelType, String> {
}
