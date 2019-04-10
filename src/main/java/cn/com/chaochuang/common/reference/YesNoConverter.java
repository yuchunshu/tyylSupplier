package cn.com.chaochuang.common.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author yuandl
 *
 */
public class YesNoConverter extends EnumDictConverter<YesNo> implements AttributeConverter<YesNo, String> {

}
