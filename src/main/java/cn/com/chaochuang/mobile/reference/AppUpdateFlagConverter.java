/*
 * FileName:    AppUpdateFlag.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年08月29日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.mobile.reference;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

import javax.persistence.AttributeConverter;

/**
 * @author Shicx
 */
public class AppUpdateFlagConverter extends EnumDictConverter<AppUpdateFlag> implements AttributeConverter<AppUpdateFlag, String> {

}

