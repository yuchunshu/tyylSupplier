/*
 * FileName:    DocsTypeConverter.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.docs.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author HeYunTao
 *
 */
public class DocsTypeConverter extends EnumDictConverter<DocsType> implements AttributeConverter<DocsType, String> {

}
