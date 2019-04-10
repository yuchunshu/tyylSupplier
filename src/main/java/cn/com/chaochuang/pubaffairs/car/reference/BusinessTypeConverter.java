package cn.com.chaochuang.pubaffairs.car.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author dengl 2018.08.24
 *
 */

public class BusinessTypeConverter extends EnumDictConverter<BusinessType>  implements AttributeConverter<BusinessType,String>{

}
