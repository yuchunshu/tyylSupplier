/*
 * FileName:    RemoteWorkDateTypeConverter.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/** 
 * @ClassName: RemoteWorkDateTypeConverter 
 * @Description: 限时办结工作日类型
 * @author: yuchunshu
 * @date: 2017年8月1日 上午2:31:45
 */
public class RemoteWorkDateTypeConverter extends EnumDictConverter<RemoteWorkDateType> implements
                AttributeConverter<RemoteWorkDateType, String> {

}
