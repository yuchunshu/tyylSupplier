package cn.com.chaochuang.pubaffairs.meal.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author dengl 2018.08.13
 *
 */
public class MealPlaceConverter extends EnumDictConverter<MealPlace> implements
AttributeConverter<MealPlace, String>{

}
