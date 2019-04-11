package cn.com.chaochuang.oa.mail.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class ReceiptFlagConverter extends EnumDictConverter<ReceiptFlag> implements AttributeConverter<ReceiptFlag, String> {
}
