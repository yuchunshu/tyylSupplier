package cn.com.chaochuang.common.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class ManOrDeptConverter extends EnumDictConverter<ManOrDept> implements AttributeConverter<ManOrDept, String> {

}
