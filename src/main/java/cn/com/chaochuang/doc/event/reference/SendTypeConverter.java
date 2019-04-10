/*
 * FileName:    SendTypeConverter.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年3月28日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.event.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author huangwq
 *
 */
public class SendTypeConverter extends EnumDictConverter<SendType> implements AttributeConverter<SendType, String> {

}
