package cn.com.chaochuang.oa.mail.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class IsNeedbackConverter extends EnumDictConverter<IsNeedback> implements AttributeConverter<IsNeedback, String> {
}
