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
 * @ClassName: RemoteDocSwapLogStatusConverter 
 * @Description: 限时日志状态
 * @author: yuchunshu
 * @date: 2017年6月12日 下午2:31:45  
 */
public class RemoteDocSwapLogStatusConverter extends EnumDictConverter<RemoteDocSwapLogStatus> implements
                AttributeConverter<RemoteDocSwapLogStatus, String> {

}
