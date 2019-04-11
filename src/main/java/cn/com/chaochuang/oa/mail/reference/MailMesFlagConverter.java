package cn.com.chaochuang.oa.mail.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class MailMesFlagConverter extends EnumDictConverter<MailMesFlag> implements AttributeConverter<MailMesFlag, String> {
}
