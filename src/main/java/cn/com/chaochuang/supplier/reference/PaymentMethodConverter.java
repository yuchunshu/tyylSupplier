package cn.com.chaochuang.supplier.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class PaymentMethodConverter extends EnumDictConverter<PaymentMethod> implements AttributeConverter<PaymentMethod, String> {
}
