package cn.com.chaochuang.supplier.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class CurrencyConverter extends EnumDictConverter<Currency> implements AttributeConverter<Currency, String> {
}
