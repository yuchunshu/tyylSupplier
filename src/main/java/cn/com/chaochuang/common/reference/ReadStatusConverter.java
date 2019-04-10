/*
 * FileName:    SexConverter.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2014
 * History:     2014年10月28日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author HeYunTao
 *
 */
public class ReadStatusConverter extends EnumDictConverter<ReadStatus> implements
                AttributeConverter<ReadStatus, String> {
}
