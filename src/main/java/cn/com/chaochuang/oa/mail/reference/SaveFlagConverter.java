package cn.com.chaochuang.oa.mail.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class SaveFlagConverter extends EnumDictConverter<SaveFlag> implements AttributeConverter<SaveFlag, String> {
}
