/*
 * FileName:    QueTypeConverter.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年11月17日 (huangm) 1.0 Create
 */

package cn.com.chaochuang.common.maintance.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author huangm
 *
 */
public class QueTypeConverter extends EnumDictConverter<QueType> implements AttributeConverter<QueType, String> {

}
