package cn.com.chaochuang.pubaffairs.car.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author dengl 2018.08.08
 *
 */

public class CarStatusConverter extends EnumDictConverter<CarStatus>  implements AttributeConverter<CarStatus,String>{

}
