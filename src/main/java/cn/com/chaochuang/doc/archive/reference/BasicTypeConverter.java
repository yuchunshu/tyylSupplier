package cn.com.chaochuang.doc.archive.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author shiql 2017.11.22
 *
 */
public class BasicTypeConverter extends EnumDictConverter<BasicType> implements AttributeConverter<BasicType, String> {

}
