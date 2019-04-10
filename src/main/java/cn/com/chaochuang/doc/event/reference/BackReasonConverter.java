/*
 * FileName:    BackReasonConverter.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年12月7日 (leon) 1.0 Create
 */

package cn.com.chaochuang.doc.event.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class BackReasonConverter extends EnumDictConverter<BackReason>
                implements AttributeConverter<BackReason, String> {

}
