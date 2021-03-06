/*
 * FileName:    DeputySexConverter.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年10月28日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author guig
 *
 */
public class SexTypeConverter extends EnumDictConverter<SexType> implements AttributeConverter<SexType, String> {
}
