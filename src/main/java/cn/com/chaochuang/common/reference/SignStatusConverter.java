/*
 * FileName:    FeedbackTypeConverter.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月3日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author HM
 *
 */
public class SignStatusConverter extends EnumDictConverter<SignStatus> implements
                AttributeConverter<SignStatus, String> {

}
