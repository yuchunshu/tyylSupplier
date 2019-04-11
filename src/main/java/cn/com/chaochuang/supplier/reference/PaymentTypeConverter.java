package cn.com.chaochuang.supplier.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class PaymentTypeConverter extends EnumDictConverter<PaymentType> implements AttributeConverter<PaymentType, String> {
}
