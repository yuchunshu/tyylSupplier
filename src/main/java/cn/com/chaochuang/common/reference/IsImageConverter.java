package cn.com.chaochuang.common.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class IsImageConverter extends EnumDictConverter<IsImage> implements AttributeConverter<IsImage, String> {
}
