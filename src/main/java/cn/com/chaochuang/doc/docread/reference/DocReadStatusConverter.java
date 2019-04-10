/*
 * FileName:    FeedbackTypeConverter.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年2月3日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.docread.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author HeYunTao
 *
 */
public class DocReadStatusConverter extends EnumDictConverter<DocReadStatus> implements
                AttributeConverter<DocReadStatus, String> {

}
