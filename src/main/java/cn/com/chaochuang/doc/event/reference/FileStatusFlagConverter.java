/*
 * FileName:    AttachFlagConverter.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.event.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author huangwq
 *
 */
public class FileStatusFlagConverter extends EnumDictConverter<FileStatusFlag> implements
                AttributeConverter<FileStatusFlag, String> {

}
