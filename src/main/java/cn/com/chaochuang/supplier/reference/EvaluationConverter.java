package cn.com.chaochuang.supplier.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class EvaluationConverter extends EnumDictConverter<Evaluation> implements AttributeConverter<Evaluation, String> {
}
