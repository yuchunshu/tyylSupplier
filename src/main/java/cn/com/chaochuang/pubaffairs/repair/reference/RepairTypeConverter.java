package cn.com.chaochuang.pubaffairs.repair.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author yucs 2018.08.09
 *
 */

public class RepairTypeConverter extends EnumDictConverter<RepairType>  implements AttributeConverter<RepairType,String>{

}
