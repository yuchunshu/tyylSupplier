package cn.com.chaochuang.pubaffairs.meal.repository;

import java.util.Date;
import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.pubaffairs.meal.domain.OaMeal;
import cn.com.chaochuang.pubaffairs.meal.reference.MealType;

/**
 * @author dengl 2018.08.13
 *
 */

public interface OaMealRepository extends SimpleDomainRepository<OaMeal, String> {
	
	List<OaMeal> findByMealUserNameAndMealDateAndMealType(String mealUserName,Date mealDate,MealType mealType);
}
