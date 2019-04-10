package cn.com.chaochuang.oa.mail.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class IncepterStatusConverter extends EnumDictConverter<IncepterStatus> implements
                AttributeConverter<IncepterStatus, String> {
}
