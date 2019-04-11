package cn.com.chaochuang.oa.mail.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class MailStatusConverter extends EnumDictConverter<MailStatus> implements AttributeConverter<MailStatus, String> {
}
