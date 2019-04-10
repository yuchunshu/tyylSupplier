package cn.com.chaochuang.common.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class FeedbackFlagConverter extends EnumDictConverter<FeedbackFlag>
                implements AttributeConverter<FeedbackFlag, String> {

}
