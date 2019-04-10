/*
 * FileName:    SubmitStatusFlagConverter.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年8月18日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.common.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author yuandl
 *
 */
public class SubmitStatusFlagConverter extends EnumDictConverter<SubmitStatusFlag> implements
                AttributeConverter<SubmitStatusFlag, String> {

}
