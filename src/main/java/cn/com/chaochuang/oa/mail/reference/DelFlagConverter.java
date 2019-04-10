package cn.com.chaochuang.oa.mail.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class DelFlagConverter extends EnumDictConverter<DelFlag> implements AttributeConverter<DelFlag, String> {
}
