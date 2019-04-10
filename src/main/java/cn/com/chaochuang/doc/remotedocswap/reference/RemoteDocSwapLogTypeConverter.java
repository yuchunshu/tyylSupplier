/*
 * FileName:    RemoteUnitFlagConverter.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/** 
 * @ClassName: RemoteDocSwapLogType 
 * @Description: 限时日志类型
 * @author: chunshu
 * @date: 2017年7月24日 下午6:10:33  
 */
public class RemoteDocSwapLogTypeConverter extends EnumDictConverter<RemoteDocSwapLogType> implements
                AttributeConverter<RemoteDocSwapLogType, String> {

}
