package cn.com.chaochuang.oa.message.im.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class ImDelFlagConverter extends EnumDictConverter<ImDelFlag> implements AttributeConverter<ImDelFlag, String> {
}
