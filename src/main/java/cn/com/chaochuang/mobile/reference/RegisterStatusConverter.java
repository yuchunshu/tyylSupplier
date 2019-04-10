/*
 * FileName:    RegisterStatus.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年09月13日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.mobile.reference;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

import javax.persistence.AttributeConverter;

/**
 * @author Shicx
 */
public class RegisterStatusConverter extends EnumDictConverter<RegisterStatus> implements AttributeConverter<RegisterStatus, String> {

}

