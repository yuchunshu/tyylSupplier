/*
 * FileName:    AppPlatform.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2018
 * History:     2018年01月19日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.mobile.reference;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

import javax.persistence.AttributeConverter;

/**
 * @author Shicx
 */
public class AppPlatformConverter extends EnumDictConverter<AppPlatform> implements AttributeConverter<AppPlatform, String> {

}

