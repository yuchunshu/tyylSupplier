package cn.com.chaochuang.common.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author rdx 2017-10-17
 *
 */
public class AdministrativeLevelConverter extends EnumDictConverter<AdministrativeLevel> implements
                AttributeConverter<AdministrativeLevel, String> {

}
