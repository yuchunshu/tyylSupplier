/*
 * FileName:    RemoteEnvStatusConverter.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author chunshu
 *
 */
public class DocSwapCheckStatusConverter extends EnumDictConverter<DocSwapCheckStatus> implements
                AttributeConverter<DocSwapCheckStatus, String> {

}
