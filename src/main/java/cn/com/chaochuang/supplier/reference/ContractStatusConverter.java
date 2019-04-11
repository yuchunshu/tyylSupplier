package cn.com.chaochuang.supplier.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class ContractStatusConverter extends EnumDictConverter<ContractStatus> implements AttributeConverter<ContractStatus, String> {
}
