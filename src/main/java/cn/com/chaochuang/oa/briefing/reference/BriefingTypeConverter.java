package cn.com.chaochuang.oa.briefing.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class BriefingTypeConverter extends EnumDictConverter<BriefingType> implements AttributeConverter<BriefingType, String> {
}
