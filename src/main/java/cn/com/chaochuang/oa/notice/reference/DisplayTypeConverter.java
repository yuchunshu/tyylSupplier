/*
 * FileName:    AttachFlagConverter.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.notice.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author HM
 *
 */
public class DisplayTypeConverter extends EnumDictConverter<DisplayType> implements
                AttributeConverter<DisplayType, String> {

}
