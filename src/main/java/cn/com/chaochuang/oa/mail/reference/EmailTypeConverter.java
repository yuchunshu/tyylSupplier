package cn.com.chaochuang.oa.mail.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class EmailTypeConverter extends EnumDictConverter<EmailType> implements AttributeConverter<EmailType, String> {
}
