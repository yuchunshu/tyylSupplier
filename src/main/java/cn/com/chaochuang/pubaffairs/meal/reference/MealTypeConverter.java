package cn.com.chaochuang.pubaffairs.meal.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author dengl 2018.08.13
 *
 */
public class MealTypeConverter extends EnumDictConverter<MealType> implements
AttributeConverter<MealType, String>{

}
