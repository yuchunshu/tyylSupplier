/*
 * FileName:    RemoteEnvTypeConverter.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/** 
 * @ClassName: RemoteFeedBackTypeConverter 
 * @Description: TODO
 * @author: chunshu
 * @date: 2017年7月5日 上午11:25:40  
 */
public class RemoteFeedBackTypeConverter extends EnumDictConverter<RemoteFeedBackType> implements
                AttributeConverter<RemoteFeedBackType, String> {

}
