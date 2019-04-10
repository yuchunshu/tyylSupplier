package cn.com.chaochuang.doc.expiredate.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class DeadlineTypeConverter extends EnumDictConverter<DeadlineType> implements
                AttributeConverter<DeadlineType, String> {
}
