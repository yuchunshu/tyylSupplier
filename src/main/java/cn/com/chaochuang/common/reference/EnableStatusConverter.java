package cn.com.chaochuang.common.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class EnableStatusConverter extends EnumDictConverter<EnableStatus>
                implements AttributeConverter<EnableStatus, String> {

}
