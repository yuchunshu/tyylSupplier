package cn.com.chaochuang.crm.customer.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class CusCategoryConverter extends EnumDictConverter<CusCategory> implements AttributeConverter<CusCategory, String> {
}
