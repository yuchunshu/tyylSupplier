package cn.com.chaochuang.supplier.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class RecommendLevelConverter extends EnumDictConverter<RecommendLevel> implements AttributeConverter<RecommendLevel, String> {
}
