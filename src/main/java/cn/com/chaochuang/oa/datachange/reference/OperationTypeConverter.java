/*
 * FileName:    OperationTypeConverter.java
 * Description:
 * Company:     
 * Copyright:    (c) 2015
 * History:     2015年3月27日 (LLM) 1.0 Create
 */

package cn.com.chaochuang.oa.datachange.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author LLM
 *
 */
public class OperationTypeConverter extends EnumDictConverter<OperationType> implements
                AttributeConverter<OperationType, String> {

}
